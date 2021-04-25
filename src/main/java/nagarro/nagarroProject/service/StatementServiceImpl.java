package nagarro.nagarroProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nagarro.nagarroProject.model.Statement;
import nagarro.nagarroProject.repository.StatementRepository;

@Service
public class StatementServiceImpl implements StatementService
{

	@Autowired
    private StatementRepository statementRepository;

	

	@Override
	public List<Statement> findAll() {
		// TODO Auto-generated method stub
		return statementRepository.findAll();
	}



	@Override
	public List<Statement> findAllByAccountId(Double id) {
		// TODO Auto-generated method stub
		return statementRepository.findAllByAccountId(id);
	}

}
