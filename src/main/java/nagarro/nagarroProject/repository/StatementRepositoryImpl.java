package nagarro.nagarroProject.repository;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import nagarro.nagarroProject.config.SecurityConfiguration;
import nagarro.nagarroProject.model.Statement;

@Repository
public class StatementRepositoryImpl implements StatementRepository {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@SuppressWarnings("deprecation")
	@Override
	public List<Statement> findAllByAccountId(Double accountId) {
		// TODO Auto-generated method stub
		return  jdbcTemplate.query(
                "select a.account_number as accNumber, "
                + "s.ID as stId, s.datefield as stDateField, s.account_id as stAccId,"
                + "s.amount as stAmount from account a join statement s on a.ID = s.account_id  where s.account_id = ?",
                new Object[]{accountId},
                (rs, rowNum) ->
                        new Statement(
                                rs.getInt("stId"),
                                (rs.getDouble("stAccId")),
                                rs.getString("stDateField"),
                                new BigDecimal(rs.getString("stAmount")),
                                passwordEncoder.encode(rs.getString("accNumber"))
                        )
				);
	}

	@Override
	public List<Statement> findAll() {             
		// TODO Auto-generated method stub
		return jdbcTemplate.query(
				 "select a.account_number as accNumber, "
			                + "s.ID as statementId, s.datefield as stDateField, s.account_id as stAccId,"
			                + "s.amount as stAmount from account a join statement s on a.ID = s.account_id",
			                (rs, rowNum) ->
                        new Statement(
                                rs.getInt("statementId"),
                                rs.getDouble("stAccId"),
                                rs.getString("stDateField"),
                                new BigDecimal(rs.getString("stAmount")),
                                passwordEncoder.encode(rs.getString("accNumber"))
                        )
        );
	}
	
	
}
