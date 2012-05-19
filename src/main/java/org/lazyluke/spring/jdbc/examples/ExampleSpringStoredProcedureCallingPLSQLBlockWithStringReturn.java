package org.lazyluke.spring.jdbc.examples;

import java.sql.Types;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
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
	
	
	public void executePLSQLBlock(String id) {
		System.out.println();
		System.out.println("====================================================");
		System.out.println("Call simple plsql block with Spring StoredProcedure");
		System.out.println("====================================================");
		
		Map<String, Object> result1 = this.execute(id);
		for (Entry<String, Object> entry : result1.entrySet()) {
    		System.out.println(entry.getKey() + "=" + entry.getValue());
		}
	}
	
}


