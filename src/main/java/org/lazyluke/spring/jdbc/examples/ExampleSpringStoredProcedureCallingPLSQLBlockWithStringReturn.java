package org.lazyluke.spring.jdbc.examples;

import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Component;

@Component
public class ExampleSpringStoredProcedureCallingPLSQLBlockWithStringReturn extends OraclePLSQLBlock {
	
	private static final String PLSQL = "" +
	" declare " +  
	"      p_id varchar2(20) := null; " +
	" begin " +
	"    p_id := :inputParameter; " +
	"    ? := 'input parameter was = ' || p_id;" +
	" end;";
	
	@Autowired
	public ExampleSpringStoredProcedureCallingPLSQLBlockWithStringReturn(DataSource datasource) {
		super(datasource, PLSQL);
        declareParameter(new SqlParameter("inputParameter", Types.VARCHAR));
        declareParameter(new SqlOutParameter("outputParameter", Types.VARCHAR));
	}
	
	public Map<String, Object> executePLSQLBlock(String id) {
		return this.execute(id);
	}
}


