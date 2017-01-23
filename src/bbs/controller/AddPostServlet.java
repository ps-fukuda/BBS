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

import bbs.beans.Post;
import bbs.beans.User;
import bbs.service.PostService;

@WebServlet(urlPatterns = { "/post" })
public class AddPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		User status = (User) session.getAttribute("loginUser");
		request.setAttribute("userId", status.getId());
		request.getRequestDispatcher("post.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		Post post = setInputPost(request);
		if (isValid(request, messages)) {
			new PostService().register(post);
			response.sendRedirect("./");
		} else {
			request.setAttribute("errorMessages", messages);
			request.setAttribute("editPost", post);
			request.setAttribute("userId", post.getUserId());
			request.getRequestDispatcher("post.jsp").forward(request, response);
		}
	}

	private Post setInputPost(HttpServletRequest request) {

		Post post = new Post();
		post.setSubject(request.getParameter("subject"));
		post.setBody(request.getParameter("body"));
		post.setCategory(request.getParameter("category"));
		post.setUserId(Integer.parseInt(request.getParameter("user_id")));

		return post;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String subject = request.getParameter("subject");
		String body = request.getParameter("body");
		String category = request.getParameter("category");

		if (StringUtils.isEmpty(subject)) {
			messages.add("件名を入力してください");
		} else if (subject.length() > 50) {
			messages.add("件名は50文字以下で入力してください");
		}
		if (StringUtils.isEmpty(body)) {
			messages.add("本文を入力してください");
		} else if (subject.length() > 1000) {
			messages.add("本文は1000文字以下で入力してください");
		}
		if (StringUtils.isEmpty(category)) {
			messages.add("カテゴリーを入力してください");
		} else if (category.length() > 10) {
			messages.add("カテゴリーは10文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
