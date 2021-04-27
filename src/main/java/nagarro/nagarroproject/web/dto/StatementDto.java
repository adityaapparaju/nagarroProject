package nagarro.nagarroproject.web.dto;

import java.math.BigDecimal;


public class StatementDto {

    private Double accountId;

    private BigDecimal fromAmount;
    
    private BigDecimal toAmount;
    
    private String fromDate;
  



	private String toDate;


    public boolean isValidAccount()
    {
    	return accountId != null && accountId >=0.0;
    }

	public Double getAccountId() {
		return accountId;
	}




	public void setAccountId(Double accountId) {
		this.accountId = accountId;
	}




	public BigDecimal getFromAmount() {
		return fromAmount;
	}




	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}




	public BigDecimal getToAmount() {
		return toAmount;
	}




	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}




	public String getFromDate() {
		return fromDate;
	}




	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}




	public String getToDate() {
		return toDate;
	}




	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
  

   
}
