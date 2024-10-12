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
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

@WebServlet("/updateProfile.do")
@MultipartConfig(
		// 해당 크기가 넘으면 디스크의 임시 디렉토리에 저장
		fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
		// 파일의 최대 크기
		maxFileSize = 1024 * 1024 * 10,       // 10 MB
		// 한 요청의 최대 크기
		// 여러 파일 및 전송값 등을 합한 최대 크기
		maxRequestSize = 1024 * 1024 * 100   // 100 MB
		)
public class UpdateMemberAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateMemberAction() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("	log : UpdateMemberAction.java		시작");

		// 프로필사진을 담을 변수 선언
		String profilePic = null;
		// 이전 프로필사진을 담을 변수 선언
		// 업데이트 성공시 파일 삭제를 위해 파일명을 복사해놓음
		String preProfilePic = null;

		// session(memberPK)에서 회원번호(PK)값 가져오기
		HttpSession session = request.getSession();
		int memberPK = (int) session.getAttribute("memberPK");
		System.out.println("	log : UpdateMemberAction.java		session(memberPK) : "+ memberPK);

		// V에서 이메일, 닉네임, 비밀번호, 전화번호 받아오기
		String email = getStringFromPart(request.getPart("email"));
		System.out.println("	log : UpdateMemberAction.java		email : " + email);
		String password = getStringFromPart(request.getPart("password"));
		System.out.println("	log : UpdatePasswordAction.java		password : " + password);
		String nickName = getStringFromPart(request.getPart("nickName"));
		System.out.println("	log : UpdateNickNameAction.java		nickName : " + nickName);
		String phoneNum = getStringFromPart(request.getPart("phoneNum"));
		System.out.println("	log : UpdatePhoneAction.java		phoneNum : " + phoneNum);
		
//		//시연을 위해 추가한 코드
//		String name = getStringFromPart(request.getPart("name"));
//		System.out.println("	log :  UpdatePhoneAction.java		name : " + name);
		
		

		// 입력된 파일을 확인하고 저장하는 메서드
		// 저장이 완료된 파일명을 반환
		profilePic = ProfilePicUpload.profilePicUpload(request, response);
		System.out.println("	log : UpdateProfilePicAction.java		profilePic : "+ profilePic);

		// MemberDTO memberDTO, DAO memberDAO 객체 new 생성
		MemberDTO memberDTO = new MemberDTO();
		MemberDAO memberDAO = new MemberDAO();
		System.out.println("	log : UpdateMemberAction.java		MemberDTO, DAO 생성");

		// 만약 이메일, 닉네임, 전화번호, null 또는 길이가 0이고, 프로필 사진이 default.png 값으로 들어온다면
		if(email == null || email.length() <= 0 || nickName == null || nickName.length() <= 0 || phoneNum == null || phoneNum.length() <= 0 || profilePic.equals(ProfilePicUpload.defaultProfilePic())) {
			// selectOne으로 회원 데이터 가져오기
			// memberDTO.setCondition : MEMBER_INFO_SELECTONE 값 넣어주기
			// memberDTO.set으로 condition, 회원번호 넣기
			memberDTO.setCondition("MEMBER_INFO_SELECTONE");
			System.out.println("	log : UpdateMemberAction.java		condition : "+ memberDTO.getCondition());
			memberDTO.setMemberNum(memberPK);
			System.out.println("	log : UpdateMemberAction.java		memberDTO에 set데이터 완료");

			// MemberDAO.selectOne 요청
			// 결과값(MemberDTO) 받아오기
			// memberDTO에 결과값 넣기
			memberDTO = memberDAO.selectOne(memberDTO);
			System.out.println("	log : UpdateMemberAction.java		memberDAO.selectOne 요청");
			System.out.println("	log : UpdateMemberAction.java		selectOne 결과 : "+ memberDTO);
		}
		
//		//시연을 위해 추가한 코드
//		if (name == null || name.isEmpty()) {
//		    name = "unKnown";
//		}
//		System.out.println("	log : UpdateMemberAction.java		이름 변경: " + name);

		// memberDTO에 새로운 이름 값 넣기
//		memberDTO.setMemberName(name);
//		System.out.println("	log : UpdateMemberAction.java		memberDTO의 이름 변경: " + memberDTO.getMemberName());

		// 첫 번째 업데이트 (비밀번호를 제외한 모든 데이터의 업데이트)
		// memberDTO.setCondition : UPDATE 값 넣어주기
		memberDTO.setCondition("UPDATE");
		System.out.println("	log : UpdateMemberAction.java		condition : "+ memberDTO.getCondition());
		// memberDTO.set으로 회원번호, 이메일, 닉네임, 프로필사진, 전화번호 값 넣어주기
		memberDTO.setMemberNum(memberPK);
		// View에서 들어온 값이 null이 아닌 값만 넣어주기
		// 이메일 값이 존재한다면
		if(email != null) {
			// memberDTO에 새로운 이메일 값 넣기
			memberDTO.setMemberEmail(email);			
			System.out.println("	log : UpdateMemberAction.java		memberDTO의 이메일 변경"+ memberDTO.getMemberEmail());
		}
		// 닉네임 값이 존재한다면
		if(nickName != null) {
			// memberDTO에 새로운 닉네임 값 넣기
			memberDTO.setMemberNickname(nickName);			
			System.out.println("	log : UpdateMemberAction.java		memberDTO의 닉네임 변경"+ memberDTO.getMemberNickname());
		}
		// 프로필사진 값이 기본 프로필 사진이 아니라면
		if(!profilePic.equals(ProfilePicUpload.defaultProfilePic())) {
			// 기존 프로필 사진은 따로 저장하고
			preProfilePic = memberDTO.getMemberProfileWay();
			System.out.println("	log : UpdateMemberAction.java		memberDTO의 기존 사진을 prefilePic에 저장 : "+ preProfilePic);
			
			// memberDTO에 새로운 프로필 사진 값 넣기
			memberDTO.setMemberProfileWay(profilePic);			
			System.out.println("	log : UpdateMemberAction.java		memberDTO의 프로필 사진 변경"+ memberDTO.getMemberProfileWay());
		}
		// 전화번호 값이존재한다면
		if(phoneNum != null) {
			// memberDTO에 새로운 전화번호 값 넣기
			memberDTO.setMemberPhone(phoneNum);
			System.out.println("	log : UpdateMemberAction.java		memberDTO의 전화번호 변경"+ memberDTO.getMemberPhone());	
		}
		System.out.println("	log : UpdateMemberAction.java		memberDTO에 set데이터 완료");
		System.out.println("	log : UpdateMemberAction.java		memberDTO : "+ memberDTO);
		
		// memberDAO.update을 사용하여 memberDTO 업데이트
		// boolean flag에 반환값 저장
		boolean flag = memberDAO.update(memberDTO);
		System.out.println("	log : UpdateMemberAction.java		memberDAO.update 요청");
		System.out.println("	log : UpdateMemberAction.java		update 결과 flag : "+ flag);


		// 첫 번째 업데이트가 실패할 경우
		// flag가 false이면
		if(!flag) {
			// 입력한 프로필사진(profilePic) 지우기 (메서드 사용)
			ProfilePicUpload.deletefile(profilePic);
			System.out.println("	log : UpdateMemberAction.java		deletefile(profilePic) 완료");

			// 업데이트가 안 됐으므로 에러페이지 표시
			// 500 에러
			try {
				System.out.println("	log : UpdateMemberAction.java		첫 번째 데이터 업데이트 실패");
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 첫 번째 데이터 업데이트에 실패하였습니다.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 두 번째 업데이트
		// 만약 비밀번호 값이 존재한다면 비밀번호도 업데이트
		// password가 null이 아니면서 공백이 아니라면
		if(password != null && password.length() > 0) {
			// memberDTO.setCondition : PASSWORD_UPDATE 값 넣어주기
			// memberDTO.set비밀번호로 password 값 넣어주기
			memberDTO.setCondition("PASSWORD_UPDATE");
			System.out.println("	log : UpdateMemberAction.java		condition : "+ memberDTO.getCondition());
			memberDTO.setMemberPassword(password);
			System.out.println("	log : UpdateMemberAction.java		memberDTO에 set데이터 완료");

			// memberDAO.update을 사용하여 memberDTO 업데이트
			// boolean flag에 반환값 저장
			flag = memberDAO.update(memberDTO);
			System.out.println("	log : UpdateMemberAction.java		memberDAO.update 요청");
			System.out.println("	log : UpdateMemberAction.java		update 결과 flag : "+ flag);
		}

		// 두 번째 업데이트가 실패할 경우
		// flag가 false이면
		if(!flag) {
			// 업데이트가 안 됐으므로 에러페이지 표시
			// 500 에러
			try {
				System.out.println("	log : UpdateMemberAction.java		두 번째 데이터 업데이트 실패");
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500: 두 번째 데이터 업데이트에 실패하였습니다.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 업데이트에 성공했다면
		else {
			// 이전 프로필사진(preProfilePic) 지우기 (메서드 사용)
			ProfilePicUpload.deletefile(preProfilePic);
			System.out.println("	log : UpdateMemberAction.java		deletefile(preProfilePic) 완료");
		}

		// 페이지 이동 (서블릿이므로 FC를 거치지 않고 이동)
				// 이동 페이지 : loginPage.do(로그인 페이지로 이동)
		System.out.println("	log : UpdateMemberAction.java		종료");
		response.sendRedirect("logout.do");
	}

	// Part 값을 String 값으로 변경해주는 메서드
		private String getStringFromPart(Part part) throws IOException {
			System.out.println("	log : UpdateMemberAction.getStringFromPart		시작");
			
			// 만약 part가 null이라면 null 값 반환
			if(part == null) {
				System.out.println("	log : UpdateMemberAction.getStringFromPart		null 값 반환");
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
			
			System.out.println("	log : UpdateMemberAction.getStringFromPart		string 값 반환");
			return stringBuilder.toString();
		}
}
