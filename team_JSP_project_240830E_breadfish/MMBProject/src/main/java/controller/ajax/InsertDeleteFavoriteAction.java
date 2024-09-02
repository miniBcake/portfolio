package controller.ajax;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.LikeDAO;
import model.dto.LikeDTO;

@WebServlet("/insertDeleteFavorite.do") // 서블릿 매핑 URL 설정
public class InsertDeleteFavoriteAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[INFO] InsertDeleteFavoriteServlet 실행 시작");

        try {
            // 1. 클라이언트로부터 전달된 게시글 번호와 사용자 정보 가져오기
            String bidParam = request.getParameter("bid");
            System.out.println("[INFO] bidParam값: " + bidParam);
            HttpSession session = request.getSession();
            Integer memberPK = (Integer) session.getAttribute("memberPK");
            System.out.println("[INFO] memberPK값: " + memberPK);

            // 2. 유효성 검사
            if (bidParam == null || bidParam.isEmpty() || memberPK == null) {
                System.out.println("[ERROR] 게시글 번호 또는 사용자 정보 누락");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 게시글 번호 또는 사용자 정보가 필요.");
                return;
            }

            int bid;
            try {
                bid = Integer.parseInt(bidParam);
                System.out.println("[INFO] 게시글 번호 파싱 성공: " + bid);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] 게시글 번호 파싱 실패: " + bidParam);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 게시글 번호는 Int 타입이어야 합니다.");
                return;
            }

            // 3. DAO와 DTO 설정
            LikeDAO likeDAO = new LikeDAO();
            LikeDTO likeDTO = new LikeDTO();
            likeDTO.setBoardNum(bid);
            likeDTO.setMemberNum(memberPK);
            likeDTO.setCondition("selectOne");
            System.out.println("[INFO] LikeDTO 설정 완료: 게시글 번호 " + bid + ", 사용자 번호 " + memberPK);

            // 4. 좋아요 상태 확인 및 처리
            LikeDTO existingFavorite = likeDAO.selectOne(likeDTO);
            boolean isLiked = (existingFavorite != null);

            boolean operationResult;
            if (isLiked) {
                try {
                	likeDTO.setLikeNum(existingFavorite.getLikeNum()); // 기존 좋아요의 LIKE_NUM 설정
                    operationResult = likeDAO.delete(likeDTO);
                    if (operationResult) {
                        System.out.println("[INFO] 좋아요 삭제 성공");
                    } else {
                        System.out.println("[ERROR] 좋아요 삭제 실패");
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 좋아요 삭제에 실패했습니다.");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("[ERROR] 좋아요 삭제 중 예외 발생: " + e.getMessage());
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 좋아요 삭제 중 오류가 발생했습니다.");
                    return;
                }
            } else {
                try {
                    operationResult = likeDAO.insert(likeDTO);
                    if (operationResult) {
                        System.out.println("[INFO] 좋아요 추가 성공");
                    } else {
                        System.out.println("[ERROR] 좋아요 추가 실패");
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 좋아요 추가에 실패했습니다.");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("[ERROR] 좋아요 추가 중 예외 발생: " + e.getMessage());
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 좋아요 추가 중 오류가 발생했습니다.");
                    return;
                }
            }

            // 5. JSON 응답 전송
            try {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": " + operationResult + ", \"liked\": " + !isLiked + "}");
            } catch (IOException e) {
                System.out.println("[ERROR] JSON 응답 전송 중 예외 발생: " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 응답 작성 중 오류가 발생했습니다.");
            }

        } catch (Exception e) {
            System.out.println("[ERROR] 서블릿 처리 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 서버 처리 중 오류가 발생했습니다.");
        }

        System.out.println("[INFO] InsertDeleteFavoriteServlet 실행 종료");
    }
}
