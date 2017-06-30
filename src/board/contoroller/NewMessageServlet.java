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

import board.beans.Message;
import board.beans.User;
import board.service.MessageService;

@WebServlet(urlPatterns = { "/newMessage" })
public class NewMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Message> categories = new MessageService().getMessages();
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("newmessage.jsp").forward(request, response);
	}


	@SuppressWarnings("unused")
	@Override
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		List<String> messages =  new ArrayList<>();
		List<String> errorMessages = new ArrayList<>();
		List<Message> categories = new MessageService().getMessages();
		User user = (User) session.getAttribute("loginUser");

		Message message = new Message();
		message.setTitle(request.getParameter("title"));
		message.setText(request.getParameter("text"));
		if (request.getParameter("category") == "") {
			message.setCategory(request.getParameter("newcategory"));
		} else {
			message.setCategory(request.getParameter("category"));
		}
		message.setUserId(user.getId());

	if (isValid(request, messages) == true) {
		new MessageService().register(message);
		response.sendRedirect("./");
	} else {
		session.setAttribute("errorMessages", messages);
		request.setAttribute("message", message);
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("newmessage.jsp").forward(request, response);
	}
	}

	private boolean isValid(HttpServletRequest request, List<String> errorMessages) {
		String title = request.getParameter("title");
		String text = request.getParameter("text");
		String category = request.getParameter("category");
		String newcategory = request.getParameter("newcategory");


		if (StringUtils.isEmpty(title) == true) {
			errorMessages.add("タイトルを入力してください");
		}

		if (title.length() > 50) {
			errorMessages.add("タイトルを50文字以内で入力してください");
		}

		if (StringUtils.isEmpty(category) && StringUtils.isEmpty(newcategory) == true) {
			errorMessages.add("カテゴリーを入力してください");
		}

		if (StringUtils.isEmpty(text) == true) {
			errorMessages.add("本文を入力してください");
		}

		if (text.length() > 1000) {
			errorMessages.add("本文を1000文字以内で入力してください");
		}

		if (errorMessages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
