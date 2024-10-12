package model.dto;

import java.util.HashMap;

public class MemberDTO {
	private int memberNum;				//멤버번호
	private String memberEmail;			//이메일
	private String memberPassword;		//비밀번호
	private String memberName;			//이름
	private String memberNickname;		//닉네임
	private String memberPhone;			//전화번호
	private String memberProfileWay;	//프로필이미지경로
	private String memberRole;			//권한
	private String memberHireDay;		//가입일자
	
	//개발용
	private String condition;			//컨디션
	private HashMap<String, String> filterList; //필터검색용 <검색구분용, 검색값>
	private int startNum;				//페이지네이션 시작번호
	private int endNum;					//페이지네이션 끝번호
	private int cnt;					//페이지네이션 개수 반환용
	
	
	//getter setter
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
	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberNickname() {
		return memberNickname;
	}
	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
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
	public HashMap<String, String> getFilterList() {
		return filterList;
	}
	public void setFilterList(HashMap<String, String> filterList) {
		this.filterList = filterList;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	@Override
	public String toString() {
		return "MemberDTO [memberNum=" + memberNum + ", memberEmail=" + memberEmail + ", memberPassword="
				+ memberPassword + ", memberName=" + memberName + ", memberNickname=" + memberNickname
				+ ", memberPhone=" + memberPhone + ", memberProfileWay=" + memberProfileWay + ", memberRole="
				+ memberRole + ", memberHireDay=" + memberHireDay + ", condition=" + condition + ", filterList="
				+ filterList + ", startNum=" + startNum + ", endNum=" + endNum + ", cnt=" + cnt + "]";
	}
}
