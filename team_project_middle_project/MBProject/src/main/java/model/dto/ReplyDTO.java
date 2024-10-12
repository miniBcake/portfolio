package model.dto;

public class ReplyDTO {
	private int replyNum;				//댓글번호
	private String replyContent;		//댓글내용
	private int memberNum;				//작성자
	private String replyWriteDay;		//작성일자
	private int boardNum;				//게시글 번호(부모)
	
	//타 테이블
	//멤버
	private String memberNickname;		//닉네임
	private String memberProfileWay;	//프로필경로
	
	//개발용
	private String condition;			//컨디션
	private int startNum;				//페이지네이션 시작번호
	private int endNum;					//페이지네이션 끝번호
	private int cnt;					//페이지네이션 개수 반환용
	
	
	//getter setter
	public int getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public int getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}
	public String getReplyWriteDay() {
		return replyWriteDay;
	}
	public void setReplyWriteDay(String replyWriteDay) {
		this.replyWriteDay = replyWriteDay;
	}
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
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
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
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
		return "ReplyDTO [replyNum=" + replyNum + ", replyContent=" + replyContent + ", memberNum=" + memberNum
				+ ", replyWriteDay=" + replyWriteDay + ", boardNum=" + boardNum + ", memberNickName=" + memberNickname
				+ ", memberProfileWay=" + memberProfileWay + ", condition=" + condition + ", startNum=" + startNum
				+ ", endNum=" + endNum + ", cnt=" + cnt + "]";
	}
}
