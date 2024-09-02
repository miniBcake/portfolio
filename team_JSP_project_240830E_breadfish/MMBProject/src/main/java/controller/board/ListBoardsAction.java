package controller.board;

import java.io.IOException;
import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BoardDAO;
import model.dto.BoardDTO;

// 게시글 목록을 조회하여 boardList.jsp로 포워딩
public class ListBoardsAction implements Action {

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
        String categoryName = request.getParameter("categoryName");
        if (categoryName == null || categoryName.isEmpty()) {
            System.out.println("[ERROR] 유효하지 않은 카테고리명 수신: " + categoryName);
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 유효하지 않은 카테고리명입니다.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null; 
        }
        System.out.println("[INFO] 카테고리명 수신: " + categoryName);

        // 4. DAO 객체 생성
        BoardDAO boardDAO = new BoardDAO();
        System.out.println("[INFO] BoardDAO 객체 생성 완료");

        // 5. 페이지네이션 하기위한 객체 생성
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setCategoryName(categoryName); 
        boardDTO.setStartNum((currentPage - 1) * pageSize + 1); // 첫번째 게시글의 번호
        boardDTO.setEndNum(currentPage * pageSize); // 마지막 게시글의 번호
        boardDTO.setCondition("ALL");
        System.out.println("[INFO] 페이지네이션 설정 완료: startNum=" + boardDTO.getStartNum() + ", endNum=" + boardDTO.getEndNum() + ", categoryName=" + boardDTO.getCategoryName());

        // 6. 총 갯수를 조회하기 위한 객체 생성
        BoardDTO boardTotalCNT = new BoardDTO();
        boardTotalCNT.setCondition("CNT");
        boardTotalCNT.setCategoryName(categoryName);
        System.out.println("[INFO] 총 게시글 수 조회 설정 완료");

        // 7. selectAll 요청
        ArrayList<BoardDTO> boardList = null;
        try {
            boardList = boardDAO.selectAll(boardDTO);
            System.out.println("[INFO] 게시글 목록 조회 성공, 게시글 수: " + boardList.size());
        } catch (Exception e) {
            System.out.println("[ERROR] 게시글 목록 조회 중 오류 발생");
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 게시글 목록 조회 중 서버 오류");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        // 8. 전체 게시글 수 조회
        int totalRecords = boardDAO.selectOne(boardTotalCNT).getBoardCnt();
        System.out.println("[INFO] 전체 게시글 수 조회 완료: totalRecords=" + totalRecords);

        // 9. view 에게 보낼 총 페이지 수
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        System.out.println("[INFO] 총 페이지 수 계산 완료: totalPages=" + totalPages);

        // 10. JSP로 데이터 전달
        request.setAttribute("boardList", boardList); // 게시글 내용
        request.setAttribute("currentPage", currentPage); // 현재 페이지 번호
        request.setAttribute("totalPages", totalPages); // 게시글 페이지네이션 갯수
        System.out.println("[INFO] JSP에 전달할 데이터 설정 완료");

        ActionForward forward = new ActionForward();
        forward.setRedirect(false);
        System.out.println("[INFO] 페이지 포워딩 설정 완료");
        
        if (categoryName.equals("문의")) {
            forward.setPath("noticeBoard.jsp"); // 문의게시판으로 포워딩
            System.out.println("[INFO] 문의 카테고리: noticeBoard.jsp로 포워딩 설정");
        } else if (categoryName.equals("일반")) {
            forward.setPath("boardlist.jsp"); // 공개게시판으로 포워딩
            System.out.println("[INFO] 일반 카테고리: boardlist.jsp로 포워딩 설정");
        }

        System.out.println("[INFO] ListBoardsAction 실행 종료");
        return forward;
    }

}
