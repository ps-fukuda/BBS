package bbs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.beans.User;
import bbs.service.LoginService;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		System.out.println("LoginServlet");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");
		User user = new LoginService().login(loginId, password);

		HttpSession session = request.getSession();
		if (user != null) {
			session.setAttribute("loginUser", user);
			response.sendRedirect("./");
		} else {
			List<String> messages = new ArrayList<String>();
			messages.add("ログインに失敗しました。");
			request.setAttribute("errorMessages", messages);
			request.setAttribute("editLogin", loginId);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}