package nagarro.nagarroproject.model;

import java.util.List;

public class Account {
	
	 private Integer id;

    private String accountType;

    private String accountNumber;

	public Integer getId() {
		return id;
	}
	
	private List<Statement> statement;

	public List<Statement> getStatement() {
		return statement;
	}

	public void setStatement(List<Statement> statement) {
		this.statement = statement;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	

}
