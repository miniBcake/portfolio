package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.ProductCateDTO;

public class ProductCateDAO {
	private final String INSERT = "INSERT INTO BB_PRODUCT_CATEGORY(PRODUCT_CATEGORY_NUM, PRODUCT_CATEGORY_NAME) VALUES((SELECT NVL(MAX(PRODUCT_CATEGORY_NUM)+1,1) FROM BB_PRODUCT_CATEGORY), ?)";
	private final String UPDATE = "UPDATE BB_PRODUCT_CATEGORY SET PRODUCT_CATEGORY_NAME = ? WHERE PRODUCT_CATEGORY_NUM = ?";
	private final String DELETE = "DELETE FROM BB_PRODUCT_CATEGORY WHERE PRODUCT_CATEGORY_NUM = ?";
	private final String SELECTALL = "SELECT PRODUCT_CATEGORY_NUM, PRODUCT_CATEGORY_NAME FROM BB_PRODUCT_CATEGORY ORDER BY PRODUCT_CATEGORY_NUM";
	
	public boolean insert(ProductCateDTO productCateDTO) {
		System.out.println("log: ProductCategory insert start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, productCateDTO.getProductCateName()); //카테고리명
			//넘어온 값 확인 로그
			System.out.println("log: parameter getProductCateName : "+productCateDTO.getProductCateName());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: ProductCategory insert execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: ProductCategory insert SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: ProductCategory insert Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: ProductCategory insert disconnect fail");
				return false;
			}
			System.out.println("log: ProductCategory insert end");
		}
		System.out.println("log: ProductCategory insert true");
		return true;
	}
	
	public boolean update(ProductCateDTO productCateDTO) {
		System.out.println("log: ProductCategory update start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1, productCateDTO.getProductCateName()); //카테고리명
			pstmt.setInt(2, productCateDTO.getProductCateNum()); //카테고리명
			//넘어온 값 확인 로그
			System.out.println("log: parameter getProductCateName : "+productCateDTO.getProductCateName());
			System.out.println("log: parameter getProductCateNum : "+productCateDTO.getProductCateNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: ProductCategory update execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: ProductCategory update SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: ProductCategory update Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: ProductCategory update disconnect fail");
				return false;
			}
			System.out.println("log: ProductCategory update end");
		}
		System.out.println("log: ProductCategory update true");
		return true;
	}
	
	public boolean delete(ProductCateDTO productCateDTO) {
		System.out.println("log: ProductCategory delete start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, productCateDTO.getProductCateNum()); //카테고리 PK
			//넘어온 값 확인 로그
			System.out.println("log: parameter getProductCateNum : "+productCateDTO.getProductCateNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: ProductCategory delete execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: ProductCategory delete SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: ProductCategory delete Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: ProductCategory delete disconnect fail");
				return false;
			}
			System.out.println("log: ProductCategory delete end");
		}
		System.out.println("log: ProductCategory delete true");
		return true;
	}
	
	public ArrayList<ProductCateDTO> selectAll(ProductCateDTO productCateDTO) {
		System.out.println("log: ProductCategory selectAll start");
		ArrayList<ProductCateDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(SELECTALL);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { 
				ProductCateDTO data = new ProductCateDTO();
				data.setProductCateNum(rs.getInt("PRODUCT_CATEGORY_NUM")); 		//카테고리 번호
				data.setProductCateName(rs.getString("PRODUCT_CATEGORY_NAME"));	//카테고리 명
				//반환된 객체 리스트에 추가
				datas.add(data); 
				System.out.print("| result "+data.getProductCateNum());
			}
			rs.close();
			System.out.println("end");
		} catch (SQLException e) {
			System.err.println("log: ProductCategory selectAll SQLException fail");
			datas.clear();//잔여데이터 삭제
		} catch (Exception e) {
			System.err.println("log: ProductCategory selectAll Exception fail");
			datas.clear();//잔여데이터 삭제
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: ProductCategory selectAll disconnect fail");
				datas.clear();//잔여데이터 삭제
			}
			System.out.println("log: ProductCategory selectAll end");
		}
		System.out.println("log: ProductCategory selectAll return datas");
		return datas;
	}
	
	private ProductCateDTO selectOne(ProductCateDTO productCateDTO) {
		ProductCateDTO data = null;
		return data;
	}
}
