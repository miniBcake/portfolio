<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<!DOCTYPE HTML>

<html>
<head>
<title>갈빵질빵 - 비밀번호 찾기</title>
<meta charset="utf-8" />
<meta name="viewport"
   content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
<link rel="stylesheet" href="assets/css/modal.css" />

</head>
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
                  <h2>비밀번호 찾기</h2>
                  <h3>이름과 이메일로 비밀번호 찾기</h3>
               </header>
               <form id="findPWForm" method="POST">
                  <div class="">
                     <input type="text" class="" id="name" name="name"
                        placeholder="이름을 입력하세요" required> <label for="name">이름
                        입력</label><br> <input type="email" class="" id="email" name="email"
                        placeholder="이메일을 입력하세요" required> <label for="email">이메일
                        입력</label>
                        <button type="button" id="sendNum">인증번호 보내기</button>
                        <span id="sendEmailMsg">이메일 인증번호 보내기 확인</span><br>
                        <input type="text" id="checkNum" placeholder="인증번호 입력" required="required">
                        <label for="email">인증번호 입력</label>
                        <button type="button" id="checkNumBtn">인증번호 확인</button>
                        <span id="checkNumMsg">인증번호를 확인해주세요</span><br>
                     <button type="button" id="findPw">비밀번호 찾기</button>
                  </div>
               </form>
               <!-- 모달창 -->
               
               <div id="myModal" class="modal">
                  <div class="modal-content">
                     <span id="close" class="close">&times;</span>
                     <div class="modal-header">
                        <h4>비밀번호 변경</h4>
                     </div>
                     <form action="updatePw.do" id="updatePw">
                     <div class="modal-body">
                     <input type="hidden" name="memberPK" id="memberPK">              
                        <input type="password" class="" id="password" name="password"
                           placeholder="변경할 비밀번호 입력"> <label for="password"
                           style="white-space: nowrap;">변경하실 비밀번호를 입력하세요</label><br>
                        <input type="password" class="" id="password2" name="password2"
                           placeholder="변경할 비밀번호 확인"> <label for="password2"
                           style="white-space: nowrap;">입력하신 비밀번호를 확인하세요</label><br>
                     </div>
                     <div class="modal-footer">
                        <button type="submit" id="updatePWBtn" class="" disabled>비밀번호
                           변경</button>
                     </div>
                     </form>
                  </div>
               </div>

               <!-- 모달 끝 -->
            </section>

         </div>
      </div>
   </div>
   </section>

<!-- Footer -->
<custom:footer />

<!-- Scripts -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/browser.min.js"></script>
<script src="assets/js/breakpoints.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/main.js"></script>
<script type="text/javascript">
  // 회원 정보 확인하기
  function emailNameCheck(e) {
    e.preventDefault(); // 폼의 기본 동작(페이지 리로드)을 막음

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;

    // AJAX로 회원정보 확인하기
    $.ajax({
      url: 'emailNameCheck',  // 서버에 회원 정보 확인 요청
      type: 'POST',
      data: {
        name: name,
        email: email
      },
      //dataType: "json",
      success: function(response) {
        // 성공: 이메일 인증번호 발송
        if (response.result) {
           console.log("로그 : response.result : "+ response.result);
           console.log("로그 : response.memberPK : "+ response.memberPK);
           console.log("로그 : 이메일 인증 시작 data : "+response);
           document.getElementById('memberPK').value = response.memberPK;
           console.log("로그 : memberPK : "+document.getElementById('memberPK').value);
          sendEmail();  // 인증 이메일 전송
        } else {
          alert('입력하신 정보가 잘못되었습니다.');
        }
      },
      error: function() {
        alert('서버와 통신에 문제가 발생했습니다.');
      }
    });
  }

  var sendEmailResult = false;
  var checkNumResult = false;

  // 이메일 전송 함수
  function sendEmail() {
    const email = document.getElementById('email').value;
    console.log("로그 : 이메일에 인증번호 전송");

    $.ajax({
      url: 'sendEmail',  // 이메일로 인증번호 전송 요청
      method: 'POST',
      data: {
        email: email
      },
      success: function(data) {
        sendEmailResult = data === 'true';
        console.log("이메일 인증 성공 : "+data);
        if (sendEmailResult) {
          $("#sendEmailMsg").text("인증번호 전송 완료").css('color', 'green');
        } else {
          $("#sendEmailMsg").text("인증번호 전송 실패").css('color', 'red');
        }
      },
      error: function() {
        alert('인증번호 전송 중 오류가 발생했습니다.');
        console.log("로그 : 인증번호 전송 실패");
      }
    });
  }

  // 인증번호 확인 함수
  function checkNum() {
     //인증번호 값
    const checkNum = document.getElementById('checkNum').value;
    console.log("로그 : 인증번호 확인");

    $.ajax({
      url: 'emailNumCheck',  // 서버에 인증번호 확인 요청
      method: 'POST',
      data: {
        checkNum: checkNum
      },
      success: function(data) {
        checkNumResult = data === 'true';
        console.log("인증번호 확인 data : "+data);
        if (checkNumResult) {
          $("#checkNumMsg").text("인증번호 일치").css('color', 'green');
        } else {
          $("#checkNumMsg").text("인증번호가 일치하지 않습니다.").css('color', 'red');
          document.getElementById('checkNum').value = ''; // 입력란 비우기
          document.getElementById('checkNum').focus();  // 입력창으로 이동
        }
      },
      error: function() {
        alert('인증번호 확인 중 오류가 발생했습니다.');
        console.log("로그 : 인증번호 확인 실패");
      }
    });
  }

  // 이메일 전송 버튼 클릭 이벤트
  document.getElementById('sendNum').addEventListener('click', function(e) {
    emailNameCheck(e);
    alert('이메일에서 인증번호를 확인해주세요.');
  });

  // 인증번호 확인 버튼 클릭 이벤트
  document.getElementById('checkNumBtn').addEventListener('click', function() {
    $('#checkNumMsg').text("");  // 기존 메시지 초기화
    if (document.getElementById('checkNum').value !== '') {
       console.log("로그 : 인증번호 확인 시작");
      checkNum();
    } else {
       console.log("로그 : 인증번호 미입력");
      alert('인증번호를 입력해주세요.');
    }
  });

  // 비밀번호 찾기 버튼 클릭 이벤트
  document.getElementById('findPw').addEventListener('click', function() {
    if (checkNumResult) {
      document.getElementById('myModal').style.display = 'block';  // 모달 열기
    } else {
      alert('인증번호를 확인해주세요');
    }
  });

  // 모달 창 닫기
  document.getElementById('close').addEventListener('click', function() {
    document.getElementById('myModal').style.display = 'none';  // 모달 닫기
  });

  // 모달 창 바깥을 클릭 시 창 닫기
  window.onclick = function(event) {
    const modal = document.getElementById('myModal');
    if (event.target == modal) {
      modal.style.display = 'none';
    }
  };

  // 비밀번호와 비밀번호 확인이 일치할 때만 비밀번호 변경 버튼 활성화
  document.addEventListener('input', function() {
    const password = document.getElementById('password').value;
    const password2 = document.getElementById('password2').value;
    const updatePWBtn = document.getElementById('updatePWBtn');

    if (password === password2 && password !== "") {
      updatePWBtn.disabled = false;  // 비밀번호 변경 버튼 활성화
    } else {
      updatePWBtn.disabled = true;  // 비밀번호 불일치 시 버튼 비활성화
    }
  });
</script>



</body>
</html>