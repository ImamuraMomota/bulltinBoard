package board.contoroller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import board.beans.Message;
import board.beans.UserMessage;
import board.service.MessageService;


@WebServlet(urlPatterns = { "/searchMessage" })
public class SearchMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {

		String selectedCategory = "0";
		if (!StringUtils.isEmpty(request.getParameter("category"))){
			selectedCategory = request.getParameter("category");
		}

		String startDate= "2017/05/01 00:00:00";
		if (!StringUtils.isEmpty(request.getParameter("startDate"))){
			startDate = request.getParameter("startDate");
			System.out.println(request.getParameter("startDate"));
		}

		String endDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		if (!StringUtils.isEmpty(request.getParameter("endDate"))){
			endDate = request.getParameter("endDate");
//			System.out.println(endDate);
		}

		System.out.println(request.getParameter(startDate));
		System.out.println(request.getParameter(endDate));

		//絞込んだメッセージの出力
		List<UserMessage> userMessages = new MessageService().getSelectedMessage(selectedCategory, startDate, endDate);
		request.setAttribute("userMessages", userMessages);

		//カテゴリの表示用（Messagesから抽出）
		List<Message> categories = new MessageService().getMessages();
		request.setAttribute("categories", categories);


		request.getRequestDispatcher("home.jsp").forward(request, response);
	}

}
