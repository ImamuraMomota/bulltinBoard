package board.contoroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.beans.User;
import board.service.FavoritesService;

@WebServlet(urlPatterns = { "/deleteFavorites" })
public class DeleteFavoritesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("home.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		int userId = user.getId();
		int messageId = Integer.parseInt(request.getParameter("messageId"));

		new FavoritesService().DeleteFavorites(messageId, userId);
		response.sendRedirect("./");
	}
}
