console.log('loginGoogleAPI');

//전역 함수로 설정
window.handleCredentialResponse = function (response) {
	console.log('handleCredentialResponse 호출');
	//토큰 값을 디코딩해서 JSON 파일로 받아옵니다.
	//decodeJwtResponse <- 디코딩하는 함수
	const responsePayload = decodeJwtResponse(response.credential);
	
	//디코딩한 정보를 콘솔창에 출력합니다.
	console.log('Full Name: ' + responsePayload.name);
	console.log('Email: ' + responsePayload.email);
	
	//값 전송
	sendforwardGooglelogin(responsePayload.name, responsePayload.email)
}

//디코딩 함수
function decodeJwtResponse(id_token) {
	console.log('decodeJwtResponse 호출');
	//받아온 토큰 값을 디코딩하여 정보를 가져옵니다.
	//id_token을 '.'으로 나누어 중간에 있는 payload 부분(base64Url)을 추출합니다.
	const base64Url = id_token.split('.')[1];
	//URL-safe Base64 형식에서 표준 Base64 형식으로 변환합니다. ('-' -> '+', '_' -> '/')
	const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
	//Base64로 인코딩된 문자열을 디코딩하고 각 문자의 유니코드 값을 %인코딩된 형식으로 변환한 후, 이를 다시 문자열로 조합하여 JSON 형식의 payload를 만듭니다.
	const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
		//각 문자의 유니코드 값을 %XX 형식으로 변환합니다.
		return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
	}).join('')); //변환된 값을 하나의 문자열로 조합합니다.
	//최종적으로 JSON 타입으로 변환해 반환합니다.
	return JSON.parse(jsonPayload);
}


//받아온 값을 보내는 함수
function sendforwardGooglelogin(fullname, email) {
    //새로운 폼 요소 생성합니다.
    let googleloginForm = document.createElement("form");
	googleloginForm.style.display = "none";
    googleloginForm.method = "POST"; // POST 요청 방식
    googleloginForm.action = "login.do"; // 요청을 보낼 URL

    //이름 값을 보낼 input태그 만들기
    let nameField = document.createElement("input");
    nameField.type = "hidden"; // 폼에 표시되지 않도록 숨김 필드로 설정
    nameField.name = "name"; // 서버에서 받을 변수 이름
    nameField.value = fullname; // 보낼 데이터

    //이메일값을 보낼 input태그 만들기
    let emailField = document.createElement("input");
    emailField.type = "hidden";
    emailField.name = "email";
    emailField.value = email;
	
    //구글 로그인임을 알려줄 input태그 만들기
    let typeField = document.createElement("input");
    typeField.type = "hidden";
    typeField.name = "type";
    typeField.value = "googleLogin";

	//만든 input태그를 form에 추가합니다.
    googleloginForm.appendChild(nameField);
    googleloginForm.appendChild(emailField);
    googleloginForm.appendChild(typeField);
    //폼을 현재 페이지에 추가한 후 전송합니다.
    document.body.appendChild(googleloginForm);
    googleloginForm.submit(); 
}