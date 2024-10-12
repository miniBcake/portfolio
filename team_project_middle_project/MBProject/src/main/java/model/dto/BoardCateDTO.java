package model.dto;

public class BoardCateDTO {
	private int boardCateNum;		//카테고리 번호
	private String boardCateName;	//카테고리 이름
	
	//getter setter
	public int getBoardCateNum() {
		return boardCateNum;
	}
	public void setBoardCateNum(int boardCateNum) {
		this.boardCateNum = boardCateNum;
	}
	public String getBoardCateName() {
		return boardCateName;
	}
	public void setBoardCateName(String boardCateName) {
		this.boardCateName = boardCateName;
	}
	@Override
	public String toString() {
		return "BoardCateDTO [boardCateNum=" + boardCateNum + ", boardCateName=" + boardCateName + "]";
	}
}
