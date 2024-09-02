package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.CategoryDTO;

public class CategoryDAO {
	private final String INSERT = "INSERT INTO BD_CATEGORY(CATEGORY_NUM, CATEGORY_NAME) VALUES((SELECT NVL(MAX(CATEGORY_NUM)+1,1) FROM BD_CATEGORY), ?)";
	private final String UPDATE = "UPDATE BD_CATEGORY SET CATEGORY_NAME = ? WHERE CATEGORY_NUM = ?";
	private final String DELETE = "DELETE FROM BD_CATEGORY WHERE CATEGORY_NUM = ?";
	
	private final String SELECTONE = "SELECT CATEGORY_NUM FROM BD_CATEGORY WHERE CATEGORY_NAME = ?";
	private final String SELECTALL = "SELECT CATEGORY_NUM, CATEGORY_NAME FROM BD_CATEGORY";	
	
	public boolean insert(CategoryDTO categoryDTO) {
		System.out.println("log: Category insert start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, categoryDTO.getCategoryName()); //카테고리명
			System.out.println("log: parameter getCategoryName : "+categoryDTO.getCategoryName());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: parameter Category insert execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Category insert SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Category insert Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Category insert disconnect fail");
				return false;
			}
			System.out.println("log: Category insert end");
		}
		System.out.println("log: Category insert true");
		return true;
	}
	public boolean update(CategoryDTO categoryDTO) {
		System.out.println("log: Category update start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1, categoryDTO.getCategoryName()); //카테고리 명
			System.out.println("log: parameter getCategoryName : "+categoryDTO.getCategoryName());
			pstmt.setInt(2, categoryDTO.getCategoryNum()); //카테고리 pk
			System.out.println("log: parameter getCategoryNum : "+categoryDTO.getCategoryNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: Category update execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Category update SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Category update Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Category update disconnect fail");
				return false;
			}
			System.out.println("log: Category update end");
		}
		System.out.println("log: Category update true");
		return true;
	}
	public boolean delete(CategoryDTO categoryDTO) {
		System.out.println("log: Category delete start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, categoryDTO.getCategoryNum()); //카테고리 pk
			System.out.println("log: parameter getCategoryNum : "+categoryDTO.getCategoryNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: Category delete execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Category delete SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Category delete Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Category delete disconnect fail");
				return false;
			}
			System.out.println("log: Category delete end");
		}
		System.out.println("log: Category delete true");
		return true;
	}
	public ArrayList<CategoryDTO> selectAll(CategoryDTO categoryDTO) {
		System.out.println("log: Category selectAll start");
		ArrayList<CategoryDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(SELECTALL);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { 
				CategoryDTO data = new CategoryDTO();
				data.setCategoryNum(rs.getInt("CATEGORY_NUM"));
				data.setCategoryName(rs.getString("CATEGORY_NAME"));
				//반환된 객체 리스트에 추가
				datas.add(data); 
				System.out.print("result "+data.getCategoryNum()+" | ");
			}
			rs.close();
			System.out.println("end");
		} catch (SQLException e) {
			System.err.println("log: Category selectAll SQLException fail");
			datas.clear();//잔여데이터 삭제
		} catch (Exception e) {
			System.err.println("log: Category selectAll Exception fail");
			datas.clear();//잔여데이터 삭제
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Category selectAll disconnect fail");
				datas.clear();//잔여데이터 삭제
			}
			System.out.println("log: Category selectAll end");
		}
		System.out.println("log: Category selectAll return datas");
		return datas;
	}
	public CategoryDTO selectOne(CategoryDTO categoryDTO) {
		System.out.println("log: Category selectOne start");
		CategoryDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(SELECTONE);
			pstmt.setString(1, categoryDTO.getCategoryName());
			System.out.println("log: parameter getCategoryName : "+categoryDTO.getCategoryName());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) { 
				data = new CategoryDTO();
				data.setCategoryNum(rs.getInt("CATEGORY_NUM"));
				System.out.println("result exists");
			}
			rs.close();
			System.out.println("end");
		} catch (SQLException e) {
			System.err.println("log: Category selectOne SQLException fail");
			return null; //실패
		} catch (Exception e) {
			System.err.println("log: Category selectOne Exception fail");
			return null; //실패
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Category selectOne disconnect fail");
				return null; //실패
			}
			System.out.println("log: Category selectOne end");
		}
		System.out.println("log: Category selectOne success");
		return data;
	}
}
