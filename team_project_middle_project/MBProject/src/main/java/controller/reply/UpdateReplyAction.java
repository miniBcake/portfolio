package controller.reply;

import java.io.IOException;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.CheckLoginUtils;
import controller.common.ErrorUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.ReplyDAO;
import model.dto.ReplyDTO;

/**
 * UpdateReplyAction 클래스는 사용자가 작성한 댓글을 수정하는 기능을 처리하는 액션 클래스입니다.
 * 사용자가 로그인된 상태에서 본인이 작성한 댓글을 수정할 수 있으며, 수정 성공 후 댓글 리스트 페이지로 포워딩합니다.
 */

// 댓글 수정 기능을 담당하는 액션 클래스
public class UpdateReplyAction implements Action {
    /**
     * 댓글 수정 로직을 처리합니다.
     * 로그인 상태인지 확인하고, 사용자가 작성한 댓글을 수정합니다.
     * @param request  클라이언트의 HTTP 요청 객체.
     *                 - "replyNum": 수정할 댓글 번호.
     *                 - "boardNum": 댓글이 속한 게시글 번호.
     *                 - "updatedContent": 수정된 댓글 내용.
     * @param response 클라이언트에게 보낼 HTTP 응답 객체.
     * @return ActionForward 댓글 수정 후 이동할 경로를 포함한 ActionForward 객체.
     */
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[INFO] UpdateReplyAction: 댓글 수정 액션 시작");  // 로그: 액션 시작

		// 1. 세션 가져오기
		HttpSession session = request.getSession();

		// 2. CheckLoginUtils를 사용해 로그인 여부 확인
		try {
			boolean isLoggedIn = CheckLoginUtils.checkLogin(session, response, request);
			if (!isLoggedIn) {
				System.out.println("[ERROR] 사용자 로그인 필요");  // 로그: 로그인 필요 메시지
				return null; // 로그인하지 않은 상태라면 null을 반환하여 이후 로직 중단
			}
		} catch (Exception e) {
			System.err.println("[ERROR] 로그인 체크 중 오류 발생");  // 로그: 예외 메시지
			ErrorUtils.handleException(response, e, "로그인 체크 중 오류 발생");
			return null;
		}

		// 로그인 상태에서 세션에서 사용자 ID('memberPK') 가져오기
		Integer memberPK = (Integer) session.getAttribute("memberPK");
		System.out.println("[INFO] 사용자 인증 성공 - memberPK: " + memberPK);  // 로그: 인증 성공

		// 3. 클라이언트로부터 'replyNum', 'boardNum' 및 'updatedContent' 파라미터를 가져옴
		String updatedContent = request.getParameter("updatedContent");
		System.out.println("[INFO] 받은 파라미터 - updatedContent: " + updatedContent);  // 로그: 파라미터 값

		// 4. 'replyNum', 'boardNum' 또는 'updatedContent'가 없거나 빈 문자열인 경우 에러 처리
		if (updatedContent == null || updatedContent.isEmpty()) {
			System.err.println("[ERROR] 필수 입력 값이 누락되었습니다.");
			ErrorUtils.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "400: 필수 입력 값이 누락되었습니다.");
			return null;
		}

		// 5. 'replyNum', 'boardNum' 파라미터를 정수로 변환 및 유효성 검사
		Integer replyNum = ErrorUtils.validateAndParseIntParam(request, response, "replyNum");
		Integer boardNum = ErrorUtils.validateAndParseIntParam(request, response, "boardNum");
		if (replyNum == null || boardNum == null) {
			return null; // 파라미터 검증에서 에러가 발생하면 처리 중단
		}

		// 6. ReplyDAO 및 ReplyDTO 객체 생성
		ReplyDAO replyDAO = new ReplyDAO();
		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setReplyNum(replyNum);  // 댓글 번호 설정
		replyDTO.setMemberNum(memberPK);  // 사용자 ID 설정

		// 7. 댓글 수정 요청을 처리
		replyDTO.setReplyContent(updatedContent);  // 수정된 댓글 내용 설정
		boolean result;
		try {
			result = replyDAO.update(replyDTO);  // 댓글 수정 수행
			System.out.println("[INFO] 댓글 수정 요청 처리 - 결과: " + result);
		} catch (Exception e) {
			System.err.println("[ERROR] 댓글 수정 중 서버 오류 발생");
			ErrorUtils.handleException(response, e, "댓글 수정 중 서버 오류 발생");
			return null;
		}

		// 7. 수정 성공 시, 해당 댓글이 포함된 게시글로 리다이렉트
		if (result) {
			System.out.println("[INFO] 댓글 수정 성공 - 게시글 번호: " + boardNum);  // 로그: 수정 성공 메시지
			request.setAttribute("boardNum", boardNum);  // 게시글 번호를 요청에 설정
			ActionForward forward = new ActionForward();
			forward.setRedirect(false);  // 리다이렉트 설정 (포워드 방식)
			forward.setPath("listReply.do");  // 해당 게시글의 댓글 리스트로 이동
			return forward;
		} else {
			// 9. 수정 실패 시, 이전 페이지로 리다이렉트
			System.err.println("[ERROR] 댓글 수정 실패 - 이전 페이지로 돌아갑니다.");
			try {
				response.sendRedirect(request.getHeader("Referer"));  // 이전 페이지로 리다이렉트
			} catch (IOException e) {
				System.err.println("[ERROR] 응답 중 오류 발생");
				ErrorUtils.handleException(response, e, "응답 중 오류 발생");
			}
			return null;
		}
	}
}
