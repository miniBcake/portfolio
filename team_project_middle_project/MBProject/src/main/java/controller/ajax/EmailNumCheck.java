package controller.ajax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/emailNumCheck")
public class EmailNumCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EmailNumCheck() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("	log : EmailNumCheck.java		시작");
		// V에서 인증번호 가져오기
		String inputCheckNum = request.getParameter("checkNum");
		System.out.println("	log : EmailNumCheck.java		입력 인증값(inputNum) : "+ inputCheckNum);
		
		// session에 있는 원본 인증값 가져오기
		String checkNum = (String) request.getSession().getAttribute("checkNum");
		System.out.println("	log : EmailNumCheck.java		원본 인증값(checkNum) : "+ checkNum);
		
		// 만약 원본 인증값과 V의 입력값이 같다면
		if(inputCheckNum.equals(checkNum)) {
			// V에 true 보내기
			response.getWriter().print(true);
			System.out.println("	log : EmailNumCheck.java		결과(true) 전송");
		}
		// 같지 않다면
		else {
		// V에 false 보내기
			response.getWriter().print(false);
			System.out.println("	log : EmailNumCheck.java		결과(false) 전송");
		}
	}

}
