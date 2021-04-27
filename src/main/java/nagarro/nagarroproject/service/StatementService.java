package nagarro.nagarroproject.service;

import java.util.List;

import nagarro.nagarroproject.model.Statement;

public interface StatementService {
	
	List<Statement> findAll();

	List<Statement> findAllByAccountId(Double id);
	

}
