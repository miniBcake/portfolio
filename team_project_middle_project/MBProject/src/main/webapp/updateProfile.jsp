<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>갈빵질빵 - 프로필 수정</title>
</head>
<meta name="viewport"
   content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />

<body>
   <!-- Header -->
   <custom:header />
   <section id="content">
      <div class="container">
         <div class="row">
            <div class="col-12">
               <!-- Main Content -->
               <section>
                  <h2>프로필 수정</h2>
                  <div class="new-members-table-container">
                     <form action="updateProfile.do" method="POST"
                        enctype="multipart/form-data">
                        <table>
                           <tbody>
                              <tr>
                                 <td>닉네임</td>
                                 <td><input type="text" id="nickName" name="nickName"
                                    value="${member.memberNickname}"></input></td>
                                 <td><button type="button" id="checkNickNameBtn"
                                       style="margin: 5px">중복확인</button></td>
                                 <td><span id="checkNickNameMsg">닉네임 중복확인</span></td>
                              </tr>
                              <tr>
                                 <td>이메일</td>
                                 <td><input type="email" id="email" name="email"
                                    value="${member.memberEmail}"></input></td>
                                 <td><button type="button" id="checkEmailBtn" class=""
                                       style="margin: 5px">중복확인</button></td>

                                 <td><span id="checkEmailMsg">이메일 중복확인</span></td>
                              </tr>
                              <tr>
                                 <td>비밀번호</td>
                                 <td><input type="password" name="password"
                                    placeholder="비밀번호 입력"></input></td>
                              </tr>
                              <tr>
                                 <td>전화번호</td>
                                 <td><input type="tel" name="phoneNum"
                                    value="${member.memberPhone}"
                                    pattern="010-[0-9]{4}-[0-9]{4}"></td>
                              </tr>
                              <tr>
                                 <td>프로필 사진</td>
                                 <td><div class="mb-3">
                                       <label for="formFile" class="form-label">수정할 프로필 사진</label>
                                       <input class="form-control" type="file" id="profilePic"
                                          value="${member.memberProfileWay}" name="profilePic">
                                    </div></td>
                              </tr>
                           </tbody>
                        </table>
                                 <input type="submit" id="submitBtn" value="변경하기"></input>
                     </form>
                  </div>
               </section>

            </div>
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
   <script type="text/javascript">
   $(document).ready(function() {
      var isNickNameChecked = false;  // 닉네임 중복 확인 여부
       var isEmailChecked = false;     // 이메일 중복 확인 여부

       // 닉네임 중복 확인 함수
       function checkNickNameFunction() {
           console.log("로그 : 닉네임 중복확인 시작");
           var nickName = $('#nickName').val();
           $.ajax({
               url: 'checkNickName',
               method: 'POST',
               data: { nickName: nickName },
               success: function(data) {
                   console.log("[" + data + "]");
                   if (data === 'true') {
                       $("#checkNickNameMsg").text("사용가능한 닉네임입니다.").css('color', 'green');
                       isNickNameChecked = true;  // 중복 확인 성공 시 true로 설정
                   } else {
                       $("#checkNickNameMsg").text("이미 사용중인 닉네임입니다.").css('color', 'red');
                       $('#nickName').val('').focus();
                   }
                   console.log("로그 : 닉네임 중복확인 종료");
               },
               error: function() {
                   alert('닉네임 중복 확인 중 오류가 발생했습니다.');
                   console.log("로그 : 닉네임 중복확인 종료(오류발생)");
               }
           });
       }

       // 이메일 중복 확인 함수
       function checkEmailFunction() {
           console.log("로그 : 이메일 중복확인 시작");
           var email = $('#email').val();
           $.ajax({
               url: 'checkEmail',
               method: 'POST',
               data: { email: email },
               success: function(data) {
                   console.log("[" + data + "]");
                   if (data === 'true') {
                       $("#checkEmailMsg").text("사용 가능한 이메일입니다.").css('color', 'green');
                       isEmailChecked = true;  // 중복 확인 성공 시 true로 설정
                   } else {
                       $("#checkEmailMsg").text("이미 사용중인 이메일입니다.").css('color', 'red');
                       $('#email').val('').focus();
                   }
                   console.log("로그 : 이메일 중복확인 종료");
               },
               error: function() {
                   alert('이메일 중복 확인 중 오류가 발생했습니다.');
                   console.log("로그 : 이메일 중복확인 종료(오류발생)");
               }
           });
       }

    // 닉네임 중복 확인 버튼 클릭
       var nick = "${member.memberNickname}";
       $('#checkNickNameBtn').on('click', function() {
           if ($('#nickName').val() !== '') {
               if($('#nickName').val() === nick){
                   $("#checkNickNameMsg").text("기존의 닉네임과 동일한 닉네임입니다.").css('color', 'red');
                   isNickNameChecked = true;  // 중복 확인 성공 시 true로 설정
               } else {
                   checkNickNameFunction();
               }
           } else {
               alert('닉네임을 입력해주세요.');
           }
       });

       // 이메일 중복 확인 버튼 클릭
       var email = "${member.memberEmail}";
       $('#checkEmailBtn').on('click', function() {
           if ($('#email').val() !== '') {
               if($('#email').val() === email ){
                   $("#checkEmailMsg").text("기존의 이메일과 동일한 이메일입니다.").css('color', 'red');
                   isEmailChecked = true;  // 중복 확인 성공 시 true로 설정
               } else {
                   checkEmailFunction();
               }
           } else {
               alert('이메일을 입력해주세요.');
           }
       });
       
    // 폼 제출 시 중복 확인 여부 검사
       $('#submitBtn').on('click', function(event) {
           if (!isNickNameChecked) {
               alert('닉네임 중복 확인을 해주세요.');
               event.preventDefault();  // 폼 제출 막기
               return false;
           }
           if (!isEmailChecked) {
               alert('이메일 중복 확인을 해주세요.');
               event.preventDefault();  // 폼 제출 막기
               return false;
           }
       });
   });

    </script>

</body>
</html>