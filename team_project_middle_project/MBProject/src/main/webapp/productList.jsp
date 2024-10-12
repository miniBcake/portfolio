<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>갈빵질빵 - 상품 목록 페이지</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
</head>
<body>
	<div id="page-wrapper">

		<!-- Header -->
		<custom:header />

		<!-- Features -->
		<section id="features">
			<div class="container">
						
				<div class="row">
					<div class="col-3 col-6-medium col-12-small">
						<!-- Feature #1: 문구류 -->
						<section>
							<a href="searchProductMD.do?productCateName=stationery" class="bordered-feature-image"><img
								src="assets/images/stationery.png" alt="문구류 이미지" /></a>
							<h2>문구류</h2>
							<p>연필, 볼펜, 필통, 지우개 등 다양한 문구류를 만나보세요.</p>
						</section>

					</div>
					<div class="col-3 col-6-medium col-12-small">

						<!-- Feature #2: 악세사리 -->
						<section>
							<a href="searchProductMD.do?productCateName=accessory" class="bordered-feature-image"><img
								src="assets/images/accessory.png" alt="악세사리 이미지" /></a>
							<h2>악세사리</h2>
							<p>세련된 악세사리와 에어팟 케이스 등을 제공합니다.</p>
						</section>

					</div>
					<div class="col-3 col-6-medium col-12-small">

						<!-- Feature #3: 생활용품 -->
						<section>
							<a href="searchProductMD.do?productCateName=daily" class="bordered-feature-image"><img
								src="assets/images/daily.png" alt="생활용품 이미지" /></a>
							<h2>생활용품</h2>
							<p>텀블러, 기타 생활 필수품을 만나보세요.</p>
						</section>

					</div>
					<div class="col-3 col-6-medium col-12-small">

						<!-- Feature #4: 의류 -->
						<section>
							<a href="searchProductMD.do?productCateName=clothes" class="bordered-feature-image"><img
								src="assets/images/clothes.png" alt="의류 이미지" /></a>
							<h2>의류</h2>
							<p>다양한 의류와 모자를 준비했습니다.</p>
						</section>
					</div>
					<div class="col-3 col-6-medium col-12-small">
						<!-- Feature #5: 전자기기 및 관련 제품 -->
						<section>
							<a href="searchProductMD.do?productCateName=electronics" class="bordered-feature-image"><img
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
									<input type="text" placeholder="검색어를 입력하세요"
										name="searchKeyWord">
								</div>
								<button type="submit" id="submitFilterBtn" value="검색">검색</button>
							</section>
						</div>
					</form>
				</div>
			</div>
		</section>
		
					<p>이미지를 누르면 해당 카테고리로 이동합니다.</p>
		

		<!-- Content -->
	      <section id="content">
	         <div class="container">
	            <div class="row aln-center">
	               <div class="col-4 col-12-medium">
	                  <!-- Box #1: 신상품 -->
	                  <section>
	                     <header>
	                        <h2>신상품</h2>
	                        <h3>가장 최근에 출시된 Top5</h3>
	                     </header>
	                     <c:choose>
	                     <c:when test="${not empty newProducts}">
	                     <a href="#" class="feature-image"><img src="images/pic05.jpg"
	                        alt="신상품 이미지" /></a>
	                     <ul class="quote-list">
	                        <c:forEach var="product" items="${newProducts}">
	                           <li><img src="${product.ProductProfileWay }" alt="상품 이미지" 
	                          style="width: 300px; height: auto; object-fit: cover; border-radius: 8px; margin: 10px 0;"/>
	                              <p>${product.productName}</p> <span>${product.productPrice}</span>
                              </li>
	                           <hr>
	                        </c:forEach>
	                     </ul>
	                     </c:when>
	                     <c:otherwise>
	                     <h1>서비스 준비중입니다.</h1>
	                     </c:otherwise>
	                     </c:choose>
	                  </section>	
	               </div>

					<div class="col-4 col-6-medium col-12-small">

	                  <!-- Box #2: 최근에 본 상품 -->
	                  <section>
	                     <header>
	                        <h2>최근에 본 상품</h2>
	                        <h3>가장 최근에 본 상품</h3>
	                     </header>
	                     <c:choose>
	                     <c:when test="${not empty resentProducts}">
	                     <ul class="quote-list">
	                        <c:forEach var="product" items="${resentProducts}">
	                           <li><img src="${product.productProfileWay}" alt="상품 이미지" 
	                           style="width: 300px; height: 300px; object-fit: cover; border-radius: 8px; margin: 10px 0;"/>
                              </li>
	                              <p>${product.boardTitle}</p> <p>${product.productName}</p> <span>${product.productPrice}원</span>
	                              <button><a href="viewProduct.do?productNum=${product.productNum}"> 상품 보러 가기</a></button>                              
	                           <hr>
	                        </c:forEach>
	                     </ul>
	                     </c:when>
	                     <c:otherwise>
	                     <h1>아직 본 상품이 없습니다.</h1>
	                     </c:otherwise>
	                     </c:choose>
	                  </section>
	
	               </div>
	               <div class="col-4 col-6-medium col-12-small">
	                  <!-- Box #3: 추천 상품 -->
                  <section>
                     <header>
                        <h2>추천 상품</h2>
                        <h3>고객 맞춤형 상품 추천 Top3</h3>
                     </header>
                     <ul class="quote-list">
                        <c:forEach var="product" items="${recommendedProducts}">
                           <li><img src="${product.productProfileWay}" alt="상품 이미지" 
                           style="width: 300px; height: 300px; object-fit: cover; border-radius: 8px; margin: 10px 0;"/>
                           </li>                           
                              <p>${product.boardTitle}</p> <p>${product.productName}</p> <span>${product.productPrice}원</span>
                              <button><a href="viewProduct.do?productNum=${product.productNum}"> 상품 보러 가기</a></button>
                           <hr>
                        </c:forEach>
                     </ul>
                  </section>

               </div>
            </div>
         </div>
      </section>


		<!-- Footer -->
		<custom:footer />

		<!-- Scripts -->
		<script src="assets/js/jquery.min.js"></script>
		<script src="assets/js/browser.min.js"></script>
		<script src="assets/js/breakpoints.min.js"></script>
		<script src="assets/js/util.js"></script>
		<script src="assets/js/main.js"></script>
		<script type="text/javascript">
		document.getElementById('minPrice').addEventListener('input', function() {
		    var minPriceValue = parseInt(this.value) || 0;
		    document.getElementById('maxPrice').min = minPriceValue + 1;
		});
		</script>

	</div>
</body>
</html>
