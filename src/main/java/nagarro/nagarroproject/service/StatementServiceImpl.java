package nagarro.nagarroproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nagarro.nagarroproject.model.Statement;
import nagarro.nagarroproject.repository.StatementRepository;

@Service
public class StatementServiceImpl implements StatementService
{

	@Autowired
    private StatementRepository statementRepository;

	

	@Override
	public List<Statement> findAll() {
		return statementRepository.findAll();
	}



	@Override
	public List<Statement> findAllByAccountId(Double id) {
		return statementRepository.findAllByAccountId(id);
	}

}
