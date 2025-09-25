package egovframework.carauction;

public class BidInfoVO {
    
    private String aucRegNo;
    private String bidAmount;
    private String depositorName;
    private String bankName;
    private String accountNumber;
    
    // 기본 생성자
    public BidInfoVO() {}
    
    // getter/setter (lombok 없이 수동으로)
    public String getAucRegNo() { return aucRegNo; }
    public void setAucRegNo(String aucRegNo) { this.aucRegNo = aucRegNo; }
    
    public String getBidAmount() { return bidAmount; }
    public void setBidAmount(String bidAmount) { this.bidAmount = bidAmount; }
    
    public String getDepositorName() { return depositorName; }
    public void setDepositorName(String depositorName) { this.depositorName = depositorName; }
    
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
}