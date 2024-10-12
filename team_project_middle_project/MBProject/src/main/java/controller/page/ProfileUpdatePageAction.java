package controller.page;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ProfilePicUpload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

public class ProfileUpdatePageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : ProfileUpdatePageAction.java		시작");

		// session 사용을 위한 선언
		HttpSession session = request.getSession();

		// session에서 memberPK 값 받아오기
		int memberPK = (int)session.getAttribute("memberPK");
		System.out.println("	log : ProfileUpdatePageAction.java		session 값 가져오기");
		System.out.println("	log : ProfileUpdatePageAction.java		session(memberPK) : "+ memberPK);

		// MemberDTO memberDTO, DAO memberDAO 객체 new 생성
		MemberDTO memberDTO = new MemberDTO();
		MemberDAO memberDAO = new MemberDAO();
		// memberDTO에 condition : MEMBER_INFO_SELECTONE
		memberDTO.setCondition("MEMBER_INFO_SELECTONE");
		System.out.println("	log : ProfileUpdatePageAction.java		condition : "+ memberDTO.getCondition());
		memberDTO.setMemberNum(memberPK);
		System.out.println("	log : ProfileUpdatePageAction.java		memberDTO에 set데이터 완료");

		// MemberDAO.selectOne 요청
		// 결과값(MemberDTO) 받아오기
		// memberDTO에 저장
		memberDTO = memberDAO.selectOne(memberDTO);
		System.out.println("	log : ProfileUpdatePageAction.java		memberDAO.selectOne 요청");
		System.out.println("	log : ProfileUpdatePageAction.java		selectOne 결과 : "+ memberDTO);

		// 만약 memberDTO가 존재한다면
		if(memberDTO != null) {
			// 프로필 사진에 주소를 붙여서 전송
			memberDTO.setMemberProfileWay(ProfilePicUpload.addPATH(memberDTO.getMemberProfileWay()));
			System.out.println("	log : ProfileUpdatePageAction.java		PATH+memberDTO.getMemberProfileWay() 결과 : "+ memberDTO.getMemberProfileWay());
		}

		// View에게 meberDTO 전송
		// request.setAttribute에 memberDTO 저장
		request.setAttribute("member", memberDTO);
		System.out.println("	log : ProfileUpdatePageAction.java		V에게 selectOne 결과 loginMemberData를 보냄");

		// forward 객체 생성
		ActionForward forward = new ActionForward();

		// 이동 방법 : 데이터가 전달되므로 redirect(false)
		// 이동 페이지 : update_mypage.jsp
		forward.setRedirect(false);
		forward.setPath("updateProfile.jsp");

		System.out.println("	log : ProfileUpdatePageAction.java		forwardPath : "+ forward.getPath());
		System.out.println("	log : ProfileUpdatePageAction.java		종료");
		return forward;
	}
}