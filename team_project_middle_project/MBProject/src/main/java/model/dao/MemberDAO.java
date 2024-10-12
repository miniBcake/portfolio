package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import model.common.JDBCUtil;
import model.dto.MemberDTO;

//FIXME 컨디션 필터값 아직 반영안됨

public class MemberDAO {
	private final String INSERT = "INSERT INTO BB_MEMBER(MEMBER_NUM, MEMBER_EMAIL, MEMBER_PASSWORD, MEMBER_NAME, MEMBER_PHONE, MEMBER_NICKNAME, MEMBER_PROFILE_WAY, MEMBER_ROLE) "
								+ "VALUES((SELECT NVL(MAX(MEMBER_NUM)+1,1) FROM BB_MEMBER), ?, ?, ?, ?, ?, ?, ?)";
	private final String UPDATE = "UPDATE BB_MEMBER SET MEMBER_EMAIL = ?, MEMBER_NAME = ?, MEMBER_PHONE = ?, MEMBER_NICKNAME = ?, MEMBER_PROFILE_WAY = ? WHERE MEMBER_NUM = ?";
	private final String UPDATE_PASSWORD = "UPDATE BB_MEMBER SET MEMBER_PASSWORD = ? WHERE MEMBER_NUM = ?";
	private final String DELETE = "DELETE FROM BB_MEMBER WHERE MEMBER_NUM = ?";
	
	private final String SELECTALL = "SELECT RN, MEMBER_NUM, MEMBER_EMAIL, MEMBER_NAME, MEMBER_PHONE, MEMBER_NICKNAME, MEMBER_PROFILE_WAY, MEMBER_ROLE, MEMBER_HIREDAY "
								+ " FROM (SELECT ROW_NUMBER() OVER(ORDER BY MEMBER_HIREDAY DESC) AS RN, MEMBER_NUM, MEMBER_EMAIL, MEMBER_NAME, MEMBER_PHONE, MEMBER_NICKNAME, MEMBER_PROFILE_WAY, MEMBER_ROLE, MEMBER_HIREDAY "
								+ "	FROM BB_MEMBER WHERE 1=1 ";
	private final String SELECTALL_ENDPART = " ORDER BY RN) WHERE RN BETWEEN ? AND ?";
	private final String SELECTALL_RECENT = "SELECT RN, MEMBER_NUM, MEMBER_EMAIL, MEMBER_NAME, MEMBER_PHONE, MEMBER_NICKNAME, MEMBER_PROFILE_WAY, MEMBER_ROLE, MEMBER_HIREDAY "
									+ " FROM (SELECT ROW_NUMBER() OVER(ORDER BY MEMBER_HIREDAY DESC) AS RN, MEMBER_NUM, MEMBER_EMAIL, MEMBER_NAME, MEMBER_PHONE, MEMBER_NICKNAME, MEMBER_PROFILE_WAY, MEMBER_ROLE, MEMBER_HIREDAY "
									+ "	FROM BB_MEMBER WHERE MEMBER_HIREDAY >= SYSDATE - ? ORDER BY RN) WHERE RN BETWEEN ? AND ?";
	
	private final String SELECTONE_EMAIL = "SELECT MEMBER_EMAIL FROM BB_MEMBER WHERE MEMBER_EMAIL = ?";
	private final String SELECTONE_NICKNAME = "SELECT MEMBER_NICKNAME FROM BB_MEMBER WHERE MEMBER_NICKNAME = ?";
	private final String SELECTONE_PASSWORD_RESET = "SELECT MEMBER_NUM FROM BB_MEMBER WHERE MEMBER_EMAIL = ? AND MEMBER_NAME = ?";
	private final String SELECTONE_LOGIN = "SELECT MEMBER_NUM, MEMBER_EMAIL, MEMBER_NICKNAME, MEMBER_ROLE FROM BB_MEMBER WHERE MEMBER_EMAIL = ? AND MEMBER_PASSWORD = ?";
	private final String SELECTONE_INFO = "SELECT MEMBER_NUM, MEMBER_EMAIL, MEMBER_NAME, MEMBER_PHONE, MEMBER_NICKNAME, MEMBER_PROFILE_WAY, MEMBER_ROLE, MEMBER_HIREDAY FROM BB_MEMBER WHERE MEMBER_NUM = ?";
	private final String SELECTONE_PASSWORD_CHECK = "SELECT MEMBER_NUM FROM BB_MEMBER WHERE MEMBER_NUM = ? AND MEMBER_PASSWORD = ?";
	private final String SELECTONE_PROFILE = "SELECT MEMBER_PROFILE_WAY FROM BB_MEMBER WHERE MEMBER_NUM = ?";
	private final String SELECTONE_CNT = "SELECT COUNT(*) AS CNT FROM BB_MEMBER WHERE 1=1 ";
	private final String SELECTONE_RECENT = "SELECT COUNT(*) AS CNT FROM BB_MEMBER WHERE MEMBER_HIREDAY >= SYSDATE - ?";
	
	//쿼리파츠
	private final String SELECT_PART_NICKNAME = "AND MEMBER_NICKNAME LIKE '%'||?||'%'";
	private final String SELECT_PART_NAME = "AND MEMBER_NAME LIKE '%'||?||'%'";
	private final String SELECT_PART_EMAIL = "AND MEMBER_EMAIL LIKE '%'||?||'%'";
	private final String SELECT_PART_ROLE = "AND MEMBER_ROLE = ?";
	private final String SELECT_PART_PHONE = "AND MEMBER_PHONE LIKE '%'||?||'%'";
	
	//고정설정
	private final int RECENT_PIVOT = 7; //최근가입한 회원 기준 (day)
	
	//컨디션
	//selectOne
	private final String EMAIL_CONDITION = "EMAIL_SELECTONE";
	private final String NICKNAME_CONDITION = "NICKNAME_SELECTONE";
	private final String PASSWORD_RESET_CONDITION = "EMAIL_NAME_SELECTONE";
	private final String LOGIN_CONDITON = "LOGIN_SELECTONE";
	private final String INFO_CONDITION = "MEMBER_INFO_SELECTONE";
	private final String PASSWORD_CHECK_CONDITION = "PASSWORD_CHECK_SELECTONE";
	private final String PROFILE_WAY_CONDITION = "PROFILEWAY_SELECTONE";
	private final String CNT_CONDITION = "CNT_SELECTONE";
	private final String RECENT_CONDITION = "RECENT_SELECTONE";
	//selectAll
	private final String ALL_LIST_CONDITON = "SELECTALL";
	private final String RECENT_LIST_CONDITON = "RECENT_SELECTALL";
	//update
	private final String UPDATE_CONDITION = "UPDATE";
	private final String UPDATE_PASSWORD_CONDTION = "PASSWORD_UPDATE";
	//필터리스트(필터검색용)
	private final String FILTER_NICKNAME_CONDITION ="SEARCH_NICKNAME";
	private final String FILTER_NAME_CONDITION = "SEARCH_NAME";
	private final String FILTER_EMAIL_CONDITION = "SEARCH_EMAIL";
	private final String FILTER_ROLE_CONDITION = "SEARCH_USERROLE";
	private final String FILTER_PHONE_CONDITION = "SEARCH_PHONENUM";
	
	public boolean insert(MemberDTO memberDTO) {
		System.out.println("log: Member insert start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, memberDTO.getMemberEmail());		//이메일
			pstmt.setString(2, memberDTO.getMemberPassword()); 	//비밀번호
			pstmt.setString(3, memberDTO.getMemberName()); 		//이름
			pstmt.setString(4, memberDTO.getMemberPhone()); 	//전화번호
			pstmt.setString(5, memberDTO.getMemberNickname()); 	//닉네임
			pstmt.setString(6, memberDTO.getMemberProfileWay()); //프로필사진경로
			pstmt.setString(7, memberDTO.getMemberRole()); 		//권한
			//넘어온 값 확인 로그
			System.out.println("log: parameter getMemberEmail : "+memberDTO.getMemberEmail());
			System.out.println("log: parameter getMemberPassword : "+memberDTO.getMemberPassword());
			System.out.println("log: parameter getMemberName : "+memberDTO.getMemberName());
			System.out.println("log: parameter getMemberPhone : "+memberDTO.getMemberPhone());
			System.out.println("log: parameter getMemberNickname : "+memberDTO.getMemberNickname());
			System.out.println("log: parameter getMemberProfileWay : "+memberDTO.getMemberProfileWay());
			System.out.println("log: parameter getMemberRole : "+memberDTO.getMemberRole());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: Member insert execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Member insert SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Member insert Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Member insert disconnect fail");
				return false;
			}
			System.out.println("log: Member insert end");
		}
		System.out.println("log: Member insert true");
		return true;
	}
	
	public boolean update(MemberDTO memberDTO) {
		System.out.println("log: Member update start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			if(memberDTO.getCondition().equals(UPDATE_CONDITION)) {
				//개인정보수정(비밀번호 제외)
				System.out.println("log: Member update : UPDATE");
				pstmt = conn.prepareStatement(UPDATE);
				pstmt.setString(1, memberDTO.getMemberEmail());		//이메일
				pstmt.setString(2, memberDTO.getMemberName()); 		//이름
				pstmt.setString(3, memberDTO.getMemberPhone()); 	//전화번호
				pstmt.setString(4, memberDTO.getMemberNickname()); 	//닉네임
				pstmt.setString(5, memberDTO.getMemberProfileWay()); //프로필사진경로
				pstmt.setInt(6, memberDTO.getMemberNum()); 			//멤버 번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberEmail : "+memberDTO.getMemberEmail());
				System.out.println("log: parameter getMemberName : "+memberDTO.getMemberName());
				System.out.println("log: parameter getMemberPhone : "+memberDTO.getMemberPhone());
				System.out.println("log: parameter getMemberNickname : "+memberDTO.getMemberNickname());
				System.out.println("log: parameter getMemberProfileWay : "+memberDTO.getMemberProfileWay());
				System.out.println("log: parameter getMemberNum : "+memberDTO.getMemberNum());
			}
			else if(memberDTO.getCondition().equals(UPDATE_PASSWORD_CONDTION)) {
				//개인정보수정 비밀번호
				System.out.println("log: Member update : UPDATE_PASSWORD");
				pstmt = conn.prepareStatement(UPDATE_PASSWORD);
				pstmt.setString(1, memberDTO.getMemberPassword());	//비밀번호
				pstmt.setInt(2, memberDTO.getMemberNum()); 			//멤버 번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberPassword : "+memberDTO.getMemberPassword());
				System.out.println("log: parameter getMemberNum : "+memberDTO.getMemberNum());
			}
			else {
				//컨디션값 오류
				System.err.println("log: Member update condition fail");
			}
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: Member update execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Member update SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Member update Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Member update disconnect fail");
				return false;
			}
			System.out.println("log: Member update end");
		}
		System.out.println("log: Member update true");
		return true;
	}
	
	public boolean delete(MemberDTO memberDTO) {
		System.out.println("log: Member delete start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, memberDTO.getMemberNum()); //멤버 번호
			//넘어온 값 확인 로그
			System.out.println("log: parameter getMemberNum : "+memberDTO.getMemberNum());
			if(pstmt.executeUpdate() <= 0) { 
				//쿼리는 정상적으로 실행됐으나 실패
				System.err.println("log: Member delete execute fail");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("log: Member delete SQLException fail");
			return false;
		} catch (Exception e) {
			System.err.println("log: Member delete Exception fail");
			return false;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Member delete disconnect fail");
				return false;
			}
			System.out.println("log: Member delete end");
		}
		System.out.println("log: Member delete true");
		return true;
	}
	
	public ArrayList<MemberDTO> selectAll(MemberDTO memberDTO) {
		System.out.println("log: Member selectAll start");
		ArrayList<MemberDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			if(memberDTO.getCondition().equals(ALL_LIST_CONDITON)) {
				//전체회원(+필터검색)
				System.out.println("log: Member selectAll : SELECTALL");
				//필터검색 추가
				HashMap<String, String> filters = memberDTO.getFilterList();//넘어온 MAP filter키워드
				pstmt = conn.prepareStatement(filterSearch(SELECTALL,filters).append(" "+SELECTALL_ENDPART).toString());
				int placeholderNum = 1; //필터검색 선택한 것만 검색어를 넣기 위한 카운트
				placeholderNum = filterKeywordSetter(pstmt,filters,placeholderNum); 		//필터 검색 검색어 
				if(placeholderNum < 0) {
					//만약 filterKeywordSetter 메서드에서 오류가 발생했다면 SQL예외처리
					throw new SQLException();
				}
				
				pstmt.setInt(placeholderNum++, memberDTO.getStartNum());	//페이지네이션 용 시작번호
				pstmt.setInt(placeholderNum++, memberDTO.getEndNum());		//페이지네이션 용 끝번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getStartNum : "+memberDTO.getStartNum());
				System.out.println("log: parameter getEndNum : "+memberDTO.getEndNum());
			}
			else if(memberDTO.getCondition().equals(RECENT_LIST_CONDITON)) {
				//신규회원
				System.out.println("log: Member selectAll : SELECTALL_RECENT");
				pstmt = conn.prepareStatement(SELECTALL_RECENT);
				pstmt.setInt(1, RECENT_PIVOT); 				//신규회원기준
				pstmt.setInt(2, memberDTO.getStartNum());	//페이지네이션 용 시작번호
				pstmt.setInt(3, memberDTO.getEndNum());		//페이지네이션 용 끝번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getStartNum : "+memberDTO.getStartNum());
				System.out.println("log: parameter getEndNum : "+memberDTO.getEndNum());
			}
			else {
				//컨디션값 오류
				System.err.println("log: Member selectAll condition fail");
			}
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { 
				MemberDTO data = new MemberDTO();
				data.setMemberNum(rs.getInt("MEMBER_NUM")); 					//멤버 번호
				data.setMemberName(rs.getString("MEMBER_NAME")); 				//이름
				data.setMemberEmail(rs.getString("MEMBER_EMAIL")); 				//이메일
				data.setMemberNickname(rs.getString("MEMBER_NICKNAME")); 		//닉네임
				data.setMemberPhone(rs.getString("MEMBER_PHONE")); 				//전화번호
				data.setMemberProfileWay(rs.getString("MEMBER_PROFILE_WAY")); 	//프로필 사진 경로
				data.setMemberRole(rs.getString("MEMBER_ROLE")); 				//권한
				data.setMemberHireDay(rs.getString("MEMBER_HIREDAY")); 			//가입일자
				//반환된 객체 리스트에 추가
				datas.add(data); 
				System.out.print(" | result "+data.getMemberNum());
			}
			rs.close();
			System.out.println("end");
		} catch (SQLException e) {
			System.err.println("log: Member selectAll SQLException fail");
			datas.clear();//잔여데이터 삭제
		} catch (Exception e) {
			System.err.println("log: Member selectAll Exception fail");
			datas.clear();//잔여데이터 삭제
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Member selectAll disconnect fail");
				datas.clear();//잔여데이터 삭제
			}
			System.out.println("log: Member selectAll end");
		}
		System.out.println("log: Member selectAll return datas");
		return datas;
	}
	
	public MemberDTO selectOne(MemberDTO memberDTO) {
		System.out.println("log: Member selectOne start");
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		MemberDTO data = null;
		try {
			if(memberDTO.getCondition().equals(EMAIL_CONDITION)) {
				//이메일 중복조회
				System.out.println("log: Member selectOne : SELECTONE_EMAIL");
				pstmt = conn.prepareStatement(SELECTONE_EMAIL);
				pstmt.setString(1, memberDTO.getMemberEmail()); 	//이메일
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberEmail : "+memberDTO.getMemberEmail());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new MemberDTO();
					data.setMemberEmail(rs.getString("MEMBER_EMAIL")); //이메일
					System.out.println("result exists");
				}
				rs.close();
			}
			else if(memberDTO.getCondition().equals(NICKNAME_CONDITION)) {
				//닉네임 중복조회
				System.out.println("log: Member selectOne : SELECTONE_NICKNAME");
				pstmt = conn.prepareStatement(SELECTONE_NICKNAME);
				pstmt.setString(1, memberDTO.getMemberNickname()); 	//닉네임
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberNickname : "+memberDTO.getMemberNickname());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new MemberDTO();
					data.setMemberNickname(rs.getString("MEMBER_NICKNAME")); //닉네임
					System.out.println("result exists");
				}
				rs.close();
			}
			else if(memberDTO.getCondition().equals(PASSWORD_RESET_CONDITION)) {
				//비밀번호 리셋 
				System.out.println("log: Member selectOne : SELECTONE_PASSWORD_RESET");
				pstmt = conn.prepareStatement(SELECTONE_PASSWORD_RESET);
				pstmt.setString(1, memberDTO.getMemberEmail()); //이메일
				pstmt.setString(2, memberDTO.getMemberName()); 	//이름
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberEmail : "+memberDTO.getMemberEmail());
				System.out.println("log: parameter getMemberName : "+memberDTO.getMemberName());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new MemberDTO();
					data.setMemberNum(rs.getInt("MEMBER_NUM")); //멤버번호
					System.out.println("result exists");
				}
				rs.close();
			}
			else if(memberDTO.getCondition().equals(LOGIN_CONDITON)) {
				//로그인
				System.out.println("log: Member selectOne : SELECTONE_LOGIN");
				pstmt = conn.prepareStatement(SELECTONE_LOGIN);
				pstmt.setString(1, memberDTO.getMemberEmail()); //이메일
				pstmt.setString(2, memberDTO.getMemberPassword()); 	//패스워드
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberEmail : "+memberDTO.getMemberEmail());
				System.out.println("log: parameter getMemberPassword : "+memberDTO.getMemberPassword());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new MemberDTO();
					data.setMemberNum(rs.getInt("MEMBER_NUM")); 			//멤버번호
					data.setMemberEmail(rs.getString("MEMBER_EMAIL"));		//이메일
					data.setMemberNickname(rs.getString("MEMBER_NICKNAME")); //닉네임
					data.setMemberRole(rs.getString("MEMBER_ROLE")); 		//권한
					System.out.println("result exists");
				}
				rs.close();
			}
			else if(memberDTO.getCondition().equals(INFO_CONDITION)) {
				//회원정보
				System.out.println("log: Member selectOne : SELECTONE_INFO");
				pstmt = conn.prepareStatement(SELECTONE_INFO);
				pstmt.setInt(1, memberDTO.getMemberNum()); //멤버 번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberNum : "+memberDTO.getMemberNum());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new MemberDTO();
					data.setMemberNum(rs.getInt("MEMBER_NUM")); 					//멤버 번호
					data.setMemberName(rs.getString("MEMBER_NAME")); 				//이름
					data.setMemberEmail(rs.getString("MEMBER_EMAIL")); 				//이메일
					data.setMemberNickname(rs.getString("MEMBER_NICKNAME")); 		//닉네임
					data.setMemberPhone(rs.getString("MEMBER_PHONE")); 				//전화번호
					data.setMemberProfileWay(rs.getString("MEMBER_PROFILE_WAY")); 	//프로필 사진 경로
					data.setMemberRole(rs.getString("MEMBER_ROLE")); 				//권한
					data.setMemberHireDay(rs.getString("MEMBER_HIREDAY")); 			//가입일자
					System.out.println("result exists");
				}
				rs.close();
			}
			else if(memberDTO.getCondition().equals(PASSWORD_CHECK_CONDITION)) {
				//패스워드 확인
				System.out.println("log: Member selectOne : SELECTONE_PASSWORD_CHECK");
				pstmt = conn.prepareStatement(SELECTONE_PASSWORD_CHECK);
				pstmt.setInt(1, memberDTO.getMemberNum()); 			//멤버 번호
				pstmt.setString(2, memberDTO.getMemberPassword()); 	//비밀번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberNum : "+memberDTO.getMemberNum());
				System.out.println("log: parameter getMemberPassword : "+memberDTO.getMemberPassword());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new MemberDTO();
					data.setMemberNum(rs.getInt("MEMBER_NUM")); //멤버번호
					System.out.println("result exists");
				}
				rs.close();
			}
			else if(memberDTO.getCondition().equals(PROFILE_WAY_CONDITION)) {
				//프로필이미지경로
				System.out.println("log: Member selectOne : SELECTONE_PROFILE");
				pstmt = conn.prepareStatement(SELECTONE_PROFILE);
				pstmt.setInt(1, memberDTO.getMemberNum()); //멤버 번호
				//넘어온 값 확인 로그
				System.out.println("log: parameter getMemberNum : "+memberDTO.getMemberNum());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new MemberDTO();
					data.setMemberProfileWay(rs.getString("MEMBER_PROFILE_WAY")); 	//프로필 사진 경로
					System.out.println("result exists");
				}
				rs.close();
			}
			else if(memberDTO.getCondition().equals(CNT_CONDITION)) {
				//전체회원 수 (+필터검색)
				System.out.println("log: Member selectOne : SELECTONE_CNT");
				//필터검색 추가
				HashMap<String, String> filters = memberDTO.getFilterList();//넘어온 MAP filter키워드
				pstmt = conn.prepareStatement(filterSearch(SELECTONE_CNT,filters).toString());
				int placeholderNum = 1; //필터검색 선택한 것만 검색어를 넣기 위한 카운트
				placeholderNum = filterKeywordSetter(pstmt,filters,placeholderNum); 		//필터 검색 검색어 
				if(placeholderNum < 0) {
					//만약 filterKeywordSetter 메서드에서 오류가 발생했다면 SQL예외처리
					throw new SQLException();
				}
			}
			else if(memberDTO.getCondition().equals(RECENT_CONDITION)) {
				//프로필이미지경로
				System.out.println("log: Member selectOne : SELECTONE_RECENT");
				pstmt = conn.prepareStatement(SELECTONE_RECENT);
				pstmt.setInt(1, RECENT_PIVOT); //최신멤버기준값
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) { 
					data = new MemberDTO();
					data.setCnt(rs.getInt("CNT")); 	//멤버 수
					System.out.println("result exists");
				}
				rs.close();
			}
			else {
				//컨디션값 오류
				System.err.println("log: Member selectOne condition fail");
			}
			System.out.println("end");
		} catch (SQLException e) {
			System.err.println("log: Member selectOne SQLException fail");
			return null;
		} catch (Exception e) {
			System.err.println("log: Member selectOne Exception fail");
			return null;
		} finally {
			//연결해제
			if(!JDBCUtil.disconnect(conn, pstmt)) {
				//연결해제 실패
				System.err.println("log: Member selectOne disconnect fail");
				return null;
			}
			System.out.println("log: Member selectOne end");
		}
		System.out.println("log: Member selectOne return datas");
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
			System.out.println("log: Member selectOne filter search");
			for(String key : filters.keySet()) {
				if(key.equals(FILTER_NICKNAME_CONDITION)) {
					//닉네임 검색추가
					System.out.println("log: Member selectOne filterList Nickname Search");
					query.append(" "+SELECT_PART_NICKNAME);
				}
				else if(key.equals(FILTER_NAME_CONDITION)) {
					//이름 검색추가
					System.out.println("log: Member selectOne filterList Name Search");
					query.append(" "+SELECT_PART_NAME);
				}
				else if(key.equals(FILTER_EMAIL_CONDITION)) {
					//이메일 검색추가
					System.out.println("log: Member selectOne filterList Email Search");
					query.append(" "+SELECT_PART_EMAIL);
				}
				else if(key.equals(FILTER_ROLE_CONDITION)) {
					//권한 검색추가
					System.out.println("log: Member selectOne filterList Role Search");
					query.append(" "+SELECT_PART_ROLE);
				}
				else if(key.equals(FILTER_PHONE_CONDITION)) {
					//폰번호 검색추가
					System.out.println("log: Member selectOne filterList Phone Search");
					query.append(" "+SELECT_PART_PHONE);
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
						if(keywoard.getKey().equals(FILTER_NICKNAME_CONDITION)) {
							//닉네임 검색어
							pstmt.setString(placeholderNum++, keywoard.getValue());
							//넘어온 값 확인 로그
							System.out.println("log: parameter Nickname Search : "+keywoard.getValue());
						}
						else if(keywoard.getKey().equals(FILTER_NAME_CONDITION)) {
							//이름 검색어
							pstmt.setString(placeholderNum++, keywoard.getValue());
							//넘어온 값 확인 로그
							System.out.println("log: parameter Name Search : "+keywoard.getValue());
						}
						else if(keywoard.getKey().equals(FILTER_EMAIL_CONDITION)) {
							//이메일 검색어
							pstmt.setString(placeholderNum++, keywoard.getValue());
							//넘어온 값 확인 로그
							System.out.println("log: parameter Email Search : "+keywoard.getValue());
						}
						else if(keywoard.getKey().equals(FILTER_ROLE_CONDITION)) {
							//권한 검색어 
							pstmt.setString(placeholderNum++, keywoard.getValue());
							//넘어온 값 확인 로그
							System.out.println("log: parameter Role Search : "+keywoard.getValue());
						}
						else if(keywoard.getKey().equals(FILTER_PHONE_CONDITION)) {
							//폰번호 검색어 
							pstmt.setString(placeholderNum++, keywoard.getValue());
							//넘어온 값 확인 로그
							System.out.println("log: parameter Phone Search : "+keywoard.getValue());
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
