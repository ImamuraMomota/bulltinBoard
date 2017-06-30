package board.contoroller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import board.beans.Comment;
import board.beans.Favorites;
import board.beans.Message;
import board.beans.User;
import board.beans.UserComment;
import board.beans.UserMessage;
import board.service.CommentService;
import board.service.FavoritesService;
import board.service.MessageService;
import board.service.UserService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		List<User> users = new UserService().getUserList();
		List<UserMessage> userMessages = new MessageService().getUserMessage();
		List<UserComment> comments = new CommentService().getComment();
		List<Favorites> favorites = new FavoritesService().getFavorites(loginUser.getId());


		//カテゴリの表示用（Messagesから抽出）
		List<Message> categories = new MessageService().getMessages();
		request.setAttribute("categories", categories);
		request.setAttribute("userMessages",userMessages);
		request.setAttribute("comments", comments);
		request.setAttribute("users", users);
		request.setAttribute("favorites", favorites);
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		List<String> messages =  new ArrayList<>();
		User user = (User) session.getAttribute("loginUser");

		//コメントの追加
		Comment comment = new Comment();
		comment.setText(request.getParameter("text"));
		comment.setMessageId(Integer.parseInt(request.getParameter("messageId")));
		comment.setUserId(user.getId());

		if (isValid(request, messages) == true) {
			new CommentService().register(comment);
			response.sendRedirect("./");
			return;
		}

		session.setAttribute("commentErrorMessages", messages);
		session.setAttribute("comment", comment);
		//request.setAttribute("comment", comment);
		response.sendRedirect("./#message" + comment.getMessageId());
	}

	private boolean isValid(HttpServletRequest request, List<String> errorMessages) {

		String text = request.getParameter("text");

		if (text.length() > 500) {
			errorMessages.add("コメントを500文字以内で入力してください");
		}

		if (StringUtils.isEmpty(text) == true) {
			errorMessages.add("コメントを追加してください");
		}

		if (errorMessages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}


