<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>갈빵질빵 - 로그인 페이지</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link rel="icon" href="img_b/favicon.png" type="image/png">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">
    
    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css_C_tmp/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="css_C_tmp/style.css" rel="stylesheet">
</head>

<body>
    <div class="container-xxl position-relative bg-white d-flex p-0">
        <!-- Spinner Start -->
        <div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
            <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
                <span class="sr-only">로딩중...</span>
            </div>
        </div>
        <!-- Spinner End -->


        <!-- Sign In Start -->
        
        <div class="container-fluid">
            <div class="row h-100 align-items-center justify-content-center" style="min-height: 100vh;">
                <div class="col-12 col-sm-8 col-md-6 col-lg-5 col-xl-4">
                    <div class="bg-light rounded p-4 p-sm-5 my-4 mx-3">
                        <div class="d-flex align-items-center justify-content-between mb-3">
                            <a href="mainPage.do" class="primary-heading">
                                <h3 class="primary-heading"><i class="fa fa-hashtag me-2"></i>갈빵질빵</h3>
                            </a>
                            <h3>로그인</h3>
                        </div>
                        <form id="loginForm" action="login.do" method="POST">
                        <div class="form-floating mb-3">
                            <input type="email" class="form-control" name="email" placeholder="name@example.com">
                            <label for="email">이메일 입력</label>
                        </div>
                        <div class="form-floating mb-4">
                            <input type="password" class="form-control" name="password" placeholder="Password">
                            <label for="password">비밀번호</label>
                        </div>
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="exampleCheck">
                                <label class="form-check-label" for="exampleCheck">아이디 저장</label>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary py-3 w-100 mb-4">로그인</button>
                        </form>
                        
                        <p class="text-center mb-0" style="white-space : nowrap;">아직 계정이 없으신가요?<a class="primary-heading" href="signupPage.do">회원가입</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
        <!-- Sign In End -->

    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="lib/chart/chart.min.js"></script>
    <script src="lib/easing/easing.min.js"></script>
    <script src="lib/waypoints/waypoints.min.js"></script>
    <script src="lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="lib/tempusdominus/js/moment.min.js"></script>
    <script src="lib/tempusdominus/js/moment-timezone.min.js"></script>
    <script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

    <!-- Template Javascript -->
    <script src="js/main.js"></script>
    
    <!-- Google LOGIN API -->




</body>

</html>