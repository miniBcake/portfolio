package controller.page;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ConfigUtils;
import controller.common.ErrorUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BoardDAO;
import model.dao.ImageDAO;
import model.dto.BoardDTO;
import model.dto.ImageDTO;

/**
 * CheckBoardPageAction 클래스는 게시글 수정 페이지(fixBoard.jsp)로 포워딩하는 액션 클래스입니다.
 * 게시글 작성자만 수정 페이지에 접근할 수 있으며, 게시글 번호와 사용자 인증 정보를 검사합니다.
 */
// 게시글 수정 페이지(fixboard.jsp)로 포워딩
public class CheckBoardPageAction implements Action {
	 /**
     * 클라이언트의 요청을 처리하여 게시글 수정 페이지로 포워딩합니다.
     * 
     * @param request  클라이언트의 HTTP 요청 객체. 
     *                 - "boardNum": 수정할 게시글의 번호.
     * @param response 클라이언트의 HTTP 응답 객체. 에러 발생 시 상태 코드와 함께 에러 메시지를 전달합니다.
     * @return ActionForward 게시글 수정 페이지로 포워딩할 경로 정보를 포함한 ActionForward 객체.
     */
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[INFO] CheckBoardPageAction 시작");

		 // 1. boardNum 파라미터 가져오기 및 유효성 검사
        Integer boardNum = ErrorUtils.validateAndParseIntParam(request, response, "boardNum");
        if (boardNum == null) {
            return null; // 파라미터 유효성 검증 실패 시 처리 중단
        }

		// 2. 세션에서 사용자 인증 정보 가져오기
		HttpSession session = request.getSession();
		Integer writerPK = (Integer) session.getAttribute("memberPK");
		if (writerPK == null) {
			// 세션에서 사용자가 없을때
            System.err.println("[ERROR] 로그인 하지 않은 사용자");
            ErrorUtils.sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "401: 로그인 하지 않은 사용자");
            return null;
		}
		System.out.println("[INFO] 사용자 인증 성공: " + writerPK);

		// 3. DAO와 DTO 인스턴스 생성
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBoardNum(boardNum);
		boardDTO.setCondition("ONE_BOARD");
		ImageDAO imageDAO = new ImageDAO(); // ImageDAO 객체 생성
		ImageDTO imageDTO = new ImageDTO(); // ImageDTO 객체 생성
		imageDTO.setBoardNum(boardNum); // 사진 조회 요청
		try {
			// 4. DAO를 사용하여 게시글 조회
			boardDTO = boardDAO.selectOne(boardDTO);
			if (boardDTO == null) {
                System.out.println("[ERROR] 게시글을 찾을 수 없습니다.");
                ErrorUtils.sendError(response, HttpServletResponse.SC_NOT_FOUND, "404: 게시글을 찾을 수 없습니다.");
                return null;
            }
            System.out.println("[INFO] 게시글 조회 성공: " + boardDTO.toString());

			// 5. 현재 사용자와 게시글 작성자가 일치하지 않을 경우 403 에러 반환
			if (boardDTO.getMemberNum() != writerPK) {
				System.err.println("[ERROR] 수정 권한 없음 - 사용자 ID: " + writerPK + ", 작성자 ID: " + boardDTO.getMemberNum());
                ErrorUtils.sendError(response, HttpServletResponse.SC_FORBIDDEN, "403: 수정 권한이 없습니다.");
                return null;
            }
			System.out.println("[INFO] 수정 권한 확인 완료");
			
			
			// 6. 이미지 정보 조회
			ArrayList<ImageDTO> images = imageDAO.selectAll(imageDTO);
			
			// 게시글 내용에 이미지를 추가하기 위한 StringBuilder
			// 기존 게시글 안지워지는 이유 : StringBuilder contentImages는 boardDTO.getContent()를 사용하여 기존의 게시글 내용을 먼저 가져오고, 그 후에 이미지 태그를 추가하는 방식
			// 기존 게시글이 없을 경우 null을 처리하여 빈 문자열로 초기화
			StringBuilder contentImages = new StringBuilder(boardDTO.getBoardContent() != null ? boardDTO.getBoardContent() : "");

			// 7. images가 null이 아닐 경우에만 request에 설정
			if (images != null && !images.isEmpty()) {
				request.setAttribute("images", images);
				System.out.println("[INFO] 이미지 정보 조회 성공, 이미지 수: " + images.size());
				
			    // 이미지 HTML 태그 생성 및 게시글 내용에 추가
			    for (ImageDTO image : images) {
			    	contentImages.append("<img src='").append(image.getImageWay()).append("' alt='첨부 이미지' style='max-width: 100%;'>");
			    }
			} else {
				System.out.println("[INFO] 이미지 정보가 없습니다.");
			}
			
			// 8. 게시글 내용에 이미지를 포함한 내용을 설정
			boardDTO.setBoardContent(contentImages.toString());
			request.setAttribute("board", boardDTO);
			
			//config.properties 값 JSP 사용을 위해 전달
			request.setAttribute("uploadUrl", ConfigUtils.getProperty("ckEditor.uploadUrl"));
	        request.setAttribute("deleteUrl", ConfigUtils.getProperty("ckEditor.deleteUrl"));
	        request.setAttribute("deleteFetchUrl", ConfigUtils.getProperty("ckEditor.delteFileFetchUrl"));
	        request.setAttribute("submitPostBoardFetchUrl", ConfigUtils.getProperty("ckEditor.submitPostBoardFetchUrl"));
	        
			// 9. 게시글을 수정할 권한이 있고, 게시글이 존재할 경우 수정 페이지로 포워딩			
			ActionForward forward = new ActionForward(); // ActionForward 인스턴스 생성
			forward.setRedirect(false); // 포워딩 방식으로 이동 
			forward.setPath("fixBoard.jsp"); 
			System.out.println("[INFO] CheckBoardPageAction 종료 - 게시글 수정 페이지로 포워딩");
			return forward;
			
		 } catch (Exception e) {
	            System.err.println("[ERROR] 게시글 조회 중 오류 발생");
	            ErrorUtils.handleException(response, e, "게시글 조회 중 오류 발생");
	            return null;
	        }
	    }
	}
