package controller.reply;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.ReplyDAO;
import model.dto.ReplyDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AddReplyAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1. 세션에서 사용자 정보 가져오기
            HttpSession session = request.getSession();
            Integer memberPK = (Integer) session.getAttribute("memberPK");

            if (memberPK == null) {
                // 401 에러 반환 - 인증되지 않은 사용자
                System.err.println("Error: 인증되지 않은 사용자입니다.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "401: 인증되지 않은 사용자입니다.");
                return null;
            }
            System.out.println("사용자 인증 성공, memberPK: " + memberPK);

            // 2. 요청 파라미터 값 가져오기 및 유효성 검사
            String content = request.getParameter("newReplyContents");
            String bidParam = request.getParameter("boardNum");

            if (content == null || content.isEmpty() || bidParam == null || bidParam.isEmpty()) {
                // 400 에러 반환 - 잘못된 요청
                System.err.println("Error: 댓글 내용 또는 게시글 번호가 누락되었습니다.");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 댓글 내용 또는 게시글 번호가 누락되었습니다.");
                return null;
            }
            System.out.println("댓글 내용 및 게시글 번호 파라미터 확인 성공");

            int bid;
            try {
                bid = Integer.parseInt(bidParam);
                System.out.println("게시글 번호 파싱 성공, bid: " + bid);
            } catch (NumberFormatException e) {
                // 400 에러 반환 - 게시글 번호가 정수가 아님
                System.err.println("Error: 게시글 번호 파싱 실패, bidParam: " + bidParam);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 게시글 번호는 정수여야 합니다.");
                return null;
            }

            // 3. 댓글 객체 생성 및 설정
            ReplyDTO replyDTO = new ReplyDTO();
            replyDTO.setMemberNum(memberPK);
            replyDTO.setReplyContent(content);
            replyDTO.setBoardNum(bid);
            System.out.println("댓글 객체 생성 및 설정 완료: " + replyDTO.toString());

            // 4. 댓글을 DB에 추가
            ReplyDAO replyDAO = new ReplyDAO();
            boolean flag = replyDAO.insert(replyDTO);

            if (!flag) {
                // 500 에러 반환 - 댓글 추가 실패
                System.err.println("Error: 댓글 추가 실패");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 댓글 추가 실패");
                return null;
            }
            System.out.println("댓글 DB 추가 성공");

            // 5. 댓글 추가 성공 시 포워딩 처리
            ActionForward forward = new ActionForward();
            forward.setRedirect(true);
            forward.setPath("reply.jsp"); // 게시글 상세보기 페이지로 이동
            System.out.println("댓글 추가 성공, 페이지 리다이렉트 설정");
            return forward;

        } catch (IOException e) {
            // 클라이언트와의 통신 에러 발생 시 처리
            System.err.println("Error: 클라이언트와의 통신 에러 발생");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            // 기타 예상치 못한 예외 처리
            System.err.println("Error: 알 수 없는 서버 오류 발생");
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 서버 오류");
            } catch (IOException ex) {
                System.err.println("Error: 응답 전송 중 오류 발생");
                ex.printStackTrace();
            }
            return null;
        }
    }
}
