package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementCreator;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.Question;

public class QuestionDao {
	public Question insert(Question question) {
        JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
        String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, question.getWriter());
				pstmt.setString(2, question.getTitle());
				pstmt.setString(3, question.getContents());
				pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
				pstmt.setInt(5, 0);
				return pstmt;
			}
		};
        
		KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return findById(keyHolder.getId());
    }
	
	public void updateCountOfAnswer(Long questionId) {
		JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
		String sql = "UPDATE QUESTIONS SET countOfAnswer=? WHERE questionId=?";
		Question question = findById(questionId);
		
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setInt(1, question.getCountOfComment() + 1);
				pstmt.setLong(2, question.getQuestionId());
			}
		};
		
		jdbcTemplate.update(sql, pss);
	}
	
	public void delete(Long questionId) {
		JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
        String sql = "DELETE FROM QUESTIONS WHERE questionId = ?";
        jdbcTemplate.update(sql, questionId);
	}
	
	public List<Question> findAll() {
		JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
		String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
				+ "order by questionId desc";
		
		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				return new Question(rs.getLong("questionId"),
						rs.getString("writer"), rs.getString("title"), null,
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfAnswer"));
			}
			
		};
		
		return jdbcTemplate.query(sql, rm);
	}

	public Question findById(long questionId) {
		JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
				+ "WHERE questionId = ?";
		
		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				return new Question(rs.getLong("questionId"),
						rs.getString("writer"), rs.getString("title"),
						rs.getString("contents"),
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfAnswer"));
			}
		};
		
		return jdbcTemplate.queryForObject(sql, rm, questionId);
	}
}
