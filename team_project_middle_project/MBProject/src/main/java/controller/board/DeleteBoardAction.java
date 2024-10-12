package controller.board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.CheckLoginUtils;
import controller.common.ConfigUtils;
import controller.common.ErrorUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BoardDAO;
import model.dao.ImageDAO;
import model.dto.BoardDTO;
import model.dto.ImageDTO;

/**
 * DeleteBoardAction 클래스는 특정 게시글을 삭제하는 기능을 담당하는 액션 클래스입니다.
 * 게시글의 작성자만 해당 게시글을 삭제할 수 있으며, 관련된 이미지 파일도 함께 삭제합니다.
 * 삭제가 완료되면 게시글 목록 페이지로 리다이렉트됩니다.
 */
// 특정 게시글을 삭제하는 액션 클래스. 게시글 작성자만 삭제할 수 있음
public class DeleteBoardAction implements Action {
	
	/**
	 * 클라이언트의 요청을 처리하고, 해당 게시글을 삭제한 후 게시글 목록 페이지로 리다이렉트합니다.
	 * 삭제 작업은 게시글 작성자만 수행할 수 있으며, 관련된 이미지도 함께 삭제됩니다.
	 * 
	 * @param request  클라이언트의 HTTP 요청 객체. 
	 *                 - "boardNum": 삭제할 게시글의 번호.
	 * @param response 클라이언트의 HTTP 응답 객체. 에러 발생 시 상태 코드와 함께 에러 메시지를 전달합니다.
	 * @return ActionForward 삭제 후 리다이렉트할 경로 정보를 포함한 ActionForward 객체.
	 */
	
	@Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        // 시작 로그 출력
        System.out.println("[INFO] DeleteBoardAction 실행 시작");

        // 1. 세션에서 로그인 여부 확인
        HttpSession session = request.getSession();            
        try {
            // CheckLoginUtils를 사용해 로그인 여부를 확인
            if (!CheckLoginUtils.checkLogin(session, response, request)) {
                return null;
            }
        } catch (IOException | ServletException e) {
            System.out.println("[ERROR] 로그인 확인 중 오류 발생");
            ErrorUtils.handleException(response, e, "로그인 확인 중 오류 발생");
            return null;
        }

        // 로그인된 사용자 ID 가져오기
        Integer writerPK = (Integer) session.getAttribute("memberPK");
        System.out.println("[INFO] 사용자 ID 확인 성공: " + writerPK);

        // 2. 게시글 번호(boardNum) 파라미터 가져오기 및 유효성 검사
        Integer boardNum = ErrorUtils.validateAndParseIntParam(request, response, "boardNum");
        if (boardNum == null) {
            return null; // 유효성 검증 실패 시 처리 종료
        }

        // 3. 게시글 정보를 가져오기 위해 BoardDAO와 BoardDTO 객체 생성
        BoardDAO boardDAO = new BoardDAO();
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardNum(boardNum);  // 게시글 번호 설정
        boardDTO.setCondition("ONE_BOARD");    // 특정 게시글을 조회하는 조건 설정

        // 4. 게시글 정보를 가져옴
        boardDTO = boardDAO.selectOne(boardDTO);
        if (boardDTO == null) {
            // 게시글이 존재하지 않으면 404 에러 반환
        	 ErrorUtils.sendError(response, HttpServletResponse.SC_NOT_FOUND, "404: 게시글을 찾을 수 없습니다.");
             return null;
         }

        System.out.println("[INFO] 게시글 조회 성공: 게시글 번호 " + boardNum);

        // 5. 게시글 작성자와 로그인된 사용자 ID를 비교하여 삭제 권한 확인
        if (boardDTO.getMemberNum() != writerPK) {
        	ErrorUtils.sendError(response, HttpServletResponse.SC_FORBIDDEN, "403: 삭제 권한이 없습니다.");
            return null;
        }

        System.out.println("[INFO] 게시글 삭제 권한 확인 완료: 사용자 ID " + writerPK);

        // 6. 이미지 삭제 로직 (이미지를 조회 후 존재하는 경우에만 실행)
        try {
            ImageDAO imageDAO = new ImageDAO();
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setBoardNum(boardNum);  // 삭제할 게시글의 이미지들을 조회하기 위해 게시글 번호 설정

            // 해당 게시글의 모든 이미지를 가져옴
            ArrayList<ImageDTO> images = imageDAO.selectAll(imageDTO);  // boardNum에 해당하는 이미지 목록 조회
            if (images != null && !images.isEmpty()) {
                System.out.println("[INFO] 이미지 삭제 시작: " + images.size() + "개의 이미지 발견");
                for (ImageDTO img : images) {
                    // 실제 파일 시스템에서 이미지 파일 삭제
                    String imagePath = ConfigUtils.getProperty("delete.path") + img.getImageWay();
                    File file = new File(imagePath);
                    if (file.exists()) {
                        if (file.delete()) {
                            System.out.println("[INFO] 파일 삭제 성공: " + imagePath);
                        } else {
                            System.out.println("[ERROR] 파일 삭제 실패: " + imagePath);
                            ErrorUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 파일 삭제 실패");
                            return null;
                        }
                    } else {
                        System.out.println("[WARN] 파일이 존재하지 않음: " + imagePath);
                    }

                    // 데이터베이스에서 이미지 정보 삭제
                    boolean imageDeleted = imageDAO.delete(img);  // 이미지 정보를 데이터베이스에서 삭제
                    if (!imageDeleted) {
                        System.out.println("[ERROR] 이미지 삭제 실패: 이미지 번호 " + img.getImageNum());
                        ErrorUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 이미지 삭제 실패");
                        return null;
                    } else {
                        System.out.println("[INFO] 이미지 삭제 성공: 이미지 번호 " + img.getImageNum());
                    }
                }
            } else {
                System.out.println("[INFO] 삭제할 이미지가 없습니다.");
            }
        } catch (Exception e) {
        	ErrorUtils.handleException(response, e, "이미지 삭제 중 오류 발생");
            return null;
        }

        // 8. 게시글 삭제 작업 수행
        boardDTO.setCondition("");  // 삭제 시 영향을 줄 수 있는 조건을 제거
        boolean result = boardDAO.delete(boardDTO);
        if (result) {
            System.out.println("[INFO] 게시글 삭제 성공: 게시글 번호 " + boardNum);

            ActionForward forward = new ActionForward();
            forward.setRedirect(true);  // 삭제 후 리다이렉트 설정

            // 카테고리별로 리다이렉트 경로 설정
            String categoryName = boardDTO.getBoardCateName();
            if (categoryName.equals("request")) {
                forward.setPath("listBoards.do?categoryName=request"); // 문의게시판으로 리다이렉트
                System.out.println("[INFO] 문의 카테고리: listBoards.do로 리다이렉트 설정");
            } else if (categoryName.equals("normal")) {
                forward.setPath("listBoards.do?categoryName=normal"); // 공개게시판으로 리다이렉트
                System.out.println("[INFO] 일반 카테고리: listBoards.do로 리다이렉트 설정");
            }

            System.out.println("[INFO] DeleteBoardAction 실행 종료");
            return forward;
        } else {
            ErrorUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 게시글 삭제 실패");
            return null;
        }
    }
}
