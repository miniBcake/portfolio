package model.dto;

public class ProductCateDTO {
	private int productCateNum;		//상품 카테고리 번호
	private String productCateName; //상품 카테고리 명
	
	//getter setter
	public int getProductCateNum() {
		return productCateNum;
	}
	public void setProductCateNum(int productCateNum) {
		this.productCateNum = productCateNum;
	}
	public String getProductCateName() {
		return productCateName;
	}
	public void setProductCateName(String productCateName) {
		this.productCateName = productCateName;
	}
	@Override
	public String toString() {
		return "ProductCateDTO [productCateNum=" + productCateNum + ", productCateName=" + productCateName + "]";
	}
}
