package controller.product;

import java.util.ArrayList;
import java.util.HashMap;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ErrorUtils;
import controller.common.PaginationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductCateDAO;
import model.dao.ProductDAO;
import model.dto.ProductCateDTO;
import model.dto.ProductDTO;

/**
 * SearchProductAction 클래스는 상품 필터링 및 검색을 처리하는 액션 클래스입니다.
 * 카테고리, 가격 범위, 검색 키워드 등을 기반으로 상품을 조회하고, 페이지네이션을 통해 결과를 나누어 출력합니다.
 */
public class SearchProductMDAction implements Action {

    private static final int PAGE_SIZE = 6; // 페이지당 항목 수 설정

    /**
     * 클라이언트의 요청을 처리하고, 검색 조건에 맞는 상품 목록을 조회하여 해당 JSP 페이지로 포워딩합니다.
     * 
     * @param request  클라이언트의 HTTP 요청 객체.
     *                 - "productCateName": 카테고리 이름.
     *                 - "currentPage": 현재 페이지 번호.
     *                 - "minPrice": 최소 가격 필터.
     *                 - "maxPrice": 최대 가격 필터.
     *                 - "searchCategory": 검색할 카테고리 (상품명, 내용 등).
     *                 - "searchKeyword": 검색할 키워드.
     * @param response 클라이언트에게 보낼 HTTP 응답 객체.
     * @return ActionForward JSP로의 포워딩 경로를 포함한 ActionForward 객체.
     */
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[INFO] SearchProductAction 실행 시작");

        try {
            // 1. 요청 파라미터 처리
            String productCateName = request.getParameter("productCateName"); // 카테고리 파라미터
            String currentPageParam = request.getParameter("currentPage"); // 현재 페이지
            String minPriceStr = request.getParameter("minPrice"); // 최소 가격
            String maxPriceStr = request.getParameter("maxPrice"); // 최대 가격
            String searchCategory = request.getParameter("searchCategory"); // 검색 카테고리
            String productKeyword = request.getParameter("searchKeyword"); // 검색 키워드
            
            int currentPage = (currentPageParam != null) ? Integer.parseInt(currentPageParam) : 1; // 기본 페이지는 1
            System.out.println("[INFO] 현재 페이지 번호: " + currentPage);

            // 로그: 파라미터 확인
            System.out.println("[INFO] 요청된 카테고리: " + productCateName);
            System.out.println("[INFO] 요청된 검색 키워드: " + productKeyword);
            System.out.println("[INFO] 검색 카테고리: " + searchCategory);
            System.out.println("[INFO] 현재 페이지 번호: " + currentPage);

            // 2. 필터를 담을 HashMap 생성
            HashMap<String, String> filterList = new HashMap<>();

            // 3. 카테고리 이름에 따른 필터 추가
            if (productCateName != null && !productCateName.isEmpty()) {
            	// 3-1. 카테고리PK값을 알기 위한 조회 
                ProductCateDTO productCateDTO = new ProductCateDTO();
                ProductCateDAO productCateDAO = new ProductCateDAO();
                ArrayList<ProductCateDTO> productCateList = productCateDAO.selectAll(productCateDTO);

                // 3-2. 카테고리 이름에 맞는 PK 찾기
                int productCateNum = -1;
                String productCateNumStr = null; // 문자열로 넣어주기 위한 형변환
                for (ProductCateDTO cateDTO : productCateList) {
                    if (cateDTO.getProductCateName().equals(productCateName)) {
                        productCateNum = cateDTO.getProductCateNum();
                        System.out.println("카테고리 이름: " + productCateName + "의 PK 값은: " + productCateNum);
                        productCateNumStr = String.valueOf(productCateNum); // 문자열로 형변환
                        System.out.println("productCateNum 문자열로 형변환: " + productCateNumStr);
                        break; // 원하는 값을 찾으면 반복문 종료
                    }
                }
                
                // 3-3. 만약 일치하는 카테고리를 찾지 못한 경우
                if (productCateNum == -1) {
                    System.out.println("[ERROR] 해당 카테고리 이름에 맞는 PK를 찾을 수 없습니다.");
                    ErrorUtils.sendError(response, HttpServletResponse.SC_BAD_REQUEST, "해당 카테고리 이름에 맞는 PK를 찾을 수 없습니다.");
                    return null;
                }
                // 3-4. 필터에 카테고리 추가
                filterList.put("GET_MD_CATEGORY", productCateNumStr);  // 카테고리 필터 추가
                System.out.println("[DEBUG] 카테고리 필터 추가: " + productCateNumStr);
            }

            // 4. 검색 키워드에 따른 필터 추가
            if (productKeyword != null && !productKeyword.isEmpty()) {
            	// 검색 카테고리에 따라 title 또는 content 필터 적용
                if (searchCategory.equals("title")) {
                    filterList.put("GET_MD_TITLE", productKeyword);  // 검색 상품명 필터 추가
                    System.out.println("[DEBUG] 상품명(제목) 필터 추가: " + productKeyword);
                } else if (searchCategory.equals("content")) {
                    filterList.put("GET_MD_KEYWORD", productKeyword);  // 검색 콘텐츠 필터 추가
                    System.out.println("[DEBUG] 콘텐츠(내용) 필터 추가: " + productKeyword);
                } else {
                    System.out.println("[DEBUG] 검색키워드 없이 검색버튼 - 전체조회 or 가격설정 조회");
                }
            }

            // 5. 가격 필터 추가
            try {
                // 최소 가격 필터 설정
                String minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? minPriceStr : "0";
                filterList.put("GET_MD_MINPRICE", minPrice); // 최소 가격 필터 추가
                System.out.println("[DEBUG] 최소 가격 필터 추가: " + minPrice);

                // 최대 가격 필터 설정
                String maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? maxPriceStr : String.valueOf(Integer.MAX_VALUE);
                filterList.put("GET_MD_MAXPRICE", maxPrice); // 최대 가격 필터 추가
                System.out.println("[DEBUG] 최대 가격 필터 추가: " + maxPrice);

            } catch (NumberFormatException e) {
                System.out.println("[ERROR] 가격 파라미터 형식 오류");
            }

            // 6. 상품 조회와 페이지네이션 설정
            ProductDTO productDTO = new ProductDTO();
            ProductDAO productDAO = new ProductDAO();   // ProductDAO 객체 생성

            // 7. 상품 수 카운트 조회 (필터 적용)
            ProductDTO productCountDTO = new ProductDTO();
            productCountDTO.setFilterList(filterList); // 필터 복사
            productCountDTO.setCondition("FILTER_CNT"); // 카운트 조건 설정
            ProductDTO productCountResult = productDAO.selectOne(productCountDTO); // 조건에 따른 상품 수 카운트 조회
            int totalCount = (productCountResult != null) ? productCountResult.getCnt() : 0; // 총 상품 수 설정
            // 로그: 총 상품 수 카운트 조회 완료
            System.out.println("[INFO] 총 상품 수 조회 완료 - 총 " + totalCount + "개 상품");
            
            // 8. 페이지네이션 설정
            PaginationUtils.setPagination(currentPage, PAGE_SIZE, totalCount, productDTO); // 페이지네이션 설정
            
            // 9. 상품 조회를 위한 필터링 설정 
            productDTO.setFilterList(filterList); 		// 필터를 DTO에 설정
            
            // 10. 상품 목록 조회
            ArrayList<ProductDTO> productList = productDAO.selectAll(productDTO); // 조건에 따른 상품 목록 조회
            // 로그: 상품 목록 조회 완료
            System.out.println("[INFO] 상품 목록 조회 완료 - 총 " + productList.size() + "개 상품");
            
            // 11. 페이지네이션 검색설정을 특정하기 위한 파라미터 값 보내주기
            request.setAttribute("productCateName", productCateName ); // 카테고리 파라미터 보내주기기      
            request.setAttribute("minPrice", minPriceStr); // 최소가격값 파라미터 보내주기
            request.setAttribute("maxPrice", maxPriceStr); // 최대가격값 파라미터 보내주기
            request.setAttribute("searchKeyword", productKeyword); // 검색 키워드 파라미터 보내주기
            request.setAttribute("searchCategory", searchCategory); // 검색 카테고리 파라미터 보내주기
            
            // 12. 결과 처리 및 뷰 전달
            int totalPages = PaginationUtils.calTotalPages(totalCount, PAGE_SIZE); // 총 페이지 수 계산
            request.setAttribute("filteredProducts", productList); // 상품 목록 전달
            request.setAttribute("currentPage", currentPage); // 현재 페이지 전달
            request.setAttribute("totalPages", totalPages); // 총 페이지 수 전달

            // 9. ActionForward 설정
            ActionForward forward = new ActionForward();
            forward.setRedirect(false); // 포워드 방식 설정
            forward.setPath("/productFilterList.jsp"); // JSP 페이지 경로 설정

            System.out.println("[INFO] SearchProductAction 실행 종료");
            return forward;

        } catch (Exception e) {
            System.out.println("[ERROR] 상품 검색 중 오류 발생: " + e.getMessage());
            ErrorUtils.handleException(response, e, "상품 검색 중 서버 오류");
            return null;
        }
    }
}
