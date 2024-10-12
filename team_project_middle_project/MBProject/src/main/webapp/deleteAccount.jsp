<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>

<!DOCTYPE HTML>
<html>
<head>
<title>갈빵질빵 - 회원탈퇴</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
</head>
<custom:header/>
<section id="content">
					<div class="container">
						<div class="row">
							<div class="col-12">
							<!-- Main Content -->
							<section>
							<!-- 회원탈퇴 Start -->

		<h3 style="white-space: nowrap">회원탈퇴 확인</h3>

		<div class="" id="deleteForm">
			<button type="button" id="delButton" class="">회원탈퇴</button>
		</div>
		<!-- 회원탈퇴 모달창 -->
		<div id="myModal" class="modal" style="display: none">
			<div class="modal-content">
				<span id="close" class="close">&times;</span>
				<div class="modal-header">
					<h4>회원탈퇴 확인</h4>
				</div>
				<div class="modal-body">
					<input type="text" class="form-control" id="checkText"
						placeholder="회원탈퇴 입력"> <label for="checkText"
						style="white-space: nowrap;">회원탈퇴를 원하시면 회원탈퇴를 정자로 기입하세요!</label><br>
				</div>
				<div class="modal-footer">
					<button type="button" id="deleteButton"
						class="btn btn-primary py-3 w-100 mb-4" disabled>회원탈퇴</button>
				</div>
			</div>
		</div>
							
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
<script type="text/javascript">
			 
			 //DOM이 완전히 로드된 후에 실행
			 document.addEventListener('DOMContentLoaded', function() {           	
			 //delButton  클릭
	                    	document.getElementById('delButton').addEventListener('click',function(){
	                    		// 모달창 열기
	                    		document.getElementById('myModal').style.display='block';
	                    		
	                    	});
			 
	                    	
	                    	//close(x) 클릭
	                    	document.getElementById('close').addEventListener('click', function(){
	                    		// 모달창 닫기
	                    		document.getElementById('myModal').style.display='none';
	                    		
	                    	});
	                    	
	                    	// 모달창 바깥 클릭
	                    	window.onclick =function(event){
	                    		const modal =document.getElementById('myModal');
	                    		
	                    		if(event.target==modal){
	                    			// 모달창 닫기
	                    			modal.style.display ='none';
	                    		}
	                    	};
	                    	
	                    	// 회원탈퇴 입력 시에만 회원탈퇴 버튼 활성화
	                    	document.addEventListener('input', function() {
	                    	        const inputText = document.getElementById('checkText').value;
	                    	        const deleteButton = document.getElementById('deleteButton');
	                    	
	                    	        if (inputText === '회원탈퇴') {
	                    	            deleteButton.disabled = false;
	                    	        } else {
	                    				
	                    	            deleteButton.disabled = true;
	                    	        }
	                    	    });
	                    	
	                    	    // 회원탈퇴 버튼(deleteButton 클릭
	                    	    document.getElementById('deleteButton').addEventListener('click', function() {
	                    	        // 회원탈퇴 기능 수행
	                    			window.location.href = 'deleteAccount.do'; 
	                    	    });
			 });
	                    	    </script>

</body>
</html>