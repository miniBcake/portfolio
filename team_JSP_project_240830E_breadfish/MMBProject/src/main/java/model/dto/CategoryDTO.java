package model.dto;

public class CategoryDTO {
	private int categoryNum;		//카테고리 pk
	private String categoryName;	//카테고리 이름
	
	
	//getter setter
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
	@Override
	public String toString() {
		return "CategoryDTO [categoryNum=" + categoryNum + ", cateboryName=" + categoryName + "]";
	}

}
