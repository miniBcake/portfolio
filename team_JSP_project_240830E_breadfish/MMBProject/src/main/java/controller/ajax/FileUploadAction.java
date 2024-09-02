package controller.ajax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

// 파일 업로드 설정을 위한 @MultipartConfig 어노테이션 사용
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB; 메모리 내 임시 저장 크기. 이 크기 이상일 경우 파일 시스템에 저장됨
    maxFileSize = 1024 * 1024 * 10,       // 10 MB; 업로드 가능한 파일의 최대 크기
    maxRequestSize = 1024 * 1024 * 100    // 100 MB; 요청 전체의 최대 크기(여러 파일 포함)
)

// 서블릿 매핑 설정을 위한 @WebServlet 어노테이션 사용
@WebServlet("/FileUpload") // 이 서블릿은 "/FileUpload" URL로 접근 가능
public class FileUploadAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 파일이 저장될 디렉토리 설정
    private static final String UPLOAD_DIRECTORY = "D:\\JJH\\workspace02\\MMBProject\\src\\main\\webapp\\img_b"; // 실제 파일이 저장될 디렉토리
    private static final String RELATIVE_UPLOAD_DIRECTORY = "img_b"; // 웹 경로에서 접근 가능한 업로드 디렉토리의 상대 경로

    // POST 요청을 처리하는 메서드
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action"); // 클라이언트 요청으로부터 액션 파라미터를 가져옴
        System.out.println("[INFO] 받은 액션: " + action); // 액션 로그 출력

        // 요청에 따라 파일 업로드 또는 삭제 처리
        if ("upload".equals(action)) {
            System.out.println("[INFO] 파일 업로드 요청 처리 시작"); // 업로드 요청 로그 출력
            handleFileUpload(request, response); // 파일 업로드 처리 메서드 호출
        } else if ("delete".equals(action)) {
            System.out.println("[INFO] 파일 삭제 요청 처리 시작"); // 삭제 요청 로그 출력
            handleFileDelete(request, response); // 파일 삭제 처리 메서드 호출
        } else {
            System.err.println("[ERROR] 유효하지 않은 액션 수신: " + action); // 유효하지 않은 요청 로그 출력
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 잘못된 요청입니다."); // 유효하지 않은 요청에 대한 에러 응답
        }
    }

 // 파일 업로드를 처리하는 메서드
    private void handleFileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String filePath = ""; // 업로드된 파일의 경로를 저장할 변수
        System.out.println("[INFO] 파일 업로드 프로세스 시작"); // 파일 업로드 시작 로그 출력

        // 모든 파일 파트를 가져와서 처리
        for (Part filePart : request.getParts()) {
            System.out.println("[DEBUG] 파일 파트 이름: " + filePart.getName() + ", 크기: " + filePart.getSize()); // 디버그 정보: 파일 파트 이름과 크기

            // 파일 파트가 존재하고 크기가 0보다 큰 경우에만 처리
            if (filePart.getName().equals("upload") && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // 업로드된 파일의 원래 이름을 가져옴
                System.out.println("[INFO] 업로드 중인 파일 이름: " + fileName); // 업로드 파일 이름 로그 출력

                String uniqueFileName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + fileName; // 고유한 파일 이름 생성
                File file = new File(UPLOAD_DIRECTORY, uniqueFileName); // 파일을 저장할 경로와 이름을 설정
                System.out.println("[INFO] 생성된 고유 파일 이름: " + uniqueFileName); // 고유한 파일 이름 로그 출력

                try (var inputStream = filePart.getInputStream()) { // 파일의 입력 스트림을 열어서
                    Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING); // 파일을 지정된 경로에 저장
                    System.out.println("[INFO] 파일이 성공적으로 저장됨: " + file.getAbsolutePath()); // 파일 저장 성공 로그 출력

                    filePath = RELATIVE_UPLOAD_DIRECTORY + "\\" + uniqueFileName; // 업로드된 파일 경로 저장
                } catch (IOException e) {
                    System.err.println("[ERROR] 파일 저장 중 오류: " + e.getMessage()); // 파일 저장 중 오류 로그 출력
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 파일 저장 중 오류가 발생했습니다."); // 파일 저장 중 오류 발생 시 에러 응답
                    return;
                }
            } else {
                System.out.println("[WARN] 업로드할 유효한 파일 파트가 없습니다. 파일 이름: " + filePart.getSubmittedFileName()); // 유효한 파일 파트가 없는 경우 로그 출력
            }
        }

        // JSON 응답 작성
        response.setContentType("application/json");
        response.getWriter().write("{\"uploaded\": true, \"url\": \"" + filePath + "\"}");
        // JSON 형식의 응답을 작성하고 클라이언트로 전송합니다.
        // {"uploaded": true, "url": "filePath의 값"} 형태

        System.out.println("[INFO] 업로드된 파일 경로가 클라이언트로 전송됨: " + filePath); // 파일 경로 전송 로그 출력
    }

    // 파일 삭제를 처리하는 메서드
    private void handleFileDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String relativeFilePath = request.getParameter("filePath"); // 삭제할 파일의 상대 경로를 클라이언트로부터 가져옴
        System.out.println("[INFO] 파일 삭제 요청 수신: " + relativeFilePath); // 파일 삭제 요청 로그 출력

        if (relativeFilePath == null || relativeFilePath.isEmpty()) {
            System.err.println("[ERROR] 삭제할 파일 경로가 제공되지 않음."); // 파일 경로가 제공되지 않은 경우 로그 출력
            response.setContentType("application/json");
            response.getWriter().write("{\"deleted\": false, \"message\": \"파일 경로가 제공되지 않았습니다.\"}"); // JSON 응답 반환
            return;
        }

        // 상대 경로를 절대 경로로 변환
        String absoluteFilePath = UPLOAD_DIRECTORY + relativeFilePath.substring(RELATIVE_UPLOAD_DIRECTORY.length());
        File file = new File(absoluteFilePath);
        System.out.println("[INFO] 서버에서 삭제할 파일: " + absoluteFilePath); // 파일 삭제 경로 로그 출력

        if (file.exists() && file.delete()) { // 파일이 존재하고 삭제에 성공한 경우
            System.out.println("[INFO] 파일 삭제 성공: " + absoluteFilePath); // 파일 삭제 성공 로그 출력
            response.setContentType("application/json");
            response.getWriter().write("{\"deleted\": true}"); // JSON 응답 반환
        } else {
            System.err.println("[ERROR] 파일 삭제 실패 또는 파일을 찾을 수 없음: " + absoluteFilePath); // 파일 삭제 실패 또는 파일을 찾을 수 없는 경우 로그 출력
            response.setContentType("application/json");
            response.getWriter().write("{\"deleted\": false, \"message\": \"파일을 찾을 수 없거나 삭제할 수 없습니다.\"}"); // JSON 응답 반환
        }
    }
}
