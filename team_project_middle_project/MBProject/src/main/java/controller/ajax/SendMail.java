package controller.ajax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;


@WebServlet("/sendEmail")
public class SendMail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Properties serverInfo; // 서버 정보
	Authenticator auth; // 인증 정보

	// 앱 비밀번호 발급 시 사용한 구글 계정
	private final String username = "galbbangjilbbang";
	// 앱 비밀번호
	private final String password = "SMG2S9ZRKFMW";



	public SendMail() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("	log : SendMail.java		시작");

		// 설정 ===================================================================================================
		// 메일 관련 정보
		String host = "smtp.naver.com";
		int port=465;

		// 요청 파라미터에서 인증번호와 수신자 이메일을 가져옴
		String receiveEmail = request.getParameter("email");
		System.out.println("	log : SendMail.java		getParameter(email) : "+ receiveEmail);

		// 랜덤한 인증번호 4자리 생성
		Random rand = new Random();
		System.out.println("	log : SendMail.java		Random 객체 생성");	
		String checkNum ="";

		for(int i = 1; i <= 4; i++) {
			checkNum += rand.nextInt(10);
		}
		System.out.println("	log : SendMail.java		인증번호 생성");
		System.out.println("	log : SendMail.java		checkNum : "+ checkNum);

		// 이메일의 제목 설정
		String title = "갈빵질빵에서 보낸 인증번호 메일입니다.";
		System.out.println("	log : SendMail.java		title : " + title);

		// 이메일 내용 설정
		String content = "인증번호 \n"+checkNum;
		System.out.println("	log : SendMail.java		content : " + content);

		// 결과가 담길 변수
		boolean flag = false;

		// 이메일 송신 =====================================================================================
		// 이메일 서버의 속성을 설정
		// 서버 정보를 저장할 객체
		Properties props = System.getProperties();
		System.out.println("	log : SendMail.java		이메일 서버 속성 설정");

		// 서버의 호스트 설정
		props.put("mail.smtp.host", host);
		// 서버의 포트 설정
		props.put("mail.smtp.port", port);
		// 사용자의 이름과 비밀번호를 사용하여 서버에 인증할 것을 설정
		props.put("mail.smtp.auth", "true");
		// SMTP 서버와의 연결을 암호화할 것을 설정
		props.put("mail.smtp.ssl.enable", "true");
		// 신뢰할 호스트 설정
		props.put("mail.smtp.ssl.trust", host);
		// TLS의 버전을 맞춰주기 위한 코드
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		System.out.println("	log : SendMail.java		서버 설정 완료");

		// 인증정보 생성 및 메일 세션을 생성
		Session emailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		emailSession.setDebug(true); //for debug
		System.out.println("	log : SendMail.java		세션에 인증번호 저장");

		// 메일 메시지를 생성합니다.
		// 생성된 Session을 사용하여 MimeMessage 객체 생성
		Message mimeMessage = new MimeMessage(emailSession);
		try {
			// 발신자 설정
			mimeMessage.setFrom(new InternetAddress(username+"@naver.com"));
			// 수신자 절정
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveEmail));
			// 제목 설정
			mimeMessage.setSubject(title);
			// 내용 설정
			mimeMessage.setText(content);
			System.out.println("	log : SendMail.java		메일 전송에 필요한 데이터 추가");

			// 이메일 전송
			Transport.send(mimeMessage);
			System.out.println("	log : SendMail.java		message 전송");
			
			// 전송에 성공했으므로 flag를 true로 변경
			flag = true;
		} catch (MessagingException e) {
			// 이메일 전송에 실패했을 경우 V에게 false 전송
			response.getWriter().print(flag);
			e.printStackTrace();
		}

		// 인증번호를 비교하기 위해 session에 저장
		HttpSession timeSession = request.getSession();

		// 세션의 유효 시간을 3분으로 설정 (180초)
		timeSession.setMaxInactiveInterval(180);
		System.out.println("	log : SendMail.java		세션이 유지되는 시간 설정");

		// 남은 시간 계산
		// 현재시간 + 180초
		long remainTime = System.currentTimeMillis() + (180 * 1000);
		System.out.println("	log : SendMail.java		세션에 남은 시간 계산 : "+ remainTime);

		// session에 인증번호, 남은 시간 저장
		timeSession.setAttribute("checkNum", checkNum);
		timeSession.setAttribute("remainTime", remainTime);
		System.out.println("	log : SendMail.java		세션에 인증번호, 남은 시간 저장");
		
		// V에게 결과 전송
		response.getWriter().print(flag);
		System.out.println("	log : SendMail.java		View에 전송결과 전송 (true)");
	}
}