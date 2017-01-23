package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import bbs.exception.SQLRuntimeException;

public class DepartmentDao {

	public Map<String, String> getDepartment(Connection connection) {
		PreparedStatement ps = null;
		try {
			String sql = "SELECT id, name FROM departments";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return toDepartmentList(rs);
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private Map<String, String> toDepartmentList(ResultSet rs) throws SQLException {
		Map<String, String> departmentMap = new HashMap<>();
		try {
			while (rs.next()) {
				departmentMap.put(rs.getString("id"), rs.getString("name"));
			}
			return departmentMap;
		} finally {
			close(rs);
		}
	}
}
