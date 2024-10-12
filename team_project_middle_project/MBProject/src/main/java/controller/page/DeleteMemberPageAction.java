package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteMemberPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : DeleteMemberPageAction.java		시작");

		// forward 객체 생성
		ActionForward forward = new ActionForward();

		// 이동 방법 : 전송할 데이터가 있으므로 redirect(false)
		// 이동 페이지 : deleteAccount.jsp
		forward.setRedirect(false);
		forward.setPath("deleteAccount.jsp");

		System.out.println("	log : DeleteMemberPageAction.java		forwardPath : "+ forward.getPath());
		System.out.println("	log : DeleteMemberPageAction.java		종료");
		return forward;
	}
}