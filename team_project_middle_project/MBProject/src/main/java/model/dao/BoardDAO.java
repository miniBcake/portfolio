package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import model.common.JDBCUtil;
import model.dto.BoardDTO;

public class BoardDAO {
	private final String INSERT = "INSERT INTO BB_BOARD(BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_OPEN, CATEGORY_NUM, MEMBER_NUM) VALUES ((SELECT NVL(MAX(BOARD_NUM)+1,1) FROM BB_BOARD), ?, ?, ?, ?, ?)";
	private final String INSERT_PRODUCT = "INSERT INTO BB_BOARD(BOARD_NUM, BOARD_TITLE, BOARD_CONTENT) VALUES ((SELECT NVL(MAX(BOARD_NUM)+1,1) FROM BB_BOARD), ?, ?)";
	private final String INSERT_STORE = "INSERT INTO BB_BOARD(BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, STORE_NUM) VALUES ((SELECT NVL(MAX(BOARD_NUM)+1,1) FROM BB_BOARD), ?, ?, ?)";
	private final String UPDATE = "UPDATE BB_BOARD SET BOARD_TITLE = ?, BOARD_CONTENT = ?, BOARD_OPEN = ? WHERE BOARD_NUM = ?";
	private final String UPDATE_DEL = "UPDATE BB_BOARD SET BOARD_DELETE = 'Y' WHERE BOARD_NUM = ?";
	private final String DELETE = "DELETE FROM BB_BOARD WHERE BOARD_NUM = ?";
	
	private final String SELECTALL = "SELECT RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_OPEN, BOARD_DELETE, CATEGORY_NUM, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_CNT, BOARD_WRITE_DAY "
								+ " FROM (SELECT ROW_NUMBER() OVER(ORDER BY BOARD_WRITE_DAY DESC) AS RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_OPEN, BOARD_DELETE, CATEGORY_NUM, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_CNT, BOARD_WRITE_DAY "
								+ "	FROM BB_VIEW_BOARD_JOIN WHERE CATEGORY_NAME = ?";
	private final String SELECTALL_ENDPART = "	ORDER BY RN) WHERE RN BETWEEN ? AND ?";
	
	private final String SELECTALL_HOT = "SELECT ROWNUM, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_OPEN, BOARD_DELETE, CATEGORY_NUM, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_CNT, BOARD_WRITE_DAY "
								+ " FROM (SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_OPEN, BOARD_DELETE, CATEGORY_NUM, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_CNT, BOARD_WRITE_DAY FROM BB_VIEW_BOARD_JOIN "
								+ "	WHERE CATEGORY_NAME = ? AND LIKE_CNT > ? ORDER BY LIKE_CNT DESC) WHERE ROWNUM <= ?";
	private final String SELECTALL_MYPAGE = "SELECT RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_OPEN, BOARD_DELETE, CATEGORY_NUM, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_CNT, BOARD_WRITE_DAY "
								+ " FROM (SELECT ROW_NUMBER() OVER(ORDER BY BOARD_WRITE_DAY DESC) AS RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_OPEN, BOARD_DELETE, CATEGORY_NUM, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_CNT, BOARD_WRITE_DAY "
								+ "	FROM BB_VIEW_BOARD_JOIN WHERE MEMBER_NUM = ? ORDER BY RN) WHERE RN BETWEEN ? AND ?";
	private final String SELECTALL_LIKE = "SELECT RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_OPEN, BOARD_DELETE, CATEGORY_NUM, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_CNT, BOARD_WRITE_DAY "
								+ " FROM (SELECT ROW_NUMBER() OVER(ORDER BY BOARD_WRITE_DAY DESC) AS RN, BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_OPEN, BOARD_DELETE, CATEGORY_NUM, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_CNT, BOARD_WRITE_DAY "
								+ "	FROM BB_VIEW_BOARD_JOIN WHERE BOARD_NUM IN((SELECT BOARD_NUM FROM BB_LIKE WHERE MEMBER_NUM = ?)) ORDER BY RN) WHERE RN BETWEEN ? AND ?";
	
	private final String SELECTONE = "SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_OPEN, BOARD_DELETE, CATEGORY_NUM, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_CNT, BOARD_WRITE_DAY FROM BB_VIEW_BOARD_JOIN WHERE BOARD_NUM = ?";
	private final String SELECTONE_SEARCH = "SELECT COUNT(*) AS CNT FROM BB_BOARD bb JOIN BB_MEMBER bm ON bb.MEMBER_NUM = bm.MEMBER_NUM WHERE CATEGORY_NUM = (SELECT CATEGORY_NUM FROM BB_BOARD_CATEGORY WHERE CATEGORY_NAME = ?) ";
	private final String SELECTONE_MAXPK = "SELECT NVL(MAX(BOARD_NUM), 0) AS MAXPK FROM BB_BOARD";
	
	//쿼리파츠	
	private String SELECT_PART_PERIOD = "AND BOARD_WRITE_DAY >= SYSDATE - ?";
	private String SELECT_PART_TITLE = "AND BOARD_TITLE LIKE '%'||?||'%'";
	private String SELECT_PART_NICKNAME = "AND MEMBER_NICKNAME LIKE '%'||?||'%'";
	private String SELECT_PART_CONTENT = "AND BOARD_CONTENT LIKE '%'||?||'%'";
	
	//고정 설정
	private final int MINLIKE = 5;		//인기글 최소 기준
	private final int SHOWHOTBOARD = 3;	//보여줄 인기글 개수
	
	public boolean insert(BoardDTO boardDTO) {
		System.out.println("log: Board insert start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			if(boardDTO.getCondition().equals("BOARD_INSERT")) {
				//새 게시글 작성
				System.out.println("log: Board insert : BOARD_INSERT");
				pstmt = conn.prepareStatement(INSERT);
				pstmt.setString(1, boardDTO.getBoardTitle()); 	//제목
				pstmt.setString(2, boardDTO.getBoardContent()); //내용
				pstmt.setString(3, boardDTO.getBoardOpen()); 	//비공개 공개 여부
				pstmt.setInt(4, boardDTO.getBoardCateNum()); 	//카테고리 번호
				pstmt.setInt(5, boardDTO.getMemberNum()); 		//멤버번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getBoardTitle : "+boardDTO.getBoardTitle());
				System.out.println("log: parameter getBoardContent : "+boardDTO.getBoardContent());
				System.out.println("log: parameter getBoardOpen : "+boardDTO.getBoardOpen());
				System.out.println("log: parameter getBoardCateNum : "+boardDTO.getBoardCateNum());
				System.out.println("log: parameter getMemberNum : "+boardDTO.getMemberNum());
			}
			else if(boardDTO.getCondition().equals("PRODUCT_INSERT")) {
				//상품설명용 게시글 작성
				System.out.println("log: Board insert : PRODUCT_INSERT");
				pstmt = conn.prepareStatement(INSERT_PRODUCT);
				pstmt.setString(1, boardDTO.getBoardTitle()); 	//제목
				pstmt.setString(2, boardDTO.getBoardContent()); //내용
				//넘어온 값 확인 로그
				System.out.println("log: parameter getBoardTitle : "+boardDTO.getBoardTitle());
				System.out.println("log: parameter getBoardContent : "+boardDTO.getBoardContent());
			}
			else if(boardDTO.getCondition().equals("MARKET_INSERT")) {
				//가게설명용 게시글 작성
				System.out.println("log: Board insert : MARKET_INSERT");
				pstmt = conn.prepareStatement(INSERT_STORE);
				pstmt.setString(1, boardDTO.getBoardTitle()); 	//제목
				pstmt.setString(2, boardDTO.getBoardContent()); //내용
				pstmt.setInt(3, boardDTO.getStoreNum()); //가게 번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getBoardTitle : "+boardDTO.getBoardTitle());
				System.out.println("log: parameter getBoardContent : "+boardDTO.getBoardContent());
				System.out.println("log: parameter getStoreNum : "+boardDTO.getStoreNum());
			}
			else {
				//컨디션값 오류
				System.err.println("log: Board insert condition fail");
			}
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
			if(boardDTO.getCondition().equals("BOARD_UPDATE")) {
				//게시글 수정
				System.out.println("log: Board update : BOARD_UPDATE");
				pstmt = conn.prepareStatement(UPDATE);
				pstmt.setString(1, boardDTO.getBoardTitle()); 	//제목
				pstmt.setString(2, boardDTO.getBoardContent()); //내용
				pstmt.setString(3, boardDTO.getBoardOpen()); 	//공개여부
				pstmt.setInt(4, boardDTO.getBoardNum()); 		//게시글 번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getBoardTitle : "+boardDTO.getBoardTitle());
				System.out.println("log: parameter getBoardContent : "+boardDTO.getBoardContent());
				System.out.println("log: parameter getBoardOpen : "+boardDTO.getBoardOpen());
				System.out.println("log: parameter getBoardNum : "+boardDTO.getBoardNum());
			}
			else if(boardDTO.getCondition().equals("ADMIN_DELETE")) {
				//관리자 삭제
				System.out.println("log: Board update : ADMIN_DELETE");
				pstmt = conn.prepareStatement(UPDATE_DEL);
				pstmt.setInt(1, boardDTO.getBoardNum()); 		//게시글 번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getBoardNum : "+boardDTO.getBoardNum());
			}
			else {
				//컨디션값 오류
				System.err.println("log: Board update condition fail");
			}
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
			pstmt.setInt(1, boardDTO.getBoardNum()); //게시글 번호
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
			if(boardDTO.getCondition().equals("FILTER_BOARD")) {
				//게시물 리스트 (+필터검색)
				System.out.println("log: Board selectAll : FILTER_BOARD");
				HashMap<String, String> filters = boardDTO.getFilterList();//넘어온 MAP filter키워드
				//메서드를 통해 쿼리문을 완성한 후 toString을 통해 다시 String으로 변환
				pstmt = conn.prepareStatement(filterSearch(SELECTALL,filters).append(" "+SELECTALL_ENDPART).toString());
				int placeholderNum = 1; //필터검색 선택한 것만 검색어를 넣기 위한 카운트
				pstmt.setString(placeholderNum++, boardDTO.getBoardCateName()); 	//카테고리 명
				placeholderNum = filterKeywordSetter(pstmt,filters,placeholderNum); 		//필터 검색 검색어 
				if(placeholderNum < 0) {
					//만약 filterKeywordSetter 메서드에서 오류가 발생했다면 SQL예외처리
					throw new SQLException();
				}
				pstmt.setInt(placeholderNum++, boardDTO.getStartNum());			//페이지네이션 용 시작번호
				pstmt.setInt(placeholderNum++, boardDTO.getEndNum());				//페이지네이션 용 끝번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getBoardCateName : "+boardDTO.getBoardCateName());
				System.out.println("log: parameter getStartNum : "+boardDTO.getStartNum());
				System.out.println("log: parameter getEndNum : "+boardDTO.getEndNum());
			}
			else if(boardDTO.getCondition().equals("HOT_BOARD")) {
				//게시판 별 인기글
				System.out.println("log: Board selectAll : HOT_BOARD");
				pstmt = conn.prepareStatement(SELECTALL_HOT);
				pstmt.setString(1, boardDTO.getBoardCateName()); 	//카테고리 명
				pstmt.setInt(2, MINLIKE); 							//최소 좋아요 
				pstmt.setInt(3, SHOWHOTBOARD); 						//반환할 게시글 개수
				//넘어온 값 확인 로그
				System.out.println("log: parameter getBoardCateName : "+boardDTO.getBoardCateName());
			}
			else if(boardDTO.getCondition().equals("MY_BOARD")) {
				//내 게시글 모아보기
				System.out.println("log: Board selectAll : MY_BOARD");
				pstmt = conn.prepareStatement(SELECTALL_MYPAGE);
				pstmt.setInt(1, boardDTO.getMemberNum()); 	//멤버 명
				pstmt.setInt(2, boardDTO.getStartNum());	//페이지네이션 용 시작번호
				pstmt.setInt(3, boardDTO.getEndNum());		//페이지네이션 용 끝번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberNum : "+boardDTO.getMemberNum());
				System.out.println("log: parameter getStartNum : "+boardDTO.getStartNum());
				System.out.println("log: parameter getEndNum : "+boardDTO.getEndNum());
			}
			else if(boardDTO.getCondition().equals("MYFAVORITE_BOARD")) {
				//내가 좋아요한 게시글 모아보기
				System.out.println("log: Board selectAll : MYFAVORITE_BOARD");
				pstmt = conn.prepareStatement(SELECTALL_LIKE);
				pstmt.setInt(1, boardDTO.getMemberNum()); 	//멤버 명
				pstmt.setInt(2, boardDTO.getStartNum());	//페이지네이션 용 시작번호
				pstmt.setInt(3, boardDTO.getEndNum());		//페이지네이션 용 끝번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberNum : "+boardDTO.getMemberNum());
				System.out.println("log: parameter getStartNum : "+boardDTO.getStartNum());
				System.out.println("log: parameter getEndNum : "+boardDTO.getEndNum());
			}
			else {
				//컨디션값 오류
				System.err.println("log: Board selectAll condition fail");
			}
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { 
				BoardDTO data = new BoardDTO();
				data.setBoardNum(rs.getInt("BOARD_NUM")); 				//게시글 번호
				data.setBoardTitle(rs.getString("BOARD_TITLE")); 		//제목
				data.setBoardContent(rs.getString("BOARD_CONTENT")); 	//내용
				data.setBoardOpen(rs.getString("BOARD_OPEN")); 			//공개여부
				data.setBoardCateNum(rs.getInt("CATEGORY_NUM")); 		//카테고리 번호
				data.setBoardCateName(rs.getString("CATEGORY_NAME")); 	//카테고리 명
				data.setMemberNum(rs.getInt("MEMBER_NUM")); 			//멤버 PK
				data.setMemberNickname(rs.getString("MEMBER_NICKNAME")); //멤버 닉네임
				data.setLikeCnt(rs.getInt("LIKE_CNT")); 				//좋아요 수
				data.setBoardWriteDay(rs.getString("BOARD_WRITE_DAY")); //작성일자
				data.setBoardDelete(rs.getString("BOARD_DELETE")); 		//관리자 글 삭제여부				
				//반환된 객체 리스트에 추가
				datas.add(data); 
				System.out.print(" | result "+data.getBoardNum());
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
		System.out.println("log: Board selectOne start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		BoardDTO data = null;
		try {
			if(boardDTO.getCondition().equals("ONE_BOARD")) {
				//게시물 상세보기
				System.out.println("log: Board selectOne : ONE_BOARD");
				pstmt = conn.prepareStatement(SELECTONE);
				pstmt.setInt(1, boardDTO.getBoardNum()); 	//게시물 pk(Fk)
				//넘어온 값 확인 로그
				System.out.println("log: parameter getBoardNum : "+boardDTO.getBoardNum());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new BoardDTO();
					data.setBoardNum(rs.getInt("BOARD_NUM")); 				//게시글 번호
					data.setBoardTitle(rs.getString("BOARD_TITLE")); 		//제목
					data.setBoardContent(rs.getString("BOARD_CONTENT")); 	//내용
					data.setBoardOpen(rs.getString("BOARD_OPEN")); 			//공개여부
					data.setBoardCateNum(rs.getInt("CATEGORY_NUM")); 		//카테고리 번호
					data.setBoardCateName(rs.getString("CATEGORY_NAME")); 	//카테고리 명
					data.setMemberNum(rs.getInt("MEMBER_NUM")); 			//멤버 PK
					data.setMemberNickname(rs.getString("MEMBER_NICKNAME")); //멤버 닉네임
					data.setLikeCnt(rs.getInt("LIKE_CNT")); 				//좋아요 수
					data.setBoardWriteDay(rs.getString("BOARD_WRITE_DAY")); //작성일자
					data.setBoardDelete(rs.getString("BOARD_DELETE")); 		//관리자 글 삭제여부
					System.out.println("result exists");
				}
				rs.close();
			}
			else if(boardDTO.getCondition().equals("CNT_BOARD")) {
				//게시판 별 글 개수(+필터검색)
				System.out.println("log: Board selectOne : CNT_BOARD");
				//필터검색 추가
				HashMap<String, String> filters = boardDTO.getFilterList();//넘어온 MAP filter키워드
				pstmt = conn.prepareStatement(filterSearch(SELECTONE_SEARCH,filters).toString());
				int placeholderNum = 1; //필터검색 선택한 것만 검색어를 넣기 위한 카운트
				pstmt.setString(placeholderNum++, boardDTO.getBoardCateName()); 	//카테고리 명
				//넘어온 값 확인 로그
				System.out.println("log: parameter getBoardCateName : "+boardDTO.getBoardCateName());
				placeholderNum = filterKeywordSetter(pstmt,filters,placeholderNum); 		//필터 검색 검색어 
				if(placeholderNum < 0) {
					//만약 filterKeywordSetter 메서드에서 오류가 발생했다면 SQL예외처리
					throw new SQLException();
				}
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new BoardDTO();
					data.setCnt(rs.getInt("CNT")); //게시글 개수
					System.out.println("result exists");
				}
				rs.close();
			}
			else if(boardDTO.getCondition().equals("MAXPK_BOARD")) {
				//가장 최근 PK
				System.out.println("log: Board selectOne : MAXPK_BOARD");
				pstmt = conn.prepareStatement(SELECTONE_MAXPK);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new BoardDTO();
					data.setMaxPk(rs.getInt("MAXPK")); //게시글pk
					System.out.println("result exists");
				}
				rs.close();
			}
			else {
				//컨디션값 오류
				System.err.println("log: Board selectOne condition fail");
			}
			System.out.println("end");
		} catch (SQLException e) {
			System.err.println("log: Board selectOne SQLException failasdfaf");
			return null;
		} catch (Exception e) {
			System.err.println("log: Board selectOne Exception fail");
			return null;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Board selectOne disconnect fail");
				return null;
			}
			System.out.println("log: Board selectAll end");
		}
		System.out.println("log: Board selectAll return datas");
		return data;
	}
	
	
	//필터 검색 메서드
	private StringBuilder filterSearch(String startQuery, HashMap<String, String> filters) {
		System.out.println("log: filterSearch start");
		//필터검색 추가
		//java에서 String 객체는 변경 불가로, String 연결을 할 시 새로운 String 객체를 생성해 비효율적이다.
		//이를 해결하는게 StringBuilder로 StringBuilder는 변경가능한 문자열을 만들어 연결 시 String을 변경해 수정한다.
		//String query = SELECTALL; == StringBuilder query = StringBuilder(SELECTALL);
		
		StringBuilder query = new StringBuilder(startQuery); //StringBuilder 객체 생성
		
		if(filters != null && !filters.isEmpty()) {
			//만약 필터 검색을 한다면 (== C에게서 넘어온 filter 키워드가 있다면)
			System.out.println("log: filterSearch search");
			for(String key : filters.keySet()) {
				if(key.equals("WRITEDAY_FILTER")) {
					//기간 검색추가
					System.out.println("log: filterSearch WRITEDAY_FILTER");
					query.append(" "+SELECT_PART_PERIOD);
				}
				else if(key.equals("TITLE_SELECTALL")) {
					//제목 검색추가
					System.out.println("log: filterSearch TITLE_SELECTALL");
					query.append(" "+SELECT_PART_TITLE);
				}
				else if(key.equals("NICKNAMEV")) {
					//작성자 검색추가
					System.out.println("log: filterSearch NICKNAMEV");
					query.append(" "+SELECT_PART_NICKNAME);
				}
				else if(key.equals("CONTENT_SELECTALL")) {
					//내용 검색추가
					System.out.println("log: filterSearch CONTENT_SELECTALL");
					query.append(" "+SELECT_PART_CONTENT);
				}
				else {
					System.err.println("log: check FilterList key");
				}
			}
		}//필터검색여부 확인 if문 종료
		System.out.println("log: filterSearch end");
		return query;
	}
	
	//필터검색 값 채우는 메서드
		private int filterKeywordSetter(PreparedStatement pstmt, HashMap<String, String> filters, int placeholderNum) {
			System.out.println("log: filterKeyword start");
			try {
				if(filters != null && !filters.isEmpty()) {
					//만약 필터 검색을 한다면 (== C에게서 넘어온 filter 키워드가 있다면)
					for(Entry<String, String> keywoard : filters.entrySet()) {
						if(keywoard.getKey().equals("WRITEDAY_FILTER")) {
							//기간 검색어
							pstmt.setInt(placeholderNum++, Integer.parseInt(keywoard.getValue()));
							//넘어온 값 확인 로그
							System.out.println("log: parameter Period Search : "+keywoard.getValue());
						}
						else if(keywoard.getKey().equals("TITLE_SELECTALL")) {
							//제목 검색어
							pstmt.setString(placeholderNum++, keywoard.getValue());
							//넘어온 값 확인 로그
							System.out.println("log: parameter Title Search : "+keywoard.getValue());
						}
						else if(keywoard.getKey().equals("NICKNAMEV")) {
							//작성자 검색어
							pstmt.setString(placeholderNum++, keywoard.getValue());
							//넘어온 값 확인 로그
							System.out.println("log: parameter Nickname Search : "+keywoard.getValue());
						}
						else if(keywoard.getKey().equals("CONTENT_SELECTALL")) {
							//내용 검색어 
							pstmt.setString(placeholderNum++, keywoard.getValue());
							//넘어온 값 확인 로그
							System.out.println("log: parameter Content Search : "+keywoard.getValue());
						}
					}
				}//필터검색여부 확인 if문 종료
			} catch (NumberFormatException e) {
				System.err.println("log: filterKeyword "+e.getMessage());
				return -1;
			} catch (SQLException e) {
				System.err.println("log: filterKeyword "+e.getMessage());
				return -1;
			}
			System.out.println("log: filterKeyword end");
			return placeholderNum;
		}
}
