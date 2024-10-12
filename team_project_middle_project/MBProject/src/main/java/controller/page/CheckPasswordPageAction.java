package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CheckPasswordPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : CheckPasswordPageAction.java		시작");
		// session 사용을 위한 선언
		HttpSession session = request.getSession();

		// forward 객체 생성
		ActionForward forward = new ActionForward();

		// 로그인이 안 된 경우 로그인 페이지로 이동
		// 만약 session 값이 null이라면
		if(session.getAttribute("memberPK") == null) {
			// 로그인 페이지로 이동
			// 이동 방법 : 페이지 이동이므로 redirect(true)
			// 이동 페이지 : loginPage.do 
			forward.setRedirect(true);
			forward.setPath("loginPage.do");
			return forward;
		}

		// 이동 방법 : 페이지 이동이므로 redirect(true)
		// 이동 페이지 : checkPW.jsp
		forward.setRedirect(true);
		forward.setPath("checkPw.jsp");

		System.out.println("	log : CheckPasswordPageAction.java		forwardPath : "+ forward.getPath());
		System.out.println("	log : CheckPasswordPageAction.java		종료");
		return forward;
	}
}