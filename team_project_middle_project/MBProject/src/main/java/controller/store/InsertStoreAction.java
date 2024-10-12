package controller.store;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.StoreMethods;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.StoreDAO;
import model.dao.StoreMenuDAO;
import model.dao.StorePaymentDAO;
import model.dao.StoreWorkDAO;
import model.dto.StoreDTO;
import model.dto.StoreMenuDTO;
import model.dto.StorePaymentDTO;
import model.dto.StoreWorkDTO;


public class InsertStoreAction implements Action{
	// 가게 추가
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : InsertStoreAction.java	시작");
		// V에서 request.getParameter로 데이터를 받아옴

		// 가게
		// 가게 이름, 가게 주소, 가게 상세 주소, 연락처, 메뉴
		// String 변수명(가게 이름) : storeName
		String storeName = request.getParameter("storeName");
		System.out.println("	log : InsertStoreAction.java	storeName : "+storeName);
		// String 변수명(가게 주소) : storeAddress
		String storeAddress = request.getParameter("storeAddress");
		System.out.println("	log : InsertStoreAction.java	storeAddress : "+storeAddress);
		// String 변수명(가게 상세 주소) : storeAddressDetail
		String storeAddressDetail = request.getParameter("storeAddressDetail");
		System.out.println("	log : InsertStoreAction.java	storeAddressDtail : "+storeAddressDetail);
		// String 변수명(연락처) : storeContact
		String storeContact = request.getParameter("storePhone");
		System.out.println("	log : InsertStoreAction.java	storeContact : "+storeContact);
		// String 변수명(영업상태) : storeClosed = "N"
		String storeClosed = "N";
		System.out.println("	log : InsertStoreAction.java	storeClosed : "+storeClosed);

		// StoreDTO, DAO 생성
		// 객체명 : storeDTO, storeDAO
		StoreDTO storeDTO = new StoreDTO();
		StoreDAO storeDAO = new StoreDAO();
		System.out.println("	log : InsertStoreAction.java	storeDTO 객체 생성");		

		// storeDTO.set으로 가게 이름, 가게 기본 주소, 가게 상세 주소, 연락처, 영업상태를 입력
		storeDTO.setStoreName(storeName);
		storeDTO.setStoreDefaultAddress(storeAddress);
		storeDTO.setStoreDetailAddress(storeAddressDetail);
		storeDTO.setStorePhoneNum(storeContact);
		storeDTO.setStoreClosed(storeClosed);
		System.out.println("	log : InsertStoreAction.java	storeDTO.set 완료");		

		// storeDAO.insert를 사용하여 storeDTO 등록
		// boolean flag로 insert 결과 반환값을 저장
		boolean flag = storeDAO.insert(storeDTO);
		System.out.println("	log : InsertStoreAction.java	storeDTO.insert : "+flag);

		// ActionForward 객체 생성
		// 객체명 : forward
		// 이동방식 : 데이터를 전달하므로 false
		// 이동할 페이지 : failInfo.do
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("failInfo.do");

		// 가게 등록에 실패 시
		// 가게 등록 실패 시 가는 경로
		String path = "insertStorePage.do";
		// flag가 false면
		if(!flag) {
			// 실패시 request.setAttribute(path, msg)를 해주는 메서드
			failSetting(request, path, "가게 등록에 실패했습니다.");

			// forward 값 반환
			System.out.println("	log : InsertStoreAction.java	종료");
			System.out.println("	log : InsertStoreAction.java	종료");
			return forward;
		}

		// 등록된 storeDTO를 통해 가게번호(PK) 값 가져오기
		// storeDTO에 condition 값 변경
		// condition : STORE_NEW_SELECTONE
		storeDTO.setCondition("STORE_NEW_SELECTONE");
		System.out.println("	log : InsertStoreAction.java	storeDTO.getCondition : "+storeDTO.getCondition());

		// storeDAO.selectOne을 가게번호(PK) 값 가져오기
		storeDTO = storeDAO.selectOne(storeDTO);
		System.out.println("	log : InsertStoreAction.java	storeDAO.selectOne 완료");
		System.out.println("	log : InsertStoreAction.java	storeDAO : "+storeDTO);
		// int storeNum에 반환값 저장
		int storeNum = storeDTO.getStoreMaxNum();
		System.out.println("	log : InsertStoreAction.java	storeNum : "+storeNum);


		// 가게 영업
		// String[] 변수명(요일) : storeWorkWeek
		String[] storeWorkWeek = request.getParameterValues("businessDays");
		System.out.println("	log : InsertStoreAction.java	storeWorkWeek : "+storeWorkWeek);
		// String 변수명(영업시작 시간) : storeStartTime
		String storeStartTime = request.getParameter("startTime");
		System.out.println("	log : InsertStoreAction.java	storeStartTime : "+storeStartTime);
		// String 변수명(영업종료 시간) : storeEndTime
		String storeEndTime = request.getParameter("endTime");
		System.out.println("	log : InsertStoreAction.java	storeEndTime : "+storeEndTime);

		// StoreWorkDTO, DAO 생성
		// 객체명 : storeWorkDTO, storeWorkDAO
		StoreWorkDTO storeWorkDTO = new StoreWorkDTO();
		StoreWorkDAO storeWorkDAO = new StoreWorkDAO();
		System.out.println("	log : InsertStoreAction.java	storeWorkDTO 객체 생성");

		// storeWorkDTO.set으로 가게번호(PK), 요일, 영업시작 시간, 영업 종료 시간 넣기
		// storeWorkWeek만큼 반복
		for(String data : storeWorkWeek) {
			System.out.println("	log : InsertStoreAction.java	data : "+data);
			storeWorkDTO.setStoreNum(storeNum);
			storeWorkDTO.setStoreWorkWeek(data);
			storeWorkDTO.setStoreWorkOpen(storeStartTime);
			storeWorkDTO.setStoreWorkClose(storeEndTime);
			System.out.println("	log : InsertStoreAction.java	storeWorkDTO.set 완료");

			// storeWorkDAO.insert로 storeWorkDTO 등록
			// flag에 insert 결과 반환값을 저장
			flag = storeWorkDAO.insert(storeWorkDTO);
			System.out.println("	log : InsertStoreAction.java	storeWorkDTO.update : "+flag);

			// 가게 등록에 실패 시
			// flag가 false일 때
			if(!flag) {
				// 실패시 request.setAttribute(path, msg)를 해주는 메서드
				failSetting(request, path, "가게 영업 등록에 실패했습니다.");

				// forward 값 반환
				System.out.println("	log : InsertStoreAction.java	종료");
				System.out.println("	log : InsertStoreAction.java	종료");
				return forward;
			}
		}


		// 가게 메뉴
		// 가게 메뉴 받기 (팥/슈크림, 야채/김치/만두, 미니, 고구마, 아이스크림/초코, 치즈, 패스츄리, 기타)
		//String[] 변수명 : storeMenu
		String[] storeMenu = request.getParameterValues("businessMenus");

		// StoreMenuDTO, DAO 생성
		// 객체명 : storeMenuDTO, storeMenuDAO
		StoreMenuDTO storeMenuDTO = new StoreMenuDTO();
		StoreMenuDAO storeMenuDAO = new StoreMenuDAO();
		System.out.println("	log : InsertStoreAction.java	storeMenuDTO 객체 생성");

		// 가게 메뉴 체크박스 데이터 전달을 위한 배열리스트 생성
		// ArrayList<String> 변수명 : storeMenuDatas
		// storeMenuValue를 사용하여 메뉴 값을 받아옴
		ArrayList<String> storeMenuDatas = StoreMethods.storeMenuValue(storeMenu);
		System.out.println("	log : InsertStoreAction.java	storeMenuValue로 storeMenu를 storeMenuDatas로 변경");
		System.out.println("	log : InsertStoreAction.java	storeMenuDatas : "+ storeMenuDatas);
		
		// insert할 땐 null 값을 N으로 변경
		// storeMenuValuePlusN을 사용하여 N 추가
		storeMenuDatas = StoreMethods.storeListValuePlusN(storeMenuDatas);
		System.out.println("	log : InsertStoreAction.java	storeMenuDatas에 N값 추가");
		System.out.println("	log : InsertStoreAction.java	storeMenuDatas : "+ storeMenuDatas);

		// storeMenuDTO.set가게번호 하기
		storeMenuDTO.setStoreNum(storeNum);
		// storeMenuSet 메서드로 storeMenuDTO.set 완료
		StoreMethods.storeMenuSet(storeMenuDatas, storeMenuDTO);
		System.out.println("	log : InsertStoreAction.java	storeMenuDTO.set 완료");
		System.out.println("	log : InsertStoreAction.java	storeMenuDTO : "+ storeMenuDTO);

		// storeMenuDAO.insert로 storeMenuDTO 등록
		// flag에 insert 결과 반환값을 저장
		flag = storeMenuDAO.insert(storeMenuDTO);
		System.out.println("	log : InsertStoreAction.java	storeMenuDTO.update : "+flag);

		// 가게 등록에 실패 시
		// flag가 false일 때
		if(!flag) {
			// 실패시 request.setAttribute(path, msg)를 해주는 메서드
			failSetting(request, path, "가게 메뉴 등록에 실패했습니다.");

			// forward 값 반환
			System.out.println("	log : InsertStoreAction.java	종료");
			System.out.println("	log : InsertStoreAction.java	종료");
			return forward;
		}


		// 결제 방식 ================================================================================
		// 결제방식 받기 (현금, 카드, 계좌이체)
		// String[] 변수명 : storePayment
		String[] storePayment = request.getParameterValues("businessPayments");
		System.out.println("	log : InsertStoreAction.java	storePayment : "+ storePayment);

		// StorePaymentDTO, DAO 객체 생성
		// 객체명 storePaymentDTO, storePaymentDAO
		StorePaymentDTO storePaymentDTO = new StorePaymentDTO();
		StorePaymentDAO storePaymentDAO = new StorePaymentDAO();	
		System.out.println("	log : InsertStoreAction.java	storePaymentDTO 객체 생성");

		// 결제방식
		// storePaymentDTO.set가게번호 하기
		storePaymentDTO.setStoreNum(storeNum);
		// storePaymentSet 메서드를 사용하여 DTO에 세팅
		StoreMethods.storePaymentSet(storePayment, storePaymentDTO);
		System.out.println("	log : InsertStoreAction.java	storePaymentDTO.set 완료");
		System.out.println("	log : InsertStoreAction.java	storePaymentDTO : "+ storePaymentDTO);
		
		// storePaymentSetN 메서드를 사용하여 null 값에 N값 넣기
		StoreMethods.storePaymentSetPlusN(storePaymentDTO);
		System.out.println("	log : InsertStoreAction.java	storePaymentDTO.set 완료");
		System.out.println("	log : InsertStoreAction.java	storePaymentDTO : "+ storePaymentDTO);

		// storePaymentDAO.insert로 storePaymentDTO 등록
		// flag에 insert 결과 반환값을 저장
		flag = storePaymentDAO.insert(storePaymentDTO);
		System.out.println("	log : InsertStoreAction.java	storePaymentDTO.update : "+flag);

		// 가게 등록에 실패 시
		// flag가 false일 때
		if(!flag) {
			// 실패시 request.setAttribute(path, msg)를 해주는 메서드
			failSetting(request, path, "가게 결제방식 등록에 실패했습니다.");

			// forward 값 반환
			System.out.println("	log : InsertStoreAction.java	종료");
			return forward;
		}

		// insert 성공으로 forwad 값 변경
		// 이동방식 : 전송할 데이터가 없으므로 true
		// 이동할 페이지 : mainPage.do
		forward.setRedirect(true);
		forward.setPath("mainPage.do");

		// forward 객체 반환
		System.out.println("	log : InsertStoreAction.java	forward.getPath : "+forward.getPath());
		System.out.println("	log : InsertStoreAction.java	성공");
		return forward;
	}

	private void failSetting(HttpServletRequest request, String path, String msg) {
		// V에게 결과 보내기
		// request.setAttribute로 msg, 이동경로 값 전달.
		// msg : "가게 등록에 실패했습니다."
		// 이동경로 : 가게 등록 페이지 이동
		request.setAttribute("msg", msg);
		request.setAttribute("path", path);

		// forward 값 반환
		System.out.println("	log : InsertStoreAction.java	request.getPath : "+path);
	}
}