package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.ImageDTO;

public class ImageDAO {
	private final String INSERT = "INSERT INTO BB_IMAGE(IMAGE_NUM, IMAGE_WAY, BOARD_NUM) VALUES((SELECT NVL(MAX(IMAGE_NUM)+1,1) FROM BB_IMAGE), ?, ?)";
	private final String DELETE = "DELETE FROM BB_IMAGE WHERE IMAGE_NUM = ?";
	private final String SELECTALL = "SELECT IMAGE_NUM, IMAGE_WAY FROM BB_IMAGE WHERE BOARD_NUM = ? ORDER BY IMAGE_NUM";
	
	public boolean insert(ImageDTO imageDTO) {
		System.out.println("log: Image insert start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, imageDTO.getImageWay()); //이미지 경로
			pstmt.setInt(2, imageDTO.getBoardNum()); 	//게시물 pk(Fk)
			//넘어온 값 확인 로그
			System.out.println("log: parameter getImageWay : "+imageDTO.getImageWay());
			System.out.println("log: parameter getBoardNum : "+imageDTO.getBoardNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: Image insert execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Image insert SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Image insert Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Image insert disconnect fail");
				return false;
			}
			System.out.println("log: Image insert end");
		}
		System.out.println("log: Image insert true");
		return true;
	}
	
	private boolean update(ImageDTO imageDTO) {
		return false;
	}
	
	public boolean delete(ImageDTO imageDTO) {
		System.out.println("log: Image delete start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, imageDTO.getImageNum()); //이미지 pk
			//넘어온 값 확인 로그
			System.out.println("log: parameter getImageNum : "+imageDTO.getImageNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: Image delete execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Image delete SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Image delete Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Image delete disconnect fail");
				return false;
			}
			System.out.println("log: Image delete end");
		}
		System.out.println("log: Image delete true");
		return true;
	}
	
	public ArrayList<ImageDTO> selectAll(ImageDTO imageDTO) {
		System.out.println("log: Image selectAll start");
		ArrayList<ImageDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(SELECTALL);
			pstmt.setInt(1, imageDTO.getBoardNum()); //게시물 pk(Fk)
			//넘어온 값 확인 로그
			System.out.println("log: parameter getBoardNum : "+imageDTO.getBoardNum());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { 
				ImageDTO data = new ImageDTO();
				data.setImageNum(rs.getInt("IMAGE_NUM")); 		//이미지 pk
				data.setImageWay(rs.getString("IMAGE_WAY"));	//이미지 경로
				//반환된 객체 리스트에 추가
				datas.add(data); 
				System.out.print(" | result "+data.getImageNum());
			}
			rs.close();
			System.out.println("end");
		} catch (SQLException e) {
			System.err.println("log: Image selectAll SQLException fail");
			datas.clear();//잔여데이터 삭제
		} catch (Exception e) {
			System.err.println("log: Image selectAll Exception fail");
			datas.clear();//잔여데이터 삭제
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Image selectAll disconnect fail");
				datas.clear();//잔여데이터 삭제
			}
			System.out.println("log: Image selectAll end");
		}
		System.out.println("log: Image selectAll return datas");
		return datas;
	}
	
	private ImageDTO selectOne(ImageDTO imageDTO) {
		ImageDTO data = null;
		return data;
	}
}
