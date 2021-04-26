package nagarro.nagarroProject.repository;

import java.text.ParseException;
import java.util.List;

import nagarro.nagarroProject.model.Statement;

public interface StatementRepository {

	List<Statement> findAllByAccountId(Double accountId) ;

	List<Statement> findAll();
}
