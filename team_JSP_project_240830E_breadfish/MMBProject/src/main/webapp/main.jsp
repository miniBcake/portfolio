<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Fruitables - Vegetable Website Template</title>
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

   <!-- Spinner Start -->
   <div id="spinner"
      class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
      <div class="spinner-grow text-primary" role="status"></div>
   </div>
   <!-- Spinner End -->


   <!-- Navbar start -->
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
                  <a href="mainPage.do" class="nav-item nav-link active">메인 메뉴</a>
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
                     <c:when test="${sessionScope.member != null && sessionScope.member.memberPK != null}">
                        <!-- 로그인 상태 -->
                        <a href="logout.do" class="btn border border-secondary rounded-pill px-2 text-primary me-4">로그아웃</a>
                     </c:when>
                     <c:otherwise>
                        <!-- 로그아웃 상태 -->
                        <a href="loginPage.do" class="btn border border-secondary rounded-pill px-2 text-primary me-4">로그인</a>
                     </c:otherwise>
                  </c:choose>
                  <a href="checkPWPage.do" class="my-auto"> <i
                     class="fas fa-user fa-2x"></i></a>
               </div>
            </div>
         </nav>
      </div>
   </div>
   <!-- Navbar End -->


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


   <!-- Hero Start -->
   <div class="container-fluid py-5 mb-5 hero-header">
      <div class="container py-5">
         <div class="row g-5 align-items-center">
            <div class="col-md-12 col-lg-7">
               <h4 class="mb-3 text-secondary">붕어빵 원정대</h4>
               <h1 class="mb-5 display-3 text-primary">내 주변 붕어빵 찾기!</h1>
               <div class="position-relative mx-auto">
                  <input
                     class="form-control border-2 border-secondary w-75 py-3 px-4 rounded-pill"
                     type="text" placeholder="가게 이름 검색">
                  <button type="submit"
                     class="btn btn-primary border-2 border-secondary py-3 px-4 position-absolute rounded-pill text-white h-100"
                     style="top: 0; right: 25%;">검색</button>
               </div>
            </div>
            <div class="col-md-12 col-lg-5">
               <div id="carouselId" class="carousel slide position-relative"
                  data-bs-ride="carousel">
                  <div class="carousel-inner" role="listbox">
                     <div class="carousel-item active rounded">
                        <img src="img/teemo1.jpg"
                           class="img-fluid w-100 h-100 bg-secondary rounded"
                           alt="First slide"> <a href="#"
                           class="btn px-4 py-2 text-white rounded">티모!</a>
                     </div>
                     <div class="carousel-item rounded">
                        <img src="img/fish.jpg" class="img-fluid w-100 h-100 rounded"
                           alt="Second slide"> <a href="#"
                           class="btn px-4 py-2 text-white rounded">붕어빵</a>
                     </div>
                     <div class="carousel-item rounded">
                        <img src="img/hero-img-2.jpg"
                           class="img-fluid w-100 h-100 rounded" alt="Second slide">
                        <a href="#" class="btn px-4 py-2 text-white rounded">야채</a>
                     </div>
                  </div>
                  <button class="carousel-control-prev" type="button"
                     data-bs-target="#carouselId" data-bs-slide="prev">
                     <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                     <span class="visually-hidden">Previous</span>
                  </button>
                  <button class="carousel-control-next" type="button"
                     data-bs-target="#carouselId" data-bs-slide="next">
                     <span class="carousel-control-next-icon" aria-hidden="true"></span>
                     <span class="visually-hidden">Next</span>
                  </button>
               </div>
            </div>
         </div>
      </div>
   </div>
   <!-- Hero End -->


   <!-- Featurs Section Start -->
   <div class="container-fluid featurs py-5">
      <div class="container py-5">
         <div class="row g-4">
            <div class="col-md-6 col-lg-3">
               <div class="featurs-item text-center rounded bg-light p-4">
                  <div
                     class="featurs-icon btn-square rounded-circle bg-secondary mb-5 mx-auto">
                     <i class="fas fa-car-side fa-3x text-white"></i>
                  </div>
                  <div class="featurs-content text-center">
                     <h5>내 주변 붕어빵 가게 찾기</h5>
                     <p class="mb-0">가게 정보</p>
                  </div>
               </div>
            </div>
            <div class="col-md-6 col-lg-3">
               <div class="featurs-item text-center rounded bg-light p-4">
                  <div
                     class="featurs-icon btn-square rounded-circle bg-secondary mb-5 mx-auto">
                     <i class="fas fa-user-shield fa-3x text-white"></i>
                  </div>
                  <div class="featurs-content text-center">
                     <h5>각종 문의 비밀 게시판</h5>
                     <p class="mb-0"></p>
                  </div>
               </div>
            </div>
            <div class="col-md-6 col-lg-3">
               <div class="featurs-item text-center rounded bg-light p-4">
                  <div
                     class="featurs-icon btn-square rounded-circle bg-secondary mb-5 mx-auto">
                     <i class="fas fa-exchange-alt fa-3x text-white"></i>
                  </div>
                  <div class="featurs-content text-center">
                     <h5>정보 소통 커뮤니티</h5>
                     <p class="mb-0">사용자간에 정보 공유 커뮤니티</p>
                  </div>
               </div>
            </div>
            <div class="col-md-6 col-lg-3">
               <div class="featurs-item text-center rounded bg-light p-4">
                  <div
                     class="featurs-icon btn-square rounded-circle bg-secondary mb-5 mx-auto">
                     <i class="fa fa-phone-alt fa-3x text-white"></i>
                  </div>
                  <div class="featurs-content text-center">
                     <h5>전화 문의</h5>
                     <p class="mb-0">010-1234-5678</p>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
   <!-- Featurs Section End -->







   <!-- Footer Start -->
   <div class="container-fluid bg-dark text-white-50 footer pt-5 mt-5">
      <div class="container py-5">
         <div class="pb-4 mb-4"
            style="border-bottom: 1px solid rgba(226, 175, 24, 0.5);">
            <div class="row g-4">
               <div class="col-lg-3">
                  <a href="#">
                     <h1 class="text-primary mb-0">갈빵질빵</h1>
                     <p class="text-secondary mb-0">Fresh products</p>
                  </a>
               </div>
               <div class="col-lg-6">
                  <div class="position-relative mx-auto">
                     <input class="form-control border-0 w-100 py-3 px-4 rounded-pill"
                        type="number" placeholder="Your Email">
                     <button type="submit"
                        class="btn btn-primary border-0 border-secondary py-3 px-4 position-absolute rounded-pill text-white"
                        style="top: 0; right: 0;">Subscribe Now</button>
                  </div>
               </div>
               <div class="col-lg-3">
                  <div class="d-flex justify-content-end pt-3">
                     <a
                        class="btn  btn-outline-secondary me-2 btn-md-square rounded-circle"
                        href=""><i class="fab fa-twitter"></i></a> <a
                        class="btn btn-outline-secondary me-2 btn-md-square rounded-circle"
                        href=""><i class="fab fa-facebook-f"></i></a> <a
                        class="btn btn-outline-secondary me-2 btn-md-square rounded-circle"
                        href=""><i class="fab fa-youtube"></i></a> <a
                        class="btn btn-outline-secondary btn-md-square rounded-circle"
                        href=""><i class="fab fa-linkedin-in"></i></a>
                  </div>
               </div>
            </div>
         </div>
         <div class="row g-5">
            <div class="col-lg-3 col-md-6">
               <div class="footer-item">
                  <h4 class="text-light mb-3">Why People Like us!</h4>
                  <p class="mb-4">typesetting, remaining essentially unchanged.
                     It was popularised in the 1960s with the like Aldus PageMaker
                     including of Lorem Ipsum.</p>
                  <a href=""
                     class="btn border-secondary py-2 px-4 rounded-pill text-primary">Read
                     More</a>
               </div>
            </div>
            <div class="col-lg-3 col-md-6">
               <div class="d-flex flex-column text-start footer-item">
                  <h4 class="text-light mb-3">Shop Info</h4>
                  <a class="btn-link" href="">About Us</a> <a class="btn-link"
                     href="">Contact Us</a> <a class="btn-link" href="">Privacy
                     Policy</a> <a class="btn-link" href="">Terms & Condition</a> <a
                     class="btn-link" href="">Return Policy</a> <a class="btn-link"
                     href="">FAQs & Help</a>
               </div>
            </div>
            <div class="col-lg-3 col-md-6">
               <div class="d-flex flex-column text-start footer-item">
                  <h4 class="text-light mb-3">Account</h4>
                  <a class="btn-link" href="">My Account</a> <a class="btn-link"
                     href="">Shop details</a> <a class="btn-link" href="">Shopping
                     Cart</a> <a class="btn-link" href="">Wishlist</a> <a class="btn-link"
                     href="">Order History</a> <a class="btn-link" href="">International
                     Orders</a>
               </div>
            </div>
            <div class="col-lg-3 col-md-6">
               <div class="footer-item">
                  <h4 class="text-light mb-3">Contact</h4>
                  <p>Address: 1429 Netus Rd, NY 48247</p>
                  <p>Email: Example@gmail.com</p>
                  <p>Phone: +0123 4567 8910</p>
                  <p>Payment Accepted</p>
                  <img src="img/payment.png" class="img-fluid" alt="">
               </div>
            </div>
         </div>
      </div>
   </div>
   <!-- Footer End -->

   <!-- Copyright Start -->
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
</body>

</html>