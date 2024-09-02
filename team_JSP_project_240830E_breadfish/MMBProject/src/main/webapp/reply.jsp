<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<!-- Favicon -->
<link href="img/favicon.ico" rel="icon">
<meta charset="utf-8">
<title>댓글 작성</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
   href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
   rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link rel="stylesheet"
   href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
<link
   href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
   rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css"
   rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">
</head>

<body>
   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
   <!-- Spinner Start -->
   <div id="spinner"
      class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
      <div class="spinner-grow text-primary" role="status"></div>
   </div>
   <!-- Spinner End -->


   <!-- 모든 페이지 공통으로 쓰는 상단 화면 -->
   <div class="container-fluid fixed-top">
      <div class="container topbar bg-primary d-none d-lg-block">
         <div class="d-flex justify-content-between">
            <div class="top-info ps-2">
               <small class="me-3"><i
                  class="fas fa-map-marker-alt me-2 text-secondary"></i> <a href="#"
                  class="text-white">123 Street, New York</a></small> <small class="me-3"><i
                  class="fas fa-envelope me-2 text-secondary"></i><a href="#"
                  class="text-white">Email@Example.com</a></small>
            </div>
         </div>
      </div>
      <div class="container px-0">
         <nav class="navbar navbar-light bg-white navbar-expand-xl">
            <h1 class="fw-bold text-primary m-0">
               갈<span class="text-secondary">빵</span>질<span class="text-secondary">빵</span>
            </h1>
            <button class="navbar-toggler py-2 px-3" type="button"
               data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
               <span class="fa fa-bars text-primary"></span>
            </button>
            <div class="collapse navbar-collapse bg-white" id="navbarCollapse">
               <div class="navbar-nav mx-auto">
                  <a href="main.jsp" class="nav-item nav-link active">메인 메뉴</a>
                  <div class="nav-item dropdown">
                     <a href="#" class="nav-link dropdown-toggle"
                        data-bs-toggle="dropdown">커뮤니티</a>
                     <div class="dropdown-menu m-0 bg-secondary rounded-0">
                        <a href="boardlist.jsp" class="dropdown-item">이웃 새글</a> <a
                           href="boardwrite.jsp" class="dropdown-item">게시글 작성</a> <a
                           href="testimonial.html" class="dropdown-item">이번주 인기 가게</a> <a
                           href="404.html" class="dropdown-item">404 Page</a>
                     </div>
                  </div>
                  <a href="contact.jsp" class="nav-item nav-link">Contact</a>
               </div>
               <div class="d-flex m-3 me-0">
                  <button
                     class="btn-search btn border border-secondary btn-md-square rounded-circle bg-white me-4"
                     data-bs-toggle="modal" data-bs-target="#searchModal">
                     <i class="fas fa-search text-primary"></i>
                  </button>
                  <!-- 로그인 여부에 따라 버튼 표시 -->
                  <c:choose>
                     <c:when test="${not empty sessionScope.member.memberPK}">
                        <!-- 로그인 상태 -->
                        <a href="logout.do"
                           class="btn border border-secondary rounded-pill px-2 text-primary me-4">Logout</a>
                     </c:when>
                     <c:otherwise>
                        <!-- 로그아웃 상태 -->
                        <a href="login.do"
                           class="btn border border-secondary rounded-pill px-2 text-primary me-4">Login</a>
                     </c:otherwise>
                  </c:choose>
                  <a href="myPage.do" class="my-auto"> <i
                     class="fas fa-user fa-2x"></i></a>
               </div>
            </div>
         </nav>
      </div>
   </div>
   <!-- 모든 페이지 공통으로 쓰는 상단 화면 -->


   <!-- Modal Search Start -->
   <div class="modal fade" id="searchModal" tabindex="-1"
      aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-fullscreen">
         <div class="modal-content rounded-0">
            <div class="modal-header">
               <h5 class="modal-title" id="exampleModalLabel">Search by
                  keyword</h5>
               <button type="button" class="btn-close" data-bs-dismiss="modal"
                  aria-label="Close"></button>
            </div>
            <div class="modal-body d-flex align-items-center">
               <div class="input-group w-75 mx-auto d-flex">
                  <input type="search" class="form-control p-3"
                     placeholder="keywords" aria-describedby="search-icon-1">
                  <span id="search-icon-1" class="input-group-text p-3"><i
                     class="fa fa-search"></i></span>
               </div>
            </div>
         </div>
      </div>
   </div>
   <!-- Modal Search End -->


   <!-- Single Page Header start -->
   <div class="container-fluid page-header py-5">
      <h1 class="text-center text-white display-6">댓글 작성</h1>
      <ol class="breadcrumb justify-content-center mb-0">
         <li class="breadcrumb-item"><a href="board.jsp">게시글로 돌아가기</a></li>
      </ol>
   </div>
   <!-- Single Page Header End -->


   <!-- Checkout Page Start -->
   <div class="container-fluid py-5">
      <div class="container py-5">
         <h3 class="mb-4">댓글 목록</h3>
         <br>

         <!-- 댓글 목록을 표시할 영역 -->
         <div id="commentContainer">
            <!-- 예시 댓글 데이터 시작 -->
            <div class="comment" id="comment-1">
               <!-- 댓글 작성자와 내용 -->
               <p>
                  <div class="small">
                     <span class="fa fa-user text-primary"></span> <span class="ms-2">닉네임입니다</span>
                  </div>
               </p>
               <p id="comment-text-1">댓글 내용입니다.</p>

               <!-- 댓글 수정 및 삭제 버튼 -->
               <button class="btn btn-primary btn-sm"
                  onclick="editComment(1, '댓글 내용입니다.')">수정하기</button>
               <button class="btn btn-danger btn-sm" onclick="deleteComment(1)">삭제하기</button>

               <!-- 댓글 수정 박스, 기본적으로 숨김 처리 -->
               <div id="edit-comment-box-1" style="display:none;">
                  <textarea class="form-control" id="edit-comment-text-1" style="height: 150px;">댓글 내용입니다.</textarea>
                  <button class="btn btn-primary mt-2" onclick="saveEditComment(1)">수정 완료</button>
                  <button class="btn btn-secondary mt-2" onclick="cancelEditComment(1)">취소</button>
               </div>
               <hr>
            </div>
            <!-- 예시 댓글 데이터 종료 -->

            <!-- 추가 댓글은 이와 같은 형식으로 반복됨 -->
         </div>
         <!-- 댓글 목록 종료 -->


         <!-- 페이지네이션 시작 -->
         <div class="col-12">
            <div class="pagination d-flex justify-content-center mt-5">
               <!-- 이전 페이지 버튼 -->
               <c:if test="${currentPage > 1}">
                  <a href="?page=${currentPage - 1}" class="rounded">&laquo; 이전</a>
               </c:if>

               <!-- 페이지 번호 -->
               <c:forEach var="i" begin="1" end="${totalPages}">
                  <c:choose>
                     <c:when test="${i == currentPage}">
                        <strong class="rounded">${i}</strong>
                     </c:when>
                     <c:otherwise>
                        <a href="?page=${i}" class="rounded">${i}</a>
                     </c:otherwise>
                  </c:choose>
               </c:forEach>

               <!-- 다음 페이지 버튼 -->
               <c:if test="${currentPage < totalPages}">
                  <a href="?page=${currentPage + 1}" class="rounded">다음 &raquo;</a>
               </c:if>
            </div>
         </div>
         <!-- 페이지네이션 끝 -->


         <!-- 댓글 작성 폼 -->
		<form action="addReply.do" method="post">
		    <div class="row g-5">
		        <div class="col-sm-12">
		            <div class="form-floating">
		                <textarea class="form-control" name="newReplyContents" id="replyContent" style="height: 150px;"></textarea>
		                <label for="replyContent">댓글 내용</label>
		                <button class="btn btn-primary w-100 py-3" type="submit">댓글 작성</button>
		            </div>
		        </div>
		    </div>
		    <!-- 게시글 번호를 전달하기 위한 hidden input 추가 -->
		    <input type="hidden" name="boardNum" value="${boardNum}">
		</form>
      </div>
   </div>
   <!-- Checkout Page End -->


   <!-- Back to Top -->
   <a href="#"
      class="btn btn-lg btn-primary btn-lg-square rounded-circle back-to-top"><i
      class="bi bi-arrow-up"></i></a>

   <!-- JavaScript Libraries -->
   <script src="lib/wow/wow.min.js"></script>
   <script src="lib/easing/easing.min.js"></script>
   <script src="lib/waypoints/waypoints.min.js"></script>
   <script src="lib/owlcarousel/owl.carousel.min.js"></script>
   <script src="lib/counterup/counterup.min.js"></script>
   <script src="lib/lightbox/js/lightbox.min.js"></script>

   <!-- Template Javascript -->
   <script src="js/main.js"></script>

   <!-- 댓글 수정 및 삭제 관련 JavaScript -->
   <script>
      // 댓글 수정하기 버튼 클릭 시 호출되는 함수
      function editComment(commentId, currentText) {
         // 댓글 내용 영역과 수정 박스를 찾음
         var commentTextElement = document.getElementById("comment-text-" + commentId);
         var editCommentBox = document.getElementById("edit-comment-box-" + commentId);
         
         // 댓글 내용 영역을 숨기고, 수정 박스를 표시
         commentTextElement.style.display = 'none';
         editCommentBox.style.display = 'block';

         // 수정 박스의 textarea에 현재 댓글 내용을 설정
         document.getElementById("edit-comment-text-" + commentId).value = currentText;
         
         // 수정 및 삭제 버튼을 숨김
         document.querySelector("#comment-" + commentId + " .btn-primary").style.display = 'none';
         document.querySelector("#comment-" + commentId + " .btn-danger").style.display = 'none';
      }

      // 댓글 수정 완료 버튼 클릭 시 호출되는 함수
      function saveEditComment(commentId) {
         // 수정된 댓글 내용 가져오기
         var newCommentText = document.getElementById("edit-comment-text-" + commentId).value;
         
         // 댓글 내용 영역과 수정 박스를 찾음
         var commentTextElement = document.getElementById("comment-text-" + commentId);
         var editCommentBox = document.getElementById("edit-comment-box-" + commentId);

         // 서버에 수정된 내용을 전송 (여기서는 예시로 콘솔에 출력)
         console.log("수정된 댓글 내용:", newCommentText);

         // 댓글 내용 영역에 수정된 내용 표시
         commentTextElement.innerText = newCommentText;
         
         // 댓글 내용 영역을 표시하고, 수정 박스를 숨김
         commentTextElement.style.display = 'block';
         editCommentBox.style.display = 'none';

         // 수정 및 삭제 버튼을 다시 표시
         document.querySelector("#comment-" + commentId + " .btn-primary").style.display = 'inline-block';
         document.querySelector("#comment-" + commentId + " .btn-danger").style.display = 'inline-block';
      }

      // 댓글 수정 취소 버튼 클릭 시 호출되는 함수
      function cancelEditComment(commentId) {
         // 댓글 내용 영역과 수정 박스를 찾음
         var commentTextElement = document.getElementById("comment-text-" + commentId);
         var editCommentBox = document.getElementById("edit-comment-box-" + commentId);

         // 댓글 내용 영역을 표시하고, 수정 박스를 숨김
         commentTextElement.style.display = 'block';
         editCommentBox.style.display = 'none';

         // 수정 및 삭제 버튼을 다시 표시
         document.querySelector("#comment-" + commentId + " .btn-primary").style.display = 'inline-block';
         document.querySelector("#comment-" + commentId + " .btn-danger").style.display = 'inline-block';
      }

      // 댓글 삭제 버튼 클릭 시 호출되는 함수
      function deleteComment(commentId) {
         // 댓글 삭제 로직을 추가 (여기서는 예시로 콘솔에 출력)
         console.log("삭제할 댓글 ID:", commentId);
      }
   </script>
</body>

</html>
