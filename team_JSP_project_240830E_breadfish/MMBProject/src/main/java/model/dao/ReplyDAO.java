package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.ReplyDTO;

public class ReplyDAO {
//   private final String SELECTONE = "SELECT R.REPLY_NUM, B.BOARD_TITLE, R.REPLY_CONTENT, M.MEMBER_NICKNAME, R.REPLY_WRITE_DAY"
//         + "   FROM BD_REPLY R JOIN BD_MEMBER M ON M.MEMBER_NUM = R.MEMBER_NUM"
//         + "   JOIN BD_BOARD B ON B.BOARD_NUM =R.BOARD_NUM WHERE R.MEMBER_NUM = ? AND R.BOARD_NUM = ?";
//   
//   //SELECTONE 특정 회원 댓글 조회 : 인풋값 회원 고유번호와 일치할 때, 댓글 고유번호, 댓글 내용 회원 작성자명, 작성일시, 게시물 번호 모두 출력.
//   private final String SELECTALL_MEMBER_REPLY = "SELECT R.REPLY_NUM, R.REPLY_CONTENT, M.MEMBER_NICKNAME, R.REPLY_WRITE_DAY,B.BOARD_NUM"
//         + "FROM BD_REPLY R JOIN BD_MEMBER M ON M.MEMBER_NUM = R.MEMBER_NUM"
//         + "JOIN BD_BOARD B ON B.BOARD_NUM =R.BOARD_NUM WHERE M.MEMBER_NUM = ?";

//   //SELECTONE 특정 게시물 댓글목록:게시글 고유번호 fk받아와서 특정 게시물 확인, 해당 게시물의 댓글 모두 출력.    
//   private final String SELECTALL_BOARD_REPLY = "SELECT R.REPLY_NUM, R.REPLY_CONTENT, M.MEMBER_NICKNAME, M.MEMBER_PROFILE_WAY, R.REPLY_WRITE_DAY, BRC.REPLY_COUNT"
//         + "   FROM BD_REPLY R JOIN BD_MEMBER M ON M.MEMBER_NUM = R.MEMBER_NUM JOIN BD_BOARD B ON B.BOARD_NUM = R.BOARD_NUM"
//         + "   JOIN (SELECT BOARD_NUM, COUNT(REPLY_NUM) AS REPLY_COUNT FROM BD_REPLY GROUP BY BOARD_NUM) BRC ON BRC.BOARD_NUM = ?";
//   //페이지 범위, 댓글 고유번호, 내용, 닉네임, 프로필경로, 작성일자, 댓글 페이지(시작~끝) 조회

//   //   게시물,회원PK 인풋값으로 댓글 정보 반환
//   private final String MEMBER_REPLY_SELECTONE = "SELECT R.REPLY_NUM, B.BOARD_TITLE, R.REPLY_CONTENT, M.MEMBER_NICKNAME, R.REPLY_WRITE_DAY FROM REPLY_NUM";

   // 댓글 추가 : 댓글 PK, 댓글 내용, 작성자(FK), 작성일시, 게시물 번호(FK) 추가
   private final String INSERT = "INSERT INTO BD_REPLY(REPLY_NUM, REPLY_CONTENT, MEMBER_NUM, BOARD_NUM)"
         + "VALUES((SELECT NVL(MAX(REPLY_NUM),0)+1 FROM BD_REPLY),'?',?, ?)";

   // SELECTALL 특정 게시물 댓글 모두 조회- 페이지 범위, 댓글 고유번호, 내용, 닉네임, 프로필경로, 작성일자, 댓글 페이지(시작~끝) 조회
   private final String SELECTALL = "SELECT REPLY_NUM, REPLY_CONTENT, MEMBER_NICKNAME, MEMBER_PROFILE_WAY, REPLY_WRITE_DAY"
         + "   FROM (SELECT ROW_NUMBER() OVER(ORDER BY REPLY_WRITE_DAY DESC) AS RN, SR.REPLY_NUM, SR.REPLY_CONTENT, M.MEMBER_NICKNAME, M.MEMBER_PROFILE_WAY, SR.REPLY_WRITE_DAY"
         + " FROM BD_REPLY SR JOIN BD_MEMBER M ON M.MEMBER_NUM = SR.MEMBER_NUM"
         + "   WHERE BOARD_NUM = ?) WHERE RN BETWEEN ? AND ? ORDER BY REPLY_WRITE_DAY DESC";


   // SELECTONE 댓글 총 개수 카운트 조회
   private final String ALLCOUNT_SELECTONE = "SELECT COUNT(REPLY_NUM) AS REPLY_COUNT"
         + "   FROM BD_REPLY GROUP BY BOARD_NUM HAVING BOARD_NUM=?";

   // DELETE 댓글 삭제 : pk값 넘겨오면, 해당댓글 찾아서 모두 삭제
   private final String DELETE = "DELETE FROM BD_REPLY WHERE REPLY_NUM = ?";

   // UPDATE 댓글 수정 : pk값 넘겨오면, 댓글내용 수정하기
   private final String UPDATE = "UPDATE BD_REPLY SET REPLY_CONTENT = '?' WHERE REPLY_NUM = 1";



   
   public boolean insert(ReplyDTO replyDTO) {// 댓글 추가 기능
      System.out.println("log: ReplyDTO insert start");
      Connection conn = JDBCUtil.connect();
      PreparedStatement pstmt = null;
      try {
         pstmt = conn.prepareStatement(INSERT); // SQL DB에서 INSERT 준비
         pstmt.setString(1, replyDTO.getReplyContent()); // 댓글내용 입력
         pstmt.setInt(2, replyDTO.getMemberNum()); // 회원 고유번호 입력
         pstmt.setInt(3, replyDTO.getBoardNum()); // 게시물 고유번호 입력

         int rs = pstmt.executeUpdate();// 실행
         if (rs <= 0) {
            System.err.println("log: ReplyDTO insert execute fail");
            return false;
         }
      } catch (Exception e) {
         System.err.println("log: ReplyDTO insert Exception fail");
         return false;
      } finally {
         // 연결해제
         if (!JDBCUtil.disconnect(conn, pstmt)) {// 만약 jdbc가 연결되어 있다면
            // 연결해제 실패
            System.err.println("log: ReplyDTO insert disconnect fail");
            return false;
         }
         System.out.println("log: ReplyDTO insert complet");
      }
      System.out.println("log: ReplyDTO insert true");
      return true;
   }

   public ArrayList<ReplyDTO> selectAll(ReplyDTO replyDTO) {// 게시물 댓글 전체 조횐
      System.out.println("log: ReplyDTO selectAll start");
      Connection conn = JDBCUtil.connect();
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      ArrayList<ReplyDTO> datas = new ArrayList<ReplyDTO>();// 댓글 데이터 담을 객체 생성
      ReplyDTO data = new ReplyDTO();// 데이터 요소 담기 위한 객체 생성

      try {
         System.out.println("log: ReplyDTO selectAll condition : SELECTALL");
         pstmt = conn.prepareStatement(SELECTALL);// SQL DB에서 SELECTALL 준비
         pstmt.setInt(1, replyDTO.getBoardNum()); // 게시물 고유번호 입력
         pstmt.setInt(2, replyDTO.getPageStartNum()); // 시작페이지 입력
         pstmt.setInt(3, replyDTO.getPageEndNum()); // 마지막 페이지 입력

         rs = pstmt.executeQuery();// 실행
         while(rs.next()) {
         data.setReplyNum(rs.getInt("REPLY_NUM"));//댓글 고유번호 넣기
         data.setReplyContent(rs.getString("REPLY_CONTENT"));// 댓글 내용 넣기
         data.setReplyNickName(rs.getString("MEMBER_NICKNAME"));// 댓글 닉네임 넣기
         data.setReplyWriteDay(rs.getString("MEMBER_PROFILE_WAY"));// 댓글 프로필사진경로 넣기
         data.setReplyWriteDay(rs.getString("REPLY_WRITE_DAY"));// 댓글 작성일자 넣기
         }
         datas.add(data);
         rs.close();
         System.out.println("log: ReplyDTO selectall complet");
      } catch (SQLException e) {
         System.err.println("log: ReplyDTO selectall SQLException fail");
      } finally {
         // 연결해제
         if (!JDBCUtil.disconnect(conn, pstmt)) {// 연결해제 실패
            System.err.println("log: ReplyDTO selectall disconnect fail");
         }
         System.out.println("log: ReplyDTO selectall complet");// 연결해제 성공
      }
      System.out.println("log: ReplyDTO selectall return data");
      return datas;
   }

   
   
   
   public ReplyDTO selectOne(ReplyDTO replyDTO) {// 댓글 조회
      System.out.println("log: replyDTO selectOne start");
      Connection conn = JDBCUtil.connect();
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      ReplyDTO data = new ReplyDTO();// 데이터 요소 담기 위한 객체 생성
      
////MEMBER_REPLY_SELECTONE---------------------------         
//         if(replyDTO.getCondition().equals("MEMBER_REPLY_SELECTONE")) {//특정 회원 특정게시물의 댓글 조회
//            System.out.println("log: replyDTO selectOne condition : MEMBER_REPLY_SELECTONE");
//            pstmt = conn.prepareStatement(MEMBER_REPLY_SELECTONE); // SQL DB에서 MEMBER_REPLY_SELECTONE 준비
//            pstmt.setInt(1, replyDTO.getMemberNum()); // 회원 고유번호 입력
//            pstmt.setInt(2, replyDTO.getBoardNum()); // 게시물 고유번호 입력
//
//            rs = pstmt.executeQuery();// 실행
//            if(rs.next()) {
//            data.setReplyNum(rs.getInt("REPLY_NUM")); // 댓글 고유번호 넣기
//            data.setReplyTitle(rs.getString("BOARD_TITLE")); // 댓글 제목 넣기
//            data.setReplyContent(rs.getString("REPLY_CONTENT")); // 댓글 내용 넣기
//            data.setReplyNickName(rs.getString("MEMBER_NICKNAME")); // 댓글 닉네임 넣기
//            data.setReplyWriteDay(rs.getString("REPLY_WRITE_DAY")); // 댓글 작성일자 넣기
//            }
//         }
//ALLCOUNT_SELECTONE--------------------------
//         if(replyDTO.getCondition().equals("ALLCOUNT_SELECTONE")) {//게시물 총 댓글 개수 조회
      try {
         System.out.println("log: replyDTO selectOne condition : ALLCOUNT_SELECTONE");
         pstmt = conn.prepareStatement(ALLCOUNT_SELECTONE); // SQL DB에서 ALLCOUNT_SELECTONE 준비
         pstmt.setInt(1, replyDTO.getMemberNum()); // 회원 고유번호 입력
            
         rs = pstmt.executeQuery();// 실행
         if(rs.next()) {
         data.setReplyCount(rs.getInt("REPLY_COUNT")); // 댓글 고유번호 넣기      
         }
      rs.close();
      System.out.println("log: replyDTO selectOne complet");
      } catch (SQLException e) {
         System.err.println("log: replyDTO selectOne SQLException fail");
      } finally {
         // 연결해제
         if (!JDBCUtil.disconnect(conn, pstmt)) {// 연결해제 실패
            System.err.println("log: replyDTO selectOne disconnect fail");
         }
         System.out.println("log: replyDTO selectOne complet");// 연결해제 성공
      }
      System.out.println("log: replyDTO selectOne return data");
      return data;
   }

   public boolean update(ReplyDTO replyDTO) {
      System.out.println("log: ReplyDTO update start");
      Connection conn = JDBCUtil.connect();
      PreparedStatement pstmt = null;
      try {
         pstmt = conn.prepareStatement("UPDATE"); // SQL DB에서 UPDATE 준비
         pstmt.setString(1, replyDTO.getReplyContent()); // 댓글 내용 입력
         pstmt.setInt(2, replyDTO.getReplyNum()); // 댓글고유번호 입력
         pstmt.executeUpdate();// 실행
         
      } catch (Exception e) {
         System.err.println("log: ReplyDTO update Exception fail");
         return false;
      } finally {
         // 연결해제
         if (!JDBCUtil.disconnect(conn, pstmt)) {// 만약 jdbc가 연결되어 있다면
            // 연결해제 실패
            System.err.println("log: ReplyDTO update disconnect fail");
            return false;
         }
         System.out.println("log: ReplyDTO update complet");
      }
      System.out.println("log: ReplyDTO update true");
      return true;
   }

   public boolean delete(ReplyDTO replyDTO) {
      System.out.println("log: ReplyDTO delete start");
      Connection conn = JDBCUtil.connect();
      PreparedStatement pstmt = null;
      try {
         pstmt = conn.prepareStatement("DELETE");// SQL DB에서 DELETE 준비
         pstmt.setInt(1, replyDTO.getReplyNum());// 댓글 고유번호 입력
         pstmt.executeUpdate();// 실행(댓글 삭제)
      } catch (Exception e) {
         System.err.println("log: ReplyDTO delete Exception fail");
         return false;
      } finally {
         // 연결해제
         if (!JDBCUtil.disconnect(conn, pstmt)) {// 만약 jdbc가 연결되어 있다면
            // 연결해제 실패
            System.err.println("log: ReplyDTO delete disconnect fail");
            return false;
         }
         System.out.println("log: ReplyDTO delete complet");
      }
      System.out.println("log: ReplyDTO delete true");
      return true;
   }
}