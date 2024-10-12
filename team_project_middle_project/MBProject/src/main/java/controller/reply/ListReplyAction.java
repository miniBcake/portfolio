package controller.reply;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ErrorUtils;
import controller.common.PaginationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BoardDAO;
import model.dao.ReplyDAO;
import model.dto.BoardDTO;
import model.dto.ReplyDTO;

/**
 * ListReplyAction 클래스는 특정 게시글에 달린 모든 댓글을 조회하는 액션 클래스입니다.
 * 게시글 번호를 받아 해당 게시글의 댓글을 조회하고, 
 * 조회된 댓글 리스트와 페이지네이션 정보를 포함하여 JSP로 포워딩합니다.
 */

// 특정 게시글의 모든 댓글을 조회하는 액션 클래스
public class ListReplyAction implements Action {
	
	 /**
     * 댓글 조회 및 페이지네이션 설정을 처리합니다.
     * 게시글 번호를 받아 해당 게시글의 댓글을 조회하고, 페이지네이션 정보를 설정하여 JSP로 포워딩합니다.
     * @param request  클라이언트의 HTTP 요청 객체.
     *                 - "boardNum": 댓글이 속한 게시글 번호.
     *                 - "page": 댓글 목록 페이지네이션을 조회할 페이지 번호.
     * @param response 클라이언트에게 보낼 HTTP 응답 객체. 에러 발생 시 상태 코드와 함께 에러 메시지를 전달합니다.
     * @return ActionForward 댓글 리스트를 표시할 JSP로의 포워딩 경로를 포함한 ActionForward 객체
     */
	
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        // 1. 게시글 번호(boardNum)를 파라미터로 가져오기 및 유효성 검사
        try {
        	// 1. 게시글 번호(boardNum)를 파라미터로 가져오기 및 유효성 검사
            Integer boardNum = ErrorUtils.validateAndParseIntParam(request, response, "boardNum");
            if (boardNum == null) {
                return null; // 오류 발생 시 종료
            }

            // 2. 기본값 설정
            int currentPage = 1; // 현재 페이지 번호
            int replySize = 6; // 페이지당 댓글 수

            // 요청으로부터 페이지 번호 받아오기
            if (request.getParameter("page") != null) {
                currentPage = Integer.parseInt(request.getParameter("page"));
                System.out.println("요청된 페이지 번호: " + currentPage);
            }

            // 3. 게시글 조회 DAO와 DTO 객체 생성
            BoardDAO boardDAO = new BoardDAO(); // BoardDAO 객체 생성
            BoardDTO boardDTO = new BoardDTO(); // BoardDTO 객체 생성
            boardDTO.setBoardNum(boardNum); // 게시글 번호 설정
            boardDTO.setCondition("ONE_BOARD"); // 조회 조건 설정

            // 3-1. DAO를 사용하여 게시글 조회
            boardDTO = boardDAO.selectOne(boardDTO);
            if (boardDTO == null) {
                System.err.println("Error: 게시글을 찾을 수 없습니다.");
                ErrorUtils.sendError(response, HttpServletResponse.SC_NOT_FOUND, "게시글을 찾을 수 없습니다.");
                return null;
            }
            request.setAttribute("board", boardDTO);
            System.out.println("[INFO] 게시글 조회 성공: " + boardDTO.toString());

            // 4. 댓글을 조회하기 위해 DAO와 DTO 객체를 생성
            ReplyDAO replyDAO = new ReplyDAO();
            ReplyDTO replyDTO = new ReplyDTO();
            replyDTO.setCondition("ALL_REPLIES");
            replyDTO.setBoardNum(boardNum);

            // 5. 총 댓글 수 조회
            ReplyDTO replyCountDTO = new ReplyDTO();
            replyCountDTO.setBoardNum(boardNum);
            replyCountDTO.setCondition("CNT_BOARD_RP");

            int totalReplies = replyDAO.selectOne(replyCountDTO).getCnt();
            System.out.println("전체 댓글 수 조회 성공: " + totalReplies);

            // 6. 페이지네이션 설정 (PaginationUtils 사용)
            PaginationUtils.setPagination(currentPage, replySize, totalReplies, replyDTO);

            // 7. 댓글 목록 조회
            // 댓글이 없을때에는 c:if empty replyList로 댓글이 없다고 클라이언트에서 표시함
            ArrayList<ReplyDTO> replyList = replyDAO.selectAll(replyDTO);
            System.out.println("댓글 조회 성공, 총 " + replyList.size() + "개 조회");
            request.setAttribute("replyList", replyList);

            // 8. 총 페이지 수 계산 (PaginationUtils 사용)
            int totalPages = PaginationUtils.calTotalPages(totalReplies, replySize);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            System.out.println("총 페이지 수 계산: " + totalPages);

            System.out.println("댓글 목록과 페이지 정보 설정 완료");

            // 9. 댓글 목록을 JSP 페이지로 포워딩
            ActionForward forward = new ActionForward();
            forward.setRedirect(false);
            forward.setPath("reply.jsp");
            System.out.println("댓글 목록 페이지로 포워딩 설정 완료");

            return forward;
        } catch (Exception e) {
            ErrorUtils.handleException(response, e, "서버 오류 발생");
            return null;
        }
    }
}