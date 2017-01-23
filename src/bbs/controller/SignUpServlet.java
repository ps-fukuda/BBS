package bbs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bbs.beans.User;
import bbs.service.BranchService;
import bbs.service.DepartmentService;
import bbs.service.UserService;

@WebServlet({"/signup"})
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		Map<String, String> branchMap = new BranchService().findAll();
		Map<String, String> departmentMap = new DepartmentService().findAll();
		session.setAttribute("branch", branchMap);
		session.setAttribute("department", departmentMap);

		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		User user = setInputUser(request);

		if (isValid(request, messages)) {
			UserService userService = new UserService();
			// ログインIDの重複チェック
			if (!userService.duplicateCheck(user)) {
				messages.add("そのログインIDは既に使用されています");
				request.setAttribute("errorMessages", messages);
				request.getRequestDispatcher("signup.jsp").forward(request, response);
				return;
			}
			// 登録
			userService.register(user);
			session.removeAttribute("branch");
			session.removeAttribute("department");
			response.sendRedirect("./");
		} else {
			request.setAttribute("errorMessages", messages);
			request.setAttribute("editUser", user);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private User setInputUser(HttpServletRequest request) {

		User user = new User();
		user.setLoginId(request.getParameter("login_id"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
		user.setDepartmentId(Integer.parseInt(request.getParameter("department_id")));

		return user;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");
		String passwordConfirm = request.getParameter("password_confirm");
		String name = request.getParameter("name");
		String branchId = request.getParameter("branch_id");
		String departmentId = request.getParameter("department_id");
		boolean passwordFlag = false;
		boolean passwordConfirmFlag = false;

		if (StringUtils.isEmpty(loginId)) {
			messages.add("ログインIDを入力してください");
		} else if (!loginId.matches("[a-zA-Z0-9]{6,20}")) {
			messages.add("ログインIDは半角英数字6文字以上20文字以下で入力してください");
		}
		if (StringUtils.isEmpty(password)) {
			messages.add("パスワードを入力してください");
		} else if (!password.matches("[ -~]{6,255}")) {
			messages.add("パスワードは半角文字6文字以上255文字以下で入力してください");
		} else {
			passwordFlag = true;
		}
		if (StringUtils.isEmpty(password)) {
			messages.add("パスワード(確認用)を入力してください");
		} else if (!password.matches("[ -~]{6,255}")) {
			messages.add("パスワード(確認用)は半角文字6文字以上255文字以下で入力してください");
		} else {
			passwordConfirmFlag = true;
		}
		if (passwordFlag && passwordConfirmFlag) {
			if (!password.equals(passwordConfirm)) {
				messages.add("パスワードとパスワード(確認用)の値が不一致です");
			}
		}
		if (StringUtils.isEmpty(name)) {
			messages.add("名前を入力してください");
		} else if (name.length() > 10) {
			messages.add("名前は10文字以下で入力してください");
		}
		if (StringUtils.isEmpty(branchId)) {
			messages.add("支店を選択してください");
		}
		if (StringUtils.isEmpty(departmentId)) {
			messages.add("部署・役職を選択してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
