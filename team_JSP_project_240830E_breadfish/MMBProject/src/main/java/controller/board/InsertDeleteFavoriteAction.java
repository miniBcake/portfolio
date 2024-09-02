package controller.board;

import java.io.IOException;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.LikeDAO;
import model.dto.LikeDTO;

public class InsertDeleteFavoriteAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        // 1. 게시글 번호와 사용자 정보를 가져옵니다.
        String bidParam = request.getParameter("bid");
        HttpSession session = request.getSession();
        Integer memberPK = (Integer) session.getAttribute("memberPK");

        if (bidParam == null || bidParam.isEmpty() || memberPK == null) {
            // 잘못된 요청 처리
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 게시글 번호 또는 사용자 정보가 필요.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        int bid;
        try {
            bid = Integer.parseInt(bidParam);
        } catch (NumberFormatException e) {
            // 잘못된 숫자 형식 처리
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 게시글 번호는 Int 타입이여야 합니다.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        // DAO와 DTO 준비
        LikeDAO likeDAO = new LikeDAO();
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setBoardNum(bid);
        likeDTO.setMemberNum(memberPK);

        // 2. 좋아요 상태를 확인합니다.
		LikeDTO existingFavorite = likeDAO.selectOne(likeDTO);

		// 3. 좋아요 추가 또는 삭제를 수행합니다.
		if (existingFavorite != null) {
		    // 좋아요가 이미 눌려있는 경우 삭제
		    boolean deleteResult = likeDAO.delete(likeDTO);
		    if (!deleteResult) {
		        // 좋아요 삭제 실패 처리
		        try {
		            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 좋아요 삭제에 실패했습니다.");
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        return null;
		    }
		} else {
		    // 좋아요가 눌려있지 않은 경우 추가
		    boolean insertResult = likeDAO.insert(likeDTO);
		    if (!insertResult) {
		        // 좋아요 추가 실패 처리
		        try {
		            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 좋아요 추가에 실패했습니다.");
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        return null;
		    }
		}

        // 4. 작업이 성공한 경우, 게시글 페이지로 리다이렉트
        ActionForward forward = new ActionForward();
        forward.setRedirect(true);
        forward.setPath("board.jsp?boardNum=" + bid);
        return forward;
    }
}