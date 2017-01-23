package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.Map;

import bbs.dao.DepartmentDao;

public class DepartmentService {

	public Map<String, String> findAll() {
		Connection connection = null;
		try {
			connection = getConnection();
			DepartmentDao departmentDao = new DepartmentDao();
			Map<String, String> departmentMap = departmentDao.getDepartment(connection);
			commit(connection);
			return departmentMap;
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
