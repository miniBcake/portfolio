<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>갈빵질빵 - 메뉴 목록 보기</title>
<meta name="viewport"
   content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />

</head>
<body>
   <!-- Header -->
   <custom:header />

   <!-- Features -->
   <section id="features">
      <div class="container">

         <div class="row">
            <div class="col-3 col-6-medium col-12-small">
               <!-- Feature #1: 문구류 -->
               <section>
                  <a href="searchProductMD.do?productCateName=stationery"
                     class="bordered-feature-image"><img
                     src="assets/images/stationery.png" alt="문구류 이미지" /></a>
                  <h2>문구류</h2>
                  <p>연필, 볼펜, 필통, 지우개 등 다양한 문구류를 만나보세요.</p>
               </section>

            </div>
            <div class="col-3 col-6-medium col-12-small">

               <!-- Feature #2: 악세사리 -->
               <section>
                  <a href="searchProductMD.do?productCateName=accessory"
                     class="bordered-feature-image"><img
                     src="assets/images/accessory.png" alt="악세사리 이미지" /></a>
                  <h2>악세사리</h2>
                  <p>세련된 악세사리와 에어팟 케이스 등을 제공합니다.</p>
               </section>

            </div>
            <div class="col-3 col-6-medium col-12-small">

               <!-- Feature #3: 생활용품 -->
               <section>
                  <a href="searchProductMD.do?productCateName=daily"
                     class="bordered-feature-image"><img
                     src="assets/images/daily.png" alt="생활용품 이미지" /></a>
                  <h2>생활용품</h2>
                  <p>텀블러, 기타 생활 필수품을 만나보세요.</p>
               </section>

            </div>
            <div class="col-3 col-6-medium col-12-small">

               <!-- Feature #4: 의류 -->
               <section>
                  <a href="searchProductMD.do?productCateName=clothes"
                     class="bordered-feature-image"><img
                     src="assets/images/clothes.png" alt="의류 이미지" /></a>
                  <h2>의류</h2>
                  <p>다양한 의류와 모자를 준비했습니다.</p>
               </section>
            </div>
            <div class="col-3 col-6-medium col-12-small">
               <!-- Feature #5: 전자기기 및 관련 제품 -->
               <section>
                  <a href="searchProductMD.do?productCateName=electronics"
                     class="bordered-feature-image"><img
                     src="assets/images/electronics.png" alt="전자기기 이미지" /></a>
                  <h2>전자기기 및 관련 제품</h2>
                  <p>마우스 등 다양한 전자기기를 확인해보세요.</p>
               </section>

            </div>

            <!-- 가격바 필터 -->
            <form action="searchProductMD.do" method="GET">
               <div class="col-3 col-6-medium col-12-small">
                  <section>
                     <div class="filter-container">
                        <h2>가격 필터</h2>
                        <div class="input-group">
                           <label for="minPrice">최소 가격: </label> <input type="number"
                              id="minPrice" name="minPrice" placeholder="최소값 입력" min="0">
                        </div>
                        <div class="input-group">
                           <label for="maxPrice">최대 가격: </label> <input type="number"
                              id="maxPrice" name="maxPrice" placeholder="최대값 입력" min="1">
                        </div>
                     </div>
                  </section>
               </div>
               <br>
               <!-- 상품 검색 필터 -->
               <div class="col-3 col-6-medium col-12-small">
                  <section>
                     <select name="searchCategory">
                        <option value="title">제목으로 검색</option>
                        <option value="content">상품명으로 검색</option>
                     </select>
                     <div>
                        <input type="hidden" name="productCateName"
                           value="${product.productCateName}"> <input type="text"
                           placeholder="검색어를 입력하세요" name="searchKeyword">
                     </div>
                     <button type="submit" id="submitFilterBtn" value="검색">검색</button>
                  </section>
               </div>
            </form>
         </div>
      </div>
   </section>

   <p>이미지를 누르면 해당 카테고리로 이동합니다.</p>

   <section id="content">
      <div class="container">
         <div class="row">
            <div class="col-12">
               <!-- Main Content -->
               <section>
                  <h2>필터링된 상품 목록</h2>
                  <!-- 예시 사진 -->
                  <c:forEach var="product" items="${filteredProducts}">
                     <div class="product-item">
                        <c:choose>
                           <c:when test="${not empty product.productProfileWay}">         
                              <a href="viewProduct.do?productNum=${product.productNum}"
                                 class="bordered-feature-image"> <img
                                 src="${product.productProfileWay}"
                                 alt="${product.productName}" 
                                 style="width: 300px; height: 300px; object-fit: cover; border-radius: 8px; margin: 10px 0;"
                                 class="thumbnail-image" />
                              </a>
                           </c:when>

                           <c:otherwise>
                              <a href="viewProduct.do?productNum=${product.productNum}"
                                 class="bordered-feature-image"> <img
                                 src="assets/images/default.png" alt="${product.productName}"
                                 class="thumbnail-image" />
                              </a>
                           </c:otherwise>
                        </c:choose>
						<span>제목: ${product.boardTitle}</span><br>
                        <span>상품명: ${product.productName}</span><br> <span>가격:
                           ${product.productPrice}원</span>
                     </div>
                     <hr>
                  </c:forEach>

                  <!-- 필터링 페이지 네이션 -->
                  <!-- 이전 페이지 버튼 -->
                  <c:if test="${currentPage > 1}">
                     <a
                        href="?currentPage=${currentPage - 1}&productCateName=${productCateName}&minPrice=${minPrice}&maxPrice=${maxPrice}&searchCategory=${searchCategory}&searchKeyword=${searchKeyword}">&laquo;
                        이전</a>
                  </c:if>

                  <!-- 페이지 번호 -->
                  <c:forEach var="i" begin="1" end="${totalPages}">
                     <c:choose>
                        <c:when test="${i == currentPage}">
                           <strong>${i}</strong>
                        </c:when>
                        <c:otherwise>
                           <a
                              href="?currentPage=${i}&productCateName=${productCateName}&minPrice=${minPrice}&maxPrice=${maxPrice}&searchCategory=${searchCategory}&searchKeyword=${searchKeyword}">${i}</a>
                        </c:otherwise>
                     </c:choose>
                  </c:forEach>

                  <!-- 다음 페이지 버튼 -->
                  <c:if test="${currentPage < totalPages}">
                     <a
                        href="?currentPage=${currentPage + 1}&productCateName=${productCateName}&minPrice=${minPrice}&maxPrice=${maxPrice}&searchCategory=${searchCategory}&searchKeyword=${searchKeyword}">다음
                        &raquo;</a>
                  </c:if>
               </section>
            </div>
         </div>
      </div>
   </section>

   <!-- Footer -->
   <custom:footer />

   <script src="assets/js/jquery.min.js"></script>
   <script src="assets/js/browser.min.js"></script>
   <script src="assets/js/breakpoints.min.js"></script>
   <script src="assets/js/util.js"></script>
   <script src="assets/js/main.js"></script>
   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</body>
</html>
