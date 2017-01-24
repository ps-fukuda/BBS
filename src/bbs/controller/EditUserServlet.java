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

@WebServlet(urlPatterns = { "/editUser" })
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		List<String> messages = new ArrayList<String>();

		// 直接アクセスを排除
		if (id == null) {
			messages.add("不正な操作が行われました");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("./userManagement");
			return;
		}
		Map<String, String> branchMap = new BranchService().findAll();
		Map<String, String> departmentMap = new DepartmentService().findAll();
		session.setAttribute("branch", branchMap);
		session.setAttribute("department", departmentMap);
		int userId = 0;
		try {
			userId = Integer.parseInt(id);
		} catch (Exception e) {
			messages.add("不正なidが入力されました");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("./userManagement");
			return;
		}
		User user = new UserService().getUser(userId);
		if (user == null) {
			messages.add("不正なidが入力されました");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("./userManagement");
			return;
		}
		session.setAttribute("userId", id);
		request.setAttribute("editUser", user);
		request.getRequestDispatcher("editUser.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		User user = setInputUser(request);
		UserService userService = new UserService();

		if (isValid(request, messages)) {
			// login_idの重複チェックで引っかかった場合
			if (!userService.idDuplicateCheck(user)) {
				messages.add("入力されたログインIDは他の方が使用中です");
				request.setAttribute("errorMessages", messages);
				request.setAttribute("editUser", user);
				request.getRequestDispatcher("editUser.jsp").forward(request, response);
				return;
			}
			User loginUser = (User) session.getAttribute("loginUser");
			// 総務人事が0人になる事を防ぐ判定
			// 自身が総務人事 & 自身を編集している & 部署idが1以外のものがpostされている
			if (loginUser.getDepartmentId() == 1 && String.valueOf(loginUser.getId()).equals(request.getParameter("id")) && !request.getParameter("department_id").equals("1")) {
				// 自分を含め2名以上存在していない場合
				if (!userService.otherGeneralExists()) {
					messages.add("総務人事担当者を0名に設定する事はできません");
					session.setAttribute("errorMessages", messages);
					response.sendRedirect("./userManagement");
					return;
				}
			}

			userService.update(user);
			session.removeAttribute("branch");
			session.removeAttribute("department");
			session.removeAttribute("userId");
			response.sendRedirect("./userManagement");
		} else {
			request.setAttribute("errorMessages", messages);
			request.setAttribute("editUser", user);
			request.getRequestDispatcher("editUser.jsp").forward(request, response);
		}
	}

	private User setInputUser(HttpServletRequest request) {

		User user = new User();
		user.setId(Integer.parseInt(request.getParameter("id")));
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
		if (!StringUtils.isEmpty(password)) {
			if (!password.matches("[ -~]{6,255}")) {
				messages.add("パスワードは半角文字6文字以上255文字以下で入力してください");
			} else {
				passwordFlag = true;
			}
		}
		if (passwordFlag) {
			if (StringUtils.isEmpty(passwordConfirm)) {
				messages.add("パスワード(確認用)を入力してください");
			} else if (!passwordConfirm.matches("[ -~]{6,255}")) {
				messages.add("パスワード(確認用)は半角文字6文字以上255文字以下で入力してください");
			} else {
				passwordConfirmFlag = true;
			}
		} else {
			if (!StringUtils.isEmpty(passwordConfirm)) {
				messages.add("パスワードを入力してください");
				if (!passwordConfirm.matches("[ -~]{6,255}")) {
					messages.add("パスワード(確認用)は半角文字6文字以上255文字以下で入力してください");
				}
			}
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