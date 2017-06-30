package board.service;


import static board.utils.CloseableUtil.*;
import static board.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import board.beans.Message;
import board.beans.UserMessage;
import board.dao.MessageDao;
import board.dao.UserMessageDao;

public class MessageService {

	//新規投稿をDBに登録
	public void register(Message message) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			messageDao.insert(connection, message);

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

	//新規投稿をリスト表示
	private static final int LIMIT_NUM = 1000;
	public List<UserMessage> getUserMessage() {
		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			List<UserMessage> dbUserMessage = messageDao.getUserMessages(connection, LIMIT_NUM);
			commit(connection);
			return dbUserMessage;
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


	//投稿の削除
	public Message DeleteMessage(int messageId) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			Message message = messageDao.DeleteMessage(connection, messageId);

			commit(connection);

			return message;
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

	//絞込み用のメッセージ
	public List<UserMessage> getSelectedMessage(String selectedCategory, String startDate, String endDate) {
		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao userMessageDao = new UserMessageDao();
			List<UserMessage> dbMessage = userMessageDao.getSelectedMessage(connection, selectedCategory, startDate,
					endDate, LIMIT_NUM);
			commit(connection);
			return dbMessage;
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

	//ホームカテゴリー表示用
	public List<Message> getMessages() {
		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			List<Message> dbMessage = messageDao.getMessages(connection, LIMIT_NUM);
			commit(connection);
			return dbMessage;
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


