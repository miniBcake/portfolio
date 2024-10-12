package controller.page;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.AuthorizationUtils;
import controller.common.ConfigUtils;
import controller.common.ErrorUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BoardDAO;
import model.dao.ImageDAO;
import model.dao.ProductDAO;
import model.dto.BoardDTO;
import model.dto.ImageDTO;
import model.dto.ProductDTO;
/**
 * CheckProductPageAction 클래스는 상품 수정 페이지로 포워딩하기 전에
 * 해당 상품과 관련된 정보를 확인하고 권한을 검증하는 액션 클래스입니다.
 * 이 클래스는 관리자 권한을 가진 사용자만 접근할 수 있으며,
 * 관련된 게시글과 이미지 정보를 조회하여 JSP 페이지로 전달합니다.
 */
public class CheckProductPageAction implements Action {
	/**
	 * 클라이언트의 요청을 처리하고, 상품 및 관련 게시글 정보를 조회한 후
	 * 수정 페이지로 포워딩합니다.
	 * 관리자 권한이 없거나 유효하지 않은 상품인 경우, 에러 상태 코드를 반환합니다.
	 * 
	 * @param request  클라이언트의 HTTP 요청 객체. 
	 *                 - "productNum": 수정할 상품의 번호.
	 * @param response 클라이언트의 HTTP 응답 객체. 에러 발생 시 상태 코드와 함께 에러 메시지를 전달합니다.
	 * @return ActionForward 수정 페이지로 포워딩할 경로 정보를 포함한 ActionForward 객체.
	 */
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[INFO] CheckProductPageAction 시작");
		
		// 1. 세션에서 로그인 및 관리자 권한 확인
        HttpSession session = request.getSession();

        // RBAC 권한 확인: 관리자 권한이 있는지 체크
        if (!AuthorizationUtils.isAdmin(session)) {
            System.out.println("[ERROR] 관리자 권한이 없음.");
            ErrorUtils.sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "401: 관리자 권한이 필요합니다.");
            return null;
        }
		
		// 2. productNum 파라미터 가져오기 및 유효성 검사
        Integer productNum = ErrorUtils.validateAndParseIntParam(request, response, "productNum");
        if (productNum == null) {
            return null; // 유효성 검증 실패 시 처리 중단
        }
		System.out.println("[INFO] 받은 파라미터 - productNum: " + productNum);
		
		// 3. 상품 조회를 위한 DAO DTO 생성 
		ProductDAO productDAO = new ProductDAO();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setCondition("MD_ONE");
		productDTO.setProductNum(productNum);
		ProductDTO DetailProductDTO = productDAO.selectOne(productDTO); // 해당 상품을 조회
		
		// 4.상품 조회 유효성 검사
		if (DetailProductDTO != null) {
		    System.out.println("[INFO] 조회된 상품 정보: " + DetailProductDTO.toString()); // DTO의 toString() 메서드 사용
		} else {
			System.out.println("[ERROR] 해당 상품을 찾을 수 없습니다.");
		    ErrorUtils.sendError(response, HttpServletResponse.SC_NOT_FOUND, "404: 상품을 찾을 수 없습니다.");
		    return null;
		}

        
        // 5.boardNum 상품DTO에서 꺼내옴
        int boardNum = DetailProductDTO.getBoardNum();
        
        // 6. 게시글과 이미지 조회
        BoardDAO boardDAO = new BoardDAO(); // BoardDAO 객체 생성
        BoardDTO boardDTO = new BoardDTO(); // BoardDTO 객체 생성
        boardDTO.setBoardNum(boardNum); // 게시글 번호 설정
        boardDTO.setCondition("ONE_BOARD"); // 조회 조건 설정
        
        // 7. 사진 조회 요청
        ImageDAO imageDAO = new ImageDAO(); // ImageDAO 객체 생성
        ImageDTO imageDTO = new ImageDTO(); // ImageDTO 객체 생성
        imageDTO.setBoardNum(boardNum); // 사진 조회 요청
        
        // 8. DAO를 사용하여 게시글 조회 유효성 검사
        boardDTO = boardDAO.selectOne(boardDTO);
               
        // 게시글 내용에 이미지를 추가하기 위한 StringBuilder
        // 기존 게시글 안지워지는 이유 : StringBuilder contentImages는 DetailProductDTO.getContent()를 사용하여 기존의 게시글 내용을 먼저 가져오고, 그 후에 이미지 태그를 추가하는 방식
        // 기존 게시글이 없을 경우 null을 처리하여 빈 문자열로 초기화
        StringBuilder contentImages = new StringBuilder(DetailProductDTO.getBoardContent() != null ? DetailProductDTO.getBoardContent() : "");
       
        if (boardDTO != null) {
            System.out.println("[INFO] 게시글 조회 성공: " + boardDTO.toString());
            request.setAttribute("board", boardDTO);
            
            // 9. 이미지 정보 조회
            ArrayList<ImageDTO> images = imageDAO.selectAll(imageDTO);

            // images가 null이 아닐 경우에만 request에 설정
            if (images != null && !images.isEmpty()) {
                request.setAttribute("images", images);
                System.out.println("[INFO] 이미지 정보 조회 성공, 이미지 수: " + images.size());
                
                // 이미지 HTML 태그 생성 및 게시글 내용에 추가
			    for (ImageDTO image : images) {
			    	contentImages.append("<img src='").append(image.getImageWay()).append("' alt='첨부 이미지' style='max-width: 100%;'>");
			    }
            } else {
              	System.out.println("[INFO] 이미지 정보가 없습니다.");
            }
        } else {
            System.err.println("[ERROR] 게시글을 찾을 수 없습니다.");
            ErrorUtils.sendError(response, HttpServletResponse.SC_NOT_FOUND, "404: 게시글을 찾을 수 없습니다.");
            return null;
        }
        
		// 10. 상품 정보 클라이언트에 전달
        DetailProductDTO.setBoardContent(contentImages.toString());
        request.setAttribute("product", DetailProductDTO);
        
        //config.propeties 값 JSP 사용을 위해 전달
		request.setAttribute("uploadUrl", ConfigUtils.getProperty("ckEditor.uploadUrl"));
        request.setAttribute("deleteUrl", ConfigUtils.getProperty("ckEditor.deleteUrl"));
        request.setAttribute("deleteFetchUrl", ConfigUtils.getProperty("ckEditor.delteFileFetchUrl"));
        request.setAttribute("submitPostProductFetchUrl", ConfigUtils.getProperty("ckEditor.submitPostProductFetchUrl"));
        
        // 11. View로 포워딩
        ActionForward forward = new ActionForward();
        forward.setPath("productUpdate.jsp"); // 상품 상세보기 페이지
        forward.setRedirect(false); // 포워딩 방식
        return forward;
	}

}