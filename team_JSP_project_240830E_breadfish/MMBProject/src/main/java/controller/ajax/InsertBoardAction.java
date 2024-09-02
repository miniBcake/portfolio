package controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BoardDAO;
import model.dao.ImageDAO;
import model.dto.BoardDTO;
import model.dto.ImageDTO;

@WebServlet("/insertBoard.do")  // 서블릿 매핑 URL 설정
public class InsertBoardAction extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[INFO] InsertBoardServlet 실행 시작");

        // 1. 클라이언트로부터 전달된 게시글 정보 가져오기
        String newBoardTitle = request.getParameter("newBoardTitle");
        String newBoardContent = request.getParameter("newBoardContent");
        String secretBoardContents = request.getParameter("secretBoardContents");
        String categoryParam = request.getParameter("category");
        String imagePaths = request.getParameter("imagePaths"); // 파일 업로드 후 전달된 이미지 경로

        System.out.println("[INFO] 게시글 제목: " + newBoardTitle);
        System.out.println("[INFO] 게시글 내용: " + newBoardContent);
        System.out.println("[INFO] 비밀글 여부: " + ("on".equals(secretBoardContents) ? "비공개" : "공개"));
        System.out.println("[INFO] 카테고리: " + categoryParam);
        System.out.println("[INFO] 이미지 경로: " + imagePaths);

        String plainTextContent = Jsoup.parse(newBoardContent).text();
        System.out.println("[INFO] 게시글 내용 HTML 태그 삭제를 위한 파싱: " + plainTextContent);

        // 2. 필수 파라미터 유효성 검사
        if (newBoardTitle == null || newBoardTitle.isEmpty() ||
                newBoardContent == null || newBoardContent.isEmpty() ||
                categoryParam == null || categoryParam.isEmpty()) {
            System.out.println("[ERROR] 필수 입력 값 누락");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: 모든 요소가 작성되어 있지 않습니다.");
            return;
        }

        int category;
        try {
            category = Integer.parseInt(categoryParam);
            System.out.println("[INFO] 카테고리 번호 파싱 성공: " + category);
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] 카테고리 번호 파싱 실패: " + categoryParam);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "400: Category는 Int타입이어야 합니다.");
            return;
        }

        // 3. 세션에서 작성자 정보 가져오기
        HttpSession session = request.getSession();
        Integer writerPK = (Integer) session.getAttribute("memberPK");
        if (writerPK == null) {
            System.out.println("[ERROR] 인증되지 않은 사용자입니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "401: 인증되지 않은 사용자 입니다.");
            return;
        }
        System.out.println("[INFO] 작성자 ID 확인 성공: " + writerPK);

        // 4. DAO 인스턴스 생성
        BoardDAO boardDAO = new BoardDAO();
        ImageDAO imageDAO = new ImageDAO();
        BoardDTO boardDTO = new BoardDTO();

        // 5. 게시글 정보 설정
        boardDTO.setTitle(newBoardTitle);
        boardDTO.setContent(plainTextContent);
        boardDTO.setMemberNum(writerPK);
        boardDTO.setCategoryNum(category);

        // 6. 비밀글 여부 설정
        if ("on".equals(secretBoardContents)) {
            boardDTO.setVisibility("비공개");
            System.out.println("[INFO] 게시글을 비공개로 설정");
        } else {
            boardDTO.setVisibility("공개");
            System.out.println("[INFO] 게시글을 공개로 설정");
        }

        // 7. 게시글을 데이터베이스에 삽입
        System.out.println("[INFO] 게시글 데이터베이스 삽입 시작");
        boolean boardResult = boardDAO.insert(boardDTO);
        if (boardResult) {
            System.out.println("[INFO] 게시글 삽입 성공");

            // 방금 삽입된 게시글의 최대 BOARD_NUM을 가져옴
            BoardDTO boardMaxPK = new BoardDTO();
            boardMaxPK.setCondition("MAXPK");
            int maxPK = boardDAO.selectOne(boardMaxPK).getBoardCnt();    
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
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 이미지 삽입 실패");
                        return;
                    }
                }
            } else {
                System.out.println("[INFO] 삽입할 이미지가 없습니다.");
            }
        } else {
            System.out.println("[ERROR] 게시글 삽입 실패");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 게시글 등록 실패");
            return;
        }

        // JSON 응답 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.write("{\"success\": true, \"message\": \"게시글이 등록되었습니다.\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[INFO] InsertBoardServlet 실행 종료");
    }
}
