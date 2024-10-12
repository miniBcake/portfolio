package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.StorePaymentDTO;

public class StorePaymentDAO {
   
   //신규 가게 결제방식 추가
   //받는 데이터 : 가게 고유번호(FK), 현금, 카드 계좌이체 사용여부
   //추가 데이터 : 결제방식 고유번호, 가게 고유번호(FK), 현금, 카드 계좌이체 사용 여부
   final String INSERT = "INSERT INTO BB_STORE_PAYMENT(STORE_PAYMENT_NUM, STORE_NUM, STORE_PAYMENT_CASHMONEY, STORE_PAYMENT_CARD, STORE_PAYMENT_ACCOUNT) \r\n"
         + "VALUES ((SELECT NVL(MAX(STORE_PAYMENT_NUM)+1,1) FROM BB_STORE_PAYMENT), ?, ?, ?, ?) ";
   
   
   //결제방식별 해당하는 매장 총 개수 모두 조회
   //조회 데이터 : 현금, 카드, 계좌이체 각 결제방식을 진행하는 매장의 수
   final String SELECTALL = "SELECT STORE_PAYMENT_CASHMONEY, STORE_PAYMENT_CARD, STORE_PAYMENT_ACCOUNT, COUNT(STORE_PAYMENT_NUM) AS STOREPAYMNET_COUNT \r\n"
         + "FROM BB_STORE_PAYMENT GROUP BY GROUPING SETS(STORE_PAYMENT_CASHMONEY, STORE_PAYMENT_CARD, STORE_PAYMENT_ACCOUNT) \r\n"
         + "HAVING STORE_PAYMENT_CASHMONEY = 'Y' OR STORE_PAYMENT_CARD = 'Y' OR STORE_PAYMENT_ACCOUNT ='Y'";
   
   
   //특정 가게 결제방식 수정
   //받는 데이터 : 가게 고유번호
   //수정 데이터 : 현금, 카드, 계좌이체 사용 여부
   final String UPDATE = "UPDATE BB_STORE_PAYMENT SET STORE_PAYMENT_CASHMONEY =?, \r\n"
         + "STORE_PAYMENT_CARD = ?, STORE_PAYMENT_ACCOUNT = ? WHERE STORE_NUM =?";
   
   
   //특정 가게 결제방식 상세조회
   //받는 데이터 : 가게 고유번호
   //조회 데이터 : 가게 고유번호, 현금, 카드, 계좌이체 사용 여부
   final String SELECTONE = "SELECT STORE_NUM, STORE_PAYMENT_CASHMONEY, STORE_PAYMENT_CARD, \r\n"
         + "STORE_PAYMENT_ACCOUNT FROM BB_STORE_PAYMENT WHERE STORE_NUM = ?";
   
   
   
   
   
//StorePaymentDAO   insert   신규가게 결제방식 추가---------------------------------------------------------------------------------------------------------------
   
   public boolean insert(StorePaymentDTO storePaymentDTO) {
      System.out.println("log_StorePaymentDAO_insert : start");
      System.out.println("log_StorePaymentDAO_insert_controller input StoreWorkDTO : " +  storePaymentDTO);

      //[1] DB 연결 객체를 conn 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
      Connection conn = JDBCUtil.connect();
      System.out.println("log_StorePaymentDAO_insert_conn setting complete");
                  
      //[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
      PreparedStatement pstmt = null;
      System.out.println("log_StorePaymentDAO_insert_pstmt null setting complete");

      // 비정상 프로그램 종료 방지 위한 try-catch 진행
      try {
            //[3] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.SQL DB와 연결하여 INSERT 변수값 미리 컴파일, 실행 준비
            pstmt = conn.prepareStatement(INSERT);
            System.out.println("log_StorePaymentDAO_insert_pstmt conn");

            //[4] 인자값으로 받은 데이터 쿼리문에 삽입
            pstmt.setInt(1, storePaymentDTO.getStoreNum());                     //가게 고유번호 입력
            pstmt.setString(2, storePaymentDTO.getStorePaymentCashmoney());         //가게 영업요일 입력
            pstmt.setString(3, storePaymentDTO.getStorePaymentCard());            //가게 영업시작시간 입력
            pstmt.setString(4, storePaymentDTO.getStorePaymentAccountTransfer());   //가게 영업종료시간 입력
            System.out.println("log_StorePaymentDAO_insert_pstmt input complete");

            //[5] rs 변수 선언 : INSERT 쿼리문 실행
            int rs = pstmt.executeUpdate();
            System.out.println("log_StorePaymentDAO_insert_excuteUpdate() complete");

            //[6] 예외처리 : 정상실행 되지 않았을 경우, false
            if(rs <= 0) {//rs = 1(success) / rs = 0 (fail)
               System.err.println("log_StoreWorkDAO_insert_executeUpdate() fail : if(rs <= 0)");
               return false;
            }
      }catch (Exception e) {
         e.printStackTrace();
         System.err.println("log_StorePaymentDAO_insert_Exception fail : Exception e ");
         return false;
      //[7] JDBC 연결 해제 진행
      }finally { 
         if (!JDBCUtil.disconnect(conn, pstmt)) { // 만약 JDBC가 연결되어 있다면
            System.err.println("log_StorePaymentDAO_insert_disconnect fail"); // 연결해제 실패
            return false; 
         } // JDBC 연결 해제 되었다면
      }System.out.println("log_StorePaymentDAO_insert_true!");// 연결해제 성공
      return true;
   }
   
   

   
   
//StorePaymentDAO   selectAll   가게 결제방식별 총 개수 조회---------------------------------------------------------------------------------------------------------------
   
   public ArrayList<StorePaymentDTO> selectAll(StorePaymentDTO storePaymentDTO) {
      System.out.println("log_StorePaymentDAO_selectAll : start");
      System.out.println("log_StorePaymentDAO_selectAll controller input StorePaymentDTO : " + storePaymentDTO.toString());
      
      //[1] DB 연결 객체를 conn. 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
      Connection conn = JDBCUtil.connect();
      System.out.println("log_StorePaymentDAO__selectAll_conn setting complete");

      //[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
      PreparedStatement pstmt = null;
      System.out.println("log_StorePaymentDAO__selectAll_psmt null setting complete");

      //[3] rs 변수 선언 : selectAll 쿼리문 실행
      ResultSet rs = null;
      System.out.println("log_StorePaymentDAO__selectAll_rs null setting complete");

      //[4] datas 변수 선언 : 결제방식 카테고리별 판매하는 총 가게수 담은 리스트
      ArrayList <StorePaymentDTO> datas = new ArrayList <StorePaymentDTO>();
      
      try {
         //[5] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
         //SQL DB와 연결하여 SELECTALL 변수값 미리 컴파일, 실행 준비
         pstmt = conn.prepareStatement(SELECTALL);
         System.out.println("log_StorePaymentDAO_selectALL_pstmt conn");

         //[6] rs 변수 선언 : SELECTALL 쿼리문 실행
         rs = pstmt.executeQuery();
         System.out.println("log_StorePaymentDAO_selectAll_executeQuery() complete");

         //[7] 결제방식 카테고리별 붕어빵 판매 매장 수 데이터 불러오기
         while(rs.next()) {
            //결제방식 카테고리 내 한 결제방식으로 판매하는 총 가게 수 
            StorePaymentDTO data = new StorePaymentDTO();
            data.setStorPaymentCount(rs.getInt("STOREPAYMNET_COUNT"));
            datas.add(data);
            System.out.println("log_StorePaymentDAO_selectAll_datagetStorPaymentCount() : " + data.getStorPaymentCount());
         }
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println("log_StorePaymentDAO_selectAll_Exception fail");
      //[8] JDBC 연결 해제 진행
      } finally {
         if (!JDBCUtil.disconnect(conn, pstmt)) { // 만약 JDBC가 연결되어 있다면
            System.err.println("log_StorePaymentDAO_selectAll_disconnect fail");// 연결해제 실패
         }// JDBC 연결 해제 되었다면
         System.out.println("log_StorePaymentDAO_selectAll_complet"); // 연결해제 성공
      }
      System.out.println("log_StorePaymentDAO_selectAll_return datas");
      return datas; // 데이터 반환
   }

   
   
   
   
//StorePaymentDAO   selectOne   특정가게 정보 조회---------------------------------------------------------------------------------------------------------------
   
   public StorePaymentDTO selectOne(StorePaymentDTO storePaymentDTO) {
      System.out.println("log_StorePaymentDAO_selectOne : start");
      System.out.println("log_StorePaymentDAO_selectOne controller input StoreMenuDTO : " + storePaymentDTO.toString());

      //[1] DB 연결 객체를 conn. 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
      Connection conn = JDBCUtil.connect();
      System.out.println("log_StorePaymentDAO__selectOne_conn setting complete");

      //[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
      PreparedStatement pstmt = null;
      System.out.println("log_StorePaymentDAO__selectOne_psmt null setting complete");
            
      //[3] rs 변수 선언 : selectOne 쿼리문 실행
      ResultSet rs = null;
      System.out.println("log_StorePaymentDAO__selectOne_rs null setting complete");

      //[4] data 변수 선언 : 결과값 담을 data
      StorePaymentDTO data = null;
      System.out.println("log_StorePaymentDAO__selectOne_data null setting complete");
      
      try {
         //[5] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
         //SQL DB와 연결하여 SELECTONE 변수값 미리 컴파일, 실행 준비
         pstmt = conn.prepareStatement(SELECTONE);
         System.out.println("log_StorePaymentDAO__selectOne_pstmt conn");

         //[6] 인자값으로 받은 데이터 쿼리문에 삽입
         pstmt.setInt(1, storePaymentDTO.getStoreNum());   //가게 고유번호 입력
         System.out.println("log_StorePaymentDAO_selectOne_pstmt input complete");

         //[7] rs 변수 선언 : SELECTONE 쿼리문 실행
         rs = pstmt.executeQuery();
         System.out.println("log_StorePaymentDAO_selectOne_executeQuery() complete");

         //[8] 특정 가게 결제방식 카테고리별 사용가능 여부 불러오기
         if(rs.next()) {
            data = new StorePaymentDTO();
            data.setStorePaymentCashmoney(rs.getString("STORE_PAYMENT_CASHMONEY"));         //현금 결제 가능(Y/N)
            data.setStorePaymentCard(rs.getString("STORE_PAYMENT_CARD"));               //카드 결제 가능(Y/N)
            data.setStorePaymentAccountTransfer(rs.getString("STORE_PAYMENT_ACCOUNT"));      //계좌이체 결제 가능(Y/N)
            data.setStoreNum(rs.getInt("STORE_NUM"));                              //가게 고유번호(FK)
               System.out.println("log_StorePaymentDAO_selectOne_data : " + data);
         }
      }catch(Exception e) {
         e.printStackTrace();
         System.err.println("log_StorePaymentDAO_selectOne_Exception fail : Exception e ");
      //[9] JDBC 연결 해제 진행 
      }finally {
         if(!JDBCUtil.disconnect(conn, pstmt)) { // 만약 JDBC가 연결되어 있다면
            System.err.println("log_StorePaymentDAO_selectOne_dissconnect fail");
         }// JDBC 연결 해제 되었다면
      }
      System.out.println("log_StorePaymentDAO_selectOne_return data");// 연결해제 성공
      return data; // 데이터 반환
   }
   
   
   
   
   
//StorePaymentDAO   update   가게 결제방식 수정---------------------------------------------------------------------------------------------------------------
   
   public boolean update(StorePaymentDTO storePaymentDTO) {
      System.out.println("log_PaymentDAO_update : start");
      System.out.println("log_PaymentDAO_update_controller input StoreDTO : " + storePaymentDTO.toString());
      
      //[1] DB 연결 객체를 conn 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
      Connection conn = JDBCUtil.connect();
      System.out.println("log_StorPaymentDAO_update_conn setting complete");

      //[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
      PreparedStatement pstmt = null;
      System.out.println("log_StorePaymentDAO_update_pstmt null setting");

      // 비정상 프로그램 종료 방지 위한 try-catch 진행
      try {
         //[3] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
         //SQL DB와 연결하여 UPDATE 변수값 미리 컴파일, 실행 준비
         pstmt = conn.prepareStatement(UPDATE);
         System.out.println("log_StorePaymentDAO_update_pstmt conn");

         //[4] 인자값으로 받은 데이터 쿼리문에 삽입
         pstmt.setString(1, storePaymentDTO.getStorePaymentCashmoney());         //결제방식 현금 가능 여부
         pstmt.setString(2, storePaymentDTO.getStorePaymentCard());            //결제방식 카드 가능 여부
         pstmt.setString(3, storePaymentDTO.getStorePaymentAccountTransfer());   //결제방식 계좌이체 가능 여부
         pstmt.setInt(4, storePaymentDTO.getStoreNum());                     //결제방식 가게고유번호 입력
         System.out.println("log_StorePaymentDAO_update_pstmt input complete");

         //[5] rs 변수 선언 : UPDATE 쿼리문 실행
         int rs = pstmt.executeUpdate(); 
         System.out.println("log_StorePayment_update_executeUpdate() complete");
            
         //[6] 예외처리 : 정상실행 되지 않았을 경우, false
         if (rs <= 0) { //rs = 1(success) / rs = 0 (fail)
            System.err.println("log_StorePaymentDAO_update_executeUpdate() fail : if(rs <= 0)");
            return false;
         }
      } catch (Exception e) {
         System.err.println("log_StorePaymentDAO_update_Exception fail : Exception e ");
         return true;
         //[7] JDBC 연결 해제 진행
      } finally { 
         if (!JDBCUtil.disconnect(conn, pstmt)) { // 만약 JDBC가 연결되어 있다면
            System.err.println("log_StorePaymentDAO_update_disconnect fail"); // 연결해제 실패
            return false; 
         } // JDBC 연결 해제 되었다면
      }System.out.println("log_StorePaymentDAO_update_true!");
      return true;
   }
   
   private boolean delete(StorePaymentDTO storePaymentDTO) {
      return false;
   }
}