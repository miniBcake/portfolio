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
<title>문의 게시판</title>
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


	<!-- Navbar Start -->
	<div class="container-fluid fixed-top px-0 wow fadeIn"
		data-wow-delay="0.1s">
		<div class="top-bar row gx-0 align-items-center d-none d-lg-flex">
			<div class="col-lg-6 px-5 text-start">
				<small><i class="fa fa-map-marker-alt me-2"></i>123 Street,
					New York, USA</small> <small class="ms-4"><i
					class="fa fa-envelope me-2"></i>info@example.com</small>
			</div>
			<div class="col-lg-6 px-5 text-end">
				<small>Follow us:</small> <a class="text-body ms-3" href=""><i
					class="fab fa-facebook-f"></i></a> <a class="text-body ms-3" href=""><i
					class="fab fa-twitter"></i></a> <a class="text-body ms-3" href=""><i
					class="fab fa-linkedin-in"></i></a> <a class="text-body ms-3" href=""><i
					class="fab fa-instagram"></i></a>
			</div>
		</div>

		<nav
			class="navbar navbar-expand-lg navbar-light py-lg-0 px-lg-5 wow fadeIn"
			data-wow-delay="0.1s">
			<a href="main.jsp" class="navbar-brand ms-4 ms-lg-0">
				<h1 class="fw-bold text-primary m-0">
					갈<span class="text-secondary">빵</span>질<span class="text-secondary">빵</span>
				</h1>
			</a>
			<button type="button" class="navbar-toggler me-4"
				data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse bg-white" id="navbarCollapse">
				<div class="navbar-nav mx-auto">
					<a href="main.jsp" class="nav-item nav-link">메인메뉴</a>
					<div class="nav-item dropdown">
						<a href="#" class="nav-link dropdown-toggle active"
							data-bs-toggle="dropdown">커뮤니티</a>
						<div class="dropdown-menu m-0 bg-secondary rounded-0">
							<a href="boardlist.jsp" class="dropdown-item">이웃 새글</a> <a
								href="boardwrite.jsp" class="dropdown-item">게시글 작성</a> <a
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
					<a href="main.jsp"
						class="btn border border-secondary rounded-pill px-2 text-primary me-4">Logout</a>
					<a href="mypage.jsp" class="my-auto"> <i
						class="fas fa-user fa-2x"></i>
					</a>
				</div>
			</div>
		</nav>
	</div>
	<!-- Navbar End -->


	<!-- Page Header Start -->
	<div class="container-fluid page-header wow fadeIn"
		data-wow-delay="0.1s">
		<div class="container"></div>
	</div>
	<!-- Page Header End -->

	<%-- 임시 게시물 데이터 설정 --%>
	<%
        // 게시물 데이터 설정
        List<Map<String, String>> postList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Map<String, String> post = new HashMap<>();
            post.put("id", String.valueOf(i));
            post.put("title", "게시글 제목 " + i);
            post.put("author", "작성자 " + i);
            post.put("commentCount", String.valueOf(i * 2));
            post.put("date", "2024-08-" + (i % 31 + 1));
            post.put("content", "게시글 내용 " + i);
            post.put("visibility", i % 2 == 0 ? "1" : "2"); // 예를 들어 짝수는 공개글, 홀수는 비공개글
            postList.add(post);
        }

        // 검색 필터와 검색어
        String filter = request.getParameter("filter");
        String keyword = request.getParameter("keyword");
        String visibilityParam = request.getParameter("visibility");

        if (filter == null) filter = "title";
        if (keyword == null) keyword = "";
        if (visibilityParam == null) visibilityParam = "1"; // 기본값 공개글

        // 필터링된 게시물 데이터
        List<Map<String, String>> filteredPosts = new ArrayList<>();

        // 게시물 필터링
        for (Map<String, String> post : postList) {
            String valueToSearch = "";
            if ("title".equals(filter)) {
                valueToSearch = post.get("title");
            } else if ("writer".equals(filter)) {
                valueToSearch = post.get("author");
            } else if ("content".equals(filter)) {
                valueToSearch = post.get("content");
            }

            if (valueToSearch != null && valueToSearch.toLowerCase().contains(keyword.toLowerCase())
                && visibilityParam.equals(post.get("visibility"))) {
                filteredPosts.add(post);
                // 사용자가 입력한 값이 null이 아닐 경우
                // valueToSearch의 값이 keyword를 포함하는지 확인
                // 게시물의 visibility 값이 사용자가 선택한 visibilityParam 값과 일치하는지 확인
            }
        }

        // 페이지네이션 변수 설정
        int pageSize = 6; // 페이지당 게시물 수
        int currentPage = 1; // 현재 페이지
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        int totalPosts = filteredPosts.size();
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

        // 현재 페이지에 표시할 게시물 계산
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalPosts);
        List<Map<String, String>> pagePosts = filteredPosts.subList(startIndex, endIndex);

        // JSP에 데이터 전달
        request.setAttribute("postList", pagePosts);
        ///request.setAttribute("postList", postList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("filter", filter);
        request.setAttribute("keyword", keyword);
        request.setAttribute("visibility", visibilityParam);
    %>

	<!-- Blog Start -->
	<div class="container-xxl py-5">
		<div class="container">
			<div class="row g-4">
				<!-- 문의 게시판 제목과 링크 -->
				<div class="col-lg-12 mx-auto mb-5">
					<h1 class="display-5 mb-3">문의 게시판</h1>
					<div class="d-flex justify-content-between fruite-name">
						<a href="boardlist.jsp"><i class="fas fa-apple-alt me-2"></i>공개
							게시판</a>
					</div>
					<div class="d-flex justify-content-between fruite-name">
						<a href="secretBoard.jsp"><i class="fas fa-apple-alt me-2"></i>문의
							게시판</a>

					</div>
				</div>
			</div>

			<!-- 비공개글 리스트 출력 -->
			<div class="row g-4">
				<c:forEach var="post" items="${postList}">
					<c:if test="${post.visibility == '1' }">
						<div class="col-lg-4 col-md-6 wow fadeInUp">
							<div class="p-4 border rounded-bottom rounded-top">
								<h4>${post.title}</h4>
								<div class="small">
									<span class="fa fa-user text-primary"></span> <span
										class="ms-2">${post.author}</span>
								</div>
								<div class="small">
									<span class="fa fa-comment-alt text-primary"></span> <span
										class="ms-2">${post.commentCount}</span>
								</div>
								<small class="me-3"> <i
									class="fa fa-calendar text-primary me-2"></i>${post.date}
								</small>
								<p>${post.content}</p>
								<div class="d-flex justify-content-between flex-lg-wrap">
									<a href="board.jsp?id=${post.id}"
										class="btn border border-secondary rounded-pill px-3 text-primary">게시글
										정보</a>
								</div>
							</div>
						</div>
					</c:if>

					<c:if test="${post.visibility == '2'}">
						<div class="col-lg-4 col-md-6 wow fadeInUp">
							<div class="p-4 border rounded-bottom rounded-top">
								<h4>비밀 게시글입니다.</h4>
								<div class="small">
									<span class="fa fa-user text-primary"></span> <span
										class="ms-2">${post.author}</span>
								</div>
								<small class="me-3"> <i
									class="fa fa-calendar text-primary me-2"></i>${post.date}
								</small>
								<p>비밀 게시글입니다.</p>
								<div class="d-flex justify-content-between flex-lg-wrap">
									<a href="board.jsp?id=${post.id}"
										class="btn border border-secondary rounded-pill px-3 text-primary">게시글
										정보</a>
								</div>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</div>

			<!-- Pagination -->
			<div class="col-12">
				<div class="pagination d-flex justify-content-center mt-5">
					<!-- 이전 페이지 버튼 -->
					<c:if test="${currentPage > 1}">
						<a
							href="?page=${currentPage - 1}&filter=${filter}&keyword=${keyword}&visibility=${visibility}"
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
									href="?page=${i}&filter=${filter}&keyword=${keyword}&visibility=${visibility}"
									class="rounded">${i}</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<!-- 다음 페이지 버튼 -->
					<c:if test="${currentPage < totalPages}">
						<a
							href="?page=${currentPage + 1}&filter=${filter}&keyword=${keyword}&visibility=${visibility}"
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