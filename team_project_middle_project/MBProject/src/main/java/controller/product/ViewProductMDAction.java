package controller.product;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ErrorUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BoardDAO;
import model.dao.ImageDAO;
import model.dao.ProductDAO;
import model.dto.BoardDTO;
import model.dto.ImageDTO;
import model.dto.ProductDTO;

/**
 * ViewProductMDAction 클래스는 특정 상품의 상세 정보를 조회하여
 * JSP 페이지로 포워딩하는 액션 클래스입니다.
 * 또한, 최근 본 상품 목록을 쿠키에 저장하고, 게시글 및 이미지를 조회합니다.
 */

public class ViewProductMDAction implements Action {
    /**
     * 클라이언트의 요청을 처리하여 특정 상품의 상세 정보를 조회하고,
     * 해당 정보를 View로 전달한 후 상품 페이지로 포워딩합니다.
     * 최근 본 상품은 쿠키에 저장하여 관리합니다.
     * 
     * @param request  클라이언트의 HTTP 요청 객체.
     *                 - "productNum": 조회할 상품의 번호.
     * @param response 클라이언트의 HTTP 응답 객체. 에러 발생 시 상태 코드와 함께 에러 메시지를 전달합니다.
     * @return ActionForward 상품 상세 페이지로 포워딩할 경로 정보를 포함한 ActionForward 객체.
     */
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[INFO] ViewProductMDAction 시작");

        // 1. 파라미터로 전달된 상품 ID 가져오기
        Integer productNum = ErrorUtils.validateAndParseIntParam(request, response, "productNum");
        if (productNum == null) {
            return null; // 파라미터 유효성 검증 실패 시 처리 중단
        }

        // 2. 상품 정보를 조회
        ProductDAO productDAO = new ProductDAO();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCondition("MD_ONE");
        productDTO.setProductNum(productNum); 
        ProductDTO DetailProductDTO = productDAO.selectOne(productDTO); // 해당 상품을 조회
       
        if (DetailProductDTO == null) {
        	System.out.println("[ERROR] 해당 상품을 찾을 수 없습니다.");
            ErrorUtils.sendError(response, HttpServletResponse.SC_NOT_FOUND, "404: 상품을 찾을 수 없습니다.");
            return null;
        }
        
        System.out.println("[INFO] 상품 정보 조회 성공: " + DetailProductDTO.toString());
        
        // 3. View에 상품 정보 전달
        request.setAttribute("product", DetailProductDTO);
        // boardNum 상품DTO에서 꺼내옴
        int boardNum = DetailProductDTO.getBoardNum();
        
        // 4. 게시글과 이미지 조회
        BoardDAO boardDAO = new BoardDAO(); // BoardDAO 객체 생성
        BoardDTO boardDTO = new BoardDTO(); // BoardDTO 객체 생성
        boardDTO.setBoardNum(boardNum); // 게시글 번호 설정
        boardDTO.setCondition("ONE_BOARD"); // 조회 조건 설정
        
        //사진 조회 요청
        ImageDAO imageDAO = new ImageDAO(); // ImageDAO 객체 생성
        ImageDTO imageDTO = new ImageDTO(); // ImageDTO 객체 생성
        imageDTO.setBoardNum(boardNum); // 사진 조회 요청
        
        // 5. DAO를 사용하여 게시글 조회 유효성 검사
        boardDTO = boardDAO.selectOne(boardDTO);
        if (boardDTO != null) {
            System.out.println("[INFO] 게시글 조회 성공: " + boardDTO.toString());
            request.setAttribute("board", boardDTO);
            
            // 6. 이미지 정보 조회
            ArrayList<ImageDTO> images = imageDAO.selectAll(imageDTO);

            // images가 null이 아닐 경우에만 request에 설정
            if (images != null && !images.isEmpty()) {
                request.setAttribute("images", images);
                System.out.println("[INFO] 이미지 정보 조회 성공, 이미지 수: " + images.size());
            } else {
              	System.out.println("[INFO] 이미지 정보가 없습니다.");
            }
        } else {
            System.err.println("[ERROR] 게시글을 찾을 수 없습니다.");
            ErrorUtils.sendError(response, HttpServletResponse.SC_NOT_FOUND, "404: 게시글을 찾을 수 없습니다.");
            return null;
        }

        // 7. 쿠키에서 기존에 본 상품 목록을 가져옴 (URL 디코딩 추가)
        Cookie[] cookies = request.getCookies();
        String viewedProducts = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("viewedProducts")) {
                	// URL 디코딩
                    try {
						viewedProducts = URLDecoder.decode(cookie.getValue(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						System.out.println("[ERROR] 쿠키 디코딩 중 오류 발생");
                        ErrorUtils.handleException(response, e, "쿠키 디코딩 오류");
                        return null;
					} 
                    break;
                }
            }
        }

        // 8. 쿠키에 본 상품 ID 추가
        if (viewedProducts == null || viewedProducts.isEmpty()) {
            // 첫 번째 상품을 본 경우
            viewedProducts = String.valueOf(productNum);
        } else {
            // 기존에 본 상품 목록이 있을 경우
            List<String> viewedProductList = new ArrayList<>(Arrays.asList(viewedProducts.split(",")));

            if (!viewedProductList.contains(String.valueOf(productNum))) {
                // 새로운 상품 ID를 추가 (중복 방지)
                viewedProductList.add(String.valueOf(productNum));

                // 최대 10개의 최근 본 상품 ID만 저장
                if (viewedProductList.size() > 10) {
                    viewedProductList.remove(0); // 가장 오래된 상품 제거
                }

                viewedProducts = String.join(",", viewedProductList);
            }
        }

        // 9. 업데이트된 본 상품 목록을 쿠키에 저장 (URL 인코딩 추가)
        String encodedViewedProducts = null;
        // URL 인코딩
		try {
			encodedViewedProducts = URLEncoder.encode(viewedProducts, "UTF-8");
	        Cookie cookie = new Cookie("viewedProducts", encodedViewedProducts);
	        cookie.setMaxAge(60 * 60 * 24 * 30); // 쿠키 만료 시간: 30일
	        cookie.setPath("/"); // 애플리케이션 전체에서 사용 가능하도록 설정
	        response.addCookie(cookie);
	        System.out.println("[INFO] 쿠키에 본 상품 목록 저장: " + viewedProducts);
		} catch (UnsupportedEncodingException e) {
            System.out.println("[ERROR] 쿠키 인코딩 중 오류 발생");
            ErrorUtils.handleException(response, e, "쿠키 인코딩 오류");
            return null;
		} 


        // 10. View로 포워딩
        ActionForward forward = new ActionForward();
        forward.setPath("productDetail.jsp"); // 상품 상세보기 페이지
        forward.setRedirect(false); // 포워딩 방식
        return forward;
    }
}