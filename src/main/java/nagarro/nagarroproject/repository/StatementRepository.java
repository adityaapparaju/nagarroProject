package nagarro.nagarroproject.repository;

import java.util.List;

import nagarro.nagarroproject.model.Statement;

public interface StatementRepository {

	List<Statement> findAllByAccountId(Double accountId) ;

	List<Statement> findAll();
}
