//package controller.page;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import controller.common.Action;
//import controller.common.ActionForward;
//import controller.common.CheckLoginUtils;
//import controller.common.PaginationUtils;
//import controller.common.ProfilePicUpload;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import model.dao.BoardCateDAO;
//import model.dao.BoardDAO;
//import model.dto.BoardCateDTO;
//import model.dto.BoardDTO;
//
//public class LikeBoardListPageAction implements Action{
//
//	@Override
//	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
//    System.out.println("[INFO] LikeBoardListPageAction 실행 시작");
//        
//        Integer memberPK;
//        String nickName = null;
//        try {
//            // 1. 세션에서 사용자 정보 가져오기
//            HttpSession session = request.getSession();
//
//            // 로그인 여부를 확인하여 로그인되지 않았으면 null을 반환
//            if (!CheckLoginUtils.checkLogin(session, response, request)) {
//                System.out.println("[ERROR] 사용자 로그인 필요");
//                return null; 
//            }
//
//            // 세션에서 사용자 ID (memberPK) 가져오기
//            memberPK = (Integer) session.getAttribute("memberPK");
//            System.out.println("[INFO] 사용자 인증 성공, memberPK: " + memberPK);
//            
//            // 세션에서 닉네임 값 받아옴
//            nickName = (String) session.getAttribute("memberNickName");
//            System.out.println("[INFO] 세션에서 가져온 닉네임: " + nickName);
//
//        } catch (Exception e) {
//            System.err.println("[ERROR] 사용자 정보 처리 중 오류 발생: " + e.getMessage());
//            e.printStackTrace();
//            try {
//                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 사용자 정보를 처리하는 중 오류가 발생했습니다.");
//            } catch (IOException ioException) {
//                System.err.println("[ERROR] 오류 응답 처리 중 문제 발생: " + ioException.getMessage());
//                ioException.printStackTrace();
//            }
//            return null;
//        }
//
//
//        // 페이지 기본 설정
//        int currentPage = 1; // 기본 현재 페이지
//        int pageSize = 6; // 페이지당 게시글 수
//        if (request.getParameter("page") != null) {
//            currentPage = Integer.parseInt(request.getParameter("page"));
//            System.out.println("[INFO] 현재 페이지: " + currentPage);
//        }
//        
//  
//        // 2.내가 좋아요한 게시글을 조회하기 위한 BoardDTO 초기설정
//        BoardDAO boardDAO = new BoardDAO();
//        BoardDTO boardDTO = new BoardDTO();
//        boardDTO.setCondition("MYFAVORITE_BOARD");
//        boardDTO.setMemberNum(memberPK);
//
//  
//        // 게시글 목록 조회
//        ArrayList<BoardDTO> datas = boardDAO.selectAll(boardDTO);
//        System.out.println("[INFO] 게시글 목록 조회 완료: " + datas.size());
//
//      
//        // View에 전달할 데이터 설정
//        String profilePic = ProfilePicUpload.loginProfilePic(request, response);
//        request.setAttribute("memberProfileWay", profilePic);
//        request.setAttribute("myLikeList", datas);
//        request.setAttribute("currentPage", currentPage);
//
//        System.out.println("[INFO] View에 데이터 전달 완료");
//
//        // 페이지 이동 설정
//        ActionForward forward = new ActionForward();
//        forward.setRedirect(false); // forward 방식
//        forward.setPath("myBoardList.jsp");
//
//        System.out.println("[INFO] 페이지 이동 설정 완료: " + forward.getPath());
//        return forward;
//    }
//}
