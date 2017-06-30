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

@WebServlet(urlPatterns = { "/edituser" })
	public class EditServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;

		@Override
		protected void doGet(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {
			User editUser = new UserService().getUser(Integer.parseInt(request.getParameter("id")));
			List<Branch> branches  = new BranchService().getBranch();
			List<Department> departments = new DepartmentService().getDepartment();
			request.setAttribute("editUser", editUser);
			request.setAttribute("branches", branches);
			request.setAttribute("departments", departments);
			request.getRequestDispatcher("edituser.jsp").forward(request, response);
		}

		@Override
		protected void doPost(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {

			List<String> messages = new ArrayList<>();
			HttpSession session = request.getSession();
			String previousPass = new UserService().getUser(Integer.parseInt(request.getParameter("editId"))).getPassword();

			User editUser = new User();
			editUser.setId(Integer.parseInt(request.getParameter("editId")));
			editUser.setAccount(request.getParameter("account"));
			boolean passwordCheck;
			if(request.getParameter("password").equals("") && request.getParameter("confirmingPassword").equals("")){
				editUser.setPassword(previousPass);
				passwordCheck = true;
			} else {
				editUser.setPassword(request.getParameter("password"));
				passwordCheck = false;
			}
			editUser.setName(request.getParameter("name"));
			editUser.setBranchId(request.getParameter("branch_id"));
			editUser.setDepartmentId(request.getParameter("department_id"));

			if (isValid(request, messages, editUser) == true) {
				new UserService().update(editUser, passwordCheck);
				response.sendRedirect("accountmanage");
			} else {
				session.setAttribute("errorMessages", messages);
				request.setAttribute("user", editUser);
				response.sendRedirect("edituser?id=" + request.getParameter("editId"));
			}
		}

		private boolean isValid(HttpServletRequest request, List<String> messages, User editUser) {
			User newUser =  new UserService().getAccouont(request.getParameter("account"));
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			int branchId = Integer.parseInt(request.getParameter("branch_id"));
			int departmentId = Integer.parseInt(request.getParameter("department_id"));
			String confirmingPassword = request.getParameter("confirmingPassword");


			if (StringUtils.isEmpty(name) == true) {
				messages.add("名前を入力してください");
			}

			if (newUser != null && (editUser.getId() != newUser.getId())) {
				messages.add("指定のログインIDは使用できません");
			}

			if (name.length() > 10) {
				messages.add("名前の欄に10文字以下で入力してください");
			}

			if (StringUtils.isEmpty(account) == true) {
				messages.add("アカウント名を入力してください");
			}
			if (!account.isEmpty()) {
				if (account.length() < 6 || account.length() > 20) {
				messages.add("アカウント名を6文字以上20文字以下で入力してください");
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

