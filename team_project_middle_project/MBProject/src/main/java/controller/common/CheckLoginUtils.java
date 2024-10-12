package controller.common;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/**
 * CheckLoginUtils 클래스는 사용자의 로그인 상태를 확인하는 유틸리티 클래스로,
 * 세션에서 사용자 정보를 확인하고, 로그인이 되어 있지 않으면 로그인 페이지로 리다이렉트하는 메서드를 제공합니다.
 */
public class CheckLoginUtils {
    /**
     * 사용자의 로그인 상태를 확인하고, 로그인이 되어 있지 않으면 로그인 페이지로 리다이렉트합니다.
     * 
     * @param session  현재 사용자의 세션 객체.
     *                 - 세션에서 "memberPK" 속성을 사용하여 로그인 상태를 확인합니다.
     * @param response HTTP 응답 객체. 로그인되지 않은 경우 로그인 페이지로 리다이렉트할 때 사용됩니다.
     * @param request  HTTP 요청 객체. 로그인되지 않은 경우 로그인 페이지로 리다이렉트할 때 사용됩니다.
     * @return boolean 로그인 상태일 경우 true, 로그인되지 않은 경우 false.
     * @throws IOException    로그인되지 않았을 때 리다이렉트 중 발생할 수 있는 IO 예외.
     * @throws ServletException 서블릿 처리 중 발생할 수 있는 예외.
     */
    public static boolean checkLogin(HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        System.out.println("[INFO] CheckLoginUtils: 로그인 확인 시작");

        // 세션에서 memberPK를 가져옴
        Integer memberPK = (Integer) session.getAttribute("memberPK");
        System.out.println("[DEBUG] 세션에서 가져온 memberPK 값: " + memberPK);

        // 로그인 여부 확인
        if (memberPK == null) {
            System.out.println("[INFO] 로그인 상태가 아님, failInfo.jsp로 포워딩");

            // request 대신 세션에 데이터 설정
            session.setAttribute("msg", "로그인이 필요합니다. 로그인 후 다시 시도하세요.");
            session.setAttribute("path", request.getContextPath() + "/loginPage.do");

            System.out.println("[DEBUG] failInfo2.jsp로 포워딩 중 - 메시지: 로그인 필요, 경로: " + request.getContextPath() + "/login.do");

            // sendRedirect 사용 시 세션에서 데이터 유지 가능
            response.sendRedirect(request.getContextPath() + "/failInfo2.jsp");
            
            System.out.println("[INFO] 로그인 상태가 아니므로 false 반환");
            return false; // 로그인 상태가 아니면 false 반환
        }

        System.out.println("[INFO] 로그인 상태 확인 완료, 로그인 상태이므로 true 반환");
        return true; // 로그인 상태일 경우 true 반환
    }
}

