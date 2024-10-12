package controller.ajax;

import java.io.IOException;

import controller.common.AsyncUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BoardDAO;
import model.dao.LikeDAO;
import model.dto.BoardDTO;
import model.dto.LikeDTO;

/**
 * InsertDeleteFavoriteAction 클래스는 게시글에 대한 좋아요 기능을 처리하는 서블릿입니다.
 * 사용자가 게시글에 좋아요를 추가하거나 제거하며, 변경된 좋아요 수를 JSON 형식으로 응답합니다.
 */

@WebServlet("/insertDeleteFavorite.do") // 서블릿 매핑 URL 설정
public class InsertDeleteFavoriteAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 /**
     * HTTP POST 요청을 처리하여 사용자의 좋아요를 추가하거나 제거합니다.
     * 
     * @param request  클라이언트의 HTTP 요청 객체.
     *                 - "boardNum": 좋아요를 추가하거나 제거할 게시글 번호.
     * @param response 클라이언트에게 보낼 HTTP 응답 객체.
     *                 - 성공 또는 실패 메시지를 포함한 JSON 응답을 보냅니다.
     * @throws ServletException 서블릿 처리 중 예외 발생 시
     * @throws IOException      입력/출력 처리 중 예외 발생 시
     */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[INFO] InsertDeleteFavoriteServlet 실행 시작");

		try {
		 
	        // 1. 세션에서 작성자 정보 가져오기
	        HttpSession session = request.getSession();
	        Integer memberPK = (Integer) session.getAttribute("memberPK");
	        if (memberPK == null) {
	            System.out.println("[ERROR] 인증되지 않은 사용자입니다.");
	            
	            // JSON 형식으로 응답을 반환
	            response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");
	            
	            // 401 상태 코드 설정
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            
	            // JSON 형식으로 메시지 작성
	            response.getWriter().write("{\"error\": \"401\", \"message\": \"인증되지 않은 사용자입니다.\", \"redirect\": \"/MBProject/loginPage.do\"}");
	            
	            return;
	        }

			// 2. 클라이언트로부터 전달된 게시글 번호
			String bidParam = request.getParameter("boardNum");
			System.out.println("[INFO] bidParam값: " + bidParam);


			// 3. 유효성 검사
			if (bidParam == null || bidParam.isEmpty()) {
				System.out.println("[ERROR] 게시글 번호 누락");
				AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "게시글 번호가 필요합니다.");
				return;
			}


			int bid;
			try {
				bid = Integer.parseInt(bidParam);
				System.out.println("[INFO] 게시글 번호 파싱 성공: " + bid);
			} catch (NumberFormatException e) {
				System.out.println("[ERROR] 게시글 번호 파싱 실패: " + bidParam);
				AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "게시글 번호는 Int 타입이어야 합니다.");
				return;
			}

			// 4. DAO와 DTO 설정
			LikeDAO likeDAO = new LikeDAO();
			LikeDTO likeDTO = new LikeDTO();
			likeDTO.setBoardNum(bid);
			likeDTO.setMemberNum(memberPK);
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
						AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "좋아요 삭제에 실패했습니다.");
						return;
					}
				} catch (Exception e) {
					System.out.println("[ERROR] 좋아요 삭제 중 예외 발생: " + e.getMessage());
					e.printStackTrace();
					AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "좋아요 삭제 중 오류가 발생했습니다.");
					return;
				}
			} else {
				try {
					operationResult = likeDAO.insert(likeDTO);
					if (operationResult) {
						System.out.println("[INFO] 좋아요 추가 성공");
					} else {
						System.out.println("[ERROR] 좋아요 추가 실패");
						AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "좋아요 추가에 실패했습니다.");
						return;
					}
				} catch (Exception e) {
					System.out.println("[ERROR] 좋아요 추가 중 예외 발생: " + e.getMessage());
					e.printStackTrace();
					AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "좋아요 추가 중 오류가 발생했습니다.");
					return;
				}
			}

			// 5. 좋아요 CNT 변경을 위해 조회
			BoardDAO boardDAO = new BoardDAO(); // BoardDAO 객체 생성
			BoardDTO boardDTO = new BoardDTO(); // BoardDTO 객체 생성
			boardDTO.setBoardNum(bid); // 게시글 번호 설정
			boardDTO.setCondition("ONE_BOARD"); // 조회 조건 설정

			boardDTO = boardDAO.selectOne(boardDTO);

			// 6. 조회된 게시글이 존재하는지 확인
			if (boardDTO != null) {
				System.out.println("[INFO] 게시글 조회 성공: " + boardDTO.toString());
				// 7. 조회된 게시글을 request에 설정하여 JSP에서 사용하도록 함
				request.setAttribute("board", boardDTO);
			}else {
				System.out.println("[ERROR] 게시글을 찾을 수 없습니다.");
				AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "게시글을 찾을 수 없습니다.");
				return;
			}

			// 8. JSON 응답 전송
			try {
			    int newLikeCount = boardDTO.getLikeCnt(); // 좋아요 개수 가져오기
			    //캐쉬 방지 : 좋아요 즉각반응이 안될떄가 있음 
			    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			    response.setHeader("Pragma", "no-cache");
			    response.setDateHeader("Expires", 0);
			    response.setContentType("application/json");
			    response.getWriter().write("{\"success\": " + operationResult + ", \"liked\": " + !isLiked + ", \"newLikeCount\": " + newLikeCount + "}");
			} catch (IOException e) {
			    System.out.println("[ERROR] JSON 응답 전송 중 예외 발생: " + e.getMessage());
			    e.printStackTrace();
			    AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "응답 작성 중 오류가 발생했습니다.");
			}

		} catch (Exception e) {
			System.out.println("[ERROR] 서블릿 처리 중 예외 발생: " + e.getMessage());
			e.printStackTrace();
			AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 처리 중 오류가 발생했습니다.");
		}

		System.out.println("[INFO] InsertDeleteFavoriteServlet 실행 종료");
	}
}
