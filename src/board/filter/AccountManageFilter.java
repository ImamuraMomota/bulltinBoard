//package board.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import board.beans.User;
//
//@WebFilter("/*")
//public class AccountManageFilter implements Filter {
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//
//		HttpSession session = ((HttpServletRequest) request).getSession();
//
//		String url = ((HttpServletRequest) request).getRequestURI();
//			if(url.endsWith("/accountmanage") || url.endsWith("/signup") || url.endsWith("/edituser")){
//				User user = (User) session.getAttribute("loginUser");
//				if(!user.getDepartmentId().equals("1")){
//					((HttpServletResponse) response).sendRedirect("./");
//					return;
//				}
//			}
//		chain.doFilter(request, response);
//	}
//
//	@Override
//	public void init(FilterConfig config) throws ServletException {
//
//	}
//
//	@Override
//	public void destroy() {
//
//	}
//}
