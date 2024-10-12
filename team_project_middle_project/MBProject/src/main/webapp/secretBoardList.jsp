<%@page import="java.util.ArrayList"%>
<%@page import="model.dto.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자모드 - 문의 게시글 목록 보기</title>
</head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
<!-- 모달 스타일 추가 -->
	<style>
		/* The Modal (background) */
		.modal {
			display: none;
			position: fixed;
			z-index: 1;
			left: 0;
			top: 0;
			width: 100%;
			height: 100%;
			overflow: auto;
			background-color: rgb(0,0,0);
			background-color: rgba(0,0,0,0.4);
			padding-top: 60px;
		}

		/* Modal Content */
		.modal-content {
			background-color: #fefefe;
			margin: 5% auto;
			padding: 20px;
			border: 1px solid #888;
			width: 80%;
		}

		/* The Close Button */
		.close {
			color: #aaa;
			float: right;
			font-size: 28px;
			font-weight: bold;
		}

		.close:hover,
		.close:focus {
			color: black;
			text-decoration: none;
			cursor: pointer;
		}
	</style>
<body>
	<!-- Header -->
	<custom:header />
	<!-- Main Content -->
	<section id="content">
		<div class="container">
			<div class="row">
				<div class="col-9 col-12-medium">
					<!-- Main Content -->
					<section>
						<!-- 게시글 목록 출력 -->

						<h3>문의 게시글 목록</h3>
					
						<div>
							<ul>
								<c:forEach var="board" items="${secretBoardList}">
									<li>제목 : ${board.title}</li>
									<!-- 게시글 제목과 기타 데이터 -->
									<li>작성일 : ${board.writeDay}</li>
									<li>작성자 : ${board.memberNickname}</li>
									<button type="button" class="viewDetailsBtn" data-id="${board.boardNum}" data-title="${board.title}" data-content="${board.content}">자세히 보기</button>
									<hr>
								</c:forEach>
							</ul>
						</div>
						<!-- 페이지네이션 -->
						<custom:pagination currentPage="${currentPage}" totalPages="${totalPages}" filterCategory="${filterCategory}" />
					</section>
						</div>
				<custom:adminMenu />
				</div>
			</div>
		</div>
	</section>
	<!-- 모달 창 -->
	<div id="detailModal" class="modal">
		<div class="modal-content">
			<span class="close">&times;</span>
			<h2 id="modalTitle">게시글 제목</h2>
			<p id="modalContent">게시글 내용</p>
			
			<!-- 답변 작성란 -->
			<h3>답변 작성</h3>
			<textarea id="answerContent" rows="4" cols="50" placeholder="답변 내용을 입력하세요"></textarea>
			<br>
			<button id="submitAnswer">답변 제출</button>
		</div>
	</div>

	<!-- Footer -->
	<custom:footer />
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/browser.min.js"></script>
	<script src="assets/js/breakpoints.min.js"></script>
	<script src="assets/js/util.js"></script>
	<script src="assets/js/main.js"></script>
	<!-- jQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	
	<script>
		// 모달 창 동작 관련 스크립트
		$(document).ready(function(){
			var modal = $('#detailModal');
			var span = $('.close');
			
			// 모달 열기
			$('.viewDetailsBtn').on('click', function(){
				var title = $(this).data('title');
				var content = $(this).data('content');
				
				$('#modalTitle').text(title);
				$('#modalContent').text(content);
				
				modal.show();
			});

			// 모달 닫기
			span.on('click', function() {
				modal.hide();
			});
			
			// 답변 제출
			$('#submitAnswer').on('click', function(){
				var answerContent = $('#answerContent').val();
				if(answerContent.trim() !== "") {
					// 이곳에 답변 제출 로직 추가
					alert("답변이 제출되었습니다: ");
				} else {
					alert("답변을 입력하세요.");
				}
			});
			
			// 모달 창 외부를 클릭하면 모달 닫기
			$(window).on('click', function(event){
				if ($(event.target).is(modal)) {
					modal.hide();
				}
			});
		});
	</script>
	
</body>
</html>