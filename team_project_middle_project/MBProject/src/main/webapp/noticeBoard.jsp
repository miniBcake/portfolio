<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"
   import="java.util.ArrayList, java.util.HashMap, java.util.Map, java.util.List"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>

<html>
<head>
<title>문의 게시판</title>
<meta charset="utf-8" />
<meta name="viewport"
   content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
<link rel="stylesheet" href="assets/css/pagination.css">
</head>

<body class="subpage">
   <div id="page-wrapper">

      <!-- Header -->
      <custom:header />

      <!-- Content -->
      <section id="content">
         <div class="container">
            <div class="row">
               <div class="col-3 col-12-medium">
                  <!-- Sidebar -->
                  <section>
                     <header>
                        <h3>게시판 선택하기</h3>
                     </header>
                     <ul class="link-list">
                        <li><a href="listBoards.do?categoryName=normal"">일반 게시판</a></li>
                        <li><a href="listBoards.do?categoryName=request">문의 게시판</a></li>
                     </ul>
                  </section>
               </div>
               <div class="col-9 col-12-medium imp-medium">

                  <!-- 페이지 정보 -->
                  <section>
                     <header>
                        <h2>문의 게시판</h2>
                        <h3>문의 내용을 작성해주세요!</h3>
                        <a href="newBoardPage.do?categoryName=request">
                           <button class="btn btn-primary btn-sm">게시글 작성하러 가기</button>
                        </a>
                     </header>
                  </section>
                  <!-- 페이지 정보 설명 종료 -->
                  
                  <!-- 게시글 전체 출력 및 검색 기능 -->
                     <!-- 검색 필터 -->
                     <section id="search-section">
                         <header>
                             <h2>게시물 검색하기</h2>
                         </header>
                         <form action="searchBoards.do" method="get" id="searchForm">
                             <div class="search-container">
                                 <label for="content_filter">검색 필터</label> 
                                 <select id="content_filter" name="content_filter" class="filter-select">
                                     <option value="title" ${filter == 'title' ? 'selected' : ''}>제목으로 검색</option>
                                     <option value="writer" ${filter == 'writer' ? 'selected' : ''}>작성자로 검색</option>
                                     <option value="content" ${filter == 'content' ? 'selected' : ''}>내용으로 검색</option>
                                 </select>
                                 <label for="writeDay_filter"></label>
                                 <select id="writeDay_filter" name="writeDay_filter" class="filter-select">
                                     <option value="alltime" ${writeDayFilter == 'alltime' ? 'selected' : ''}>전체 검색</option>
                                     <option value="oneWeek" ${writeDayFilter == 'oneWeek' ? 'selected' : ''}>7일 전 작성</option>
                                     <option value="twoWeek" ${writeDayFilter == 'twoWeek' ? 'selected' : ''}>15일 전 작성</option>
                                     <option value="oneMonth" ${writeDayFilter == 'oneMonth' ? 'selected' : ''}>30일 전 작성</option>
                                 </select>
                                 <input type="search" id="keyword" name="keyword" placeholder="검색어 입력" value="${keyword}" class="search-input">
                                 <button type="submit" class="search-button">검색</button>
                             </div>
                           
                         </form>
                     <!-- 검색 필터 종료 -->
                     
                     <!-- 게시물 출력 시작 -->
                     <hr>
                     <br>
                     <div>
                        <c:if test="${empty boardList}">
                                    작성된 게시글이 없습니다..
                                </c:if>

                        <c:if test="${not empty boardList}">
                           <c:forEach var="board" items="${boardList}">
                                       <c:if test="${board.boardOpen == 'Y'}"> <!-- 공개 게시글 -->
                                           ${board.boardWriteDay}<br>
                                           제목 : ${board.boardTitle}<br>
                                 내용 : <c:choose>
                                       <c:when test="${fn:length(board.boardContent) > 30}">
                                             ${fn:substring(board.boardContent, 0, 30)}...
                                         </c:when>
                                       <c:otherwise>
                                             ${board.boardContent}
                                         </c:otherwise>
                                    </c:choose>
                                 <br>
                                           작성자 : ${board.memberNickname}<br>
                                           좋아요 : ${board.likeCnt}<br>
                                           카테고리 : ${board.boardCateName}<br>
                                 <button>
                                    <a href="viewBoard.do?boardNum=${board.boardNum}"> 게시글 정보 보기</a>
                                 </button>
                                 <br>
                                 <hr>
                                 <br>
                              </c:if>
                              
                              <c:if test="${board.boardOpen == 'N'}"> <!-- 비공개 게시글 -->
                                 ${board.boardWriteDay}<br>
                                           비공개 게시글 입니다<br>
                                           작성자 : ${board.memberNickname}<br>
                                 <br>
                                 <hr>
                                 <br>
                              </c:if>
                           </c:forEach>
                        </c:if>
                     </div>
                     <!-- 게시물 출력 종료 -->
                  </section>
                  <!-- 게시글 전체 출력 및 검색 기능 종료 -->

                  <!-- 페이지네이션 시작 -->
                  <section id="pagination">
                      <div class="pagination">
                          <!-- 이전 페이지 버튼 -->
                          <c:if test="${currentPage > 1}">
                              <a href="?page=${currentPage - 1}&categoryName=${boardCateName}&content_filter=${filter}&writeDay_filter=${writeDayFilter}&keyword=${keyword}">&laquo; 이전</a>
                          </c:if>
                  
                          <!-- 페이지 번호 -->
                          <c:set var="startPage" value="${currentPage - 5}"/>
                          <c:set var="endPage" value="${currentPage + 4}"/>
                  
                          <c:if test="${startPage < 1}">
                              <c:set var="startPage" value="1"/>
                          </c:if>
                          <c:if test="${endPage > totalPages}">
                              <c:set var="endPage" value="${totalPages}"/>
                          </c:if>
                  
                          <c:forEach var="i" begin="${startPage}" end="${endPage}">
                              <c:choose>
                                  <c:when test="${i == currentPage}">
                                      <strong>${i}</strong>
                                  </c:when>
                                  <c:otherwise>
                                      <a href="?page=${i}&categoryName=${boardCateName}&content_filter=${filter}&writeDay_filter=${writeDayFilter}&keyword=${keyword}">${i}</a>
                                  </c:otherwise>
                              </c:choose>
                          </c:forEach>
                  
                          <!-- 다음 페이지 버튼 -->
                          <c:if test="${currentPage < totalPages}">
                              <a href="?page=${currentPage + 1}&categoryName=${boardCateName}&content_filter=${filter}&writeDay_filter=${writeDayFilter}&keyword=${keyword}">다음 &raquo;</a>
                          </c:if>
                      </div>
                  </section>
                  <!-- 페이지네이션 종료 -->
                  
               </div>
            </div>
         </div>
      </section>


      <!-- Footer -->
      <custom:footer />

   </div>

   <!-- Scripts -->
   <script src="assets/js/jquery.min.js"></script>
   <script src="assets/js/browser.min.js"></script>
   <script src="assets/js/breakpoints.min.js"></script>
   <script src="assets/js/util.js"></script>
   <script src="assets/js/main.js"></script>

</body>
</html>