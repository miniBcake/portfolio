package model.dto;

import java.util.HashMap;

public class BoardDTO {
	private int boardNum;				//게시글 번호
	private String boardTitle;			//제목
	private String boardContent;		//내용
	private int memberNum;				//작성자
	private String boardWriteDay;		//작성일자
	private String boardOpen;			//비공개 공개 여부 Y/N
	private int boardCateNum;			//게시글 카테고리 번호
	private int storeNum;				//가게 번호
	private String boardDelete;			//관리자 글 삭제 여부 Y/N
	
	//타 테이블
	//좋아요 합계
	private int likeCnt;				//좋아요 수(합계)
	//멤버
	private String memberNickname;		//닉네임
	//카테고리
	private String boardCateName;		//게시글 카테고리 명
	
	//개발용
	private String condition;			//컨디션
	private HashMap<String, String> filterList; //필터검색용 <검색구분용, 검색값>
	private int startNum;				//페이지네이션 시작번호
	private int endNum;					//페이지네이션 끝번호
	private int cnt;					//페이지네이션 개수 반환용
	private int maxPk;					//가장 최근에 사용된 PK번호
	
	
	//getter setter
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public int getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}
	public String getBoardWriteDay() {
		return boardWriteDay;
	}
	public void setBoardWriteDay(String boardWriteDay) {
		this.boardWriteDay = boardWriteDay;
	}
	public String getBoardOpen() {
		return boardOpen;
	}
	public void setBoardOpen(String boardOpen) {
		this.boardOpen = boardOpen;
	}
	public int getBoardCateNum() {
		return boardCateNum;
	}
	public void setBoardCateNum(int boardCateNum) {
		this.boardCateNum = boardCateNum;
	}
	public int getStoreNum() {
		return storeNum;
	}
	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}
	public String getBoardDelete() {
		return boardDelete;
	}
	public void setBoardDelete(String boardDelete) {
		this.boardDelete = boardDelete;
	}
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
	public String getMemberNickname() {
		return memberNickname;
	}
	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}
	public String getBoardCateName() {
		return boardCateName;
	}
	public void setBoardCateName(String boardCateName) {
		this.boardCateName = boardCateName;
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
	public int getMaxPk() {
		return maxPk;
	}
	public void setMaxPk(int maxPk) {
		this.maxPk = maxPk;
	}
	@Override
	public String toString() {
		return "BoardDTO [boardNum=" + boardNum + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", memberNum=" + memberNum + ", boardWriteDay=" + boardWriteDay + ", boardOpen=" + boardOpen
				+ ", boardCateNum=" + boardCateNum + ", storeNum=" + storeNum + ", boardDelete=" + boardDelete
				+ ", likeCnt=" + likeCnt + ", memberNickname=" + memberNickname + ", boardCateName=" + boardCateName
				+ ", condition=" + condition + ", filterList=" + filterList + ", startNum=" + startNum + ", endNum="
				+ endNum + ", cnt=" + cnt + ", maxPk=" + maxPk + "]";
	}
}
