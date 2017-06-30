package board.contoroller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.beans.Branch;
import board.beans.Department;
import board.beans.User;
import board.service.AccountService;
import board.service.BranchService;
import board.service.DepartmentService;
import board.service.UserService;

@WebServlet(urlPatterns = { "/accountmanage" })
public class AccountManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {

		List<User> users = new UserService().getUserList();
		List<Branch> branches  = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();
		request.setAttribute("branches", branches);
		request.setAttribute("departments", departments);
		request.setAttribute("users", users);
		request.getRequestDispatcher("./accountmanage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int isStopped = Integer.parseInt(request.getParameter("isStopped"));
		int userId = Integer.parseInt(request.getParameter("userId"));

		new AccountService().isStopped(userId, isStopped);
		response.sendRedirect("./accountmanage");
	}
}


