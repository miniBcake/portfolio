package controller.page;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ProfilePicUpload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

public class NewMemberListPageAction implements Action{
	// 신규회원목록 출력

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : NewMemberListPageAction.java		시작");

		// 프로필사진을 담을 변수 선언
		String profilePic = ProfilePicUpload.loginProfilePic(request, response);

		// 데이터를 전달해줄 ArrayList<MemberDTO> datas 객체 생성
		ArrayList<MemberDTO> datas = new ArrayList<MemberDTO>();

		// MemberDTO, MemberDAO 객체 생성
		MemberDTO memberDTO2 = new MemberDTO();
		MemberDAO memberDAO2 = new MemberDAO();
		// MemberDTO에 condition 입력 : NEWMEMBERS
		memberDTO2.setCondition("NEWMEMBERS_SELECTALL");
		System.out.println("	log : NewMemberListPageAction.java		condition : "+ memberDTO2.getCondition());

		// MemberDAO.selectAll로 신규회원 목록 받아오기
		// datas 리스트에 받기
		datas = memberDAO2.selectAll(memberDTO2);
		System.out.println("	log : NewMemberListPageAction.java		memberDAO.selectAll 요청");
		System.out.println("	log : NewMemberListPageAction.java		selectAll 결과 : "+ memberDTO2);

		// View에게 전달
		// request.setAttribute에 flag 저장
		request.setAttribute("memberProfileWay", profilePic);
		System.out.println("	log : NewMemberListPageAction.java		V에게 update 결과 result를 보냄");
		// request.setAttribute에 datas저장
		request.setAttribute("newMember", datas);
		System.out.println("	log : NewMemberListPageAction.java		V에게 selectAll 결과 보냄");

		// 페이지 이동
		// forward 객체 생성
		ActionForward forward = new ActionForward();
		// 이동 방법 : 데이터가 있으므로 forward (false)
		// 이동 페이지 : newMember.jsp
		forward.setRedirect(false);
		forward.setPath("newMember.jsp");

		System.out.println("	log : NewMemberListPageAction.java		forwardPath : "+ forward.getPath());
		System.out.println("	log : NewMemberListPageAction.java		종료");
		return forward;
	}
}