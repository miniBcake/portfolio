package model.dto;

public class StoreMenuDTO {
   
   private int storeMenuCount;   //카테고리별 판매하는 매장 갯수
   private int storeMenuNum;    // 가게메뉴 고유번호(PK)
   private int storeNum;       // 가게 고유번호(FK)
   private String storeMenuNomal; // 팥/슈프림 붕어빵 판매 여부(Y/N)
   private String storeMenuVegetable; // 야채/김치/만두 판매 여부(Y/N)
   private String storeMenuMini; // 미니 붕어빵 판매 여부(Y/N)
   private String storeMenuPotato; // 고구마 붕어빵 판매 여부(Y/N)
   private String storeMenuIceCream; // 아이스크림/초코 붕어빵 판매 여부(Y/N)
   private String storeMenuCheese; // 치즈 붕어빵 판매 여부(Y/N)
   private String storeMenuPastry; // 페스츄리 붕어빵 판매 여부(Y/N)
   private String storeMenuOthers; // 기타 붕어빵 해당 여부(Y/N)
   
   public int getStoreMenuCount() {
      return storeMenuCount;
   }

   public void setStoreMenuCount(int storeMenuCount) {
      this.storeMenuCount = storeMenuCount;
   }
   public int getStoreMenuNum() {
      return storeMenuNum;
   }
   public void setStoreMenuNum(int storeMenuNum) {
      this.storeMenuNum = storeMenuNum;
   }
   public int getStoreNum() {
      return storeNum;
   }
   public void setStoreNum(int storeNum) {
      this.storeNum = storeNum;
   }
   public String getStoreMenuNomal() {
      return storeMenuNomal;
   }
   public void setStoreMenuNomal(String storeMenuNomal) {
      this.storeMenuNomal = storeMenuNomal;
   }
   public String getStoreMenuVegetable() {
      return storeMenuVegetable;
   }
   public void setStoreMenuVegetable(String storeMenuVegetable) {
      this.storeMenuVegetable = storeMenuVegetable;
   }
   public String getStoreMenuMini() {
      return storeMenuMini;
   }
   public void setStoreMenuMini(String storeMenuMini) {
      this.storeMenuMini = storeMenuMini;
   }
   public String getStoreMenuPotato() {
      return storeMenuPotato;
   }
   public void setStoreMenuPotato(String storeMenuPotato) {
      this.storeMenuPotato = storeMenuPotato;
   }
   public String getStoreMenuIceCream() {
      return storeMenuIceCream;
   }
   public void setStoreMenuIceCream(String storeMenuIceCream) {
      this.storeMenuIceCream = storeMenuIceCream;
   }
   public String getStoreMenuCheese() {
      return storeMenuCheese;
   }
   public void setStoreMenuCheese(String storeMenuCheese) {
      this.storeMenuCheese = storeMenuCheese;
   }
   public String getStoreMenuPastry() {
      return storeMenuPastry;
   }
   public void setStoreMenuPastry(String storeMenuPastry) {
      this.storeMenuPastry = storeMenuPastry;
   }
   public String getStoreMenuOthers() {
      return storeMenuOthers;
   }
   public void setStoreMenuOthers(String storeMenuOthers) {
      this.storeMenuOthers = storeMenuOthers;
   }
   @Override
   public String toString() {
      return "StoreMenuDTO [storeMenuCount=" + storeMenuCount + ", storeMenuNum=" + storeMenuNum + "\n"
            + ", storeNum="   + storeNum + ", storeMenuNomal=" + storeMenuNomal + ", storeMenuVegetable=" + storeMenuVegetable + "\n"
            + ", storeMenuMini=" + storeMenuMini + ", storeMenuPotato=" + storeMenuPotato + ", storeMenuIceCream="+ storeMenuIceCream + "\n"
            + ", storeMenuCheese=" + storeMenuCheese + ", storeMenuPastry=" + storeMenuPastry + ", storeMenuOthers=" + storeMenuOthers + "] \n";
   }


}
