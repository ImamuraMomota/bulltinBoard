package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import board.beans.User;
import board.exception.NoRowsUpdatedRuntimeException;
import board.exception.SQLRuntimeException;

public class UserDao {

	public User getLoginUser(Connection connection, String account, String Password) {

		PreparedStatement ps = null;

		try {
			String sql = "SELECT * FROM users WHERE account = ? AND password = ? AND is_stopped = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, account);
			ps.setString(2, Password);
			ps.setInt(3, 0);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			}
			else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			}
			else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//ユーザーの情報をまとめてとってくるためのSQL文
	public List<User> getUserList(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users ");
			sql.append("ORDER BY branch_id ASC; ");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<User> dbuser = toUserList(rs);
			return dbuser;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//ユーザー情報をまとめてとってくる（管理画面）
	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> dbuser = new ArrayList<>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String account = rs.getString("account");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String branchId = rs.getString("branch_id");
				String departmentId = rs.getString("department_id");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");
				int isStopped = rs.getInt("is_stopped");


				User user = new User();
				user.setId(id);
				user.setAccount(account);
				user.setPassword(password);
				user.setName(name);
				user.setBranchId(branchId);
				user.setDepartmentId(departmentId);
				user.setInsertDate(insertDate);
				user.setUpdateDate(updateDate);
				user.setIsStopped(isStopped);

				dbuser.add(user);
			}
			return dbuser;
		} finally {
			close(rs);
		}
	}

	//新規ユーザーの登録
	public void insert(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users (");
			sql.append("account");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append("?"); //account
			sql.append(", ?"); //password
			sql.append(", ?"); //name
			sql.append(", ?"); //branchId
			sql.append(", ?"); //departmentId
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getAccount());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getBranchId());
			ps.setString(5, user.getDepartmentId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//ユーザー情報の更新
	public void update(Connection connection, User editUser) {


		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET ");
			sql.append("account = ?");
			sql.append(", password = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", department_id = ?");
			sql.append(", update_date = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" id = ?");
//			sql.append(" AND");
//			sql.append(" update_date = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, editUser.getAccount());
			ps.setString(2, editUser.getPassword());
			ps.setString(3, editUser.getName());
			ps.setString(4, editUser.getBranchId());
			ps.setString(5, editUser.getDepartmentId());
			ps.setInt(6, editUser.getId());

			System.out.println(ps);

			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);

		}
	}

	//ユーザー編集用（一人だけの情報をとってくる）
	public User getUser(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//重複しているアカウントの検索
	public User getAccount(Connection connection, String account) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE account = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, account);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	//アカウントの停止
	public User isActivate(Connection connection, int userId, int isStopped) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET ");
			sql.append("is_stopped = ? where id =?");

			ps = connection.prepareStatement(sql.toString());


			ps.setInt(1, isStopped);
			ps.setInt(2, userId);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);

		}
		return null;
	}

	//ユーザーの削除
	public User DeleteUser(Connection connection, int userId) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM users WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, userId);

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

