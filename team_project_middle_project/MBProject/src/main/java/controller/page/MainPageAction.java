package controller.page;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ConfigUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BoardDAO;
import model.dto.BoardDTO;

public class MainPageAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[INFO] MainPageAction.java - 시작");

        // 1. config.properties에서 카테고리 값을 불러옴
        String cateNormal = ConfigUtils.getProperty("category.normal");
        System.out.println("[INFO] config.properties에서 카테고리 값 불러오기: " + cateNormal);

        // 2. DAO 호출하여 인기 게시물 TOP 3을 가져오기
        BoardDAO boardDAO = new BoardDAO();
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setCondition("HOT_BOARD");
        boardDTO.setBoardCateName(cateNormal);

        System.out.println("[INFO] BoardDAO.selectAll 호출 - 인기 게시물 조회 시작");
        ArrayList<BoardDTO> hotBoard = boardDAO.selectAll(boardDTO); // DAO에서 인기 게시물 조회

        // 3. 인기 게시물 중 상위 3개만 추출
        ArrayList<BoardDTO> topThreeHotBoard = new ArrayList<>();
        if (hotBoard != null && !hotBoard.isEmpty()) {
            System.out.println("[INFO] 인기 게시물 조회 완료, 게시물 수: " + hotBoard.size());

            // 리스트의 사이즈가 3개보다 많을 경우 상위 3개만 추출
            int limit = Math.min(3, hotBoard.size()); // 최대 3개 또는 실제 리스트 크기까지
            for (int i = 0; i < limit; i++) {
                topThreeHotBoard.add(hotBoard.get(i)); // 상위 3개 게시물 추가
            }
            System.out.println("[INFO] 상위 3개 인기 게시물 추출 완료");
        } else {
            System.out.println("[WARN] 인기 게시물 조회 실패 또는 게시물이 없습니다.");
        }

        // 4. JSP로 전달할 데이터를 request에 설정
        request.setAttribute("hotBoardList", topThreeHotBoard); // 상위 3개 인기 게시물 리스트를 request에 전달
        System.out.println("[INFO] JSP로 전달할 인기 게시물 리스트 설정 완료");

        // 5. 이동 방법 설정 및 경로 지정
        ActionForward forward = new ActionForward();
        forward.setRedirect(false); // 데이터가 있으므로 forward 방식 사용
        forward.setPath("main.jsp"); // 이동할 페이지 설정
        System.out.println("[INFO] 페이지 포워딩 설정 완료: " + forward.getPath());

        // 6. 로그 및 종료 메시지 출력
        System.out.println("[INFO] MainPageAction.java - 종료");
        return forward;
    }
}
