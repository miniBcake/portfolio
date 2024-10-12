package controller.ajax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.common.ProfilePicUpload;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

@WebServlet("/join.do")
@MultipartConfig(
		// 해당 크기가 넘으면 디스크의 임시 디렉토리에 저장
		fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
		// 파일의 최대 크기
		maxFileSize = 1024 * 1024 * 10,       // 10 MB
		// 한 요청의 최대 크기
		// 여러 파일 및 전송값 등을 합한 최대 크기
		maxRequestSize = 1024 * 1024 * 100   // 100 MB
		)
public class JoinAction extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public JoinAction() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("	log : JoinAction.java		시작");

		// V에서 String request.getPart로 받아오기
		System.out.println("	log : JoinAction.java		사용자 입력 데이터 받아오기");
		// 이메일(email), 비밀번호(password), 닉네임(nickname), 권한(role) 데이터 받기 (필수 데이터)
		String email = getStringFromPart(request.getPart("email"));
		System.out.println("	log : JoinAction.java		email : " + email);
		String password = getStringFromPart(request.getPart("password"));
		System.out.println("	log : JoinAction.java		password : " + password);
		String nickName = getStringFromPart(request.getPart("nickName"));
		System.out.println("	log : JoinAction.java		nickName : " + nickName);
		String role = getStringFromPart(request.getPart("role"));
		System.out.println("	log : JoinAction.java		role : " + role);
		String name = getStringFromPart(request.getPart("name"));
		System.out.println("	log : JoinAction.java		name : " + name);

		// 프로필사진(profilePic)은 초기에 기본프로필 사진으로 설정 
		// 입력된 파일을 확인하고 가져오는 메서드
		String profilePic = ProfilePicUpload.profilePicUpload(request, response);
		System.out.println("	log : JoinAction.java		profilePic : " + profilePic);
		// 전화번호(phoneNum) 받기 데이터 받기 (선택 데이터)
		String phoneNum = getStringFromPart(request.getPart("phoneNum"));
		System.out.println("	log : JoinAction.java		phoneNum : " + phoneNum);

		// MemberDTO memberDTO, DAO memberDAO 객체 new 생성
		MemberDTO memberDTO = new MemberDTO();
		MemberDAO memberDAO = new MemberDAO();
		// memberDTO에 이메일, 비밀번호, 닉네임, 권한데이터, 프로필사진, 전화번호 넣기
		memberDTO.setMemberEmail(email);				// 1. 이메일 입력
		memberDTO.setMemberPassword(password);		// 2. 비밀번호 입력
		memberDTO.setMemberNickname(nickName);			// 3. 닉네임 입력
		memberDTO.setMemberRole(role);				// 4. 권한 입력
		memberDTO.setMemberProfileWay(profilePic);	// 5. 프로필사진 경로 입력
		memberDTO.setMemberPhone(phoneNum);			// 6. 권한 입력(일반, 점주)
		memberDTO.setMemberName(name);				// 7. 이름 입력

		// MemberDAO.insert 요청
		// 결과값(boolean flag) 받아오기
		boolean flag = memberDAO.insert(memberDTO);
		System.out.println("	log : JoinAction.java		memberDAO.insert 요청");
		System.out.println("	log : JoinAction.java		insert 결과 flag : "+ flag);

		// 회원가입 실패 시 파일을 삭제하는 메서드
		if(!flag) {
			// 기본 프로필 사진이 아니면 삭제하는 메서드
			ProfilePicUpload.deletefile(profilePic);

			// 에러페이지로 이동
			System.out.println("	log : JoinAction.java		insert 실패");
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 회원가입에 실패하였습니다.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 페이지 이동 (서블릿이므로 FC를 거치지 않고 이동)
		// 이동 페이지 : loginPage.do(로그인 페이지로 이동)
		System.out.println("	log : JoinAction.java		종료");
		response.sendRedirect("loginPage.do");
	}

	// Part 값을 String 값으로 변경해주는 메서드
	private String getStringFromPart(Part part) throws IOException {
		System.out.println("	log : JoinAction.getStringFromPart		시작");
		
		// 만약 part가 null이라면 null 값 반환
		if(part == null) {
			System.out.println("	log : JoinAction.getStringFromPart		null 값 반환");
			return null;
		}
		
		// StringBuilder를 사용하여 문자열을 저장할 객체 생성
		StringBuilder stringBuilder = new StringBuilder();
		// InputStream을 사용하여 문자열로 변환
		InputStreamReader input = new InputStreamReader(part.getInputStream());
		// BufferedReader로 한 줄씩 읽기
		BufferedReader reader = new BufferedReader(input);
		
		// 읽어들인 줄을 넣을 String 선언
		String line = null;

		// 값이 존재하지 않을 때까지 읽어들임
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
		}
		
		System.out.println("	log : JoinAction.getStringFromPart		string 값 반환");
		return stringBuilder.toString();
	}
}
