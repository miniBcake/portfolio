package controller.member;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

public class LoginAction implements Action{
	// 로그인

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : LoginAction.java		시작");

		System.out.println("	log : LoginAction.java		사용자 입력 데이터 받아오기");
		// View에서 로그인 방식 받아오기
		String type = request.getParameter("type");
		System.out.println("	log : LoginAction.java		type : " + type);


		// View에서 이메일(아이디), 비밀번호 데이터 받기
		// String request.getParameter(이메일) (비밀번호) (이름)
		String email = request.getParameter("email");
		System.out.println("	log : LoginAction.java		email : " + email);
		String password = request.getParameter("password");
		System.out.println("	log : LoginAction.java		password : " + password);
		String name = request.getParameter("name");
		System.out.println("	log : LoginAction.java		name : " + name);

		// MemberDTO memberDTO, DAO memberDAO 객체 new 생성
		MemberDTO memberDTO = new MemberDTO();
		MemberDAO memberDAO = new MemberDAO();

		// 만약 구글 로그인 방법으로 로그인을 시도 했다면
		// 만약 type이 null이 아니라면서 type이 googleLogin이라면
		if(type != null && type.equals("googleLogin")) {
			// memberDTO에 condition : EMAIL_SELECTONE 넣어주기
			// memberDTO에 이메일, 비밀번호 넣기
			memberDTO.setCondition("EMAIL_NAME_SELECTONE");
			System.out.println("	log : LoginAction.java		condition : "+ memberDTO.getCondition());
			memberDTO.setMemberEmail(email);
			memberDTO.setMemberName(name);
			System.out.println("	log : LoginAction.java		memberDTO에 set데이터 완료");
		}
		// 그 외의 방식이면
		else {
			// memberDTO에 condition : LOGIN_SELECTONE 넣어주기
			// memberDTO에 이메일, 비밀번호 넣기
			memberDTO.setCondition("LOGIN_SELECTONE");
			System.out.println("	log : LoginAction.java		condition : "+ memberDTO.getCondition());
			memberDTO.setMemberEmail(email);
			memberDTO.setMemberPassword(password);
			System.out.println("	log : LoginAction.java		memberDTO에 set데이터 완료");
		}

		// MemberDAO.selectOne 요청
		// 결과값(MemberDTO) 받아오기
		// memberDTO에 결과값 넣기
		memberDTO = memberDAO.selectOne(memberDTO);
		System.out.println("	log : LoginAction.java		memberDAO.selectOne 요청");
		System.out.println("	log : LoginAction.java		selectOne 결과 : "+ memberDTO);

		// 페이지 이동 (결과에 따라 다른 페이지)
		// forward 객체 생성
		ActionForward forward = new ActionForward();

		// 결과값이 있을 때
		// memberDTO 값이 존재할 때(null이 아닐 때)
		if(memberDTO != null) {
			System.out.println("	log : LoginAction.java		로그인 성공");
			// 로그인 성공 시 View에게 true 전달

			// 만약 구글 로그인 방법으로 로그인을 시도 했다면 데이터를 따로 가져오는 작업을 실행
			// type이 googleLogin이라면
			if(type != null && type.equals("googleLogin")) {
				// selectOne으로 회원 데이터 가져오기
				// memberDTO.setCondition : MEMBER_INFO_SELECTONE 값 넣어주기
				// 회원번호는 이미 DTO 안에 있음
				memberDTO.setCondition("MEMBER_INFO_SELECTONE");
				System.out.println("	log : LoginAction.java		condition : "+ memberDTO.getCondition());

				// MemberDAO.selectOne 요청
				// 결과값(MemberDTO) 받아오기
				// memberDTO에 결과값 넣기
				memberDTO = memberDAO.selectOne(memberDTO);
				System.out.println("	log : LoginAction.java		memberDAO.selectOne 요청");
				System.out.println("	log : LoginAction.java		selectOne 결과 : "+ memberDTO);
			}

			// session(memberPK)에 회원번호(PK) 저장
			// session(memberNickName) 회원 닉네임 저장
			// session(memberRole) 회원 권한 저장
			HttpSession session = request.getSession();
			session.setAttribute("memberPK", memberDTO.getMemberNum());
			System.out.println("	log : LoginAction.java		session(memberPK) : " +session.getAttribute("memberPK"));
			session.setAttribute("memberNickName", memberDTO.getMemberNickname());
			System.out.println("	log : LoginAction.java		session(memberNickName) : " +session.getAttribute("memberNickName"));
			session.setAttribute("memberRole", memberDTO.getMemberRole());
			System.out.println("	log : LoginAction.java		session(memberRole) : " +session.getAttribute("memberRole"));

			// 이동 방법 : 페이지 이동이므로 redirect
			forward.setRedirect(true);
			// 이동할 페이지 : mainPage.do
			forward.setPath("mainPage.do");
		}
		// 로그인이 실패한다면
		else {
			System.out.println("	log : LoginAction.java		로그인 실패");

			// 이동 방법 : 로그인 실패 여부를 전송해야 하므로 forward 방법
			forward.setRedirect(false);

			// 만약 구글 로그인 방법으로 로그인을 시도 했다면 데이터를 따로 가져오는 작업을 실행
			// type이 googleLogin이라면
			if(type != null && type.equals("googleLogin")) {
				// 이메일 값과 이름 값을 View로 전송
				request.setAttribute("email", email);
				request.setAttribute("name", name);

				// 이동할 페이지 : 
				forward.setPath("googleSignup.jsp");
			}
			else {
				// 로그인 실패시 View에게 실패 멘트와 이동할 경로 전달
				request.setAttribute("msg", "로그인에 실패하셨습니다.");
				request.setAttribute("path", "loginPage.do");
				
				// 이동할 페이지 : failInfo.do
				forward.setPath("failInfo.do");
			}
		}

		System.out.println("	log : LoginAction.java		forwardPath : "+ forward.getPath());
		System.out.println("	log : LoginAction.java		종료");
		return forward;
	}
}