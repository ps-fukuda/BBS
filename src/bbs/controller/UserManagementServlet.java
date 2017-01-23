package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.beans.ComplementUser;
import bbs.beans.User;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/userManagement" })
public class UserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		List<ComplementUser> userList = new UserService().getAllComplementUser();

		request.setAttribute("userList", userList);
		// 自分判定(状態ボタン用)
		HttpSession session = request.getSession();
		User status = (User) session.getAttribute("loginUser");
		request.setAttribute("myId", status.getId());

		request.getRequestDispatcher("userManagement.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
	}
}