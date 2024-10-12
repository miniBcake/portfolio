package controller.board;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ErrorUtils;
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

/**
 * ViewBoardAction 클래스는 특정 게시글을 조회하여 게시글 정보 및 관련 데이터를 JSP로 포워딩하는 역할을 합니다.
 * 
 * 이 클래스는 게시글의 번호를 요청으로부터 받아 게시글 정보를 조회한 후, 
 * 게시글에 대한 댓글 수, 이미지, 좋아요 여부 등의 정보를 조회하여 JSP로 전달합니다.
 * 게시글이 존재하지 않을 경우, 에러 메시지를 클라이언트로 반환합니다.
 */

// 특정 게시글을 조회하여 board.jsp로 포워딩
public class ViewBoardAction implements Action {
	
	/**
     * 클라이언트의 요청을 처리하고 특정 게시글의 정보를 조회하여 JSP 페이지로 포워딩합니다.
     * 
     * @param request  클라이언트의 HTTP 요청 객체.
     *                 - "boardNum": 조회할 게시글 번호.
     * @param response 클라이언트의 HTTP 응답 객체.
     *                 에러 발생 시 상태 코드와 함께 에러 메시지를 전달합니다.
     * @return ActionForward JSP로의 포워딩 경로를 포함한 ActionForward 객체.
     */
	
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[INFO] ViewBoardAction 시작");

        // 1. 게시글 번호 파라미터 가져오기 및 유효성 검사
        Integer boardNum = ErrorUtils.validateAndParseIntParam(request, response, "boardNum");
        if (boardNum == null) {
            return null; // 유효성 검증 실패 시 처리 종료
        }

        
        // 2. 전체 댓글 수 조회
        ReplyDAO replyDAO = new ReplyDAO();
        ReplyDTO replyCountDTO = new ReplyDTO();
        replyCountDTO.setBoardNum(boardNum);  // 조회할 게시글 번호 설정
        replyCountDTO.setCondition("CNT_BOARD_RP");  // 전체 댓글 수 조회를 위한 조건 설정
        int totalReplies = replyDAO.selectOne(replyCountDTO).getCnt();
        System.out.println("[INFO] 총 댓글 수: " + totalReplies);
        // 댓글 수 저장
        request.setAttribute("totalReplies", totalReplies);

        // 3. 로그인 상태 확인 및 좋아요 유무 확인
        HttpSession session = request.getSession();
        Integer writerPK = (Integer) session.getAttribute("memberPK");
        boolean userLiked = false; // 초기화

        if (writerPK != null) {
            System.out.println("[INFO] 사용자 ID 확인 성공: " + writerPK);

            LikeDAO likeDAO = new LikeDAO();
            LikeDTO likeDTO = new LikeDTO();
            likeDTO.setBoardNum(boardNum);
            likeDTO.setMemberNum(writerPK);

            // 4. 좋아요 상태 확인            
            LikeDTO existingFavorite = likeDAO.selectOne(likeDTO);
            if (existingFavorite != null) {
                userLiked = true;
                System.out.println("[INFO] 사용자 좋아요 상태: 좋아요 누름");
            } else {
                System.out.println("[INFO] 사용자 좋아요 상태: 좋아요 안 누름");
            }
        } else {
            System.out.println("[INFO] 사용자 인증 없이 접근");
        }
        // 좋아요 상태 저장
        request.setAttribute("userLiked", userLiked);
        
        // 5. 게시글 조회를 위한 DAO와 DTO 객체 생성
        BoardDAO boardDAO = new BoardDAO(); // BoardDAO 객체 생성
        BoardDTO boardDTO = new BoardDTO(); // BoardDTO 객체 생성
        boardDTO.setBoardNum(boardNum); // 게시글 번호 설정
        boardDTO.setCondition("ONE_BOARD"); // 조회 조건 설정
  
        try {
            // 6. DAO를 사용하여 게시글 조회
            boardDTO = boardDAO.selectOne(boardDTO);

            // 7. 조회된 게시글이 존재하는지 확인
            if (boardDTO != null) {
                System.out.println("[INFO] 게시글 조회 성공: " + boardDTO.toString());
                // 8. 조회된 게시글을 request에 설정하여 JSP에서 사용하도록 함
                request.setAttribute("board", boardDTO);
                
                // 9. 이미지 정보 조회
                ImageDAO imageDAO = new ImageDAO(); // ImageDAO 객체 생성
                ImageDTO imageDTO = new ImageDTO(); // ImageDTO 객체 생성
                imageDTO.setBoardNum(boardNum); // 사진 조회 요청
                ArrayList<ImageDTO> images = imageDAO.selectAll(imageDTO);

                // images가 null이 아닐 경우에만 request에 설정
                if (images != null && !images.isEmpty()) {
                    request.setAttribute("images", images);
                    System.out.println("[INFO] 이미지 정보 조회 성공, 이미지 수: " + images.size());
                    System.out.println("[INFO] 이미지 정보 조회 성공, 이미지 파일: " + images.toString());
                } else {
                  	System.out.println("[INFO] 이미지 정보가 없습니다.");
                }

                ActionForward forward = new ActionForward(); // ActionForward 인스턴스 생성
                forward.setRedirect(false); // 포워딩 방식으로 이동 
                forward.setPath("board.jsp"); // board.jsp로 페이지 설정
                System.out.println("[INFO] ViewBoardAction 종료 - 게시글 페이지로 포워딩");
                return forward;
            } else {
                System.out.println("[ERROR] 게시글을 찾을 수 없습니다.");
                ErrorUtils.sendError(response, HttpServletResponse.SC_NOT_FOUND, "404: 게시글을 찾을 수 없습니다.");
                return null;
            }
        } catch (Exception e) {
        	 System.out.println("[ERROR] 게시글 조회 중 오류 발생");
             ErrorUtils.handleException(response, e, "게시글 조회 중 오류 발생");
             return null;
         }
     }
 }
