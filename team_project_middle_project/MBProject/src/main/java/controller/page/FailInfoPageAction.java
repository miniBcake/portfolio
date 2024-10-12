package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FailInfoPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : FailInfoPageAction		시작");
		// 스크립트 페이지 이동
		
		// forward 객체 생성
		ActionForward forward = new ActionForward();
		
		// 이동 방법 : 데이터가 없으므로 redirect (false)s
		// 이동 페이지 : failInfo.jsp
		forward.setRedirect(false);
		forward.setPath("failInfo.jsp");
		
		System.out.println("	log : FailInfoPageAction		forwardPath : "+ forward.getPath());
		System.out.println("	log : FailInfoPageAction		종료");
		
		return forward;
	}
}