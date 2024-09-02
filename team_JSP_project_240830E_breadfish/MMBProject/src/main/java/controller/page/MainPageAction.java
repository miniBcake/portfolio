package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MainPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : MainPageAction.java		시작");
		// 메인 페이지 이동
		// forward 객체 생성
		ActionForward forward = new ActionForward();
		
		// 이동 방법 : 데이터가 없으므로 redirect (true)
		// 이동 페이지 : main.jsp
		forward.setRedirect(true);
		forward.setPath("main.jsp");
		
		System.out.println("	log : MainPageAction.java		forwardPath : "+ forward.getPath());
		System.out.println("	log : MainPageAction.java		종료");
		return forward;
	}
}