package board.service;


import static board.utils.CloseableUtil.*;
import static board.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import board.beans.Favorites;
import board.dao.FavoritesDao;

public class FavoritesService {

	//お気に入りリストに登録
	public void register(Favorites favorites) {

		Connection connection = null;
		try {
			connection = getConnection();

			FavoritesDao favoritesDao = new FavoritesDao();
			favoritesDao.insert(connection, favorites);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	//お気に入りリストゲット
	private static final int LIMIT_NUM = 1000;
	public List<Favorites> getFavorites(int userId) {
		Connection connection = null;
		try {
			connection = getConnection();

			FavoritesDao favoritesDao = new FavoritesDao();
			List<Favorites> dbfavorites = favoritesDao.getFavorites(connection, userId,  LIMIT_NUM);
			commit(connection);
			return dbfavorites;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	//お気に入りを削除
	public Favorites DeleteFavorites(int messageId, int userId) {

		Connection connection = null;
		try {
			connection = getConnection();

			FavoritesDao favoritesDao = new FavoritesDao();
			Favorites favorites = favoritesDao.DeleteFavorites(connection, messageId, userId);

			commit(connection);

			return favorites;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}


