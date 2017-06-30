package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import board.beans.UserMessage;
import board.exception.SQLRuntimeException;

public class UserMessageDao {

	public List<UserMessage> getUserMessages(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_messages ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserMessage> dbUserMessage = toUserMessageList(rs);
			return dbUserMessage;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//絞込み用
	public List<UserMessage> getSelectedMessage(Connection connection, String selectedCategory, String startDate,
			String endDate, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_messages WHERE ");
			if (selectedCategory != "0") {
				sql.append("category = ? AND ");
			}
			sql.append("insert_date BETWEEN ? AND ? ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			int count = 1;
			if (selectedCategory != "0") ps.setString(count++, selectedCategory);
			ps.setString(count++, startDate);
			ps.setString(count++, endDate);

			ResultSet rs = ps.executeQuery();
			List<UserMessage> dbUserMessage = toUserMessageList(rs);
			return dbUserMessage;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserMessage> toUserMessageList(ResultSet rs)
			throws SQLException {

		List<UserMessage> dbUserMessage = new ArrayList<>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				int branchId = rs.getInt("branch_id");
				String account = rs.getString("account");
				String name = rs.getString("name");
				String title = rs.getString("title");
				String text = rs.getString("text");
				String category = rs.getString("category");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserMessage Usermessage = new UserMessage();
				Usermessage.setId(id);
				Usermessage.setUserId(userId);
				Usermessage.setBranchId(branchId);
				Usermessage.setAccount(account);
				Usermessage.setName(name);
				Usermessage.setTitle(title);
				Usermessage.setText(text);
				Usermessage.setCategory(category);
				Usermessage.setInsertDate(insertDate);

				dbUserMessage.add(Usermessage);
			}
			return dbUserMessage;
		} finally {
			close(rs);
		}
	}

}
