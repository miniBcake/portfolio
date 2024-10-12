package controller.reply;

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
 * AddReplyAction 클래스는 새로운 댓글을 추가하는 액션 클래스입니다.
 * 로그인한 사용자가 작성한 댓글을 데이터베이스에 저장한 후,
 * 성공적으로 저장되면 해당 게시글로 다시 포워딩합니다.
 */
public class AddReplyAction implements Action {
	
	/**
     * 새로운 댓글을 추가하는 로직을 처리합니다.
     * 사용자가 로그인 상태인지 확인하고, 댓글 내용을 검증한 후 댓글을 데이터베이스에 저장합니다.
     * 성공 시 댓글 리스트 페이지로 포워딩합니다.
     *
     * @param request  클라이언트의 HTTP 요청 객체
     *                 - "newReplyContents": 댓글 내용.
     *                 - "boardNum": 댓글이 달린 게시글 번호.
     * @param response 클라이언트에게 보낼 HTTP 응답 객체
     * @return ActionForward 댓글 리스트 페이지로의 포워딩 경로를 포함한 ActionForward 객체
     */
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 1. 세션에서 사용자 정보 가져오기
			HttpSession session = request.getSession();
			System.out.println("[INFO] AddReplyAction: 사용자 세션에서 정보 가져오기 시도");

			// 로그인 여부를 확인하여 로그인되지 않았으면 null을 반환
			if (!CheckLoginUtils.checkLogin(session, response, request)) {
				System.out.println("[ERROR] 사용자 로그인 필요");
				return null; 
			}

			// 세션에서 사용자 ID (memberPK) 가져오기
			Integer memberPK = (Integer) session.getAttribute("memberPK");
			System.out.println("[INFO] 사용자 인증 성공, memberPK: " + memberPK);

			// 2. 요청 파라미터 값 가져오기 및 유효성 검사
			String content = request.getParameter("newReplyContents"); // 댓글 내용

            // 필수 파라미터 유효성 검사 (댓글 내용이 비어있는지 확인)
            if (content == null || content.isEmpty()) {
                System.err.println("[ERROR] 필수 파라미터 누락: 댓글 내용이 누락됨");
                ErrorUtils.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "400: 댓글 내용이 누락되었습니다.");
                return null;
            }
            System.out.println("[INFO] 댓글 내용 파라미터 확인 성공");

            // 게시글 번호를 ErrorUtils에서 파싱 및 유효성 검사
            Integer boardNum = ErrorUtils.validateAndParseIntParam(request, response, "boardNum");
            if (boardNum == null) {
                return null;  // 파라미터 오류 시 함수에서 이미 에러가 처리되므로 추가 조치 불필요
            }

			// 3. 댓글 객체 생성 및 설정
			ReplyDTO replyDTO = new ReplyDTO();
			replyDTO.setMemberNum(memberPK); // 사용자 ID 설정
			replyDTO.setReplyContent(content); // 댓글 내용 설정
			replyDTO.setBoardNum(boardNum); // 게시글 번호 설정
			System.out.println("[INFO] 댓글 객체 생성 및 설정 완료: " + replyDTO.toString());

			// 4. 댓글을 DB에 추가
			ReplyDAO replyDAO = new ReplyDAO();
			boolean addReplyToDB = replyDAO.insert(replyDTO); // 댓글을 데이터베이스에 삽입 시도

			// 댓글 삽입 실패 시 500 에러 반환
			if (!addReplyToDB) {
				System.err.println("[ERROR] 댓글 추가 실패");
				ErrorUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 댓글 추가 실패");
				return null;
			}
			System.out.println("[INFO] 댓글 DB 추가 성공");

			// 5. 댓글 추가 성공 시 해당게시글 댓글리스트로 퍼워딩
			request.setAttribute("boardNum", boardNum); // 게시글 번호를 리퀘스트에 설정
			ActionForward forward = new ActionForward();
			forward.setRedirect(false); // 포워딩 방식 설정 
			forward.setPath("listReply.do"); // 댓글 리스트 페이지로 이동
			System.out.println("[INFO] 댓글 추가 성공, listReply.do로 포워딩");
			return forward;
		} catch (Exception e) {
			System.err.println("[ERROR] 서버 오류 발생");
			e.printStackTrace();
			ErrorUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 서버 오류 발생");
			return null;
		}
	}
}
