<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>갈빵질빵 - 로그인 페이지</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="assets/css/main.css" />
    <!-- JavaScript -->
    <script src="assets/js/jquery.min.js" defer></script>
    <script src="assets/js/browser.min.js" defer></script>
    <script src="assets/js/breakpoints.min.js" defer></script>
    <script src="assets/js/util.js" defer></script>
    <script src="assets/js/main.js" defer></script>
    <!-- 구글 로그인 JavaScript -->
    <script src="https://accounts.google.com/gsi/client" async defer></script>
    <script type="text/javascript" src="assets/js/loginGoogleAPI.js" defer></script>
</head>
<body>
    <!-- header -->
    <custom:header />

    <!-- Content -->
    <section id="content">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <!-- Main Content -->
                    <section>
                        <header>
                            <h2>로그인</h2>
                            <h3>로그인 페이지</h3>
                        </header>
						<form id="loginForm" action="login.do" method="POST">
							<div>
								<input type="email" name="email" placeholder="name@example.com" required> 
								<label for="email">이메일 입력</label>
							</div>
							<div>
								<input type="password" name="password" placeholder="Password" required> 
								<label for="password">비밀번호</label>
							</div>
							<div>
								<input type="checkbox" id="exampleCheck"> 
								<label for="exampleCheck">아이디 저장</label>
							</div>
							<button type="submit">로그인</button>
							<a href="findPwPage.do" role="button"><button type="button" id="findPw">비밀번호 찾기</button></a>
						</form>
						
						<%-- 구글 로그인 버튼 시작 : ** client_id를 변경하시면 됩니다 --%>
						<div id="g_id_onload"
							data-client_id="730285026476-3dh5pad8cclbr7gsvi75rrmejnemf58l.apps.googleusercontent.com"
							data-context="signin" 
							data-ux_mode="popup"
							data-callback="handleCredentialResponse" 
							data-auto_prompt="false">
						</div>

						<div class="g_id_signin" 
							data-type="icon" 
							data-shape="circle"
							data-theme="outline" 
							data-text="signin_with" 
							data-size="large">
						</div>
						<%--구글 로그인 버튼 끝 --%>
						
						<div id="buttonDiv"></div>
                        <p style="white-space: nowrap;">
                            아직 계정이 없으신가요? <a href="signupPage.do">회원가입</a>
                        </p>
                    </section>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <custom:footer />

</body>
</html>
