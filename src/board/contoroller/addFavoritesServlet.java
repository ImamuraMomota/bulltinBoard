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

import board.beans.Favorites;
import board.beans.User;
import board.beans.UserComment;
import board.beans.UserMessage;
import board.service.CommentService;
import board.service.FavoritesService;
import board.service.MessageService;
import board.service.UserService;


@WebServlet(urlPatterns = { "/addFavorites" })
public class addFavoritesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			List<String> messages = new ArrayList<>();
			List<User> users = new UserService().getUserList();
			List<UserMessage> userMessages = new MessageService().getUserMessage();
			List<UserComment> comments = new CommentService().getComment();
			List<Favorites> favorites = new FavoritesService().getFavorites(loginUser.getId());


			request.setAttribute("userMessages",userMessages);
			request.setAttribute("comments", comments);
			request.setAttribute("users", users);
			request.setAttribute("favorites", favorites);

		request.getRequestDispatcher("favorite.jsp").forward(request, response);

	}


	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int messageId = Integer.parseInt(request.getParameter("messageId"));
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");


		Favorites favorites = new Favorites();
		favorites.setUserId(user.getId());
		favorites.setMessageId(messageId);

		new FavoritesService().register(favorites);
		response.sendRedirect("./#" + messageId);
	}



}



