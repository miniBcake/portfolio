<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
<!-- Favicon -->
<link href="img/favicon.ico" rel="icon">
    <meta charset="utf-8">
    <title>게시글 상세보기</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="css/style.css" rel="stylesheet">

    <!-- 내부에서 생성한 버튼 css -->
    <style>
        .btn-light-green {
            background-color: #32CD32; /* 연두색 */
            color: white;
        }
    </style>
</head>

<body>

  <!-- Spinner Start -->
   <div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
      <div class="spinner-border text-primary" role="status"></div>
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
            <a href="main.do" class="navbar-brand ms-4 ms-lg-0">
               <h1 class="fw-bold text-primary m-0">
                  갈<span class="text-secondary">빵</span>질<span
                     class="text-secondary">빵</span>
               </h1>
            </a>
            <button class="navbar-toggler py-2 px-3" type="button"
               data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
               <span class="fa fa-bars text-primary"></span>
            </button>
            <div class="collapse navbar-collapse bg-white" id="navbarCollapse">
               <div class="navbar-nav mx-auto">
                  <a href="main.do" class="nav-item nav-link">메인메뉴</a>
                  <div class="nav-item dropdown">
                     <a href="#" class="nav-link dropdown-toggle active"
                        data-bs-toggle="dropdown">커뮤니티</a>
                     <div class="dropdown-menu m-0 bg-secondary rounded-0">
                        <a href="listBoards.do?categoryName=일반" class="dropdown-item">이웃 새글</a> <a
                           href="newBoardPage.do" class="dropdown-item">게시글 작성</a> <a
                           href="404.html" class="dropdown-item">404 Page</a>
                     </div>
                  </div>
               </div>
               <div class="d-flex m-3 me-0">
                  <button
                     class="btn-search btn border border-secondary btn-md-square rounded-circle bg-white me-4"
                     data-bs-toggle="modal" data-bs-target="#searchModal">
                     <i class="fas fa-search text-primary"></i>
                  </button>
                  <a href="logoutdo."
                     class="btn border border-secondary rounded-pill px-2 text-primary me-4">Logout</a>
                  <a href="myPage.do" class="my-auto"> <i
                     class="fas fa-user fa-2x"></i>
                  </a>
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
      <h1 class="text-center text-white display-6">${board.title}</h1>
      <ol class="breadcrumb justify-content-center mb-0">
         <li class="breadcrumb-item"><a href="listBoards.do?categoryName=일반">게시글 목록으로 이동</a></li>
      </ol>
   </div>
   <!-- Single Page Header End -->

   <!-- Checkout Page Start -->
   <div class="container-fluid py-5">
      <div class="container py-5">
         <h1 class="mb-4">${board.title}</h1>
         <!-- 게시글 이름을 받아옴 -->
         <form action="#">
            <div class="row g-5">
               <div class="col-md-12 col-lg-6 col-xl-12">
                  <div class="row">
                     <div class="col-md-12 col-lg-6"></div>
                  </div>
                  <!-- 게시글 작성 날짜와 작성자를 보여주는 부분 -->
                  <div class="form-item">
                     <small class="me-3"> <i
                        class="fa fa-calendar text-primary me-2"></i> ${board.writeDay}
                        <!-- 작성 날짜를 받아옴 -->
                     </small>
                  </div>
                  <div>
                     <div class="small">
                        <span class="fa fa-user text-primary"></span> <span class="ms-2">${board.memberNickname}</span>
                        <!-- 작성자 이름을 받아옴 -->
                     </div>
                  </div>
                  <!-- 좋아요 버튼과 좋아요 상태 표시 -->
                  <div class="d-flex align-items-center my-3">
                     <button id="likeButton" class="btn btn-light border-0 me-2"
                        onclick="toggleLike()">
                        <i id="likeIcon"${userLiked ?  '♥' : '♡'}></i>
                     </button>
                     <span id="likeCount">${board.likeCnt}</span> 개의 좋아요
                  </div>
                  <!-- 수정과 삭제 버튼 추가 -->
                  <div class="d-flex align-items-center my-3">
                     <button id="editButton" class="btn btn-light-green me-2"
                        onclick="editPost()">수정</button>
                     <button id="deleteButton" class="btn btn-danger"
                        onclick="deletePost()">삭제</button>
                  </div>
                  <hr>
                  <!-- 게시글 내용 textarea, 읽기 전용 -->
                  <div class="form-item">
                     <div class="form-item">
                        <h3>이미지</h3>
                        <c:choose>
                           <c:when test="${not empty images}">
                              <c:forEach items="${images}" var="image">
                                 <img src="${image.imageWay}" alt="게시글 이미지" style="max-width: 100%; height: auto;" />
                              </c:forEach>
                           </c:when>
                           <c:otherwise>
                                 <p>이미지가 없습니다.</p>
                           </c:otherwise>
                        </c:choose>
                     </div>
                     <div class="form-item">
                        <p>${board.content}</p>
                        <!-- 게시글 내용을 받아옴 -->
                     </div>
                   		<div class="small">
                           <span class="fa fa-comment-alt text-primary"></span> 
                          	 <span class="ms-2"> <span class="breadcrumb-item">
                           		<a href="listReply.do?boardNum=${board.boardNum}">댓글 작성</a>
                           	</span> 
                           		&nbsp;&nbsp;${totalReplies}
                           </span>
                        </div>
                  </div>
               </div>
            </div>
         </form>
      </div>
   </div>
   <!-- Checkout Page End -->

   <!-- Footer Start -->
   <div class="container-fluid bg-dark text-light footer mt-5 py-5">
      <div class="container">
         <div class="row g-5">
            <div class="col-md-6 col-lg-4">
               <h4 class="text-white mb-4">회사 정보</h4>
               <p><i class="fa fa-map-marker-alt me-3"></i>123 Street, New York</p>
               <p><i class="fa fa-phone-alt me-3"></i>+012 345 6789</p>
               <p><i class="fa fa-envelope me-3"></i>info@example.com</p>
            </div>
            <div class="col-md-6 col-lg-4">
               <h4 class="text-white mb-4">메뉴</h4>
               <a class="btn btn-link" href="#">Home</a>
               <a class="btn btn-link" href="#">About Us</a>
               <a class="btn btn-link" href="#">Our Services</a>
               <a class="btn btn-link" href="#">Contact Us</a>
            </div>
            <div class="col-md-6 col-lg-4">
               <h4 class="text-white mb-4">Newsletter</h4>
               <p>Sign up for our newsletter to get the latest updates.</p>
               <div class="position-relative mx-auto" style="max-width: 400px;">
                  <input class="form-control border-0 rounded-0 py-3 ps-4 pe-5" type="text"
                     placeholder="Your email address">
                  <button type="button" class="btn btn-primary rounded-0 py-3 position-absolute top-0 end-0 mt-2 me-2">Sign
                     Up</button>
               </div>
            </div>
         </div>
      </div>
   </div>
   <!-- Footer End -->

   <!-- Back to Top -->
   <a href="#"
      class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
      class="fa fa-arrow-up"></i></a>

   <!-- JavaScript Libraries -->
   <script
      src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
   <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
   <script src="lib/easing/easing.min.js"></script>
   <script src="lib/waypoints/waypoints.min.js"></script>
   <script src="lib/lightbox/js/lightbox.min.js"></script>
   <script src="lib/owlcarousel/owl.carousel.min.js"></script>

   <!-- Template Javascript -->
   <script src="js/main.js"></script>


   <script>
   function toggleLike() {
	    event.preventDefault();
	   
	    // 서버로 좋아요 상태 전송
	    $.ajax({
	        url: `/MMBProject/insertDeleteFavorite.do?bid=${board.boardNum}`, // 서버 URL
	        type: 'POST', // HTTP 메서드 설정
	        success: function(data, textStatus, jqXHR) {
	            console.log('Response status:', jqXHR.status); // 응답 상태 코드 출력

	            if (typeof data === 'object' && data.success !== undefined) {
	                // 좋아요 상태 변경에 성공한 경우 UI 업데이트
	                userLiked = data.liked; // 서버로부터 받은 새로운 좋아요 상태
	                document.getElementById('likeIcon').className = userLiked ?  '♥' : '♡';

	                document.getElementById('likeCount').textContent = parseInt(document.getElementById('likeCount').textContent) + (userLiked ? 1 : -1);
	            } else {
	                alert('좋아요 상태를 변경할 수 없습니다. 다시 시도해 주세요.');
	            }
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	            console.log('[ERROR] 좋아요 요청 오류:', errorThrown);
	            alert('서버와의 통신 중 문제가 발생했습니다. 다시 시도해주세요.');
	        }
	    });
	}


      function editPost() {
    	 event.preventDefault();
         location.href = `checkBoardPage.do?boardNum=${board.boardNum}`;
      }

      function deletePost() {
    	 event.preventDefault();
         if (confirm('정말 삭제하시겠습니까?')) {
            location.href = `deleteBoard.do?boardNum=${board.boardNum}`;
         }
      }
   </script>
</body>

</html>
