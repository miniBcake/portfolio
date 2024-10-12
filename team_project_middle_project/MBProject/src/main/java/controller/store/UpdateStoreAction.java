package controller.store;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.StoreMethods;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.StoreDAO;
import model.dao.StoreMenuDAO;
import model.dao.StorePaymentDAO;
import model.dao.StoreWorkDAO;
import model.dto.StoreDTO;
import model.dto.StoreMenuDTO;
import model.dto.StorePaymentDTO;
import model.dto.StoreWorkDTO;

public class UpdateStoreAction implements Action{
   // 가게 업데이트 action
   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
      System.out.println("   log : UpdateStoreAction.java   시작");
      System.out.println("   log : UpdateStoreAction.java   request.getParameter(\"storeNum\") : "+request.getParameter("storeNum"));

      // 사용할 데이터 선언 부분 ==================================================================
      // 가게 번호(PK)
      // int 변수명(가게 번호) : storeNum
      int storeNum = Integer.parseInt(request.getParameter("storeNum"));
      System.out.println("   log : UpdateStoreAction.java   storeNum : "+storeNum);

      // 기존 가게 영업 요일을 저장할 변수 생성
      ArrayList<String> preStoreWorkWeek = new ArrayList<String>();

      // V에서 request.getParameter로 데이터를 받아옴

      // 가게 ---------------------------------------------------------------------------------
      // 가게 이름, 가게 주소, 가게 상세 주소, 연락처, 메뉴
      // String 변수명(가게 이름) : storeName
      String storeName = request.getParameter("storeName");
      System.out.println("   log : UpdateStoreAction.java   storeName : "+storeName);
      // String 변수명(가게 주소) : storeAddress
      String storeAddress = request.getParameter("storeAddress");
      System.out.println("   log : UpdateStoreAction.java   storeAddress : "+storeAddress);
      // String 변수명(가게 상세 주소) : storeAddressDetail
      String storeAddressDetail = request.getParameter("storeAddressDetail");
      System.out.println("   log : UpdateStoreAction.java   storeAddressDtail : "+storeAddressDetail);
      // String 변수명(연락처) : storeContact
      String storePhone = request.getParameter("storePhone");
      System.out.println("   log : UpdateStoreAction.java   storeContact : "+storePhone);
      // String[] 변수명(영업상태) : storeClosedV
      String[] storeClosedV = request.getParameterValues("businessOff");
      System.out.println("   log : UpdateStoreAction.java   storeClosed : "+storeClosedV);
      // storeClosed가 존재하는 지에 따라 값을 변경
      String storeClosed = null;
      // null이 아니라면
      if(storeClosedV != null && storeClosedV[0].equals("Y")) {
         System.out.println("   log : UpdateStoreAction.java   storeClosedV != null");
         System.out.println("   log : UpdateStoreAction.java   storeClosedV : "+storeClosedV[0]);
         // charStoreClosed의 값을 storeClosed[0] 값으로 변경
         storeClosed = storeClosedV[0];
      }
      else {
         storeClosed = "N";
      }

      // 가게 영업 -----------------------------------------------------------------------------
      // String[] 변수명(요일) : storeWorkWeek
      String[] storeWorkWeek = request.getParameterValues("businessDays");
      System.out.println("   log : UpdateStoreAction.java   storeWorkWeek : "+storeWorkWeek);      
      // String 변수명(영업시작 시간) : storeStartTime
      String storeStartTime = request.getParameter("startTime");
      System.out.println("   log : UpdateStoreAction.java   storeStartTime : "+storeStartTime);
      // String 변수명(영업종료 시간) : storeEndTime
      String storeEndTime = request.getParameter("endTime");
      System.out.println("   log : UpdateStoreAction.java   storeEndTime : "+storeEndTime);

      // 가게 영업 요일을 사용하기 편하게 ArrayList로 변경
      // 리스트명 : storeWorkWeekList
      ArrayList<String> storeWorkWeekList = new ArrayList<String>();
      // 배열 안에 있는 값을 반복하여 storeWorkWeekList에 추가
      for(String data : storeWorkWeek) {
         storeWorkWeekList.add(data);
      }
      System.out.println("   log : UpdateStoreAction.java   storeWorkWeek : "+storeWorkWeekList);      

      // 가게 메뉴 ------------------------------------------------------------------------------
      // 가게 메뉴 받기 (팥/슈크림, 야채/김치/만두, 미니, 고구마, 아이스크림/초코, 치즈, 패스츄리, 기타)
      //String[] 변수명 : storeMenu
      String[] storeMenu = request.getParameterValues("businessMenus");
      System.out.println("   log : UpdateStoreAction.java   storeMenu : "+storeMenu);

      // 가게 메뉴 체크박스 데이터 전달을 위한 배열리스트 생성
      // ArrayList<String> 변수명 : storeMenuDatas
      // storeMenuValue를 사용하여 메뉴 값을 받아옴
      ArrayList<String> storeMenuDatas = StoreMethods.storeMenuValue(storeMenu);

      // insert할 땐 null 값을 N으로 변경
      // storeMenuValuePlusN을 사용하여 N 추가
      storeMenuDatas = StoreMethods.storeListValuePlusN(storeMenuDatas);

      // 결제 방식 -------------------------------------------------------------------------------
      // 결제방식 받기 (현금, 카드, 계좌이체)
      // String[] 변수명 : storePayment
      String[] storePayment = request.getParameterValues("businessPayments");
      System.out.println("   log : UpdateStoreAction.java   stroePayment : "+storePayment);

      // 필요한 기존 데이터 가져오는 부분 ============================================================
      // 기존 storeWorkDTO 리스트 가져오기
      // StoreWorkDTO, DAO 생성
      StoreWorkDTO preStoreWorkDTO = new StoreWorkDTO();
      StoreWorkDAO preStoreWorkDAO = new StoreWorkDAO();
      System.out.println("   log : UpdateStoreAction.java   preStoreWorkDTO,DAO 생성");

      // storeWorkDTO.set으로 condition, 가게번호(PK) 넣기
      // condition : STORE_NUM_SELECTONE
//      preStoreWorkDTO.setCondition("STORE_NUM_SELECTONE");
      preStoreWorkDTO.setStoreNum(storeNum);
      System.out.println("   log : UpdateStoreAction.java   preStoreWorkDTO.set가게 번호 완료");

      // DAO.selectAll로 가게 영업 정보 가져오기
      ArrayList<StoreWorkDTO> storeWorkDatas = preStoreWorkDAO.selectAll(preStoreWorkDTO);
      System.out.println("   log : UpdateStoreAction.java   prestoreWorkDAO.selecAll 완료 : "+storeWorkDatas);      

      // 가게 영업 정보에서 영업 요일 데이터만 추출하기
      for(StoreWorkDTO data : storeWorkDatas) {
         preStoreWorkWeek.add(data.getStoreWorkWeek());
      }
      System.out.println("   log : UpdateStoreAction.java   preStoreWorkWeek : "+preStoreWorkWeek);      
      

      // DTO, DAO 생성 ===========================================================================
      // StoreDTO, DAO 생성
      // 객체명 : storeDTO, storeDAO
      StoreDTO storeDTO = new StoreDTO();
      StoreDAO storeDAO = new StoreDAO();

      // StoreWorkDTO, DAO 생성
      // 객체명 : storeWorkDTO, storeWorkDAO
      StoreWorkDTO storeWorkDTO = new StoreWorkDTO();
      StoreWorkDAO storeWorkDAO = new StoreWorkDAO();

      // StoreMenuDTO, DAO 생성
      // 객체명 : storeMenuDTO, storeMenuDAO
      StoreMenuDTO storeMenuDTO = new StoreMenuDTO();
      StoreMenuDAO storeMenuDAO = new StoreMenuDAO();

      // StorePaymentDTO, DAO 객체 생성
      // 객체명 storePaymentDTO, storePaymentDAO
      StorePaymentDTO storePaymentDTO = new StorePaymentDTO();
      StorePaymentDAO storePaymentDAO = new StorePaymentDAO();
      System.out.println("   log : UpdateStoreAction.java   DTO, DAO들 생성");


      // update 할 데이텨 세팅 부분 ==============================================================
      // storeDTO.set으로 가게 번호(PK), 가게 이름, 가게 기본 주소, 가게 상세 주소, 연락처, 영업상태를 입력
      storeDTO.setStoreNum(storeNum);
      storeDTO.setStoreName(storeName);
      storeDTO.setStoreDefaultAddress(storeAddress);
      storeDTO.setStoreDetailAddress(storeAddressDetail);
      storeDTO.setStorePhoneNum(storePhone);
      storeDTO.setStoreClosed(storeClosed);
      System.out.println("   log : UpdateStoreAction.java   storeDTO.set 완료");

      // storeWorkDTO.set으로 가게번호(PK), 영업시작 시간, 영업 종료 시간 넣기   
      storeWorkDTO.setStoreNum(storeNum);
      storeWorkDTO.setStoreWorkOpen(storeStartTime);
      storeWorkDTO.setStoreWorkClose(storeEndTime);
      System.out.println("   log : UpdateStoreAction.java   storeWorkDTO.set 완료");

      // sotreMenuDTO.set가게 번호
      storeMenuDTO.setStoreNum(storeNum);
      // storeMenuSet 메서드로 storeMenuDTO.set 완료
      StoreMethods.storeMenuSet(storeMenuDatas, storeMenuDTO);
      System.out.println("   log : UpdateStoreAction.java   storeMenuDTO.set 완료");
      System.out.println("   log : UpdateStoreAction.java   storeMenuDTO : "+ storeMenuDTO);

      // sotrePaymentDTO.set가게 번호
      storePaymentDTO.setStoreNum(storeNum);
      
      // storePaymentSet 메서드를 사용하여 DTO에 세팅
      StoreMethods.storePaymentSet(storePayment, storePaymentDTO);
      System.out.println("   log : UpdateStoreAction.java   storePaymentDTO.set 완료");
      System.out.println("   log : UpdateStoreAction.java   storePaymentDTO : "+ storePaymentDTO);
      
      // storePaymentSetN 메서드를 사용하여 null 값에 N값 넣기
      StoreMethods.storePaymentSetPlusN(storePaymentDTO);
      System.out.println("   log : UpdateStoreAction.java   storePaymentDTO.set 완료");
      System.out.println("   log : UpdateStoreAction.java   storePaymentDTO : "+ storePaymentDTO);


      // ActionForward 객체 생성
      // 객체명 : forward
      // 이동방식 : 데이터를 전달하므로 false
      // 이동할 페이지 : 실패 알림창.do
      ActionForward forward = new ActionForward();
      forward.setRedirect(false);
      forward.setPath("failInfo.do");
      
      // 가게 등록에 실패 시
      // 가게 등록 실패 시 가는 경로
      String path = "updateStorePage.do";
      
      // 결과를 저장할 flag 생성
      // 기본값은 false
      boolean flag = false;

      // update 부분 ===========================================================================
      // 가게 update --------------------------------------------------------------------------
      System.out.println("   log : UpdateStoreAction.java   storeDTO.update 시작");
      // storeDAO.update를 사용하여 storeDTO 등록 
      // flag에 update 결과 반환값을 저장
      flag = storeDAO.update(storeDTO);
      System.out.println("   log : UpdateStoreAction.java   storeDTO.update : "+flag);

      // flag가 false면
      if(!flag) {
         // 실패시 request.setAttribute(path, msg)를 해주는 메서드
         failSetting(request, path, "가게 업데이트에 실패했습니다.");

         // forward 값 반환
         System.err.println("   log : UpdateStoreAction.java   종료");
         return forward;
      }

      // 가게 영업 시간 update -------------------------------------------------------------------
      System.out.println("   log : UpdateStoreAction.java   storeWorkDTO.update 시작");
      // storeWorkWeek의 갯수만큼 반복해서 업데이트 

      // update, insert
      // storeWorkWeekList 값을 하나씩 가져와(newData) 반복
      // 기존 데이터인 preStoreWorkWeek 리스트 데이터 안에 newData 값이 존재하는지 판단하여 action 결정
      for(String newData : storeWorkWeekList) {
         // 기존 데이터와 비교하여 insert, update를 결정한다.
         // 일치 여부를 판단할 데이터 String action
         String action = null;
         // 판단 결과를 넣을 int result 변수 생성
         int result = 0;

         // indexOf 메서드를 사용하여 newData 값이 preStoreWorkWeek 안에 존재하는지 판단
         result = preStoreWorkWeek.indexOf(newData);
         System.out.println("   log : UpdateStoreAction.java   "+newData+"의 result : "+ result);

         // 일치하는 값이 있다면
         // result가 0보다 크다면
         if(result >= 0) {
            action = "update";
            System.out.println("   log : UpdateStoreAction.java   action : "+ action);
         }
         // newData가 preDatas에 없다면
         // result가 0보다 작다면
         else if(result < 0) {
            action = "insert";
            System.out.println("   log : UpdateStoreAction.java   action : "+ action);
         }

         // 해당 newData 값을 DTO.setStoreWeek 안에 넣고
         storeWorkDTO.setStoreWorkWeek(newData);

         // 결정된 action을 실행
         // action이 update라면
         if(action.equals("update")) {
            // storeWorkDAO.update로 storeWorkDTO 등록
            // flag에 update 결과 반환값을 저장
            flag = storeWorkDAO.update(storeWorkDTO);
            System.out.println("   log : UpdateStoreAction.java   storeWorkDTO.update : "+flag);
         }
         else if(action.equals("insert")) {
            // storeWorkDAO.insert로 storeWorkDTO 등록
            // flag에 insert 결과 반환값을 저장
            flag = storeWorkDAO.insert(storeWorkDTO);
            System.out.println("   log : UpdateStoreAction.java   storeWorkDTO.insert : "+flag);
         }

         // 가게 등록에 실패 시
         // flag가 false면
         if(!flag) {
            // 실패시 request.setAttribute(path, msg)를 해주는 메서드
            failSetting(request, path, "가게 영업시간 업데이트에 실패했습니다.");

            // forward 값 반환
            System.err.println("   log : UpdateStoreAction.java   종료");
            return forward;
         }
      }

      // delete
      // preStoreWorkWeek 값을 하나씩 가져와(data) 반복
      // 기존 데이터인 storeWorkWeekList 리스트 데이터 안에 preData 값이 존재하는지 판단하여 delete 결정
      // 기존 데이터와 비교
      for(String preData : preStoreWorkWeek) {
         // 판단 결과를 넣을 int result 변수 생성
         int result = 0;

         // indexOf 메서드를 사용하여 preData 값이 storeWorkWeekList 안에 존재하는지 판단
         result = storeWorkWeekList.indexOf(preData);
         System.out.println("   log : UpdateStoreAction.java   "+preData+"의 result : "+ result);

         // 만약 데이터가 없다면
         // result가 0보다 작다면
         if(result < 0) {
            // data를 storeWorkDTO.set 함
            storeWorkDTO.setStoreWorkWeek(preData);

            // storeWorkDAO.delete로 storeWorkDTO 값 삭제
            // flag에 delete 결과 반환값을 저장
            flag = storeWorkDAO.delete(storeWorkDTO);
         }

         // 가게 삭제에 실패 시
         // flag가 false면
         if(!flag) {
            // 실패시 request.setAttribute(path, msg)를 해주는 메서드
            failSetting(request, path, "가게 영업시간 업데이트에 실패했습니다.");

            // forward 값 반환
            System.err.println("   log : UpdateStoreAction.java   종료");
            return forward;
         }
      }


      // 가게 메뉴 update ----------------------------------------------------------------------
      // storeMenuDAO.update로 storeMenuDTO 등록
      // flag에 update 결과 반환값을 저장
      flag = storeMenuDAO.update(storeMenuDTO);
      System.out.println("   log : UpdateStoreAction.java   storeMenuDTO.update : "+flag);

      // 가게 등록에 실패 시
      // flag가 false면
      if(!flag) {
         // 실패시 request.setAttribute(path, msg)를 해주는 메서드
         failSetting(request, path, "가게 메뉴 업데이트에 실패했습니다.");

         // forward 값 반환
         System.err.println("   log : UpdateStoreAction.java   종료");
         return forward;
      }

      // 가게 결제 방법 update -------------------------------------------------------------------
      // storePaymentDAO.update로 storeWorkDTO 등록
      // flag에 update 결과 반환값을 저장
      flag = storePaymentDAO.update(storePaymentDTO);
      System.out.println("   log : UpdateStoreAction.java   storePaymentDTO.update : "+flag);

      // 가게 등록에 실패 시
      // flag가 false면
      if(!flag) {
         // 실패시 request.setAttribute(path, msg)를 해주는 메서드
         failSetting(request, path, "가게 결제방법 업데이트에 실패했습니다.");

         // forward 값 반환
         System.err.println("   log : UpdateStoreAction.java   종료");
         return forward;
      }

      // 성공 후 이동 ============================================================================
      // update 성공으로 forwad 값 변경
      // 이동방식 : 전송할 데이터가 없으므로 true
      // 이동할 페이지 : searchStore.do
      forward.setRedirect(true);
      forward.setPath("searchStore.do");

      // forward 객체 반환
      System.out.println("   log : UpdateStoreAction.java   forward.getPath : "+forward.getPath());
      System.out.println("   log : UpdateStoreAction.java   종료");
      return forward;
   }

   private void failSetting(HttpServletRequest request, String path, String msg) {
      // V에게 결과 보내기
      // request.setAttribute로 msg, 이동경로 값 전달.
      // msg : 업데이트가 실패된 부분에 관한 메세지
      // 이동경로 : 가게 업데이트 페이지 이동
      request.setAttribute("msg", msg);
      request.setAttribute("path", path);

      // forward 값 반환
      System.out.println("   log : UpdateStoreAction.java   request.getPath : "+path);
   }
}