package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import board.beans.UserComment;
import board.exception.SQLRuntimeException;

public class UserCommentDao {

	public List<UserComment> getUserComments(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_comments ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserComment> dbComment = toUserCommentList(rs);
			return dbComment;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserComment> toUserCommentList(ResultSet rs)
			throws SQLException {

		List<UserComment> dbComment = new ArrayList<>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				String account = rs.getString("account");
				String name = rs.getString("name");
				int messageId = rs.getInt("message_id");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserComment comment = new UserComment();
				comment.setId(id);
				comment.setUserId(userId);
				comment.setAccount(account);
				comment.setName(name);
				comment.setMessageId(messageId);
				comment.setText(text);
				comment.setInsertDate(insertDate);

				dbComment.add(comment);
			}
			return dbComment;
		} finally {
			close(rs);
		}
	}

}

