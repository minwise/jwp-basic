package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.User;

public class UserDao {
	public void insert(User user) {
//		PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
//			
//			@Override
//			public void setValues(PreparedStatement pstmt) throws SQLException {
//				pstmt.setString(1, user.getUserId());
//				pstmt.setString(2, user.getPassword());
//				pstmt.setString(3, user.getName());
//				pstmt.setString(4, user.getEmail());				
//			}
//		};
		
		PreparedStatementSetter pstmtSetter = (PreparedStatement pstmt) -> {
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail());};
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", pstmtSetter);
	}

	public void update(User user) {
		PreparedStatementSetter pstmtSetter = (PreparedStatement pstmt) -> {
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getUserId());
		};
		JdbcTemplate jdbcTemplate = new JdbcTemplate();

		jdbcTemplate.update("UPDATE USERS SET name=?, email=?, password=? WHERE userID = ?", pstmtSetter);
	}
	
	public List<User> findAll() {
		
		RowMapper<User> rm = (ResultSet rs) -> {
			return new User(
					rs.getString("userId"), 
					rs.getString("password"), 
					rs.getString("name"),
					rs.getString("email"));
		};
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		
		return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS",
				rm);
	}

	public User findByUserId(String userId) {
		PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, userId);
			}
		};
		
//		RowMapper<User> rm = new RowMapper<User>() {
//			
//			@Override
//			public User mapRow(ResultSet rs) throws SQLException {
//				if (!rs.next()) {
//					return null;
//				}
//
//				return new User(
//						rs.getString("userId"), 
//						rs.getString("password"), 
//						rs.getString("name"),
//						rs.getString("email"));
//			}
//		};
		
		RowMapper<User> rm = (ResultSet rs) -> {
			return new User(
					rs.getString("userId"), 
					rs.getString("password"), 
					rs.getString("name"),
					rs.getString("email"));
		};
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		
		return jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?",
				rm, pstmtSetter);
	}
}
