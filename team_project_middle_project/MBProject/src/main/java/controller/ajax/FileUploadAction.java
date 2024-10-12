package controller.ajax;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import controller.common.AsyncUtils;
import controller.common.ConfigUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * 파일 업로드와 삭제를 처리하는 서블릿입니다.
 * 클라이언트로부터 파일을 업로드하거나, 지정된 파일을 삭제하는 기능을 제공합니다.
 * 이 서블릿은 "/FileUpload" 경로로 접근 가능하며, 파일 업로드는 POST 요청을 통해 처리됩니다.
 * 
 * 파일 업로드에 대한 설정은 @MultipartConfig 어노테이션을 통해 정의됩니다.
 * 
 * @param request  클라이언트의 HTTP 요청 객체.
 *                 - "action": 처리할 작업 (upload 또는 delete).
 *                 - "filePath": 삭제할 파일의 경로 (삭제 작업 시).
 *                 - 업로드 작업 시 파일은 `Part` 객체로 전달됩니다.
 * @param response 클라이언트의 HTTP 응답 객체. 작업 완료 시 JSON 형식의 응답을 반환합니다.
 */

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
	private static final String UPLOAD_DIRECTORY = ConfigUtils.getProperty("upload.absolute.path"); // 실제 파일이 저장될 디렉토리
	private static final String RELATIVE_UPLOAD_DIRECTORY = ConfigUtils.getProperty("upload.relative.path"); // 웹 경로에서 접근 가능한 업로드 디렉토리의 상대 경로
	
    // POST 요청을 처리하는 메서드
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("[INFO] FileUploadAction 시작");
    	String action = request.getParameter("action"); // 클라이언트 요청으로부터 액션 파라미터를 가져옴
        System.out.println("[INFO] 클라이언트로부터 받은 액션: " + action); // 액션 로그 출력

        // 요청에 따라 파일 업로드 또는 삭제 처리
        if ("upload".equals(action)) {
            System.out.println("[INFO] 파일 업로드 프로세스 시작."); // 파일 업로드 시작 로그 출력
            handleFileUpload(request, response); // 파일 업로드 처리 메서드 호출
        } else if ("delete".equals(action)) {
            System.out.println("[INFO] 파일 삭제 프로세스 시작."); // 파일 삭제 시작 로그 출력
            handleFileDelete(request, response); // 파일 삭제 처리 메서드 호출
        } else {
            System.out.println("[ERROR] 유효하지 않은 액션 수신: " + action); // 유효하지 않은 요청 로그 출력
            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "400: 잘못된 요청입니다.");
        }
    }

    // 파일 업로드를 처리하는 메서드
    private void handleFileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        StringBuilder filePaths = new StringBuilder(); // 업로드된 파일들의 경로를 저장할 StringBuilder
        System.out.println("[INFO] 파일 업로드 처리 시작."); // 파일 업로드 처리 시작 로그 출력
        
        // 업로드 경로가 존재하지 않으면 생성
        File uploadDir = new File(UPLOAD_DIRECTORY);
        if (!uploadDir.exists()) {
            if (uploadDir.mkdirs()) {
                System.out.println("[INFO] 업로드 디렉토리가 생성되었습니다: " + UPLOAD_DIRECTORY);
            } else {
                System.out.println("[ERROR] 업로드 디렉토리 생성 실패.");
                AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 업로드 디렉토리 생성 실패");
                return;
            }
        }

        // 모든 파일 파트를 가져와서 처리
        for (Part filePart : request.getParts()) {
            // 파일 파트가 존재하고 크기가 0보다 큰 경우에만 처리
            if (filePart.getName().equals("upload") && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // 업로드된 파일의 원래 이름을 가져옴
                System.out.println("[INFO] 업로드 중인 파일: " + fileName); // 업로드 파일 이름 로그 출력

                // 고유한 파일 이름 생성
                String uniqueFileName = "postImage_" + UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + fileName;
                File file = new File(UPLOAD_DIRECTORY, uniqueFileName); // 파일을 저장할 경로와 이름을 설정
                System.out.println("[INFO] 생성된 고유 파일 이름: " + uniqueFileName); // 고유한 파일 이름 로그 출력

                try (var inputStream = filePart.getInputStream()) {
                    // 파일을 지정된 경로에 저장
                    Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("[INFO] 파일이 성공적으로 저장됨: " + file.getAbsolutePath()); // 파일 저장 성공 로그 출력

                    if (filePaths.length() > 0) {
                        filePaths.append(","); // 경로 구분을 위한 콤마 추가
                    }
                    filePaths.append(RELATIVE_UPLOAD_DIRECTORY + "/" + uniqueFileName); // 클라이언트로 보낼 웹 경로 기준의 파일 경로를 추가
                } catch (IOException e) {
                    System.out.println("[ERROR] 파일 저장 중 오류 발생: " + e.getMessage()); // 파일 저장 중 오류 로그 출력
                    AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 파일 저장 중 오류가 발생했습니다.");
                    return;
                }
            } else {
                System.out.println("[WARN] 업로드할 유효한 파일 파트가 없음."); // 유효한 파일 파트가 없는 경우 로그 출력
            }
        }
        // 파일 업로드 성공 후 클라이언트에게 업로드된 파일 경로를 JSON으로 응답
        response.setContentType("application/json");
        response.getWriter().write("{\"uploaded\": true, \"url\": \"" + filePaths.toString() + "\"}");
        System.out.println("[INFO] 업로드된 파일 경로가 클라이언트로 전송됨: " + filePaths.toString());
    }

    // 파일 삭제를 처리하는 메서드
    private void handleFileDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String relativeFilePath = request.getParameter("filePath"); // 삭제할 파일의 상대 경로를 클라이언트로부터 가져옴
        System.out.println("[INFO] 파일 삭제 요청 수신: " + relativeFilePath); // 파일 삭제 요청 로그 출력

        // 파일 경로가 제공되지 않은 경우 처리
        if (relativeFilePath == null || relativeFilePath.isEmpty()) {
            System.out.println("[ERROR] 삭제할 파일 경로가 제공되지 않음."); // 파일 경로가 제공되지 않은 경우 로그 출력
            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "400: 파일 경로가 제공되지 않았습니다.");
            return;
        }

        // 파일 경로 디코딩
        String decodedFilePath = URLDecoder.decode(relativeFilePath, StandardCharsets.UTF_8.name());
        System.out.println("[INFO] 디코딩된 파일 경로: " + decodedFilePath); // 디코딩된 파일 경로 로그 출력

        // 상대 경로를 절대 경로로 변환
        String absoluteFilePath = UPLOAD_DIRECTORY + decodedFilePath.substring(RELATIVE_UPLOAD_DIRECTORY.length());
        File file = new File(absoluteFilePath);
        System.out.println("[INFO] 서버에서 삭제할 파일 경로: " + absoluteFilePath); // 파일 삭제 경로 로그 출력
        
        // JSON 응답 준비
        // 파일이 존재하고 삭제에 성공한 경우        
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {           
            if (file.exists() && file.delete()) {
                System.out.println("[INFO] 파일 삭제 성공: " + absoluteFilePath); // 파일 삭제 성공 로그 출력
                response.setStatus(HttpServletResponse.SC_OK); // HTTP 상태 코드 200(성공) 설정
                out.write("{\"deleted\": true, \"filePath\": \"" + relativeFilePath + "\"}"); // JSON 형식으로 응답
            } else {
                // 파일 삭제 실패 또는 파일이 존재하지 않는 경우 처리
                System.out.println("[ERROR] 파일 삭제 실패 또는 파일을 찾을 수 없음: " + absoluteFilePath); // 파일 삭제 실패 또는 파일을 찾을 수 없는 경우 로그 출력
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // HTTP 상태 코드 404 설정
                out.write("{\"deleted\": false, \"error\": \"파일을 찾을 수 없거나 삭제할 수 없습니다.\"}"); // JSON 형식으로 응답
            }
        } catch (IOException e) {
            // 예외 처리
            e.printStackTrace();
            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
        }
    }
}
