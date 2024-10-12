package controller.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

public class AsyncUtils {

    // 공통 설정을 처리하는 메서드 (Content-Type 및 인코딩 설정)
    private static void setupResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    // 공통 JSON 응답 처리 함수
    private static void writeJsonResponse(HttpServletResponse response, String jsonResponse) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.write(jsonResponse);
        }
    }

    // 공통 에러 응답 처리 함수
    public static void sendErrorResponse(HttpServletResponse response, int statusCode, String errorMessage) throws IOException {
        response.setStatus(statusCode);
        setupResponse(response);  // 공통 설정 메서드 호출
        String jsonResponse = "{\"success\": false, \"error\": \"" + errorMessage + "\"}";
        writeJsonResponse(response, jsonResponse); // 공통 JSON 응답 메서드 호출
        System.out.println("[ERROR] " + statusCode + ": " + errorMessage);
    }

    // 추가적인 옵션 필드를 동적으로 포함하는 성공 응답 처리 함수
    public static void sendJsonResponse(HttpServletResponse response, boolean success, String message, Map<String, Object> options) throws IOException {
        setupResponse(response);

        StringBuilder jsonResponse = new StringBuilder();
        jsonResponse.append("{\"success\": ").append(success).append(", \"message\": \"").append(message).append("\"");

        // 추가적인 옵션 필드가 있을 경우 JSON에 포함
        if (options != null) {
            for (Map.Entry<String, Object> entry : options.entrySet()) {
                jsonResponse.append(", \"").append(entry.getKey()).append("\": ").append(entry.getValue());
            }
        }

        jsonResponse.append("}");
        writeJsonResponse(response, jsonResponse.toString());
        System.out.println("[INFO] JSON 응답 전송: " + message);
    }
}
