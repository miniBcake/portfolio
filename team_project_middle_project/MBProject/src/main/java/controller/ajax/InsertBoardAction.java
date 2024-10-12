package controller.ajax;

import java.io.IOException;
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
 * 클라이언트의 게시글 등록 요청을 처리하는 서블릿입니다.
 * 게시글과 관련된 이미지를 업로드한 후 데이터베이스에 삽입하고, 성공 여부를 클라이언트에게 JSON으로 응답합니다.
 * 
 * @param request  클라이언트의 HTTP 요청 객체. 
 *                 - "newBoardTitle": 게시글 제목.
 *                 - "newBoardContent": 게시글 내용.
 *                 - "secretBoardContents": 비밀글 여부.
 *                 - "category": 게시글 카테고리 (normal, request).
 *                 - "imagePaths": 게시글에 첨부된 이미지 경로.
 * @param response 클라이언트의 HTTP 응답 객체. 에러 발생 시 상태 코드와 함께 에러 메시지를 전달합니다.
 * @return ActionForward 게시글 삽입 성공 후 JSON 형식의 응답을 클라이언트로 전송합니다.
 */

@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
	    maxFileSize = 1024 * 1024 * 10,       // 10 MB
	    maxRequestSize = 1024 * 1024 * 100    // 100 MB
)


@WebServlet("/insertBoard.do")  // 서블릿 매핑 URL 설정
public class InsertBoardAction extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[INFO] InsertBoardServlet 실행 시작");
        
        // 1. 세션에서 사용자 정보 가져오기
        HttpSession session = request.getSession();            
        System.out.println("[INFO] DeleteReplyAction: 사용자 세션에서 정보 가져오기 시도");

        // 로그인 여부를 확인하여 로그인되지 않았으면 null을 반환
        if (!CheckLoginUtils.checkLogin(session, response, request)) {
            System.out.println("[ERROR] 사용자 로그인 필요");
            return; 
        }
        
        // 세션에서 사용자 ID (memberPK) 가져오기
        Integer memberPK = (Integer) session.getAttribute("memberPK");
        System.out.println("[INFO] 사용자 인증 성공, memberPK: " + memberPK);

        // 2. 클라이언트로부터 전달된 게시글 정보 가져오기
        String newBoardTitle = request.getParameter("newBoardTitle");
        String newBoardContent = request.getParameter("newBoardContent");
        String secretBoardContents = request.getParameter("secretBoardContents");
        String categoryParam = request.getParameter("category");
        String imagePaths = request.getParameter("imagePaths");
        
      
        System.out.println("[INFO] 게시글 제목: " + newBoardTitle);
        System.out.println("[INFO] 게시글 내용: " + newBoardContent);
        System.out.println("[INFO] 비밀글 여부: " + ("on".equals(secretBoardContents) ? "비공개" : "공개"));
        System.out.println("[INFO] 카테고리: " + categoryParam);
        System.out.println("[INFO] 이미지 경로: " + imagePaths);

        String plainTextContent = Jsoup.parse(newBoardContent).text();
        System.out.println("[INFO] 게시글 내용 HTML 태그 삭제를 위한 파싱: " + plainTextContent);

        // 3. 필수 파라미터 유효성 검사
        if (newBoardTitle == null || newBoardTitle.isEmpty() ||
                newBoardContent == null || newBoardContent.isEmpty() ||
                categoryParam == null || categoryParam.isEmpty()) {
            System.out.println("[ERROR] 필수 입력 값 누락");
            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "모든 요소가 작성되어 있지 않습니다.");
            return;
        }

        int category;
        switch (categoryParam) {
	        case "normal":
	            category = 1;
	            System.out.println("[INFO] 문자열 'normal'에 대한 카테고리 설정: " + category);
	            break;
	        case "request":
	            category = 2;
	            System.out.println("[INFO] 문자열 'request'에 대한 카테고리 설정: " + category);
	            break;
	        default:
	            System.out.println("[ERROR] 유효하지 않은 카테고리 값: " + categoryParam);
	            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "유효하지 않은 카테고리 값입니다.");
	            return;
        }
        

        // 4. DAO 인스턴스 생성
        BoardDAO boardDAO = new BoardDAO();
        ImageDAO imageDAO = new ImageDAO();
        BoardDTO boardDTO = new BoardDTO();

        // 5. 게시글 정보 설정
        boardDTO.setCondition("BOARD_INSERT");
        boardDTO.setBoardTitle(newBoardTitle);
        boardDTO.setBoardContent(plainTextContent);
        boardDTO.setMemberNum(memberPK);
        boardDTO.setBoardCateNum(category);

		// 6. 비밀글/공개글 여부 설정
		if (secretBoardContents != null && secretBoardContents.equals("on")) {
			boardDTO.setBoardOpen("N");  // 비밀글로 설정
			System.out.println("[INFO] 게시글 비밀글로 설정");
		} else {
			boardDTO.setBoardOpen("Y");  // 공개글로 설정
			System.out.println("[INFO] 게시글 공개글로 설정");
		}

        // 7. 게시글을 데이터베이스에 삽입
        int maxPK;
        System.out.println("[INFO] 게시글 데이터베이스 삽입 시작");
        boolean boardResult = boardDAO.insert(boardDTO);
        if (boardResult) {
            System.out.println("[INFO] 게시글 삽입 성공");

            // 방금 삽입된 게시글의 최대 BOARD_NUM을 가져옴
            BoardDTO boardMaxPK = new BoardDTO();
            boardMaxPK.setCondition("MAXPK_BOARD");
            maxPK = boardDAO.selectOne(boardMaxPK).getMaxPk();    
            System.out.println("maxPK :"+ maxPK);

            // 이미지 경로가 있는 경우 이미지를 삽입
            if (imagePaths != null && !imagePaths.isEmpty()) {
                System.out.println("[INFO] 이미지 삽입 시작");
                String[] imagePathArray = imagePaths.split(",");
                for (String imagePath : imagePathArray) {
                    ImageDTO imageDTO = new ImageDTO();
                    imageDTO.setImageWay(imagePath.trim());
                    imageDTO.setBoardNum(maxPK);  // 방금 삽입된 게시글의 BOARD_NUM을 사용
                    boolean imageResult = imageDAO.insert(imageDTO);
                    if (imageResult) {
                        System.out.println("[INFO] 이미지 삽입 성공: " + imagePath);
                    } else {
                        System.out.println("[ERROR] 이미지 삽입 실패: " + imagePath);
                        AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "이미지 삽입 실패");
                        return;
                    }
                }
            } else {
                System.out.println("[INFO] 삽입할 이미지가 없습니다.");
            }
        } else {
            System.out.println("[ERROR] 게시글 삽입 실패");
            AsyncUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "게시글 등록 실패");
            return;
        }

        // JSON 응답 반환
        Map<String, Object> options = new HashMap<>();
        options.put("boardNum", maxPK); // 추가 필드 설정
        AsyncUtils.sendJsonResponse(response, true, "게시글이 수정되었습니다.", options);
        System.out.println("[INFO] InsertBoardServlet 실행 종료");
    }
}
