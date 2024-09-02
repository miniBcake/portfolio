package controller.page;

import java.util.ArrayList;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ProfilePicUpload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BoardDAO;
import model.dao.MemberDAO;
import model.dto.BoardDTO;
import model.dto.MemberDTO;

public class MyBoardListPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 데이터를 전달해줄 ArrayList<BoardDTO> datas 객체 생성
		ArrayList<BoardDTO> datas = new ArrayList<BoardDTO>();

		// 프로필사진을 담을 변수 선언
		String profilePic = ProfilePicUpload.loginProfilePic(request);

		// session에서 닉네임 값 받아옴
		HttpSession session = request.getSession();
		String nickName = (String)session.getAttribute("memberNickName");
		System.out.println("	log : MyBoardListPageAction.java		session 값 가져오기");
		System.out.println("	log : MyBoardListPageAction.java		session(memberNickName)"+session.getAttribute("memberNickName"));

		// BoardDTO, DAO 객체 new 생성
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO boardDTO = new BoardDTO();
		// BoardDTO에 회원번호(PK) 값 넣어주기
		boardDTO.setMemberNickname(nickName);;
		System.out.println("	log : MyBoardListPageAction.java		boardDTO에 set 완료");

		// BoardDAO.selectAll로 신규회원 목록 받아오기
		// datas 리스트에 받기
		datas = boardDAO.selectAll(boardDTO);
		System.out.println("	log : MyBoardListPageAction.java		boardDAO.selectAll 요청");
		System.out.println("	log : MyBoardListPageAction.java		selectAll 결과 : "+ boardDTO);

		// View에게 전달
		// request.setAttribute에 flag 저장
		request.setAttribute("memberProfileWay", profilePic);
		System.out.println("	log : MyBoardListPageAction.java		V에게 update 결과 result를 보냄");
		// request.setAttribute에 datas저장
		request.setAttribute("myBoardList", datas);
		System.out.println("	log : MyBoardListPageAction.java		V에게 selectAll 결과 myBoardList 보냄");

		// 페이지 이동
		// forward 객체 생성
		ActionForward forward = new ActionForward();
		// 이동 방법 : 데이터가 있으므로 forward (false)
		// 이동 페이지 : myBoardList.jsp
		forward.setRedirect(false);
		forward.setPath("myBoardList.jsp");

		System.out.println("	log : MyBoardListPageAction.java		forwardPath : "+ forward.getPath());
		System.out.println("	log : MyBoardListPageAction.java		종료");
		return forward;
	}
}
