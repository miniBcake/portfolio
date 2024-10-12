package controller.store;

import java.util.ArrayList;
import java.util.Arrays;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.PaginationUtils;
import controller.common.StoreMethods;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.StoreDAO;
import model.dto.StoreDTO;
import model.dto.StoreMenuDTO;
import model.dto.StorePaymentDTO;


public class SearchStoreAction implements Action{
	// 가게 조회 기능
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.err.println("	log : SearchStoreAction.java	시작");

		// M에게서 받을 데이터를 담을 ArrayList<StoreDTO> 생성
		// 변수명 : datas
		ArrayList<StoreDTO> datas = new ArrayList<StoreDTO>();
		System.out.println("	log : SearchStoreAction.java(25)	"
				+ "데이터를 담을 ArryaList<StoreDTO> datas 생성");

		// 페이지네이션을 하기 위한 기본값 설정
		// int 현재 페이지 번호(currentPage), 1로 초기화
		int currentPage = 1;
		// int 페이지당 데이터 수(pageSize) : 6개
		int pageSize = 6;
		System.out.println("	log : SearchStoreAction.java(33)	"
				+ "페이지네이션 변수 생성 currentPage : "+currentPage + ", pageSize : "+ pageSize);

		// View에서 현재 페이지 번호와 페이지 크기 받아오기
		// "page" 요청이 null이 아니라면
		if(request.getParameter("page") != null) {
			// currentPage에 "page" 요청값 받아오기
			// 형변환 필요
			currentPage = Integer.parseInt(request.getParameter("page").trim());
			System.out.println("	log : SearchStoreAction.java(42)	request(page) : "+currentPage);
		}
		// "pageSize" 요청이 null이 아니라면
//		if(request.getParameter("pageSize") != null) {
//			// pageSize에 "pageSize" 요청값 받아오기
//			// 형변환 필요
//			pageSize = Integer.parseInt(request.getParameter("pageSize"));
//			System.out.println("	log : SearchStoreAction.java(49)	request(pageSize) : "+pageSize);
//		}
		// V에서 검색 데이터 가져오기 ========================================================================
		// request.getParameter; 

		// 영업중 여부 데이터를 받음
		// String[] 변수명 : storeClosed
		String[] storeClosedV = request.getParameterValues("storeClosed");
		System.out.println("	log : SearchStoreAction.java(57)	storeClosed : "+storeClosedV);
		// storeClosed가 존재하는 지에 따라 값을 변경
		String storeClosed = null;
		// null이 아니라면
		if(storeClosedV != null && storeClosedV[0].equals("open")) {
			System.out.println("	log : SearchStoreAction.java(62)	storeClosedV != null");
			System.out.println("	log : SearchStoreAction.java(57)	storeClosedV : "+storeClosedV[0]);
			// charStoreClosed의 값을 storeClosed[0] 값으로 변경
			storeClosed = "N";
		}
		System.out.println("	log : SearchStoreAction.java(66)	charStoreClosed : "+storeClosed);

		// 가게이름 받기
		// String 변수명 : storeName
		String storeName = request.getParameter("storeName");
		System.out.println("	log : SearchStoreAction.java(71)	storeName : "+storeName);

		// 가게 메뉴 받기 (팥/슈크림, 야채/김치/만두, 미니, 고구마, 아이스크림/초코, 치즈, 패스츄리, 기타)
		// String[] 변수명 : storeMenu
		String[] storeMenu = request.getParameterValues("storeMenu");
		if (storeMenu != null) {
		    for (int i = 0; i < storeMenu.length; i++) {
		        storeMenu[i] = storeMenu[i].trim(); // 공백 제거
		        System.out.println("선택된 메뉴: " + storeMenu[i]); // 선택된 값을 출력
		    }
		}
		System.out.println("	log : SearchStoreAction.java(76)	storeMenu : "+ Arrays.toString(storeMenu));

		// 가게 메뉴 체크박스 데이터 전달을 위한 배열리스트 생성
		// ArrayList<String> 변수명 : storeMenuDatas
		// storeMenuValue를 사용하여 메뉴 값을 받아옴
		ArrayList<String> storeMenuDatas = StoreMethods.storeMenuValue(storeMenu);
		System.out.println("	log : SearchStoreAction.java(82)	storeMenuDatas : "+storeMenuDatas);

		// 결제방식 받기 (현금, 카드, 계좌이체)
		// String[] 변수명 : storePayment
		String[] storePayment = request.getParameterValues("storePayment");

		// M과 데이터를 주고 받음 =======================================================================
		// StoreDTO, DAO 생성
		// 객체명 : storeDTO, storeDAO
		// 전체 검색 시에는 DTO를 null 값으로 보내기
		StoreDTO storeDTO = new StoreDTO();
		StoreDAO storeDAO = new StoreDAO();
		System.out.println("	log : SearchStoreAction.java(94)	StoreDTO, DAO 생성");

		// V에서 받아온 parameter들을 전부 null 값이면
		if(storeClosed == null && storeName == null && storeMenuDatas == null && storePayment == null) {
			// StoreDTO에 condition 값 입력
			// condtion : ALL_NUM_CNT_SELECTONE
			storeDTO.setCondition("ALL_NUM_CNT_SELECTALL");
			System.out.println("	log : SearchStoreAction.java(101)	"
					+ "StoreDTO.setCondition : "+ storeDTO.getCondition());
		}
		// 하나라도 값이 있다면
		else {
			// DTO 세팅

			// StoreDTO에 condition 값 입력
			// condtion : FILTER_NUM_CNT_SELECTONE
			storeDTO.setCondition("FILTER_NUM_CNT_SELECTALL");
			System.out.println("	log : SearchStoreAction.java(111)	"
					+ "StoreDTO.setCondition : "+ storeDTO.getCondition());

			// DTO에 View에서 입력받은 모든 값을 입력
			// 영업중 여부
			storeDTO.setStoreClosed(storeClosed);
			// 가게 이름
			storeDTO.setStoreName(storeName);

			// 가게 메뉴
			// StoreDTO에 storeMenuDatas 넣음
			storeDTO.setStoreMenu(storeMenuDatas);

			// 결제방식
			// storePaymentSet 메서드를 사용하여 DTO에 세팅
			StoreMethods.storePaymentSet(storePayment, storeDTO);
		}
		System.out.println("	log : SearchStoreAction.java(128)	storeDTO : "+ storeDTO);

		// storeDAO.selectAll으로 검색결과 갯수 가져오기
		ArrayList<StoreDTO> pkDatas = storeDAO.selectAll(storeDTO);
		System.out.println("	log : SearchStoreAction.java(132)	pkDatas : "+ pkDatas);

		// 결과물 총 걧수 기록
		int totalSize = pkDatas.size();
		System.out.println("	log : SearchStoreAction.java(136)	pkDatas.size : "+ pkDatas.size());

		// 검색 결과가 존재한다면
		// 만약 검색 결과 사이즈가 0보다 크다면
		if(totalSize > 0) {
			// PK 배열리스트(ArrayList<Integer>) 만들기
			ArrayList<Integer> pkList = new ArrayList<Integer>();
			for(StoreDTO data : pkDatas) {
				pkList.add(data.getStoreNum());
			}
			System.out.println("	log : SearchStoreAction.java(143)	pkList : "+ pkList);

			// 배열 리스트를 만들어서 storeDTO에 넣기
			storeDTO.setStoreNumCNT(pkList);
			System.out.println("	log : SearchStoreAction.java(147)	storeDTO.setStoreNumCNT(pkList) 완료");

			// 메서드
			PaginationUtils.setPagination(currentPage, pageSize, totalSize, storeDTO);
			System.out.println("	log : SearchStoreAction.java(152)	페이지네이션 완료");
			System.out.println("	log : SearchStoreAction.java	storeDTO : "+ storeDTO);

			// StoreDTO에 condition 값 넣음
			// condtion : SEARCH_STORE_SELECTALL
			storeDTO.setCondition("SEARCH_STORE_SELECTALL");
			System.out.println("	log : SearchStoreAction.java(158)	"
					+ "StoreDTO.setCondition : "+ storeDTO.getCondition());

			// storeDAO.selectAll로 가게의 전체 데이터 가져오기
			// datas에 M에게 받은 데이터 넣기
			datas = storeDAO.selectAll(storeDTO);
			System.out.println("	log : SearchStoreAction.java(164)	StoreDAO.selectAll 완료");
			System.out.println("	log : SearchStoreAction.java	datas : "+ datas);


		}
		// 만약 결과가 없다면
		else {
			datas = null;
		}

		// V에게 데이터 전달 ===============================================================================
		// datas 전달
		// request.setAttribute사용
		// 속성명 : storeList
		request.setAttribute("stores", datas);
		System.out.println("	log : SearchStoreAction.java(171)	V에게 datas 전달(storeList)");


		// V에게 받은 키워드와 storeDTO, storeMenuDTO를 전달
		request.setAttribute("keyword", storeName);
		request.setAttribute("storeData", storeDTO);
		// 만약 전달할 sotreMenuDatas가 null이 아니라면
		if(storeMenuDatas != null) {
			// V에게 전달할 menu 데이터를 DTO형식으로 변경
			StoreMenuDTO storeMenuDTO = new StoreMenuDTO();
			StoreMethods.storeMenuSet(storeMenuDatas, storeMenuDTO);
			System.out.println("	log : SearchStoreAction.java(184)	storeMenuDTO : "+ storeMenuDTO);
			request.setAttribute("storeMenu", storeMenuDTO);
		}
		System.out.println("	log : SearchStoreAction.java(190)	V에게 keywordstoreClosedV, storeMenu, storePayment 전달");

		// CNT 데이터 전송 부분 ==========================================================================
		// 새로운 StoreDTO,StoreMenuDTO, StorePaymentDTO 생성
		// 객체명 : storeCNT, storeMenuCNT, storePaymentCNT
		StoreDTO storeCNTDTO = new StoreDTO();
		StoreMenuDTO storeMenuCNTDTO = new StoreMenuDTO();
		StorePaymentDTO storePaymentCNTDTO = new StorePaymentDTO();
		System.out.println("	log : SearchStoreAction.java(182)	"
				+ "storeCNT, storeMenuCNT, storePaymentCNT 생성(dto)");

		// CNT 결과를 StoreMethods.storeCnt() 메서드를 사용하여 받아오기
		int storeCNT = StoreMethods.storeCntOne(storeCNTDTO);
		System.out.println("	log : SearchStoreAction.java(189)	StoreCNT : "+ storeCNT);
		
		ArrayList<Integer> storeMenuCNT = StoreMethods.storeCnt(storeMenuCNTDTO);
		System.out.println("	log : SearchStoreAction.java(192)	StoreMenuCNT : "+ storeMenuCNT);
		
		ArrayList<Integer> storePaymentCNT = StoreMethods.storeCnt(storePaymentCNTDTO);
		System.out.println("	log : SearchStoreAction.java(195)	StorePaymentCNT : "+ storePaymentCNT);

		// View에게 storeCNT, storeMenuCNT, storePaymentCNT 전달
		// 속성명 : storeCNT, storeMenuCNT, storePaymentCNT
		request.setAttribute("storeCNT", storeCNT);
		request.setAttribute("storeMenuCNT", storeMenuCNT);
		request.setAttribute("storePaymentCNT", storePaymentCNT);
		System.out.println("	log : SearchStoreAction.java(202)	"
				+ "request.setAttirbute storeCNT, storeMenuCNT, storePaymentCNT 완료");

		// 페이지네이션 부분 ==============================================================================
		// 데이터 사이즈가 0이 아니라면 페이지네이션 실행
		// datas가 null이 아니거나 datas.size가 0이 아니라면
		if(datas != null) {
			// View에게 보낼 총 페이지 갯수
			// int 변수명 : totalPages
			// PaginationUtils.calTotalPages(전체 데이터 갯수, 페이지 크기)의 반환값을 넣음
			int totalPages = PaginationUtils.calTotalPages(totalSize, pageSize);
			System.out.println("	log : SearchStoreAction.java(227)	totalPages : "+ totalPages);

			// View에게 현재 페이지 번호 currentPage,  totalPages 보내기
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("totalPages", totalPages);
		}

		// forward 부분 ===================================================================================
		// ActionForward 생성
		// 객체명 : forward
		ActionForward forward = new ActionForward();

		// forward 객체에 이동방식과 이동할 페이지 넣기
		// 이동방식 : 데이터가 있으므로 false
		forward.setRedirect(false);
		// 이동할 페이지 : SearchStore.jsp
		forward.setPath("storeList.jsp");

		System.out.println("	log : SearchStoreAction.java(229)	forwardPath : "+ forward.getPath());
		System.out.println("	log : SearchStoreAction.java	종료");
		// forward로 반환
		return forward;
	}
}