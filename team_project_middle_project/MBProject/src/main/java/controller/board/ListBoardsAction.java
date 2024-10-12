package controller.board;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ErrorUtils;
import controller.common.PaginationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BoardDAO;
import model.dto.BoardDTO;
/**
 * ListBoardsAction 클래스는 게시글 목록을 조회하여 해당 JSP 페이지로 포워딩하는 액션 클래스입니다.
 * 
 * 이 클래스는 요청된 페이지 번호와 카테고리명을 기반으로 게시글 목록을 조회한 후,
 * 페이지네이션 정보를 설정하고, JSP로 전달합니다.
 */

// 게시글 목록을 조회하여 boardList.jsp로 포워딩
public class ListBoardsAction implements Action {
	
	/**
     * 클라이언트의 요청을 처리하고, 게시글 목록을 조회하여 해당 JSP 페이지로 포워딩합니다.
     * 
     * @param request  클라이언트의 HTTP 요청 객체. 
     *                 - "page": 조회할 페이지 번호.
     *                 - "categoryName": 게시글 카테고리명.
     * @param response 클라이언트의 HTTP 응답 객체. 에러 발생 시 상태 코드와 함께 에러 메시지를 전달합니다.
     * @return ActionForward JSP로의 포워딩 경로를 포함한 ActionForward 객체.
     */
	
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[INFO] ListBoardsAction 실행 시작");

        // 1. 기본값 설정
        int currentPage = 1; // 현재 페이지 번호
        int pageSize = 6; // 페이지당 게시글 수
        System.out.println("[INFO] 기본 페이지 설정: currentPage=" + currentPage + ", pageSize=" + pageSize);

        // 2. 요청으로부터 페이지 번호와 페이지 크기 받아오기
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
            System.out.println("[INFO] 요청으로부터 페이지 번호 수신: currentPage=" + currentPage);
        }

        // 3. 카테고리명을 요청으로부터 받아오기
        String boardCateName = request.getParameter("categoryName");
        if (boardCateName == null || boardCateName.isEmpty()) {
            System.out.println("[ERROR] 유효하지 않은 카테고리명 수신: " + boardCateName);
            ErrorUtils.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "400: 유효하지 않은 카테고리명입니다.");
            return null; 
        }
        System.out.println("[INFO] 카테고리명 수신: " + boardCateName);

        // 4. DAO 객체 생성
        BoardDAO boardDAO = new BoardDAO();
        System.out.println("[INFO] BoardDAO 객체 생성 완료");

        // 5. 페이지네이션 하기위한 객체 생성
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardCateName(boardCateName);
        boardDTO.setCondition("FILTER_BOARD");
        System.out.println("[INFO] 페이지네이션 설정 시작");

        // 6. 총 게시글 수 조회 및 페이지네이션 설정
        BoardDTO boardTotalCNT = new BoardDTO();
        boardTotalCNT.setCondition("CNT_BOARD");
        boardTotalCNT.setBoardCateName(boardCateName);
        int totalRecords = boardDAO.selectOne(boardTotalCNT).getCnt();
        System.out.println("[INFO] 전체 게시글 수 조회 완료: totalRecords=" + totalRecords);

        // 페이지네이션 정보 설정
        PaginationUtils.setPagination(currentPage, pageSize, totalRecords, boardDTO);

        // 7. selectAll 요청
        ArrayList<BoardDTO> boardList = null;
        try {
            boardList = boardDAO.selectAll(boardDTO);
            System.out.println("[INFO] 게시글 목록 조회 성공, 게시글 수: " + boardList.size());
        } catch (Exception e) {
            System.out.println("[ERROR] 게시글 목록 조회 중 오류 발생");
            ErrorUtils.handleException(response, e, "게시글 목록 조회 중 서버 오류"); 
            return null;
        }

        // 8. view 에게 보낼 총 페이지 수
        int totalPages = PaginationUtils.calTotalPages(totalRecords, pageSize);
        System.out.println("[INFO] 총 페이지 수 계산 완료: totalPages=" + totalPages);

        // 9. JSP로 데이터 전달
        request.setAttribute("boardList", boardList); // 게시글 내용
        request.setAttribute("currentPage", currentPage); // 현재 페이지 번호
        request.setAttribute("totalPages", totalPages); // 게시글 페이지네이션 갯수
        request.setAttribute("boardCateName", boardCateName); // 게시글 카테고리 이름
        System.out.println("[INFO] JSP에 전달할 데이터 설정 완료");

        ActionForward forward = new ActionForward();
        forward.setRedirect(false);
        System.out.println("[INFO] 페이지 포워딩 설정 완료");
        
        if (boardCateName.equals("request")) {
            forward.setPath("noticeBoard.jsp"); // 문의게시판으로 포워딩
            System.out.println("[INFO] 문의 카테고리: noticeBoard.jsp로 포워딩 설정");
        } else if (boardCateName.equals("normal")) {
            forward.setPath("boardList.jsp"); // 공개게시판으로 포워딩
            System.out.println("[INFO] 일반 카테고리: boardList.jsp로 포워딩 설정");
        }

        System.out.println("[INFO] ListBoardsAction 실행 종료");
        return forward;
    }

}