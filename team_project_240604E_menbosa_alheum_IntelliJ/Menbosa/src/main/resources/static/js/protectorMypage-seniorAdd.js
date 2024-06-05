//인증하기 버튼
let $certiMessage = $("#certiMessage");
//input 폰번호
let $mid = $("#phoneNum-second");
let $back = $("#phoneNum-last");
//input 이름
let $name = $("#seniorname");

//어디서 들어온 데이터인지 구분하기 위한 데이터
let division = 2;
//TODO proMem = 1, senMem:jiyoon = 2

//초기값
let phoneNum = "";
let name = "";


$certiMessage.on("click", function () {
    if($mid.val().length === 4 || $back.val().length === 4) {
        //입력값 가져옴
        phoneNum = "010"+$mid.val()+$back.val();
        name = $name.val().trim();

        console.log(phoneNum);
        console.log($name.val().trim());

        //TODO 실제로 문자 발송됩니다!!!!! DB에 본인 번호 데이터 넣어서 테스트 본인 번호로 진행하세요!!!!!!!!
        //TODO 실제로 문자 발송됩니다!!!!! DB에 본인 번호 데이터 넣어서 테스트 본인 번호로 진행하세요!!!!!!!!

        $.ajax({
            url: `/sms/send-one`,
            type: 'post',
            data: {
                phoneNum: phoneNum,
                division: division,
                name: name
            },
            success: function(response) {
                // 성공 응답 처리
                console.log('메시지 전송 성공:', response);
                alert("인증번호를 확인 후 입력해주세요.");
            },
            error: function(xhr, status, error) {
                // 오류 응답 처리
                console.error('메시지 전송 오류:', status, error);
                alert("휴대폰 번호 및 성함이 어르신 계정과 일치하는지 다시 확인해주세요.");
            }
        });
    } else {
        alert("휴대폰 번호를 다시 확인해주세요.");
    }
});