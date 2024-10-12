<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지 - 가게 정보 등록</title>
<meta name="viewport"
   content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/main.css" />
<!-- 체크박스 -->
<style type="text/css">
.custom-checkbox {
   display: inline-block;
   position: relative;
   padding-left: 35px;
   cursor: pointer;
   font-size: 16px;
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
   height: 25px;
   width: 25px;
   background-color: #eee;
   border-radius: 5px;
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

.form-group {
   margin-bottom: 15px;
}

label {
   display: block;
   font-weight: bold;
   margin-bottom: 5px;
}

input[type="text"], input[type="tel"], input[type="time"] {
   width: 100%;
   padding: 8px;
   margin-bottom: 10px;
   border: 1px solid #ccc;
   border-radius: 4px;
}

input[type="submit"] {
   padding: 10px 20px;
   background-color: #2196F3;
   color: white;
   border: none;
   border-radius: 4px;
   cursor: pointer;
}

input[type="submit"]:hover {
   background-color: #1E88E5;
}

.checkbox-group {
   margin-bottom: 15px;
}
</style>
</head>
<body>
   <!-- Header -->
   <custom:header />
   <section id="content">
      <div class="container">
         <div class="row">
            <div class="col-9 col-12-medium">
               <!-- MainContent -->
               <section>
                  <h2>붕어빵 가게 정보 등록</h2>
                  <form id="storeRegister" action="storeRegister.do" method="POST">
                     <div class="form-group">
                        <label for="storeName">상호명</label> <input type="text"
                           id="storeName" name="storeName" value="${storeData.storeName}">
                     </div>
                     <!-- 주소 검색 버튼 및 입력 필드 -->
<div>
<input type="button" onclick="sample2_execDaumPostcode()" value="주소 검색"><br> 
<input type="text" id="sample2_postcode" placeholder="우편번호" readonly><br> 
<input type="text" id="sample2_address" name="storeAddress" value="${storeData.storeDefaultAddress}" placeholder="주소 검색을 진행해주세요" readonly><br> 
<input type="text" id="storeAddressDetail" name="storeAddressDetail" value="${storeData.storeDetailAddress}" placeholder="상세 주소를 입력해주세요"><br>
</div>

<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
<div id="layer"
    style="display: none; position: fixed; overflow: hidden; z-index: 1; -webkit-overflow-scrolling: touch;">
    <img src="//t1.daumcdn.net/postcode/resource/images/close.png"
        id="btnCloseLayer"
        style="cursor: pointer; position: absolute; right: -3px; top: -3px; z-index: 1"
        onclick="closeDaumPostcode()" alt="닫기 버튼">
</div>

<!-- 다음 우편번호 API 스크립트 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    var element_layer = document.getElementById('layer');

    function closeDaumPostcode() {
        element_layer.style.display = 'none';
    }

    function sample2_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                // 주소 유형에 따라 처리
                if (data.userSelectedType === 'R') { 
                    addr = data.roadAddress;
                } else { 
                    addr = data.jibunAddress;
                }

                // 도로명 주소일 때 참고 항목 추가
                if (data.userSelectedType === 'R') {
                    if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                        extraAddr += data.bname;
                    }
                    if (data.buildingName !== '' && data.apartment === 'Y') {
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    if (extraAddr !== '') {
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    
                } else {
                    extraAddr='';
                }

                // 우편번호와 주소 입력
                document.getElementById('sample2_postcode').value = data.zonecode;
                document.getElementById("sample2_address").value = addr+extraAddr;
                document.getElementById("storeAddressDetail").focus();

                // 레이어 닫기
                element_layer.style.display = 'none';
            },
            width: '100%',
            height: '100%',
            maxSuggestItems: 5
        }).embed(element_layer);

        // 레이어 보이기
        element_layer.style.display = 'block';
        initLayerPosition();
    }

    // 레이어 중앙 배치 함수
    function initLayerPosition(){
        var width = 800; 
        var height = 400; 
        var borderWidth = 5; 

        element_layer.style.width = width + 'px';
        element_layer.style.height = height + 'px';
        element_layer.style.border = borderWidth + 'px solid';

        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
    }
</script>
                     

                     <div class="form-group">
                        <label for="storePhone">연락처(선택)</label> <input type="tel"
                           id="storePhone" name="storePhone"
                           value="${storeData.storePhoneNum}"
                           pattern="010-[0-9]{4}-[0-9]{4}">
                     </div>
<div class="checkbox-group" id="businessMenusGroup">
                        <label for="businessMenus">메뉴(하나 이상 선택) <span style="color: red">*</span></label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessMenus" value="팥/슈크림"><span class="checkmark"></span> 팥/슈크림</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessMenus" value="야채/김치/만두"><span class="checkmark"></span> 야채/김치/만두</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessMenus" value="미니"><span class="checkmark"></span> 미니 붕어빵</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessMenus" value="고구마"><span class="checkmark"></span> 고구마</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessMenus" value="아이스크림/초코"><span class="checkmark"></span> 아이스크림/초코</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessMenus" value="치즈"><span class="checkmark"></span> 치즈</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessMenus" value="패스츄리"><span class="checkmark"></span> 패스츄리</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessMenus" value="기타"><span class="checkmark"></span> 기타</label>
                     </div>

                     <div class="checkbox-group" id="businessDaysGroup">
                        <label for="businessDays">영업 요일(하나 이상 선택) <span style="color: red">*</span></label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessDays" value="MON"><span class="checkmark"></span> 월요일</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessDays" value="TUE"><span class="checkmark"></span> 화요일</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessDays" value="WED"><span class="checkmark"></span> 수요일</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessDays" value="THU"><span class="checkmark"></span> 목요일</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessDays" value="FRI"><span class="checkmark"></span> 금요일</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessDays" value="SAT"><span class="checkmark"></span> 토요일</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessDays" value="SUN"><span class="checkmark"></span> 일요일</label>
                     </div>
                     <div class="checkbox-group" id="businessPaymentsGroup">
                        <label for="businessPayment">결제 방식(하나 이상 선택) <span style="color: red">*</span></label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessPayments" value="현금결제"><span class="checkmark"></span> 현금 결제</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessPayments" value="카드결제"><span class="checkmark"></span> 카드 결제</label>
                        <label class="custom-checkbox"><input type="checkbox" name="businessPayments" value="계좌이체"><span class="checkmark"></span> 계좌이체</label>
                     </div>


                     <div class="form-group">
                        <label for="start-time">영업 시작 시간 :</label> <input type="time"
                           id="start-time" name="startTime" value="${store.startTime}" required="required">
                     </div>

                     <div class="form-group">
                        " <label for="end-time">영업 종료 시간 : </label> <input type="time"
                           id="end-time" name="endTime" value="${store.endTime}" required="required">
                     </div>

                     <input type="submit" value="등록하기">
                  </form>
               </section>
            </div>
            <custom:adminMenu />
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
   <!-- 주소 검색 api -->
   <script
      src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

   <script type="text/javascript">
      document.getElementById('storeUpdate').addEventListener('submit', function(event) {
         event.preventDefault();
      
         const startTime = document.getElementById('start-time').value;
         const endTime = document.getElementById('end-time').value;

         // 시간 문자열을 직접 비교
          if (startTime >= endTime) {
              alert('영업 시작 시간이 종료 시간보다 이후일 수 없습니다.');
              document.getElementById('start-time').value = ''; // 값을 빈 문자열로 초기화
              document.getElementById('start-time').focus(); // 잘못된 필드에 포커스
              return; // 검증 실패 시 폼 제출 중단
          }
         
         // 메뉴 체크박스 검증
           const businessMenusGroup = document.getElementById('businessMenusGroup');
         const menuCheckboxes = businessMenusGroup.querySelectorAll('input[type="checkbox"]');
         const isMenuChecked = Array.from(menuCheckboxes).some(checkbox => checkbox.checked);
           if (!isMenuChecked) {
               alert('메뉴를 하나 이상 선택해야 합니다.');
               return; // 검증 실패 시 폼 제출 중단
           }

           const businessDaysGroup = document.getElementById('businessDaysGroup');
           const dayCheckboxes = businessDaysGroup.querySelectorAll('input[type="checkbox"]');
           const isDayChecked = Array.from(dayCheckboxes).some(checkbox => checkbox.checked);
           if (!isDayChecked) {
               alert('영업 요일을 하나 이상 선택해야 합니다.');
               return; // 검증 실패 시 폼 제출 중단
           }
           
           const businessPaymentsGroup = document.getElementById('businessPaymentsGroup');
           const payCheckboxes = businessPaymentsGroup.querySelectorAll('input[type="checkbox"]');
           const isPayChecked = Array.from(payCheckboxes).some(checkbox => checkbox.checked);
           if (!isPayChecked) {
               alert('결제 방식을 하나 이상 선택해야 합니다.');
               return; // 검증 실패 시 폼 제출 중단
           }

           // 모든 검증 통과 시 폼 제출
           this.submit();
      });
   </script>

</body>
</html>
