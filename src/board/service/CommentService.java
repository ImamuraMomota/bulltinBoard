package board.service;


import static board.utils.CloseableUtil.*;
import static board.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import board.beans.Comment;
import board.beans.UserComment;
import board.dao.CommentDao;
import board.dao.UserCommentDao;

public class CommentService {


	public void register(Comment comment) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.insert(connection, comment);

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

	private static final int LIMIT_NUM = 1000;
	public List<UserComment> getComment() {
		Connection connection = null;
		try {
			connection = getConnection();

			UserCommentDao commnetDao = new UserCommentDao();
			List<UserComment> dbComment = commnetDao.getUserComments(connection, LIMIT_NUM);
			commit(connection);
			return dbComment;
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

	public Comment DeleteComment(int commentId) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			Comment comment = commentDao.DeleteComment(connection, commentId);

			commit(connection);

			return comment;
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


