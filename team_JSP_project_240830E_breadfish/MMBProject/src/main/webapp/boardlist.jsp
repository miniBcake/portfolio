<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.ArrayList, java.util.HashMap, java.util.List, java.util.Map"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html lang="en">

<head>
<!-- Favicon -->
<link href="img/favicon.ico" rel="icon">
<meta charset="utf-8">
<title>게시글 목록</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Favicon -->
<link href="img/favicon.ico" rel="icon">

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;500&family=Lora:wght@600;700&display=swap"
	rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="lib/animate/animate.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css"
	rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">
</head>

<body>
	<!-- Spinner Start -->
	<div id="spinner"
		class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
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
								<a href="listBoards.do?categoryName=일반" class="dropdown-item">이웃
									새글</a> <a href="newBoardPage.do" class="dropdown-item">게시글 작성</a> <a
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


	<!-- Page Header Start -->
	<div class="container-fluid page-header wow fadeIn"
		data-wow-delay="0.1s">
		<div class="container"></div>
	</div>
	<!-- Page Header End -->

	<%-- 임시 게시물 데이터 설정 --%>

	<!-- Blog Start -->
	<div class="container-xxl py-6">
		<div class="container">
			<div class="section-header text-center mx-auto mb-5">
				<h1 class="display-5 mb-3">게시글 목록</h1>
				<p>게시글을 보고 댓글을 달아 소통해보세요!</p>
				<div class="d-flex justify-content-between fruite-name">
					<a href="listBoards.do?categoryName=일반"><i
						class="fas fa-apple-alt me-2"></i>공개 게시판</a>
				</div>
				<div class="d-flex justify-content-between fruite-name">
					<a href="listBoards.do?categoryName=문의"><i
						class="fas fa-apple-alt me-2"></i>문의 게시판</a>
				</div>
			</div>

			<!-- 검색 폼 -->
			<form action="searchBoards.do" method="get" id="searchForm">
				<input type="hidden" name="categoryName" value="일반">
				<div
					class="bg-light ps-3 py-3 rounded d-flex justify-content-between mb-4">
					<label for="filter" style="white-space: nowrap">검색 필터</label> <select
						id="filter" name="searchKeyword" 
						class="border-0 form-select-sm bg-light me-3">
						<option value="searchWriter"
							${filter == 'searchWriter' ? 'selected' : ''}>작성자로 검색</option>
						<option value="searchTitle"
							${filter == 'searchTitle' ? 'selected' : ''}>제목으로 검색</option>
						<option value="searchContents"
							${filter == 'searchContents' ? 'selected' : ''}>내용으로 검색</option>
					</select> <input type="search" name="searchContent" class="form-control p-1"
						placeholder="keywords" value="${searchContent}"> </span>
					<button type="submit" class="btn btn-primary">검색</button>
				</div>
			</form>

			<!-- 게시글 리스트 출력 -->
			<div class="row g-4">
				<c:forEach var="boardList" items="${boardList}">
					<div class="col-lg-4 col-md-6 wow fadeInUp">
						<div class="p-4 border rounded-bottom rounded-top">
							<h4>${boardList.title}</h4>
							<div class="small">
								<span class="fa fa-user text-primary"></span> <span class="ms-2">${boardList.memberNickname}</span>
							</div>
							<small class="me-3"> <i
								class="fa fa-calendar text-primary me-2"></i>${boardList.writeDay}
							</small>
							<p>${boardList.content}</p>
							<div class="d-flex justify-content-between flex-lg-wrap">
								<a href="viewBoard.do?bid=${boardList.boardNum}"
									class="btn border border-secondary rounded-pill px-3 text-primary">게시글
									정보</a>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
			<!-- 게시글 리스트 출력 -->


			<!-- Pagination -->
			<!--  
        int pageSize = 6; // 페이지당 게시물 수
        int currentPage = 1; // 현재 페이지
        String pageParam = request.getParameter("page");
        -->
			<div class="col-12">
				<div class="pagination d-flex justify-content-center mt-5">
					<!-- 이전 페이지 버튼 -->
					<c:if test="${currentPage > 1}">
						<a
							href="?page=${currentPage - 1}&categoryName=일반&filter=${filter}&keyword=${keyword}"
							class="rounded">&laquo; 이전</a>
					</c:if>

					<!-- 페이지 번호 -->
					<c:forEach var="i" begin="1" end="${totalPages}">
						<c:choose>
							<c:when test="${i == currentPage}">
								<strong class="rounded">${i}</strong>
							</c:when>
							<c:otherwise>
								<a
									href="?page=${i}&categoryName=일반&filter=${filter}&keyword=${keyword}"
									class="rounded">${i}</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<!-- 다음 페이지 버튼 -->
					<c:if test="${currentPage < totalPages}">
						<a
							href="?page=${currentPage + 1}&categoryName=일반&filter=${filter}&keyword=${keyword}&categoryName=${categoryName}"
							class="rounded">다음 &raquo;</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<!-- Blog End -->

	<!-- Copyright Start -->
	<br>
	<br>
	<div class="container-fluid copyright bg-dark py-4">
		<div class="container">
			<div class="row">
				<div class="col-md-6 text-center text-md-start mb-3 mb-md-0">
					<span class="text-light"><a href="#"><i
							class="fas fa-copyright text-light me-2"></i>Your Site Name</a>, All
						right reserved.</span>
				</div>
				<div class="col-md-6 my-auto text-center text-md-end text-white">
					<!--/*** This template is free as long as you keep the below author’s credit link/attribution link/backlink. ***/-->
					<!--/*** If you'd like to use the template without the below author’s credit link/attribution link/backlink, ***/-->
					<!--/*** you can purchase the Credit Removal License from "https://htmlcodex.com/credit-removal". ***/-->
					Designed By <a class="border-bottom" href="https://htmlcodex.com">HTML
						Codex</a>
				</div>
			</div>
		</div>
	</div>
	<!-- Copyright End -->

	<!-- Back to Top -->
	<a href="#"
		class="btn btn-lg btn-primary btn-lg-square rounded-circle back-to-top"><i
		class="bi bi-arrow-up"></i></a>


	<!-- JavaScript Libraries -->
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="lib_new/wow/wow.min.js"></script>
	<script src="lib_new/easing/easing.min.js"></script>
	<script src="lib_new/waypoints/waypoints.min.js"></script>
	<script src="lib_new/owlcarousel/owl.carousel.min.js"></script>

	<!-- Template Javascript -->
	<script src="js_new/main.js"></script>
</body>

</html>