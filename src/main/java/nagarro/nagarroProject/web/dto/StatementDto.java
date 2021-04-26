package nagarro.nagarroProject.web.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class StatementDto {

    private Double accountId;

    private BigDecimal fromAmount;
    
    private BigDecimal toAmount;
    
    private String fromDate;
  



	private String toDate;




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
