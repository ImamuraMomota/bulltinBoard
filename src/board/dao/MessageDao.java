package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import board.beans.Message;
import board.exception.NoRowsUpdatedRuntimeException;
import board.exception.SQLRuntimeException;

public class MessageDao {

	//メッセージをデータベースに格納
	public void insert(Connection connection, Message message) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO messages ( ");
			sql.append("user_id");
			sql.append(", title");
			sql.append(", text");
			sql.append(", category");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append("?"); // user_id
			sql.append(", ?"); // title
			sql.append(", ?");//text
			sql.append(", ?");//category
			sql.append(", CURRENT_TIMESTAMP"); // insert_date
			sql.append(", CURRENT_TIMESTAMP"); // update_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, message.getUserId());
			ps.setString(2, message.getTitle());
			ps.setString(3, message.getText());
			ps.setString(4, message.getCategory());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//カテゴリー表示用
	public List<Message> getMessages(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM messages group by category ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Message> dbMessage = toMessageList(rs);
			return dbMessage;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//カテゴリー表示用
	private List<Message> toMessageList(ResultSet rs)
			throws SQLException {

		List<Message> dbMessage = new ArrayList<>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				String title = rs.getString("title");
				String text = rs.getString("text");
				String category = rs.getString("category");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				Message message = new Message();
				message.setId(id);
				message.setUserId(userId);
				message.setTitle(title);
				message.setText(text);
				message.setCategory(category);
				message.setInsertDate(insertDate);

				dbMessage.add(message);
			}
			return dbMessage;
		} finally {
			close(rs);
		}
	}

	//コメントの削除
	public Message DeleteMessage(Connection connection, int messageId) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM messages WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, messageId);

			int updatecount = ps.executeUpdate();
			if (updatecount == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);

		}
		return null;
	}

}

