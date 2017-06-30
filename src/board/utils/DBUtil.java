package board.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import board.exception.SQLRuntimeException;

//DBとをつなぐユーティリティー
public class DBUtil {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/board_db";
	private static final String USER = "root";
	private static final String PASSWORD = "g12r16a6c2ie";


	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

//コネクションの取得
	public static Connection getConnection() {

		try {
			Connection connection = DriverManager.getConnection(URL, USER,
					PASSWORD);

			connection.setAutoCommit(false);

			return connection;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

//コミット
	public static void commit(Connection connection) {

		try {
			connection.commit();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

//ロールバック
	public static void rollback(Connection connection) {

		try {
			connection.rollback();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

}


