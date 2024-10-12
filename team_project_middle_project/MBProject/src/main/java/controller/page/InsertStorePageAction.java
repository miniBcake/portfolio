package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class InsertStorePageAction implements Action{
	// 가게 추가 페이지로 이동
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : InsertStorePageAction		시작");
		// V에게 받아올 데이터 없음
		// 페이지 이동

		// ActionForward 객체 생성
		// 객체명 : forward
		ActionForward forward = new ActionForward();

		// forward 객체에 이동방식과 이동할 페이지 넣기
		// 이동방식 : 페이지 이동이므로 true
		// 이동할 페이지 : storeRegister.jsp
		forward.setRedirect(true);
		forward.setPath("storeRegister.jsp");
		
		System.out.println("	log : InsertStorePageAction		forwardPath : "+ forward.getPath());
		System.out.println("	log : InsertStorePageAction		종료");

		// forward 객체 반환
		return forward;
	}
}