package controller.reply;

import java.io.IOException;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.ReplyDAO;
import model.dto.ReplyDTO;

// 댓글 수정 기능을 담당하는 액션 클래스
public class UpdateReplyAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("UpdateReplyAction 시작");  // 로그: 액션 시작

        // 1. 클라이언트로부터 'replyNum' 및 'updatedContent' 파라미터를 가져옴
        String replyNumParam = request.getParameter("replyNum");
        String updatedContent = request.getParameter("updatedContent");
        System.out.println("받은 파라미터 - replyNum: " + replyNumParam + ", updatedContent: " + updatedContent);  // 로그: 파라미터 값

        // 2. 'replyNum' 또는 'updatedContent'가 없거나 빈 문자열인 경우 에러 처리
        if (replyNumParam == null || replyNumParam.isEmpty() || updatedContent == null || updatedContent.isEmpty()) {
            System.err.println("필수 입력 값이 누락되었습니다.");  // 로그: 에러 메시지
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "필수 입력 값이 누락되었습니다.");
            } catch (IOException e) {
                System.err.println("응답 중 오류 발생");  // 로그: 예외 메시지
                e.printStackTrace();
            }
            return null;
        }

        // 3. 'replyNum' 파라미터를 정수로 변환 및 유효성 검사
        int replyNum;
        try {
            replyNum = Integer.parseInt(replyNumParam);
            System.out.println("replyNum 파싱 성공: " + replyNum);  // 로그: 파싱 성공
        } catch (NumberFormatException e) {
            System.err.println("잘못된 요청입니다: 댓글 번호는 숫자여야 합니다.");  // 로그: 에러 메시지
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다: 댓글 번호는 숫자여야 합니다.");
            } catch (IOException ex) {
                System.err.println("응답 중 오류 발생");  // 로그: 예외 메시지
                ex.printStackTrace();
            }
            return null;
        }

        // 4. 세션에서 사용자 ID('memberPK') 가져오기
        HttpSession session = request.getSession();
        Integer memberPK = (Integer) session.getAttribute("memberPK");

        // 5. 사용자 인증 여부 확인
        if (memberPK == null) {
            System.err.println("인증되지 않은 사용자입니다.");  // 로그: 에러 메시지
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증되지 않은 사용자입니다.");
            } catch (IOException e) {
                System.err.println("응답 중 오류 발생");  // 로그: 예외 메시지
                e.printStackTrace();
            }
            return null;
        }
        System.out.println("사용자 인증 성공 - memberPK: " + memberPK);  // 로그: 인증 성공

        // 6. ReplyDAO 및 ReplyDTO 객체 생성
        ReplyDAO replyDAO = new ReplyDAO();
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setReplyNum(replyNum);
        replyDTO.setMemberNum(memberPK);

        // 7. 댓글이 존재하고, 작성자가 본인인지 확인
        try {
            ReplyDTO existingReply = replyDAO.selectOne(replyDTO);
            if (existingReply == null || existingReply.getMemberNum() != memberPK) {
                System.err.println("댓글 수정 권한이 없습니다.");  // 로그: 에러 메시지
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "댓글 수정 권한이 없습니다.");
                return null;
            }
            System.out.println("댓글 존재 및 수정 권한 확인 - replyNum: " + replyNum);  // 로그: 권한 확인 성공
        } catch (Exception e) {
            System.err.println("댓글 확인 중 서버 오류가 발생했습니다.");  // 로그: 예외 메시지
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "댓글 확인 중 서버 오류가 발생했습니다.");
            } catch (IOException ex) {
                System.err.println("응답 중 오류 발생");  // 로그: 예외 메시지
                ex.printStackTrace();
            }
            return null;
        }

        // 8. 댓글 수정 요청을 처리
        replyDTO.setReplyContent(updatedContent);
        boolean result;
        try {
            result = replyDAO.update(replyDTO);
            System.out.println("댓글 수정 요청 처리 - 결과: " + result);  // 로그: 수정 결과
        } catch (Exception e) {
            System.err.println("댓글 수정 중 서버 오류가 발생했습니다.");  // 로그: 예외 메시지
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "댓글 수정 중 서버 오류가 발생했습니다.");
            } catch (IOException ex) {
                System.err.println("응답 중 오류 발생");  // 로그: 예외 메시지
                ex.printStackTrace();
            }
            return null;
        }

        // 9. 수정 성공 시, 해당 댓글이 포함된 게시글로 리다이렉트
        if (result) {
            try {
                int boardNum = replyDTO.getBoardNum(); // 댓글이 속한 게시글 번호를 가져옴
                System.out.println("댓글 수정 성공 - 게시글 번호: " + boardNum);  // 로그: 수정 성공 메시지
                ActionForward forward = new ActionForward();
                forward.setRedirect(true);
                forward.setPath("board.jsp?boardNum=" + boardNum);  // 수정된 댓글이 포함된 게시글로 이동
                return forward;
            } catch (Exception e) {
                System.err.println("댓글 조회 중 서버 오류가 발생했습니다.");  // 로그: 예외 메시지
                e.printStackTrace();
                try {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "댓글 조회 중 서버 오류가 발생했습니다.");
                } catch (IOException ex) {
                    System.err.println("응답 중 오류 발생");  // 로그: 예외 메시지
                    ex.printStackTrace();
                }
                return null;
            }
        } else {
            // 10. 수정 실패 시, 이전 페이지로 돌아가는 스크립트 실행
            System.err.println("댓글 수정 실패 - 이전 페이지로 돌아갑니다.");  // 로그: 수정 실패 메시지
            try {
                response.getWriter().print("<script>history.back();</script>");
            } catch (IOException e) {
                System.err.println("응답 중 오류 발생");  // 로그: 예외 메시지
                e.printStackTrace();
            }
            return null;
        }
    }
}
