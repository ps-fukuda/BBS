package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bbs.beans.ComplementUser;
import bbs.beans.User;
import bbs.dao.UserDao;
import bbs.utils.CipherUtil;

public class UserService {

	public void register(User user) {

		Connection connection = null;
		try {
			connection = getConnection();
			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);
			userDao.insert(connection, user);
			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public boolean duplicateCheck(User user) {

		Connection connection = null;
		try {
			connection = getConnection();
			UserDao userDao = new UserDao();
			boolean bool = userDao.duplicateCheck(connection, user);
			commit(connection);
			return bool;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void setStatus(User user) {

		Connection connection = null;
		try {
			connection = getConnection();
			UserDao userDao = new UserDao();
			userDao.setStatus(connection, user);
			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public User getUser(int userId) {

		Connection connection = null;
		try {
			connection = getConnection();
			UserDao userDao = new UserDao();
			User user = userDao.getUser(connection, userId);
			commit(connection);
			return user;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public List<User> getAllUser() {

		Connection connection = null;
		try {
			connection = getConnection();
			UserDao userDao = new UserDao();
			List<User> userList = userDao.getAllUser(connection);
			commit(connection);
			return userList;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public List<ComplementUser> getAllComplementUser() {

		Connection connection = null;
		try {
			connection = getConnection();
			UserDao userDao = new UserDao();
			List<ComplementUser> userList = userDao.getAllComplementUser(connection);
			commit(connection);
			return userList;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void update(User user) {

		Connection connection = null;
		try {
			connection = getConnection();

			// passwordに値がある場合
			if (!user.getPassword().equals("")) {
				String encPassword = CipherUtil.encrypt(user.getPassword());
				user.setPassword(encPassword);
			}

			UserDao userDao = new UserDao();
			userDao.update(connection, user);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void delete(int id) {
		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			userDao.delete(connection, id);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public List<Integer> getUserIdList(int branchId) {
		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			List<Integer> userIdList = userDao.getUserIdList(connection, branchId);

			commit(connection);
			return userIdList;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public boolean otherGeneralExists() {
		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			boolean bool = userDao.otherGeneralExists(connection);

			commit(connection);
			return bool;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public boolean idEditCheck(User user) {
		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			boolean bool = userDao.idEditCheck(connection, user);

			commit(connection);
			return bool;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}
