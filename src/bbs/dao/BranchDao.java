package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import bbs.exception.SQLRuntimeException;

public class BranchDao {

	public Map<String, String> getBranch(Connection connection) {
		PreparedStatement ps = null;
		try {
			String sql = "SELECT id, name FROM branches";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return toBranchList(rs);
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private Map<String, String> toBranchList(ResultSet rs) throws SQLException {
		Map<String, String> branchMap = new HashMap<>();
		try {
			while (rs.next()) {
				branchMap.put(rs.getString("id"), rs.getString("name"));
			}
			return branchMap;
		} finally {
			close(rs);
		}
	}
}