package org.lazyluke.spring.jdbc.examples;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

@Component
public class ExampleJdbcDaoSupportDaoUsingPlainSql extends JdbcDaoSupport {

	@Qualifier(value="ExampleDaoDataSource")
	@Autowired
	public final void setExampleDaoDataSource(DataSource ds) {
		setDataSource(ds);
	}
	
	public void doSomethingInTheDatabase() {

		System.out.println();
		System.out.println("===================================");
		System.out.println("Call plain sql with getJdbcTemplate");
		System.out.println("===================================");
		
		doInConnectionExample();
		
		queryForListExample();
	}

	private void queryForListExample() {
		String sql = "" +
				"select 1 id, 'luke' name from dual " +
				"union " +
				"select 2 id, 'john' name from dual " +
				"order by 1";
		List<Map<String, Object>> queryForList = getJdbcTemplate().queryForList(sql);
		for (Map<String, Object> map : queryForList) {
			Set<Entry<String, Object>> entrySet = map.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				System.out.print(entry.getKey()+"="+entry.getValue()+" ");
			}
			System.out.println();
		}
	}

	private void doInConnectionExample() {
		getJdbcTemplate().execute(new ConnectionCallback<String>() {
			public String doInConnection(Connection c) throws SQLException,
					DataAccessException {
				Statement s = c.createStatement();
				String sql = "select 6250251 from dual";
				ResultSet rs = s.executeQuery(sql);
				rs.next();
				System.out.println(rs.getInt(1));
				s.close();
				return null;
			}
		});
	}
	
}
