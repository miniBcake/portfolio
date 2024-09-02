package controller.reply;

import java.io.IOException;
import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ReplyDAO;
import model.dto.ReplyDTO;

// 특정 게시글의 모든 댓글을 조회하는 액션 클래스
public class ListReplyAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        // 1. 게시글 번호(boardNum)를 파라미터로 가져오기 및 유효성 검사
        String bidparam = request.getParameter("boardNum");
        System.out.println("요청된 게시글 번호: " + bidparam);

        if (bidparam == null || bidparam.isEmpty()) {
            System.err.println("Error: 게시글 번호가 필요합니다.");
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다: 게시글 번호가 필요합니다.");
            } catch (IOException e) {
                System.err.println("Error: 응답 중 오류 발생");
                e.printStackTrace();
            }
            return null;
        }

        int bid;
        try {
            bid = Integer.parseInt(bidparam);
            System.out.println("게시글 번호 파싱 성공: " + bid);
        } catch (NumberFormatException e) {
            System.err.println("Error: 게시글 번호는 숫자여야 합니다.");
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다: 게시글 번호는 숫자여야 합니다.");
            } catch (IOException ex) {
                System.err.println("Error: 응답 중 오류 발생");
                ex.printStackTrace();
            }
            return null;
        }

        // 2. 기본값 설정
        int currentPage = 1; // 현재 페이지 번호
        int replySize = 10; // 페이지당 댓글 수

        // 요청으로부터 페이지 번호 받아오기
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
            System.out.println("요청된 페이지 번호: " + currentPage);
        }

        // 3. 댓글을 조회하기 위해 DAO와 DTO 객체를 생성합니다.
        ReplyDAO replyDAO = new ReplyDAO();

        // 페이지네이션을 위해 필요한 시작 번호와 끝 번호 계산
        int startNum = (currentPage - 1) * replySize + 1;
        int endNum = currentPage * replySize;
        System.out.println("댓글 조회 시작 번호: " + startNum + ", 끝 번호: " + endNum);

        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setBoardNum(bid);  // 조회할 게시글 번호 설정
        replyDTO.setPageStartNum(startNum);  // 시작 댓글 번호 설정
        replyDTO.setPageEndNum(endNum);      // 끝 댓글 번호 설정

        // 4. DAO를 사용하여 해당 게시글의 모든 댓글을 조회합니다.
        ArrayList<ReplyDTO> replyList = null;
        try {
            replyList = replyDAO.selectAll(replyDTO);
            System.out.println("댓글 조회 성공, 총 " + replyList.size() + "개 조회");
        } catch (Exception e) {
            System.err.println("Error: 댓글 조회 중 서버 오류가 발생했습니다.");
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "댓글 조회 중 서버 오류가 발생했습니다.");
            } catch (IOException ex) {
                System.err.println("Error: 응답 중 오류 발생");
                ex.printStackTrace();
            }
            return null;
        }

        // 5. 전체 댓글 수를 조회합니다.
        ReplyDTO replyCountDTO = new ReplyDTO();
        replyCountDTO.setBoardNum(bid);  // 조회할 게시글 번호 설정
        replyCountDTO.setCondition("ALLCOUNT_SELECTONE");  // 전체 댓글 수 조회를 위한 조건 설정
        int totalReplies = replyDAO.selectOne(replyCountDTO).getReplyCount();
        System.out.println("전체 댓글 수 조회 성공: " + totalReplies);

        // 6. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalReplies / replySize);
        System.out.println("총 페이지 수 계산: " + totalPages);

        // 7. 조회된 댓글 목록을 요청 객체에 설정하여 JSP에서 사용할 수 있도록 합니다.
        request.setAttribute("boardNum", bid); // 댓글 등록시 보내야되는 값
        request.setAttribute("replyList", replyList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        System.out.println("댓글 목록과 페이지 정보 설정 완료");

        // 8. 댓글 목록을 보여줄 JSP 페이지로 포워딩합니다.
        ActionForward forward = new ActionForward();
        forward.setRedirect(false);  // 요청 데이터를 유지하며 포워딩
        forward.setPath("reply.jsp");
        System.out.println("댓글 목록 페이지로 포워딩 설정 완료");

        return forward;
    }
}
