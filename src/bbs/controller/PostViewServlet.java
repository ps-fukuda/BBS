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

import bbs.beans.Comment;
import bbs.beans.CustomPost;
import bbs.beans.ViewComment;
import bbs.service.CommentService;
import bbs.service.PostService;

@WebServlet({"/post/view"})
public class PostViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String viewId = request.getParameter("id");
		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		
		if (viewId != null) {
			int id = 0;
			try {
				id = Integer.parseInt(viewId);
			} catch (Exception e) {
				messages.add("リクエストが不正です");
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("../");
				return;
			}
			if (id != 0) {
				List<ViewComment> viewCommentList = new PostService().getViewComment(id);
				CustomPost customPost = new PostService().getCustomPost(id);
				if (customPost == null) {
					messages.add("リクエストが不正です");
					session.setAttribute("errorMessages", messages);
					response.sendRedirect("../");
					return;
				}
				request.setAttribute("viewCommentList", viewCommentList);
				request.setAttribute("customPost", customPost);
				request.setAttribute("loginUser", session.getAttribute("loginUser"));
				request.getRequestDispatcher("/view.jsp").forward(request, response);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		List<String> messages = new ArrayList<String>();
		Comment comment = setInputComment(request);
		if (isValid(request, messages)) {
			new CommentService().register(comment);
			response.sendRedirect("./view?id=" + comment.getPostId());
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("./view?id=" + comment.getPostId());
		}
	}

	private Comment setInputComment(HttpServletRequest request) {

		Comment comment = new Comment();
		comment.setPostId(Integer.parseInt(request.getParameter("post_id")));
		comment.setUserId(Integer.parseInt(request.getParameter("user_id")));
		comment.setBody(request.getParameter("body"));

		return comment;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String body = request.getParameter("body");

		if (StringUtils.isEmpty(body)) {
			messages.add("コメントを入力してください");
		} else if (body.length() > 500) {
			messages.add("コメントは500文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}