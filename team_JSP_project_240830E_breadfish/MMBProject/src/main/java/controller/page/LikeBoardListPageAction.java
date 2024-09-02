package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LikeBoardListPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// session에서 회원번호(PK)값 받아옴
		HttpSession session = request.getSession();
		int memberNum = (int)session.getAttribute("memberPK");
		System.out.println("	log : MemberDeleteAction.java		session 값 가져오기");
		System.out.println("	log : MemberDeleteAction.java		session(memberPK)"+session.getAttribute("memberPK"));
		// 중프 때 작성
		
		
		return null;
	}

}
