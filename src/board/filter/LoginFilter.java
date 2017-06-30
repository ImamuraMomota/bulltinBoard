package board.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.beans.User;

@WebFilter("/*")
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();
		User user = (User) session.getAttribute("loginUser");


		if (!((HttpServletRequest) request).getRequestURI().contains("css")&&
				!((HttpServletRequest) request).getRequestURI().contains("jpg")) {

			if (!((HttpServletRequest) request).getRequestURI().endsWith("/login") && user == null) {
				((HttpServletResponse) response).sendRedirect("login");

				List<String> messages = new ArrayList<>();
				HttpSession errorSession = ((HttpServletRequest)request).getSession();
				errorSession.setAttribute("errorMessages", messages);

//				((HttpServletRequest)request).getRequestDispatcher("login.jsp").forward(((HttpServletRequest) request), (HttpServletResponse) response);

				messages.add("ログインしてください");

				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) {
	}

	@Override
	public void destroy() {
	}

}

