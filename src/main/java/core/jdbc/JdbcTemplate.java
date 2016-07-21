package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.dao.UserDao;
import next.model.User;

public class JdbcTemplate {
	private static final Logger log = LoggerFactory.getLogger(JdbcTemplate.class);
	
	public void update(String sql, PreparedStatementSetter pstmtSetter) throws DataAccessException {
		try (Connection con = ConnectionManager.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmtSetter.setValues(pstmt);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} 
	}
	
	public void update(String sql, Object... parameters) throws DataAccessException {
		update(sql, createPreparedStatementSetter(parameters));
	}
	
	public <T> T queryForObject(String sql, RowMapper<T> rm, PreparedStatementSetter pstmtSetter) throws DataAccessException {
		List<T> list = query(sql, rm, pstmtSetter);
		log.debug(list.toString());
		if (list.isEmpty()) {
			return null;
		}
		
		return list.get(0);
	}
	
	public <T> T queryForObject(String sql, RowMapper<T> rm, Object... parameters) throws DataAccessException {
		return queryForObject(sql, rm, createPreparedStatementSetter(parameters));
	}
	
	public <T> List<T> query(String sql, RowMapper<T> rm, PreparedStatementSetter pstmtSetter) throws DataAccessException {
		ResultSet rs = null;
		
		try (Connection con = ConnectionManager.getConnection();
				 PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmtSetter.setValues(pstmt);
			rs = pstmt.executeQuery();

			List<T> list = new ArrayList<T>();
			
			while (rs.next()) {
				list.add(rm.mapRow(rs));
			}
			return list;
		}catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
	}
	
	public <T> List<T> query(String sql, RowMapper<T> rm, Object... parameters) throws DataAccessException {
		return query(sql, rm, createPreparedStatementSetter(parameters));
	}
	
	private PreparedStatementSetter createPreparedStatementSetter(Object... parameters) {
		return new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < parameters.length; i++) {
					pstmt.setObject(i+1, parameters[i]);
				}
			}
		};
	}
}
