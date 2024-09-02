package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.MemberDTO;

public class MemberDAO {
   // 회원 추가
   final String INSERT = "INSERT INTO BD_MEMBER(MEMBER_NUM,MEMBER_EMAIL,MEMBER_PW,MEMBER_NAME,MEMBER_PHONE,MEMBER_NICKNAME,MEMBER_PROFILE_WAY,MEMBER_ROLE,MEMBER_HIREDAY)\r\n"
         + "VALUES((SELECT NVL(MAX(MEMBER_NUM),0)+1 FROM BD_MEMBER),?,?,?,?,?,?,?,TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI'))";

   // 전체 회원 조회
   final String SELECTALL = "SELECT MEMBER_NUM, MEMBER_NAME, MEMBER_EMAIL, MEMBER_NICKNAME, MEMBER_PHONE, MEMBER_PROFILE_WAY,MEMBER_ROLE,MEMBER_HIREDAY FROM BD_MEMBER";

   // 최근 7일간 가입한 전체 회원 조회 : (현재날짜-7일)보다 가입일자가 클 때
   final String NEWMEMBERS_SELECTALL = "SELECT MEMBER_NUM, MEMBER_NAME, MEMBER_EMAIL, MEMBER_NICKNAME, MEMBER_PHONE, MEMBER_PROFILE_WAY,MEMBER_ROLE,MEMBER_HIREDAY \r\n"
         + "FROM BD_MEMBER WHERE TRUNC(MEMBER_HIREDAY) >= SYSDATE - INTERVAL '7' DAY";

   // 이메일 중복회원 조회 : 인풋값 이메일과 동일한 데이터 있는지 회원 조회.
   final String EMAIL_SELECTONE = "SELECT MEMBER_EMAIL FROM BD_MEMBER WHERE MEMBER_EMAIL=?";

   // 닉네임 중복회원 조회 : 인풋값 닉네임과 동일한 데이터 있는지 회원 조회
   final String NICKNAME_SELECTONE = "SELECT MEMBER_NICKNAME FROM BD_MEMBER WHERE MEMBER_NICKNAME=?";

   // 로그인 진행 회원 조회.인풋값 이메일, 비밀번호 일치할 때 고유번호, 닉네임, 역할 모두 반환
   final String LOGIN_SELECTONE = "SELECT MEMBER_NUM, MEMBER_EMAIL, MEMBER_NICKNAME, MEMBER_ROLE \r\n"
         + "FROM BD_MEMBER WHERE MEMBER_EMAIL = ? AND MEMBER_PW = ?";

   // 회원 1명 상세조회.인풋값 PK통해서 고유번호, 이름, 이메일, 닉네임, 전화번호, 프로필 사진경로 가입일자 역할 모두 반환
   final String MEMBER_DETAIL_SELECTONE = "SELECT MEMBER_NUM,MEMBER_NAME, MEMBER_EMAIL, MEMBER_NICKNAME,"
   		+ "MEMBER_PHONE,MEMBER_PROFILE_WAY, MEMBER_ROLE, MEMBER_HIREDAY FROM BD_MEMBER WHERE MEMBER_NUM =?";

   // 패스워드 일치 여부 확인 회원 1명 조회. 인풋값 PK,패스워드 통해서 고유번호, 이메일, 이름, 폰번호, 닉네임, 프로필사진 경로, 역할
   // 모두 반환
   final String PASSWORD_CHECK_SELECTONE = "SELECT MEMBER_NUM FROM BD_MEMBER WHERE MEMBER_NUM = ? AND MEMBER_PW = ?";

   // 특정 회원 프로필 사진 경로 조회 : PK값 C에서 주면, M은 특정 회원 조회 + 사진 경로 반환
   final String PROFILEWAY_SELECTONE = "SELECT MEMBER_PROFILE_WAY, MEMBER_NUM FROM BD_MEMBER WHERE MEMBER_NUM = ?";

   // 회원 데이터 삭제: 아이디값 같을 때, 해당 회원 데이터 모두 삭제
   private final String DELETE = "DELETE FROM BD_MEMBER WHERE MEMBER_NUM=?";

   // 이메일값 업데이트 : 인풋값 PK 받아서 이메일 수정
   private final String EMAIL_UPDATE = "UPDATE BD_MEMBER SET MEMBER_EMAIL = ? WHERE MEMBER_NUM = ?";
   // 비밀번호값 업데이트 : 인풋값 PK 받아서 비밀번호 수정
   private final String PASSWORD_UPDATE = "UPDATE BD_MEMBER SET MEMBER_PW = ? WHERE MEMBER_NUM = ?";
   // 닉네임값 업데이트 : 인풋값 PK 받아서 닉네임 수정
   private final String NICKNAME_UPDATE = "UPDATE BD_MEMBER SET MEMBER_NICKNAME = ? WHERE MEMBER_NUM = ?";
   // 핸드폰번호값 업데이트 : 인풋값 PK 받아서 핸드폰번호 수정
   private final String PHONENUM_UPDATE = "UPDATE BD_MEMBER SET MEMBER_PHONE = ? WHERE MEMBER_NUM = ?";
   // 프로필사진값 업데이트 : 인풋값 PK 받아서 프로필 사진 경로 수정
   private final String PROFILEPIC_UPDATE = "UPDATE BD_MEMBER SET MEMBER_PROFILE_WAY = ? WHERE MEMBER_NUM = ?";

//insert -------------------------------------------

   public boolean insert(MemberDTO memberDTO) {// 회원 추가
      System.out.println("log: MemberDAO insert start");

      System.out.println(memberDTO);
      Connection conn = JDBCUtil.connect();
      PreparedStatement pstmt = null;
      try {
         pstmt = conn.prepareStatement(INSERT); // SQL DB에서 INSERT 준비
         pstmt.setString(1, memberDTO.getMemberEmail()); // 이메일 입력
         pstmt.setString(2, memberDTO.getMemberPassword()); // 비밀번호 입력
         pstmt.setString(3, memberDTO.getMemberName()); // 이름 입력
         pstmt.setString(4, memberDTO.getMemberPhone()); // 전화번호 입력
         pstmt.setString(5, memberDTO.getMemberNickname()); // 닉네임 입력
         pstmt.setString(6, memberDTO.getMemberProfileWay()); // 사진경로 입력
         pstmt.setString(7, memberDTO.getMemberRole()); // 권한 입력(일반, 점주, 관리자)
         int rs = pstmt.executeUpdate(); // 실행
         if (rs <= 0) {
            System.err.println("log: MemberDAO insert execute fail");
            return false;
         }
      } catch (Exception e) {
         System.err.println("log: MemberDAO insert Exception fail");
         return false;
      } finally {// 연결해제
         if (!JDBCUtil.disconnect(conn, pstmt)) { // 만약 jdbc가 연결되어 있다면
            System.err.println("log: MemberDAO insert disconnect fail"); // 연결해제 실패
            return false;
         }
         System.out.println("log: MemberDAO insert complet");
      }
      System.out.println("log: MemberDAO insert true");
      return true;
   }

   public ArrayList<MemberDTO> selectAll(MemberDTO memberDTO) { // 회원 조회
      System.out.println("log: MemberDAO selectAll start");
      ArrayList<MemberDTO> datas = new ArrayList<MemberDTO>(); // 전체 회원 담을 리스트
      Connection conn = JDBCUtil.connect();
      PreparedStatement pstmt = null;
      try {

//selectAll ----------------------------------------------------------------
         if (memberDTO.getCondition().equals("SELECTALL")) { // 전체 회원 조회
            System.out.println("log: MemberDAO selectAll condition : SELECTALL");
            pstmt = conn.prepareStatement(SELECTALL); // SELECTALL준비

//NEWMEMBERS_SELECTALL ----------------------------------------------------------------
         } else if (memberDTO.getCondition().equals("NEWMEMBERS_SELECTALL")) { // 최근 7일간 신규 회원 모두 조회.
            System.out.println("log: MemberDAO selectAll condition : NEWMEMBERS_SELECTALL");
            pstmt = conn.prepareStatement(NEWMEMBERS_SELECTALL); // NEWMEMBERS_SELECTALL준비
         }
         ResultSet rs = pstmt.executeQuery(); // 실행
         while (rs.next()) {
            MemberDTO data = new MemberDTO(); // 데이터 요소 담기 위한 객체 생성
            data.setMemberNum(rs.getInt("MEMBER_NUM")); // 회원 고유번호 입력
            data.setMemberName(rs.getString("MEMBER_NAME"));
            data.setMemberEmail(rs.getString("MEMBER_EMAIL")); // 회원 이메일 입력
            data.setMemberNickname(rs.getString("MEMBER_NICKNAME")); // 회원 닉네임 입력
            data.setMemberPhone(rs.getString("MEMBER_PHONE")); // 회원 전화번호 입력
            data.setMemberProfileWay(rs.getString("MEMBER_PROFILE_WAY")); // 회원 프로필사진경로 입력
            data.setMemberRole(rs.getString("MEMBER_ROLE")); // 회원 권한 입력
            data.setMemberHireDay(rs.getString("MEMBER_HIREDAY")); // 회원 가입일자 입력
            datas.add(data);
         }
         rs.close();
         System.out.println("log: MemberDAO selectAll complet");
      } catch (Exception e) {
         System.err.println("log: MemberDAO selectAll Exception fail");
      } finally {
         if (!JDBCUtil.disconnect(conn, pstmt)) { // 연결해제 실패
            System.err.println("log: MemberDAO selectAll disconnect fail");
         }
         System.out.println("log: MemberDAO selectAll complet"); // 연결해제 성공
      }
      System.out.println("log: MemberDAO selectAll NEWMEMBERS return datas");
      return datas; // 데이터 반환
   }

   public MemberDTO selectOne(MemberDTO memberDTO) {
      System.out.println("log: MemberDAO selectOne start");
      Connection conn = JDBCUtil.connect();
      System.out.println("conn연결");
      PreparedStatement pstmt = null;
      ResultSet rs=null;
      MemberDTO data = null;                        //데이터 요소 담기 위한 객체 생성

      try {
//EMAIL_SELECTONE ----------------------------------------------------------------
         if(memberDTO.getCondition().equals("EMAIL_SELECTONE")){            //이메일 중복회원 조회
            System.out.println("log: MemberDAO selectOne condition : EMAIL_SELECTONE");
            pstmt = conn.prepareStatement(EMAIL_SELECTONE);               //SQL DB에서 EMAIL_SELECTONE 준비
            pstmt.setString(1, memberDTO.getMemberNickname());             //이메일 입력

            rs=pstmt.executeQuery();                              //실행
            
            if(rs.next()) {
            	data = new MemberDTO();
               data.setMemberEmail(rs.getString("MEMBER_EMAIL"));
            }   
//NICKNAME_SELECTONE ----------------------------------------------------------------
         }else if(memberDTO.getCondition().equals("NICKNAME_SELECTONE")){   //닉네임 중복회원 조회
            System.out.println("log: MemberDAO selectOne condition : NICKNAME_SELECTONE");
            pstmt = conn.prepareStatement(NICKNAME_SELECTONE);            //SQL DB에서 NICKNAME_SELECTONE 준비
            pstmt.setString(1, memberDTO.getMemberNickname());             //닉네임 입력
            rs=pstmt.executeQuery();                              //실행
            System.out.println();
            if(rs.next()){
            	data = new MemberDTO();
               data.setMemberNickname(rs.getString("MEMBER_NICKNAME"));
            }

//LOGIN_SELECTONE ----------------------------------------------------------------            
         }else if(memberDTO.getCondition().equals("LOGIN_SELECTONE")){      //로그인 정보 조회 
            System.out.println("log: MemberDAO selectOne condition : LOGIN_SELECTONE");
            pstmt = conn.prepareStatement(LOGIN_SELECTONE);               //SQL DB에서 LOGIN_SELECTONE 준비
            pstmt.setString(1, memberDTO.getMemberEmail());             //이메일 입력
            pstmt.setString(2, memberDTO.getMemberPassword());             //비밀번호 입력

            System.out.println("log: MemberDAO selectOne rs before : "+memberDTO);
            rs=pstmt.executeQuery();//실행
            if(rs.next()){
            	data = new MemberDTO();
               data.setMemberNum(rs.getInt("MEMBER_NUM"));
               data.setMemberEmail(rs.getString("MEMBER_EMAIL"));
               data.setMemberNickname(rs.getString("MEMBER_NICKNAME"));
               data.setMemberRole(rs.getString("MEMBER_ROLE"));   
            }
            System.out.println("log: MemberDAO selectOne LOGIN_SELECTONE rs end : "+data);
//MEMBER_DETAIL_SELECTONE ----------------------------------------------------------------            
         }else if(memberDTO.getCondition().equals("MEMBER_DETAIL_SELECTONE")){   //마이페이지 정보 조회
            System.out.println("log: MemberDAO selectOne condition : MEMBER_DETAIL_SELECTONE");
            pstmt = conn.prepareStatement(MEMBER_DETAIL_SELECTONE);            //SQL DB에서 MEMBER_DETAIL_SELECTONE 준비
            pstmt.setInt(1, memberDTO.getMemberNum());                   //고유번호 입력

            rs=pstmt.executeQuery();                              //실행
            if(rs.next()){
            	data = new MemberDTO();
               data.setMemberNum(rs.getInt("MEMBER_NUM"));               //회원 고유번호 입력
               data.setMemberName(rs.getString("MEMBER_NAME"));         //회원 이름 입력
               data.setMemberEmail(rs.getString("MEMBER_EMAIL"));         //회원 이메일 입력
               data.setMemberNickname(rs.getString("MEMBER_NICKNAME"));         //회원 닉네임 입력
               data.setMemberPhone(rs.getString("MEMBER_PHONE"));            //회원 전화번호 입력
               data.setMemberProfileWay(rs.getString("MEMBER_PROFILE_WAY"));         //회원 프로필사진경로 입력
               data.setMemberRole(rs.getString("MEMBER_ROLE"));            //회원 권한 입력
               data.setMemberHireDay(rs.getString("MEMBER_HIREDAY"));            //회원 가입일자 입력
            }
//PASSWORD_CHECK_SELECTONE ----------------------------------------------------------------               
         }else if(memberDTO.getCondition().equals("PASSWORD_CHECK_SELECTONE")){
            System.out.println("log: MemberDAO selectOne condition : PASSWORD_CHECK_SELECTONE");
            pstmt = conn.prepareStatement(PASSWORD_CHECK_SELECTONE);         //SQL DB에서 PASSWORD_CHECK_SELECTONE 준비
            pstmt.setInt(1, memberDTO.getMemberNum());                   //고유번호 입력
            pstmt.setString(2, memberDTO.getMemberPassword());            //패스워드 입력
            rs=pstmt.executeQuery();    //실행
            System.out.println("rs:"+rs);
            if(rs.next()){
            	data = new MemberDTO();
               data.setMemberNum(rs.getInt("MEMBER_NUM"));               //회원 고유번호 입력
            }
            
//PROFILEWAY_SELECTONE ----------------------------------------------------------------               
         }else if(memberDTO.getCondition().equals("PROFILEWAY_SELECTONE")){
            System.out.println("log: MemberDAO selectOne condition : PROFILEWAY_SELECTONE");
            pstmt = conn.prepareStatement(PROFILEWAY_SELECTONE);         //SQL DB에서 PROFILEWAY_SELECTONE특정 준비
            pstmt.setInt(1, memberDTO.getMemberNum());                   //고유번호 입력
            rs=pstmt.executeQuery();                              //실행
            if(rs.next()){
            	data = new MemberDTO();
               data.setMemberProfileWay(rs.getString("MEMBER_PROFILE_WAY"));               //회원 프로필사진 경로 입력
               data.setMemberNum(rs.getInt("MEMBER_NUM"));        					       //회원 프로필사진 경로 입력
            }
         } rs.close();
         System.out.println("log: MemberDAO selectOne complet");
      }catch(Exception e){
      System.err.println("log: MemberDAO selectOne Exception fail");
   }finally
   {
      if (!JDBCUtil.disconnect(conn, pstmt)) { // 연결해제 실패
         System.err.println("log: MemberDAO selectOne disconnect fail");
      }
      System.out.println("log: MemberDAO selectOne complet"); // 연결해제 성공
   }System.out.println("log: MemberDAO selectOne return data");
   return data;
   }

   public boolean update(MemberDTO memberDTO) {
      System.out.println("log: MemberDAO update start");
      Connection conn = JDBCUtil.connect();
      PreparedStatement pstmt = null;
      try {
         if (memberDTO.getCondition().equals("EMAIL_UPDATE")) { // 이메일 수정
            pstmt = conn.prepareStatement(EMAIL_UPDATE); // SQL DB에서 UPDATE 준비
            pstmt.setString(1, memberDTO.getMemberEmail()); // 변경할 이메일 입력
            pstmt.setInt(2, memberDTO.getMemberNum()); // 회원고유번호 입력
//            System.out.println("log 인풋값 update함수 들어왔는지 확인 : "+memberDTO);

         } else if (memberDTO.getCondition().equals("PASSWORD_UPDATE")) { // 비밀번호 수정
            pstmt = conn.prepareStatement(PASSWORD_UPDATE); // SQL DB에서 UPDATE 준비
            pstmt.setString(1, memberDTO.getMemberPassword()); // 변경할 비밀번호 입력
            pstmt.setInt(2, memberDTO.getMemberNum()); // 회원 고유번호 입력

         } else if (memberDTO.getCondition().equals("NICKNAME_UPDATE")) { // 닉네임 수정
            pstmt = conn.prepareStatement(NICKNAME_UPDATE); // SQL DB에서 UPDATE 준비
            pstmt.setString(1, memberDTO.getMemberNickname()); // 변경할 닉네임 입력
            pstmt.setInt(2, memberDTO.getMemberNum()); // 회원 고유번호 입력

         } else if (memberDTO.getCondition().equals("PHONENUM_UPDATE")) { // 폰번호 수정
            pstmt = conn.prepareStatement(PHONENUM_UPDATE); // SQL DB에서 UPDATE 준비
            pstmt.setString(1, memberDTO.getMemberPhone()); // 변경할 폰번호 입력
            pstmt.setInt(2, memberDTO.getMemberNum()); // 회원 고유번호 입력

         } else if (memberDTO.getCondition().equals("PROFILEPIC_UPDATE")) { // 프로필사진경로 수정
            pstmt = conn.prepareStatement(PROFILEPIC_UPDATE); // SQL DB에서 UPDATE 준비
            pstmt.setString(1, memberDTO.getMemberProfileWay()); // 사진 경로 입력
            pstmt.setInt(2, memberDTO.getMemberNum()); // 회원 고유번호 입력
         }
         System.out.println("log: MemberDAO update excuteUpdate before : " + memberDTO);
         pstmt.executeUpdate();// 실행
      } catch (Exception e) {
         System.err.println("log: MemberDAO update Exception");
      } finally {// 연결해제
         if (!JDBCUtil.disconnect(conn, pstmt)) { // 만약 jdbc가 연결되어 있다면
            System.err.println("log: MemberDAO update disconnect fail"); // 연결해제 실패
            return false;
         }
         System.out.println("log: MemberDAO update complet");
      }
      System.out.println("log: MemberDAO update true");
      return true;
   }

   public boolean delete(MemberDTO memberDTO) {
      System.out.println("log: MemberDAO delete start");
      Connection conn = JDBCUtil.connect();
      PreparedStatement pstmt = null;
      try {
         pstmt = conn.prepareStatement(DELETE);// SQL DB에서 DELETE 준비
         pstmt.setInt(1, memberDTO.getMemberNum());
         pstmt.executeUpdate();// 실행
      } catch (SQLException e) {
         return false;
      } finally {
         // 연결해제
         if (!JDBCUtil.disconnect(conn, pstmt)) {// 만약 jdbc가 연결되어 있다면
            // 연결해제 실패
            System.err.println("log: MemberDAO delete disconnect fail");
            return false;
         }
         System.out.println("log: MemberDAO delete complet");
      }
      System.out.println("log: MemberDAO delete true");
      return true;
   }
}