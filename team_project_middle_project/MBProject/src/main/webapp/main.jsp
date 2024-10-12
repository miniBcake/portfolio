<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>

<html>
   <head>
      <title>Main</title>
      <meta charset="utf-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
      <link rel="stylesheet" href="assets/css/main.css" />
   </head>
   <body>
      <div id="page-wrapper">
      
<!-- Header 커스터 태그 -->
<custom:header/>

         <!-- Features -->
            <section id="features">
               <div class="container">
                  <div class="row">
                     <div class="col-3 col-6-medium col-12-small">

                        <!-- Feature #1 -->
                           <section>
                              <a href="#" class="bordered-feature-image"><img src="images/pic01.jpg" alt="" /></a>
                              <h2>Welcome to Halcyonic</h2>
                              <p>
                                 This is <strong>Halcyonic</strong>, a free site template
                                 by <a href="http://twitter.com/ajlkn">AJ</a> for
                                 <a href="http://html5up.net">HTML5 UP</a>. It's responsive,
                                 built on HTML5 + CSS3, and includes 5 unique page layouts.
                              </p>
                           </section>

                     </div>
                     <div class="col-3 col-6-medium col-12-small">

                        <!-- Feature #2 -->
                           <section>
                              <a href="#" class="bordered-feature-image"><img src="images/pic02.jpg" alt="" /></a>
                              <h2>Responsive You Say?</h2>
                              <p>
                                 Yes! Halcyonic is built to be fully responsive so it looks great
                                 at every screen size, from desktops to tablets to mobile phones.
                              </p>
                           </section>

                     </div>
                     <div class="col-3 col-6-medium col-12-small">

                        <!-- Feature #3 -->
                           <section>
                              <a href="#" class="bordered-feature-image"><img src="images/pic03.jpg" alt="" /></a>
                              <h2>License Info</h2>
                              <p>
                                 Halcyonic is licensed under the <a href="http://html5up.net/license">CCA</a> license,
                                 so use it for personal/commercial use as much as you like (just keep
                                 our credits intact).
                              </p>
                           </section>

                     </div>
                     <div class="col-3 col-6-medium col-12-small">

                        <!-- Feature #4 -->
                           <section>
                              <a href="#" class="bordered-feature-image"><img src="images/pic04.jpg" alt="" /></a>
                              <h2>Volutpat etiam aliquam</h2>
                              <p>
                                 Duis neque nisi, dapibus sed mattis quis, rutrum accumsan sed. Suspendisse
                                 eu varius nibh. Suspendisse vitae magna mollis.
                              </p>
                           </section>

                     </div>
                  </div>
               </div>
            </section>

         <!-- Content -->
            <section id="content">
               <div class="container">
                  <div class="row aln-center">
                     <div class="col-4 col-12-medium">

                        <!-- Box #1 -->
                           <section>
                              <header>
                                 <h2>Who We Are</h2>
                                 <h3>A subheading about who we are</h3>
                              </header>
                              <a href="#" class="feature-image"><img src="images/pic05.jpg" alt="" /></a>
                              <p>
                                 Duis neque nisi, dapibus sed mattis quis, rutrum accumsan magna sed.
                                 Suspendisse eu varius nibh. Suspendisse vitae magna eget odio amet mollis
                                 justo facilisis quis. Sed sagittis amet lorem ipsum.
                              </p>
                           </section>

                     </div>
                     <div class="col-4 col-6-medium col-12-small">

                        <!-- Box #2 -->
                           <section>
                              <header>
                                 <h2>What We Do</h2>
                                 <h3>A subheading about what we do</h3>
                              </header>
                              <ul class="check-list">
                                 <li>Sed mattis quis rutrum accum</li>
                                 <li>Eu varius nibh suspendisse lorem</li>
                                 <li>Magna eget odio amet mollis justo</li>
                                 <li>Facilisis quis sagittis mauris</li>
                                 <li>Amet tellus gravida lorem ipsum</li>
                              </ul>
                           </section>

                     </div>
                     <div class="col-4 col-6-medium col-12-small">

                        <!-- Box #3 -->
                           <section>
                              <header>
                                 <h2>인기 게시물 TOP 3</h2>
                                 <h4>일반 게시판에서 작성된 인기 게시물</h4>
                              </header>
								<ul class="quote-list">
								   <c:forEach var="boardList" items="${hotBoardList}" varStatus="status">
								      <li>
								         <h2>Top ${status.index + 1}</h2> <!-- 인덱스 값을 1부터 표시 -->
								         <img src="images/pic06.jpg" alt="" />
								         <p>${boardList.boardTitle}</p>
								         <span>${boardList.memberNickname}</span>
								         <span>
								            <c:choose>
								               <c:when test="${fn:length(boardList.boardContent) > 30}">
								                  ${fn:substring(boardList.boardContent, 0, 30)}...
								               </c:when>
								               <c:otherwise>
								                  ${boardList.boardContent}
								               </c:otherwise>
								            </c:choose>
								         </span>
								         <button>
								            <a href="viewBoard.do?boardNum=${boardList.boardNum}"> 게시글 보러 가기</a>
								         </button>
								      </li>
								   </c:forEach>
								</ul>
                           </section>

                     </div>
                  </div>
               </div>
            </section>
            
         <!-- footer 커스텀 태그 -->
         <custom:footer/>


      </div>

      <!-- Scripts -->
         <script src="assets/js/jquery.min.js"></script>
         <script src="assets/js/browser.min.js"></script>
         <script src="assets/js/breakpoints.min.js"></script>
         <script src="assets/js/util.js"></script>
         <script src="assets/js/main.js"></script>

   </body>
</html>