package controller.ajax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.UUID;

import org.jsoup.Jsoup;

import controller.common.AsyncUtils;
import controller.common.AuthorizationUtils;
import controller.common.ConfigUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.dao.BoardDAO;
import model.dao.ImageDAO;
import model.dao.ProductCateDAO;
import model.dao.ProductDAO;
import model.dto.BoardDTO;
import model.dto.ImageDTO;
import model.dto.ProductCateDTO;
import model.dto.ProductDTO;

/**
 * 상품 등록을 처리하는 서블릿으로, 상품의 기본 정보, 카테고리, 관련 게시글, 이미지를 데이터베이스에 삽입하는 기능을 담당합니다.
 * 이 서블릿은 관리자 권한이 필요하며, 상품 정보 및 이미지를 업로드하고 저장합니다.
 * 
 * @param request  클라이언트로부터 전달받은 HTTP 요청 객체. 요청에는 다음과 같은 값들이 포함되어야 합니다:
 *                 - "productName": 상품 이름 (필수).
 *                 - "productPrice": 상품 가격, 숫자 형식 (필수).
 *                 - "productCateName": 상품 카테고리 이름 (필수).
 *                 - "boardContent": 상품 설명, 게시글로 등록됨 (필수).
 *                 - "imagePaths": 게시글과 관련된 추가 이미지 경로 (선택).
 *                 - "productProfileWay": 상품의 대표 이미지 파일 (파일 업로드 처리, 필수).
 * 
 * @param response 클라이언트에게 응답할 HTTP 응답 객체. 작업이 성공하면 success=true, 실패하면 success=false 및 에러 메시지를 반환합니다.
 * @return 작업 성공 시 JSON 응답을 반환하며, 실패 시 상태 코드와 함께 에러 메시지를 전달합니다.
 */

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
    maxFileSize = 1024 * 1024 * 10,       // 10 MB
    maxRequestSize = 1024 * 1024 * 100    // 100 MB
)
@WebServlet("/insertProduct.do")  // 서블릿 매핑 URL 설정
public class InsertProductMDAction extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private static final String UPLOAD_DIRECTORY = ConfigUtils.getProperty("upload.absolute.path"); // 실제 파일이 저장될 디렉토리
    private static final String RELATIVE_UPLOAD_DIRECTORY = ConfigUtils.getProperty("upload.relative.path"); // 웹 경로에서 접근 가능한 업로드 디렉토리의 상대 경로
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[INFO] InsertProductMDAction 시작");

        // 1. 세션에서 로그인 및 관리자 권한 확인
        HttpSession session = request.getSession();

        // 관리자 권한이 있는지 체크
        if (!AuthorizationUtils.isAdmin(session)) {
            System.out.println("[ERROR] 관리자 권한이 없음.");
            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "관리자 권한이 필요합니다.");
            return;
        }

        // 2. 클라이언트로부터 전달된 상품 정보 가져오기
        String productName = request.getParameter("productName");    // 상품 이름
        String productPriceStr = request.getParameter("productPrice"); // 상품 가격
        
        System.out.println("[INFO] 상품 이름: " + productName);
        System.out.println("[INFO] 상품 가격: " + productPriceStr);
        
        // 3. productCateName 필수 값 받아오기
        String productCateName = request.getParameter("productCateName");
        System.out.println("[INFO] 상품 카테고리 이름: " + productCateName);
        
        // 필수 입력값 유효성 검사
        if (productName == null || productName.isEmpty() ||
            productPriceStr == null || productPriceStr.isEmpty() ||
            productCateName == null || productCateName.isEmpty()) {
            System.out.println("[ERROR] 필수 입력값 누락 - 상품 이름, 가격, 카테고리 입력되지 않음.");
            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "상품 이름, 가격, 카테고리는 필수입니다.");
            return;
        }

        // 상품 가격 파싱 및 오류 처리
        int productPrice;
        try {
            productPrice = Integer.parseInt(productPriceStr);
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] 상품 가격 파싱 실패: " + productPriceStr);
            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "가격은 숫자 형식이어야 합니다.");
            return;
        }
        
        // 3-1. productCateName이 null이 아닐 때만 카테고리 조회를 실행
        int productCateNum = -1; // 초기값 설정
        if (productCateName != null && !productCateName.isEmpty()) {
            // 3-2. 카테고리PK값을 알기 위한 조회 
            ProductCateDTO productCateDTO = new ProductCateDTO();
            ProductCateDAO productCateDAO = new ProductCateDAO();

            ArrayList<ProductCateDTO> productCateList = productCateDAO.selectAll(productCateDTO);

            // 3-3. 카테고리 이름에 맞는 PK 찾기
            for (ProductCateDTO cateDTO : productCateList) {
                if (cateDTO.getProductCateName().equals(productCateName)) {
                    productCateNum = cateDTO.getProductCateNum();
                    System.out.println("카테고리 이름: " + productCateName + "의 PK 값은: " + productCateNum);               
                    break; // 원하는 값을 찾으면 반복문 종료
                }
            }
            // 3-4. 만약 일치하는 카테고리를 찾지 못한 경우
            if (productCateNum == -1) {
                System.out.println("해당 카테고리 이름에 맞는 PK를 찾을 수 없습니다.");
                AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "해당 카테고리 이름에 맞는 PK를 찾을 수 없습니다.");
                return;
            }
        }
        
        // 4. 상품 게시글 정보 등록
        String boardTitle = request.getParameter("boardTitle");
        String boardContent = request.getParameter("boardContent");
        
        // 로그 출력
        System.out.println("[INFO] 요청된 boardTitle: " + boardTitle);
        System.out.println("[INFO] 요청된 boardContent: " + boardContent);
        
        String plainTextContent = Jsoup.parse(boardContent).text();
        System.out.println("[INFO] 게시글 내용 HTML 태그 삭제를 위한 파싱: " + plainTextContent);

        BoardDTO boardDTO = new BoardDTO();
        BoardDAO boardDAO = new BoardDAO();
        boardDTO.setCondition("PRODUCT_INSERT");
        boardDTO.setBoardTitle(boardTitle);
        boardDTO.setBoardContent(plainTextContent);

        boolean boardInsertResult = boardDAO.insert(boardDTO);
        if (!boardInsertResult) {
            System.out.println("[ERROR] 게시글 등록 실패");
            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "게시글 등록 실패");
            return;
        }

        // 5. 게시글의 MAX PK 값 가져오기
        BoardDTO boardMaxPK = new BoardDTO();
        boardMaxPK.setCondition("MAXPK_BOARD");
        int boardNum = boardDAO.selectOne(boardMaxPK).getMaxPk();  // 가장 최근에 등록된 게시글의 PK 가져오기

        // 6. 메인 사진 파일 업로드 처리
        Part productImagePart = request.getPart("productProfileWay");
        
        // 파일이 없거나 크기가 0이면 오류 처리
        if (productImagePart == null || productImagePart.getSize() <= 0) {
            System.out.println("[ERROR] 파일이 업로드되지 않음 또는 파일이 선택되지 않음.");
            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "파일은 필수 항목입니다.");
            return;
        }
        // 파일 크기가 10MB를 초과하는지 확인
        if (productImagePart.getSize() > 10 * 1024 * 1024) { // 10MB = 10 * 1024KB = 10 * 1024 * 1024 bytes
            System.out.println("[ERROR] 파일이 너무 큽니다."); // 파일 크기 초과 로그 출력
            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "파일 크기는 10MB를 초과할 수 없습니다.");
            return; // 이후 코드 실행을 중지하고 반환
        }

        // UUID와 현재 시간을 이용해 고유한 파일 이름을 생성
        String originalFileName = Paths.get(productImagePart.getSubmittedFileName()).getFileName().toString();
        String fileName = "mainImage_" + UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + originalFileName;

        // 파일이 저장될 경로를 변수에 저장
        String uploadPath = UPLOAD_DIRECTORY;

        // File 객체를 통해 경로가 실제 존재하는지 확인하고, 없으면 폴더를 생성
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            // 디렉토리가 없을 경우 디렉토리를 생성
            uploadDir.mkdirs(); // 경로에 디렉토리 생성 (필요한 상위 디렉토리도 함께 생성)
            System.out.println("[INFO] 업로드 디렉토리가 생성되었습니다: " + uploadPath); // 생성된 경로 출력
        }

        // 파일 저장 처리
        String relativeFilePath = null; // 상대경로
        try (var inputStream = productImagePart.getInputStream()) {
            // 파일을 지정된 경로에 저장
            Files.copy(inputStream, Paths.get(uploadPath, fileName), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("[INFO] 파일이 성공적으로 저장됨: " + uploadPath + "/" + fileName);
            // 클라이언트가 접근할 수 있도록 상대 경로 설정
            relativeFilePath = RELATIVE_UPLOAD_DIRECTORY + "/" + fileName;
        } catch (IOException e) {
            // 파일 저장 중 오류 발생 시 예외 처리
            System.out.println("[ERROR] 파일 저장 실패: " + e.getMessage()); // 저장 실패 시 오류 메시지 출력
            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "파일 저장 실패");
            return; // 이후 코드 실행을 중지하고 반환
        }
        
        // 7. ProductDTO 설정
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCondition(""); //크롤링후 컨디션 유지 방지 - 초기화
        productDTO.setProductName(productName);
        productDTO.setProductPrice(productPrice);
        productDTO.setProductProfileWay(relativeFilePath);  // 상대 경로를 저장
        productDTO.setBoardNum(boardNum);
        productDTO.setProductCateNum(productCateNum);

        ProductDAO productDAO = new ProductDAO();
        boolean productInsertResult = productDAO.insert(productDTO);
       
        if (productInsertResult) {
            System.out.println("[INFO] 상품 등록 성공 - 상품명: " + productName);

            // 8. 게시판 서브 이미지 경로
            String imagePaths = request.getParameter("imagePaths");
            System.out.println("[INFO] 이미지 경로: " + imagePaths);
            
            // 9. 이미지 경로가 있는 경우 이미지를 삽입
            if (imagePaths != null && !imagePaths.isEmpty()) {
                System.out.println("[INFO] 이미지 삽입 시작");
                
                String[] imagePathArray = imagePaths.split(",");  // 쉼표로 구분된 이미지 경로들
                ImageDAO imageDAO = new ImageDAO();
                
                for (String imagePath : imagePathArray) {
                    ImageDTO imageDTO = new ImageDTO();
                    imageDTO.setImageWay(imagePath.trim());  // 이미지 경로 설정
                    imageDTO.setBoardNum(boardNum);  
                    
                    boolean imageResult = imageDAO.insert(imageDTO);
                    if (!imageResult) {
                        System.out.println("[ERROR] 이미지 삽입 실패: " + imagePath);
                        AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "이미지 삽입 실패");
                        return;
                    }
                }
            } 

            // 성공 시 응답
            AsyncUtils.sendJsonResponse(response, true, "상품이 성공적으로 등록되었습니다.", null);
        } else {
            // 상품 등록 실패 시 JSON 응답
        	 System.out.println("[ERROR] 상품 등록 실패 - 상품명: " + productName);
             AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "상품 등록 실패");
        }
    }

}