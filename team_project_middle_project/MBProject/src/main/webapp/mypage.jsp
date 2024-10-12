<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>갈빵질빵 - 마이페이지</title>
<meta charset="utf-8" />
<meta name="viewport"
   content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
</head>
<body>
   <custom:header />

   <!-- 상단 프로필 영역 -->
   <section id="content">
      <div class="container">
         <div class="row">
               <div class="col-9 col-12-medium">
                  <section>
                     <div class="">
                        <img src="${member.memberProfileWay}" style="width: 50%; height: auto;" alt="프로필 사진"
                           id="profileImage">
                     </div>
                     <div class="profile-info">
                        닉네임
                        <h2 id="userNickname">${member.memberNickname}</h2>
                        <a href="updateProfilePage.do"><button class="btn-edit">프로필
                              수정</button></a>
                        <div>
                           <table>
                              <tr>
                                 <td><strong id="email" style="white-space: nowrap;">이메일</strong></td>
                                 <td>${member.memberEmail}</td>
                              </tr>
                              <tr>
                                 <td><strong id="name" style="white-space: nowrap;">이름</strong></td>
                                 <td>${member.memberName}</td>
                              </tr>
                              <tr>
                                 <td><strong id="phoneNum" style="white-space: nowrap;">전화번호</strong></td>
                                 <td>${member.memberPhone}</td>
                              </tr>
                           </table>
                        </div>
                     </div>
                  </section>
               </div>
               <custom:userMenu />
            </div>
         </div>
   </section>
   <custom:footer />
   <!-- Scripts -->
   <script src="assets/js/jquery.min.js"></script>
   <script src="assets/js/browser.min.js"></script>
   <script src="assets/js/breakpoints.min.js"></script>
   <script src="assets/js/util.js"></script>
   <script src="assets/js/main.js"></script>
   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</body>
</html>