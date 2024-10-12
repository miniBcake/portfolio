<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 모드 - 신규 회원 목록</title>
</head>
<body>
	<meta name="viewport"
		content="width=device-width, initial-scale=1, user-scalable=no" />
	<link rel="stylesheet" href="assets/css/main.css" />
<body>

	<!-- Header -->
	<custom:header />
	<!-- MainContent -->
	<!-- 상단 프로필 영역 -->
	<section id="content">
		<div class="container">
			<div class="row">
				<div class="col-9 col-12-medium">
					<!-- Main Content -->
					<section>
						<h3>신규회원 목록 보기</h3>
						<div>
							<ul>
								<c:forEach var="member" items="${newMemberList}">
									<!-- 출력할 회원 정보 -->
									<li>닉네임 : ${member.memberNickname}</li>
									<li>이메일 : ${member.memberEmail}</li>
									<li>전화번호 : ${member.memberPhone}</li>
									<li>가입일 : ${member.memberHireDay}</li>
									<button type="button">자세히 보기</button>
									<hr>
								</c:forEach>
							</ul>
						</div>
					</section>
				</div>
				<custom:adminMenu />
			</div>
		</div>
	</section>

	<!-- Footer -->
	<custom:footer />
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/browser.min.js"></script>
	<script src="assets/js/breakpoints.min.js"></script>
	<script src="assets/js/util.js"></script>
	<script src="assets/js/main.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</body>
</html>