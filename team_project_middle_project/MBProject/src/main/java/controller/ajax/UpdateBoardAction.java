package controller.ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;

import controller.common.AsyncUtils;
import controller.common.CheckLoginUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BoardDAO;
import model.dao.ImageDAO;
import model.dto.BoardDTO;
import model.dto.ImageDTO;

/**
 * 게시글을 수정하는 서블릿입니다. 
 * 이 서블릿은 사용자가 게시글을 수정하고, 관련된 이미지 파일도 업데이트하는 작업을 처리합니다.
 * 
 * @param request  클라이언트의 HTTP 요청 객체.
 *                 - "boardNum": 수정할 게시글 번호.
 *                 - "fixBoardTitle": 게시글 제목.
 *                 - "fixBoardContent": 게시글 내용.
 *                 - "secretBoardContents": 비밀글 여부 (on: 비밀글, null: 공개글).
 *                 - "category": 게시글의 카테고리.
 *                 - "imagePaths": 업데이트할 이미지 경로.
 * @param response 클라이언트의 HTTP 응답 객체. 에러 발생 시 상태 코드와 함께 에러 메시지를 전달합니다.
 * 
 * @throws ServletException 서블릿 관련 오류가 발생할 경우
 * @throws IOException      입출력 오류가 발생할 경우
 */

// @MultipartConfig 어노테이션을 추가하여 파일 업로드를 지원하도록 설정
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
		maxFileSize = 1024 * 1024 * 10,       // 10 MB
		maxRequestSize = 1024 * 1024 * 100    // 100 MB
		)

@WebServlet("/updateBoard.do")  // 서블릿 매핑 URL 설정
public class UpdateBoardAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[INFO] UpdateBoardServlet 시작");
		
		 // 1. 세션에서 사용자 정보 가져오기
        HttpSession session = request.getSession();            
        System.out.println("[INFO] 사용자 세션에서 정보 가져오기 시도");

        // 로그인 여부를 확인하여 로그인되지 않았으면 null을 반환
        if (!CheckLoginUtils.checkLogin(session, response, request)) {
            System.out.println("[ERROR] 사용자 로그인 필요");
            return; 
        }
        
        // 세션에서 사용자 ID (memberPK) 가져오기
        Integer memberPK = (Integer) session.getAttribute("memberPK");
        System.out.println("[INFO] 사용자 인증 성공, memberPK: " + memberPK);

		// 2. 파라미터 가져오기 및 유효성 검사
		String boardNumParam = request.getParameter("boardNum");
		String fixBoardTitle = request.getParameter("fixBoardTitle");
		String fixBoardContent = request.getParameter("fixBoardContent");
		String secretBoardContents = request.getParameter("secretBoardContents");
		String categoryName = request.getParameter("category");
		String imagePaths = request.getParameter("imagePaths");


		System.out.println("[INFO] 받은 파라미터 - boardNum: " + boardNumParam + ", fixBoardTitle: " + fixBoardTitle + 
				", fixBoardContent: " + fixBoardContent + ", secretBoardContents: " + secretBoardContents + 
				", category: " + categoryName + ", imagePaths: " + imagePaths);
		
		String plainTextContent = Jsoup.parse(fixBoardContent).text();
        System.out.println("[INFO] 게시글 내용 HTML 태그 삭제를 위한 파싱: " + plainTextContent);

		// 3. 필수 입력 값이 누락되었는지 확인
		if (boardNumParam == null || boardNumParam.isEmpty() || 
				fixBoardTitle == null || fixBoardTitle.isEmpty() ||
				fixBoardContent == null || fixBoardContent.isEmpty() ||
				categoryName == null || categoryName.isEmpty()) {
			System.out.println("[ERROR] 필수 입력 값이 누락되었습니다.");
			AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "필수 입력 값이 모두 작성되어야 합니다.");
			return;
		}

		// 4. 게시글 번호 파싱 및 유효성 검사
		int boardNum;
		try {
			boardNum = Integer.parseInt(boardNumParam); // 게시글 번호를 정수로 파싱
			System.out.println("[INFO] 게시글 번호 파싱 성공: " + boardNum);
		} catch (NumberFormatException e) {
			System.out.println("[ERROR] 게시글 번호 파싱 실패: " + boardNumParam);
			AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "게시글 번호는 숫자여야 합니다.");
			return;
		}


		// 5. BoardDTO 객체 생성 및 수정할 정보 설정
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO boardUpdate = new BoardDTO();
		boardUpdate.setCondition("BOARD_UPDATE");
		boardUpdate.setBoardNum(boardNum); // 게시글 번호 설정
		boardUpdate.setBoardTitle(fixBoardTitle); // 제목 설정
		boardUpdate.setBoardContent(plainTextContent); // 내용 설정
		boardUpdate.setMemberNum(memberPK); // 작성자 ID 설정
		boardUpdate.setBoardCateName(categoryName); // 카테고리 이름 설정
		
		System.out.println("[INFO] BoardDTO 설정 완료 - " + boardUpdate.toString());

		// 6. 비밀글/공개글 여부 설정
		if (secretBoardContents != null && secretBoardContents.equals("on")) {
			boardUpdate.setBoardOpen("N");  // 비밀글로 설정
			System.out.println("[INFO] 게시글 비밀글로 설정");
		} else {
			boardUpdate.setBoardOpen("Y");  // 공개글로 설정
			System.out.println("[INFO] 게시글 공개글로 설정");
		}

		// 7. 게시글 업데이트 수행
		try {
		    boardDAO.update(boardUpdate); // 게시글 업데이트 시도
		    System.out.println("[INFO] 게시글 업데이트 성공");
		} catch (Exception e) {
		    System.out.println("[ERROR] 게시글 업데이트 중 오류 발생");
		    e.printStackTrace();
		    AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "게시글 업데이트 중 오류가 발생.");
		    return;
		}

		// 8. 이미지 업데이트 수행
		if (imagePaths != null && !imagePaths.isEmpty()) {
		    System.out.println("[INFO] 이미지 업데이트 수행 시작");

		    ImageDAO imageDAO = new ImageDAO();
		    
		    // 8.1. 기존 이미지 삭제 (서버 파일 삭제는 제거하고 데이터베이스에서만 삭제)
		    System.out.println("[INFO] 기존 이미지 삭제 시작");
		    ImageDTO imageDTO = new ImageDTO();
		    imageDTO.setBoardNum(boardNum);
		    
		    ArrayList<ImageDTO> existingImages = imageDAO.selectAll(imageDTO);
		    for (ImageDTO img : existingImages) {
		        System.out.println("[INFO] 삭제할 이미지: " + img.getImageNum() + " - 경로: " + img.getImageWay());
		        boolean deleteResult = imageDAO.delete(img); // 데이터베이스에서 이미지 삭제
		        if (!deleteResult) {
		            System.out.println("[ERROR] 이미지 데이터베이스 삭제 실패: " + img.getImageNum());
		        } else {
		            System.out.println("[INFO] 이미지 데이터베이스 삭제 성공: " + img.getImageNum());
		        }
		    }

		    // 8.2. 새로운 이미지 삽입
		    // Image update 미구현으로 insert로 진행
		    System.out.println("[INFO] 새로운 이미지 추가 시작");
		    String[] imagePathArray = imagePaths.split(",");
		    for (String imagePath : imagePathArray) {
		        ImageDTO newImageDTO = new ImageDTO();
		        newImageDTO.setImageWay(imagePath.trim()); // 경로 양쪽 공백 제거
		        newImageDTO.setBoardNum(boardNum);
		        System.out.println("[INFO] 삽입할 이미지 경로: " + imagePath.trim());
		        boolean imageResult = imageDAO.insert(newImageDTO);
		        if (!imageResult) {
		            System.out.println("[ERROR] 이미지 삽입 실패: " + imagePath.trim());
		            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "이미지 삽입 실패");
		            return;
		        }
		    }
		} else {
		    System.out.println("[INFO] 이미지 업데이트를 수행할 이미지가 없습니다.");
		}

		// JSON 응답 반환
		Map<String, Object> options = new HashMap<>();
		options.put("boardNum", boardNum); // 게시글 번호 추가
		AsyncUtils.sendJsonResponse(response, true, "게시글이 수정되었습니다.", options);

		System.out.println("[INFO] UpdateBoardServlet 종료");
	}

}
