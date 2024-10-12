<%@page import="model.dto.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>갈빵질빵 - 상품 자세히 보기</title>
<meta name="viewport"
   content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
<style>
.product-images img {
    max-width: 50%;  /* 이미지가 부모 요소의 너비를 넘지 않도록 제한 */
    height: auto;     /* 비율을 유지하며 자동으로 높이 조정 */
}

.gallery-image {
    width: 300px;     /* 갤러리 이미지의 고정 너비 */
    height: 200px;    /* 갤러리 이미지의 고정 높이 */
    object-fit: cover; /* 이미지를 잘라서 비율에 맞춤 */
}
</style>
</head>
<body>

   <!-- Header -->
   <custom:header />

   <section id="content">
      <div class="container">
         <div class="row">
            <div class="col-12">
               <!-- Main Content -->
               <section>
                  <h2>상품 상세 정보</h2>

                  <div class="product-detail">
                     <p>
                        <strong>상품 번호:</strong> ${product.productNum}
                     </p>
                     <p>
                        <strong>게시글 제목:</strong> ${product.boardTitle}
                     </p>

                     <div class="product-images">
                        <!-- 상품 이미지가 있는지 확인 -->
                           <c:choose>
                           <c:when test="${not empty product.productProfileWay}">         
                              <a href="viewProduct.do?productNum=${product.productNum}"
                                 class="bordered-feature-image"> <img
                                 src="${product.productProfileWay}"
                                 alt="${product.productName}" class="thumbnail-image" />
                              </a>
                           </c:when>

                           <c:otherwise>
                              <a href="viewProduct.do?productNum=${product.productNum}"
                                 class="bordered-feature-image"> 
                                 <img src="assets/images/default.png" 
                                 	  alt="${product.productName}"
                                      class="thumbnail-image"/>
                              </a>
                           </c:otherwise>
                        </c:choose>

                           <!-- 나머지 사진이 있는지 확인하고 나머지 사진 출력 -->
                           <div class="gallery">
                              <c:forEach var="image" items="${images}"
                                 varStatus="status">
                                 <!-- 첫 번째 사진을 제외하고 나머지 사진 출력 -->
                                    <img src="${image.imageWay}" alt="${product.productName} 추가 이미지"
                                       class="gallery-image"/>
                              </c:forEach>
                           </div>
                  
                     </div>

                     <p>
                        <strong>상품명:</strong> ${product.productName}
                     </p>
                     <p>
                        <strong>가격:</strong> ${product.productPrice}원
                     </p>
                     <p>
                        <strong>상세 설명:</strong>
                     </p>
                     <p>${product.boardContent}</p>


                     <!-- 구매 버튼 -->
                     <form action="addToCart" method="POST">
                        <input type="hidden" name="productNum"
                           value="${product.productNum}" />
                        <button type="submit" class="btn btn-primary">구매하기</button>
                     </form>
                     <!-- 관리자 전용 버튼 -->
                     <c:if test="${memberRole=='ADMIN'}">
                        <!-- Product delete button -->
                        <form
                           action="deleteProduct.do?productNum=${product.productNum}"
                           method="post">
                           <input type="hidden" name="productNum"
                              value="${product.productNum}" />
                           <button type="submit" class="btn btn-danger">상품 삭제</button>
                        </form>

                        <!-- Product update button -->
                        <a href="checkProductPage.do?productNum=${product.productNum}"
                           class="btn btn-secondary">
                           <button type="button">상품 수정</button>
                        </a>
                     </c:if>
                  </div>
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
</body>
</html>
