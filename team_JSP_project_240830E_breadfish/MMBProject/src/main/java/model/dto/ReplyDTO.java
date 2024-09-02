package model.dto;

public class ReplyDTO {
	private int replyNum;//댓글 고유번호(PK)
	private String replyContent;//댓글 내용
	private String replyWriteDay;//댓글작성일자
	private int memberNum;//회원 고유번호PK(FK)
	private int boardNum;//게시물 고유번호PK(FK)
	private String condition;//상태변수
	private String replyTitle;//게시물 제목
	private String replyNickName;//회원 닉네임
	private String replyProfileWay;//회원 사진 경로
	private String replyEmail;//회원 이메일
	private int replyCount;//댓글 총 개수
	private int pageStartNum;//페이지 시작 번호
	private int pageEndNum;//페이지 끝 번호

	//getter setter	
	
	
	public int getReplyNum() {
		return replyNum;
	}

	public int getPageStartNum() {
		return pageStartNum;
	}

	public void setPageStartNum(int pageStartNum) {
		this.pageStartNum = pageStartNum;
	}

	public int getPageEndNum() {
		return pageEndNum;
	}

	public void setPageEndNum(int pageEndNum) {
		this.pageEndNum = pageEndNum;
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

	public String getReplyWriteDay() {
		return replyWriteDay;
	}

	public void setReplyWriteDay(String replyWriteDay) {
		this.replyWriteDay = replyWriteDay;
	}

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getReplyTitle() {
		return replyTitle;
	}

	public void setReplyTitle(String replyTitle) {
		this.replyTitle = replyTitle;
	}

	public String getReplyNickName() {
		return replyNickName;
	}

	public void setReplyNickName(String replyNickName) {
		this.replyNickName = replyNickName;
	}

	public String getReplyProfileWay() {
		return replyProfileWay;
	}

	public void setReplyProfileWay(String replyProfileWay) {
		this.replyProfileWay = replyProfileWay;
	}

	public String getReplyEmail() {
		return replyEmail;
	}

	public void setReplyEmail(String replyEmail) {
		this.replyEmail = replyEmail;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	
	@Override
	public String toString() {
		return "ReplyDTO [toString()=" + super.toString() + "]";
	}
}