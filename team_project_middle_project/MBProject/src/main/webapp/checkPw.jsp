<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>갈빵질빵 - 비밀번호 확인</title>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css" />
</head>
<body>
<!-- header -->
<custom:header/>
<section id="content">
					<div class="container">
						<div class="row">
							<div class="col-12">							
							<section>
<h3 style="white-space:nowrap">비밀번호 확인</h3>
                        <form action="checkPw.do" method="POST">
                        <div class="">
                            <input type="password" class="" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
                            <label for="password">비밀번호 입력</label><br>
                            <button type="submit" class="">입력 완료</button>
                        </div>
                        </form>
                        </section>
                        </div>
                        </div>
                        </div>
                        </section>
                        
                        
                        <!-- Footer -->
                        <custom:footer/>
                        <!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/browser.min.js"></script>
			<script src="assets/js/breakpoints.min.js"></script>
			<script src="assets/js/util.js"></script>
			<script src="assets/js/main.js"></script>
			<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                        
                        
</body>
</html>