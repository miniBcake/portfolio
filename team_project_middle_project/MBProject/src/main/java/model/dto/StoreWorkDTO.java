package model.dto;

public class StoreWorkDTO {

	private int storeWorkNum; 		// 영업정보 고유번호(PK)
	private int storeNum;			// 가게 고유번호(FK)
	private String storeWorkWeek; 	// 영업요일(MON, TUE, WED, THU, FRI, SAT, SUN)
	private String storeWorkOpen; 	// 영업시작시간
	private String storeWorkClose; 	// 영업종료시간
	private String condition;		//상태여부

	public int getStoreWorkNum() {
		return storeWorkNum;
	}

	public void setStoreWorkNum(int storeWorkNum) {
		this.storeWorkNum = storeWorkNum;
	}

	public int getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}

	public String getStoreWorkWeek() {
		return storeWorkWeek;
	}

	public void setStoreWorkWeek(String storeWorkWeek) {
		this.storeWorkWeek = storeWorkWeek;
	}

	public String getStoreWorkOpen() {
		return storeWorkOpen;
	}

	public void setStoreWorkOpen(String storeWorkOpen) {
		this.storeWorkOpen = storeWorkOpen;
	}

	public String getStoreWorkClose() {
		return storeWorkClose;
	}

	public void setStoreWorkClose(String storeWorkClose) {
		this.storeWorkClose = storeWorkClose;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	@Override
	public String toString() {
		return "StoreWorkDTO ["
				+ "condition=" + condition  + "\n"
				+ ", storeWorkNum=" + storeWorkNum + "\n" 
				+ ", storeNum=" + storeNum  + "\n"
				+ ", storeWorkWeek=" + storeWorkWeek + "\n" 
				+ ", storeWorkOpen=" + storeWorkOpen  + "\n"
				+ ", storeWorkClose=" + storeWorkClose + "]";
	}
}