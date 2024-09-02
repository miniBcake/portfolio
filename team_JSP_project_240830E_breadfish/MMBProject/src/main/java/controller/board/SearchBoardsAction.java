package controller.board;

import java.io.IOException;
import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BoardDAO;
import model.dto.BoardDTO;

// 게시글 검색을 수행하고 결과를 JSP로 포워딩하는 액션 클래스
public class SearchBoardsAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[INFO] SearchBoardsAction 실행 시작");

		// 1. 기본값 설정
		int currentPage = 1; // 현재 페이지 번호
		int pageSize = 6; // 페이지당 게시글 수
		System.out.println("[INFO] 기본 페이지 설정: currentPage=" + currentPage + ", pageSize=" + pageSize);

		// 2. 요청으로부터 페이지 번호 받아오기
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
			System.out.println("[INFO] 요청으로부터 페이지 번호 수신: currentPage=" + currentPage);
		}

		// 3. 사용자 입력 값(검색 키워드, 검색어, 카테고리)을 요청 객체에서 가져오기
		String searchKeyword = request.getParameter("searchKeyword"); // 제목/작성자/내용
		String searchContent = request.getParameter("searchContent"); // 사용자 검색어
		String categoryName = request.getParameter("categoryName"); // 카테고리
		
		// 4. 검색 키워드 및 기타 필수 입력 값 유효성 검사
		if (searchKeyword == null || searchKeyword.isEmpty() || 
			searchContent == null || searchContent.isEmpty() || 
			categoryName == null || categoryName.isEmpty()) {
			System.out.println("[ERROR] 필수 입력 값이 누락되었습니다.");
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 검색 키워드가 필요합니다.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		System.out.println("[INFO] 검색 키워드 및 입력 값 수신: searchKeyword=" + searchKeyword + ", searchContent=" + searchContent + ", categoryName=" + categoryName);

		// 5. 검색을 수행하기 위해 DAO와 DTO 객체 생성
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO boardDTO = new BoardDTO();
		BoardDTO boardTotalCNT = new BoardDTO();

		// 6. 검색 키워드에 따라 검색 조건 설정
		if (searchKeyword.equals("searchTitle")) {
			boardDTO.setCondition("TITLE_SELECTALL");  // 제목으로 검색
			boardTotalCNT.setCondition("CNTTITLE"); // 제목으로 검색한 글 수
		} else if (searchKeyword.equals("searchWriter")) {
			boardDTO.setCondition("NICKNAMEV");  // 작성자로 검색
			boardTotalCNT.setCondition("CNTWRITER"); // 작성자로 검색한 글 수
		} else if (searchKeyword.equals("searchContents")) {
			boardDTO.setCondition("CONTENT_SELECTALL");  // 내용으로 검색
			boardTotalCNT.setCondition("CNTCONTENTS"); // 내용으로 검색한 글 수
		} else {
			System.out.println("[ERROR] 잘못된 검색 키워드입니다: " + searchKeyword);
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 잘못된 검색 키워드입니다.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		System.out.println("[INFO] 게시글 조회 컨디션: " + boardDTO.getCondition() + ", 게시글 수 컨디션: " + boardTotalCNT.getCondition());

		// 7. 페이지네이션 정보 설정
		boardDTO.setCategoryName(categoryName);
		boardDTO.setKeyword(searchContent); // 검색어 설정
		boardDTO.setStartNum((currentPage - 1) * pageSize + 1); // 첫번째 게시글의 번호
		boardDTO.setEndNum(currentPage * pageSize); // 마지막 게시글의 번호	
		System.out.println("[INFO] 페이지네이션 설정 완료: startNum=" + boardDTO.getStartNum() + ", endNum=" + boardDTO.getEndNum() + ", 검색어=" + boardDTO.getKeyword());

		// 8. DAO를 사용하여 조건에 맞는 게시글 목록 조회
		ArrayList<BoardDTO> searchResults = null;
		try {
			searchResults = boardDAO.selectAll(boardDTO);
			System.out.println("[INFO] 게시글 목록 조회 성공, 게시글 수: " + searchResults.size());
		} catch (Exception e) {
			System.out.println("[ERROR] 게시글 목록 조회 중 오류 발생");
			e.printStackTrace();
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 게시글 검색 중 서버 오류.");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return null;
		}

		// 9. 전체 게시글 수 조회
		boardTotalCNT.setCategoryName(categoryName);
		boardTotalCNT.setKeyword(searchContent); // 검색어 설정
		int totalRecords = boardDAO.selectOne(boardTotalCNT).getBoardCnt();
		System.out.println("[INFO] 전체 게시글 수 조회 완료: totalRecords=" + totalRecords);

		// 10. view 에게 보낼 총 페이지 수 계산
		int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
		System.out.println("[INFO] 총 페이지 수 계산 완료: totalPages=" + totalPages);

		// 11. JSP로 데이터 전달
		request.setAttribute("boardList", searchResults); // 게시글 목록
		request.setAttribute("currentPage", currentPage); // 현재 페이지 번호
		request.setAttribute("totalPages", totalPages); // 총 페이지 수
		request.setAttribute("categoryName", categoryName); // 카테고리명
		System.out.println("[INFO] JSP에 전달할 데이터 설정 완료");

		// 12. 페이지 포워딩 설정
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);  // 요청 데이터를 유지하며 포워딩
		System.out.println("[INFO] 페이지 포워딩 설정 완료");

		// 13. 카테고리 이름에 따라 다른 JSP 페이지로 포워딩
		if (categoryName.equals("문의")) {
			forward.setPath("noticeBoard.jsp"); // 문의게시판으로 포워딩
			System.out.println("[INFO] 문의 카테고리: noticeBoard.jsp로 포워딩 설정");
		} else if (categoryName.equals("일반")) {
			forward.setPath("boardlist.jsp"); // 일반게시판으로 포워딩
			System.out.println("[INFO] 일반 카테고리: boardlist.jsp로 포워딩 설정");
		}

		System.out.println("[INFO] SearchBoardsAction 실행 종료");
		return forward;
	}
}
