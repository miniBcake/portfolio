package model.dto;
public class StorePaymentDTO {
   private int storPaymentCount;               //결제방식별 사용가능한 매장갯수
   private int storePaymentNum;               //결제방식 고유번호(PK)
   private int storeNum;                     //가게 고유번호(FK)
   private String storePaymentCashmoney;         //현금 결제 가능(Y/N)
   private String storePaymentCard;            //카드 결제 가능(Y/N)
   private String storePaymentAccountTransfer;      //계좌이체 결제 가능(Y/N)
   
   
   public int getStorPaymentCount() {
      return storPaymentCount;
   }
   public void setStorPaymentCount(int storPaymentCount) {
      this.storPaymentCount = storPaymentCount;
   }
   public int getStorePaymentNum() {
      return storePaymentNum;
   }
   public void setStorePaymentNum(int storePaymentNum) {
      this.storePaymentNum = storePaymentNum;
   }
   public int getStoreNum() {
      return storeNum;
   }
   public void setStoreNum(int storeNum) {
      this.storeNum = storeNum;
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
   public String getStorePaymentAccountTransfer() {
      return storePaymentAccountTransfer;
   }
   public void setStorePaymentAccountTransfer(String storePaymentAccountTransfer) {
      this.storePaymentAccountTransfer = storePaymentAccountTransfer;
   }
   @Override
   public String toString() {
      return "StorePaymentDTO [storPaymentCount=" + storPaymentCount + ", storePaymentNum=" + storePaymentNum + "\n"
            + ", storeNum=" + storeNum + ", storePaymentCashmoney=" + storePaymentCashmoney 
            + ", storePaymentCard="   + storePaymentCard + ", storePaymentAccountTransfer=" + storePaymentAccountTransfer + "] \n";
   }      
}
   
   