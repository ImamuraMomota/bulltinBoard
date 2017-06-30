package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import board.beans.Favorites;
import board.exception.NoRowsUpdatedRuntimeException;
import board.exception.SQLRuntimeException;

public class FavoritesDao {
	//お気に入りへの登録
	public void insert(Connection connection, Favorites favorites) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO favorites ( ");
			sql.append("user_id");
			sql.append(", message_id");
			sql.append(") VALUES (");
			sql.append("?"); // user_id
			sql.append(", ?");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, favorites.getUserId());
			ps.setInt(2, favorites.getMessageId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	//お気に入りの表示
	public List<Favorites> getFavorites(Connection connection, int userId, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM favorites where user_id = ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			List<Favorites> dbFavorites = toFavoritesList(rs);
			return dbFavorites;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}




	//お気に入りリストのゲット
	private List<Favorites> toFavoritesList(ResultSet rs)
			throws SQLException {

		List<Favorites> dbFavorites = new ArrayList<>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				int messageId = rs.getInt("message_id");

				Favorites favorites = new Favorites();
				favorites.setId(id);
				favorites.setUserId(userId);
				favorites.setMessageId(messageId);

				dbFavorites.add(favorites);
			}
			return dbFavorites;
		} finally {
			close(rs);
		}
	}

	//お気に入りリストの削除
	public Favorites DeleteFavorites(Connection connection, int messageId, int userId) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM favorites WHERE user_id = ?");
			sql.append(" and message_id = ?; ");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, userId);
			ps.setInt(2, messageId);

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

