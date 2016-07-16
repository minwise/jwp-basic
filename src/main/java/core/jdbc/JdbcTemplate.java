package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public class JdbcTemplate {
	public void update(String sql, PreparedStatementSetter pstmtSetter) throws Exception {
		try (Connection con = ConnectionManager.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmtSetter.setValues(pstmt);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(String sql, Object... parameters) throws Exception {
		update(sql, createPreparedStatementSetter(parameters));
	}
	
	public <T> List<T> query(String sql, PreparedStatementSetter pstmtSetter,
			RowMapper<T> rowMapper) throws Exception {
		ResultSet rs = null;
		
		try (Connection con = ConnectionManager.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			List<T> objLists = new ArrayList<T>();
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				objLists.add(rowMapper.mapRow(rs));
			}
			
			return objLists;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
	
	public <T> T queryForObject(String sql, PreparedStatementSetter pstmtSetter,
			RowMapper<T> rowMapper) throws Exception {
		ResultSet rs = null;
		
		try (Connection con = ConnectionManager.getConnection(); 
			 PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmtSetter.setValues(pstmt);
			rs = pstmt.executeQuery();
			
			T obj = rowMapper.mapRow(rs);
			
			return obj;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
	
	private PreparedStatementSetter createPreparedStatementSetter(Object... parameters) {
		return new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < parameters.length; i++) {
					pstmt.setObject(i + 1, parameters[i]);
				}
			}
		};
	}
}
