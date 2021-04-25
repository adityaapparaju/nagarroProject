package nagarro.nagarroProject.repository;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;  
import org.springframework.stereotype.Repository;

import nagarro.nagarroProject.model.Statement;

@Repository
public class StatementRepositoryImpl implements StatementRepository {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("deprecation")
	@Override
	public List<Statement> findAllByAccountId(Double accountId) {
		// TODO Auto-generated method stub
		return  jdbcTemplate.query(
                "select * from statement where account_id = ?",
                new Object[]{accountId},
                (rs, rowNum) ->
                        new Statement(
                                rs.getInt("id"),
                                (rs.getDouble("account_id")),
                                rs.getString("datefield"),
                                new BigDecimal(rs.getString("amount"))
                        )
        );
	}

	@Override
	public List<Statement> findAll() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(
                "select * from statement",
                (rs, rowNum) ->
                        new Statement(
                                rs.getInt("id"),
                                rs.getDouble("account_id"),
                                rs.getString("datefield"),
                                new BigDecimal(rs.getString("amount"))
                        )
        );
	}
	
	
}
