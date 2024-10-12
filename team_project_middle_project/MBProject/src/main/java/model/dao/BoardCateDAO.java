package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.BoardCateDTO;

public class BoardCateDAO {
	private final String INSERT = "INSERT INTO BB_BOARD_CATEGORY(CATEGORY_NUM, CATEGORY_NAME) VALUES((SELECT NVL(MAX(CATEGORY_NUM)+1,1) FROM BB_BOARD_CATEGORY), ?)";
	private final String UPDATE = "UPDATE BB_BOARD_CATEGORY SET CATEGORY_NAME = ? WHERE CATEGORY_NUM = ?";
	private final String DELETE = "DELETE FROM BB_BOARD_CATEGORY WHERE CATEGORY_NUM = ?";
	private final String SELECTALL = "SELECT CATEGORY_NUM, CATEGORY_NAME FROM BB_BOARD_CATEGORY ORDER BY CATEGORY_NUM";
	
	public boolean insert(BoardCateDTO boardCateDTO) {
		System.out.println("log: BoardCategory insert start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, boardCateDTO.getBoardCateName()); //카테고리명
			//넘어온 값 확인 로그
			System.out.println("log: parameter getBoardCateName : "+boardCateDTO.getBoardCateName());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: BoardCategory insert execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: BoardCategory insert SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: BoardCategory insert Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: BoardCategory insert disconnect fail");
				return false;
			}
			System.out.println("log: BoardCategory insert end");
		}
		System.out.println("log: BoardCategory insert true");
		return true;
	}
	
	public boolean update(BoardCateDTO boardCateDTO) {
		System.out.println("log: BoardCategory update start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1, boardCateDTO.getBoardCateName()); //카테고리명
			pstmt.setInt(2, boardCateDTO.getBoardCateNum()); //카테고리명
			//넘어온 값 확인 로그
			System.out.println("log: parameter getBoardCateName : "+boardCateDTO.getBoardCateName());
			System.out.println("log: parameter getBoardCateNum : "+boardCateDTO.getBoardCateNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: BoardCategory update execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: BoardCategory update SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: BoardCategory update Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: BoardCategory update disconnect fail");
				return false;
			}
			System.out.println("log: BoardCategory update end");
		}
		System.out.println("log: BoardCategory update true");
		return true;
	}
	
	public boolean delete(BoardCateDTO boardCateDTO) {
		System.out.println("log: BoardCategory delete start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, boardCateDTO.getBoardCateNum()); //카테고리 PK
			//넘어온 값 확인 로그
			System.out.println("log: parameter getBoardCateNum : "+boardCateDTO.getBoardCateNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: BoardCategory delete execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: BoardCategory delete SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: BoardCategory delete Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: BoardCategory delete disconnect fail");
				return false;
			}
			System.out.println("log: BoardCategory delete end");
		}
		System.out.println("log: BoardCategory delete true");
		return true;
	}
	
	public ArrayList<BoardCateDTO> selectAll(BoardCateDTO boardCateDTO) {
		System.out.println("log: BoardCategory selectAll start");
		ArrayList<BoardCateDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(SELECTALL);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { 
				BoardCateDTO data = new BoardCateDTO();
				data.setBoardCateNum(rs.getInt("CATEGORY_NUM")); 		//카테고리 번호
				data.setBoardCateName(rs.getString("CATEGORY_NAME"));	//카테고리 명
				//반환된 객체 리스트에 추가
				datas.add(data); 
				System.out.print("| result "+data.getBoardCateNum());
			}
			rs.close();
			System.out.println("end");
		} catch (SQLException e) {
			System.err.println("log: BoardCategory selectAll SQLException fail");
			datas.clear();//잔여데이터 삭제
		} catch (Exception e) {
			System.err.println("log: BoardCategory selectAll Exception fail");
			datas.clear();//잔여데이터 삭제
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: BoardCategory selectAll disconnect fail");
				datas.clear();//잔여데이터 삭제
			}
			System.out.println("log: BoardCategory selectAll end");
		}
		System.out.println("log: BoardCategory selectAll return datas");
		return datas;
	}
	
	private BoardCateDTO selectOne(BoardCateDTO boardCateDTO) {
		BoardCateDTO data = null;
		return data;
	}
}
