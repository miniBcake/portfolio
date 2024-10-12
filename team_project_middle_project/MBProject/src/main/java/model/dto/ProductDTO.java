package model.dto;

import java.util.HashMap;

public class ProductDTO {
	private int productNum;				//상품번호
	private String productName;			//이름
	private int boardNum;				//게시글번호
	private int productPrice;			//가격
	private String productProfileWay;	//썸네일 이미지
	private int productCateNum;			//카테고리 번호
	
	//타 테이블
	//게시글 (board)
	private String boardTitle;			//제목
	private String boardContent;		//내용
	//상품 카테고리
	private String productCateName;		//카테고리명
	
	//개발용
	private String condition;			//컨디션
	private HashMap<String, String> filterList; //필터검색용 <검색구분용, 검색값>
	private int startNum;				//페이지네이션 시작번호
	private int endNum;					//페이지네이션 끝번호
	private int cnt;					//페이지네이션 개수 반환용
	
	
	//getter setter
	public int getProductNum() {
		return productNum;
	}
	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductProfileWay() {
		return productProfileWay;
	}
	public void setProductProfileWay(String productProfileWay) {
		this.productProfileWay = productProfileWay;
	}
	public int getProductCateNum() {
		return productCateNum;
	}
	public void setProductCateNum(int productCateNum) {
		this.productCateNum = productCateNum;
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
	public String getProductCateName() {
		return productCateName;
	}
	public void setProductCateName(String productCateName) {
		this.productCateName = productCateName;
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
		return "ProductDTO [productNum=" + productNum + ", productName=" + productName + ", boardNum=" + boardNum
				+ ", productPrice=" + productPrice + ", productProfileWay=" + productProfileWay + ", productCateNum="
				+ productCateNum + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", productCateName=" + productCateName + ", condition=" + condition + ", filterList=" + filterList
				+ ", startNum=" + startNum + ", endNum=" + endNum + ", cnt=" + cnt + "]";
	}
}
