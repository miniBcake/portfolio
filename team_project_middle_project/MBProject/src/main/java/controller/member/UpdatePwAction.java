package controller.member;

import java.io.IOException;

import controller.common.Action;
import controller.common.ActionForward;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

public class UpdatePwAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : UpdatePwAction.java(16)		시작");
		System.out.println("	log : UpdatePwAction.java(20)		memberNum : "+ request.getParameter("memberNum"));
		// V에서 request.getParameter로 회원번호(PK), 비밀번호 받아오기
		// int 변수명 : memberNum
		int memberNum = Integer.parseInt(request.getParameter("memberPK"));
		System.out.println("	log : UpdatePwAction.java(20)		memberNum : "+ memberNum);
		// String 변수명 : password
		String password = request.getParameter("password");
		System.out.println("	log : UpdatePwAction.java(23)		password : "+ password);

		// MemberDTO, MemberDAO 생성
		// 객체명 : memberDTO, memberDAO
		MemberDTO memberDTO = new MemberDTO();
		MemberDAO memberDAO = new MemberDAO();
		System.out.println("	log : UpdatePwAction.java(29)		MemberDTO, DAO 생성");

		// memberDTO.setCondition : PASSWORD_UPDATE 값 넣어주기
		// memberDTO.set으로 memberNum, password 값 넣기
		memberDTO.setCondition("PASSWORD_UPDATE");
		System.out.println("	log : UpdatePwAction.java(34)		MemberDTO.setCondition : "+ memberDTO.getCondition());
		memberDTO.setMemberNum(memberNum);
		memberDTO.setMemberPassword(password);
		System.out.println("	log : UpdatePwAction.java(37)		memberDTO.set 완료");

		// memberDAO.update을 사용하여 memberDTO 업데이트
		// boolean flag에 반환값 저장
		boolean flag = memberDAO.update(memberDTO);
		System.out.println("	log : UpdatePwAction.java(42)		memberDAO.update 결과 : "+ flag);

		// 업데이트에 성공했다면
		// flag가 false이면
		if(!flag) {
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 회원가입에 실패하였습니다.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// ActionForward 객체 생성
		// 객체명 : forward
		// 이동 방법 : 전송할 데이터가 없으므로 forward(false)
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		// 이동할 페이지 : loginPage.do
		forward.setPath("loginPage.do");

		// forward 객체로 반환
		return forward;
	}
}