$(document).ready(function(){
	var checkNickName=false;
	var checkEmail=false;
	
	function checkNickNameFunction(){
		console.log("로그 : 닉네임 중복확인 시작");
		var nickName = $('input[name="nickName"]').val();
		// 닉네임 입력값 저장
		$.ajax({
			url : "checkNickName",
			method: "POST",
			data: {nickName: nickName},
			success:function(data){
				console.log("["+data+"]");
				checkNickName = data; //true or false
				if(checkNickName==='true'){
					$("#checkNickNameMsg").text("사용가능한 닉네임입니다.").css('color','green');
				}else{
					$("#checkNickNameMsg").text("이미 사용중인 닉네임입니다.").css('color','red');
				}
				console.log("로그 : 닉네임 중복확인 종료");
			},
			error : function(error){
				alert('닉네임 중복 확인 중 오류가 발생했습니다.');
				console.log("로그 : 닉네임 중복확인 종료(오류발생)");
			}
		});
	}
	
	function checkEmailFunction(){
		console.log("로그 : 이메일 중복확인 시작");
		var email = $('input[name ="email"]').val();
		
		$.ajax({
			method : "POST",
			url:"checkEmail",
			data : {email:email},
			success:function(data){
				console.log("["+data+"]");
				checkEmail = data; //true or false
				if(checkEmail ==='true'){
					$("#checkEmailMsg").text("사용 가능한 이메일입니다.").css('color','green');
				}else{
					$("#checkEmailMsg").text("이미 사용중인 이메일입니다.").css('color','red');
				}
				console.log("로그 : 이메일 중복확인 종료");
			},
			error: function(error){
				alert('이메일 중복 확인 중 오류가 발생했습니다.');
				console.log("로그 : 이메일 중복확인 종료(오류발생)");
			}
		});
		
	}
	
	$('#checkNickNameBtn').on('click', function(){
		//버튼 클릭시 메세지 초기화
		$('#checkNickNameMsg').text("");
			if($('input[name="nickName"]').val() !==''){
				checkNickNameFunction();
			}else{
				alert('닉네임을 입력해주세요.');
			}
		});
	
	$('#checkEmailBtn').on('click', function(){
		//버튼 클릭시 메세지 초기화
				$('#checkEmailMsg').text("");
		if($('input[name="email"]').val() !==''){
			checkEmailFunction();
		}else{
			alert('이메일을 입력해주세요.');
		}
	});
	
	$('#joinForm').on('submit',function(){
		if(!checkNickName){
			alert('닉네임 중복 검사를 완료해주세요!');
			console.log("닉네임 중복 검사 미실행");
			return false;
		}
		
		if(!checkEmail){
			alert('이메일 중복검사를 완료해주세요!');
			console.log("이메일 중복 검사 미실행");
			return false;
		}
	});
	
});