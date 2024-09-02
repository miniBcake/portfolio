package controller.member;

import controller.common.Action;
import controller.common.ActionForward;
import controller.common.ProfilePicUpload;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

//multipart에 대한 정의
@MultipartConfig(
		// 해당 크기가 넘으면 디스크의 임시 디렉토리에 저장
		fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
		// 파일의 최대 크기
		maxFileSize = 1024 * 1024 * 10,       // 10 MB
		// 한 요청의 최대 크기
		// 여러 파일 및 전송값 등을 합한 최대 크기
		maxRequestSize = 1024 * 1024 * 100   // 100 MB
		)
public class JoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("	log : JoinAction.java		시작");
		// V에서 String request.getParameter로 받아오기
		System.out.println("	log : JoinAction.java		사용자 입력 데이터 받아오기");
		// 이메일(email), 비밀번호(password), 닉네임(nickname), 권한(role) 데이터 받기 (필수 데이터)
		String email = request.getParameter("email");
		System.out.println("	log : JoinAction.java		email : " + email);
		String password = request.getParameter("password");
		System.out.println("	log : JoinAction.java		password : " + password);
		String nickName = request.getParameter("nickName");
		System.out.println("	log : JoinAction.java		nickName : " + nickName);
		String role = request.getParameter("role");
		System.out.println("	log : JoinAction.java		role : " + role);
		String name = request.getParameter("name");
		System.out.println("	log : JoinAction.java		name : " + name);

		// 프로필사진(profilePic)은 초기에 기본프로필 사진으로 설정 
		// 전화번호(phoneNum) 받기 데이터 받기 (선택 데이터)
		String profilePic = ProfilePicUpload.defaultProfilePic();
		System.out.println("	log : JoinAction.java		profilePic : " + profilePic);
		String phoneNum = request.getParameter("phoneNum");
		System.out.println("	log : JoinAction.java		phoneNum : " + phoneNum);

		// MemberDTO memberDTO, DAO memberDAO 객체 new 생성
		MemberDTO memberDTO = new MemberDTO();
		MemberDAO memberDAO = new MemberDAO();
		// memberDTO에 이메일, 비밀번호, 닉네임, 권한데이터, 프로필사진, 전화번호 넣기
		memberDTO.setMemberEmail(email);				// 1. 이메일 입력
		memberDTO.setMemberPassword(password);		// 2. 비밀번호 입력
		memberDTO.setMemberNickname(nickName);			// 3. 닉네임 입력
		memberDTO.setMemberRole(role);				// 4. 권한 입력
		// memberDTO.setMemberProfileWay(profilePic);	// 5. 프로필사진 경로 입력
		memberDTO.setMemberPhone(phoneNum);			// 6. 권한 입력(일반, 점주)
		memberDTO.setMemberName(name);				// 7. 이름 입력
		
		// 입력된 파일을 확인하고 가져오는 메서드
		profilePic = ProfilePicUpload.profilePicUpload(request, response);
		System.out.println("	log : JoinAction.java		profilePic : "+ profilePic);
		
		// 파일 저장이 완료된 profilePic을 memberDTO에 넣기	
		memberDTO.setMemberProfileWay(profilePic);
		System.out.println("	log : JoinAction.java		memberDTO에 set데이터 완료");

		// MemberDAO.insert 요청
		// 결과값(boolean flag) 받아오기
		boolean flag = memberDAO.insert(memberDTO);
		System.out.println("	log : JoinAction.java		memberDAO.insert 요청");
		System.out.println("	log : JoinAction.java		insert 결과 flag : "+ flag);

		// 회원가입 실패 시 파일을 삭제하는 메서드
		if(!flag) {
			// 기본 프로필 사진이 아니면 삭제하는 메서드
			ProfilePicUpload.deletefile(profilePic);
		}
		
		// View에서 결과 출력용
		// request.setAttribute에 flag 저장
		request.setAttribute("joinResult", flag);
		System.out.println("	log : JoinAction.java		V에게 insert 결과 result를 보냄");

		// 페이지 이동 (결과에 따라 다른 페이지)
		// forward 객체 생성
		// 이동 방법 : 전송할 데이터가 있으므로 forward(false)
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);

		// 회원가입이 성공이든 실패든 같은 페이지로 이동
		// 이동 페이지 : script.do (결과 출력 페이지)
		forward.setPath("script.do");

		System.out.println("	log : JoinAction.java		forwardPath : "+ forward.getPath());
		System.out.println("	log : JoinAction.java		종료");
		return forward;
	}
}