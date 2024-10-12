<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<meta name="viewport"
   content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
</head>
<body>
   <!-- Header -->
   <custom:header />
   <!-- MainContent -->
   <!-- 상단 프로필 영역 -->
   <section id="content">
      <div class="container">
         <div class="row">
            <div class="col-9 col-12-medium">

               <!-- Main Content -->
               <section>
                  <header>
                     <h2>관리자 프로필</h2>
                  </header>
                  <div class="">
                     <div class="">
                        <img src="${member.memberProfileWay}" style="width: 50%; height: auto;" alt="프로필 사진"
                           id="profileImage">
                     </div>
                     <div class="">
                        <h2 id="nickName">${member.memberNickname}</h2>
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
                  </div>
               </section>
            </div>
            <custom:adminMenu/>
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

</body>
</html>