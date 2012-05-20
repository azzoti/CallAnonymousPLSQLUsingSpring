package org.lazyluke.spring.jdbc.examples;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.AbstractSqlTypeValue;
import org.springframework.stereotype.Component;

@Component
public class ExampleSpringStoredProcedureCallingComplexPLSQLBlockWithCursorReturn extends OraclePLSQLBlock {
	
	private static final String PLSQL = "" +
			" declare " +  
			"    p_id student_array := null; " +
			"	 l_rc sys_refcursor;" +
			" begin " +
			"    p_id := :inputParameter; " +
			"    ? := 'input parameter first element was = (' || p_id(1).id || ', ' || p_id(1).name || p_id(1).phone_number || ')'; " +
			"    open l_rc for select * from table(p_id) ; " +
			"    ? := l_rc;" +
			" end;";

	
	@Autowired
	public ExampleSpringStoredProcedureCallingComplexPLSQLBlockWithCursorReturn(DataSource datasource) {
		super(datasource, PLSQL);
        declareParameter(new SqlParameter("inputParameter", Types.ARRAY, "STUDENT_ARRAY"));
        declareParameter(new SqlOutParameter("outputParameter1", Types.VARCHAR));
        declareParameter(new SqlOutParameter("outputParameter2", OracleTypes.CURSOR, new BeanPropertyRowMapper<Person>(Person.class)));
	}
	
	public Map<String, Object> executePLSQLBlock(String id) {

		AbstractSqlTypeValue p1 = new AbstractSqlTypeValue() {
			@Override
			protected Object createTypeValue(Connection c, int sqlType,
					String typeName) throws SQLException {
				StructDescriptor structDescr = StructDescriptor.createDescriptor("STUDENT", c);
				STRUCT s1struct = new STRUCT(structDescr, c, new Object[]{1, "mathew", "+44(0)123456789"});
				STRUCT s2struct = new STRUCT(structDescr, c, new Object[]{2, "mark", null});
				ArrayDescriptor arrayDescr = ArrayDescriptor.createDescriptor( "STUDENT_ARRAY", c );
				return new ARRAY( arrayDescr, c, new Object[]{s1struct, s2struct} );
			}
		};
		
		return this.execute(p1);

	}	
}


