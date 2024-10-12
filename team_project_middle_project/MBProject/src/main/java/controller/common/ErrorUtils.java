package controller.common;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ErrorUtils 클래스는 서블릿에서 공통적으로 사용할 수 있는 에러 처리 메서드를 제공합니다.
 * 이 클래스의 메서드를 사용하여 HTTP 응답에서 에러를 전송할 수 있습니다.
 */
public class ErrorUtils {
   
	/**
     * HTTP 응답에 에러 상태 코드와 메시지를 설정하여 클라이언트에게 전송합니다.
     * @param response HTTP 응답 객체
     * @param statusCode HTTP 상태 코드
     * @param message 에러 메시지
     */
    public static void sendError(HttpServletResponse response, int statusCode, String message) {
        try {
        	// HTTP 응답에 에러 상태 코드와 메시지를 설정하여 클라이언트에게 전송합니다.
            response.sendError(statusCode, message);
        } catch (IOException e) {
        	 // 에러 응답 전송 중 발생한 예외를 처리합니다.
            System.err.println("[ERROR] 응답 전송 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 예외 처리 메서드.
     * @param response HTTP 응답 객체
     * @param e 발생한 예외
     * @param errorMessage 오류 메시지
     */
    public static void handleException(HttpServletResponse response, Exception e, String errorMessage) {
        System.err.println("[ERROR] " + errorMessage);
        e.printStackTrace();
        try {
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: " + errorMessage);
        } catch (Exception ex) {
            System.err.println("[ERROR] 응답 전송 중 오류 발생");
            ex.printStackTrace();
        }
    }

    
    /**
     * 요청 파라미터를 검증하고 정수로 변환하는 유틸리티 메서드.
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param paramName 파라미터 이름
     * @return 변환된 정수 값 또는 null
     */
    public static Integer validateAndParseIntParam(HttpServletRequest request, HttpServletResponse response, String paramName) {
        String paramValue = request.getParameter(paramName);
        if (paramValue == null || paramValue.isEmpty()) {
            System.err.println("[ERROR] 필수 파라미터 누락: " + paramName);
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "400: 필수 파라미터 누락 - " + paramName);
            return null;
        }

        try {
            return Integer.parseInt(paramValue);
        } catch (NumberFormatException e) {
            System.err.println("[ERROR] 잘못된 파라미터 형식 - " + paramName);
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "400: 잘못된 파라미터 형식 - " + paramName);
            return null;
        }
    }
}
