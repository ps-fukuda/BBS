package bbs.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class AuthorityFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("Authority");
		String target = ((HttpServletRequest)request).getRequestURI();
		HttpSession session = ((HttpServletRequest)request).getSession();
		User user = (User) session.getAttribute("loginUser");
		List<String> messages = new ArrayList<String>();

		// 停止中のユーザーの場合
		if (user != null && user.isStopped()) {
			// loginUserを削除
			session.removeAttribute("loginUser");
			messages.add("あなたのアカウントは現在停止中です");
			session.setAttribute("errorMessages", messages);
			((HttpServletResponse) response).sendRedirect("login");
			return;
		}

		// 総務部じゃない場合
		if (!isGeneralRole(request, user)) {
			if (target.equals("/BBS/signup") || target.equals("/BBS/editUser") || target.equals("/BBS/userManagement")) {
				messages.add("そのページにアクセスする権限がありません");
				session.setAttribute("errorMessages", messages);
				((HttpServletResponse) response).sendRedirect("./");
				return;
			}
		}
		// 情報部がindexを表示した場合
		if (isInformationRole(request, user) && (target.equals("/BBS/") || target.equals("/BBS/view") || target.equals("/BBS/view.jsp"))) {
			request.setAttribute("deletePost", true);
		}
		// 支店長がindexを表示した場合
		if (isManagerRole(request, user) && (target.equals("/BBS/") || target.equals("/BBS/view") || target.equals("/BBS/view.jsp"))) {
			List<Integer> userIdList = new UserService().getUserIdList(user.getBranchId());
			request.setAttribute("userIdList", userIdList);
		}

		chain.doFilter(request, response);
	}

	private boolean isGeneralRole(ServletRequest request, User user) {
		if (user != null && user.getDepartmentId() == 1) {
			return true;
		}
		return false;
	}

	private boolean isInformationRole(ServletRequest request, User user) {
		if (user != null && user.getDepartmentId() == 2) {
			return true;
		}
		return false;
	}

	private boolean isManagerRole(ServletRequest request, User user) {
		if (user != null && user.getDepartmentId() == 3) {
			return true;
		}
		return false;
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
