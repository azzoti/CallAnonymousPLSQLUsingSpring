package org.lazyluke.spring.jdbc.examples;

import java.sql.Types;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Component;

@Component
public class ExampleSpringStoredProcedureCallingPLSQLBlockWithCursorReturn extends OraclePLSQLBlock {
	
	private static final String PLSQL = "" +
			" declare " +  
			"    p_id varchar2(20) := null; " +
			"	 l_rc sys_refcursor;" +
			" begin " +
			"    p_id := :inputParameter; " +
			"    ? := 'input parameter was = ' || p_id;" +
			"    open l_rc for " +
			"        select 1 id, 'hello' name, '+44(0)131123456' phone_number from dual " +
			"        union " +
			"        select 2, 'peter', null from dual; " +
			"    ? := l_rc;" +
			" end;";
	
	@Autowired
	public ExampleSpringStoredProcedureCallingPLSQLBlockWithCursorReturn(DataSource datasource) {
		super(datasource, PLSQL);
        declareParameter(new SqlParameter("inputParameter", Types.VARCHAR));
        declareParameter(new SqlOutParameter("outputParameter1", Types.VARCHAR));
        declareParameter(new SqlOutParameter("outputParameter2", OracleTypes.CURSOR, new BeanPropertyRowMapper<Person>(Person.class)));
	}
	
	public void executePLSQLBlock(String id) {
		System.out.println();
		System.out.println("====================================================");
		System.out.println("Call plsql block with string input and string output and cursor output via spring StoredProcedure");
		System.out.println("====================================================");
		
		Map<String, Object> result1 = this.execute(id);
		for (Entry<String, Object> entry : result1.entrySet()) {
    		System.out.println(entry.getKey() + "=" + entry.getValue());
		}
	}	
}


