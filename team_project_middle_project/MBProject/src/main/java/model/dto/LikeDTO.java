package model.dto;

public class LikeDTO {
	private int likeNum;	//좋아요 고유번호(PK)
	private int memberNum;	//회원 고유번호(FK)
	private int boardNum;	//게시물 고유번호(FK)
	
	public int getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
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
	
	@Override
	public String toString() {
		return "LikeDTO [likeNum=" + likeNum + ", memberNum=" + memberNum + ", boardNum=" + boardNum + "]";
	}
}
