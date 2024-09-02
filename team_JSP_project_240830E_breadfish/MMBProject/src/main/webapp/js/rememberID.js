window.onload=function(){
	// 페이지 로드 시작
	const savedEmail = getCookie("savedEmail");
	if(savedEmail){
		document.getElementById("emiail").value = savedEmail;
		document.getElementById("exampleCheck").checked = true;
		// 체크 박스 체크 상태 유지
	}
}

//쿠키에서 특정 이름 값을 가져오는 함수
function getCookie(name){
	let cookies = document.cookie.split("; ");
	for(let i = 0; i<cookies.length; i++){
		let cookiePair = cookies[i].split("=");
		if(cookiePair[0]==name){
			return decodeURIComponent(cookiePair[1]);
		}
	}
	return null;
}

// 로그인 폼 제출시 실행
function saveEmail(){
	const email = document.getElementById("email").value;
	const saveEmailCheckbox = document.getElementById("exampleCheck").checked;
	
	if(saveEmailCheckbox){
		// 쿠키에 이메일 저장(30일 만료)
		document.cookie="savedEmail = "+encodeURIComponent(email)+"; path=/; max-age="+30*24*60*60;
	}
	else{
		// 쿠키 삭제
		document.cookie = "savedEmail=; path=/; max-age=0"
	}
}