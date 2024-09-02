package model.dto;

public class BoardDTO {
	private int boardNum; 				//게시물 pk
	private String title; 				//제목
	private String content; 			//내용
	private String visibility; 			//공개여부 (공개, 비공개)
	private String writeDay;			//작성일자
	private int categoryNum; 			//카테고리 pk(fk)
	private String categoryName; 		//카테고리 이름
	private int memberNum; 				//멤버 pk(fk)
	private String memberNickname; 		//멤버 닉네임
	private int likeCnt; 				//좋아요 개수
	private String condition; 			//쿼리 구분값
	private String keyword; 			//검색어
	private int boardCnt;				//게시글 별 게시물 개수
	private int startNum;				//페이지네이션 게시물 시작 번호
	private int endNum;					//페이지네이션 게시물 끝 번호
	
	//getter setter
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getWriteDay() {
		return writeDay;
	}
	public void setWriteDay(String writeDay) {
		this.writeDay = writeDay;
	}
	public int getCategoryNum() {
		return categoryNum;
	}
	public void setCategoryNum(int categoryNum) {
		this.categoryNum = categoryNum;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}
	public String getMemberNickname() {
		return memberNickname;
	}
	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getBoardCnt() {
		return boardCnt;
	}
	public void setBoardCnt(int boardCnt) {
		this.boardCnt = boardCnt;
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
	@Override
	public String toString() {
		return "BoardDTO [boradNum=" + boardNum + ", title=" + title + ", content=" + content + ", visibility="
				+ visibility + ", writeDay=" + writeDay + ", categoryNum=" + categoryNum + ", categoryName="
				+ categoryName + ", memberNum=" + memberNum + ", memberNickname=" + memberNickname + ", likeCnt="
				+ likeCnt + ", condition=" + condition + ", keyword=" + keyword + ", boardCnt=" + boardCnt
				+ ", startNum=" + startNum + ", endNum=" + endNum + "]";
	}
}
