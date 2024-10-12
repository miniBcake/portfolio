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
 * 상품 정보를 업데이트하는 서블릿입니다. 
 * 클라이언트로부터 수정된 상품 정보, 이미지 파일 등을 받아 데이터베이스에 반영하고,
 * 기존 이미지를 삭제 및 업데이트하며, 새로운 이미지를 서버에 저장합니다.
 * 
 * @param request  클라이언트의 HTTP 요청 객체.
 *                 - "productNum": 수정할 상품 번호.
 *                 - "productName": 수정할 상품 이름.
 *                 - "productPrice": 수정할 상품 가격.
 *                 - "productCateName": 수정할 상품 카테고리 이름.
 *                 - "boardContent": 수정할 게시글 내용.
 *                 - "newImagePath": 수정할 이미지 경로.
 *                 - "productProfileWay": 수정할 상품 대표 이미지 파일.
 * @param response 클라이언트의 HTTP 응답 객체. 성공 또는 실패 시 JSON 형식으로 결과를 응답합니다.
 * 
 * @throws ServletException 서블릿 관련 예외가 발생할 경우
 * @throws IOException      입출력 관련 예외가 발생할 경우
 */

// 파일 업로드를 처리하기 위한 설정
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB - 메모리 임계값
		maxFileSize = 1024 * 1024 * 10,       // 10 MB - 개별 파일의 최대 크기
		maxRequestSize = 1024 * 1024 * 100    // 100 MB - 전체 요청의 최대 크기
)

// 서블릿 매핑 URL 설정
@WebServlet("/updateProduct.do")
public class UpdateProductMDAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// 파일 저장 경로 설정 - 실제 서버에 저장되는 경로 및 클라이언트가 접근할 수 있는 상대 경로 설정
	private static final String UPLOAD_DIRECTORY = ConfigUtils.getProperty("upload.absolute.path");
    private static final String RELATIVE_UPLOAD_DIRECTORY = ConfigUtils.getProperty("upload.relative.path");

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[INFO] UpdateProductMDAction 시작");

		// 1. 세션에서 로그인 및 관리자 권한 확인
		HttpSession session = request.getSession();

		// 관리자 권한이 없는 경우 처리
		if (!AuthorizationUtils.isAdmin(session)) {
			System.out.println("[ERROR] 관리자 권한이 없음.");
			AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "관리자 권한이 필요합니다.");
			return; // 권한이 없으므로 더 이상 진행하지 않음
		}

		// 2. 요청 파라미터에서 업데이트할 상품 정보 가져오기
		String productNumStr = request.getParameter("productNum");
		String productName = request.getParameter("productName");
		String productPriceStr = request.getParameter("productPrice");
		String boardNumStr = request.getParameter("boardNum");

		// 입력된 파라미터 로그 출력
		System.out.println("[INFO] 받아온 파라미터 값 - productNum: " + productNumStr + 
				", productName: " + productName + 
				", productPrice: " + productPriceStr + 
				", boardNum: " + boardNumStr);

		// 3. productCateName 필수 값 받아오기
		String productCateName = request.getParameter("productCateName");
		System.out.println("[INFO] 상품 카테고리 이름: " + productCateName);
		
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


		// 필수 입력값 유효성 검사 - 하나라도 누락되면 에러 처리
		if (productNumStr == null || productNumStr.isEmpty() || 
				productName == null || productName.isEmpty() || 
				productPriceStr == null || productPriceStr.isEmpty() || 
				boardNumStr == null || boardNumStr.isEmpty() || 
				productCateName == null || productCateName.isEmpty() ) {

			System.out.println("[ERROR] 필수 입력값 누락.");
			AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "필수 입력 값이 모두 작성되어야 합니다.");
			return; // 필수 값이 모두 입력되지 않았으므로 처리 중지
		}

		// 상품 ID, 가격, 게시판 번호 등을 숫자 형식으로 변환
		int productNum, productPrice, boardNum;
		try {
			productNum = Integer.parseInt(productNumStr);
			productPrice = Integer.parseInt(productPriceStr);
			boardNum = Integer.parseInt(boardNumStr);
		} catch (NumberFormatException e) {
			System.out.println("[ERROR] 상품 ID, 가격 또는 게시판 번호 파싱 실패.");
			AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "상품 ID, 가격, 게시판 번호는 숫자 형식이어야 합니다.");
			return; // 형식 변환 실패 시 처리 중지
		}

		// 모든 필수 입력값이 검증 성공했음을 알림
		System.out.println("[INFO] 모든 필수 입력값 검증 성공 - 상품 ID: " + productNum + 
				", 상품 이름: " + productName + ", 상품 가격: " + productPrice + 
				", 게시판 번호: " + boardNum + ", 카테고리: " + productCateName);

		// 4. 이미지 업데이트 시작     
		String newImagePath = request.getParameter("newImagePath"); // 새 이미지 경로

		// 새로운 이미지가 있을 경우 처리
		if (newImagePath != null && !newImagePath.isEmpty()) {
			System.out.println("[INFO] 기존 이미지 삭제 시작");

			ImageDAO imageDAO = new ImageDAO();           
			ImageDTO imageDTO = new ImageDTO();
			imageDTO.setBoardNum(boardNum); // 게시판 번호로 기존 이미지 찾기

			// DB에서 기존 이미지를 삭제
			ArrayList<ImageDTO> existingImages = imageDAO.selectAll(imageDTO);
			if (existingImages != null && !existingImages.isEmpty()) {
				for (ImageDTO img : existingImages) {
					boolean imageDeleted = imageDAO.delete(img); 
					if (!imageDeleted) {
						System.out.println("[ERROR] 이미지 삭제 실패 - 이미지 ID: " + img.getImageNum());
					} else {
						System.out.println("[INFO] 기존 이미지 삭제 성공 - 이미지 ID: " + img.getImageNum());
					}
				}
			}

			// 새로운 이미지 삽입 처리
			System.out.println("[INFO] 새로운 이미지 추가 시작");
			String[] imagePathArray = newImagePath.split(",");
			for (String imagePath : imagePathArray) {
				ImageDTO newImageDTO = new ImageDTO();
				newImageDTO.setImageWay(imagePath.trim()); // 경로 앞뒤 공백 제거
				newImageDTO.setBoardNum(boardNum);
				boolean imageResult = imageDAO.insert(newImageDTO);
				if (!imageResult) {
					// 이미지 삽입 실패 시 처리 중지
					AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "이미지 삽입 실패");
					return;
				}
			}
		} else {
			System.out.println("[INFO] 추가할 이미지가 없습니다.");
		}
		
		// 5. 게시판 업데이트
		String boardTitle = request.getParameter("boardTitle");
		String boardContent = request.getParameter("boardContent");
		// HTML 태그를 제거한 텍스트로 변환
		String plainTextContent = Jsoup.parse(boardContent).text();
        System.out.println("[INFO] 게시글 내용 HTML 태그 삭제를 위한 파싱: " + plainTextContent);
        
        // 게시판 정보 업데이트
        BoardDAO boardDAO = new BoardDAO();
        BoardDTO boardUpdate = new BoardDTO();
        boardUpdate.setCondition("BOARD_UPDATE");
        boardUpdate.setBoardNum(boardNum); 
        boardUpdate.setBoardTitle(boardTitle);
        boardUpdate.setBoardContent(plainTextContent);

		try {
		    boardDAO.update(boardUpdate);
		    System.out.println("[INFO] 게시글 업데이트 성공");
		} catch (Exception e) {
			System.out.println("[ERROR] 게시글 업데이트 중 오류 발생");
		    AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "게시글 업데이트중 오류가 발생");
		    return; // 게시글 업데이트 실패 시 처리 중지
		}

		// 6. 상품 정보 업데이트
		ProductDAO productDAO = new ProductDAO();
		ProductDTO currentImage = new ProductDTO();
		currentImage.setCondition("MD_ONE");
		currentImage.setProductNum(productNum);
		
		
		// 7. 상품 대표이미지 (파일 업로드 처리)
		
		// 파일이 저장될 경로 확인
		File uploadDir = new File(UPLOAD_DIRECTORY);
		if (!uploadDir.exists()) {
		    // 업로드 경로가 존재하지 않는다면 디렉토리를 생성
		    if (uploadDir.mkdirs()) {
		        System.out.println("[INFO] 업로드 디렉토리가 생성되었습니다: " + UPLOAD_DIRECTORY);
		    } else {
		        System.out.println("[ERROR] 업로드 디렉토리 생성 실패: " + UPLOAD_DIRECTORY);
		        AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "업로드 디렉토리 생성 실패");
		        return; // 디렉토리 생성 실패 시 처리 중지
		    }
		}
		
		// 상품 대표이미지 서버에 업데이트 시작
		Part productImagePart = request.getPart("productProfileWay");
		System.out.println("[INFO] 파일 업로드 - productProfileWay: " + 
		        (productImagePart != null ? productImagePart.getSubmittedFileName() : "No file uploaded"));

		String relativeFilePath = null; // 이미지 경로를 저장할 변수
		String currentImagePath = productDAO.selectOne(currentImage).getProductProfileWay(); // DB에서 기존 이미지 경로 조회

		if (productImagePart == null || productImagePart.getSize() == 0) {
		    // 파일이 업로드되지 않은 경우 - 기존 이미지 경로 유지
		    System.out.println("[INFO] 파일이 업로드되지 않았습니다. 기존 이미지 경로를 유지합니다.");
		    relativeFilePath = currentImagePath;  // 기존 이미지를 그대로 사용
		} else {
		    // 파일 크기가 10MB를 초과하는지 확인 - 초과 시 오류 처리
		    if (productImagePart.getSize() > 10 * 1024 * 1024) {
		        System.out.println("[ERROR] 파일이 너무 큽니다.");
		        AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "파일 크기는 10MB를 초과할 수 없습니다.");
		        return; // 파일 크기가 초과되었으므로 더 이상 진행하지 않음
		    }

		    // 새로운 파일이 업로드된 경우 처리
		    System.out.println("[INFO] 새 이미지가 업로드되었습니다.");

		    // 기존 이미지가 있으면 삭제
		    if (currentImagePath != null && !currentImagePath.isEmpty()) {
		        String existingFileFullPath = UPLOAD_DIRECTORY + "/" + currentImagePath;
		        File existingFile = new File(existingFileFullPath);
		        
		        if (existingFile.exists() && existingFile.delete()) {
		            System.out.println("[INFO] 서버에서 기존 이미지 삭제 성공: " + existingFileFullPath);
		        } else {
		            System.out.println("[ERROR] 서버에서 기존 이미지 삭제 실패 또는 파일이 존재하지 않음: " + existingFileFullPath);
		        }
		    }

		    // 새로운 파일 저장 (UUID로 파일명 고유화)
		    String uploadedFileName = Paths.get(productImagePart.getSubmittedFileName()).getFileName().toString();
		    String newFileName = "mainImage_"+ UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + uploadedFileName;
		    String fullNewFilePath = UPLOAD_DIRECTORY + "/" + newFileName;

		    try (var inputStream = productImagePart.getInputStream()) {
		        Files.copy(inputStream, Paths.get(UPLOAD_DIRECTORY, newFileName), StandardCopyOption.REPLACE_EXISTING);
		        System.out.println("[INFO] 새로운 파일 저장 성공: " + fullNewFilePath);
		        relativeFilePath = RELATIVE_UPLOAD_DIRECTORY + "/" + newFileName;
		    } catch (IOException e) {
		        System.out.println("[ERROR] 파일 저장 실패: " + e.getMessage());
		        AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "파일 저장 실패");
		        return; // 파일 저장 실패 시 처리 중지
		    }
		}
        
        // 8. 상품 정보 업데이트
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductName(productName);
		productDTO.setProductPrice(productPrice);
		productDTO.setBoardNum(boardNum);
		productDTO.setProductProfileWay(relativeFilePath); // 이미지 경로 설정
		productDTO.setProductCateNum(productCateNum);
		productDTO.setProductNum(productNum);

		// 상품 정보 DB 업데이트
		boolean updateResult = productDAO.update(productDTO);

		// 업데이트 성공 여부에 따라 응답 처리
		if (updateResult) {
			System.out.println("[INFO] 상품 업데이트 성공 - 상품명: " + productName);
			AsyncUtils.sendJsonResponse(response, true, "상품 업데이트 성공", null);
		} else {
			System.out.println("[ERROR] 상품 업데이트 실패 - 상품명: " + productName);
			AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "상품 업데이트 실패");
		}
	}

}
