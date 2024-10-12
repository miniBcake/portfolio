package controller.product;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ErrorUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductCateDAO;
import model.dao.ProductDAO;
import model.dto.ProductCateDTO;
import model.dto.ProductDTO;
/**
 * ListProductMDAction 클래스는 최근 본 상품 목록을 조회하고,
 * 사용자가 많이 본 카테고리에 따른 추천 상품을 제공하는 액션 클래스입니다.
 * 
 * 이 클래스는 사용자 쿠키에서 최근 본 상품을 가져오고, 해당 상품 목록을 조회한 후
 * 특정 카테고리 내 추천 상품을 보여주기 위해 상품 목록을 필터링합니다.
 */
public class ListProductMDAction implements Action {
	/**
     * 클라이언트의 요청을 처리하고, 최근 본 상품 목록 및 추천 상품을 조회하여 상품 목록 페이지로 포워딩합니다.
     * 
     * @param request  클라이언트의 HTTP 요청 객체. 
     *                 - 쿠키에서 "viewedProducts" 값을 가져옵니다.
     * @param response 클라이언트의 HTTP 응답 객체. 에러 발생 시 상태 코드와 함께 에러 메시지를 전달합니다.
     * @return ActionForward 상품 목록 페이지로 포워딩 경로를 포함한 ActionForward 객체.
     */
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[INFO] ListProductMDAction 시작");

        // 1. 쿠키에서 본 상품 목록을 가져옴 (URL 디코딩 추가)
        // 쿠키를 통해 사용자들이 본 상품 정보를 저장하고, 그 값을 읽어들인다.
        Cookie[] cookies = request.getCookies();
        String viewedProducts = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("viewedProducts")) {
                    try {
                        // URL 인코딩된 값을 디코딩하여 쿠키에서 저장된 상품 목록을 가져옴
                        viewedProducts = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        System.out.println("[INFO] 쿠키에서 가져온 viewedProducts 값: " + viewedProducts);
                    } catch (IOException e) {
                        System.out.println("[ERROR] 쿠키 디코딩 오류");
                        ErrorUtils.handleException(response, e, "쿠키 디코딩 오류");
                        return null;
                    }
                    break;
                }
            }
        }

        // 2. 조회된 상품 목록 출력
        // 쿠키에서 가져온 상품 목록을 ','로 구분하여 리스트에 저장
        List<String> viewedProductList = new ArrayList<>();
        if (viewedProducts != null && !viewedProducts.isEmpty()) {
            viewedProductList = Arrays.asList(viewedProducts.split(","));
        }

        System.out.println("[INFO] 조회된 상품 목록 (viewedProductList): " + viewedProductList);

        // 3. 상품 목록 조회 로직
        ProductDAO productDAO = new ProductDAO();  // 상품 DAO 객체 생성
        ArrayList<ProductDTO> resentProducts = new ArrayList<>();  // 최근 본 상품 목록
        Map<Integer, Integer> categoryCount = new HashMap<>(); // 카테고리별 상품 수 저장

        // 쿠키에서 가져온 각 상품 ID를 기반으로 상품을 조회하고 카테고리별로 갯수 카운팅
        for (String productNumStr : viewedProductList) {
            try {
            	int productNum = Integer.parseInt(productNumStr);
                
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProductNum(productNum);
                productDTO.setCondition("MD_ONE");
                ProductDTO product = productDAO.selectOne(productDTO); // 상품 정보 조회
                
                if (product != null) {
                	 // 최근 본 상품에 추가하기 전에, 리스트 크기 제한
                    if (resentProducts.size() >= 10) {
                        resentProducts.remove(0);  // 가장 오래된 항목을 제거
                        System.out.println("[INFO] 최근 본 상품 10개 초과로 가장 오래된 항목 삭제");
                    }
                    
                    // 최근에 본 상품을 역순으로 저장 -> 클라이언트에서 사용자경험 증대
                    resentProducts.add(0, product);  
                    System.out.println("[INFO] 최근 본 상품 추가: " + product.getProductName());

                    // 카테고리 번호별로 상품 수 계산 
                    int categoryNum = product.getProductCateNum();
                    categoryCount.put(categoryNum, categoryCount.getOrDefault(categoryNum, 0) + 1);
                }
            } catch (Exception e) {
                System.out.println("[ERROR] 상품 번호 처리 중 오류: " + productNumStr);
                ErrorUtils.handleException(response, e, "상품 번호 처리 중 오류");
                return null;
            }
        }

        System.out.println("[INFO] 최근에 본 상품 목록: " + resentProducts);
        System.out.println("[INFO] 카테고리별로 본 상품 갯수: " + categoryCount);

        // 4. 가장 많이 본 카테고리를 찾기
        // categoryCount Map에 저장된 카테고리별 조회 횟수를 기준으로 가장 많이 조회된 카테고리를 찾는다.
        // Map.Entry<Integer, Integer>는 카테고리 번호(Integer)와 해당 카테고리의 조회 수(Integer)를 의미.
        // max() 메서드는 가장 큰 값을 가진 카테고리를 찾는다.
        Optional<Map.Entry<Integer, Integer>> mostViewedCategoryOpt = categoryCount.entrySet().stream()
        	    .max(Map.Entry.comparingByValue());	
        
        // Optional이 비어있지 않으면 (가장 많이 본 카테고리가 존재할 경우)
        if (mostViewedCategoryOpt.isPresent()) {
            // 가장 많이 본 카테고리의 key 값을 가져옴 (카테고리 번호)
            int mostViewedCategory = mostViewedCategoryOpt.get().getKey();
            System.out.println("[INFO] 가장 많이 본 카테고리: " + mostViewedCategory);

            // 5. 해당 카테고리의 추천 상품 조회
            // 가장 많이 본 카테고리에 속하는 상품을 필터링하여 추천 상품으로 설정
            // HashMap<String, String>을 사용하여 필터를 설정함
            HashMap<String, String> filterList = new HashMap<>();
            
            // 카테고리 번호를 문자열로 변환하여 필터에 추가
            String mostViewedCategoryStr = String.valueOf(mostViewedCategory);
            filterList.put("GET_MD_CATEGORY", mostViewedCategoryStr);  // 카테고리 필터 추가
            System.out.println("[INFO] 추천 상품 필터 설정 - 카테고리 번호: " + mostViewedCategoryStr);

            // ProductDTO 객체 생성 후 필터 설정
            ProductDTO productDTO = new ProductDTO();
            productDTO.setFilterList(filterList);  // 필터를 설정하여 해당 카테고리의 상품만 검색

            // 페이지네이션 설정 (추천 상품 수를 3개로 제한)
            // startNum은 시작 인덱스, endNum은 가져올 최대 인덱스
            productDTO.setStartNum(1); // 추천 상품의 시작 번호
            productDTO.setEndNum(3);  // 추천 상품의 마지막 번호 (3개로 제한)
            System.out.println("[INFO] 페이지네이션 설정 - 시작 번호: 1, 끝 번호: 3");

            // 필터링된 상품 목록을 조회하여 추천 상품 리스트를 가져옴
            try {
                ArrayList<ProductDTO> recommendedProducts = productDAO.selectAll(productDTO); // 상품 목록 조회
                System.out.println("[INFO] 추천 상품 목록: " + recommendedProducts);
                // 추천 상품 리스트를 request 객체에 저장하여 View로 전달
                request.setAttribute("recommendedProducts", recommendedProducts);
            } catch (Exception e) {
                System.out.println("[ERROR] 추천 상품 목록 조회 중 오류 발생");
                ErrorUtils.handleException(response, e, "추천 상품 목록 조회 중 오류");
                return null;
            }
        } else {
            // 만약 가장 많이 본 카테고리가 없을 경우
            System.out.println("[INFO] 가장 많이 본 카테고리가 없습니다.");            
            // 추천할 상품이 없을 때, 빈 리스트를 생성하여 request 객체에 전달
            request.setAttribute("recommendedProducts", new ArrayList<ProductDTO>()); // 추천 상품이 없을 경우 빈 리스트 전달
        }
        
        // 6. BoardCateDAO를 사용하여 카테고리 정보 조회
        ProductCateDAO productCateDAO = new ProductCateDAO();
        ArrayList<ProductCateDTO> productCategories; 
        try {
            productCategories = productCateDAO.selectAll(new ProductCateDTO()); // 전체 카테고리 목록 조회
        } catch (Exception e) {
            System.out.println("[ERROR] 카테고리 조회 중 오류 발생");
            ErrorUtils.handleException(response, e, "카테고리 조회 중 오류");
            return null;
        }
        System.out.println("[INFO] 조회된 전체 상품 카테고리 목록: " + productCategories);


        // 7. View에 상품 정보 전달
        // 최근에 본 상품과 추천 상품을 View에 전달하여 화면에 표시할 수 있도록 설정
        request.setAttribute("resentProducts", resentProducts); // 최근에 본 상품 전달
        request.setAttribute("productCategories", productCategories); // 카테고리 정보 전달
        
        // 8. View로 포워딩
        // 상품 목록 페이지로 포워딩하며, forward 방식으로 데이터를 전달
        ActionForward forward = new ActionForward();
        forward.setPath("productList.jsp"); // 상품 목록 페이지
        forward.setRedirect(false); // 포워딩 방식 (forward)
        System.out.println("[INFO] ListProductMDAction 완료, productList.jsp로 포워딩");
        return forward;
    }
}
