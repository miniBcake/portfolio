package controller.board;

import java.io.IOException;
import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BoardDAO;
import model.dao.ImageDAO;
import model.dao.LikeDAO;
import model.dao.ReplyDAO;
import model.dto.BoardDTO;
import model.dto.ImageDTO;
import model.dto.LikeDTO;
import model.dto.ReplyDTO;


// 특정 게시글을 조회하여 board.jsp로 포워딩
public class ViewBoardAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[INFO] ViewBoardAction 시작");

        // 1. 파라미터 가져오기 및 유효성 검사
        String bidParam = request.getParameter("bid"); // 클라이언트로부터 게시글 번호 파라미터를 가져옴
        System.out.println("[INFO] 받은 파라미터 - bid: " + bidParam);

        // 2. 게시글 번호 파라미터가 존재하는지 확인
        if (bidParam == null || bidParam.isEmpty()) {
            System.err.println("[ERROR] 게시글 번호 파라미터가 없습니다.");
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 게시글 번호가 필요.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        int bid;
        try {
            // 3. 게시글 번호를 정수로 파싱
            bid = Integer.parseInt(bidParam);
            System.out.println("[INFO] 게시글 번호 파싱 성공: " + bid);
        } catch (NumberFormatException e) {
            System.err.println("[ERROR] 게시글 번호 파싱 실패: " + bidParam);
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 게시글 번호는 Int type 이여야 합니다.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
        
        // 4. 전체 댓글 수 조회
        ReplyDAO replyDAO = new ReplyDAO();
        ReplyDTO replyCountDTO = new ReplyDTO();
        replyCountDTO.setBoardNum(bid);  // 조회할 게시글 번호 설정
        replyCountDTO.setCondition("ALLCOUNT_SELECTONE");  // 전체 댓글 수 조회를 위한 조건 설정
        int totalReplies = replyDAO.selectOne(replyCountDTO).getReplyCount();
        System.out.println("[INFO] 총 댓글 수: " + totalReplies);
        // 댓글 수 저장
        request.setAttribute("totalReplies", totalReplies);

// 로그인 없이 test 하기 위해서는 여기서부터 -------------------------------------

        // 5. 유저 좋아요 유무 확인
        HttpSession session = request.getSession();
        Integer writerPK = (Integer) session.getAttribute("memberPK");
        if (writerPK == null) {
            System.err.println("[ERROR] 인증되지 않은 사용자");
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "401: 인증되지 않은 사용자");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        System.out.println("[INFO] 사용자 ID 확인 성공: " + writerPK);

        LikeDAO likeDAO = new LikeDAO();
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setBoardNum(bid);
        likeDTO.setMemberNum(writerPK);
        likeDTO.setCondition("selectOne");

        // 6. 좋아요 상태 확인
        boolean userLiked = false; // 초기화
        LikeDTO existingFavorite = likeDAO.selectOne(likeDTO);
        if (existingFavorite != null) {
            userLiked = true;
            System.out.println("[INFO] 사용자 좋아요 상태: 좋아요 누름");
        } else {
            System.out.println("[INFO] 사용자 좋아요 상태: 좋아요 안 누름");
        }
        // 좋아요 상태 저장
        request.setAttribute("userLiked", userLiked);
        
// ------------------------------------- 여기까지 블록 처리를 하고 test 하십시요
        
        // 7. 게시글 조회를 위한 DAO와 DTO 객체 생성
        BoardDAO boardDAO = new BoardDAO(); // BoardDAO 객체 생성
        BoardDTO boardDTO = new BoardDTO(); // BoardDTO 객체 생성
        boardDTO.setBoardNum(bid); // 게시글 번호 설정
        boardDTO.setCondition("ONE"); // 조회 조건 설정
        ImageDAO imageDAO = new ImageDAO(); // ImageDAO 객체 생성
        ImageDTO imageDTO = new ImageDTO(); // ImageDTO 객체 생성
        imageDTO.setBoardNum(bid); // 사진 조회 요청

        try {
            // 8. DAO를 사용하여 게시글 조회
            boardDTO = boardDAO.selectOne(boardDTO);

            // 9. 조회된 게시글이 존재하는지 확인
            if (boardDTO != null) {
                System.out.println("[INFO] 게시글 조회 성공: " + boardDTO.toString());
                // 10. 이미지 정보 조회
                ArrayList<ImageDTO> images = imageDAO.selectAll(imageDTO);

                // 11. 조회된 게시글을 request에 설정하여 JSP에서 사용하도록 함
                request.setAttribute("board", boardDTO);

                // images가 null이 아닐 경우에만 request에 설정
                if (images != null && !images.isEmpty()) {
                    request.setAttribute("images", images);
                    System.out.println("[INFO] 이미지 정보 조회 성공, 이미지 수: " + images.size());
                } else {
                  	System.out.println("[INFO] 이미지 정보가 없습니다.");
                }

                ActionForward forward = new ActionForward(); // ActionForward 인스턴스 생성
                forward.setRedirect(false); // 포워딩 방식으로 이동 
                forward.setPath("board.jsp"); // board.jsp로 페이지 설정
                System.out.println("[INFO] ViewBoardAction 종료 - 게시글 페이지로 포워딩");
                return forward;
            } else {
                System.err.println("[ERROR] 게시글을 찾을 수 없습니다.");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "404: 게시글을 찾을 수 없습니다.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("[ERROR] 게시글 조회 중 오류 발생");
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 게시글 조회 중 서버 오류가 발생.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
