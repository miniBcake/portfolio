package controller.ajax;

import java.io.IOException;

import org.json.JSONObject;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.ReplyDAO;
import model.dto.ReplyDTO;

/**
 * Servlet implementation class CheckReplyOwnerAction.
 * 이 서블릿은 사용자가 댓글의 작성자인지 확인하는 기능을 담당합니다.
 * 클라이언트로부터 댓글 번호와 게시글 번호를 받아, 로그인한 사용자가 해당 댓글의 작성자인지 확인 후 결과를 JSON 형태로 반환합니다.
 */

@WebServlet("/checkReplyOwner.do")
public class CheckReplyOwnerAction extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    /**
     * POST 요청을 처리하는 메서드.
     * 사용자가 로그인되어 있는지 확인한 후, 해당 사용자가 댓글 작성자인지 확인하여
     * JSON 응답을 클라이언트로 전송합니다.
     * JSONObject: 클라이언트와 비동기처리 호환성을 위해 JSON 응답
     * @param request  클라이언트로부터의 HTTP 요청 객체.
     *                 - "replyNum": 확인하려는 댓글 번호 .
     *                 - "boardNum": 댓글이 속한 게시글 번호 .
     * @param response 클라이언트에게 보낼 HTTP 응답 객체.
     * @throws IOException 입력/출력 오류가 발생한 경우.
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 세션을 불러옴
        HttpSession session = request.getSession();
        
        /**
         * JSONObject jsonResponse = new JSONObject();
         * 클라이언트에게 반환할 JSON 데이터를 생성하는 객체.
         * 키-값 쌍으로 이루어진 JSON 구조를 생성하고, 데이터를 추가 및 조작할 수 있습니다.
         * 이 객체는 주로 서버 응답으로 JSON 형식의 데이터를 보내기 위해 사용됩니다.
         */
        JSONObject jsonResponse = new JSONObject();

        // Session에서 로그인한 정보 가져오기
        Integer memberPK = (Integer) session.getAttribute("memberPK");
        
        // 로그: 세션에서 가져온 사용자 정보 확인
        System.out.println("[DEBUG] 세션에서 가져온 memberPK 값: " + memberPK);
        
        // 로그인 여부 확인: memberPK가 null인 경우 로그인되지 않은 상태
        if (memberPK == null) {
            System.out.println("[INFO] 사용자가 로그인되어 있지 않음, 로그인 요청 전송");
            // JSON 응답 설정: 로그인 필요 메시지 전송
            jsonResponse.put("isLoggedIn", false);
            jsonResponse.put("message", "로그인이 필요합니다.");
        } else {
        	// 로그: 사용자 인증 성공
            System.out.println("[INFO] 사용자 인증 성공 - memberPK: " + memberPK);
            try {
            	// 요청에서 댓글 번호(replyNum)와 게시글 번호(boardNum) 가져오기
                int replyNum = Integer.parseInt(request.getParameter("replyNum"));
                int boardNum = Integer.parseInt(request.getParameter("boardNum"));

                System.out.println("[DEBUG] 클라이언트 요청 - replyNum: " + replyNum + ", boardNum: " + boardNum);
                
                // ReplyDAO를 사용해 댓글 작성자를 가져옴
                ReplyDAO replyDAO = new ReplyDAO();
                ReplyDTO replyDTO = new ReplyDTO();
                replyDTO.setCondition("REPLY_ONE");
                replyDTO.setReplyNum(replyNum);
                ReplyDTO replyCheck = replyDAO.selectOne(replyDTO);
                
                // 작성자 확인: 댓글 작성자와 로그인한 사용자가 같은지 확인
                if (replyCheck != null && replyCheck.getMemberNum() == memberPK) {
                	// 로그: 댓글 작성자가 맞는 경우
                	System.out.println("[INFO] 댓글 작성자 확인 완료 - 사용자와 일치함");
                	// 작성자가 맞으면 댓글 수정 권한이 있음을 알림
                	jsonResponse.put("isLoggedIn", true);
                    jsonResponse.put("isOwner", true);
                    jsonResponse.put("replyContent", replyCheck.getReplyContent());
                    jsonResponse.put("boardNum", boardNum);
                    jsonResponse.put("replyNum", replyCheck.getReplyNum());
                } else {
                	// 로그: 댓글 작성자가 아닌 경우
                	System.out.println("[INFO] 댓글 작성자 확인 완료 - 사용자와 일치하지 않음");
                	// 작성자가 아니면 수정 권한 없음 메시지 전송
                	jsonResponse.put("isLoggedIn", true);
                    jsonResponse.put("isOwner", false);
                    jsonResponse.put("message", "수정 권한이 없습니다.");
                    jsonResponse.put("boardNum", boardNum);
                    jsonResponse.put("replyNum", replyCheck != null ? replyCheck.getReplyNum() : replyNum);
                }
            } catch (NumberFormatException e) {
            	// 로그: 잘못된 형식의 숫자 처리
                System.out.println("[ERROR] 잘못된 숫자 형식 - replyNum 또는 boardNum이 올바르지 않음");
                jsonResponse.put("error", "잘못된 요청입니다.");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("[ERROR] 서버 오류 발생");
                jsonResponse.put("error", "서버에 오류가 발생했습니다.");
                e.printStackTrace();
            }
        }
        // JSON 응답 헤더 및 데이터 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
        // 로그: 응답 전송 완료
        System.out.println("[INFO] 댓글 작성자 확인 응답 전송 완료");
    }
}