package controller.board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BoardDAO;
import model.dao.ImageDAO;
import model.dto.BoardDTO;
import model.dto.ImageDTO;

// 특정 게시글을 삭제합니다. 게시글 작성자만 삭제 가능
public class DeleteBoardAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        // 시작 로그
        System.out.println("[INFO] DeleteBoardAction 실행 시작");

        // 1. bid 파라미터 가져오기 및 유효성 검사
        String bidParam = request.getParameter("boardNum");
        if (bidParam == null || bidParam.isEmpty()) {
            try {
                // 2. bid 파라미터가 없거나 빈 문자열일 경우 400 에러 반환
                System.out.println("[ERROR] 게시글 번호(bid)가 제공되지 않았습니다.");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 게시글 번호 필요");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // 3. bid 파라미터를 정수로 변환
        int bid;
        try {
            bid = Integer.parseInt(bidParam);
            System.out.println("[INFO] 게시글 번호 파싱 성공: " + bid);
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] 게시글 번호 파싱 실패: " + bidParam);
            try {
                // 4. bid 파라미터가 정수가 아닌 경우 400 에러 반환
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 게시글 번호는 Int 타입이여야 합니다");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        // 5. 세션에서 사용자 정보를 가져와서 작성자 확인
        HttpSession session = request.getSession();
        Integer writerPK = (Integer) session.getAttribute("memberPK");
        if (writerPK == null) {
            try {
                // 6. 사용자 정보가 없을 경우 401 에러 반환
                System.out.println("[ERROR] 인증되지 않은 사용자입니다.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "401: 인증되지 않은 사용자");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        System.out.println("[INFO] 사용자 ID 확인 성공: " + writerPK);

        // 7. 게시글 정보를 가져오기 위해 BoardDAO와 BoardDTO 객체 생성
        BoardDAO boardDAO = new BoardDAO();
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardNum(bid);
        boardDTO.setCondition("ONE");

        // 8. 게시글 정보를 가져옴
        boardDTO = boardDAO.selectOne(boardDTO);
        
        

        // 9. 게시글이 존재하지 않으면 404 에러 반환
        if (boardDTO == null) {
            try {
                System.out.println("[ERROR] 게시글을 찾을 수 없습니다. 게시글 번호: " + bid);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "404: 게시글을 찾을 수 없습니다");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        
        System.out.println("[INFO] 게시글 조회 성공: 게시글 번호 " + bid);
        
        // 9-1. 삭제 후 경로를 정해주기 위한 저장
        String categoryName = boardDTO.getCategoryName();
        
        // 10. 게시글의 작성자와 세션의 사용자 ID를 비교
        if (boardDTO.getMemberNum() != writerPK) {
            try {
                // 11. 작성자와 사용자 ID가 다르면 403 에러 반환
                System.out.println("[ERROR] 삭제 권한이 없습니다. 사용자 ID(" + writerPK + ")와 작성자 ID(" + boardDTO.getMemberNum() + ")가 일치하지 않습니다.");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "403: 삭제 권한이 없습니다");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        System.out.println("[INFO] 게시글 삭제 권한 확인 완료: 사용자 ID " + writerPK);

        try {
            // 12. 이미지가 있는 경우 이미지 삭제 수행
            boolean hasImages = Boolean.parseBoolean(request.getParameter("hasImages"));
            System.out.println("[INFO] 이미지 존재 여부 확인: " + hasImages);


            ImageDAO imageDAO = new ImageDAO();
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setBoardNum(bid);

            ArrayList<ImageDTO> images = imageDAO.selectAll(imageDTO);
            if (images != null && !images.isEmpty()) {
                System.out.println("[INFO] 이미지 삭제 시작: " + images.size() + "개의 이미지 발견");
                for (ImageDTO img : images) {
                    // 이미지 파일 삭제
                    String imagePath = "C:\\workspace02\\MMBProject\\src\\main\\webapp" + img.getImageWay();
                    File file = new File(imagePath);
                    if (file.exists()) {
                        if (file.delete()) {
                            System.out.println("[INFO] 파일 삭제 성공: " + imagePath);
                        } else {
                            System.out.println("[ERROR] 파일 삭제 실패: " + imagePath);
                            // 파일 삭제 실패 시 에러 반환
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 파일 삭제 실패");
                            return null;
                        }
                    } else {
                        System.out.println("[WARN] 파일이 존재하지 않음: " + imagePath);
                    }

                    // 데이터베이스에서 이미지 정보 삭제
                    boolean imageDeleted = imageDAO.delete(img);
                    if (!imageDeleted) {
                        System.out.println("[ERROR] 이미지 삭제 실패: 이미지 번호 " + img.getImageNum());
                        // 이미지 삭제 실패 시 에러 반환
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 이미지 삭제 실패");
                        return null;
                    } else {
                        System.out.println("[INFO] 이미지 삭제 성공: 이미지 번호 " + img.getImageNum());
                    }
                }
            } else {
                System.out.println("[INFO] 삭제할 이미지가 없습니다.");
            }
            

            // 13. 게시글 삭제 수행
            boardDTO.setCondition(""); // delete 요청 시 혹여나 영향을 주는 것을 방지
            boolean result = boardDAO.delete(boardDTO);
            if (result) {
                // 14. 삭제 성공 시 게시글 목록 페이지로 리다이렉트
                System.out.println("[INFO] 게시글 삭제 성공: 게시글 번호 " + bid);
                ActionForward forward = new ActionForward();
                forward.setRedirect(true);
                if (categoryName.equals("문의")) {
                    forward.setPath("noticeBoard.jsp"); // 문의게시판으로 포워딩
                    System.out.println("[INFO] 문의 카테고리: noticeBoard.jsp로 포워딩 설정");
                } else if (categoryName.equals("일반")) {
                    forward.setPath("boardlist.jsp"); // 공개게시판으로 포워딩
                    System.out.println("[INFO] 일반 카테고리: boardlist.jsp로 포워딩 설정");
                }
                System.out.println("[INFO] DeleteBoardAction 실행 종료");
                return forward;
            } else {
                System.out.println("[ERROR] 게시글 삭제 실패: 게시글 번호 " + bid);
                // 15. 삭제 실패 시 500 에러 반환
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 게시글 삭제 실패");
                return null;
            }
        } catch (Exception e) {
            // 16. 예외 발생 시 500 에러 반환
            System.out.println("[ERROR] 서버 오류 발생");
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 서버 오류");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
