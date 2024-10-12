package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.StoreDTO;


public class StoreDAO {

	//가게 기본정보 추가
	//받은 데이터 : 가게 상호명, 기본&상세주소, 가게 전화번호
	//추가 데이터 : 가게 고유번호(PK), 가게 상호명, 기본&상세주소, 가게 전화번호, 가게폐점여부(N-자동등록)
	final String INSERT = "INSERT INTO BB_STORE(STORE_NUM, STORE_NAME, STORE_ADDRESS, STORE_ADDRESS_DETAIL, STORE_CONTACT) \r\n"
			+ "VALUES ((SELECT NVL(MAX(STORE_NUM)+1,1) FROM BB_STORE), ?, ?, ?, ?)";
	
	
	//특정가게 상세정보 조회
	//받은 데이터 : 가게 고유번호(PK)
	//조회 데이터 : 가게 고유번호(PK), 가게 상호명, 기본&상세주소, 가게 전화번호, 가게폐점여부
	final String INFO_STORE_SELECTONE = "SELECT STORE_NUM, STORE_NAME, STORE_ADDRESS, STORE_ADDRESS_DETAIL, \r\n"
			+ "STORE_CONTACT, STORE_CLOSED FROM BB_STORE WHERE STORE_NUM = ?";
	
	
	//가게 고유번호(PK) 최댓값 조회(+insert 과정에 활용)
	final String STORE_NEW_SELECTONE = "SELECT MAX(STORE_NUM) AS MAX_S_NUM FROM BB_STORE";

	
	//폐점 안 한 가게 수 조회
	final String STORE_CNT_SELECTONE = "SELECT COUNT(STORE_NUM) AS STORE_COUNT FROM BB_STORE WHERE 1=1 AND STORE_CLOSED = ?";

	
	//전체 데이터의 고유번호만 조회(중복제거)
	final String ALL_NUM_CNT_SELECTALL = "SELECT DISTINCT STORE_NUM FROM BB_STORE";
	
	
	//필터링 후 해당하는 가게 고유번호(PK) 모두 조회
	final String SELECTALL_VIEW = "SELECT DISTINCT STORE_NUM FROM BB_VIEW_SEARCHSTOREDATA_JOIN";
	
	
	//항상 조건절 충족하도록 WHERE 1=1 변수 생성
	final String SELECTALLNUMFILTER = " WHERE 1=1 ";

	
	// 가게 고유번호 조회를 위한 조건절 모음 변수
	final String NAME_LIKE = "AND STORE_NAME LIKE '%'||?||'%'";
	final String STORE_CLOSED = "AND STORE_CLOSED = ?";
	final String MENU_NOMAL = "AND STORE_MENU_NOMAL = ?";
	final String MENU_VEG = "AND STORE_MENU_VEG = ?";
	final String MENU_MINI = "AND STORE_MENU_MINI = ?";
	final String MENU_POTATO = "AND STORE_MENU_POTATO = ?";
	final String MENU_ICE = "AND STORE_MENU_ICE = ?";
	final String MENU_CHEESE = "AND STORE_MENU_CHEESE = ?";
	final String MENU_PASTRY = "AND STORE_MENU_PASTRY = ?";
	final String MENU_OTHER = "AND STORE_MENU_OTHER = ?";
	final String PAYMENT_CASHMONEY = "AND STORE_PAYMENT_CASHMONEY = ?";
	final String PAYMENT_CARD = "AND STORE_PAYMENT_CARD = ?";
	final String PAYMENT_ACCOUNT = "AND STORE_PAYMENT_ACCOUNT = ?";


	// [1] 필터링 결과 조회된 데이터 RN(가게고유번호 역순) 값 설정
	final String SELECTALL_STORE_DATAS = "SELECT * FROM(SELECT ROW_NUMBER() OVER (ORDER BY STORE_NUM DESC) AS RN, \r\n"
			+ "	STORE_NUM, STORE_NAME, STORE_ADDRESS, STORE_ADDRESS_DETAIL, STORE_CONTACT, STORE_CLOSED \r\n"
			+ "	FROM(SELECT STORE_NUM, STORE_NAME, STORE_ADDRESS, STORE_ADDRESS_DETAIL, STORE_CONTACT, STORE_CLOSED \r\n"
			+ "	FROM BB_STORE WHERE STORE_NUM IN( ";

	// [2] input값으로 들어온 조건 기반 가게 고유번호PK들: 1,3,4,5,6...

	// [3] 가게 고유번호(PK) 역순정렬 + 페이지네이션 요소
	final String SELECTALL_ELEMENT_DESC_PAGENATION = ") ORDER BY STORE_NUM DESC))SQ WHERE RN BETWEEN ? AND ? ";
	
	
	// UPDATE_필터 변수 모음 ------------------------------------------------------------------------------------------------------
	final String UPDATE_SET = "UPDATE BB_STORE SET";
	final String UPDATE_NAME = "STORE_NAME = ?";
	final String UPDATE_ADDRESS = "STORE_ADDRESS = ?";
	final String UPDATE_STORE_ADDRESS_DETAIL = "STORE_ADDRESS_DETAIL = ?";
	final String UPDATE_PHONENUM = "STORE_CONTACT = ?";
	final String UPDATE_CLOSED = "STORE_CLOSED = ?";
	final String UPDATE_WHERE_STORENUM = "WHERE STORE_NUM = ?";
	
	
	
	
	
	// StoreDAO insert --------------------------------------------------------------------------------------

	// pstmt 입력값 선언 및 초기화

	public boolean insert(StoreDTO storeDTO) { // 신규등록가게 추가
		System.out.println("log_StoreDAO_insert : start");
		System.out.println("log_StoreDAO_insert_controller input StoreDTO : " + storeDTO);

		//[1] DB 연결 객체를 'conn' 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
		Connection conn = JDBCUtil.connect();

		//[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
		PreparedStatement pstmt = null;
		System.out.println("log_StoreDAO_insert_pstmt null setting complete!");
		
		// 비정상 프로그램 종료 방지 위한 try-catch 진행
		try { 			
			//SQL DB와 연결하여 INSERT 변수값 미리 컴파일, 실행 준비
			pstmt = conn.prepareStatement(INSERT); // input값 storeDTO 이하 입력
			System.out.println("log_StoreDAO_insert_pstmt prepareStatement insert : " + pstmt);
			
			/*[4] 인자값으로 받은 데이터 쿼리문에 삽입: 가게 상호명, 기본,상세주소, 전화번호*/
			pstmt.setString(1, storeDTO.getStoreName()); 			// 상호명 입력
			pstmt.setString(2, storeDTO.getStoreDefaultAddress()); 	// 기본주소 입력
			pstmt.setString(3, storeDTO.getStoreDetailAddress()); 	// 상세주소 입력
			pstmt.setString(4, storeDTO.getStorePhoneNum()); 		// 전화번호 입력
			
			//[5] rs 변수 선언 : INSERT 쿼리문 실행
			int rs = pstmt.executeUpdate(); 						// INSERT 쿼리문 실행
			System.out.println("log_StoreDAO_update_rs complete");

			//[6] 예외처리 : 정상실행 되지 않았을 경우, false
			if (rs <= 0) {//rs >= 1(success) / rs = 0 (fail)
				System.err.println("log_StoreDAO_insert_executeUpdate() fail : if(rs <= 0)");
				return false; 
			}
		} catch (Exception e) {
			System.err.println("log_StoreDAO_insert_Exception fail : Exception e ");
			return false;
			
		//[7] JDBC 연결 해제 진행
		} finally {
			if (!JDBCUtil.disconnect(conn, pstmt)) { // 만약 JDBC가 연결되어 있다면
				System.err.println("log_StoreDAO_insert_disconnect fail"); // 연결해제 실패
				return false; // 반환값 false
			} // JDBC 연결 해제 되었다면
		}
		System.out.println("log_StoreDAO_insert_true!");
		return true;
	}

//   StoreDAO selectAll ------------------------------------------------------------------------------------   

	public ArrayList<StoreDTO> selectAll(StoreDTO storeDTO) { // 검색 가게 모두 조회
		System.out.println("log_StoreDAO_selectaAll : start");
		System.out.println("log_StoreDAO_selectAll controller input StoreDTO : " + storeDTO.toString());

		//[1] DB 연결 객체를 conn 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
		Connection conn = JDBCUtil.connect();
		System.out.println("log_StoreDAO_selectaAll conn setting complete");
								
		//[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
		PreparedStatement pstmt = null;
		System.out.println("log_StoreDAO_selectaAll pstmt null setting complete");

		//[3] rs 변수 선언 : selectAll 쿼리문 실행
		ResultSet rs = null;
		System.out.println("log_StoreDAO_selectaAll rs null setting complete");

		//[4] input값 pstmt에 넣을 때 활용하는 인덱스 번호
		int index = 1; 

		//[5] datas 변수 선언 : 결과값 담을 datas
		ArrayList<StoreDTO> datas = new ArrayList<StoreDTO>();
		
		//[6] queryBuilder 변수 선언 : 필터기능 위한 문자열 조작
		StringBuilder queryBuilder = null;
		System.out.println("log_StoreDAO_selectaAll queryBuilder null setting complete");

		
// ALL_NUM_CNT_SELECTALL : 전체 가게 고유번호 반환 메서드---------------------------------------------------------------------------
		if (storeDTO.getCondition().equals("ALL_NUM_CNT_SELECTALL")) {
			try {
				//[1] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
				//SQL DB와 연결하여 SELECTALL 변수값 미리 컴파일, 실행 준비
				pstmt = conn.prepareStatement(ALL_NUM_CNT_SELECTALL);
				System.out.println("log_StoreDAO_selectaAll pstmt conn");

				//[2] rs 변수 선언 : ALL_NUM_CNT_SELECTALL 쿼리문 실행
				rs = pstmt.executeQuery();
				
				//[3] 전체 가게의 고유번호 불러오기(중복 고유번호는 제외)
				while (rs.next()) {// 실행한 결과 고유번호 data에 불러오기
					StoreDTO data = new StoreDTO();
					data.setStoreNum(rs.getInt("STORE_NUM")); 
					datas.add(data);
				}
			} catch (Exception e) {
				System.err.println("log_StoreDAO_selectAll_Exception fail");
			}

// FILTER_NUM_CNT_SELECTALL : 필터링된 가게 고유번호 반환 메서드---------------------------------------------------------------------------ㄴ
		} else if (storeDTO.getCondition().equals("FILTER_NUM_CNT_SELECTALL")) {
			try {
				//[1] queryBuilder 가변 객체 생성
				//SELECTALL_VIEW = "SELECT DISTINCT STORE_NUM FROM BB_VIEW_SEARCHSTOREDATA_JOIN";
				queryBuilder = new StringBuilder(SELECTALL_VIEW); // WHERE절 없는 queryBuilder
				queryBuilder.append(SELECTALLNUMFILTER); // WHERE절 1=1 추가(항상 조건식 성립)
				System.out.println(	"log_StoreDAO_selectAll_queryBuilder StoreNum & Filtering : " + queryBuilder.toString());

				
				//[2] 가게 조건 확인
				// storeDTO.getStoreName(가게상호명) 값을 전달받았을 때
				if (storeDTO.getStoreName() != null && !storeDTO.getStoreName().isEmpty()) {
					queryBuilder.append(NAME_LIKE);
					queryBuilder.append(" ");
				}
				// storeDTO.getStoreClosed(폐점여부) 값을 전달받았을 때
				if (storeDTO.getStoreClosed() != null && !storeDTO.getStoreClosed().isEmpty()) {
					queryBuilder.append(STORE_CLOSED);
					queryBuilder.append(" ");
					System.out.println("log_StoreDAO_selectAll_queryBuilder STORE_CLOSED : " + queryBuilder.toString());
				}

				
				//[3] 가게 메뉴 조건 확인 : 메뉴 배열리스트가 null이 아닐 때 진행 선택메뉴 확인 절차 진행
				
				//		[0] 팥/슈크림 붕어빵 값을 전달받았을 때 조건쿼리문 붙이기
				if (storeDTO.getStoreMenu() != null) {
					if (storeDTO.getStoreMenu().get(0) != null && !storeDTO.getStoreMenu().get(0).isEmpty()) {
						queryBuilder.append(MENU_NOMAL + " ");
						System.out.println("log_StoreDAO_selectAll_queryBuilder MENU_NOMAL : " + queryBuilder.toString());
					}
					// 	[1] 야채/김치/만두 붕어빵 값을 전달받았을 때 조건쿼리문 붙이기
					if (storeDTO.getStoreMenu().get(1) != null && !storeDTO.getStoreMenu().get(1).isEmpty()) {
						queryBuilder.append(MENU_VEG + " ");
						System.out.println("log_StoreDAO_selectAll_queryBuilder MENU_VEG : " + queryBuilder.toString());
					}
					// 	[2] 미니 붕어빵 값을 전달받았을 때 조건쿼리문 붙이기
					if (storeDTO.getStoreMenu().get(2) != null && !storeDTO.getStoreMenu().get(2).isEmpty()) {
						queryBuilder.append(MENU_MINI + " ");
						System.out.println("log_StoreDAO_selectAll_queryBuilder MENU_MINI : " + queryBuilder.toString());
					}
					// 	[3] 고구마 붕어빵 값을 전달받았을 때 조건쿼리문 붙이기
					if (storeDTO.getStoreMenu().get(3) != null && !storeDTO.getStoreMenu().get(3).isEmpty()) {
						queryBuilder.append(MENU_POTATO + " ");
						System.out.println("log_StoreDAO_selectAll_queryBuilder MENU_POTATO : " + queryBuilder.toString());
					}
					// 	[4] 아이스크림/초코 붕어빵 값을 전달받았을 때 조건쿼리문 붙이기
					if (storeDTO.getStoreMenu().get(4) != null && !storeDTO.getStoreMenu().get(4).isEmpty()) {
						queryBuilder.append(MENU_ICE + " ");
						System.out.println("log_StoreDAO_selectAll_queryBuilder MENU_ICE : " + queryBuilder.toString());
					}
					// 	[5] 치즈 붕어빵 값을 전달받았을 때 조건쿼리문 붙이기
					if (storeDTO.getStoreMenu().get(5) != null && !storeDTO.getStoreMenu().get(5).isEmpty()) {
						queryBuilder.append(MENU_CHEESE + " ");
						System.out.println("log_StoreDAO_selectAll_queryBuilder MENU_CHEESE : " + queryBuilder.toString());
					}
					//	[6] 페스츄리 입력 값을 전달받았을 때 조건쿼리문 붙이기
					if (storeDTO.getStoreMenu().get(6) != null && !storeDTO.getStoreMenu().get(6).isEmpty()) {
						queryBuilder.append(MENU_PASTRY + " ");
						System.out.println("log_StoreDAO_selectAll_queryBuilder MENU_PASTRY : " + queryBuilder.toString());
					}
					// 	[7] 기타 값을 전달받았을 때 조건쿼리문 붙이기
					if (storeDTO.getStoreMenu().get(7) != null && !storeDTO.getStoreMenu().get(7).isEmpty()) {
						queryBuilder.append(MENU_OTHER + " ");
						System.out.println("log_StoreDAO_selectAll_queryBuilder MENU_OTHER : " + queryBuilder.toString());
					}
				}
				//[4] 결제방식 조건 확인
					
				//현금 값을 전달받았을 때 조건쿼리문 붙이기
				if(storeDTO.getStorePaymentCashmoney() != null && !storeDTO.getStorePaymentCashmoney().isEmpty()) {
					queryBuilder.append(PAYMENT_CASHMONEY + " ");
					System.out.println("log_StoreDAO_selectAll_queryBuilder PAYMENT_CASHMONEY : " + queryBuilder.toString());
				}
				//카드 값을 전달받았을 때 조건쿼리문 붙이기
				if(storeDTO.getStorePaymentCard() != null && !storeDTO.getStorePaymentCard().isEmpty()) {
					queryBuilder.append(PAYMENT_CARD + " ");
					System.out.println("log_StoreDAO_selectAll_queryBuilder getStorePaymentCard : " + queryBuilder.toString());
				}
				//계좌이체 값을 전달받았을 때 조건쿼리문 붙이기
				if(storeDTO.getStorePaymentaccountTransfer() != null && !storeDTO.getStorePaymentaccountTransfer().isEmpty()) {
					queryBuilder.append(PAYMENT_ACCOUNT + " ");
					System.out.println("log_StoreDAO_selectAll_queryBuilder getStorePaymentaccountTransfer : " + queryBuilder.toString());
				}
				
				// 필터링 된 selectAll 쿼리문
				System.out.println("log_StoreDAO_selectAll_queryBuilder.toString() finish: " + queryBuilder.toString());
				
				//[5] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
				//SQL DB와 연결하여 미리 컴파일, 실행 준비
				pstmt = conn.prepareStatement(queryBuilder.toString());

				
				//[6] 인자값으로 받은 데이터 쿼리문에 삽입
				
				//	가게상호명 값을 전달받았을 때 값 입력
				if (storeDTO.getStoreName() != null && !storeDTO.getStoreName().isEmpty()) {
					pstmt.setString(index++, storeDTO.getStoreName());
					System.out.println("log_StoreDAO_selectAll_pstmt getStoreName() compelete index++ : " + index);
				}
				// 	폐점여부 값을 전달받았을 때 값 입력
				if (storeDTO.getStoreClosed() != null && !storeDTO.getStoreClosed().isEmpty()) {
					pstmt.setString(index++, storeDTO.getStoreClosed());
					System.out.println("log_StoreDAO_selectAll_pstmt getStoreClosed() compelete index++ : " + index);
				}
				
				//	메뉴 조건이 들어왔다면 이하 메뉴 if문 진행
				if(storeDTO.getStoreMenu() != null){
					// [0] 팥/슈크림 붕어빵 값을 전달받았을 때 입력
					if (storeDTO.getStoreMenu().get(0) != null && !storeDTO.getStoreMenu().get(0).isEmpty()) {
						pstmt.setString(index++, storeDTO.getStoreMenu().get(0));
						System.out.println("log_StoreDAO_selectAll_pstmt setStoreMenuNomal() compelete index++ : " + index);
					}
					// [1] 야채/김치/만두 붕어빵 값을 전달받았을 때 입력
					if (storeDTO.getStoreMenu().get(1) != null && !storeDTO.getStoreMenu().get(1).isEmpty()) {
						pstmt.setString(index++, storeDTO.getStoreMenu().get(1));
						System.out.println("log_StoreDAO_selectAll_pstmt setStoreMenuVegetable() compelete index++ : " + index);
					}
					// [2] 미니 붕어빵 값을 전달받았을 때 입력
					if (storeDTO.getStoreMenu().get(2) != null && !storeDTO.getStoreMenu().get(2).isEmpty()) {
						pstmt.setString(index++, storeDTO.getStoreMenu().get(2));
						System.out.println("log_StoreDAO_selectAll_pstmt setStoreMenuMini() compelete index++ : " + index);
					}	
					// [3] 고구마 붕어빵 값을 전달받았을 때 입력
					if (storeDTO.getStoreMenu().get(3) != null && !storeDTO.getStoreMenu().get(3).isEmpty()) {
						pstmt.setString(index++, storeDTO.getStoreMenu().get(3));
						System.out.println("log_StoreDAO_selectAll_pstmt setStoreMenuPotato() compelete index++ : " + index);
					}
					// [4] 아이스크림/초코 붕어빵 값을 전달받았을 때 입력
					if (storeDTO.getStoreMenu().get(4) != null && !storeDTO.getStoreMenu().get(4).isEmpty()) {
						pstmt.setString(index++, storeDTO.getStoreMenu().get(4));
						System.out.println("log_StoreDAO_selectAll_pstmt setStoreMenuIceCream() compelete index++ : " + index);
					}
					// [5] 치즈 붕어빵 값을 전달받았을 때 입력
					if (storeDTO.getStoreMenu().get(5) != null && !storeDTO.getStoreMenu().get(5).isEmpty()) {
						pstmt.setString(index++, storeDTO.getStoreMenu().get(5));
						System.out.println("log_StoreDAO_selectAll_pstmt setStorMenuCheese() compelete index++ : " + index);
					}
					// [6] 페스츄리 입력 값을 전달받았을 때 입력
					if (storeDTO.getStoreMenu().get(6) != null && !storeDTO.getStoreMenu().get(6).isEmpty()) {
						pstmt.setString(index++, storeDTO.getStoreMenu().get(6));
						System.out.println("log_StoreDAO_selectAll_pstmt setStoreMenuPastry() compelete index++ : " + index);
					}
					// [7] 기타 값을 전달받았을 때 입력
					if (storeDTO.getStoreMenu().get(7) != null && !storeDTO.getStoreMenu().get(7).isEmpty()) {
						pstmt.setString(index++, storeDTO.getStoreMenu().get(7));
						System.out.println("log_StoreDAO_selectAll_pstmt setStoreMenuOthers() compelete index++ : " + index);
					}
				}
					// 결제 현금 가능여부 값을 전달받았을 때 입력
				if (storeDTO.getStorePaymentCashmoney() != null && !storeDTO.getStorePaymentCashmoney().isEmpty()) {
					pstmt.setString(index++,storeDTO.getStorePaymentCashmoney());
					System.out.println("log_StoreDAO_selectAll_pstmt getStorePaymentCashmoney() compelete index++ : " + index);	
				}
					// 결제 카드 가능여부 값을 전달받았을 때 입력
				if (storeDTO.getStorePaymentCard() != null && !storeDTO.getStorePaymentCard().isEmpty()) {
					pstmt.setString(index++,storeDTO.getStorePaymentCard());
					System.out.println("log_StoreDAO_selectAll_pstmt getStorePaymentCard() compelete index++ : " + index);	
				}
					//결제 계좌이체 가능여부 값을 전달받았을 때 입력
				if (storeDTO.getStorePaymentaccountTransfer() != null && !storeDTO.getStorePaymentaccountTransfer().isEmpty()) {
					pstmt.setString(index++,storeDTO.getStorePaymentaccountTransfer());
					System.out.println("log_StoreDAO_selectAll_pstmt getStorePaymentaccountTransfer() compelete index++ : " + index);	
				}
				
				//[7] rs 변수 선언 : queryBuilder.toString() 쿼리문 실행
				rs = pstmt.executeQuery();
				System.out.println("log_StoreDAO_selectAll executeQuery() complete");
				
				//[8] 실행한 결과 가게 고유번호 data에 불러오기
				while (rs.next()) {
					StoreDTO data = new StoreDTO();
					data.setStoreNum(rs.getInt("STORE_NUM"));
					datas.add(data); // data값 배열리스트 datas에 넣기
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("log_StoreDAO_selectAll_Exception fail");
			}
			
			
//조건값에 따른 가게 고유번호들의 기본정보 조회---------------------------------------------------------------------------------------------------
// 가게 기본 정보 : 가게 고유번호, 상호명, 기본주소, 상세주소, 전화번호, 폐점여부
		} else if (storeDTO.getCondition().equals("SEARCH_STORE_SELECTALL")) {
			try {
				System.out.println("log_StoreDAO_selectAll_condition : SEARCH_STORE_SELECTALL");
				// SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화

				// [1] 만약 가게 고유번호 값이 1개라도 있을 때 queryBuilder 가변 객체 생성
				// [2] 필터링 결과 조회된 데이터 RN(가게 고유번호 역순)값 설정
				queryBuilder = new StringBuilder(SELECTALL_STORE_DATAS);// 조건값에 해당하는 고유번호 1개까지 쿼리문 형성

				
				/*[가게 기본정보 조회를 위한 쿼리문 전체 예시] 
					"SELECT * FROM(SELECT ROW_NUMBER() OVER (ORDER BY STORE_NUM DESC) AS RN, \r\n"
				+ "	STORE_NUM, STORE_NAME, STORE_ADDRESS, STORE_ADDRESS_DETAIL, STORE_CONTACT, STORE_CLOSED \r\n"
				+ "	FROM(SELECT STORE_NUM, STORE_NAME, STORE_ADDRESS, STORE_ADDRESS_DETAIL, STORE_CONTACT, STORE_CLOSED \r\n"
				+ "	FROM BB_STORE WHERE STORE_NUM IN( 1, 3, 4, 5, 6 ) ORDER BY STORE_NUM DESC))SQ WHERE RN BETWEEN 1 AND 6	*/
				 								

				/* 1) SELECTALL_STORE_DATAS 값 예시 
				+ "SELECT * FROM(SELECT ROW_NUMBER() OVER (ORDER BY STORE_NUM DESC) AS RN, \r\n"
				+ "	STORE_NUM, STORE_NAME, STORE_ADDRESS, STORE_ADDRESS_DETAIL, STORE_CONTACT, STORE_CLOSED \r\n"
				+ "	FROM(SELECT STORE_NUM, STORE_NAME, STORE_ADDRESS, STORE_ADDRESS_DETAIL, STORE_CONTACT, STORE_CLOSED \r\n"
				+ "	FROM BB_STORE WHERE STORE_NUM IN( */

				// 2) input값으로 들어온 조건 기반 가게 고유번호PK들: 1,3,4,5,6
					// 1. 반드시 들어가는 가게 고유번호 리스트 [0] pstmt에 넣기 ex.IN( 1
					// 2. ",가게고유번호"의 반복
					/* ex. , 1 , 3 , 4 , 5 , 6 */
				// 3) ')' 괄호를 닫아주기

				
				// 1) 반드시 1개 이상 들어가는 가게 고유번호[0] + 오류방지 띄어쓰기
				queryBuilder.append(" " + storeDTO.getStoreNumCNT().get(0));
				System.out.println("log_StoreDAO_selectAll_getStoreNumCNT().get(0) : " + storeDTO.getStoreNumCNT().get(0));

				// 2) 가게 고유번호 반복(, N)
				for (int i = 1; i < storeDTO.getStoreNumCNT().size(); i++) {
					queryBuilder.append(", " + storeDTO.getStoreNumCNT().get(i));
					System.out.println("log_StoreDAO_selectAll_getStoreNumCNT().size() : " + storeDTO.getStoreNumCNT().size());
					System.out.println("log_StoreDAO_selectAll_queryBuilder : " + queryBuilder);
				}
				// 3) 괄호 닫기
				System.out.println("log_StoreDAO_selectAll_queryBuilder forfinish : " + queryBuilder.toString());

				//[3] 가게 고유번호(PK)역순 정렬 + 페이지네이션 요소
				queryBuilder.append(SELECTALL_ELEMENT_DESC_PAGENATION);

				//[4] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
				//SQL DB와 연결하여 queryBuilder.toString()값 미리 컴파일, 실행 준비
				pstmt = conn.prepareStatement(queryBuilder.toString());

				//[5] 인자값으로 받은 데이터 쿼리문에 삽입
				pstmt.setInt(1, storeDTO.getStoreStartPage()); 	// 페이지네이션 시작 번호
				pstmt.setInt(2, storeDTO.getStoreEndPage()); 	// 페이지네이션 종료 번호
				System.out.println("log_StoreDAO_selectAll_queryBuilder all finish : " + queryBuilder.toString());

				//[6] rs 변수 선언 : 쿼리문 실행
				rs = pstmt.executeQuery();
				
				//[7] 실행한 결과 기본정보 data에 불러오기
				while (rs.next()) {
					StoreDTO data = new StoreDTO();
					data.setStoreNum(rs.getInt("STORE_NUM"));							// 가게 고유번호
					data.setStoreName(rs.getString("STORE_NAME")); 						// 가게 상호명
					data.setStoreDefaultAddress(rs.getString("STORE_ADDRESS")); 		// 가게 기본주소
					data.setStoreDetailAddress(rs.getString("STORE_ADDRESS_DETAIL")); 	// 가게 상세주소
					data.setStorePhoneNum(rs.getString("STORE_CONTACT"));				// 가게 전화번호
					data.setStoreClosed(rs.getString("STORE_CLOSED")); 					// 가게 폐점여부
					datas.add(data);	//datas로 취합
					System.out.println("log_StoreDAO_selectAll_data : " + data);
				}
				System.out.println("log_StoreDAO_selectAll_datas : " + datas);
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("log_StoreDAO_selectAll_Exception fail");
			//[8] JDBC 연결 해제 진행 
			} finally {
				if (!JDBCUtil.disconnect(conn, pstmt)) { // 만약 JDBC가 연결되어 있다면, 연결해제 실패
					System.err.println("log_StoreDAO_selectAll_disconnect fail");
				}// JDBC 연결 해제 되었다면, 연결해제 성공 
			}
			System.out.println("log_StoreDAO_selectAll_SEARCH_STORE_SELECTAll return datas");
		}
		return datas; // 데이터 반환
	}

	
	
	// StoreDAO selectOne ----------------------------------------------------------------------------------------------------------------------------
	public StoreDTO selectOne(StoreDTO storeDTO) {
		System.out.println("log_StoreDAO_selectOne : start");
		System.out.println("log_StoreDAO_selectOne controller input StoreDTO : " + storeDTO);

		//[1] DB 연결 객체를 conn. 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
		Connection conn = JDBCUtil.connect();
		System.out.println("log_StoreDAO_selectOne conn setting complete");

		//[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
		PreparedStatement pstmt = null;
		System.out.println("log_StoreDAO_selectOne psmt null setting complete");

		//[3] rs 변수 선언 : selectOne 쿼리문 실행
		ResultSet rs = null;
		System.out.println("log_StoreDAO_selectOne rs null setting complete");

		//[4] data 변수 선언 : 결과값 담을 data
		StoreDTO data = null;
		System.out.println("log_StoreDAO_selectOne data null setting complete");

		try {
	// 값이 가장 큰 가게고유번호(PK) 조회(+insert 과정에 활용)---------------------------------------------------
			if (storeDTO.getCondition().equals("STORE_NEW_SELECTONE")) {
				System.out.println("log_StoreDAO_selectOne condition : STORE_NEW_SELECTONE");
				
				//[5] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
				//SQL DB와 연결하여 STORE_NEW_SELECTONE 변수값 미리 컴파일, 실행 준비
				pstmt = conn.prepareStatement(STORE_NEW_SELECTONE);
				System.out.println("log_StoreDAO_selectOne pstmt conn");

				//[6] rs 변수 선언 : STORE_NEW_SELECTONE 쿼리문 실행
				rs = pstmt.executeQuery(); // 이하 rs 실행 및 데이터 불러오기
				System.out.println("log_StoreDAO_selectOne executeQuery() complete");

				//[7] 가게 고유번호 최댓값 불러오기
				if (rs.next()) {
					data = new StoreDTO(); // 데이터 요소 담기 위한 객체 생성
					data.setStoreMaxNum(rs.getInt("MAX_S_NUM"));
					System.out.println("log_StoreDAO_selectOne_data finish : " + data);
				}
			}
			// selectOne 폐점 안한 가게 수 조회-----------------------------------------------------------------------
			if (storeDTO.getCondition().equals("NOT_CLOSED_NUM_CNT_SELECTONE")) {
				System.out.println("log_StoreDAO_selectOne condition : STORE_CNT_SELECTONE");

				//[5] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
				//SQL DB와 연결하여 STORE_CNT_SELECTONE 변수값 미리 컴파일, 실행 준비
				pstmt = conn.prepareStatement(STORE_CNT_SELECTONE);
				System.out.println("log_StoreDAO_selectOne pstmt conn");

				//[6] 인자값으로 받은 데이터 쿼리문에 삽입
				pstmt.setString(1, storeDTO.getStoreClosed()); // 폐점 여부 입력
				System.out.println("log_StoreDAO_selectOne_pstmt input complete");

				//[7] rs 변수 선언 : STORE_CNT_SELECTONE 쿼리문 실행
				rs = pstmt.executeQuery();
				System.out.println("log_StoreDAO_selectOne_executeQuery() complete");

				//[8] 폐점 안 한 가게 수 모두 조회
				if (rs.next()) {
					data = new StoreDTO(); // 데이터 요소 담기 위한 객체 생성
					data.setStoreNotClosedCount(rs.getInt("STORE_COUNT"));
		            System.out.println("log_StoreDAO_selectOne_data : " + data);
				}
			}
			// 특정가게 상세정보 조회---------------------------------------------------------------------------------
			if (storeDTO.getCondition().equals("INFO_STORE_SELECTONE")) {
				System.out.println("log_StoreDAO_selectOne_condition : INFO_STORE_SELECTONE");
				
				
				//[5] pstmt 변수 선언 : () 안 쿼리문으로 실행 준비 완료.
				//SQL DB와 연결하여 INFO_STORE_SELECTONE 변수값 미리 컴파일, 실행 준비
				pstmt = conn.prepareStatement(INFO_STORE_SELECTONE);
				System.out.println("log_StoreDAO__selectOne_pstmt conn");

				//[6] 인자값으로 받은 데이터 쿼리문에 삽입
				pstmt.setInt(1, storeDTO.getStoreNum()); // 폐점 여부 입력
				System.out.println("log_StoreDAO_selectOne_pstmt input complete");

				//[7] rs 변수 선언 : INFO_STORE_SELECTONE 쿼리문 실행
				rs = pstmt.executeQuery();
				System.out.println("log_StoreDAO_selectOne_executeQuery() complete");

				//[8] 특정 가게의 기본정보 불러오기
				if (rs.next()) {
					data = new StoreDTO(); // 데이터 요소 담기 위한 객체 생성
					data.setStoreNum(rs.getInt("STORE_NUM"));							//가게 고유번호
					data.setStoreName(rs.getString("STORE_NAME"));						//가게 상호명
					data.setStoreDefaultAddress(rs.getString("STORE_ADDRESS"));			//가게 기본주소
					data.setStoreDetailAddress(rs.getString("STORE_ADDRESS_DETAIL"));	//가게 상세주소
					data.setStorePhoneNum(rs.getString("STORE_CONTACT"));				//가게 전화번호
					data.setStoreClosed(rs.getString("STORE_CLOSED"));					//가게 폐점 여부
					System.out.println("log_StoreDAO_selectOne_data INFO_STORE_SELECTONE finish : " + data);
				}
			}rs.close();
			System.out.println("log_StoreDAO_selectOne_complet!");
		} catch (SQLException e) {
			System.err.println("log_StoreDAO_selectOne_Exception fail : Exception e ");
		} finally {
			if (!JDBCUtil.disconnect(conn, pstmt)) { // 연결해제 실패
				System.err.println("log_StoreDAO_selectOne_disconnect fail");
			}
			System.out.println("log_StoreDAO_selectOne_disconnect complet!"); // 연결해제 성공
		}
		System.out.println("log_StoreDAO_selectOne_SEARCH_STORE_SELECTOne return data!");
		return data; // 데이터 반환
	}

	// StoreDAO update 가게 기본 정보 수정
	// ------------------------------------------------------------------------------
	public boolean update(StoreDTO storeDTO) {
		System.out.println("log_StoreDAO_update : start");
		System.out.println("log_StoreDAO_update_controller input StoreDTO : " + storeDTO);

		//[1] DB 연결 객체를 conn 변수로 선언: JDBC 연결 관리하는 JDBCUtil 클래스에서 DB연결 설정 메서드 실행.
		Connection conn = JDBCUtil.connect();

		//[2] SQL 쿼리 미리 컴파일하는 객체 PreparedStatement를 참조하는 pstmt 변수 선언 및 초기화
		PreparedStatement pstmt = null;
		System.out.println("log_StoreDAO_update_pstmt null setting");

		//pstmt 변수 초기화
		int index = 1;
		
		// 비정상 프로그램 종료 방지 위한 try-catch 진행
		try {
			
			//[3]동적 쿼리 queryBuilder 변수 선언 : UPDATE 위한 공통절
			StringBuilder queryBuilder = new StringBuilder(UPDATE_SET + " ");
			System.out.println("log_StoreDAO_update_queryBuilder UPDATE_SET setting");
//			final String UPDATE_SET = "UPDATE BB_STORE";

			
			//[4]SET절 추가 및 쿼리문 형성
			// storeDTO.getStoreName 이름 값을 전달받았을 때 쿼리문 연결
			if (storeDTO.getStoreName() != null && !storeDTO.getStoreName().isEmpty()) {
				queryBuilder.append(UPDATE_NAME + ", ");
				System.out.println("log_StoreDAO_update_queryBuilder getStoreName setting");
			}
			// storeDTO.getStoreDefaultAddress 기본주소 값을 전달받았을 때 쿼리문 연결
			if (storeDTO.getStoreDefaultAddress() != null && !storeDTO.getStoreDefaultAddress().isEmpty()) {
				queryBuilder.append(UPDATE_ADDRESS + ", ");
				System.out.println("log_StoreDAO_update_queryBuilder getStoreDefaultAddress setting");
			}
			// storeDTO.getStoreDetailAddress 상세주소 값을 전달받았을 때
			if (storeDTO.getStoreDetailAddress() != null && !storeDTO.getStoreDetailAddress().isEmpty()) {
				queryBuilder.append(UPDATE_STORE_ADDRESS_DETAIL + ", ");
				System.out.println("log_StoreDAO_update_queryBuilder getStoreDetailAddress setting");
			}
			// storeDTO.getStorePhoneNum 전화번호 값을 전달받았을 때
			if (storeDTO.getStorePhoneNum() != null && !storeDTO.getStorePhoneNum().isEmpty()) {
				queryBuilder.append(UPDATE_PHONENUM + ", ");
				System.out.println("log_StoreDAO_update_queryBuilder getStorePhoneNum setting");
			}
			// storeDTO.getStoreClosed 폐점여부 값을 전달받았을 때
			if (storeDTO.getStoreClosed() != null && !storeDTO.getStoreClosed().isEmpty()) {
				queryBuilder.append(UPDATE_CLOSED + " ");
				System.out.println("log_StoreDAO_update_queryBuilder getStoreClosed setting");
			}
			queryBuilder.delete(queryBuilder.length() - 1, queryBuilder.length()); // 마지막 ,글자 삭제
			// WHERE절 쿼리문에 추가(가게 고유번호(PK) 포함)
			queryBuilder.append(" ");
			queryBuilder.append(UPDATE_WHERE_STORENUM);
			System.out.println("log_StoreDAO_update_queryBuilder finish : " + queryBuilder);

			//쿼리문 형성 완료
			pstmt = conn.prepareStatement(queryBuilder.toString()); 						
			
			
			//[5] 쿼리문에 인자값으로 받은 데이터 삽입---------------------------------------------------------
			if (storeDTO.getStoreName() != null && !storeDTO.getStoreName().isEmpty()) {
				// storeDTO.getStoreName 상호명 인자값으로 받았다면 데이터 쿼리문에 삽입
				pstmt.setString(index++, storeDTO.getStoreName());
				System.out.println("log_StoreDAO_update_pstmt setStoreName()");
				System.out.println("getStoreName : after "+ index);
			}
			// storeDTO.getStoreDefaultAddress 기본주소 인자값으로 받았다면 데이터 쿼리문에 삽입
			if (storeDTO.getStoreDefaultAddress() != null && !storeDTO.getStoreDefaultAddress().isEmpty()) {
				pstmt.setString(index++, storeDTO.getStoreDefaultAddress());
				System.out.println("log_StoreDAO_update_pstmt setStoreDefaultAddress()");
				System.out.println("getStoreDefaultAddress : after "+ index);

			}
			// storeDTO.getStoreDetailAddress 상세주소 인자값으로 받았다면 데이터 쿼리문에 삽입
			if (storeDTO.getStoreDetailAddress() != null && !storeDTO.getStoreDetailAddress().isEmpty()) {
				pstmt.setString(index++, storeDTO.getStoreDetailAddress());
				System.out.println("log_StoreDAO_update_pstmt setStoreDetailAddress()");
				System.out.println("getStoreDetailAddress : after "+ index);

			}			
			// storeDTO.getStorePhoneNum 전화번호 인자값으로 받았다면 데이터 쿼리문에 삽입
			if (storeDTO.getStorePhoneNum() != null && !storeDTO.getStorePhoneNum().isEmpty()) {
				pstmt.setString(index++, storeDTO.getStorePhoneNum());
				System.out.println("log_StoreDAO_update_pstmt setStorePhoneNum()");
				System.out.println("getStorePhoneNum : after "+ index);

			}		
			// storeDTO.getStoreClosed 폐점여부 인자값으로 받았다면 데이터 쿼리문에 삽입
			if (storeDTO.getStoreClosed() != null && !storeDTO.getStoreClosed().isEmpty()) {
				pstmt.setString(index++, storeDTO.getStoreClosed());
				System.out.println("log_StoreDAO_update_pstmt setStoreClosed()");
				System.out.println("getStoreClosed : after "+ index);

			}		
			// storeDTO.getStoreNum 가게고유번호(PK) 인자값 쿼리문에 삽입
			pstmt.setInt(index++, storeDTO.getStoreNum());
			System.out.println("log_StoreDAO_update_pstmt setStoreNum()");
					
			//[6] rs 변수 선언 : UPDATE 쿼리문 실행
			int rs = pstmt.executeUpdate();
			
			//[7] 예외처리 : 정상실행 되지 않았을 경우, false
			if (rs <= 0) {
				System.err.println("log_StoreDAO_update_executeUpdate() fail : if(rs <= 0)");
				return false;
			}
		} catch (Exception e) {
			System.err.println("log_StoreDAO_update_Exception fail : Exception e ");
			e.printStackTrace();

			return false;
		//[8] JDBC 연결 해제 진행
		} finally { 
			if (!JDBCUtil.disconnect(conn, pstmt)) { // 만약 JDBC가 연결되어 있다면
				System.err.println("log_StoreDAO_update_disconnect fail"); // 연결해제 실패
				return false;
			} // JDBC 연결 해제 되었다면
		}System.out.println("log_StoreDAO_update_true!");
		return true; //반환값 true
	}

	private boolean delete(StoreDTO storeDTO) { // 신규등록가게 추가
		return false;
	}
}