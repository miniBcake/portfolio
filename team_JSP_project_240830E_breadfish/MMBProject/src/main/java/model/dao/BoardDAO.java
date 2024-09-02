package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.BoardDTO;

public class BoardDAO {
	//쿼리
	//join없이 진행
	private final String INSERT = "INSERT INTO BD_BOARD(BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, MEMBER_NUM) "
								+ "VALUES((SELECT NVL(MAX(BOARD_NUM)+1,1) FROM BD_BOARD), ?, ?, ?, ?, ?)";
	private final String UPDATE = "UPDATE BD_BOARD SET BOARD_TITLE = ?, BOARD_CONTENT = ?, BOARD_VISIBILITY = ? WHERE BOARD_NUM = ?";
	private final String DELETE = "DELETE FROM BD_BOARD WHERE BOARD_NUM = ?";
	
	//join해서 진행
	//*BD_BOARD_JOIN_MEMCATELIKE => board, member, category, like 조인한 view
	private final String SELECTONE = "SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_COUNT, BOARD_WRITE_DAY "
									+ "FROM BD_BOARD_JOIN_MEMCATELIKE WHERE BOARD_NUM = ?";
	
	private final String SELECTONE_CNT = "SELECT COUNT(*) AS B_CNT FROM BD_BOARD bb JOIN BD_CATEGORY bc ON bb.BOARD_CATEGORY = bc.CATEGORY_NUM WHERE CATEGORY_NAME = ? ";
	
	private final String SELECTONE_TITLE_CNT = "SELECT COUNT(*) AS B_CNT FROM BD_BOARD bb JOIN BD_CATEGORY bc ON bb.BOARD_CATEGORY = bc.CATEGORY_NUM WHERE CATEGORY_NAME = ?  AND BOARD_TITLE LIKE '%'|| ? ||'%'";
	
	private final String SELECTONE_CONTENT_CNT = "SELECT COUNT(*) AS B_CNT FROM BD_BOARD bb JOIN BD_CATEGORY bc ON bb.BOARD_CATEGORY = bc.CATEGORY_NUM WHERE CATEGORY_NAME = ?  AND BOARD_CONTENT LIKE '%'|| ? ||'%'";
	
	private final String SELECTONE_NICKNAME_CNT = "SELECT COUNT(*) AS B_CNT FROM BD_BOARD_JOIN_MEMCATELIKE WHERE CATEGORY_NAME = ?  AND MEMBER_NICKNAME LIKE '%'|| ? ||'%' ";
	
	private final String SELECTONE_MAX_PK = "SELECT MAX(BOARD_NUM) AS B_MAX_PK FROM BD_BOARD";
	
	private final String SELECTALL = "SELECT RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_COUNT, BOARD_WRITE_DAY "
								+ "FROM (SELECT ROW_NUMBER() OVER(ORDER BY BOARD_WRITE_DAY DESC) AS RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_COUNT, BOARD_WRITE_DAY "
								+ "FROM BD_BOARD_JOIN_MEMCATELIKE  WHERE CATEGORY_NAME = ? ORDER BY RN) "
								+ "WHERE RN BETWEEN ? AND ?";
	
	private final String SELECTALL_HOT = "SELECT ROWNUM, b.BOARD_NUM, b.BOARD_TITLE, b.BOARD_CONTENT, b.BOARD_VISIBILITY, b.BOARD_CATEGORY, b.CATEGORY_NAME, b.MEMBER_NUM, b.MEMBER_NICKNAME, b.LIKE_COUNT, b.BOARD_WRITE_DAY FROM ( "
										+ "SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_COUNT, BOARD_WRITE_DAY "
										+ "FROM BD_BOARD_JOIN_MEMCATELIKE WHERE CATEGORY_NAME = ? AND LIKE_COUNT > ? ORDER BY LIKE_COUNT DESC ) b WHERE ROWNUM <= ? ORDER BY BOARD_WRITE_DAY DESC ";
	
	private final String SELECTALL_TITLE = "SELECT RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_COUNT, BOARD_WRITE_DAY "
									+ "FROM (SELECT ROW_NUMBER() OVER(ORDER BY BOARD_WRITE_DAY DESC) AS RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_COUNT, BOARD_WRITE_DAY "
									+ "FROM BD_BOARD_JOIN_MEMCATELIKE WHERE CATEGORY_NAME = ? AND BOARD_TITLE LIKE '%'|| ? ||'%' ORDER BY RN ) "
									+ "WHERE RN BETWEEN ? AND ? ";
	
	private final String SELECTALL_NICKNAME = "SELECT RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_COUNT, BOARD_WRITE_DAY "
										+ "FROM (SELECT ROW_NUMBER() OVER(ORDER BY BOARD_WRITE_DAY DESC) AS RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_COUNT, BOARD_WRITE_DAY "
										+ "FROM BD_BOARD_JOIN_MEMCATELIKE WHERE CATEGORY_NAME = ? AND MEMBER_NICKNAME LIKE '%'|| ? ||'%' ORDER BY RN ) "
										+ "WHERE RN BETWEEN ? AND ? ";
	
	private final String SELECTALL_CONTENT = "SELECT RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_COUNT, BOARD_WRITE_DAY "
										+ "FROM (SELECT ROW_NUMBER() OVER(ORDER BY BOARD_WRITE_DAY DESC) AS RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_COUNT, BOARD_WRITE_DAY "
										+ "FROM BD_BOARD_JOIN_MEMCATELIKE WHERE CATEGORY_NAME = ? AND BOARD_CONTENT LIKE '%'|| ? ||'%' ORDER BY RN ) "
										+ "WHERE RN BETWEEN ? AND ? ";
	
	//고정 설정
	private final int MINLIKE = 5;		//인기글 최소 기준
	private final int SHOWHOTBOARD = 3;	//보여줄 인기글 개수
	
	public boolean insert(BoardDTO boardDTO) {
		System.out.println("log: Board insert start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, boardDTO.getTitle()); 		//제목
			pstmt.setString(2, boardDTO.getContent()); 		//내용
			pstmt.setString(3, boardDTO.getVisibility()); 	//공개 비공개
			pstmt.setInt(4, boardDTO.getCategoryNum()); 	//카테고리 pk(fk)
			pstmt.setInt(5, boardDTO.getMemberNum()); 			//멤버 pk(fk)
			//넘어온 값 확인 로그
			System.out.println("log: parameter getTitle : "+boardDTO.getTitle());
			System.out.println("log: parameter getContent : "+boardDTO.getContent());
			System.out.println("log: parameter getVisibility : "+boardDTO.getVisibility());
			System.out.println("log: parameter getCategoryNum : "+boardDTO.getCategoryNum());
			System.out.println("log: parameter getMemberNum : "+boardDTO.getMemberNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: Board insert execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Board insert SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Board insert Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Board insert disconnect fail");
				return false;
			}
			System.out.println("log: Board insert end");
		}
		System.out.println("log: Board insert true");
		return true;
	}
	public boolean update(BoardDTO boardDTO) {
		System.out.println("log: Board update start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1, boardDTO.getTitle()); 		//제목
			pstmt.setString(2, boardDTO.getContent()); 		//내용
			pstmt.setString(3, boardDTO.getVisibility()); 	//공개 비공개
			pstmt.setInt(4, boardDTO.getBoardNum()); 		//게시물 pk
			//넘어온 값 확인 로그
			System.out.println("log: parameter getTitle : "+boardDTO.getTitle());
			System.out.println("log: parameter getContent : "+boardDTO.getContent());
			System.out.println("log: parameter getVisibility : "+boardDTO.getVisibility());
			System.out.println("log: parameter getBoardNum : "+boardDTO.getBoardNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: Board update execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Board update SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Board update Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Board update disconnect fail");
				return false;
			}
			System.out.println("log: Board update end");
		}
		System.out.println("log: Board update true");
		return true;
	}
	public boolean delete(BoardDTO boardDTO) {
		System.out.println("log: Board delete start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, boardDTO.getBoardNum()); //게시글 pk
			//넘어온 값 확인 로그
			System.out.println("log: parameter getBoardNum : "+boardDTO.getBoardNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: Board delete execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Board delete SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Board delete Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Board delete disconnect fail");
				return false;
			}
			System.out.println("log: Board delete end");
		}
		System.out.println("log: Board delete true");
		return true;
	}
	public ArrayList<BoardDTO> selectAll(BoardDTO boardDTO) {
		System.out.println("log: Board selectAll start");
		ArrayList<BoardDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			if(boardDTO.getCondition().equals("ALL")) {
				//- 게시판 별 목록
				System.out.println("log: Board selectAll condition : SELECTALL");
				pstmt = conn.prepareStatement(SELECTALL);
				pstmt.setString(1, boardDTO.getCategoryName()); 	//카테고리 명
				pstmt.setInt(2, boardDTO.getStartNum());			//페이지네이션 시작번호
				pstmt.setInt(3, boardDTO.getEndNum());				//페이지네이션 끝번호	
				//넘어온 값 확인 로그
				System.out.println("log: parameter getCategoryName : "+boardDTO.getCategoryName());
				System.out.println("log: parameter getStartNum : "+boardDTO.getStartNum());
				System.out.println("log: parameter getEndNum : "+boardDTO.getEndNum());
			}
			else if(boardDTO.getCondition().equals("HOT_SELECTALL")) {
				//- 게시판 별 인기글 3개
				System.out.println("log: Board selectAll condition : SELECTALL_HOT");
				pstmt = conn.prepareStatement(SELECTALL_HOT);
				pstmt.setString(1, boardDTO.getCategoryName()); 	//카테고리 명
				pstmt.setInt(2, MINLIKE);							//인기글 최소 기준
				pstmt.setInt(3, SHOWHOTBOARD); 						//보여줄 인기글 개수
				//넘어온 값 확인 로그
				System.out.println("log: parameter getCategoryName : "+boardDTO.getCategoryName());
			}
			else if(boardDTO.getCondition().equals("TITLE_SELECTALL")) {
				//- 검색 기능 (제목)
				System.out.println("log: Board selectAll condition : SELECTALL_TITLE");
				pstmt = conn.prepareStatement(SELECTALL_TITLE);
				pstmt.setString(1, boardDTO.getCategoryName()); 	//카테고리 명
				pstmt.setString(2, boardDTO.getKeyword());			//검색어
				pstmt.setInt(3, boardDTO.getStartNum());			//페이지네이션 시작번호
				pstmt.setInt(4, boardDTO.getEndNum());				//페이지네이션 끝번호	
				//넘어온 값 확인 로그
				System.out.println("log: parameter getCategoryName : "+boardDTO.getCategoryName());
				System.out.println("log: parameter getKeyword : "+boardDTO.getKeyword());
				System.out.println("log: parameter getStartNum : "+boardDTO.getStartNum());
				System.out.println("log: parameter getEndNum : "+boardDTO.getEndNum());
			}
			else if(boardDTO.getCondition().equals("NICKNAMEV")) {
				//- 검색 기능 (작성자)
				System.out.println("log: Board selectAll condition : SELECTALL_NICKNAME");
				pstmt = conn.prepareStatement(SELECTALL_NICKNAME);
				pstmt.setString(1, boardDTO.getCategoryName()); 	//카테고리 명
				pstmt.setString(2, boardDTO.getKeyword());			//검색어
				pstmt.setInt(3, boardDTO.getStartNum());			//페이지네이션 시작번호
				pstmt.setInt(4, boardDTO.getEndNum());				//페이지네이션 끝번호	
				//넘어온 값 확인 로그
				System.out.println("log: parameter getCategoryName : "+boardDTO.getCategoryName());
				System.out.println("log: parameter getKeyword : "+boardDTO.getKeyword());
				System.out.println("log: parameter getStartNum : "+boardDTO.getStartNum());
				System.out.println("log: parameter getEndNum : "+boardDTO.getEndNum());
			}
			else if(boardDTO.getCondition().equals("CONTENT_SELECTALL")) {
				//- 검색 기능 (내용)
				System.out.println("log: Board selectAll condition : SELECTALL_CONTENT");
				pstmt = conn.prepareStatement(SELECTALL_CONTENT);
				pstmt.setString(1, boardDTO.getCategoryName()); 	//카테고리 명
				pstmt.setString(2, boardDTO.getKeyword());			//검색어
				pstmt.setInt(3, boardDTO.getStartNum());			//페이지네이션 시작번호
				pstmt.setInt(4, boardDTO.getEndNum());				//페이지네이션 끝번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getCategoryName : "+boardDTO.getCategoryName());
				System.out.println("log: parameter getKeyword : "+boardDTO.getKeyword());
				System.out.println("log: parameter getStartNum : "+boardDTO.getStartNum());
				System.out.println("log: parameter getEndNum : "+boardDTO.getEndNum());
			}
			else {
				System.err.println("log: Board selectAll condition fail");
			}
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { 
				BoardDTO data = new BoardDTO();
				data.setBoardNum(rs.getInt("BOARD_NUM"));						//게시글 pk
				data.setTitle(rs.getString("BOARD_TITLE"));						//제목
				data.setContent(rs.getString("BOARD_CONTENT"));					//내용
				data.setVisibility(rs.getString("BOARD_VISIBILITY"));			//공개 비공개
				data.setCategoryNum(rs.getInt("BOARD_CATEGORY"));				//카테고리 pk(fk)
				data.setCategoryName(rs.getString("CATEGORY_NAME"));			//카테고리 명
				data.setMemberNum(rs.getInt("MEMBER_NUM"));	 					//작성자 pk(fk)
				data.setMemberNickname(rs.getString("MEMBER_NICKNAME"));		//작성자 닉네임
				data.setLikeCnt(rs.getInt("LIKE_COUNT")); 						//좋아요 수 
				data.setWriteDay(rs.getString("BOARD_WRITE_DAY")); 				//작성일자
				//반환된 객체 리스트에 추가
				datas.add(data); 
				System.out.print("result "+data.getCategoryNum()+" | ");
			}
			rs.close();
			System.out.println("end");
		} catch (SQLException e) {
			System.err.println("log: Board selectAll SQLException fail");
			datas.clear();//잔여데이터 삭제
		} catch (Exception e) {
			System.err.println("log: Board selectAll Exception fail");
			datas.clear();//잔여데이터 삭제
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Board selectAll disconnect fail");
				datas.clear();//잔여데이터 삭제
			}
			System.out.println("log: Board selectAll end");
		}
		System.out.println("log: Board selectAll return datas");
		return datas;
	}
	public BoardDTO selectOne(BoardDTO boardDTO) {
		BoardDTO data = null;
		System.out.println("log: Board selectOne start");
		ArrayList<BoardDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			if(boardDTO.getCondition().equals("ONE")) {
				//- 게시글 상세 조회
				System.out.println("log: Board selectOne condition : SELECTONE");
				pstmt = conn.prepareStatement(SELECTONE);
				pstmt.setInt(1, boardDTO.getBoardNum()); 			//게시글 번호
				System.out.println("log: parameter getBoardNum : "+boardDTO.getBoardNum());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new BoardDTO();
					data.setBoardNum(rs.getInt("BOARD_NUM"));				//게시글 pk
					data.setTitle(rs.getString("BOARD_TITLE"));				//제목
					data.setContent(rs.getString("BOARD_CONTENT"));			//내용
					data.setVisibility(rs.getString("BOARD_VISIBILITY"));	//공개 비공개
					data.setCategoryNum(rs.getInt("BOARD_CATEGORY"));		//카테고리 pk(fk)
					data.setCategoryName(rs.getString("CATEGORY_NAME"));	//카테고리 명
					data.setMemberNum(rs.getInt("MEMBER_NUM"));	 			//작성자 pk(fk)
					data.setMemberNickname(rs.getString("MEMBER_NICKNAME"));//작성자 닉네임
					data.setLikeCnt(rs.getInt("LIKE_COUNT")); 				//좋아요 수 
					data.setWriteDay(rs.getString("BOARD_WRITE_DAY")); 		//작성일자
					System.out.println("result true");
				}
				rs.close();
			}
			else if(boardDTO.getCondition().equals("CNT")) {
				//- 게시판 별 글 개수
				System.out.println("log: Board selectOne condition : SELECTONE_CNT");
				pstmt = conn.prepareStatement(SELECTONE_CNT);
				pstmt.setString(1, boardDTO.getCategoryName()); 			//카테고리 명
				System.out.println("log: parameter getCategoryName : "+boardDTO.getCategoryName());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new BoardDTO();
					data.setBoardCnt(rs.getInt("B_CNT"));			//게시판 별 게시글 총 개수
					System.out.println("result true");
				}
				rs.close();
			}
			else if(boardDTO.getCondition().equals("CNTCONTENTS")) {
				//- 게시판 별 내용 검색 글 개수
				System.out.println("log: Board selectOne condition : SELECTONE_CONTENT_CNT");
				pstmt = conn.prepareStatement(SELECTONE_CONTENT_CNT);
				pstmt.setString(1, boardDTO.getCategoryName()); 			//카테고리 명
				System.out.println("log: parameter getCategoryName : "+boardDTO.getCategoryName());
				pstmt.setString(2, boardDTO.getKeyword()); 					//검색어
				System.out.println("log: parameter getKeyword : "+boardDTO.getKeyword());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new BoardDTO();
					data.setBoardCnt(rs.getInt("B_CNT"));			//게시판 별 게시글 총 개수
					System.out.println("result true");
				}
				rs.close();
			}
			else if(boardDTO.getCondition().equals("CNTWRITER")) {
				//- 게시판 별 닉네임 검색 글 개수
				System.out.println("log: Board selectOne condition : SELECTONE_NICKNAME_CNT");
				pstmt = conn.prepareStatement(SELECTONE_NICKNAME_CNT);
				pstmt.setString(1, boardDTO.getCategoryName()); 			//카테고리 명
				System.out.println("log: parameter getCategoryName : "+boardDTO.getCategoryName());
				pstmt.setString(2, boardDTO.getKeyword()); 					//검색어
				System.out.println("log: parameter getKeyword : "+boardDTO.getKeyword());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new BoardDTO();
					data.setBoardCnt(rs.getInt("B_CNT"));			//게시판 별 게시글 총 개수
					System.out.println("result exists");
				}
				rs.close();
			}
			else if(boardDTO.getCondition().equals("CNTTITLE")) {
				//- 게시판 별 제목 검색 글 개수
				System.out.println("log: Board selectOne condition : SELECTONE_TITLE_CNT");
				pstmt = conn.prepareStatement(SELECTONE_TITLE_CNT);
				pstmt.setString(1, boardDTO.getCategoryName()); 			//카테고리 명
				System.out.println("log: parameter getCategoryName : "+boardDTO.getCategoryName());
				pstmt.setString(2, boardDTO.getKeyword()); 					//검색어
				System.out.println("log: parameter getKeyword : "+boardDTO.getKeyword());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new BoardDTO();
					data.setBoardCnt(rs.getInt("B_CNT"));			//게시판 별 게시글 총 개수
					System.out.println("result exists");
				}
				rs.close();
			}
			else if(boardDTO.getCondition().equals("MAXPK")) {
				//- 가장 마지막으로 사용된 PK넘버
				System.out.println("log: Board selectOne condition : SELECTONE_MAX_PK");
				pstmt = conn.prepareStatement(SELECTONE_MAX_PK);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new BoardDTO();
					data.setBoardCnt(rs.getInt("B_MAX_PK"));		//가장 마지막으로 사용된 PK넘버
					System.out.println("result exists");
				}
				rs.close();
			}
			else {
				System.err.println("log: Board selectOne condition fail");
			}
			System.out.println("end");
		} catch (SQLException e) {
			System.err.println("log: Board selectOne SQLException fail");
			datas.clear();//잔여데이터 삭제
		} catch (Exception e) {
			System.err.println("log: Board selectOne Exception fail");
			datas.clear();//잔여데이터 삭제
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Board selectOne disconnect fail");
				datas.clear();//잔여데이터 삭제
			}
			System.out.println("log: Board selectOne end");
		}
		System.out.println("log: Board selectOne return datas");
		return data;
	}
}
