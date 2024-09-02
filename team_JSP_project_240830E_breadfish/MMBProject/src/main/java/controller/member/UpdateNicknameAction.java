package controller.member;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.MemberDAO;
import model.dto.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UpdateNicknameAction implements Action{
	// 닉네임 변경

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : UpdateNickNameAction.java		시작");

		// View에서 nickName 가져오기
		String nickName = request.getParameter("nickName");
		System.out.println("	log : UpdateNickNameAction.java		nickName : " + nickName);

		// session(memberPK)에서 회원번호(PK)값 가져오기
		HttpSession session = request.getSession();
		int memberPK = (int) session.getAttribute("memberPK");
		System.out.println("	log : UpdateNickNameAction.java		session(memberPK) : "+ memberPK);

		// MemberDTO memberDTO, DAO memberDAO 객체 new 생성
		MemberDTO memberDTO = new MemberDTO();
		MemberDAO memberDAO = new MemberDAO();
		// memberDTO에 condition : NICKNAME_UPDATE 넣어주기
		// memberDTO에 회원번호(PK)값 넣어주기
		// memberDTO에 닉네임 값 넣어주기
		memberDTO.setCondition("NICKNAME_UPDATE"); // 나중 수정 예상
		System.out.println("	log : UpdateNickNameAction.java		condition : "+ memberDTO.getCondition());
		memberDTO.setMemberNum(memberPK);
		memberDTO.setMemberNickname(nickName);;
		System.out.println("	log : UpdateNickNameAction.java		memberDTO에 set데이터 완료");

		// MemberDAO.update 요청
		// 결과값(boolean flag) 받아오기
		boolean flag = memberDAO.update(memberDTO);
		System.out.println("	log : UpdateNickNameAction.java		memberDAO.update 요청");
		System.out.println("	log : UpdateNickNameAction.java		update 결과 flag : "+ flag);

		// View에서 결과 출력용
		// request.setAttribute에 flag 저장
		request.setAttribute("result", flag);
		System.out.println("	log : UpdateNickNameAction.java		V에게 update 결과 result를 보냄");

		// 페이지 이동 (결과에 따라 다른 페이지)
		// forward 객체 생성
		// 이동 방법 : 전송할 데이터가 있으므로 forward(false)
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);

		// update가 성공한다면
		// 만약 flag가 true라면
		if(flag) {
			System.out.println("	log : UpdateNickNameAction.java		update 성공");
			// session(memberNickName)에 닉네임 넣기
			session.setAttribute("memberNickName", nickName);

		}
		// 업데이트 성공 실패에 상관없이 수정 페이지로 이동
		// 이동할 페이지 : logout.do
		forward.setPath("logout.do");

		System.out.println("	log : UpdateNickNameAction.java		forwardPath : "+ forward.getPath());
		System.out.println("	log : UpdateNickNameAction.java		종료");
		return forward;
	}
}
