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
 * DeleteReplyAction 클래스는 댓글 삭제를 처리하는 액션 클래스입니다.
 * 로그인한 사용자가 댓글 작성자인지 확인하고, 댓글 작성자와 일치할 경우 댓글을 삭제합니다.
 * 삭제 성공 여부에 따라 적절한 응답을 클라이언트에게 전송합니다.
 */

public class DeleteReplyAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
    	 /**
         * HTTP 요청을 처리하는 메서드입니다.
         * 로그인 상태를 확인하고, 요청된 댓글이 현재 로그인한 사용자가 작성한 댓글인지 확인한 후,
         * 해당 댓글을 삭제합니다.
         * @param request  클라이언트로부터의 HTTP 요청 객체. 댓글 번호와 게시글 번호를 포함합니다.
         *                 - "replyNum": 확인하려는 댓글 번호 .
         *                 - "boardNum": 댓글이 속한 게시글 번호 .
         * @param response 클라이언트에게 보낼 HTTP 응답 객체.
         * @return ActionForward 댓글 삭제 후 이동할 경로를 포함한 ActionForward 객체.
         */
        try {
            // 1. 세션에서 사용자 정보 가져오기
            HttpSession session = request.getSession();
            Integer memberPK = (Integer) session.getAttribute("memberPK");
            System.out.println("[INFO] 사용자 세션에서 정보 가져오기 시도");

            // 로그인 여부를 확인하여 로그인되지 않았으면 null을 반환
            if (!CheckLoginUtils.checkLogin(session, response, request)) {
                System.out.println("[ERROR] 사용자 로그인 필요");
                return null;
            }
            
            System.out.println("[INFO] 사용자 인증 성공, memberPK: " + memberPK);

            // 2. 요청 파라미터 파싱 및 유효성 검사
            Integer replyNum = ErrorUtils.validateAndParseIntParam(request, response, "replyNum"); // 댓글 번호
            Integer boardNum = ErrorUtils.validateAndParseIntParam(request, response, "boardNum"); // 게시글 번호
            if (replyNum == null || boardNum == null) {
                return null; // 잘못된 파라미터 형식이므로 종료
            }

            // 3. 댓글 정보 조회 및 권한 검증
            ReplyDAO replyDAO = new ReplyDAO();
            ReplyDTO replyDTO = new ReplyDTO();
            replyDTO.setCondition("REPLY_ONE");
            replyDTO.setReplyNum(replyNum);

            replyDTO = replyDAO.selectOne(replyDTO);

            // *** DB 검증 이유  ***
            // DB에서 댓글 작성자 정보를 조회하여 현재 세션의 memberPK와 비교합니다.
            // 클라이언트에서 전달된 memberPK는 신뢰할 수 없으므로, DB에서 한 번 더 확인하여
            // 사용자가 댓글의 실제 작성자인지 검증합니다.
            // 이 검증은 다음과 같은 이유로 중요합니다:
            // 1. 클라이언트에서 전달된 데이터는 변조될 수 있습니다.
            // 2. 세션 탈취 및 세션 변조로 인해 잘못된 사용자가 댓글을 삭제할 수 있는 위험을 줄입니다.
            // 3. 세션 정보와 DB 정보의 동기화를 보장하여, 권한 변동이 있더라도 정확한 검증을 수행합니다.

            if (replyDTO == null) {
                System.err.println("[ERROR] 댓글이 존재하지 않음 - replyNum: " + replyNum);
                ErrorUtils.sendError(response, HttpServletResponse.SC_NOT_FOUND, "404: 댓글이 존재하지 않습니다");
                return null;
            }

            System.out.println("[INFO] 댓글 조회 성공 - " + replyDTO.toString());

            // 4. 댓글 작성자와 현재 로그인한 사용자의 ID(memberPK)가 일치하는지 확인
            if (replyDTO.getMemberNum() != memberPK) {
                System.err.println("[ERROR] 댓글 삭제 권한 없음 - memberPK: " + memberPK + ", 작성자 ID: " + replyDTO.getMemberNum());
                ErrorUtils.sendError(response, HttpServletResponse.SC_FORBIDDEN, "403: 댓글 삭제 권한이 없습니다");
                return null;
            }

            // 5. 댓글 작성자가 로그인한 사용자와 일치할 경우 삭제 수행
            boolean isSuccess = replyDAO.delete(replyDTO);
            if (!isSuccess) {
                System.err.println("[ERROR] 댓글 삭제 실패 - replyNum: " + replyNum);
                ErrorUtils.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 댓글 삭제 실패");
                return null;
            }

            // 6. 성공 시 포워딩
            System.out.println("[INFO] 댓글 삭제 성공 - replyNum: " + replyNum);
            request.setAttribute("boardNum", boardNum);

            ActionForward forward = new ActionForward();
            forward.setRedirect(false);
            forward.setPath("listReply.do");  // 댓글 리스트로 포워딩
            return forward;

        } catch (Exception e) {
        	ErrorUtils.handleException(response, e, "알 수 없는 서버 오류 발생");
            return null;
        }
    }

}
