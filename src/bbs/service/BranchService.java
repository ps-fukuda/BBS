package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.Map;

import bbs.dao.BranchDao;

public class BranchService {

	public Map<String, String> findAll() {
		Connection connection = null;
		try {
			connection = getConnection();
			BranchDao branchDao = new BranchDao();
			Map<String, String> branchMap = branchDao.getBranch(connection);
			commit(connection);
			return branchMap;
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