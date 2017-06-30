package board.contoroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.service.MessageService;


@WebServlet(urlPatterns = { "/deleteMessage" })
public class DeleteMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("home.jsp").forward(request, response);

	}


	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int messageId = Integer.parseInt(request.getParameter("messageId"));

		new MessageService().DeleteMessage(messageId);
		response.sendRedirect("./");
	}
}



