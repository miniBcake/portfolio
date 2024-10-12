<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"
   import="java.util.ArrayList, java.util.HashMap, java.util.Map, java.util.List"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>

<html>
<head>
<title>Two Column 2 - Halcyonic by HTML5 UP</title>
<meta charset="utf-8" />
<meta name="viewport"
   content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
<link rel="stylesheet" href="assets/css/pagination.css">

<!-- dropdown 박스 style -->
<style>
        .dropdown {
            width: 250px;
            margin: 20px;
        }
        .dropdown select {
            width: 100%;
            padding: 4px; /* 패딩 줄이기 */
           font-size: 16px; /* 폰트 크기 줄이기 */
        }
</style>

</head>
<body class="subpage">
   <div id="page-wrapper">

      <custom:header />

      <!-- Content -->
      <section id="content">
         <div class="container">
            <div class="row">
               <div class="col-3 col-12-medium">

                  <!-- Sidebar -->
                  <section>
                     <header>
                        <h5>다른 가게 보러 가기!</h5>
                     </header>
                     <ul class="link-list">
                        <li><a href="searchStore.do?">가게 목록으로 돌아가기</a></li>
                     </ul>
                     <c:if test="${memberRole == 'ADMIN'}">
                     <hr>
                        <div class="pagination">
                                    <button onclick="viewStore('${storeData.storeNum}')">가게 정보 수정하기</button>
                                </div>
                        </c:if>
                  </section>
               </div>
               <div class="col-9 col-12-medium imp-medium">

                  <!-- Main Content -->
                  <section>
                     <header>
                                <h2>가게 이름 : ${storeData.storeName}</h2><br>
                                <h3>가게 번호 : <c:if test="${empty storeData.storePhoneNum}"> 
                                      연락처 정보 없음
                                  </c:if><br>
                                  <c:if test="${not empty storeData.storePhoneNum}">
                                      <span id="storePhoneNumber">${storeData.storePhoneNum}</span>
                                      <button onclick="copyStorePhoneNumber('${storeData.storePhoneNum}')">전화번호 복사</button>
                                  </c:if><br>
                                <h3>가게 주소 : ${storeData.storeDefaultAddress}</h3><br>
                                <h3>상세 주소 : ${storeData.storeDetailAddress}</h3><br>
                                <h2><c:if test="${storeData.storeClosed == 'Y'}">
                                   폐업
                                   </c:if>
                                   <c:if test="${storeData.storeClosed == 'N'}">
                                   영업 중
                                   </c:if>   
                                </h2>
                                <div class="dropdown">
                                    <label for="workWeekDropdown">영업 요일</label>
                                    <select id="workWeekDropdown">
                                        <c:forEach var="day" items="${allDays}">
                                            <c:set var="hasDetail" value="false" />
                                            <c:forEach var="detail" items="${storeWorkData}">
                                                <c:if test="${detail.storeWorkWeek == day}">
                                                    <c:set var="hasDetail" value="true" />
                                                    <option value="${day}">
                                                        ${day}
                                                        ${detail.storeWorkOpen}~${detail.storeWorkClose} 정상 영업
                                                    </option>
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${!hasDetail}">
                                                <option value="${day}">
                                                    ${day} 휴무일
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </header>
                     <hr>
                     <div>
                        <c:choose>
                           <c:when test="${not empty images}">
                              <c:forEach items="${images}" var="image">
                                 <img src="${image.url}" alt="게시글 이미지"
                                    style="max-width: 100%; height: auto;" />
                              </c:forEach>
                           </c:when>
                           <c:otherwise>
                              <p>등록된 이미지가 없습니다.</p>
                           </c:otherwise>
                        </c:choose>
                     </div>
                     <div>
                     <h2>메뉴 : ${storeMenuData}</h2><br>
                     <h2>결제 방식 : ${storePaymentData}</h2>
                     </div>
                  </section>
               </div>
            </div>
         </div>
      </section>

      <custom:footer />

   </div>

   <!-- Scripts -->
   <script src="assets/js/jquery.min.js"></script>
   <script src="assets/js/browser.min.js"></script>
   <script src="assets/js/breakpoints.min.js"></script>
   <script src="assets/js/util.js"></script>
   <script src="assets/js/main.js"></script>

   <script>        
        // 복사하기 버튼 눌렀을 때 수행 되는 함수
           function copyStorePhoneNumber(storeContact) {
               navigator.clipboard.writeText(storeContact).then(function() {
                   alert('전화 번호를 복사 했습니다!');
               }, function(err) {
                   alert('복사에 실패 했습니다..: ', err);
               });
           }
           
        function viewStore(storeNum){
               window.location.href = 'updateStorePage.do?storeNum=' + encodeURIComponent(storeNum);
        }
      
    </script>

</body>
</html>