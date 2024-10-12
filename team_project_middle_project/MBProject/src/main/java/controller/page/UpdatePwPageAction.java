package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdatePwPageAction implements Action{
	// 비밀번호를 변경하기 위한 페이지로 이동
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 페이지 이동만 실행

		// ActionForward 객체 생성
		// 객체명 : forward
		ActionForward forward = new ActionForward();

		// 이동 방법 : 페이지 이동이므로 true
		// 이동 페이지 : findPw.jsp
		forward.setRedirect(true);
		forward.setPath("findPw.jsp");

		// forward 객체 반환
		System.out.println("	log : UpdatePwPageAction.java	forward.getPath : "+forward.getPath());
		System.out.println("	log : UpdatePwPageAction.java	종료");
		return forward;
	}
}