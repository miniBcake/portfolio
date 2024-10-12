package model.dto;

import java.util.ArrayList;

public class StoreDTO {
	private int storeNum; // 가게 고유번호(PK)
	private String storeName; // 가게 상호명
	private String storeDefaultAddress; // 가게 기본주소
	private String storeDetailAddress; // 가게 상세주소
	private String storePhoneNum; // 가게 전화번호
	private String storeClosed; // 폐점여부(Y/N)
	private String condition; // 상태여부
	private ArrayList<Integer> storeNumCNT;// 고유번호 반환값 배열리스트
	private ArrayList<String> storeMenu; // 각 카테고리별 붕어빵 판매종류(Y/N)

	// [0] 팥/슈프림 [1] 야채/김치/만두 [2] 미니 붕어빵 [3] 고구마 붕어빵
	// [4] 아이스크림/초코 [5] 치즈 [6] 페스츄리 [7] 기타

	private String storePaymentCashmoney; 		// 현금 결제 가능(Y/N)
	private String storePaymentCard; 			// 카드 결제 가능(Y/N)
	private String storePaymentaccountTransfer;	// 계좌이체 결제 가능(Y/N)

	private String storeWorkWeek; // 영업요일(MON, TUE, WED, THU, FRI, SAT, SUN)
	private String storeWorkOpen; // 영업시작시간
	private String storeWorkClose; // 영업종료시간

	private int storeMaxNum; // 가장 최근 등록 가게
	private int storeNotClosedCount; // 폐점 안 한 가게 수
	private int storeStartPage; // 시작 페이지
	private int storeEndPage; // 종료 페이지
	
	// getter & setter

	public ArrayList<Integer> getStoreNumCNT() {
		return storeNumCNT;
	}
	public void setStoreNumCNT(ArrayList<Integer> storeNumCNT) {
		this.storeNumCNT = storeNumCNT;
	}
	public int getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreDefaultAddress() {
		return storeDefaultAddress;
	}

	public void setStoreDefaultAddress(String storeDefaultAddress) {
		this.storeDefaultAddress = storeDefaultAddress;
	}

	public String getStoreDetailAddress() {
		return storeDetailAddress;
	}

	public void setStoreDetailAddress(String storeDetailAddress) {
		this.storeDetailAddress = storeDetailAddress;
	}
	public String getStorePhoneNum() {
		return storePhoneNum;
	}

	public void setStorePhoneNum(String storePhoneNum) {
		this.storePhoneNum = storePhoneNum;
	}

	public String getStoreClosed() {
		return storeClosed;
	}

	public void setStoreClosed(String storeClosed) {
		this.storeClosed = storeClosed;
	}

	public ArrayList<String> getStoreMenu() {
		return storeMenu;
	}

	public void setStoreMenu(ArrayList<String> storeMenu) {
		this.storeMenu = storeMenu;
	}

	public String getStorePaymentCashmoney() {
		return storePaymentCashmoney;
	}

	public void setStorePaymentCashmoney(String storePaymentCashmoney) {
		this.storePaymentCashmoney = storePaymentCashmoney;
	}

	public String getStorePaymentCard() {
		return storePaymentCard;
	}

	public void setStorePaymentCard(String storePaymentCard) {
		this.storePaymentCard = storePaymentCard;
	}

	public String getStorePaymentaccountTransfer() {
		return storePaymentaccountTransfer;
	}

	public void setStorePaymentaccountTransfer(String storePaymentaccountTransfer) {
		this.storePaymentaccountTransfer = storePaymentaccountTransfer;
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

	public int getStoreMaxNum() {
		return storeMaxNum;
	}

	public void setStoreMaxNum(int storeMaxNum) {
		this.storeMaxNum = storeMaxNum;
	}

	public int getStoreNotClosedCount() {
		return storeNotClosedCount;
	}

	public void setStoreNotClosedCount(int storeNotClosedCount) {
		this.storeNotClosedCount = storeNotClosedCount;
	}

	public int getStoreStartPage() {
		return storeStartPage;
	}

	public void setStoreStartPage(int storeStartPage) {
		this.storeStartPage = storeStartPage;
	}

	public int getStoreEndPage() {
		return storeEndPage;
	}

	public void setStoreEndPage(int storeEndPage) {
		this.storeEndPage = storeEndPage;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	@Override
	public String toString() {
		return "StoreDTO ["
				+ "condition=" + condition  + "\n"
				+ ", storeNum=" + storeNum  + "\n"
				+ ", storeName=" + storeName  + "\n"
				+ ", storeDefaultAddress=" + storeDefaultAddress  + "\n"
				+ ", storeDetailAddress=" + storeDetailAddress  + "\n"
				+ ", storePhoneNum=" + storePhoneNum  + "\n"
				+ ", storeClosed=" + storeClosed  + "\n"
				+ ", storeNumCNT=" + storeNumCNT  + "\n"
				+ ", storeMenu=" + storeMenu  + "\n"
				+ ", storePaymentCashmoney=" + storePaymentCashmoney  + "\n"
				+ ", storePaymentCard=" + storePaymentCard  + "\n"
				+ ", storePaymentaccountTransfer=" + storePaymentaccountTransfer + "\n" 
				+ ", storeWorkWeek=" + storeWorkWeek  + "\n"
				+ ", storeWorkOpen=" + storeWorkOpen  + "\n"
				+ ", storeWorkClose=" + storeWorkClose + "\n"
				+ ", storeMaxNum=" + storeMaxNum  + "\n"
				+ ", storeNotClosedCount=" + storeNotClosedCount  + "\n"
				+ ", storeStartPage=" + storeStartPage  + "\n"
				+ ", storeEndPage=" + storeEndPage + "]";
	}
}
