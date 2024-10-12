package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.common.JDBCUtil;
import model.dto.LikeDTO;

public class LikeDAO {

	//좋아요 추가
	//받은 데이터 : 게시물 고유번호(PK), 회원 고유번호(PK) 
	//추가 데이터 : 좋아요 고유번호(PK), 게시물 고유번호(PK), 회원 고유번호(PK) 
	private final String INSERT = "INSERT INTO BB_LIKE(LIKE_NUM, BOARD_NUM, MEMBER_NUM) "
			+ "VALUES(BB_LIKE_SEQ.NEXTVAL, ?, ?)";


	//특정 회원의 특정 게시물 좋아요 여부 확인
	//받은 데이터 : 게시물 고유번호(PK), 회원 고유번호(PK)
	//조회 데이터 : 좋아요 고유번호(PK)
	private final String SELECTONE = "SELECT LIKE_NUM FROM BB_LIKE WHERE MEMBER_NUM = ? AND BOARD_NUM = ?";


	//좋아요 삭제
	//받은 데이터 : 좋아요 고유번호(PK)
	private final String DELETE = "DELETE FROM BB_LIKE WHERE LIKE_NUM = ?";





	//좋아요 추가----------------------------------------------------------------------

	public boolean insert(LikeDTO likeDTO) {
		System.out.println("log_StoreLikeDAO_insert : start");
		System.out.println("log_StoreLikeDAO_insert_controller input StoreLikeDTO : " + likeDTO.toString());

		//[1] DB 연결 객체를 conn 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
		Connection conn = JDBCUtil.connect();
		System.out.println("log_StoreLikeDAO_insert_conn null setting complete");

		//[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
		PreparedStatement pstmt = null;
		System.out.println("log_StoreLikeDAO_insert_pstmt null setting complete");

		// 비정상 프로그램 종료 방지 위한 try-catch 진행
		try {
			//[3] pstmt 변수 선언 : ( ) 안 쿼리문으로 실행 준비 완료.
			//SQL DB와 연결하여 INSERT 변수값 미리 컴파일, 실행 준비
			pstmt = conn.prepareStatement(INSERT);
			System.out.println("log_StoreLikeDAO_insert_pstmt conn");

			//[4] 인자값으로 받은 데이터 쿼리문에 삽입
			pstmt.setInt(1, likeDTO.getBoardNum());      //게시물 고유번호
			pstmt.setInt(2, likeDTO.getMemberNum());   //회원 고유번호
			System.out.println("log_StoreLikeDAO_insert pstmt input complete");

			//[5] rs 변수 선언 : INSERT 쿼리문 실행
			int rs = pstmt.executeUpdate();
			System.out.println("log_StoreLikeDAO_insert pstmt conn");

			//[6] 예외처리 : 정상실행 되지 않았을 경우, false
			if (rs <= 0) { //rs >= 1(success) / rs = 0 (fail)
				System.err.println("log_LikeDAO_insert executeUpdate() fail : if(rs <= 0)");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("log_LikeDAO_insert Exception fail : Exception e");
			return false;
			//[7] JDBC 연결 해제 진행
		} finally {
			if (!JDBCUtil.disconnect(conn, pstmt)) {// 만약 JDBC가 연결되어 있다면
				System.err.println("log_LikeDAO_insert disconnect fail");
				return false;
			}// JDBC 연결 해제 되었다면
		}System.out.println("log_StoreLikeDAO insert true");
		return true;
	}

	private boolean selectAll(LikeDTO likeDTO) {
		return false;
	}


	//특정 회원의 특정 게시물 좋아요 여부 확인-----------------------------------------------------------------------------------
	public LikeDTO selectOne(LikeDTO likeDTO) {
		System.out.println("log_StoreLikeDAO_selectOne : start");
		System.out.println("log_StoreLikeDAO_selectOne controller input likeDTO : " + likeDTO.toString());

		//[1] DB 연결 객체를 conn 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
		Connection conn = JDBCUtil.connect();
		System.out.println("log_StoreLikeDAO_selectOne conn null setting complete");

		//[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
		PreparedStatement pstmt = null;
		System.out.println("log_StoreLikeDAO_selectOn pstmt null setting complete");

		//[3] rs 변수 선언 : selectOne 쿼리문 실행
		ResultSet rs = null;
		System.out.println("log_StoreLikeDAO_selectOn rs null setting complete");

		//[4] data 변수 선언 : 결과값 담을 data
		LikeDTO data = null;
		System.out.println("log_StoreLikeDAO__selectOne data null setting complete");

		try {
			//[5] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
			//SQL DB와 연결하여 SELECTONE 변수값 미리 컴파일, 실행 준비
			pstmt = conn.prepareStatement(SELECTONE);
			System.out.println("log_StoreLikeDAO_selectOne_pstmt conn");

			//[6] 인자값으로 받은 데이터 쿼리문에 삽입
			pstmt.setInt(1, likeDTO.getMemberNum());   //회원 고유번호
			pstmt.setInt(2, likeDTO.getBoardNum());      //게시물 고유번호
			System.out.println("log_StoreLikeDAO_selectOne_pstmt input complete");

			//[7] rs 변수 선언 : SELECTONE 쿼리문 실행
			rs = pstmt.executeQuery();
			System.out.println("log_StoreLikeDAO_selectOne_executeQuery() complete");

			//[8] 좋아요 데이터 여부 불러오기
			if (rs.next()) {
				data = new LikeDTO();
				data.setLikeNum(rs.getInt("LIKE_NUM")); // 좋아요 고유번호
				System.out.println("log_StoreLikeDAO_selectOne data : " + data.getLikeNum());
			}rs.close(); // ResultSet 해제
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("log_LikeDTO_selectOne Exception fail");
			//[9] JDBC 연결 해제 진행
		} finally {
			if (!JDBCUtil.disconnect(conn, pstmt)) {// 연결해제 실패
				System.err.println("log_LikeDTO_selectOne disconnect fail");
			}// JDBC 연결 해제 되었다면
			System.out.println("log_LikeDTO_selectOne complete");
		}return data;
	}

	private boolean update(LikeDTO likeDTO) {
		return false;
	}


	//좋아요 취소(삭제)
	public boolean delete(LikeDTO likeDTO) {
		System.out.println("log_StoreLikeDAO_delete : start");
		System.out.println("log_StoreLikeDAO_delete controller input likeDTO : " + likeDTO.toString());

		//[1] DB 연결 객체를 conn 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
		Connection conn = JDBCUtil.connect();
		System.out.println("log_StoreLikeDAO_delete conn null setting complete");

		//[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
		PreparedStatement pstmt = null;
		System.out.println("log_StoreLikeDAO_delete pstmt null setting complete");

		try {
			//[3] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
			//SQL DB와 연결하여 DELETE 변수값 미리 컴파일, 실행 준비
			pstmt = conn.prepareStatement(DELETE);
			System.out.println("log_LikeDAO_delete_pstmt conn");

			//[4] 인자값으로 받은 데이터 쿼리문에 삽입
			pstmt.setInt(1, likeDTO.getLikeNum());   //좋아요 고유번호
			System.out.println("log_LikeDAO_delete_pstmt input complete");

			//[5] rs 변수 선언 : DELETE 쿼리문 실행
			int rs = pstmt.executeUpdate();
			System.out.println("log_LikeDAO_delete_executeUpdate() complete");

			//[6] 예외처리 : 정상실행 되지 않았을 경우, false
			if (rs <= 0) {//rs >= 1(success) / rs = 0 (fail)
				System.err.println("log_LikeDTO_delete execute() fail");
				return false;
			}
		} catch (Exception e) {
			System.err.println("log_LikeDTO_delete Exception fail");
			return false;
			//[7] JDBC 연결 해제 진행
		} finally {
			if (!JDBCUtil.disconnect(conn, pstmt)) {// 만약 JDBC가 연결되어 있다면
				System.err.println("log_LikeDAO_delete_disconnect fail"); // 연결해제 실패
				return false;
			}// JDBC 연결 해제 되었다면
			System.out.println("log_LikeDAO_delete_true!");
		}return true;
	}
}
