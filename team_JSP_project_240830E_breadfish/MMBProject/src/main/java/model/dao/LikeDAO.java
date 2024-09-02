package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.LikeDTO;

public class LikeDAO {
	//좋아요 추가
	private final String INSERT = "INSERT INTO BD_LIKE(LIKE_NUM, BOARD_NUM, MEMBER_NUM) VALUES((SELECT NVL(MAX(MEMBER_NUM),0)+1 FROM BD_MEMBER),?,?)";

	//SELECTONE 특정 회원(PK), 특정 게시물(PK)에 좋아요 누른 데이터 있는지 조회.
	private final String SELECTONE = "SELECT LIKE_NUM FROM BD_LIKE WHERE MEMBER_NUM = ? AND BOARD_NUM = ?";
	//좋아요 삭제
	private final String DELETE = "DELETE FROM BD_LIKE WHERE LIKE_NUM = ?";

	
	
	public boolean insert(LikeDTO likeDTO) {
		System.out.println("log: LikeDTO insert start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(INSERT);//SQL DB에서 INSERT 준비
			pstmt.setInt(1, likeDTO.getBoardNum()); 		//게시물 고유번호 입력
			pstmt.setInt(2, likeDTO.getMemberNum()); 		//회원 고유번호 입력
		
			int rs=pstmt.executeUpdate();//실행
			if(rs <= 0) {
				System.err.println("log: LikeDTO insert execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: LikeDTO insert SQLException fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {//만약 jdbc가 연결되어 있다면
				//연결해제 실패
				System.err.println("log: LikeDTO insert disconnect fail");
				return false;
			}System.out.println("log: LikeDTO insert complet");
		}System.out.println("log: LikeDTO insert true");
		return true;
	}
	
	private ArrayList<LikeDTO> selectAll(LikeDTO likeDTO) {
		ArrayList<LikeDTO> datas = new ArrayList<LikeDTO>();
		return datas;
	}
	
	public LikeDTO selectOne(LikeDTO likeDTO) {
		System.out.println("log: LikeDTO selectOne start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		LikeDTO data = new LikeDTO();//데이터 요소 담기 위한 객체 생성

		try {
			if(likeDTO.getCondition().equals("selectOne")){//좋아요 여부 확인
				System.out.println("log: LikeDTO selectOne condition : selectOne");
				pstmt = conn.prepareStatement(SELECTONE);//SQL DB에서 SELECTONE 준비
				pstmt.setInt(1, likeDTO.getMemberNum()); 		//회원 고유번호 입력
				pstmt.setInt(2, likeDTO.getBoardNum()); 		//게시물고유번호 입력

				rs=pstmt.executeQuery();//실행
				data.setLikeNum(rs.getInt("LIKE_NUM"));		//좋아요 고유번호
			} rs.close();
			System.out.println("log: LikeDTO selectOne complet");
		}catch (SQLException e) {
			System.err.println("log: LikeDTO selectOne SQLException fail");
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {//연결해제 실패
				System.err.println("log: MemberDAO selectOne disconnect fail");
			}
			System.out.println("log: MemberDAO selectOne complet");//연결해제 성공
		}System.out.println("log: MemberDAO selectOne return data");
		return data;
	}
	
	private boolean update(LikeDTO likeDTO) {
		return true;
	}

	public boolean delete(LikeDTO likeDTO) {//좋아요 삭제
		System.out.println("log: likeDTO delete start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("DELETE");//SQL DB에서 DELETE 준비
			pstmt.setInt(1, likeDTO.getLikeNum());	//좋아요 고유번호 입력
			int rs=pstmt.executeUpdate();//실행
		}catch (SQLException e) {
			System.err.println("log: likeDTO delete SQLException fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {//만약 jdbc가 연결되어 있다면
				//연결해제 실패
				System.err.println("log: likeDTO delete disconnect fail");
				return false;
			}System.out.println("log: likeDTO delete complet");
		}System.out.println("log: likeDTO delete true");
		return true;
	}
}