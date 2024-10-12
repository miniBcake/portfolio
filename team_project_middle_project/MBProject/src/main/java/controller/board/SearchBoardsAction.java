package controller.board;

import java.util.ArrayList;
import java.util.HashMap;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ErrorUtils;
import controller.common.PaginationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BoardDAO;
import model.dto.BoardDTO;
/**
 * SearchBoardsAction 클래스는 게시글 검색 기능을 처리하는 액션 클래스입니다.
 * 
 * 이 클래스는 클라이언트로부터 검색 조건(제목, 작성자, 내용, 작성일 필터 등)을 받아
 * 해당 조건에 맞는 게시글을 조회하고 결과를 페이지에 맞게 출력합니다.
 * 또한, JSP 페이지로 검색 결과를 포워딩합니다.
 */
public class SearchBoardsAction implements Action {
	 /**
     * 클라이언트의 요청을 처리하고, 검색 조건에 맞는 게시글 목록을 조회하여 해당 JSP 페이지로 포워딩합니다.
     * 
     * @param request  클라이언트의 HTTP 요청 객체. 
     *                 - "page": 조회할 페이지 번호.
     *                 - "content_filter": 검색 필터(제목, 작성자, 내용).
     *                 - "keyword": 검색 키워드.
     *                 - "writeDay_filter": 작성일 필터(최근 일주일, 15일, 30일 등).
     *                 - "categoryName": 게시판 카테고리명.
     * @param response 클라이언트의 HTTP 응답 객체. 에러 발생 시 상태 코드와 함께 에러 메시지를 전달합니다.
     * @return ActionForward JSP로의 포워딩 경로를 포함한 ActionForward 객체.
     */
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[INFO] SearchBoardsAction 실행 시작");

        // 1. 기본값 설정
        int currentPage = 1; 
        int pageSize = 6; 
        System.out.println("[INFO] 기본 페이지 설정: currentPage=" + currentPage + ", pageSize=" + pageSize);

        // 2. 요청으로부터 페이지 번호 받아오기
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
            System.out.println("[INFO] 요청으로부터 페이지 번호 수신: currentPage=" + currentPage);
        }

        // 3. 사용자 입력 값(검색 키워드, 검색어, 카테고리, 검색기간)을 요청 객체에서 가져오기
        String content_filter = request.getParameter("content_filter"); // 검색 필터 (제목, 작성자, 내용)
        String keyword = request.getParameter("keyword"); // 검색어
        String writeDay_filter = request.getParameter("writeDay_filter"); // 작성일 필터
        String categoryName = request.getParameter("categoryName"); // 게시판 카테고리

        // 4. 검색 키워드 및 기타 필수 입력 값 유효성 검사
        if (categoryName == null || categoryName.isEmpty()) {
            System.out.println("[ERROR] 필수 입력 값이 누락되었습니다. 카테고리 이름: " + categoryName);
            ErrorUtils.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "400: 카테고리 이름이 필요합니다.");
            return null;
        }

        System.out.println("[INFO] 검색 키워드 및 입력 값 수신: content_filter=" + content_filter + ", keyword=" + keyword + ", categoryName=" + categoryName + ", writeDay_filter=" + writeDay_filter);

        // 5. 검색을 수행하기 위해 DAO와 DTO 객체 생성
        BoardDAO boardDAO = new BoardDAO();
        BoardDTO boardDTO = new BoardDTO();  // 실제 검색에 사용할 DTO
        BoardDTO boardTotalCNT = new BoardDTO();  // 총 게시글 수를 확인하기 위한 DTO

        // 6. HashMap을 사용하여 검색 조건을 설정
        HashMap<String, String> filterList = new HashMap<>();

        // 7. 검색 조건 설정
        if (content_filter != null && keyword != null && !keyword.isEmpty()) {
            switch (content_filter) {
                case "title":
                    filterList.put("TITLE_SELECTALL", keyword);
                    System.out.println("[INFO] 제목으로 검색: " + keyword);
                    break;
                case "writer":
                    filterList.put("NICKNAMEV", keyword);
                    System.out.println("[INFO] 작성자로 검색: " + keyword);
                    break;
                case "content":
                    filterList.put("CONTENT_SELECTALL", keyword);
                    System.out.println("[INFO] 내용으로 검색: " + keyword);
                    break;
                default:
                    System.out.println("[ERROR] 잘못된 검색 키워드입니다: " + content_filter);
                    ErrorUtils.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "400: 잘못된 검색 키워드입니다.");
                    return null;
            }
        }

        // 8. 작성일 필터 처리
        if (writeDay_filter != null) {
            switch (writeDay_filter) {
                case "oneWeek":
                    filterList.put("writeDay_filter", "7");  // 7일 전
                    System.out.println("[INFO] 작성일 필터: 최근 7일");
                    break;
                case "twoWeek":
                    filterList.put("writeDay_filter", "15"); // 15일 전
                    System.out.println("[INFO] 작성일 필터: 최근 15일");
                    break;
                case "oneMonth":
                    filterList.put("writeDay_filter", "30"); // 30일 전
                    System.out.println("[INFO] 작성일 필터: 최근 30일");
                    break;
                case "alltime":
                    System.out.println("[INFO] 작성일 필터: 전체 기간");// 전체 기간 검색
                    break;
                default:
                    System.out.println("[ERROR] 잘못된 작성일 필터입니다: " + writeDay_filter);
                    ErrorUtils.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "400: 잘못된 작성일 필터입니다.");
                    return null;
            }
        }

        // null check 방어코드: 작성일 필터 처리
        String writeDayFilterValue = filterList.get("writeDay_filter");
        if (writeDayFilterValue == null) {
            System.out.println("[INFO] 전체 기간 검색이 선택되었습니다.");
        } else {
            System.out.println("[INFO] 선택된 작성일 필터: " + writeDayFilterValue + "일 전");
        }

        System.out.println("[INFO] 필터 리스트 설정 완료: " + filterList.toString());

        //9-1. 게시글을 검색조건에 맞게 검색하기 위한 DTO
        boardDTO.setCondition("FILTER_BOARD");
        boardDTO.setFilterList(filterList);
        boardDTO.setBoardCateName(categoryName);
        
        
        //9-2. CNT를 구하기위한 DTO
        boardTotalCNT.setCondition("CNT_BOARD");
        boardTotalCNT.setFilterList(filterList);
        boardTotalCNT.setBoardCateName(categoryName);

        // 10. 게시글 수 조회
        try {
            int totalRecords = boardDAO.selectOne(boardTotalCNT).getCnt();
            System.out.println("[INFO] 검색에 따른 게시글 수 조회 완료: totalRecords=" + totalRecords);

            // 11. 페이지네이션 설정
            PaginationUtils.setPagination(currentPage, pageSize, totalRecords, boardDTO);

            // 12. 전체 게시글 조회
            ArrayList<BoardDTO> searchResults = boardDAO.selectAll(boardDTO);
            System.out.println("[INFO] 게시글 목록 조회 성공, 게시글 수: " + searchResults.size());
            System.out.println("[INFO] 게시글 목록 조회 성공, 게시글 내용: " + searchResults.toString());

            // 13. 총 페이지 수 계산
            int totalPages = PaginationUtils.calTotalPages(totalRecords, pageSize);
            System.out.println("[INFO] 총 페이지 수 계산 완료: totalPages=" + totalPages);

            // 14. JSP로 데이터 전달
            request.setAttribute("boardList", searchResults);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("boardCateName", categoryName);
            System.out.println("[INFO] JSP에 전달할 데이터 설정 완료");
            
            // 검색 조건을 유지하기 위한 필터 값 전달
            request.setAttribute("filter", content_filter); // 검색 필터
            request.setAttribute("keyword", keyword); // 검색어
            request.setAttribute("writeDayFilter", writeDay_filter); // 작성일 필터

            // 15. 페이지 포워딩 설정
            ActionForward forward = new ActionForward();
            forward.setRedirect(false);
            System.out.println("[INFO] 페이지 포워딩 설정 완료");

            // 16. 카테고리 이름에 따라 다른 JSP 페이지로 포워딩
            if (categoryName.equals("request")) {
                forward.setPath("noticeBoard.jsp");
                System.out.println("[INFO] 문의 카테고리: noticeBoard.jsp로 포워딩 설정");
            } else if (categoryName.equals("normal")) {
                forward.setPath("boardList.jsp");
                System.out.println("[INFO] 일반 카테고리: boardList.jsp로 포워딩 설정");
            }

            System.out.println("[INFO] SearchBoardsAction 실행 종료");
            return forward;
        } catch (Exception e) {
        	System.out.println("[ERROR] 게시글 목록 조회 중 오류 발생: " + e.getMessage());
            ErrorUtils.handleException(response, e, "게시글 목록 조회 중 서버 오류");
            return null;
        }
    }
}
