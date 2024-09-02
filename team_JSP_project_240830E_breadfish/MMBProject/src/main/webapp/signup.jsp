<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="utf-8">
<title>회원가입 페이지</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Favicon -->
<link rel="icon" href="img_b/favicon.png" type="image/png">

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap"
	rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="lib/owlcarousel/assets/owl.carousel.min.css"
	rel="stylesheet">
<link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css"
	rel="stylesheet" />

<!-- Customized Bootstrap Stylesheet -->
<link href="css_C_tmp/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css_C_tmp/style.css" rel="stylesheet">

<!-- Javascript -->

</head>
<body>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<div class="container-xxl position-relative bg-white d-flex p-0">
		<!-- Spinner Start -->
		<div id="spinner"
			class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
			<div class="spinner-border text-primary"
				style="width: 3rem; height: 3rem;" role="status">
				<span class="sr-only">로딩중...</span>
			</div>
		</div>
		<!-- Spinner End -->


		<!-- Sign Up Start -->
		<div class="container-fluid">
			<div class="row h-100 align-items-center justify-content-center"
				style="min-height: 100vh;">
				<div class="col-12 col-sm-8 col-md-6 col-lg-5 col-xl-4">
					<div class="bg-light rounded p-4 p-sm-5 my-4 mx-3">
						<div
							class="d-flex align-items-center justify-content-between mb-3">
							<h3>
							<a href="mainPage.do" class="primary-heading" style="white-space: nowrap">			
									<i class="fa fa-hashtag me-2"></i>갈빵질빵					
							</a>
							</h3>
						</div>
						<h3 style="white-space: nowrap">회원가입</h3>
						<form action="join.do" id="joinForm" method="POST" enctype="multipart/form-data">
							<div class="form-floating mb-3">
								<select name="role" class="form-select" required>
									<option id="user" value="user">사용자</option>
									<option id="seller" value="seller" >판매자</option>
								</select>
								<label for="role">권한을 선택하세요</label>
							</div>
							<div class="form-floating mb-3">
							<input type="text" class="form-control" id="name" name="name" placeholder="이름을 입력하세요" required> 
							<label for="name">이름을 입력하세요</label>
							</div>
							<div class="form-floating mb-3">
							<input type="text" class="form-control" id="nickName" name="nickName" placeholder="닉네임을 입력하세요" required> 
							<label for="nickName">닉네임을 입력하세요</label>
							<button type="button" id="checkNickNameBtn" class="btn btn-secondary" style="margin:5px">중복확인</button>
							<span id="checkNickNameMsg"></span>
							</div>
							<div class="form-floating mb-1">
								<input type="email" class="form-control" id="email" name="email" placeholder="name@example.com" required>
								<label for="email">이메일을 입력하세요</label>
								<button type="button" id="checkEmailBtn" class="btn btn-secondary" style="margin:5px">중복확인</button>
							<span id="checkEmailMsg"></span>
							</div>				
							<div class="form-floating mb-4">
								<input type="password" class="form-control" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
									<label for="password">비밀번호를 입력하세요</label>
							</div>
							<div class="form-floating mb-4">
								<input type="tel" class="form-control" id="phoneNum"
									placeholder="전화번호를 입력하세요"  pattern="010-[0-9]{4}-[0-9]{4}"> <label for="phoneNum"
									style="white-sapce: nowrap">전화번호를 입력하세요(선택)</label>
							</div>

							<div
								class="d-flex align-items-center justify-content-between mb-4">
								<div class="mb-3">
									<label for="formFile" class="form-label">프로필 사진 업로드</label> <input
										class="form-control" type="file" id="profilePic" name="profilePic">
								</div>
							</div>
						<button type="submit" class="btn btn-primary py-3 w-100 mb-4">회원가입</button>
						</form>
						<p class="text-center mb-0">
							이미 계정이 있으신가요? <a class="primary-heading" href="loginPage.do">로그인</a>
						</p>
					</div>
				</div>
			</div>
		</div>
		<!-- Sign Up End -->
	</div>

	<!-- JavaScript Libraries -->
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="lib/chart/chart.min.js"></script>
	<script src="lib/easing/easing.min.js"></script>
	<script src="lib/waypoints/waypoints.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>
	<script src="lib/tempusdominus/js/moment.min.js"></script>
	<script src="lib/tempusdominus/js/moment-timezone.min.js"></script>
	<script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

	<!-- Template Javascript -->
	<script src="js/main.js"></script>
	<script src="js/check.js"></script>
</body>

</html>