package controller.common;

import java.util.ArrayList;

import model.dao.StoreDAO;
import model.dao.StoreMenuDAO;
import model.dao.StorePaymentDAO;
import model.dao.StoreWorkDAO;
import model.dto.StoreDTO;
import model.dto.StoreMenuDTO;
import model.dto.StorePaymentDTO;
import model.dto.StoreWorkDTO;


public class StoreMethods {
   // 0.팥/슈크림         1.야채/김치/만두      2.미니   3.고구마   4.아이스크림/초코   5.치즈   6.패스츄리   7.기타
   private static final String[] menu = {"팥/슈크림", "야채/김치/만두", "미니", "고구마", "아이스크림/초코", "치즈", "패스츄리", "기타"}; 
   // 0.현금결제      1.카드결제      2.계좌이체
   private static final String[] payment = {"현금결제", "카드결제", "계좌이체"};
   // 0.MON   1.TUE   2.WED   3.THU   4.FRI   5.SAT   6.SUN
   private static final String[] week = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
   

   // 가게 관련 DTO들의 상세정보를 가져오는 메서드 =======================================================================
   // data : 가져오려는 가게의 정보(dto)
   // storeNum : 가져오려는 가게의 번호

   // 가게 상세 정보 -----------------------------------------------------------------------------------------------
   public static StoreDTO storeDetails(int storeNum) {
      System.out.println("   log : StoreMethods.storeDetails()   시작");
      System.out.println("   log : StoreMethods.storeDetails()   storeNum : "+ storeNum);

      // StoreDTO, StoreDAO 생성 : storeDTO, storeDAO
      StoreDTO storeDTO = new StoreDTO();
      StoreDAO storeDAO = new StoreDAO();
      System.out.println("   log : StoreMethods.storeDetails()   storeDTO, storeDAO 생성");

      // DTO 객체에 condition 넣기
      // condition : INFO_STORE_SELECTONE
      storeDTO.setCondition("INFO_STORE_SELECTONE");
      System.out.println("   log : StoreMethods.storeDetails()   setCondition : "+ storeDTO.getCondition());
      // DTO 객체에 storeNum 넣기
      storeDTO.setStoreNum(storeNum);

      // DAO.selectOne으로 DTO 값 가져오기
      // storeDTO에 반환값 넣기
      storeDTO = storeDAO.selectOne(storeDTO);
      System.out.println("   log : StoreMethods.storeDetails()   storeDAO.selectOne 완료");
      System.out.println("   log : StoreMethods.storeDetails()   storeDTO : "+ storeDTO);

      // storeDTO 반환
      return storeDTO;
   }

   // 가게 운영 정보 ----------------------------------------------------------------------------------------------
   public static ArrayList<StoreWorkDTO> storeWorkDetails(int storeNum) {
      System.out.println("   log : StoreMethods.storeWorkDetails()   시작");
      System.out.println("   log : StoreMethods.storeWorkDetails()   storeNum : "+ storeNum);

      // StoreDTO, StoreDAO 생성 : storeDTO, storeDAO
      StoreWorkDTO storeWorkDTO = new StoreWorkDTO();
      StoreWorkDAO storeWorkDAO = new StoreWorkDAO();
      System.out.println("   log : StoreMethods.storeWorkDetails()   storeWorkDTO, storeWorkDAO 생성");

      // DTO 객체에 condition 넣기
      // condition : STORE_NUM_SELECTALL
      storeWorkDTO.setCondition("STORE_NUM_SELECTALL");
      System.out.println("   log : StoreMethods.storeWorkDetails()   setCondition : "+ storeWorkDTO.getCondition());
      // DTO 객체에 storeNum 넣기
      storeWorkDTO.setStoreNum(storeNum);

      // DAO.selectAll으로 DTO 값 가져오기
      // ArrayList datas에 반환값 넣기
      ArrayList<StoreWorkDTO> datas = storeWorkDAO.selectAll(storeWorkDTO);
      System.out.println("   log : StoreMethods.storeWorkDetails()   storeWorkDAO.selectOne 완료");
      System.out.println("   log : StoreMethods.storeWorkDetails()   storeWorkDTO : "+ datas);

      // storeDTO 반환
      return datas;
   }

   // 가게 메뉴 정보 ----------------------------------------------------------------------------------------------
   public static StoreMenuDTO storeMenuDetails(int storeNum) {
      System.out.println("   log : StoreMethods.storeMenuDetails()   시작");
      System.out.println("   log : StoreMethods.storeMenuDetails()   storeNum : "+ storeNum);

      // StoreDTO, StoreDAO 생성 : storeDTO, storeDAO
      StoreMenuDTO storeMenuDTO = new StoreMenuDTO();
      StoreMenuDAO storeMenuDAO = new StoreMenuDAO();
      System.out.println("   log : StoreMethods.storeMenuDetails()   storeDTO, storeDAO 생성");

      // DTO 객체에 condition 넣기
      // condition : STORE_NUM_SELECTONE
      //         storeMenuDTO.setCondition("STORE_NUM_SELECTONE");
      //         System.out.println("   log : StoreMethods.storeMenuDetails()   setCondition : "+ storeMenuDTO.getCondition());
      // DTO 객체에 storeNum 넣기
      storeMenuDTO.setStoreNum(storeNum);

      // DAO.selectOne으로 DTO 값 가져오기
      // storeDTO에 반환값 넣기
      storeMenuDTO = storeMenuDAO.selectOne(storeMenuDTO);
      System.out.println("   log : StoreMethods.storeMenuDetails()   storeDAO.selectOne 완료");
      System.out.println("   log : StoreMethods.storeMenuDetails()   storeDTO : "+ storeMenuDTO);

      // storeDTO 반환
      return storeMenuDTO;
   }

   // 가게 결제 정보 -----------------------------------------------------------------------------------------------
   public static StorePaymentDTO storePaymentDetails(int storeNum) {
      System.out.println("   log : StoreMethods.storePaymentDetails()   시작");
      System.out.println("   log : StoreMethods.storePaymentDetails()   storeNum : "+ storeNum);

      // StoreDTO, StoreDAO 생성 : storeDTO, storeDAO
      StorePaymentDTO storePaymentDTO = new StorePaymentDTO();
      StorePaymentDAO storePaymentDAO = new StorePaymentDAO();
      System.out.println("   log : StoreMethods.storePaymentDetails()   storeDTO, storeDAO 생성");

      // DTO 객체에 condition 넣기
      // condition : STORE_NUM_SELECTONE
      //         storePaymentDTO.setCondition("STORE_NUM_SELECTONE");
      //         System.out.println("   log : StoreMethods.storePaymentDetails()   setCondition : "+ storePaymentDTO.getCondition());
      // DTO 객체에 storeNum 넣기
      storePaymentDTO.setStoreNum(storeNum);

      // DAO.selectOne으로 DTO 값 가져오기
      // storeDTO에 반환값 넣기
      storePaymentDTO = storePaymentDAO.selectOne(storePaymentDTO);
      System.out.println("   log : StoreMethods.storePaymentDetails()   storeDAO.selectOne 완료");
      System.out.println("   log : StoreMethods.storePaymentDetails()   storeDTO : "+ storePaymentDTO);

      // storeDTO 반환
      return storePaymentDTO;
   }

   // CNT를 구한는 메서드 ============================================================================================
   // StoreDTO에서 영업중 가게 CNT 값을 구하는 메서드 ------------------------------------------------------------------
   public static int storeCntOne(StoreDTO data) {
      System.out.println("   log : StoreMethods.storeCnt()   data는 StoreDTO 타입");
      // data를 StoreDTO로 형변환 
      StoreDTO storeDTO = data;
      System.out.println("   log : StoreMethods.storeCnt()   StoreDTO으로 형변환");

      // StoreDAO 생성
      StoreDAO storeDAO = new StoreDAO();
      System.out.println("   log : StoreMethods.storeCnt()   StoreDAO 생성");

      // DTO.setCondition을 한다.
      // condition : NOT_CLOSED_NUM_CNT_SELECTONE
      storeDTO.setCondition("NOT_CLOSED_NUM_CNT_SELECTONE");
      System.out.println("   log : StoreMethods.storeCnt()   condition : "+storeDTO.getCondition());
      storeDTO.setStoreClosed("N");

      // DAO.selectOne으로 DTO 값 가져오기
      // DTO 객체에 반환값 넣기
      storeDTO = storeDAO.selectOne(storeDTO);
      System.out.println("   log : StoreMethods.storeCnt()   StoreDAO.selectAll 완료");

      // storeDTO의 영업중인 가게의 CNT 가져옥
      return storeDTO.getStoreNotClosedCount();
   }

   // 필터별 가게 수 --------------------------------------------------------------------------------------------
   // data : 가져오려는 가게 정보(dto)
   public static ArrayList<Integer> storeCnt(Object data) {
      System.out.println("   log : StoreMethods.storeCnt()   시작");
      System.out.println("   log : StoreMethods.storeCnt()   data : "+ data);

      // 반환할 ArrayList<Integer> 생성
      ArrayList<Integer> datas = null;
      System.out.println("   log : StoreMethods.storeCnt()   ArrayList<Integer> datas 생성");

      // 객체를 받아서 알맞은 dto 객체 생성
      // 만약 인자 타입이 StoreMenuDTO라면
      if(data instanceof StoreMenuDTO) {
         System.out.println("   log : StoreMethods.storeCnt()   data는 StoreMenuDTO 타입");
         // data를 StoreMenuDTO로 형변환 
         StoreMenuDTO storeMenuDTO = (StoreMenuDTO) data;
         System.out.println("   log : StoreMethods.storeCnt()   StoreMenuDTO으로 형변환");

         // datas 객체 생성
         datas = new ArrayList<Integer>();
         System.out.println("   log : StoreMethods.storeCnt()   datas 메모리 할당");

         // StoreMenuDAO 생성
         StoreMenuDAO storeMenuDAO = new StoreMenuDAO();
         System.out.println("   log : StoreMethods.storeCnt()   StoreMenuDAO 생성");

         // DTO.setCondition을 한다.
         // condition : STORE_CNT_SELECTONE
         // storeMenuDTO.setCondition("STORE_CNT_SELECTONE");
         // System.out.println("   log : StoreMethods.storeCnt()   condition : "+storeMenuDTO.getCondition());

         // selectAll의 반환값을 받을 datas 생성
         ArrayList<StoreMenuDTO> menuCNTDatas = new ArrayList<StoreMenuDTO>();
         System.out.println("   log : StoreMethods.storeCnt()   ArrayList<StoreMenuDTO> 생성");

         // DAO.selectAll으로 DTO 값 가져오기
         // menuCNTDatas에 반환값 넣기
         menuCNTDatas = storeMenuDAO.selectAll(storeMenuDTO);
         System.out.println("   log : StoreMethods.storeCnt()   StoreMenuDAO.selectoAll 완료");

         // 만약 menuCNTDatas의 size가 0보다 작으면
         if(menuCNTDatas.size() <= 0) {
            System.out.println("   log : StoreMethods.storeCnt()   StoreMenuDAO.selectoAll 결과가 없음");
            // null 값 반환하기
            return null;
         }
         // CNT 결과 뽑아내기
         // slectAll 결과만큼 반복해서 각 DTO에 있는 DTO 값 빼오기
         for(StoreMenuDTO cntData : menuCNTDatas) {
            datas.add(cntData.getStoreMenuCount());
         }
         System.out.println("   log : StoreMethods.storeCnt()   datas에 데이터 넣음");
      }
      // 만약 인자 타입이 StorePaymentDTO라면
      else if(data instanceof StorePaymentDTO) {
         System.out.println("   log : StoreMethods.storeCnt()   data는 StorePaymentDTO 타입");
         // data를 StorePaymentDTO로 형변환 
         StorePaymentDTO storePaymentDTO = (StorePaymentDTO) data;
         System.out.println("   log : StoreMethods.storeCnt()   StorePaymentDTO으로 형변환");

         // datas 객체 생성
         datas = new ArrayList<Integer>();
         System.out.println("   log : StoreMethods.storeCnt()   datas 메모리 할당");

         // StorePaymentDAO 생성
         StorePaymentDAO storePaymentDAO = new StorePaymentDAO();
         System.out.println("   log : StoreMethods.storeCnt()   StorePaymentDAO 생성");

         // DTO.setCondition을 한다.
         // condition : STORE_CNT_SELECTONE
         // storePaymentDTO.setCondition("STORE_CNT_SELECTONE");
         // System.out.println("   log : StoreMethods.storeCnt()   condition : "+storePaymentDTO.getCondition());

         // selectAll의 반환값을 받을 datas 생성
         ArrayList<StorePaymentDTO> paymentCNTDatas = new ArrayList<StorePaymentDTO>();
         System.out.println("   log : StoreMethods.storeCnt()   ArrayList<StorePaymentDTO> 생성");

         // DAO.selectAll으로 DTO 값 가져오기
         // menuCNTDatas에 반환값 넣기
         paymentCNTDatas = storePaymentDAO.selectAll(storePaymentDTO);
         System.out.println("   log : StoreMethods.storeCnt()   StorePaymentDAO.selectoAll 완료");

         // 만약 paymentCNTDatas의 size가 0보다 작으면
         if(paymentCNTDatas.size() <= 0) {
            System.out.println("   log : StoreMethods.storeCnt()   StorePaymentDAO.selectoAll 결과가 없음");
            // null 값 반환하기
            return null;
         }

         // CNT 결과 뽑아내기
         // slectAll 결과만큼 반복해서 각 DTO에 있는 DTO 값 빼오기
         for(StorePaymentDTO cntData : paymentCNTDatas) {
            datas.add(cntData.getStorPaymentCount());
         }
         System.out.println("   log : StoreMethods.storeCnt()   datas에 데이터 넣음");
      }
      // 지정 외의 객체이면
      else {
         System.out.println("   log : StoreMethods.storeCnt()   data가 지원되지 않는 타입");
         throw new IllegalArgumentException("지원되지 않는 DTO 타입입니다.");
      }
      
      System.out.println("   log : StoreMethods.storeCnt()   datas : "+ datas);
      
      System.out.println("   log : StoreMethods.storeCnt()   종료");
      return datas;
   }

   // 메뉴 데이터를 DB에 사용할 수 있도록 가공하는 메서드 ===============================================================
   // 입력받은 메뉴 배열을 Y와 null로 이루어진 배열리스트로 변환하는 메서드 -----------------------------------------------
   public static ArrayList<String> storeMenuValue(String[] storeMenu){
      System.out.println("   log : StoreMethods.storeMenuValue()   시작");
      System.out.println("   log : StoreMethods.storeMenuValue()   storeMenu : "+ storeMenu);
      // 반환할 객체 생성
      ArrayList<String> storeMenuDatas = null;

      // 값이 하나라도 있다면 검색 가능한 형태로 데이터 변경해주기
      // storeMenu가 null이 아니라면
      if(storeMenu != null) {
         System.out.println("   log : StoreMethods.storeMenuValue()   storeMenu != null");

         storeMenuDatas = new ArrayList<String>();
         System.out.println("   log : StoreMethods.storeMenuValue()   storeMenuDatas 생성");
         
         // 메뉴 크기 변수 (8개)
         // int 변수명 : menuSize
         int menuSize = 8;

         storeMenuDatas = new ArrayList<String>();
         System.out.println("   log : StoreMethods.storeMenuValue()   "
               + "ArrayList<String> storeMenuDatas 생성");

         // N이 menuSize만큼 데이터를 넣어줌
         for(int i = 1; i <= menuSize; i++) {
            // storeMenuDatas에 'null'을 넣어줌
            storeMenuDatas.add(null);
         }
         System.out.println("   log : StoreMethods.storeMenuValue()   storeMenuDatas : "+ storeMenuDatas);

         // 가게 메뉴
         // storeMenu의 크기만큼 반복
         for(String data : storeMenu) {
            // sotreMenu 안에 데이터에 따라 storeMenuDatas의 특정 위치의 값을 'Y'로 변경
            // 0.팥/슈크림      1.야채/김치/만두   2.미니   3.고구마   4.아이스크림/초코   5.치즈   6.패스츄리   7.기타
            System.out.println("   log : StoreMethods.storeMenuValue()   storeMenu : "+ data);

            // 인덱스 변수
            int index = 0;
            
            data = data.trim();
            // 만약 storeMenu가 "팥/슈크림"이라면
            if(data.equals(menu[index++])) {
               // storeMenuDatas의 인덱스 0 값을 'Y'로 변경함
               storeMenuDatas.set(0, "Y");
               System.out.println("   log : StoreMethods.storeMenuValue()"
                     + "   storeMenuDatas 인덱스 0을 'Y'로 변경");
            }
            // 만약 storeMenu가 "야채/김치/만두"이라면
            else if(data.equals(menu[index++])) {
               // storeMenuDatas의 인덱스 1 값을 'Y'로 변경함
               storeMenuDatas.set(1, "Y");
               System.out.println("   log : StoreMethods.storeMenuValue()"
                     + "   storeMenuDatas 인덱스 1을 'Y'로 변경");
            }
            // 만약 storeMenu가 "미니"이라면
            else if(data.equals(menu[index++])) {
               // storeMenuDatas의 인덱스 2 값을 'Y'로 변경함
               storeMenuDatas.set(2, "Y");
               System.out.println("   log : StoreMethods.storeMenuValue()"
                     + "   storeMenuDatas 인덱스 2를 'Y'로 변경");
            }
            // 만약 storeMenu가 "고구마"이라면
            else if(data.equals(menu[index++])) {
               // storeMenuDatas의 인덱스 3 값을 'Y'로 변경함
               storeMenuDatas.set(3, "Y");
               System.out.println("   log : StoreMethods.storeMenuValue()"
                     + "   storeMenuDatas 인덱스 3을 'Y'로 변경");
            }
            // 만약 storeMenu가 "아이스크림/초코"이라면
            else if(data.equals(menu[index++])) {
               // storeMenuDatas의 인덱스 4 값을 'Y'로 변경함
               storeMenuDatas.set(4, "Y");
               System.out.println("   log : StoreMethods.storeMenuValue()"
                     + "   storeMenuDatas 인덱스 4를 'Y'로 변경");
            }
            // 만약 storeMenu가 "치즈"이라면
            else if(data.equals(menu[index++])) {
               // storeMenuDatas의 인덱스 5 값을 'Y'로 변경함
               storeMenuDatas.set(5, "Y");
               System.out.println("   log : StoreMethods.storeMenuValue()"
                     + "   storeMenuDatas 인덱스 5를 'Y'로 변경");
            }
            // 만약 storeMenu가 "패스츄리"이라면
            else if(data.equals(menu[index++])) {
               // storeMenuDatas의 인덱스 6 값을 'Y'로 변경함
               storeMenuDatas.set(6, "Y");
               System.out.println("   log ㄴ: StoreMethods.storeMenuValue()"
                     + "   storeMenuDatas 인덱스 6을 'Y'로 변경");
            }
            // 만약 storeMenu가 "기타"이라면
            else if(data.equals(menu[index])) {
               // storeMenuDatas의 인덱스 7 값을 'Y'로 변경함
               storeMenuDatas.set(7, "Y");
               System.out.println("   log : StoreMethods.storeMenuValue()"
                     + "   storeMenuDatas 인덱스 7을 'Y'로 변경");
            }
            else {
               // 로그 메뉴 값에 다른 이름이 들어왔음
               System.out.println("   log : StoreMethods.storeMenuValue()   메뉴와 상관없는 값이 들어옴");
            }
         }
      }

      return storeMenuDatas;
   }

   // 요일 데이터를 V에서 사용할 수 있도록 가공하는 메서드 ===============================================================
   // 입력받은 리스트 Y와 null로 이루어진 배열리스트로 변환하는 메서드 -----------------------------------------------
   public static ArrayList<String> storeWeekValue(ArrayList<StoreWorkDTO> datas){
      System.out.println("   log : StoreMethods.storeWeekValue()   시작");
      System.out.println("   log : StoreMethods.storeWeekValue()   datas : "+ datas);
      // 반환할 객체 생성
      ArrayList<String> storeWeekDatas = null;

      // 값이 하나라도 있다면 검색 가능한 형태로 데이터 변경해주기
      // datas가 null이 아니라면
      if(datas != null) {
         System.out.println("   log : StoreMethods.storeWeekValue()   storeMenu != null");

         storeWeekDatas = new ArrayList<String>();
         System.out.println("   log : StoreMethods.storeWeekValue()   storeWeekDatas 생성");

         // 요일 크기 변수 (7개)
         // int 변수명 : weekSize
         int weekSize = 7;

         storeWeekDatas = new ArrayList<String>();
         System.out.println("   log : StoreMethods.storeWeekValue()   "
               + "ArrayList<String> storeWeekDatas 생성");

         // weekSize만큼 반복
         for(int i = 1; i <= weekSize; i++) {
            // storeWeekDatas에 'null'을 넣어줌
            storeWeekDatas.add(null);
         }
         System.out.println("   log : StoreMethods.storeWeekValue()   storeMenuDatas : "+ storeWeekDatas);

         // 가게 메뉴
         // datas의 크기만큼 반복
         for(StoreWorkDTO data : datas) {
            // data.getStoreWorkWeek 안에 데이터에 따라 storeWeekDatas의 특정 위치의 값을 'Y'로 변경
            // 0.MON   1.TUE   2.WED   3.THU   4.FRI   5.SAT   6.SUN
            System.out.println("   log : StoreMethods.storeWeekValue()   storeMenu : "+ data);

            // 인덱스 변수
            int index = 0;
            
            // 만약 storeWorkWeek "MON"이라면
            if(data.getStoreWorkWeek().equals(week[index++])) {
               // storeWeekDatas의 인덱스 0 값을 'Y'로 변경함
               storeWeekDatas.set(0, "Y");
               System.out.println("   log : StoreMethods.storeWeekValue()"
                     + "   storeWeekDatas 인덱스 0을 'Y'로 변경");
            }
            // 만약 storeWorkWeek가 "TUE"이라면
            else if(data.getStoreWorkWeek().equals(week[index++])) {
               // storeWeekDatas의 인덱스 1 값을 'Y'로 변경함
               storeWeekDatas.set(1, "Y");
               System.out.println("   log : StoreMethods.storeMenuValue()"
                     + "   storeMenuDatas 인덱스 1을 'Y'로 변경");
            }
            // 만약 storeWorkWeek가 "WED"이라면
            else if(data.getStoreWorkWeek().equals(week[index++])) {
               // storeWeekDatas의 인덱스 2 값을 'Y'로 변경함
               storeWeekDatas.set(2, "Y");
               System.out.println("   log : StoreMethods.storeWorkWeekValue()"
                     + "   storeWorkWeekDatas 인덱스 2를 'Y'로 변경");
            }
            // 만약 storeWorkWeek가 "THU"이라면
            else if(data.getStoreWorkWeek().equals(week[index++])) {
               // storeWeekDatas의 인덱스 3 값을 'Y'로 변경함
               storeWeekDatas.set(3, "Y");
               System.out.println("   log : StoreMethods.storeWorkWeekValue()"
                     + "   storeWorkWeekDatas 인덱스 3을 'Y'로 변경");
            }
            // 만약 storeWorkWeek가 "FRI"이라면
            else if(data.getStoreWorkWeek().equals(week[index++])) {
               // storeWeekDatas의 인덱스 4 값을 'Y'로 변경함
               storeWeekDatas.set(4, "Y");
               System.out.println("   log : StoreMethods.storeWorkWeekValue()"
                     + "   storeWorkWeekDatas 인덱스 4를 'Y'로 변경");
            }
            // 만약 storeWorkWeek가 "SAT"이라면
            else if(data.getStoreWorkWeek().equals(week[index++])) {
               // storeWeekDatas의 인덱스 5 값을 'Y'로 변경함
               storeWeekDatas.set(5, "Y");
               System.out.println("   log : StoreMethods.storeWorkWeekValue()"
                     + "   storeWorkWeekDatas 인덱스 5를 'Y'로 변경");
            }
            // 만약 storeWorkWeek가 "SUN"이라면
            else if(data.getStoreWorkWeek().equals(week[index])) {
               // storeWeekDatas의 인덱스 6 값을 'Y'로 변경함
               storeWeekDatas.set(6, "Y");
               System.out.println("   log : StoreMethods.storeWorkWeekValue()"
                     + "   storeWorkWeekDatas 인덱스 6을 'Y'로 변경");
            }
            else {
               // 로그 메뉴 값에 다른 이름이 들어왔음
               System.out.println("   log : StoreMethods.storeWeekValue()   메뉴와 상관없는 값이 들어옴");
            }
         }
      }

      return storeWeekDatas;
   }

   // 리스트에 null 값을 N으로 변경해주는 메서드 -----------------------------------------------------------
   public static ArrayList<String> storeListValuePlusN(ArrayList<String> datas) {
      System.out.println("   log : StoreMethods.storeListValuePlusN()   시작");
      ArrayList<String> storeListValue = datas;
      System.out.println("   log : StoreMethods.storeListValuePlusN()   storeListValue : " + storeListValue);

      // datas 크기만큼 반복
      // 해당 위치의 인덱스 값의 데이터를 변경해야 하므로 i번 반복하는 for문 사용
      for(int i = 0; i < storeListValue.size(); i++) {
         // 만약 data가 null이면
         if(storeListValue.get(i) == null) {
            System.out.println("   log : StoreMethods.storeListValuePlusN()   storeMenuValue.get("+i+") : " + storeListValue.get(i));
            // N으로 변경
            storeListValue.set(i, "N");
            System.out.println("   log : StoreMethods.storeListValuePlusN()   N으로 변경");
            System.out.println("   log : StoreMethods.storeListValuePlusN()   storeMenuValue.get("+i+") : " + storeListValue.get(i));
         }
      }
      System.out.println("   log : StoreMethods.storeListValuePlusN()   storeMenuValue : " + storeListValue);
      System.out.println("   log : StoreMethods.storeListValuePlusN()   storeMenuValue 반환");
      return storeListValue;
   }

   // 결제방법 데이터를 DB에 사용할 수 있도록 가공하는 메서드 =================================================================
   // 오버리딩
   // storePayment 값을 객체에 저장 (StoreDTO일 때) --------------------------------------------------------------------
   public static void storePaymentSet(String[] storePayment, StoreDTO storeDTO) {
      System.out.println("   log : StoreMethods.storePaymentSet()   시작");
      System.out.println("   log : StoreMethods.storePaymentSet()   storePayment : "+ storePayment);
      System.out.println("   log : StoreMethods.storePaymentSet()   storeDTO : "+ storeDTO);

      // 만약 storePayment가 null이 아니라면
      if(storePayment != null) {
         // storePayment의 크기만큼 반복)
         for(String data : storePayment) {
            // sotrePayment 안에 데이터에 따라 
            // 1.현금결제      2.카드결제   3.계좌이체

            // 인젝스 변수
            int index = 0;
            
            // 만약 data가 "현금결제"이라면
            if(data.equals(payment[index++])) {
               // storeDTO.set현금결제에 'Y' 값을 넣음
               storeDTO.setStorePaymentCashmoney("Y");
               System.out.println("   log : StoreMethods.storePaymentSet()   현금결제를 Y로 변경");
            }
            // 만약 data가 "카드결제"이라면
            else if(data.equals(payment[index++])) {
               // storeDTO.set카드결제에 'Y' 값을 넣음
               storeDTO.setStorePaymentCard("Y");
               System.out.println("   log : StoreMethods.storePaymentSet()   카드결제를 Y로 변경");
            }
            // 만약 data가 "계좌이체"이라면
            else if(data.equals(payment[index])) {
               // storeDTO.set계좌이체에 'Y' 값을 넣음
               storeDTO.setStorePaymentaccountTransfer("Y");
               System.out.println("   log : StoreMethods.storePaymentSet()   계좌결제를 Y로 변경");
            }
            else {
               // 로그 잘못된 storePayment 값이 들어왔음
               System.out.println("   log : StoreMethods.storePaymentSet()   결제 방법과 상관없는 값이 들어옴");
            }
         }
      }
      System.out.println("   log : StoreMethods.storePaymentSet()   종료");
   }

   // 인자값이 StorePaymentDTO일 때 ----------------------------------------------------------------------------------
   public static void storePaymentSet(String[] storePayment, StorePaymentDTO storePaymentDTO) {
      System.out.println("   log : StoreMethods.storePaymentSet()   시작");
      System.out.println("   log : StoreMethods.storePaymentSet()   storePayment : "+ storePayment);
      System.out.println("   log : StoreMethods.storePaymentSet()   storePaymentDTO : "+ storePaymentDTO);

      // 만약 storePayment가 null이 아니라면
      if(storePayment != null) {
         // storePayment의 크기만큼 반복)
         for(String data : storePayment) {
            // sotrePayment 안에 데이터에 따라 
            // 1.현금결제      2.카드결제   3.계좌이체

            // 인덱스 변수
            int index = 0;
            
            // 만약 data가 "현금결제"이라면
            if(data.equals(payment[index++]) || data.equals("Cash")) {
               // storeDTO.set현금결제에 'Y' 값을 넣음
               storePaymentDTO.setStorePaymentCashmoney("Y");
               System.out.println("   log : StoreMethods.storePaymentSet()   현금결제를 Y로 변경");
            }
            // 만약 data가 "카드결제"이라면
            else if(data.equals(payment[index++]) || data.equals("Card")) {
               // storeDTO.set카드결제에 'Y' 값을 넣음
               storePaymentDTO.setStorePaymentCard("Y");
               System.out.println("   log : StoreMethods.storePaymentSet()   카드결제를 Y로 변경");
            }
            // 만약 data가 "계좌이체"이라면
            else if(data.equals(payment[index]) || data.equals("Account")) {
               // storeDTO.set계좌이체에 'Y' 값을 넣음
               storePaymentDTO.setStorePaymentAccountTransfer("Y");
               System.out.println("   log : StoreMethods.storePaymentSet()   계좌결제를 Y로 변경");
            }
            else {
               // 로그 잘못된 storePayment 값이 들어왔음
               System.out.println("   log : StoreMethods.storePaymentSet()   결제 방법과 상관없는 값이 들어옴");
            }
         }
      }
      System.out.println("   log : StoreMethods.storePaymentSet()   종료");
   }

   // storePayment의 null 값을 N으로 변경하는 메서드 ----------------------------------------------------------------
   public static void storePaymentSetPlusN(StorePaymentDTO storePaymentDTO) {
      System.out.println("   log : StoreMethods.storePaymentSetN()   시작");
      System.out.println("   log : StoreMethods.storePaymentSetN()   storeDTO : "+ storePaymentDTO);

      // N을 넣는 작업
      // storePaymentDTO.getStorePaymentCashmoney()이 null이라면
      if(storePaymentDTO.getStorePaymentCashmoney() == null) {
         // N값을 넣어줌
         storePaymentDTO.setStorePaymentCashmoney("N");
         System.out.println("   log : StoreMethods.storePaymentSetN()   StorePaymentCashmoney를 N으로 변경 : "+ storePaymentDTO.getStorePaymentCashmoney());
      }
      // storePaymentDTO.getStorePaymentCard()이 null이라면
      if(storePaymentDTO.getStorePaymentCard() == null) {
         // N값을 넣어줌
         storePaymentDTO.setStorePaymentCard("N");
         System.out.println("   log : StoreMethods.storePaymentSetN()   StorePaymentCard()를 N으로 변경 : "+ storePaymentDTO.getStorePaymentCard());
      }
      // storePaymentDTO.getStorePaymentAccountTransfer()이 null이라면
      if(storePaymentDTO.getStorePaymentAccountTransfer() == null) {
         // N값을 넣어줌
         storePaymentDTO.setStorePaymentAccountTransfer("N");
         System.out.println("   log : StoreMethods.storePaymentSetN()   StorePaymentAccountTransfer()를 N으로 변경 : "+ storePaymentDTO.getStorePaymentAccountTransfer());
      }
      System.out.println("   log : StoreMethods.storePaymentSetN()   종료");
   }

   // 메뉴 값을 DTO에 set 하는 메서드 =========================================================================
   public static void storeMenuSet(ArrayList<String> storeMenuDatas, StoreMenuDTO storeMenuDTO) {
      System.out.println("   log : StoreMethods.storeMenuSet()   시작");
      // storeMenuDTO.set으로 storeMenuDatas 값 차례로 넣기
      // 0.팥/슈크림      1.야채/김치/만두   2.미니   3.고구마   4.아이스크림/초코   5.치즈   6.패스츄리   7.기타
      int i = 0;

      System.out.println("   log : StoreMethods.storeMenuSet()   i : "+ i);
      storeMenuDTO.setStoreMenuNomal(storeMenuDatas.get(i++));      // 0.팥/슈크림
      System.out.println("   log : StoreMethods.storeMenuSet()   i : "+ i);
      storeMenuDTO.setStoreMenuVegetable(storeMenuDatas.get(i++));   // 1.야채/김치/만두
      System.out.println("   log : StoreMethods.storeMenuSet()   i : "+ i);
      storeMenuDTO.setStoreMenuMini(storeMenuDatas.get(i++));         // 2.미니
      System.out.println("   log : StoreMethods.storeMenuSet()   i : "+ i);
      storeMenuDTO.setStoreMenuPotato(storeMenuDatas.get(i++));      // 3.고구마
      System.out.println("   log : StoreMethods.storeMenuSet()   i : "+ i);
      storeMenuDTO.setStoreMenuIceCream(storeMenuDatas.get(i++));      // 4.아이스크림/초코
      System.out.println("   log : StoreMethods.storeMenuSet()   i : "+ i);
      storeMenuDTO.setStoreMenuCheese(storeMenuDatas.get(i++));      // 5.치즈
      System.out.println("   log : StoreMethods.storeMenuSet()   i : "+ i);
      storeMenuDTO.setStoreMenuPastry(storeMenuDatas.get(i++));      // 6.패스츄리
      System.out.println("   log : StoreMethods.storeMenuSet()   i : "+ i);
      storeMenuDTO.setStoreMenuOthers(storeMenuDatas.get(i));      // 7. 기타

      System.out.println("   log : StoreMethods.storeMenuSet()   .setMenu 완료");
      System.out.println("   log : StoreMethods.storeMenuSet()   종료");
   }
   
   // V가 출력하기 위해 dto를 리스트로 변환하는 메서드 ==============================================================
   // 메뉴 DTO를 배열리스트(메뉴 이름)로 변환하는 메서드 -------------------------------------------------------------
   public static ArrayList<String> storeMenuList(StoreMenuDTO storeMenuDTO){
      System.out.println("   log : StoreMethods.storeMenuList()   시작");
      
      // 반환할 객체 생성
      ArrayList<String> menuList = null;
      
      // 만약 storeDTO가 존재한다면
      if(storeMenuDTO != null) {
         System.out.println("   log : StoreMethods.storeMenuList()   storeMenuDTO 존재");
         
         // menuList 객체 생성
         menuList = new ArrayList<String>();
         
         // DTO에 있는 메뉴 변수 값이 Y이면 menuList에 추가
         // 만약 getStoreMenuNomal()이 Y라면
         if(storeMenuDTO.getStoreMenuNomal().equals("Y")) {
            menuList.add(menu[0]);
            System.out.println("   log : StoreMethods.storeMenuList()   getStoreMenuNomal()이 Y");
            System.out.println("   log : StoreMethods.storeMenuList()   menu[0] 추가");
         }
         // 만약 getStoreMenuVegetable()이 Y라면
         if(storeMenuDTO.getStoreMenuVegetable().equals("Y")) {
            menuList.add(menu[1]);
            System.out.println("   log : StoreMethods.storeMenuList()   getStoreMenuVegetable()이 Y");
            System.out.println("   log : StoreMethods.storeMenuList()   menu[1] 추가");
         }
         // 만약 getStoreMenuMini()이 Y라면
         if(storeMenuDTO.getStoreMenuMini().equals("Y")) {
            menuList.add(menu[2]);
            System.out.println("   log : StoreMethods.storeMenuList()   getStoreMenuMini()이 Y");
            System.out.println("   log : StoreMethods.storeMenuList()   menu[2] 추가");
         }
         // 만약 getStoreMenuPotato()이 Y라면
         if(storeMenuDTO.getStoreMenuPotato().equals("Y")) {
            menuList.add(menu[3]);
            System.out.println("   log : StoreMethods.storeMenuList()   getStoreMenuPotato()이 Y");
            System.out.println("   log : StoreMethods.storeMenuList()   menu[3] 추가");
         }
         // 만약 getStoreMenuIceCream()이 Y라면
         if(storeMenuDTO.getStoreMenuIceCream().equals("Y")) {
            menuList.add(menu[4]);
            System.out.println("   log : StoreMethods.storeMenuList()   getStoreMenuIceCream()이 Y");
            System.out.println("   log : StoreMethods.storeMenuList()   menu[4] 추가");
         }
         // 만약 getStoreMenuCheese()이 Y라면
         if(storeMenuDTO.getStoreMenuCheese().equals("Y")) {
            menuList.add(menu[5]);
            System.out.println("   log : StoreMethods.storeMenuList()   getStoreMenuCheese()이 Y");
            System.out.println("   log : StoreMethods.storeMenuList()   menu[5] 추가");
         }
         // 만약 getStoreMenuPastry()이 Y라면
         if(storeMenuDTO.getStoreMenuPastry().equals("Y")) {
            menuList.add(menu[6]);
            System.out.println("   log : StoreMethods.storeMenuList()   getStoreMenuPastry()이 Y");
            System.out.println("   log : StoreMethods.storeMenuList()   menu[6] 추가");
         }
         // 만약 getStoreMenuOthers()이 Y라면
         if(storeMenuDTO.getStoreMenuOthers().equals("Y")) {
            menuList.add(menu[7]);
            System.out.println("   log : StoreMethods.storeMenuList()   getStoreMenuOthers()이 Y");
            System.out.println("   log : StoreMethods.storeMenuList()   menu[7] 추가");
         }
      }
      
      System.out.println("   log : StoreMethods.storeMenuList()   종료");
      return menuList;
   }
   
   public static ArrayList<String> storePaymentList(StorePaymentDTO storePaymentDTO){
System.out.println("   log : StoreMethods.storeMenuList()   시작");
      
      // 반환할 객체 생성
      ArrayList<String> paymentList = null;
      
      // 만약 storeDTO가 존재한다면
      if(storePaymentDTO != null) {
         System.out.println("   log : StoreMethods.storePaymentList()   storePaymentDTO 존재");
         // DTO에 있는 메뉴 변수 값이 Y이면 menuList에 추가
         
         // paymentList 객체 생성
         paymentList = new ArrayList<String>();

         // 만약 getStoreMenuNomal()이 Y라면
         if(storePaymentDTO.getStorePaymentCashmoney().equals("Y")) {
            paymentList.add(payment[0]);
            System.out.println("   log : StoreMethods.storePaymentList()   getStorePaymentCashmoney()이 Y");
            System.out.println("   log : StoreMethods.storePaymentList()   menu[0] 추가");
         }
         // 만약 getStoreMenuVegetable()
         if(storePaymentDTO.getStorePaymentCard().equals("Y")) {
            paymentList.add(payment[1]);
            System.out.println("   log : StoreMethods.storePaymentList()   getStorePaymentCard()이 Y");
            System.out.println("   log : StoreMethods.storePaymentList()   menu[1] 추가");
         }
         // 만약 getStoreMenuMini()
         if(storePaymentDTO.getStorePaymentAccountTransfer().equals("Y")) {
            paymentList.add(payment[2]);
            System.out.println("   log : StoreMethods.storePaymentList()   getStorePaymentAccountTransfer()이 Y");
            System.out.println("   log : StoreMethods.storePaymentList()   menu[2] 추가");
         }
         
      }
      
      System.out.println("   log : StoreMethods.storeMenuList()   종료");
      return paymentList;
   }
}