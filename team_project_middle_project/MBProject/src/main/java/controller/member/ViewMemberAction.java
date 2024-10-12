package controller.member;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ProfilePicUpload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

public class ViewMemberAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : ViewMemberPageAction.java		시작");

		// 프로필사진을 담을 변수 선언
		String profilePic = ProfilePicUpload.loginProfilePic(request, response);

		// View에게서 비밀번호 데이터 받기
		// String request.getParameter(비밀번호)
		int memberPK = Integer.parseInt(request.getParameter("memberPK"));
		System.out.println("	log : ViewMemberPageAction.java		memberPK : " + memberPK);

		// MemberDTO memberDTO, DAO memberDAO 객체 new 생성
		MemberDTO memberDTO = new MemberDTO();
		MemberDAO memberDAO = new MemberDAO();
		// memberDTO에 condition : MEMBER_INFO_SELECTONE 넣어주기
		// memberDTO에 회원번호(PK)값 넣어주기
		// memberDTO에 비밀번호 값 넣어주기
		memberDTO.setCondition("MEMBER_INFO_SELECTONE"); // 나중 수정 예상
		System.out.println("	log : ViewMemberPageAction.java		condition : "+ memberDTO.getCondition());
		memberDTO.setMemberNum(memberPK);
		System.out.println("	log : ViewMemberPageAction.java		memberDTO에 set데이터 완료");

		// MemberDAO.selectOne 요청
		// 결과값(MemberDTO) 받아오기
		// memberDTO에 결과값 넣기
		memberDTO = memberDAO.selectOne(memberDTO);
		System.out.println("	log : ViewMemberPageAction.java		memberDAO.selectOne 요청");
		System.out.println("	log : ViewMemberPageAction.java		selectOne 결과 : "+ memberDTO);


		// 해당번호의 회원이 존재하지 않는다면
		if(memberDTO == null) {
			System.out.println("	log : ViewMemberPageAction.java		해당 회원번호의 회원은 존재하지 않음");
			// HTTP 에러 응답을 보내는 메서드 (404 오류를 보냄)
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 결과값이 있을 때 밑의 코드 진행
		// memberDTO 값이 존재할 때(null이 아닐 때)
		System.out.println("	log : ViewMemberPageAction.java		해당 회원번호의 회원 존재");

		// 프로필 사진에 주소를 붙여서 전송
		memberDTO.setMemberProfileWay(ProfilePicUpload.addPATH(memberDTO.getMemberProfileWay()));
		System.out.println("	log : ViewMemberPageAction.java		PATH+memberDTO.getMemberProfileWay() 결과 : "+ memberDTO.getMemberProfileWay());

		// View에게 전달
		// request.setAttribute에 flag 저장
		request.setAttribute("memberProfileWay", profilePic);
		System.out.println("	log : ViewMemberPageAction.java		V에게 update 결과 result를 보냄");
		// request.setAttribute에 memberDTO 저장
		request.setAttribute("member", memberDTO);
		System.out.println("	log : ViewMemberPageAction.java		V에게 selectOne 결과 loginMemberData를 보냄");


		// 페이지 이동 (결과에 따라 다른 페이지)
		// forward 객체 생성
		// 이동 방법 : 데이터가 있으므로 false
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		// 이동할 페이지 : mainpage.do
		forward.setPath("viewMember.jsp");

		System.out.println("	log : ViewMemberPageAction.java		forwardPath : "+ forward.getPath());
		System.out.println("	log : ViewMemberPageAction.java		종료");
		return forward;
	}

}
