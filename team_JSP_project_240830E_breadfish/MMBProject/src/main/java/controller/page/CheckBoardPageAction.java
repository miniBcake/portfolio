package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BoardDAO;
import model.dao.ImageDAO;
import model.dto.BoardDTO;
import model.dto.ImageDTO;

import java.io.IOException;
import java.util.ArrayList;

// 게시글 수정 페이지(fixboard.jsp)로 포워딩
public class CheckBoardPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[INFO] CheckBoardPageAction 시작");

		// 1. boardNum 파라미터 가져오기 및 유효성 검사
		String bidParam = request.getParameter("boardNum");
		System.out.println("[INFO] 받은 파라미터 - boardNum: " + bidParam);

		if (bidParam == null || bidParam.isEmpty()) {
			System.err.println("[ERROR] 게시글 번호 파라미터가 없습니다.");
			// 2. 게시글 번호가 없을 경우 400 에러 반환
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 게시글 번호 없음");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		int bid;
		try {
			// 3. boardNum 파라미터를 정수로 변환
			bid = Integer.parseInt(bidParam);
			System.out.println("[INFO] 게시글 번호 파싱 성공: " + bid);
		} catch (NumberFormatException e) {
			System.err.println("[ERROR] 게시글 번호 파싱 실패: " + bidParam);
			// 4. boardNum이 정수가 아닐 경우 400 에러 반환
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 게시글 번호는 Int타입이어야합니다.");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return null;
		}

		// 5. 세션에서 사용자 인증 정보 가져오기
		HttpSession session = request.getSession();
		Integer writerPK = (Integer) session.getAttribute("memberPK");
		if (writerPK == null) {
			System.err.println("[ERROR] 인증되지 않은 사용자");
			// 6. 사용자가 인증되지 않았을 경우 401 에러 반환
			try {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "401: 인증되지 않은 사용자");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		System.out.println("[INFO] 사용자 인증 성공: " + writerPK);

		// 7. DAO와 DTO 인스턴스 생성
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBoardNum(bid);
		boardDTO.setCondition("ONE");
		ImageDAO imageDAO = new ImageDAO(); // ImageDAO 객체 생성
		ImageDTO imageDTO = new ImageDTO(); // ImageDTO 객체 생성
		imageDTO.setBoardNum(bid); // 사진 조회 요청
		try {
			// 8. DAO를 사용하여 게시글 조회
			boardDTO = boardDAO.selectOne(boardDTO);
			System.out.println("[INFO] 게시글 조회 성공: " + boardDTO.toString());

			// 9. 현재 사용자와 게시글 작성자가 일치하지 않을 경우 403 에러 반환
			if (boardDTO.getMemberNum() != writerPK) {
				System.err.println("[ERROR] 수정 권한 없음 - 사용자 ID: " + writerPK + ", 작성자 ID: " + boardDTO.getMemberNum());
				try {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "403: 수정 권한이 없습니다");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			System.out.println("[INFO] 수정 권한 확인 완료");
			
			// 10. 이미지 정보 조회
			ArrayList<ImageDTO> images = imageDAO.selectAll(imageDTO);

			// 11. 조회된 게시글을 request에 설정하여 JSP에서 사용하도록 함
			request.setAttribute("boardDTO", boardDTO);

			// 12. images가 null이 아닐 경우에만 request에 설정
			if (images != null && !images.isEmpty()) {
				request.setAttribute("images", images);
				request.setAttribute("hasImages", true); // 이미지가 있음을 알림
				System.out.println("[INFO] 이미지 정보 조회 성공, 이미지 수: " + images.size());
			} else {
				request.setAttribute("hasImages", false); // 이미지가 없음을 알림
				System.out.println("[INFO] 이미지 정보가 없습니다.");
			}

			// 13. 게시글을 수정할 권한이 있고, 게시글이 존재할 경우 수정 페이지로 포워딩			
			ActionForward forward = new ActionForward(); // ActionForward 인스턴스 생성
			forward.setRedirect(false); // 포워딩 방식으로 이동 
			forward.setPath("fixboard.jsp"); // fixboard.jsp로 페이지 설정
			System.out.println("[INFO] CheckBoardPageAction 종료 - 게시글 수정 페이지로 포워딩");
			return forward;
		
		} catch (Exception e) {
			System.err.println("[ERROR] 게시글 조회 중 오류 발생");
			e.printStackTrace();
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 게시글 조회 중 서버 오류가 발생.");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return null;
		}
	}
}

