<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="header"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 임시 데이터 설정 (테스트 용도) -->

<!-- Header -->
<section id="header">
   <div class="container">
      <div class="row">
         <div class="col-12">

            <!-- Logo -->
            <h1>
               <a href="mainPage.do" id="logo">갈빵질빵</a>
            </h1>

            <!-- Nav -->
            <nav id="nav">
               <a href="listProduct.do">상품 보러가기</a> 
               <a href="searchStore.do">가게 보러가기</a> 
               <a href="listBoards.do?categoryName=normal">게시판 보러가기</a>
               <!-- 회원의 로그인 여부에 따라 로그인 버튼 혹은 로그아웃 버튼을 화면에 출력해줌 -->
               <c:if test="${empty memberPK}">
               <a href="signupPage.do">회원가입</a>
                  <a href="loginPage.do">로그인</a>
               </c:if>
               <c:if test="${not empty memberPK}">
                  <a href="checkPWPage.do">마이페이지</a>
                  <a href="logout.do">로그아웃</a>
               </c:if>
            </nav>
         </div>
      </div>
   </div>
</section>