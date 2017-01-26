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

import bbs.beans.CustomPost;
import bbs.beans.Search;
import bbs.beans.User;
import bbs.service.PostService;

@WebServlet({"/index.jsp"})
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		boolean from = true;
		boolean to = true;
		Search search = setInputSearch(request);

		// ユーザー管理へのリンクを表示させるか否か
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if (user.getDepartmentId() == 1) {
			request.setAttribute("isManager", true);
		}

		if (search.getCategory() == null && search.getFrom() == null && search.getTo() == null) {
			List<CustomPost> customPostList = new PostService().customPostAll();
			request.setAttribute("customPostList", customPostList);

			request.getRequestDispatcher("home.jsp").forward(request, response);
		} else {
			if (!StringUtils.isEmpty(search.getCategory())) {
				request.setAttribute("editCategory", search.getCategory());
			}
			if (!StringUtils.isEmpty(search.getFrom())) {
				request.setAttribute("editFrom", search.getFrom());
				from = checkDate(request, search.getFrom());
			}
			if (!StringUtils.isEmpty(search.getTo())) {
				request.setAttribute("editTo", search.getTo());
				to = checkDate(request, search.getTo());
			}

			// from or to に異常がある場合
			if (!(from && to)) {
				List<String> messages = new ArrayList<String>();
				messages.add("検索結果はありません");
				request.setAttribute("errorMessages", messages);
				request.getRequestDispatcher("home.jsp").forward(request, response);
				return;
			}
			List<CustomPost> customPostList = new PostService().search(search);
			if (customPostList == null) {
				List<String> messages = new ArrayList<String>();
				messages.add("検索結果はありません");
				request.setAttribute("errorMessages", messages);
			}
			request.setAttribute("customPostList", customPostList);

			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
	}

	private boolean checkDate(HttpServletRequest request, String inputDate) {
		return inputDate.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}");
	}

	private Search setInputSearch(HttpServletRequest request) {
		Search search = new Search();
		search.setCategory(request.getParameter("category"));
		search.setFrom(request.getParameter("from"));
		search.setTo(request.getParameter("to"));
		return search;
	}
}