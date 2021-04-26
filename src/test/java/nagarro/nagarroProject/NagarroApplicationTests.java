package nagarro.nagarroProject;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import nagarro.nagarroProject.config.SecurityConfiguration;
import nagarro.nagarroProject.model.Statement;
import nagarro.nagarroProject.service.StatementService;

import static org.junit.Assert.assertEquals;



@SpringBootTest
class NagarroApplicationTests {

    @Autowired
	private StatementService statementService;
	
	@Test                
	void contextLoads() {
	}
	
	@Test         
	public void findStatementsByAccountId()
	{
		Double accountId = 3.0;
		Statement statement = new Statement(1, 3.0, "09.08.2020", new BigDecimal("535.197875027054"));
		
		List<Statement> statementList = statementService.findAllByAccountId(accountId);
		if (statementList != null  && statementList.size() > 0)
		{
			for (Statement st :statementList )
			{
				if (st.getId() == 1)
				{
					assertEquals(st.getAccountId(), statement.getAccountId());
					assertEquals(st.getDateField(), statement.getDateField());			
					assertEquals(st.getAmount(), statement.getAmount());
					assertEquals(31, statementList.size());
				}
			}
		}
	}
	
	@Test
	public void findStatements()
	{
		Statement statement = new Statement(1, 3.0, "09.08.2020", new BigDecimal("535.197875027054"));
		List<Statement> statementList = statementService.findAll();
		if (statementList != null && statementList.size() > 0)
		{
			for (Statement st :statementList )
			{
				if (st.getId() == 1)
				{
					assertEquals(st.getAccountId(), statement.getAccountId());
					assertEquals(st.getDateField(), statement.getDateField());
					assertEquals(st.getAmount(), statement.getAmount());
				}
			}
			assertEquals(142, statementList.size()); 

		}
		
	}
	

}
