package controller.reply;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.ReplyDAO;
import model.dto.ReplyDTO;
import java.io.IOException;

public class DeleteReplyAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1. 세션에서 사용자 정보 가져오기
            HttpSession session = request.getSession();
            Integer memberPK = (Integer) session.getAttribute("memberPK");

            if (memberPK == null) {
                // 세션에 사용자 정보가 없는 경우 에러 처리
                System.err.println("Error: 인증되지 않은 사용자입니다.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "401: 인증되지 않은 사용자");
                return null;
            }
            System.out.println("사용자 인증 성공, memberPK: " + memberPK);

            // 2. 요청에서 댓글 ID와 게시글 ID 가져오기
            String replyNumParam = request.getParameter("replyNum");
            String bidParam = request.getParameter("bid");

            if (replyNumParam == null || replyNumParam.isEmpty() || bidParam == null || bidParam.isEmpty()) {
                // 필요한 파라미터가 없는 경우 에러 처리
                System.err.println("Error: 필수 파라미터가 없습니다.");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 필수 파라미터 누락");
                return null;
            }

            int replyNum;
            int bid;

            try {
                replyNum = Integer.parseInt(replyNumParam);
                bid = Integer.parseInt(bidParam);
                System.out.println("파라미터 파싱 성공, replyNum: " + replyNum + ", bid: " + bid);
            } catch (NumberFormatException e) {
                // 파라미터가 정수가 아닌 경우 에러 처리
                System.err.println("Error: 잘못된 파라미터 형식");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 잘못된 파라미터 형식");
                return null;
            }

            // 3. 댓글 정보 조회를 위해 객체 생성
            ReplyDAO replyDAO = new ReplyDAO();
            ReplyDTO replyDTO = new ReplyDTO();
            replyDTO.setReplyNum(replyNum);
            replyDTO = replyDAO.selectOne(replyDTO);

            if (replyDTO == null) {
                // 댓글이 존재하지 않는 경우 에러 처리
                System.err.println("Error: 댓글이 존재하지 않습니다.");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "404: 댓글이 존재하지 않습니다");
                return null;
            }

            System.out.println("댓글 조회 성공, 댓글 내용: " + replyDTO.getReplyContent());

            ActionForward forward = new ActionForward();

            if (replyDTO.getMemberNum() == memberPK) {
                // 4. 작성자가 일치하면 댓글 삭제
                boolean isSuccess = replyDAO.delete(replyDTO);
                if (isSuccess) {
                    System.out.println("댓글 삭제 성공");
                    // 삭제 성공 시 게시글 상세보기 페이지로 리다이렉트
                    forward.setRedirect(true);
                    forward.setPath("board.jsp?boardNum=" + bid); // 게시글 상세보기 페이지로 이동
                } else {
                    System.err.println("Error: 댓글 삭제 실패");
                    // 댓글 삭제 실패 시 에러 페이지로 리다이렉트
                    forward.setRedirect(true);
                    forward.setPath("error.do"); 
                }
            } else {
                // 5. 작성자가 일치하지 않거나 댓글이 없으면 에러 페이지로 이동
                System.err.println("Error: 댓글 삭제 권한이 없습니다.");
                forward.setRedirect(true);
                forward.setPath("error.do");
            }

            return forward;

        } catch (IOException e) {
            // IOException 발생 시 에러 처리
            System.err.println("Error: IOException 발생");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            // 기타 예외 발생 시 에러 처리
            System.err.println("Error: 알 수 없는 오류 발생");
            e.printStackTrace();
            return null;
        }
    }
}
