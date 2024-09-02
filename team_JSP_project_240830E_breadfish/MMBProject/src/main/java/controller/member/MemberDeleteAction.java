package controller.member;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.MemberDAO;
import model.dto.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MemberDeleteAction implements Action{
	// 회원탈퇴

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : MemberDeleteAction.java		시작");
		// View에서 받아올 데이터 없음
		
		// (C -> M) 계정 삭제 요청
		// session에서 회원번호(PK)값 받아옴
		HttpSession session = request.getSession();
		int memberNum = (int)session.getAttribute("memberPK");
		System.out.println("	log : MemberDeleteAction.java		session 값 가져오기");
		System.out.println("	log : MemberDeleteAction.java		session(memberPK)"+session.getAttribute("memberPK"));
		
		// MemberDTO, DAO 객체 new 생성
		MemberDTO memberDTO = new MemberDTO();
		MemberDAO memberDAO = new MemberDAO();
		// MemberDTO에 회원번호(PK) 값 넣어주기
		memberDTO.setMemberNum(memberNum);
		System.out.println("	log : MemberDeleteAction.java		memberDTO에 set 완료");
		
		// MemberDAO.delete 요청
		// 결과값(boolean flag) 받아오기
		boolean flag = memberDAO.delete(memberDTO);
		System.out.println("	log : MemberDeleteAction.java		memberDAO.Delete 요청");
		System.out.println("	log : MemberDeleteAction.java		delete 결과 flag : "+ flag);
		
		// View에서 결과 출력용
		// request.setAttribute에 flag 저장
		request.setAttribute("result", flag);
		System.out.println("	log : MemberDeleteAction.java		V에게 delete 결과 result를 보냄");
		
		// 페이지 이동 (결과에 따라 다른 페이지)
		// forward 객체 생성
		ActionForward forward = new ActionForward();
		// 이동 방법 : 전송할 데이터가 있으므로 forward(false)
		forward.setRedirect(false);
		
		// 계정 삭제 성공
		// flag가 true라면
		if(flag) {
			System.out.println("	log : MemberDeleteAction.java		delete 성공");
			// 모든 session 삭제
			// invalidate 사용
			session.invalidate();
			System.out.println("	log : MemberDeleteAction.java		session 전체 삭제");
			
			// 이동 페이지 : mainPage.do
			forward.setPath("mainPage.do");
		}
		// 계정 삭제 실패
		else {
			System.out.println("	log : MemberDeleteAction.java		delete 실패");
			// 이동 페이지 : myPage.do
			forward.setPath("myPage.do");
		}
		
		System.out.println("	log : MemberDeleteAction.java		forwardPath : "+ forward.getPath());
		System.out.println("	log : MemberDeleteAction.java		종료");
		return forward;
	}

}
