package controller.common;

import jakarta.servlet.http.HttpSession;
/**
 * AuthorizationUtils 클래스는 사용자 권한을 확인하는 유틸리티 클래스로,
 * 세션에서 사용자 역할을 가져와 특정 권한(예: 관리자 권한)을 확인하는 메서드를 제공합니다.
 */
public class AuthorizationUtils {
    /**
     * 세션에서 사용자의 역할을 확인하여 관리자인지 여부를 반환합니다.
     * 
     * @param session 현재 사용자의 세션 객체.
     *                - "memberRole" 세션 속성을 사용하여 사용자의 역할을 확인합니다.
     * @return boolean 사용자가 관리자일 경우 true, 그렇지 않을 경우 false.
     */
    // 관리자 권한 확인 메서드
    public static boolean isAdmin(HttpSession session) {
        String userRole = (String) session.getAttribute("memberRole");
        return userRole != null && userRole.equals("ADMIN");
    }

}
