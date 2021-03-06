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

import org.apache.commons.lang.StringUtils;

import bbs.beans.User;
import bbs.service.LoginService;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		//System.out.println("LoginServlet");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");
		List<String> messages = new ArrayList<String>();

		if (isValid(request, messages)) {
			User user = new LoginService().login(loginId, password);

			HttpSession session = request.getSession();
			if (user != null) {
				session.setAttribute("loginUser", user);
				response.sendRedirect("./");
			} else {
				messages.add("ログインに失敗しました");
				request.setAttribute("errorMessages", messages);
				request.setAttribute("editLogin", loginId);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("errorMessages", messages);
			request.setAttribute("editLogin", loginId);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(loginId)) {
			messages.add("ログインIDを入力してください");
		} else if (!loginId.matches("[a-zA-Z0-9]{6,20}")) {
			messages.add("ログインIDは半角英数字6文字以上20文字以下で入力してください");
		}
		if (StringUtils.isEmpty(password)) {
			messages.add("パスワードを入力してください");
		} else if (!password.matches("[ -~]{6,255}")) {
			messages.add("パスワードは半角文字6文字以上255文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}