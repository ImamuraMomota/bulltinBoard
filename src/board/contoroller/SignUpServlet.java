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

import board.beans.Branch;
import board.beans.Department;
import board.beans.User;
import board.service.BranchService;
import board.service.DepartmentService;
import board.service.UserService;

@WebServlet(urlPatterns = { "/signup" })
	public class SignUpServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;
		private static final String newUser = null;

		@Override
		protected void doGet(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {
			List<User> users = new UserService().getUserList();
			List<Branch> branches  = new BranchService().getBranch();
			List<Department> departments = new DepartmentService().getDepartment();
			request.setAttribute("branches", branches);
			request.setAttribute("departments", departments);
			request.setAttribute("users", users);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}

		@Override
		protected void doPost(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {

			List<String> messages = new ArrayList<>();
			HttpSession session = request.getSession();
			List<Branch> branches  = new BranchService().getBranch();
			List<Department> departments = new DepartmentService().getDepartment();


				User user = new User();
				user.setAccount(request.getParameter("account"));
				user.setPassword(request.getParameter("password"));
				user.setName(request.getParameter("name"));
				user.setBranchId(request.getParameter("branch_id"));
				user.setDepartmentId(request.getParameter("department_id"));


			if (isValid(request, messages) == true) {
				new UserService().register(user);
				response.sendRedirect("accountmanage");
			} else {
				session.setAttribute("errorMessages", messages);
				request.setAttribute("user", user);
				request.setAttribute("branches", branches);
				request.setAttribute("departments", departments);
				request.getRequestDispatcher("signup.jsp").forward(request, response);
			}
		}

		private boolean isValid(HttpServletRequest request, List<String> messages) {
			User newUser =  new UserService().getAccouont(request.getParameter("account"));
			String name = request.getParameter("name");
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			int branchId = Integer.parseInt(request.getParameter("branch_id"));
			int departmentId = Integer.parseInt(request.getParameter("department_id"));
			String confirmingPassword = request.getParameter("confirmingPassword");

			if (StringUtils.isEmpty(name) == true) {
				messages.add("名前を入力してください");
			}

			if (newUser != null) {
				messages.add("他のアカウント名を使用してください");
			}

			if (name.length() > 10) {
				messages.add("名前の欄に10文字以下で入力してください");
			}

			if (StringUtils.isEmpty(account) == true) {
				messages.add("ログインIDを入力してください");
			}
			if (!account.isEmpty()) {
				if (!account.matches("[0-9a-zA-Z]+") || (account.length() < 6 || account.length() > 20)) {
					messages.add("ログインIDを文字以上20文字以下の半角英数字で入力してください");
				}
			}

			if (StringUtils.isEmpty(password) == true) {
				messages.add("パスワードを入力してください");
			}
			if (!password.isEmpty()){
				if (password.length() < 6 || password.length() > 256) {
					messages.add("パスワードを6文字以上255文字以下で入力してください");
				}
			}

			if (!password.isEmpty()) {
				if (StringUtils.isEmpty(confirmingPassword)== true) {
					messages.add("確認用パスワードを入力してください");
				}

				if (!password.equals(confirmingPassword)) {
					messages.add("パスワードが一致しません");
				}
			}

			if (branchId == 0) {
				messages.add("支店を選択してください");
			}

			if (departmentId == 0) {
				messages.add("役職を選択してください");
			}

			if (branchId != 0 && departmentId != 0){
				if ((((branchId != 1) && (departmentId < 2)) || ((branchId == 1) && (departmentId > 2))
						|| (branchId > 1) && (departmentId < 2))) {
					messages.add("支店と役職との組み合わせを確認してください");
				}
			}


			if (messages.size() == 0) {
				return true;
			} else {
				return false;
			}
		}

	}


