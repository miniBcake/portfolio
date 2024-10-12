package controller.page;

import java.io.IOException;
import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.StoreMethods;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.StoreDTO;
import model.dto.StoreMenuDTO;
import model.dto.StorePaymentDTO;
import model.dto.StoreWorkDTO;

public class UpdateStorePageAction implements Action{

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
      System.out.println("   log : UpdateStorePageAction.java      시작");
      // V에게 가져오는 데이터 있음
      // 해당 가게의 PK 값을 받아옴
      // int 변수명 :  storeNum
      // 형변환 필요
      int storeNum = Integer.parseInt(request.getParameter("storeNum"));
      System.out.println("   log : UpdateStorePageAction.java      storeNum : "+ storeNum);

      // V에게 전달할 데이터 있음 : 변경하려는 가게 정보

      // StoreDTO 객체를 생성하고 입력한 번호와 일치하는 데이터를 찾아옴
      // StoreMethods.storeDetials 메서드 사용
      // 객체명 : storeDTO
      StoreDTO storeDTO = StoreMethods.storeDetails(storeNum);
      System.out.println("   log : UpdateStorePageAction.java      storeDTO 생성, storeDetails 메스드 사용");
      System.out.println("   log : UpdateStorePageAction.java      storeDTO : "+ storeDTO);

      // 만약 입력받은 가게 번호와 일치하는 가게가 없을 경우
      // 해당 정보가 없는 경우 밑의 selectOne 결과는 존재하지 않기 때문에 한 번 검사
      // storeDTO가 null이라면
      if(storeDTO == null) {
         // HTTP 에러 응답을 보내는 메서드 (404 오류를 보냄)
         try {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

      // StoreWorkDTO
      // 객체명 : storeWorkList
      // StoreMethods.storeDetails(storeWorkDTO, storeNum)
      ArrayList<StoreWorkDTO> storeWorkList = StoreMethods.storeWorkDetails(storeNum);
      System.out.println("   log : UpdateStorePageAction.java      storeWorkList: "+ storeWorkList);
      
      // 전달하기 좋은 형태로 가공
      ArrayList<String> storeWorkWeekList = StoreMethods.storeWeekValue(storeWorkList);
      storeWorkWeekList = StoreMethods.storeListValuePlusN(storeWorkWeekList);
      System.out.println("   log : UpdateStorePageAction.java      storeWorkWeekList : "+ storeWorkWeekList);
      // 시간 데이터 가공
      String startTime = null;
      String endTime = null;
      // 만약 storeWorkList의 값이 존재한다면
      if(storeWorkList != null && storeWorkList.size() > 0) {
         System.out.println("   log : UpdateStorePageAction.java      storeWorkList.get(0).getStoreWorkClose() : "+ storeWorkList.get(0).getStoreWorkClose());
         // 시작 시간과 종료 시간을 변수에 넣음 
         startTime = storeWorkList.get(0).getStoreWorkOpen();
         endTime = storeWorkList.get(0).getStoreWorkClose();
      }
      // 만약 존재하지 않는다면
      else {
         System.out.println("   log : UpdateStorePageAction.java      storeWorkList == null");
         // 시작, 종료 시간 모두 00:00으로 작성
         startTime = "00:00";
         endTime = "00:00";
      }
      System.out.println("   log : UpdateStorePageAction.java      startTime : "+ startTime);
      System.out.println("   log : UpdateStorePageAction.java      endTime : "+ endTime);
      
      // StoreMenuDTO
      // 객체명 : storeMenuDTO
      // StoreMethods.storeDetails(storeMenuDTO, storeNum)
      StoreMenuDTO storeMenuDTO = StoreMethods.storeMenuDetails(storeNum);
      System.out.println("   log : UpdateStorePageAction.java      storeMenuDTO : "+ storeMenuDTO);
      // StorePaymentDTO
      // 객체명 : storePaymentDTO
      // StoreMethods.storeDetails(storePaymentDTO, storeNum)
      StorePaymentDTO storePaymentDTO = StoreMethods.storePaymentDetails(storeNum);
      System.out.println("   log : UpdateStorePageAction.java      storePaymentDTO : "+ storePaymentDTO);

      // V에게 데이터 전달
      // V에게 request.setAttribute로 storeDTO, storeWorkWeekList, storeWorkStartTime, storeWorkEndTime, storeMenuDTO, storePaymentDTO 전달
      // 속성명 : storeData, storeWorkData, storeWorkWeek, getStoreWorkOpen(), getStoreWorkClose() storeMenuData, storePaymentData
      request.setAttribute("storeData", storeDTO);
      request.setAttribute("storeWorkWeek", storeWorkWeekList);
      request.setAttribute("storeWorkStartTime", startTime);
      request.setAttribute("storeWorkEndTime", endTime);
      request.setAttribute("storeMenuData", storeMenuDTO);
      request.setAttribute("storePaymentData", storePaymentDTO);
      System.out.println("   log : UpdateStorePageAction.java      V에게 storeDTO, storeWorkDTO, storeMenuDTO, storePaymentDTO 전달");

      // ActionForward 객체 생성
      // 객체명 : forward
      // 이동 방법 : 데이터가 있으므로 false
      // 이동 페이지 : storeUpdate.jsp
      ActionForward forward = new ActionForward();
      forward.setRedirect(false);
      forward.setPath("storeUpdate.jsp");

      // forward 객체 반환
      System.out.println("   log : UpdateStorePageAction.java   forward.getPath : "+forward.getPath());
      System.out.println("   log : UpdateStorePageAction.java   종료");
      return forward;
   }
}