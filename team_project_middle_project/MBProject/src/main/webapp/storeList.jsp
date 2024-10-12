<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"
   import="java.util.ArrayList, java.util.HashMap, java.util.Map, java.util.List"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>

<html>
<head>
<title>가게 목록</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
<link rel="stylesheet" href="assets/css/pagination.css">

<style type="text/css">
.custom-checkbox {
   display: inline-block;
   position: relative;
   padding-left: 35px; /* 체크박스와 라벨 텍스트 사이의 여백 */
   cursor: pointer;
   font-size: 16px; /* 텍스트 크기 조정 */
}

.custom-checkbox input {
   position: absolute;
   opacity: 0;
   cursor: pointer;
}

.checkmark {
   position: absolute;
   top: 0;
   left: 0;
   height: 25px; /* 체크박스 높이 */
   width: 25px; /* 체크박스 너비 */
   background-color: #eee;
   border-radius: 5px; /* 둥근 모서리 */
}

.custom-checkbox input:checked ~ .checkmark {
   background-color: #2196F3;
}

.checkmark:after {
   content: "";
   position: absolute;
   display: none;
}

.custom-checkbox input:checked ~ .checkmark:after {
   display: block;
   left: 9px;
   top: 5px;
   width: 5px;
   height: 10px;
   border: solid white;
   border-width: 0 3px 3px 0;
   transform: rotate(45deg);
}

</style>
   
</head>
<body>
   <div id="page-wrapper">
      
   <!-- Header 커스터 태그 -->
   <custom:header/>
   
   <!-- 폼 태그를 추가하여 모든 검색 필터와 입력 필드를 포함 -->
   <form id="filterForm" action="searchStore.do" method="GET">
      <!-- 붕어빵 선택 filter 검색 시작 -->
      <section id="features">
         <div class="container">
            <section>
               <header>
                        <h2>좋아하는 붕어빵을 선택해서 판매 가게를 찾아보세요!</h2>
                    </header>
                    <div class="row">
                        <div class="col-3 col-6-medium col-12-small">
                            <section>
                                <label class="custom-checkbox">
                                    <input type="checkbox" name="storeMenu" value="팥/슈크림" id="num1-pot"
                                        ${storeMenu != null && fn:contains(storeMenu.storeMenuNomal, 'Y') ? 'checked' : ''}>
                                    <span class="checkmark"></span> 팥 / 슈크림 (${storeMenuCNT.get(0)})
                                </label>
                            </section>
                        </div>
                        <div class="col-3 col-6-medium col-12-small">
                            <section>
                                <label class="custom-checkbox">
                                    <input type="checkbox" name="storeMenu" value="야채/김치/만두" id="num1-mando"
                                        ${storeMenu != null && fn:contains(storeMenu.storeMenuVegetable, 'Y') ? 'checked' : ''}>
                                    <span class="checkmark"></span> 야채 / 김치 / 만두 (${storeMenuCNT.get(1)})
                                </label>
                            </section>
                        </div>
                        <div class="col-3 col-6-medium col-12-small">
                            <section>
                                <label class="custom-checkbox">
                                    <input type="checkbox" name="storeMenu" value="미니" id="num1-min"
                                        ${storeMenu != null && fn:contains(storeMenu.storeMenuMini, 'Y') ? 'checked' : ''}>
                                    <span class="checkmark"></span> 미니 붕어빵 (${storeMenuCNT.get(2)})
                                </label>
                            </section>
                        </div>
                        <div class="col-3 col-6-medium col-12-small">
                            <section>
                                <label class="custom-checkbox">
                                    <input type="checkbox" name="storeMenu" value="고구마" id="num1-sweetpotato"
                                        ${storeMenu != null && fn:contains(storeMenu.storeMenuPotato, 'Y') ? 'checked' : ''}>
                                    <span class="checkmark"></span> 고구마 (${storeMenuCNT.get(3)})
                                </label>
                            </section>
                        </div>
                        <div class="col-3 col-6-medium col-12-small">
                            <section>
                                <label class="custom-checkbox">
                                    <input type="checkbox" name="storeMenu" value="아이스크림/초코" id="num1-ice"
                                        ${storeMenu != null && fn:contains(storeMenu.storeMenuIceCream, 'Y') ? 'checked' : ''}>
                                    <span class="checkmark"></span> 아이스크림 / 초코 (${storeMenuCNT.get(4)})
                                </label>
                            </section>
                        </div>
                        <div class="col-3 col-6-medium col-12-small">
                            <section>
                                <label class="custom-checkbox">
                                    <input type="checkbox" name="storeMenu" value="치즈" id="num1-cheese"
                                        ${storeMenu != null && fn:contains(storeMenu.storeMenuCheese, 'Y') ? 'checked' : ''}>
                                    <span class="checkmark"></span> 치즈 (${storeMenuCNT.get(5)})
                                </label>
                            </section>
                        </div>
                        <div class="col-3 col-6-medium col-12-small">
                            <section>
                                <label class="custom-checkbox">
                                    <input type="checkbox" name="storeMenu" value="패스츄리" id="num1-pastry"
                                        ${storeMenu != null && fn:contains(storeMenu.storeMenuPastry, 'Y') ? 'checked' : ''}>
                                    <span class="checkmark"></span> 패스츄리 (${storeMenuCNT.get(6)})
                                </label>
                            </section>
                        </div>
                        <div class="col-3 col-6-medium col-12-small">
                            <section>
                                <label class="custom-checkbox">
                                    <input type="checkbox" name="storeMenu" value="기타" id="num1-etc"
                                        ${storeMenu != null && fn:contains(storeMenu.storeMenuOthers, 'Y') ? 'checked' : ''}>
                                    <span class="checkmark"></span> 기타 (${storeMenuCNT.get(7)})
                                </label>
                            </section>
                        </div>
                        <div class="col-3 col-6-medium col-12-small">
                            <section>
                                <label class="custom-checkbox">
                                    <input type="checkbox" id="selectAll">
                                    <span class="checkmark"></span> 전체 선택
                                </label>
                            </section>
                        </div>
                    </div>
                    <br>
                    <hr>
                    <br>
                    <div class="row">
                        <div class="col-6 col-6-medium col-12-small">
                            <section>
                                <header>
                                    <h2>폐업 된 가게 제외</h2>
                                </header>
                            </section>
                            <section>
                                <label class="custom-checkbox">
                                    <input type="checkbox" name="storeClosed" value="open"
                                        ${fn:contains(storeData.storeClosed, 'N') ? 'checked' : ''}>
                                    <span class="checkmark"></span> 영업중인 가게만 보기 (${storeCNT})
                                </label>
                            </section>
                        </div>
                        <div class="col-6 col-6-medium col-12-small">
                            <section>
                                <header>
                                    <h2>결제 방식 선택</h2>
                                </header>
                            </section>
                            <section>
                                <label class="custom-checkbox">
                                    <input type="checkbox" name="storePayment" value="현금결제"
                                        ${fn:contains(storeData.storePaymentCashmoney, 'Y') ? 'checked' : ''}>
                                    <span class="checkmark"></span> 현금 결제 (${storePaymentCNT.get(0)})
                                </label>
                                <label class="custom-checkbox">
                                    <input type="checkbox" name="storePayment" value="카드결제"
                                        ${fn:contains(storeData.storePaymentCard, 'Y') ? 'checked' : ''}>
                                    <span class="checkmark"></span> 카드 결제 (${storePaymentCNT.get(1)})
                                </label>
                                <label class="custom-checkbox">
                                    <input type="checkbox" name="storePayment" value="계좌이체"
                                        ${fn:contains(storeData.storePaymentaccountTransfer, 'Y') ? 'checked' : ''}>
                                    <span class="checkmark"></span> 계좌이체 (${storePaymentCNT.get(2)})
                                </label>
                            </section>
                        </div>
                    </div>
                    <br>
                    <hr>
                    <br>
                    <div class="row">
                        <div class="col-3 col-3-medium col-12-small">
                            <h2>가게 이름으로 검색</h2>
                        </div>
                        <div class="col-9 col-9-medium col-12-small">
                            <div class="row">
                                <div class="col-9">
                                    <input type="search" id="keyword" name="storeName" placeholder="검색어 입력"
                                        value="${param.storeName}" class="search-input" size="100">
                                </div>
                                <div class="col-3">
                                    <button type="submit" class="search-button" style="margin-top: 8px;">
                                        적용된 내용으로 검색
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                 </section>
               </div>
           </section>
        </form>

      <!-- Content -->
      <section id="content">
          <div class="container">
              <div class="row aln-center">
                  <c:if test="${empty stores}">
                      <div class="col-12-medium">
                          검색 하려는 가게가 없습니다..
                      </div>
                  </c:if>

                  <c:if test="${not empty stores}">
                      <c:forEach var="store" items="${stores}">
                          <div class="col-4 col-6-medium col-12-small">
                              <section class="store-section">
                                  <h2>${store.storeName}</h2><br>
                                  <p>가게 주소 : ${store.storeDefaultAddress}</p>
                                  상세 주소 : ${store.storeDetailAddress}<br>
                                  연락처 : <c:if test="${empty store.storePhoneNum}"> 
                                      연락처 정보 없음
                                  </c:if>
                                  <c:if test="${not empty store.storePhoneNum}">
                                      <span id="storePhoneNumber">${store.storePhoneNum}</span>
                                      <button onclick="copyStorePhoneNumber('${store.storePhoneNum}')">전화번호 복사</button>
                                  </c:if>
                                  <br>
                                  <hr>
                                  <div class="pagination">
                                  <button onclick="viewStore('${store.storeNum}')">가게 정보 보러 가기</button>
                              </div>
                              </section>
                          </div>
                      </c:forEach>
                  </c:if>
              </div>
          </div>
      </section>
      
      <!-- 페이지네이션 -->
<section id="pagination">
    <div class="pagination">
        <!-- 이전 페이지 버튼 -->
        <c:if test="${currentPage > 1}">
            <a href="?page=${currentPage - 1}&storeName=${param.storeName != null ? param.storeName : ''}" 
                    id="pagenationPreValue">&laquo; 이전</a>
        </c:if>

        <c:set var="startPage" value="${currentPage - 5}"/>
        <c:set var="endPage" value="${currentPage + 4}"/>

        <c:if test="${startPage < 1}">
            <c:set var="startPage" value="1"/>
        </c:if>
        <c:if test="${endPage > totalPages}">
            <c:set var="endPage" value="${totalPages}"/>
        </c:if>

        <c:forEach var="i" begin="${startPage}" end="${endPage}">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <strong>${i}</strong>
                </c:when>
                <c:otherwise>
                    <a href="?page=${i}&storeName=${param.storeName != null ? param.storeName : ''}" 
                    id="pagenationValue">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <a href="?page=${currentPage + 1}&storeName=${param.storeName != null ? param.storeName : ''}" 
                    id="pagenationNextValue">다음 &raquo;</a>
        </c:if>
    </div>
</section>
<!-- 페이지네이션 종료 -->
   
   <!-- footer 커스텀 태그 -->
   <custom:footer/>

   </div>

   <!-- Scripts -->
   <script src="assets/js/jquery.min.js"></script>
   <script src="assets/js/browser.min.js"></script>
   <script src="assets/js/breakpoints.min.js"></script>
   <script src="assets/js/util.js"></script>
   <script src="assets/js/main.js"></script>
    
    <script>
    // 복사하기 버튼 눌렀을 때 수행 되는 함수
    function copyStorePhoneNumber(storePhoneNum) {
        navigator.clipboard.writeText(storePhoneNum).then(function() {
            alert('전화 번호를 복사 했습니다!');
        }, function(err) {
            alert('복사에 실패 했습니다..: ', err);
        });
    }
        
        // 전체 선택 클릭 했을 때 수행 되는 함수
        $(document).ready(function() {
            $('#selectAll').change(function() {
                // 전체 선택 체크박스의 상태를 가져옵니다.
                var isChecked = $(this).is(':checked');
                
                // ID에 'num1'이 포함된 모든 체크박스를 선택하거나 해제합니다.
                $('input[id*="num1"]').prop('checked', isChecked);
            });
        });
        
        function viewStore(storeNum){
            window.location.href = 'viewStorePage.do?storeNum=' + encodeURIComponent(storeNum);
        }
        
        var menu_arr = []; // 선택된 메뉴 체크박스의 값을 저장할 배열
        var payment_arr = []; // 선택된 결제 방법 체크박스의 값을 저장할 배열
        var storeClosed = null; // 선택된 가게 종료 상태를 저장할 변수 / 기본값을 null로 초기화
        let checkBoxUrl = ''; // 종적으로 만들어질 쿼리 문자열을 저장할 빈 문자열
        
        $("input[name=storeMenu]:checked").each(function(){ // jQuery 를 사용하여 name값이 storeMenu라는 이름을 가진 input 요소를 찾고 ckecked 되어있는 것을 찾아옴
           var menu = $(this).val().trim(); // 찾아온 ckecked 되어 있는 값을 menu 변수에 저장 하고 trim 메서드를 사용하여 양쪽 공백을 모두 제거
           menu_arr.push(menu); // menu 변수에 저장된 값을 menu_arr 배열에 추가
           console.log("menu : "+menu);
           console.log("menu_arr : "+menu_arr);
        })
        $("input[name=storePayment]:checked").each(function(){ // name값이 storePayment라는 이름을 가진 input 요소를 찾고 동일하게 checked 되어있는 값을 찾아옴
           var payment = $(this).val().trim(); // 찾아온 값을 공백을 모두 제거한 후 payment 변수에 저장
           payment_arr.push(payment); // payment 변수에 저장된 값을 payment_arr 배열에 추가
           console.log("payment : "+payment);
           console.log("payment_arr : "+payment_arr);
        })
        $("input[name=storeClosed]:checked").each(function(){ // name값이 storeClosed라는 이름을 가진 input 요소를 찾고 checked 되어있는 값을 찾아옴
           storeClosed = $(this).val().trim(); // 배열이 아닌 단일 String 값이기 때문에 배열없이 storeColsed 변수에 저장
           console.log("storeClosed : "+storeClosed);
        })
        
        menu_arr.forEach(function(menu){ // menu_arr 배열을 forEach 메서드를 사용하여 반복한 후 각각의 요소를 checkBoxUrl 문자열에 추가 
            checkBoxUrl += '&storeMenu=' + encodeURIComponent(menu); // menu_arr의 각 요소는 '$storeMenu=' 뒤에 인코딩 된 형태로 추가 
        })
        payment_arr.forEach(function(payment){ // payment_arr 배열을 forEach 메서드를 사용하여 반복한 후 각 요소를 checkBoxUrl 문자열에 추가
           checkBoxUrl += '&storePayment=' + encodeURIComponent(payment);  // payment의 각 요소는 '$storePayment=' 뒤에 인코딩 된 형태로 추가
        })
        if(storeClosed != null){ // storeColsed 가 null 이 아니라면 
           checkBoxUrl += '&storeClosed=' + encodeURIComponent(storeClosed); // storeClosed의 각 요소는 checkBoxUrl에 인코딩 된 형태로 추가
        }
        
        
        console.log("checkBoxUrl : "+checkBoxUrl);
        
        let preLink = document.getElementById('pagenationPreValue');  // 이전 버튼 / pagenationPreValue라는 id 값을 가진 요소를 선택하여 prelink 요소에 추가
        let nextLink = document.getElementById('pagenationNextValue'); // 다음 버튼 / pagenationNextValue라는 id 값을 가진 요소를 선택하여 nextLink 요소에 추가
        let pageLinks = document.querySelectorAll('#pagination a[id^="pagenationValue"]'); // id가 'pagination'인 요소 아래에있는 모든 a 태그들 중 
                                                           // 페이지 숫자 버튼   // id가 pagenationValue 로 시작하는 요소를 선택하여 pageLinks에 저장
                                                                       // ^=는 "시작하는"이라는 의미의 CSS 선택자
                                                                       // 예를 들어, id가 pagenationValue 등으로 시작하는 모든 a 태그를 선택
        
           if (pageLinks.length > 0) { // pageLinks가 하나 이상 있는지 확인 / 없다면 수행 x / 페이지 숫자 버튼
              pageLinks.forEach(function(link) { // 각 페이지 링크에 foreEach 메서드를 수행
                     link.addEventListener('click', function(event) { // 각 링크에 이벤트 리스너 추가
                         event.preventDefault(); // 링크 클릭 시 기본 동작(페이지 이동)을 방지
                         link.href += checkBoxUrl; // 링크의 URL에 체크박스에서 선택한 값들을 추가 / 검색 조건이 url에 추가됨
                         window.location.href = link.href; // 최종적으로 수정된 페이지로 이동
                     });
                 });
           }
           if (preLink) { // preLink 가 있다면 / 이전 버튼
               preLink.addEventListener('click', function(event) { // 클릭 이벤트 리스너를 추가
                   event.preventDefault(); // 기본동작 제출 방지
                   preLink.href += checkBoxUrl;   // url에 체크박스에서 선택된 값을 추가 / 검색 조건이 url에 추가
                   window.location.href = preLink.href; // 새 페이지로 이동
               });
           }
           if (nextLink) { // nextLink 가 있다면 / 다음 버튼
               nextLink.addEventListener('click', function(event) { // 클릭 이벤트 리스너 추가
                   event.preventDefault(); // 기본동자 제출 방지
                   nextLink.href += checkBoxUrl; // url에 체크박스에서 선택된 값을 추가 / 검색 조건이 url에 추가
                   window.location.href = nextLink.href; // 새 페이지로 이동
               });
           }
        
        
    </script>
</body>
</html>
