package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : LoginPageAction.java		시작");
		// forward 객체 생성
		ActionForward forward = new ActionForward();

		// 이동 방법 : 페이지 이동이므로 redirect(true)
		// 이동 페이지 : signup.jsp
		forward.setRedirect(true);
		forward.setPath("login.jsp");

		System.out.println("	log : LoginPageAction.java		forwardPath : "+ forward.getPath());
		System.out.println("	log : LoginPageAction.java		종료");
		return forward;
	}
}