<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"
   import="java.util.ArrayList, java.util.HashMap, java.util.Map, java.util.List"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>

<html>
<head>
<title>Two Column 2 - Halcyonic by HTML5 UP</title>
<meta charset="utf-8" />
<meta name="viewport"
   content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
<link rel="stylesheet" href="assets/css/pagination.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />

<!-- 좋아요 버튼 스타일 -->
<style>
.img-btn {
   font-size: 2rem; /* 하트 아이콘 크기 조절 */
   cursor: pointer; /* 마우스 포인터가 손 모양으로 변경 */
   background: none; /* 기본 배경 제거 */
   border: none; /* 기본 테두리 제거 */
   color: red; /* 기본 하트 색상 */
   transition: color 0.3s; /* 색상 변경에 부드러운 전환 추가 */
}

.img-btn:hover {
   color: darkred; /* 호버 시 색상 변경 */
}

.img-btn.liked {
   color: red; /* 좋아요 상태 시 색상 */
}

.img-btn.not-liked {
   color: grey; /* 좋아요 취소 상태 시 색상 */
}
</style>

</head>
<body class="subpage">
   <div id="page-wrapper">

      <custom:header />

      <!-- Content -->
      <section id="content">
         <div class="container">
            <div class="row">
               <div class="col-3 col-12-medium">

                  <!-- Sidebar -->
                  <section>
                     <header>
                        <h5>새로운 글 보러가기!</h5>
                     </header>
                     <ul class="link-list">
                        <li><a href="listBoards.do?categoryName=${board.boardCateName}">게시판으로 돌아가기</a></li>
                     </ul>
                  </section>
               </div>
               <div class="col-9 col-12-medium imp-medium">

                  <!-- Main Content -->
                  <section>
                     <header>
                        <h2>제목 : ${board.boardTitle}</h2>
                        <h3>작성 일자 : ${board.boardWriteDay}</h3>
                     </header>
 					  <div>
                         <!-- 사용자가 좋아요를 누른 경우 -->
                         <c:if test="${userLiked}">
                             <button class="img-btn liked" id="likebutton" data-board-id="${board.boardNum}">
                                 ❤️
                             </button>
                         </c:if>
                         
                         <!-- 사용자가 좋아요를 누르지 않은 경우 -->
                         <c:if test="${!userLiked}">
                             <button class="img-btn not-liked" id="likebutton" data-board-id="${board.boardNum}">
                                 🩶
                             </button>
                         </c:if>
                         
                         <span id="likeCount">${board.likeCnt}</span> 개의 좋아요
                     </div>

                     <!-- 사용자의 게시글인지 비교 -->
                     <div>
                        <c:if test="${memberNickName eq board.memberNickname}">
                              <button id="deleteButton" class="btn btn-primary btn-sm" onclick="deletePost()">게시글 삭제</button>
                              <button id="fixButton" class="btn btn-primary btn-sm" onclick="fixPost()">게시글 수정</button>
                        </c:if>
                        <c:if test="${memberNickName ne board.memberNickname}">
                           <p/>
                        </c:if>
                     </div>
                     <hr>
                     <br>
                     <div>
                        <c:choose>
                           <c:when test="${not empty images}">
                              <c:forEach items="${images}" var="image">
                                 <img src="${image.imageWay}" alt="게시글 이미지"
                                    style="max-width: 100%; height: auto;" />
                              </c:forEach>
                           </c:when>
                           <c:otherwise>
                              <p>이미지가 없습니다.</p>
                           </c:otherwise>
                        </c:choose>
                        <div>
                           <h2>${board.boardContent}</h2>
                        </div>
                        <hr>
                        <div>
                           <a href="listReply.do?boardNum=${board.boardNum}">
                              <button class="btn btn-primary btn-sm">댓글 작성하러 가기</button>
                           </a>
                           <p>${totalReplies}</p>
                        </div>
                     </div>
                  </section>
               </div>
            </div>
         </div>
      </section>

      <custom:footer />

   </div>

   <!-- Scripts -->
   <script src="assets/js/jquery.min.js"></script>
   <script src="assets/js/browser.min.js"></script>
   <script src="assets/js/breakpoints.min.js"></script>
   <script src="assets/js/util.js"></script>
   <script src="assets/js/main.js"></script>

   <script>
	   $(document).ready(function() {
	       $('.img-btn').click(function() {
	           const $this = $(this);
	           const boardNum = $this.data('board-id');
	
	           if ($this.hasClass('not-liked')) {
	               // 좋아요 상태로 변경
	               $this.text('❤️').removeClass('not-liked').addClass('liked');
	               $.ajax({
	                   url: '/MBProject/insertDeleteFavorite.do',
	                   type: 'POST',
	                   cache: false, // 캐시 방지
	                   data: { action: 'like', boardNum: boardNum },
	                   success: function(response) {
	                       $('#likeCount').text(response.newLikeCount);
	                   },
	                   error: function(xhr) {
	                       if (xhr.status === 401) {
	                           const response = JSON.parse(xhr.responseText);
	                           // SweetAlert로 알림 표시
	                           swal({
	                               title: "로그인 필요",
	                               text: response.message,
	                               type: "warning",
	                               showCancelButton: true,
	                               confirmButtonText: "로그인",
	                               cancelButtonText: "취소",
	                               closeOnConfirm: false
	                           }, function(isConfirm) {
	                               if (isConfirm) {
	                                   // 로그인 페이지로 리디렉션
	                                   window.location.href = response.redirect;
	                               }
	                           });
	                       } else {
	                           console.error('좋아요 상태 업데이트 실패');
	                       }
	                   }
	               });
	           } else {
	               // 좋아요 취소 상태로 변경
	               $this.text('🩶').removeClass('liked').addClass('not-liked');
	               $.ajax({
	                   url: '/MBProject/insertDeleteFavorite.do',
	                   type: 'POST',
	                   cache: false, // 캐시 방지
	                   data: { action: 'unlike', boardNum: boardNum },
	                   success: function(response) {
	                       $('#likeCount').text(response.newLikeCount);
	                   },
	                   error: function(xhr) {
	                       if (xhr.status === 401) {
	                           const response = JSON.parse(xhr.responseText);
	                           // SweetAlert로 알림 표시
	                           swal({
	                               title: "로그인 필요",
	                               text: response.message,
	                               type: "warning",
	                               showCancelButton: true,
	                               confirmButtonText: "로그인",
	                               cancelButtonText: "취소",
	                               closeOnConfirm: false
	                           }, function(isConfirm) {
	                               if (isConfirm) {
	                                   // 로그인 페이지로 리디렉션
	                                   window.location.href = response.redirect;
	                               }
	                           });
	                       } else {
	                           console.error('좋아요 상태 업데이트 실패');
	                       }
	                   }
	               });
	           }
	       });
	   });
		        
       // 수정과 삭제 버튼
       function fixPost() { // 수정 버튼을 눌렀을 때 실행 되는 함수
       window.location.href = 'checkBoardPage.do?boardNum=${board.boardNum}';
                         // checkBoardPage.do로 현재 보고있는 board.boardNumd을 보냄
      }
       
        // 삭제
       function deletePost(){
           window.location.href = 'deleteBoard.do?boardNum=${board.boardNum}';
        }
        
        // 이벤트 리스너 등록
        document.getElementById('likeButton').addEventListener('click', toggleLike); 
        document.getElementById('fixButton').addEventListener('click', fixPost);
        document.getElementById('deleteButton').addEventListener('click', deletePost);
    </script>

</body>
</html>