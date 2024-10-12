package controller.page;

import java.util.ArrayList;
import java.util.HashMap;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.CheckLoginUtils;
import controller.common.ErrorUtils;
import controller.common.PaginationUtils;
import controller.common.ProfilePicUpload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BoardCateDAO;
import model.dao.BoardDAO;
import model.dto.BoardCateDTO;
import model.dto.BoardDTO;
/**
 * MyBoardListPageAction 클래스는 사용자가 작성한 게시글 목록을 조회하고 
 * 해당 데이터를 JSP로 전달하는 액션 클래스입니다.
 */
public class MyBoardListPageAction implements Action {
    /**
     * 사용자가 작성한 게시글 목록을 조회하고, 이를 JSP로 포워딩합니다.
     * @param request  클라이언트의 HTTP 요청 객체.
     *                 - "page": 현재 페이지 번호를 나타냅니다.
     * @param response 클라이언트의 HTTP 응답 객체.
     * @return ActionForward JSP로의 포워딩 경로를 포함한 ActionForward 객체.
     */
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[INFO] MyBoardListPageAction 실행 시작");
        
        Integer memberPK;
        String nickName = null;
        try {
            // 1. 세션에서 사용자 정보 가져오기
            HttpSession session = request.getSession();

            // 로그인 여부를 확인하여 로그인되지 않았으면 null을 반환
            if (!CheckLoginUtils.checkLogin(session, response, request)) {
                System.out.println("[ERROR] 사용자 로그인 필요");
                return null; 
            }

            // 세션에서 사용자 ID (memberPK) 가져오기
            memberPK = (Integer) session.getAttribute("memberPK");
            System.out.println("[INFO] 사용자 인증 성공, memberPK: " + memberPK);
            
            // 세션에서 닉네임 값 받아옴
            nickName = (String) session.getAttribute("memberNickName");
            System.out.println("[INFO] 세션에서 가져온 닉네임: " + nickName);

        } catch (Exception e) {
            System.err.println("[ERROR] 사용자 정보 처리 중 오류 발생: " + e.getMessage());
            ErrorUtils.handleException(response, e, "사용자 정보를 처리하는 중 오류가 발생했습니다.");
            return null;
        }

        // 페이지 기본 설정
        int currentPage = 1; // 기본 현재 페이지
        int pageSize = 6; // 페이지당 게시글 수
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
            System.out.println("[INFO] 현재 페이지: " + currentPage);
        }
        
  
        // 2.내가 쓴 게시글 조회하기 위한 BoardDTO 초기설정
        BoardDAO boardDAO = new BoardDAO();
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setCondition("MY_BOARD");
        boardDTO.setMemberNum(memberPK);


        // 3. 총 개수를 조회하기 위한 필터 설정
        HashMap<String, String> filterList = new HashMap<>();
        filterList.put("NICKNAMEV", nickName);
        
        // 카테고리 조회에서 모든 카테고리 게시판에 쓴글 갯수 조회
        int totalRecords = 0; 

        try {
            // 4. BoardCateDTO 목록 조회
            BoardCateDAO boardCateDAO = new BoardCateDAO();
            BoardCateDTO boardCateDTO = new BoardCateDTO();
            ArrayList<BoardCateDTO> boardCateList = boardCateDAO.selectAll(boardCateDTO);

            // 5. 각 카테고리별로 게시글 수를 조회
            for (BoardCateDTO cateDTO : boardCateList) {
                String categoryName = cateDTO.getBoardCateName();
                System.out.println("[INFO] 카테고리 이름: " + categoryName);

                // 게시글 수 조회를 위한 DTO 설정
                BoardDTO boardTotalCNT = new BoardDTO();
                boardTotalCNT.setCondition("CNT_BOARD");
                boardTotalCNT.setFilterList(filterList);
                boardTotalCNT.setBoardCateName(categoryName);

                // 각 카테고리별 게시글 수 조회
                int categoryRecords = boardDAO.selectOne(boardTotalCNT).getCnt();
                System.out.println("[INFO] '" + categoryName + "에 내가 쓴 게시글 수: " + categoryRecords);

                // 전체 게시글 수에 추가
                totalRecords += categoryRecords;
            }

            System.out.println("[INFO] 전체 카테고리에서 내가 쓴 게시글 수 총합: " + totalRecords);

        } catch (Exception e) {
        	 System.err.println("[ERROR] 게시글 수 조회 중 오류 발생: " + e.getMessage());
             ErrorUtils.handleException(response, e, "게시글 수 조회 중 오류 발생");
             return null;
         }

        // 6. totalRecords 로그
        System.out.println("[INFO] 최종 게시글 수 처리: " + totalRecords);

        // 7. 페이지네이션 함수 실행
        PaginationUtils.setPagination(currentPage, pageSize, totalRecords, boardDTO);

        // 8. 게시글 목록 조회
        ArrayList<BoardDTO> datas;
        try {
            datas = boardDAO.selectAll(boardDTO);
            System.out.println("[INFO] 게시글 목록 조회 완료: " + datas.size());
        } catch (Exception e) {
            System.err.println("[ERROR] 게시글 목록 조회 중 오류 발생: " + e.getMessage());
            ErrorUtils.handleException(response, e, "게시글 목록 조회 중 오류 발생");
            return null;
        }

        // 9. 총 페이지 수 계산
        int totalPages = PaginationUtils.calTotalPages(totalRecords, pageSize);

        // 10. View에 전달할 데이터 설정
        try {
            String profilePic = ProfilePicUpload.loginProfilePic(request, response);
            request.setAttribute("memberProfileWay", profilePic);
        } catch (Exception e) {
            System.err.println("[ERROR] 프로필 사진 처리 중 오류 발생: " + e.getMessage());
            ErrorUtils.handleException(response, e, "프로필 사진 처리 중 오류 발생");
            return null;
        }
        request.setAttribute("myBoardList", datas);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        System.out.println("[INFO] View에 데이터 전달 완료");

        // 11. 페이지 이동 설정
        ActionForward forward = new ActionForward();
        forward.setRedirect(false); // forward 방식
        forward.setPath("myBoardList.jsp");

        System.out.println("[INFO] 페이지 이동 설정 완료: " + forward.getPath());
        return forward;
    }
}
