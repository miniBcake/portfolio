package controller.ajax;

import java.io.IOException;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

@WebServlet("/emailNameCheck")
public class EmailNameCheckAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 이메일과 이름 확인 서블릿
	public EmailNameCheckAction() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("	log : EmailNameCheckAction.java		시작");
		// 결과를 보관할 boolean flag 변수 생성
		// 기본 값은 false (일치하는 값이 존재하지 않음)
		boolean flag = false;
		
		// V에서 request.getParameter로 데이터를 받음
		// 이메일, 이름 데이터를 받음
		// String 변수명(이메일) : email
		String email = request.getParameter("email");
		System.out.println("	log : EmailNameCheckAction.java		View에서 이메일 데이터 받음");
		System.out.println("	log : EmailNameCheckAction.java		email : "+ email);
		// String 변수명(이름) : name
		String name = request.getParameter("name");
		System.out.println("	log : EmailNameCheckAction.java		View에서 이름 데이터 받음");
		System.out.println("	log : EmailNameCheckAction.java		name : "+ name);
		
		// MemberDTO, MemberDAO 생성
		// 객체명 : memberDTO, memberDAO
		MemberDTO memberDTO = new MemberDTO();
		MemberDAO memberDAO = new MemberDAO();
		System.out.println("	log : EmailNameCheckAction.java		MemberDTO, DAO 객체 생성");
		
		// memberDTO.setCondition
		// condition = EMAIL_NAME_SELECTONE
		memberDTO.setCondition("EMAIL_NAME_SELECTONE");
		System.out.println("	log : EmailNameCheckAction.java		setCondition 완료 : "+ memberDTO.getCondition());
		// memberDTO.set으로 이메일, 이름 값 넣기
		memberDTO.setMemberEmail(email);
		memberDTO.setMemberName(name);
		System.out.println("	log : EmailNameCheckAction.java		MemberDTO 세터 완료");
		
		// memberDAO.selectOne으로 해당 memberDTO의 이메일, 이름과 일치하는 값 반환받음
		// memberDTO에 반환값 저장
		memberDTO = memberDAO.selectOne(memberDTO);
		System.out.println("	log : EmailNameCheckAction.java		selectOne 완료");
		System.out.println("	log : EmailNameCheckAction.java		memberDTO : "+ memberDTO);
		
		// 일치하는 값이 존재한다면
		// memberDTO가 null이 아니라면
		if(memberDTO != null) {
			System.out.println("	log : EmailNameCheckAction.java		memberrDTO 값 존재");
			// flag를 true로 변환
			flag = true;
		}
		System.out.println("	log : EmailNameCheckAction.java		flag : "+ flag);
		
		// V에게 결과 보내기
		// json 형식으로 데이터를 보내기 위한 객체 생성
		JSONObject jsonData = new JSONObject();
		
		// 보낼 데이터 넣기
		jsonData.put("result", flag);
		jsonData.put("memberPK", memberDTO.getMemberNum());
		
		// 보낼 데이터 타입 설정
		response.setContentType("application/x-json; charset=utf-8");
		
		// response.getWriter을 호출하여 생성한 jsonData 전송		
		response.getWriter().print(jsonData);
		System.out.println("	log : EmailNameCheckAction.java		V에게 값 전달");
	}
}