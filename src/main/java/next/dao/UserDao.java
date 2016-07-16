package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.User;

public class UserDao {
	private static final Logger log = LoggerFactory.getLogger(UserDao.class);
	
	public void insert(User user) throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
			}
		};
		jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)",
				pstmtSetter);
		
	}

	public void update(User user) throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getPassword());
				pstmt.setString(2, user.getName());
				pstmt.setString(3, user.getEmail());
				pstmt.setString(4, user.getUserId());
			}
		};
		jdbcTemplate.update("UPDATE USERS SET password=?, name=?, email=? WHERE userId=?",
				pstmtSetter);
	}

	public List<User> findAll() throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				
			}
		};
		
		RowMapper<User> rowMapper = new RowMapper<User>() {
			
			@Override
			public User mapRow(ResultSet rs) throws Exception {
				User obj = null;

				if (rs.next()) {
					obj = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
							rs.getString("email"));
				}
				return obj;
			}
		};
		return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS",
				pstmtSetter, rowMapper);
	}

	public User findByUserId(String userId) throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		PreparedStatementSetter pstmtSetter = new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, userId);
			}
		};
		
		RowMapper<User> rowMapper = new RowMapper<User>() {
			
			@Override
			public User mapRow(ResultSet rs) throws Exception {
				User obj = null;

				if (rs.next()) {
					obj = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
							rs.getString("email"));
				}
				return obj;
			}
		};
		
		return jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?",
				pstmtSetter, rowMapper);
	}
}
