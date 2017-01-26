package bbs.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.beans.User;
import bbs.service.UserService;

//@WebFilter("/*")
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//System.out.println("Login");
		String target = ((HttpServletRequest)request).getRequestURI();
		// ログインしていない && RequestURLが/login || /style.css でない
		if (!isLogined(request) && !target.equals("/BBS/login.jsp") && !target.equals("/BBS/login") && !target.equals("/BBS/css/style.css")) {
			((HttpServletResponse) response).sendRedirect("/BBS/login");
			return;
		}

		chain.doFilter(request, response);
	}

	private boolean isLogined(ServletRequest request) {
		HttpSession session = ((HttpServletRequest)request).getSession();
		User status = (User) session.getAttribute("loginUser");
		if (status != null) {
			// loginUserを最新にする
			User user = new UserService().getUser(status.getId());
			session.setAttribute("loginUser", user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void destroy() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自動生成されたメソッド・スタブ

	}

}