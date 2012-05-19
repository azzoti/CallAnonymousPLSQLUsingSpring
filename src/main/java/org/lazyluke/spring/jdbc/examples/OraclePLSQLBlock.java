package org.lazyluke.spring.jdbc.examples;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.StoredProcedure;

public class OraclePLSQLBlock extends StoredProcedure {
	
	public OraclePLSQLBlock(JdbcTemplate jdbcTemplate, String plsql) {
		super(jdbcTemplate, plsql);
	}

	public OraclePLSQLBlock(DataSource datasource, String plsql) {
		super(datasource, plsql);
	}
	
	@Override
	public String getCallString() {
		// we control the creation of the sql string that is actually sent to the database
		// (by default spring will construct a string {call xxx} where xxx is the sql that 
		// we set in the contructor) 
		return getSql();
	}
	
	@Override
	public boolean isSqlReadyForUse() {
		// stop spring from adding ? parameter placeholders to the sql
		return true;
	}
}