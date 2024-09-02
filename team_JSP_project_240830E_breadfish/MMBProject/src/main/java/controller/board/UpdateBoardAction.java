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

public class UpdateBoardAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[INFO] UpdateBoardAction 시작");

        // 1. 파라미터 가져오기 및 유효성 검사
        String bidParam = request.getParameter("bid"); // 게시글 번호
        String fixBoardTitle = request.getParameter("fixBoardTitle"); // 수정할 게시글 제목
        String fixBoardContent = request.getParameter("fixBoardContent"); // 수정할 게시글 내용
        String secretBoardContents = request.getParameter("secretBoardContents"); // 비밀글 여부
        String categoryName = request.getParameter("categoryName"); // 카테고리 이름
        String imagePaths = request.getParameter("imagePaths"); // 새로운 이미지 경로

        System.out.println("[INFO] 받은 파라미터 - bid: " + bidParam + ", fixBoardTitle: " + fixBoardTitle + 
                           ", fixBoardContent: " + fixBoardContent + ", secretBoardContents: " + secretBoardContents + 
                           ", categoryName: " + categoryName + ", imagePaths: " + imagePaths);

        // 2. 필수 입력 값이 누락되었는지 확인
        if (bidParam == null || bidParam.isEmpty() || 
            fixBoardTitle == null || fixBoardTitle.isEmpty() ||
            fixBoardContent == null || fixBoardContent.isEmpty() ||
            categoryName == null || categoryName.isEmpty()) {
            System.err.println("[ERROR] 필수 입력 값이 누락되었습니다.");
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 필수 입력 값이 누락되었습니다.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // 3. 게시글 번호 파싱 및 유효성 검사
        int bid;
        try {
            bid = Integer.parseInt(bidParam); // 게시글 번호를 정수로 파싱
            System.out.println("[INFO] 게시글 번호 파싱 성공: " + bid);
        } catch (NumberFormatException e) {
            System.err.println("[ERROR] 게시글 번호 파싱 실패: " + bidParam);
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 잘못된 요청입니다: 게시글 번호는 숫자여야 합니다.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        // 4. 사용자 세션에서 사용자 ID 가져오기
        HttpSession session = request.getSession();
        Integer writerPK = (Integer) session.getAttribute("memberPK"); // 작성자 ID
        if (writerPK == null) {
            System.err.println("[ERROR] 인증되지 않은 사용자입니다.");
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "401: 인증되지 않은 사용자입니다.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        System.out.println("[INFO] 사용자 ID 확인 성공: " + writerPK);

        // 5. BoardDTO 객체 생성 및 수정할 정보 설정
        BoardDAO boardDAO = new BoardDAO();
        BoardDTO boardUpdate = new BoardDTO();

        boardUpdate.setBoardNum(bid); // 게시글 번호 설정
        boardUpdate.setTitle(fixBoardTitle); // 제목 설정
        boardUpdate.setContent(fixBoardContent); // 내용 설정
        boardUpdate.setMemberNum(writerPK); // 작성자 ID 설정
        boardUpdate.setCategoryName(categoryName); // 카테고리 이름 설정

        System.out.println("[INFO] BoardDTO 설정 완료 - " + boardUpdate.toString());

        // 6. 비밀글/공개글 여부 설정
        if (secretBoardContents != null && secretBoardContents.equals("on")) {
            boardUpdate.setVisibility("비공개");  // 비밀글로 설정
            System.out.println("[INFO] 게시글 비밀글로 설정");
        } else {
            boardUpdate.setVisibility("공개");  // 공개글로 설정
            System.out.println("[INFO] 게시글 공개글로 설정");
        }

        // 7. 게시글 업데이트 수행
        boolean result;
        try {
            result = boardDAO.update(boardUpdate); // 게시글 업데이트 시도
            System.out.println("[INFO] 게시글 업데이트 성공 여부: " + result);
        } catch (Exception e) {
            System.err.println("[ERROR] 게시글 업데이트 중 오류 발생");
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 게시글 수정 중 서버 오류가 발생.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        // 8. 이미지 업데이트 수행
        if (result) {
            ImageDAO imageDAO = new ImageDAO();
            if (imagePaths != null && !imagePaths.isEmpty()) {
                System.out.println("[INFO] 기존 이미지 삭제 및 새로운 이미지 추가 시작");
                // 8.1. 기존 이미지 삭제
                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setBoardNum(bid);
                ArrayList<ImageDTO> existingImages = imageDAO.selectAll(imageDTO);
                for (ImageDTO img : existingImages) {
                    System.out.println("[INFO] 삭제할 이미지: " + img.getImageNum() + " - 경로: " + img.getImageWay());
                    boolean deleteResult = imageDAO.delete(img); // 데이터베이스에서 이미지 삭제
                    if (!deleteResult) {
                        System.err.println("[ERROR] 이미지 데이터베이스 삭제 실패: " + img.getImageNum());
                    }
                    File file = new File("C:\\workspace02\\MMBProject\\src\\main\\webapp" + img.getImageWay()); // 이미지 파일 삭제
                    if (file.exists()) {
                        if (file.delete()) {
                            System.out.println("[INFO] 이미지 파일 삭제 성공: " + img.getImageWay());
                        } else {
                            System.err.println("[ERROR] 이미지 파일 삭제 실패: " + img.getImageWay());
                        }
                    }
                }

                // 8.2. 새로운 이미지 삽입
                String[] imagePathArray = imagePaths.split(",");
                for (String imagePath : imagePathArray) {
                    ImageDTO newImageDTO = new ImageDTO();
                    newImageDTO.setImageWay(imagePath.trim()); // 경로 양쪽 공백 제거
                    newImageDTO.setBoardNum(bid);
                    System.out.println("[INFO] 삽입할 이미지 경로: " + imagePath.trim());
                    boolean imageResult = imageDAO.insert(newImageDTO);
                    if (!imageResult) {
                        System.err.println("[ERROR] 이미지 삽입 실패: " + imagePath.trim());
                        try {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 이미지 삽입 실패");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }
            }
        }

        // 9. 업데이트 결과 처리
        if (result) {
            System.out.println("[INFO] 업데이트 성공, 게시글 다시 조회 시작");
            // 9.1. 업데이트 성공 시, 해당 글 다시 조회하여 board.jsp로 포워딩
            try {
                BoardDTO updatedBoard = new BoardDTO();  // 새 BoardDTO 객체 생성
                updatedBoard.setBoardNum(bid); // 게시글 번호 설정
                updatedBoard.setCondition("ONE"); // 조회 조건 설정
                
                updatedBoard = boardDAO.selectOne(updatedBoard);  // 업데이트된 게시글 조회

                if (updatedBoard != null) {
                    System.out.println("[INFO] 업데이트된 게시글 조회 성공: " + updatedBoard.toString());
                    // 10. 조회된 게시글을 request에 설정하고 포워딩
                    request.setAttribute("board", updatedBoard);
                    ActionForward forward = new ActionForward();
                    forward.setRedirect(false);  // 포워딩 방식으로 이동
                    forward.setPath("board.jsp?boardNum=" + bid);
                    return forward;
                } else {
                    System.err.println("[ERROR] 업데이트된 게시글을 찾을 수 없습니다.");
                    // 11. 게시글을 찾을 수 없는 경우 404 오류 응답
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "404: 게시글을 찾을 수 없습니다.");
                    return null;
                }
            } catch (Exception e) {
                System.err.println("[ERROR] 게시글 조회 중 오류 발생");
                e.printStackTrace();
                try {
                    // 12. 조회 중 서버 오류 발생 시 500 오류 응답
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 게시글 조회 중 서버 오류가 발생.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        } else {
            System.err.println("[ERROR] 게시글 업데이트 실패");
            try {
                // 13. 업데이트 실패 시 500 오류 응답
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 게시글 수정에 실패.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
