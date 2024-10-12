package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.StoreWorkDTO;

public class StoreWorkDAO {
	// 등록데이터 : 영업고유번호(PK), 가게 고유번호(FK-여러행으로 발생), 영업 요일(7개중 1개씩 입력), 영업시작시간, 영업종료시간
	final String INSERT = "INSERT INTO BB_STORE_WORK(WORK_NUM, STORE_NUM, STORE_WORK_WEEK, STORE_WORK_OPEN, STORE_WORK_CLOSE) \r\n"
			+ "VALUES ((SELECT NVL(MAX(WORK_NUM)+1,1) FROM BB_STORE_WORK), ?, ?, TO_DATE(?, 'HH24:MI:SS'), TO_DATE(?, 'HH24:MI:SS'))";

	// 특정 가게 영업방식 상세조회
	// 받은 데이터 : 가게 고유번호
	// 조회 데이터 : 가게 영업 요일, 영업시작,종료시간
	final String selectAll = "SELECT STORE_WORK_WEEK, \r\n"
			+ "TO_CHAR(STORE_WORK_OPEN, 'HH24:MI') AS STORE_OPEN, \r\n"
			+ "TO_CHAR(STORE_WORK_CLOSE, 'HH24:MI') AS STORE_CLOSED\r\n"
			+ "FROM BB_STORE_WORK WHERE STORE_NUM = ?";
	
	//가게 영업방식 업데이트(고유번호의 1개의 행 변경)
	//받은 데이터 : 가게 고유번호, 영업요일
	//업데이트 : 영업요일, 시작, 종료 시간
	final String UPDATE = "UPDATE BB_STORE_WORK\r\n"
			+ "SET STORE_WORK_WEEK = ?, \r\n"
			+ "STORE_WORK_OPEN = TO_DATE(?, 'HH24:MI:SS'),\r\n"
			+ "STORE_WORK_CLOSE = TO_DATE(?, 'HH24:MI:SS')\r\n"
			+ "WHERE STORE_NUM = ? AND STORE_WORK_WEEK = ?";
	
	//BB_VIEW_DELETE_JOIN : 가게 고유번호 및 영업요일 조회(view 활용)
	//가게 고유번호, 영업요일 일치 시 삭제
	final String DELETE = "DELETE BB_VIEW_DELETE_JOIN WHERE STORE_NUM =? AND STORE_WORK_WEEK = ? ";
	
	//CREATE VIEW BB_VIEW_DELETE_JOIN AS
	//SELECT S.STORE_NUM, SW.STORE_WORK_WEEK
	//FROM BB_STORE S
	//JOIN BB_STORE_WORK SW ON S.STORE_NUM = SW.STORE_NUM
	
// StoreWorkDAO insert 영업정보 추가----------------------------------------------------------------------------------
	// 영업요일 배열리스트 사이즈에 따라 가게 고유번호, 영업시작시간, 영업종료시간 반복
	public boolean insert(StoreWorkDTO storeWorkDTO) {
		System.out.println("log_StoreWorkDAO_insert : start");
		System.out.println("log_StoreWorkDAO_insert_controller input StoreWorkDTO : " +  storeWorkDTO);
		
		
		//[1] DB 연결 객체를 conn 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
		Connection conn = JDBCUtil.connect();
		System.out.println("log_StorWorkDAO_insert_conn setting complete");
		
		
		//[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
		PreparedStatement pstmt = null;
		System.out.println("log_StorWorkDAO_insert_pstmt null setting complete");
		
		// 비정상 프로그램 종료 방지 위한 try-catch 진행
		try {
			//[3] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
			//SQL DB와 연결하여 INSERT 변수값 미리 컴파일, 실행 준비
			pstmt = conn.prepareStatement(INSERT);
			System.out.println("log_StoreWorkDAO_insert_pstmt conn");
			
			//[4] 인자값으로 받은 데이터 쿼리문에 삽입
			pstmt.setInt(1, storeWorkDTO.getStoreNum());			//가게 고유번호 입력
			pstmt.setString(2, storeWorkDTO.getStoreWorkWeek());	//가게 영업요일 입력
			pstmt.setString(3, storeWorkDTO.getStoreWorkOpen());	//가게 영업시작시간 입력
			pstmt.setString(4, storeWorkDTO.getStoreWorkClose());	//가게 영업종료시간 입력
			System.out.println("log_StoreWorkDAO_insert_pstmt input complete");
			
			//[5] rs 변수 선언 : INSERT 쿼리문 실행
			int rs = pstmt.executeUpdate();
			System.out.println("log_StoreWorkDAO_insert_excuteUpdate() complete");

			
			//[6] 예외처리 : 정상실행 되지 않았을 경우, false
			if(rs <= 0) {//rs = 1(success) / rs = 0 (fail)
				System.err.println("log_StoreWorkDAO_insert_executeUpdate() fail : if(rs <= 0)");
				return false;
			}
		}catch (Exception e) {
				System.err.println("log_StoreWorkDAO_insert_Exception fail : Exception e ");
				return false;
		//[7] JDBC 연결 해제 진행
		}finally { 
			if (!JDBCUtil.disconnect(conn, pstmt)) { // 만약 JDBC가 연결되어 있다면
				System.err.println("log_StoreWorkDAO_insert_disconnect fail"); // 연결해제 실패
				return false; 
			} // JDBC 연결 해제 되었다면
		}System.out.println("log_StoreWorkDAO_insert_true!");// 연결해제 성공
		return true; // 데이터 반환
	}
	
// StoreWorkDAO selectAll 영업정보 상세 조회----------------------------------------------------------------------------------
	public ArrayList<StoreWorkDTO> selectAll(StoreWorkDTO storeWorkDTO) {
		
		System.out.println("log_StoreWorkDAO_selectAll : start");
		System.out.println("log_StoreWorkDAO_selectAll_controller input StoreWorkDAO : " + storeWorkDTO.toString());
		
		//[1] DB 연결 객체를 conn. 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
		Connection conn = JDBCUtil.connect();
		System.out.println("log_StoreWorkDAO_selectAll_conn setting complete");

		//[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
		PreparedStatement pstmt = null;
		System.out.println("log_StoreWorkDAO_selectAll_pstmt null setting complete");
		
		//[3] rs 변수 선언 : selectAlle 쿼리문 실행
		ResultSet rs = null;
		System.out.println("log_StoreWorkDAO_selectAll_rs null setting complete");
		
		//[4] datas 변수 선언 : 전체 영업정보 데이터 담을 배열리스트
		ArrayList <StoreWorkDTO> datas = new ArrayList <StoreWorkDTO>();
		StoreWorkDTO data = new StoreWorkDTO();	
		try {
			//[5] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
			//SQL DB와 연결하여 SELECTALL 변수값 미리 컴파일, 실행 준비
			pstmt = conn.prepareStatement(selectAll);
			System.out.println("log_StoreWorkDAO_selectAll_pstmt conn");
			
			//[6] 인자값으로 받은 데이터 쿼리문에 삽입
			pstmt.setInt(1, storeWorkDTO.getStoreNum());	//가게 고유번호 입력
			
			//[7] rs 변수 선언 : selectAll 쿼리문 실행
			rs = pstmt.executeQuery();	
			System.out.println("log_StoreWorkDAO_selectAll_executeQuery() complete");
			
			//[8] 특정 가게 영업정보 데이터 불러오기
			while(rs.next()) {
				data = new StoreWorkDTO();
				data.setStoreWorkWeek(rs.getString("STORE_WORK_WEEK"));
				data.setStoreWorkOpen(rs.getString("STORE_OPEN"));
				data.setStoreWorkClose(rs.getString("STORE_CLOSED"));
	            System.out.println("log_StoreWorkDAO_selectAll_data : " + data);
	            datas.add(data);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("log_StoreWorkDAO_selectAll_Exception fail : Exception e ");
		}finally {
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				System.err.println("log_StoreWorkDAO_selectAll_disconnect fail");// 연결해제 실패
			}// JDBC 연결 해제 되었다면
			System.out.println("log_StoreWorkDAO_selectAll_complet"); // 연결해제 성공
		}
		System.out.println("log_StoreWorkDAO_selectAll_return datas");
		return datas; // 데이터 반환
	}
		
	
	
	private StoreWorkDAO selectOne(StoreWorkDAO storeWorkDTO) {
		return storeWorkDTO;
	}
	
// StoreWorkDAO update 영업정보 수정----------------------------------------------------------------------------------
	public boolean update(StoreWorkDTO storeWorkDTO) {
		//[1] DB 연결 객체  선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
		Connection conn = JDBCUtil.connect();
		
		//[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
		PreparedStatement pstmt = null;
		System.out.println("log_StorWorkDAO_update_pstmt null setting complete");
	
		try{
			//[3] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.SQL DB와 연결하여 INSERT 변수값 미리 컴파일, 실행 준비
			pstmt = conn.prepareStatement(UPDATE);

			/*[4] 인자값으로 받은 데이터 쿼리문에 삽입: 가게 고유번호(FK), 영업요일, 영업시작,종료시간*/
			pstmt.setString(1, storeWorkDTO.getStoreWorkWeek());
			pstmt.setString(2, storeWorkDTO.getStoreWorkOpen());
			pstmt.setString(3, storeWorkDTO.getStoreWorkClose());
			pstmt.setInt(4, storeWorkDTO.getStoreNum());
			pstmt.setString(5, storeWorkDTO.getStoreWorkWeek());

			System.out.println("log_StorWorkDAO_update_pstmt complete");
		
			//[5] rs 변수 선언 : UPDATE 쿼리문 실행
			int rs = pstmt.executeUpdate();
		
			//[6] 예외처리 : 정상실행 되지 않았을 경우, false
			if (rs <= 0) { //rs = 1(success) / rs = 0 (fail)
				System.err.println("log_StoreWorkDAO_update_executeUpdate() fail : if(rs <= 0)");
				return false;
			}
		} catch (Exception e) {
			System.err.println("log_StoreWorkDAO_update_Exception fail : Exception e ");
			return true;
			//[7] JDBC 연결 해제 진행
		} finally { 
			if (!JDBCUtil.disconnect(conn, pstmt)) { // 만약 JDBC가 연결되어 있다면
				System.err.println("log_StoreWorkDAO_update_disconnect fail"); // 연결해제 실패
				return false; 
			} // JDBC 연결 해제 되었다면
		}System.out.println("log_StoreWorkDAO_update_true!");
		return true;
	}
	
	public boolean delete(StoreWorkDTO storeWorkDTO) {
		System.out.println("log_StoreWorkDTO_delete : start");
		System.out.println("log_StoreWorkDTO_delete_controller input StoreWorkDTO : " + storeWorkDTO);
		
		//[1] DB 연결 객체를 conn 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
		Connection conn = JDBCUtil.connect();
		System.out.println("log_StoreWorkDTO_delete_conn null setting");

				
		//[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
		PreparedStatement pstmt = null;
		System.out.println("log_StoreWorkDTO_delete_pstmt null setting complete");

		// 비정상 프로그램 종료 방지 위한 try-catch 진행
		try { 
			//[3] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
			//SQL DB와 연결하여 DELETE 변수값 미리 컴파일, 실행 준비
			pstmt = conn.prepareStatement(DELETE); // input값 storeWorkDTO 이하 입력
			System.out.println("log_StoreWorkDTO_delete_pstmt conn");
			
			//[4] 인자값으로 받은 데이터 쿼리문에 삽입
			pstmt.setInt(1, storeWorkDTO.getStoreNum());			//가게 고유번호(PK)
			pstmt.setString(2, storeWorkDTO.getStoreWorkWeek());	//가게 영업 요일
			System.out.println("log_StoreWorkDTO_delete_pstmt input complete");

			//[5] rs 변수 선언 : DELETE 쿼리문 실행
			int rs = pstmt.executeUpdate(); 
			System.out.println("log_StoreWorkDTO_delete_excuteUpdate() complete");

			//[6] 예외처리 : 정상실행 되지 않았을 경우, false
			if(rs<=0) {//rs = 1(success) / rs = 0 (fail)
				System.err.println("log_StoreWorkDTO_delete_excuteUpdate() fail : if(rs <= 0)");
				return false; 
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("log_StoreWorkDTO_delete_Exception fail : Exception e");
			return false;
		//[7] JDBC 연결 해제 진행
		}finally {
			if (!JDBCUtil.disconnect(conn, pstmt)) { // 만약 JDBC가 연결되어 있다면
				System.err.println("log_StoreWorkDTO_delete_disconnect fail"); // 연결해제 실패
				return false;
			} // JDBC 연결 해제 되었다면
		}System.out.println("log_StoreWorkDTO_delete_true!");
		return true;
	}
}

