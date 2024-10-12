package controller.member;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

public class CheckPasswordAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : CheckPasswordAction.java		시작");

		System.out.println("	log : CheckPasswordAction.java		사용자 입력 데이터 받아오기");
		// View에게 받을 데이터 비밀번호
		// 비밀번호(password) 데이터 받기
		String password = request.getParameter("password");
		System.out.println("	log : CheckPasswordAction.java		password : " + password);

		// session에서 memberPK 값 받아오기
		HttpSession session = request.getSession();
		int memberPK = (int)session.getAttribute("memberPK");
		System.out.println("	log : CheckPasswordAction.java		session 값 가져오기");
		System.out.println("	log : CheckPasswordAction.java		session(memberPK) : "+ memberPK);

		// MemberDTO memberDTO, DAO memberDAO 객체 new 생성
		MemberDTO memberDTO = new MemberDTO();
		MemberDAO memberDAO = new MemberDAO();
		// memberDTO에 condition : PASSWORD_CHECK_SELECTONE
		memberDTO.setCondition("PASSWORD_CHECK_SELECTONE"); // 나중 수정 예상
		System.out.println("	log : CheckPasswordAction.java		condition : "+ memberDTO.getCondition());
		memberDTO.setMemberNum(memberPK);
		memberDTO.setMemberPassword(password);
		System.out.println("	log : CheckPasswordAction.java		memberDTO에 set데이터 완료");

		// MemberDAO.selectOne 요청
		// 결과값(MemberDTO) 받아오기
		// memberDTO에 저장
		memberDTO = memberDAO.selectOne(memberDTO);
		System.out.println("	log : CheckPasswordAction.java		memberDAO.selectOne 요청");
		System.out.println("	log : CheckPasswordAction.java		selectOne 결과 : "+ memberDTO);

		
		// forward 객체 생성
		ActionForward forward = new ActionForward();

		// 이동 방법 : 데이터 전송이므로 redirect(false)
		forward.setRedirect(false);

		// 만약 memberDTO가 존재한다면
		if(memberDTO != null) {
			// checkPW 성공 결과 보내기
			request.setAttribute("checkResult", true);
			System.out.println("	log : CheckPasswordAction.java		V에게 selectOne 결과 loginMemberData를 보냄 : true");
			
			// session(role) 값 가져오기
			String role = (String)session.getAttribute("memberRole");

			// 만약 사용자가 관리자라면
			if(role.equals("admin")) {
				// 이동 페이지 보내기 : adminPage.do
				//request.setAttribute("path", "adminPage.do");
				forward.setPath("adminPage.do");
			}
			//  그 외의 사용자라면
			else {
				// 이동 페이지 보내기 : myPage.do
				//request.setAttribute("path", "myPage.do");
				forward.setPath("myPage.do");
			}
		}
		// 일치하지 않는다면
		else {
			// checkPW 실패 결과 보내기
			request.setAttribute("checkResult", false);
			System.out.println("	log : CheckPasswordAction.java		V에게 selectOne 결과 loginMemberData를 보냄 : false");
			// 이동 페이지 보내기 : checkPWPage.do
			//request.setAttribute("path", "checkPWPage.do");
			forward.setPath("checkPWPage.do");
		}
		
		// chekPW 결과 출력 페이지로 보내기 : script.do
		//forward.setPath("script.do");
		System.out.println("	log : CheckPasswordAction.java		forwardPath : "+ forward.getPath());
		System.out.println("	log : CheckPasswordAction.java		종료");
		return forward;
	}

}
