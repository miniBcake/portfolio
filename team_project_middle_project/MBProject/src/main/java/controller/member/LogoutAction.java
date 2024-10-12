package controller.member;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutAction implements Action{
	// 로그아웃

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : LogoutAction.java		시작");
		// session memberPK, memberNickName, memberRole, temp 제거
		// invalidate 사용
		HttpSession session = request.getSession();
		session.invalidate();
		System.out.println("	log : LogoutAction.java		session 전체 삭제");

		// 페이지 이동
		// forward 객체 생성
		// 이동 방법 : 페이지 이동이므로 (true)
		// 이동 페이지 : main.do
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("mainPage.do");
		System.out.println("	log : LogoutAction.java		forwardPath : "+ forward.getPath());
		
		System.out.println("	log : LogoutAction.java		종료");
		return forward;
	}
}