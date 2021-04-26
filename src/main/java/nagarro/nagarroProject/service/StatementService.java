package nagarro.nagarroProject.service;

import java.util.List;

import nagarro.nagarroProject.model.Statement;

public interface StatementService {
	
	List<Statement> findAll();

	List<Statement> findAllByAccountId(Double id);
	

}
