package model.dto;

public class MemberDTO {
	private int memberNum;// 회원 고유번호(PK)
	private String memberEmail;// 회원 이메일
	private String memberPassword;// 회원 비밀번호
	private String memberName;// 회원 이름
	private String memberPhone;// 회원 전화번호
	private String memberNickname;// 회원 별칭
	private String memberProfileWay;// 회원 프로필경로
	private String memberRole;// 회원 분류
	private String memberHireDay;// 회원 가입일자
	private String condition;// 상태변수

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassward) {
		this.memberPassword = memberPassward;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberNickname() {
		return memberNickname;
	}

	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}

	public String getMemberProfileWay() {
		return memberProfileWay;
	}

	public void setMemberProfileWay(String memberProfileWay) {
		this.memberProfileWay = memberProfileWay;
	}

	public String getMemberRole() {
		return memberRole;
	}

	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}

	public String getMemberHireDay() {
		return memberHireDay;
	}

	public void setMemberHireDay(String memberHireDay) {
		this.memberHireDay = memberHireDay;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "MemberDTO [memberNum=" + memberNum + ", memberEmail=" + memberEmail + ", memberPassword="
				+ memberPassword + ", memberName=" + memberName + ", memberPhone=" + memberPhone + ", memberNickname="
				+ memberNickname + ", memberProfileWay=" + memberProfileWay + ", memberRole=" + memberRole
				+ ", memberHireDay=" + memberHireDay + ", condition=" + condition + "]";
	}

	
}