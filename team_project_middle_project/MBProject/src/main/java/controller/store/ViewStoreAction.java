package controller.store;

import java.io.IOException;
import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.StoreMethods;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.StoreDTO;
import model.dto.StoreMenuDTO;
import model.dto.StorePaymentDTO;
import model.dto.StoreWorkDTO;

public class ViewStoreAction implements Action{
	// 가게 상세정보 조회
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : StorePageAction.java	시작");
		// V에게 가져오는 데이터 있음
		// 해당 가게의 PK 값을 받아옴
		// int 변수명 :  storeNum
		// 형변환 필요
		int storeNum = Integer.parseInt(request.getParameter("storeNum"));
		System.out.println("	log : StorePageAction.java	storeNum : "+storeNum);

		// V에게 전달할 데이터 있음 : 변경하려는 가게 정보

		// StoreDTO 객체를 생성하고 입력한 번호와 일치하는 데이터를 찾아옴
		// StoreMethods.storeDetials 메서드 사용
		// 객체명 : storeDTO
		StoreDTO storeDTO = StoreMethods.storeDetails(storeNum);
		System.out.println("	log : StorePageAction.java	storeDTO : "+storeDTO);
		
		// 만약 입력받은 가게 번호와 일치하는 가게가 없을 경우
		// 해당 정보가 없는 경우 밑의 selectOne 결과는 존재하지 않기 때문에 한 번 검사ㅐ
		// storeDTO가 null이라면
		if(storeDTO == null) {
			// HTTP 에러 응답을 보내는 메서드 (404 오류를 보냄)
			System.out.println("	log : StorePageAction.java	storeDTO가 존재하지 않음");
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// StoreWorkDTO
		// 객체명 : storeWorkDTO
		// StoreMethods.storeDetails(storeWorkDTO, storeNum)
		ArrayList<StoreWorkDTO> storeWorkDatas = StoreMethods.storeWorkDetails(storeNum);
		System.out.println("	log : StorePageAction.java	storeWorkDTO : "+storeWorkDatas);
		
		// StoreMenuDTO
		// 객체명 : storeMenuDTO
		// StoreMethods.storeDetails(storeMenuDTO, storeNum)
		StoreMenuDTO storeMenuDTO = StoreMethods.storeMenuDetails(storeNum);
		System.out.println("	log : StorePageAction.java	storeMenuDTO : "+storeMenuDTO);
		// V에서 출력할 수 있도록 리스트로 변환
		ArrayList<String> storeMenuList = StoreMethods.storeMenuList(storeMenuDTO);
		System.out.println("	log : StorePageAction.java	storeMenuList : "+storeMenuList);
		
		// StorePaymentDTO
		// 객체명 : storePaymentDTO
		// StoreMethods.storeDetails(storePaymentDTO, storeNum)
		StorePaymentDTO storePaymentDTO = StoreMethods.storePaymentDetails(storeNum);
		System.out.println("	log : StorePageAction.java	storePaymentDTO : "+storePaymentDTO);
		// V에서 출력할 수 있도록 리스트로 변환
		ArrayList<String> storePaymList = StoreMethods.storePaymentList(storePaymentDTO);
		System.out.println("	log : StorePageAction.java	storePaymList : "+storePaymList);
		
		String[] allDays = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"}; 
		
		// V에게 데이터 전달
		// V에게 request.setAttribute로 storeDTO, storeWorkDTO, storeMenuList, storePaymentDTO, allDays 전달
		// 속성명 : storeData, storeWorkData, storeMenuData, storePaymentData, allDays
		request.setAttribute("storeData", storeDTO);
		request.setAttribute("storeWorkData", storeWorkDatas);
		request.setAttribute("storeMenuData", storeMenuList);
		request.setAttribute("storePaymentData", storePaymList);
		request.setAttribute("allDays", allDays);
		System.out.println("	log : StorePageAction.java	V에게 storeDTO, storeWorkDTO, storeMenuDTO, storePaymentDTO 전송 완료");

		// ActionForward 객체 생성
		// 객체명 : forward
		// 이동 방법 : 데이터가 있으므로 false
		// 이동 페이지 : store.jsp
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("store.jsp");

		// forward 객체 반환
		System.out.println("	log : StorePageAction.java	forward.getPath : "+forward.getPath());
		System.out.println("	log : StorePageAction.java	종료");
		return forward;
	}
}