<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<!DOCTYPE HTML>

<html>
<head>
<title>갈빵질빵 - 구글 로그인   회원가입 페이지</title>
<meta charset="utf-8" />
<meta name="viewport"
   content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
</head>
<body>
   <!-- header 커스텀 태그 -->
   <custom:header />

   <!-- Content -->
   <section id="content">
      <div class="container">
         <div class="row">
            <div class="col-12">

               <!-- Main Content -->
               <section>
                  <h3 style="white-space: nowrap">회원가입</h3>
                  <form action="join.do" id="joinForm" method="POST"
                     enctype="multipart/form-data">
                     <div class="">
                        <select name="role" class="form-select" required>
                           <option value="USER">사용자</option>
                           <option value="SELLER">판매자</option>
                        </select> <label for="role">권한을 선택하세요</label>
                     </div>
                     <div class="">
                        <input type="text" class="" id="name" name="name"
                           value="${name}" readonly> <label for="name">이름을
                           입력하세요</label>
                     </div>
                     <div class="">
                        <input type="text" class="" id="nickName" name="nickName"
                           placeholder="닉네임을 입력하세요" required> <label
                           for="nickName">닉네임을 입력하세요</label>
                        <button type="button" id="checkNickNameBtn"
                           class="btn btn-secondary" style="margin: 5px">중복확인</button>
                        <span id="checkNickNameMsg">닉네임 중복확인</span>
                     </div>
                     <div class="">
                        <input type="email" class="" id="email" name="email"
                           value="${email}" readonly> <label
                           for="email">이메일을 입력하세요</label>
                     </div>
                     
                     <div class="">
                        <input type="password" class="" id="password" name="password"
                           placeholder="비밀번호를 입력하세요" required> <label
                           for="password">비밀번호를 입력하세요</label>
                     </div>
                     <div class="">
                        <input type="password" class="" id="password2" name="password2"
                           placeholder="비밀번호를 확인하세요" required> <label
                           for="password">비밀번호를 확인하세요</label>
                        <button type="button" id="checkPasswordBtn">비밀번호 확인</button>
                        <span id="checkPassword">비밀번호 확인</span>
                     </div>

                     <div class="">
                        <input type="tel" class="" id="phoneNum"
                           placeholder="전화번호를 입력하세요" pattern="010-[0-9]{4}-[0-9]{4}">
                        <label for="phoneNum" style="white-space: nowrap">전화번호를
                           입력하세요(선택)</label>
                     </div>

                     <div class="">
                        <div class="mb-3">
                           <label for="prodfilePic" class="form-label">프로필 사진 업로드</label> <input
                              class="" type="file" id="profilePic" name="profilePic">
                        </div>
                     </div>
                     <button type="submit" class="">회원가입</button>
                  </form>
                  <p class="">
                     이미 계정이 있으신가요? <a href="loginPage.do">로그인</a>
                  </p>
               </section>

            </div>
         </div>
      </div>
   </section>

   <!-- footer -->
   <custom:footer />

   <!-- Scripts -->
   <script src="assets/js/jquery.min.js"></script>
   <script src="assets/js/browser.min.js"></script>
   <script src="assets/js/breakpoints.min.js"></script>
   <script src="assets/js/util.js"></script>
   <script src="assets/js/main.js"></script>
   <script
      src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"
      integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ=="
      crossorigin="anonymous" referrerpolicy="no-referrer"></script>
   <script type="text/javascript">
      $(document).ready(
            function() {
               var checkNickName = false;

               function checkNickNameFunction() {
                  console.log("로그 : 닉네임 중복확인 시작");
                  var nickName = $('input[name="nickName"]').val();
                  // 닉네임 입력값 저장
                  $.ajax({
                     url : "checkNickName",
                     method : "POST",
                     data : {
                        nickName : nickName
                     },
                     success : function(data) {
                        console.log("[" + data + "]");
                        checkNickName = data; //flag(true or false)로 반환
                        if (checkNickName === 'true') {
                           $("#checkNickNameMsg")
                                 .text("사용가능한 닉네임입니다.").css('color',
                                       'green');
                        } else {
                           $("#checkNickNameMsg").text(
                                 "이미 사용중인 닉네임입니다.").css('color',
                                 'red');

                           // 닉네임이 중복인 경우, 입력란을 비우고, 입력창으로 이동
                           $("#nickName").val('').focus();
                        }
                        console.log("로그 : 닉네임 중복확인 종료");
                     },
                     error : function(error) {
                        alert('닉네임 중복 확인 중 오류가 발생했습니다.');
                        console.log("로그 : 닉네임 중복확인 종료(오류발생)");
                     }
                  });
               }

               $('#checkNickNameBtn').on('click', function() {
                  //버튼 클릭시 메세지 초기화
                  $('#checkNickNameMsg').text("");
                  if ($('input[name="nickName"]').val() !== '') {
                     checkNickNameFunction();
                  } else {
                     alert('닉네임을 입력해주세요.');
                  }
               });

               // 비밀번호 일치 여부 확인
               $('#checkPasswordBtn').on('click', function() {
                  // 입력값 받아오기
                  var password = $('#password').val();
                  var password2 = $('#password2').val();
                  var checkPassword = $('#checkPassword');

                  if (password !== password2) {
                     checkPassword.text("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
                     checkPassword.css('color', 'red');
                     $('password2').val('').focus();

                  } else {
                     checkPassword.text("비밀번호가 일치합니다.");
                     checkPassword.css('color', 'green');
                  }
               });
            });
   </script>
 
</body>
</html>