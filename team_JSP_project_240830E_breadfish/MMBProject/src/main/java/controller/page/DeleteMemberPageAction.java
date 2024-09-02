package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ProfilePicUpload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

public class DeleteMemberPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : DeleteMemberPageAction.java		시작");

		// 프로필사진을 담을 변수 선언
		String profilePic = ProfilePicUpload.loginProfilePic(request);

		// View에서 결과 출력용
		// request.setAttribute에 flag 저장
		request.setAttribute("memberProfileWay", profilePic);
		System.out.println("	log : DeleteMemberPageAction.java		V에게 update 결과 result를 보냄");

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