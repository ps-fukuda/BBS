package bbs.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.User;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/status" })
public class UserStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String isStopped = request.getParameter("is_stopped");
		if (isStopped != null) {
			try {
				boolean status = Boolean.valueOf(isStopped);
			} catch (Exception e) {
				// boolじゃない場合
				return;
			}
			User user = setInputUser(request);
			new UserService().setStatus(user);
			response.sendRedirect("userManagement");
		} else {
			// nullの場合
			return;
		}
	}

	private User setInputUser(HttpServletRequest request) {

		User user = new User();
		user.setId(Integer.parseInt(request.getParameter("id")));
		user.setStopped(Boolean.valueOf(request.getParameter("is_stopped")));

		return user;
	}
}
