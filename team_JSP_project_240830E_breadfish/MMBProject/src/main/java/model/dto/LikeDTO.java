package model.dto;

public class LikeDTO {

	private int likeNum;//좋아요 고유번호(PK)
	private int boardNum;//게시물 고유번호(FK)
	private int memberNum;//회원 고유번호(FK)
	private String condition;//상태변수
	
	//getter setter
	public int getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}

	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	@Override
	public String toString() {
		return "LikeDTO [toString()=" + super.toString() + "]";
	}
}
