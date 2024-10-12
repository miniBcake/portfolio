package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.ReplyDTO;

public class ReplyDAO {
	private final String INSERT = "INSERT INTO BB_REPLY(REPLY_NUM, REPLY_CONTENT, MEMBER_NUM, BOARD_NUM) VALUES((SELECT NVL(MAX(REPLY_NUM)+1,1) FROM BB_REPLY), ?, ?, ?)";
	private final String UPDATE = "UPDATE BB_REPLY SET REPLY_CONTENT = ? WHERE REPLY_NUM = ?";
	private final String DELETE = "DELETE FROM BB_REPLY WHERE REPLY_NUM = ?";
	
	private final String SELECTALL_BOARD = "SELECT RN, REPLY_NUM, REPLY_CONTENT, MEMBER_NUM, MEMBER_NICKNAME, MEMBER_PROFILE_WAY, REPLY_WRITE_DAY FROM (SELECT ROW_NUMBER() OVER(ORDER BY REPLY_WRITE_DAY DESC) AS RN, "
										+ " br.REPLY_NUM, br.REPLY_CONTENT, br.MEMBER_NUM, bm.MEMBER_NICKNAME, bm.MEMBER_PROFILE_WAY ,br.REPLY_WRITE_DAY FROM BB_REPLY br  JOIN BB_MEMBER bm ON br.MEMBER_NUM = bm.MEMBER_NUM "
										+ "	WHERE br.BOARD_NUM = ? ORDER BY br.REPLY_WRITE_DAY DESC) WHERE RN BETWEEN ? AND ?";
	private final String SELECTALL_MYPAGE = "SELECT RN, REPLY_NUM, REPLY_CONTENT, MEMBER_NICKNAME, MEMBER_PROFILE_WAY, BOARD_NUM, REPLY_WRITE_DAY FROM (SELECT ROW_NUMBER() OVER(ORDER BY REPLY_WRITE_DAY DESC) AS RN, "
										+ " br.REPLY_NUM, br.REPLY_CONTENT, br.BOARD_NUM, bm.MEMBER_NICKNAME, bm.MEMBER_PROFILE_WAY ,br.REPLY_WRITE_DAY FROM BB_REPLY br  JOIN BB_MEMBER bm ON br.MEMBER_NUM = bm.MEMBER_NUM "
										+ "	WHERE br.MEMBER_NUM = ? ORDER BY br.REPLY_WRITE_DAY DESC) WHERE RN BETWEEN ? AND ?";
	
	private final String SELECTONE = "SELECT br.REPLY_NUM, br.REPLY_CONTENT, br.MEMBER_NUM, bm.MEMBER_NICKNAME, bm.MEMBER_PROFILE_WAY ,br.REPLY_WRITE_DAY FROM BB_REPLY br JOIN BB_MEMBER bm ON br.MEMBER_NUM = bm.MEMBER_NUM "
									+ " WHERE br.REPLY_NUM = ?";
	private final String SELECTONE_BOARD = "SELECT COUNT(*) AS CNT FROM BB_REPLY WHERE BOARD_NUM = ?";
	private final String SELECTONE_MYPAGE = "SELECT COUNT(*) AS CNT FROM BB_REPLY WHERE MEMBER_NUM = ?";
	
	public boolean insert(ReplyDTO replyDTO) {
		System.out.println("log: Reply insert start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, replyDTO.getReplyContent()); //댓글내용
			pstmt.setInt(2, replyDTO.getMemberNum()); 		//멤버 번호
			pstmt.setInt(3, replyDTO.getBoardNum()); 		//게시글 번호
			//넘어온 값 확인 로그
			System.out.println("log: parameter getReplyContent : "+replyDTO.getReplyContent());
			System.out.println("log: parameter getMemberNum : "+replyDTO.getMemberNum());
			System.out.println("log: parameter getBoardNum : "+replyDTO.getBoardNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: Reply insert execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Reply insert SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Reply insert Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Reply insert disconnect fail");
				return false;
			}
			System.out.println("log: Reply insert end");
		}
		System.out.println("log: Reply insert true");
		return true;
	}
	
	public boolean update(ReplyDTO replyDTO) {
		System.out.println("log: Reply update start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1, replyDTO.getReplyContent()); //댓글 내용
			pstmt.setInt(2, replyDTO.getReplyNum()); 		//댓글 번호
			//넘어온 값 확인 로그
			System.out.println("log: parameter getReplyContent : "+replyDTO.getReplyContent());
			System.out.println("log: parameter getReplyNum : "+replyDTO.getReplyNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: Reply update execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Reply update SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Reply update Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Reply update disconnect fail");
				return false;
			}
			System.out.println("log: Reply update end");
		}
		System.out.println("log: Reply update true");
		return true;
	}
	
	public boolean delete(ReplyDTO replyDTO) {
		System.out.println("log: Reply delete start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, replyDTO.getReplyNum()); //댓글 번호
			//넘어온 값 확인 로그
			System.out.println("log: parameter getReplyNum : "+replyDTO.getReplyNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: Reply delete execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Reply delete SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Reply delete Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Reply delete disconnect fail");
				return false;
			}
			System.out.println("log: Reply delete end");
		}
		System.out.println("log: Reply delete true");
		return true;
	}
	
	public ArrayList<ReplyDTO> selectAll(ReplyDTO replyDTO) {
		System.out.println("log: Reply selectAll start");
		ArrayList<ReplyDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			if(replyDTO.getCondition().equals("ALL_REPLIES")) {
				//게시물 댓글 조회
				System.out.println("log: Reply selectAll : ALL_REPLIES");
				pstmt = conn.prepareStatement(SELECTALL_BOARD);
				pstmt.setInt(1, replyDTO.getBoardNum()); 	//게시물 pk(Fk)
				pstmt.setInt(2, replyDTO.getStartNum());	//페이지네이션 용 시작번호
				pstmt.setInt(3, replyDTO.getEndNum());		//페이지네이션 용 끝번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getBoardNum : "+replyDTO.getBoardNum());
				System.out.println("log: parameter getStartNum : "+replyDTO.getStartNum());
				System.out.println("log: parameter getEndNum : "+replyDTO.getEndNum());
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) { 
					ReplyDTO data = new ReplyDTO();
					data.setReplyNum(rs.getInt("REPLY_NUM")); 						//댓글 번호
					data.setReplyContent(rs.getString("REPLY_CONTENT"));			//댓글 내용
					data.setMemberNum(rs.getInt("MEMBER_NUM"));						//멤버 번호
					data.setMemberNickname(rs.getString("MEMBER_NICKNAME"));		//작성자 닉네임
					data.setMemberProfileWay(rs.getString("MEMBER_PROFILE_WAY"));	//작성자 프로필
					data.setReplyWriteDay(rs.getString("REPLY_WRITE_DAY"));			//작성일시
					//반환된 객체 리스트에 추가
					datas.add(data); 
					System.out.print(" | result "+data.getReplyNum());
				}
				rs.close();
			}
			else if(replyDTO.getCondition().equals("MY_REPLIES")) {
				//내가 쓴 댓글 모아보기 (마이페이지)
				System.out.println("log: Reply selectAll : MY_REPLIES");
				pstmt = conn.prepareStatement(SELECTALL_MYPAGE);
				pstmt.setInt(1, replyDTO.getMemberNum()); 	//멤버 번호
				pstmt.setInt(2, replyDTO.getStartNum());	//페이지네이션 용 시작번호
				pstmt.setInt(3, replyDTO.getEndNum());		//페이지네이션 용 끝번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberNum : "+replyDTO.getMemberNum());
				System.out.println("log: parameter getStartNum : "+replyDTO.getStartNum());
				System.out.println("log: parameter getEndNum : "+replyDTO.getEndNum());
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) { 
					ReplyDTO data = new ReplyDTO();
					data.setReplyNum(rs.getInt("REPLY_NUM")); 						//댓글 번호
					data.setReplyContent(rs.getString("REPLY_CONTENT"));			//댓글 내용
					data.setMemberNickname(rs.getString("MEMBER_NICKNAME"));		//작성자 닉네임
					data.setMemberProfileWay(rs.getString("MEMBER_PROFILE_WAY"));	//작성자 프로필
					data.setBoardNum(rs.getInt("BOARD_NUM"));						//게시글 번호
					data.setReplyWriteDay(rs.getString("REPLY_WRITE_DAY"));			//작성일시
					//반환된 객체 리스트에 추가
					datas.add(data); 
					System.out.print(" | result "+data.getReplyNum());
				}
				rs.close();
			}
			else {
				//컨디션값 오류
				System.err.println("log: Reply selectAll condition fail");
			}
			System.out.println("end");
		} catch (SQLException e) {
			System.err.println("log: Reply selectAll SQLException fail");
			datas.clear();//잔여데이터 삭제
		} catch (Exception e) {
			System.err.println("log: Reply selectAll Exception fail");
			datas.clear();//잔여데이터 삭제
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Reply selectAll disconnect fail");
				datas.clear();//잔여데이터 삭제
			}
			System.out.println("log: Reply selectAll end");
		}
		System.out.println("log: Reply selectAll return datas");
		return datas;
	}
	
	public ReplyDTO selectOne(ReplyDTO replyDTO) {
		System.out.println("log: Reply selectOne start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		ReplyDTO data = null;
		try {
			if(replyDTO.getCondition().equals("CNT_BOARD_RP")) {
				//게시물 댓글 조회
				System.out.println("log: Reply selectOne : CNT_BOARD_RP");
				pstmt = conn.prepareStatement(SELECTONE_BOARD);
				pstmt.setInt(1, replyDTO.getBoardNum()); 	//게시물 pk(Fk)
				//넘어온 값 확인 로그
				System.out.println("log: parameter getBoardNum : "+replyDTO.getBoardNum());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new ReplyDTO();
					data.setCnt(rs.getInt("CNT")); //댓글 개수
					System.out.println("result exists");
				}
				rs.close();
			}
			else if(replyDTO.getCondition().equals("CNT_MY_RP")) {
				//내가 쓴 댓글 모아보기 (마이페이지)
				System.out.println("log: Reply selectOne : CNT_MY_RP");
				pstmt = conn.prepareStatement(SELECTONE_MYPAGE);
				pstmt.setInt(1, replyDTO.getMemberNum()); 	//멤버 번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberNum : "+replyDTO.getMemberNum());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new ReplyDTO();
					data.setCnt(rs.getInt("CNT")); //댓글 개수
					System.out.println("result exists");
				}
				rs.close();
			}
			else if(replyDTO.getCondition().equals("REPLY_ONE")) {
				//댓글 하나 조회
				System.out.println("log: Reply selectOne : REPLY_ONE");
				pstmt = conn.prepareStatement(SELECTONE);
				pstmt.setInt(1, replyDTO.getReplyNum()); 	//댓글 번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberNum : "+replyDTO.getMemberNum());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new ReplyDTO();
					data.setReplyNum(rs.getInt("REPLY_NUM")); 						//댓글 번호
					data.setReplyContent(rs.getString("REPLY_CONTENT"));			//댓글 내용
					data.setMemberNum(rs.getInt("MEMBER_NUM"));						//멤버 번호
					data.setMemberNickname(rs.getString("MEMBER_NICKNAME"));		//작성자 닉네임
					data.setMemberProfileWay(rs.getString("MEMBER_PROFILE_WAY"));	//작성자 프로필
					data.setReplyWriteDay(rs.getString("REPLY_WRITE_DAY"));			//작성일시
					System.out.println("result exists");
				}
				rs.close();
			}
			else {
				//컨디션값 오류
				System.err.println("log: Reply selectOne condition fail");
			}
			System.out.println("end");
		} catch (SQLException e) {
			System.err.println("log: Reply selectOne SQLException fail");
			return null;
		} catch (Exception e) {
			System.err.println("log: Reply selectOne Exception fail");
			return null;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Reply selectOne disconnect fail");
				return null;
			}
			System.out.println("log: Reply selectAll end");
		}
		System.out.println("log: Reply selectAll return datas");
		return data;
	}
}
