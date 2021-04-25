package nagarro.nagarroProject.model;

import java.math.BigDecimal;
import java.util.Date;



public class Statement 
{
	private Integer id;

    private Double accountId;
    
    private String dateField;

    private BigDecimal amount;
    
    


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getAccountId() {
		return accountId;
	}

	public void setAccountId(Double accountId) {
		this.accountId = accountId;
	}

	public String getDateField() {
		return dateField;
	}

	public void setDateField(String dateField) {
		this.dateField = dateField;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Statement(Integer id, Double accountId, String date, BigDecimal amount) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.dateField = date;
		this.amount = amount;
	}
	
	
    
    
    
    

}
