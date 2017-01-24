package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.ComplementUser;
import bbs.beans.User;
import bbs.exception.SQLRuntimeException;

public class UserDao {

	public User getUser(Connection connection, String loginId, String password) {
		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE login_id = ? AND password = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty()) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException();
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public User getUser(Connection connection, int id) {
		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty()) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException();
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<User> getAllUser(Connection connection) {
		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty()) {
				return null;
			} else {
				return userList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<ComplementUser> getAllComplementUser(Connection connection) {
		PreparedStatement ps = null;
		try {
			String sql = "";
			sql += "SELECT";
			sql += "   u.*";
			sql += " , b.name AS branch_name";
			sql += " , d.name AS department_name";
			sql += " FROM";
			sql += "   users AS u";
			sql += " LEFT JOIN";
			sql += "   branches AS b";
			sql += " ON";
			sql += "   u.branch_id = b.id";
			sql += " LEFT JOIN";
			sql += "   departments AS d";
			sql += " ON";
			sql += "   u.department_id = d.id";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<ComplementUser> userList = toComplementUserList(rs);
			if (userList.isEmpty()) {
				return null;
			} else {
				return userList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toUserList(ResultSet rs) throws SQLException {
		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setLoginId(rs.getString("login_id"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBranchId(rs.getInt("branch_id"));
				user.setDepartmentId(rs.getInt("department_id"));
				user.setStopped(rs.getBoolean("is_stopped"));
				user.setCreatedAt(rs.getTimestamp("created_at"));
				user.setUpdatedAt(rs.getTimestamp("updated_at"));
				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	private List<ComplementUser> toComplementUserList(ResultSet rs) throws SQLException {
		List<ComplementUser> ret = new ArrayList<ComplementUser>();
		try {
			while (rs.next()) {
				ComplementUser user = new ComplementUser();
				user.setId(rs.getInt("id"));
				user.setLoginId(rs.getString("login_id"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBranchId(rs.getInt("branch_id"));
				user.setDepartmentId(rs.getInt("department_id"));
				user.setStopped(rs.getBoolean("is_stopped"));
				user.setBranchName(rs.getString("branch_name"));
				user.setDepartmentName(rs.getString("department_name"));
				user.setName(rs.getString("name"));
				user.setCreatedAt(rs.getTimestamp("created_at"));
				user.setUpdatedAt(rs.getTimestamp("updated_at"));
				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public void insert(Connection connection, User user) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users (");
			sql.append("login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", is_stopped");
			sql.append(", created_at");
			sql.append(", updated_at");
			sql.append(") VALUES (");
			sql.append(" ?"); // login_id
			sql.append(", ?"); // password
			sql.append(", ?"); // name
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // department_id
			sql.append(", ?"); // is_stopped
			sql.append(", NOW()"); // created_at
			sql.append(", NOW()"); // updated_at
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranchId());
			ps.setInt(5, user.getDepartmentId());
			ps.setBoolean(6, user.isStopped());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void update(Connection connection, User user) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			int idIndex = 5;
			sql.append("UPDATE users SET");
			sql.append(" login_id = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", department_id = ?");

			if (!user.getPassword().equals("")) {
				sql.append(", password = ?");
				idIndex = 6;
			}

			sql.append(" where id = ?");
			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getBranchId());
			ps.setInt(4, user.getDepartmentId());
			if (!user.getPassword().equals("")) {
				ps.setString(5, user.getPassword());
			}
			ps.setInt(idIndex, user.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete(Connection connection, int id) {
		PreparedStatement ps = null;
		try {
			String sql = "DELETE FROM users WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void setStatus(Connection connection, User user) {
		PreparedStatement ps = null;
		try {
			String sql = "UPDATE users SET is_stopped = ? WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setBoolean(1, !user.isStopped());
			ps.setInt(2, user.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<Integer> getUserIdList(Connection connection, int branchId) {
		PreparedStatement ps = null;
		try {
			String sql = "SELECT id FROM users WHERE branch_id = ? AND department_id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, branchId);
			ps.setInt(2, 4);
			ResultSet rs = ps.executeQuery();
			List<Integer> userIdList = toUserIdList(rs);
			return userIdList;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Integer> toUserIdList(ResultSet rs) throws SQLException {
		List<Integer> ret = new ArrayList<Integer>();
		try {
			while (rs.next()) {
				ret.add(rs.getInt("id"));
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public boolean otherGeneralExists(Connection connection) {
		PreparedStatement ps = null;
		try {
			String sql = "SELECT COUNT(*) AS count FROM users WHERE department_id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, 1);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt("count") <= 1) return false;
			}
			return true;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public boolean duplicateCheck(Connection connection, User user) {
		PreparedStatement ps = null;
		try {
			String sql = "SELECT EXISTS(SELECT id FROM users WHERE login_id = ?)";
			ps = connection.prepareStatement(sql);
			ps.setString(1, user.getLoginId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public boolean idDuplicateCheck(Connection connection, User user) {
		PreparedStatement ps = null;
		try {
			String sql = "";
			sql += "SELECT";
			sql += "  COUNT(*) as count";
			sql += " FROM";
			sql += "  users AS u";
			sql += " WHERE";
			sql += "  NOT EXISTS";
			sql += " (";
			sql += " SELECT";
			sql += "  u.login_id";
			sql += " FROM";
			sql += "  users";
			sql += " WHERE";
			sql += "  users.id = ?";
			sql += " AND";
			sql += "  users.login_id = ?";
			sql += " )";
			sql += " AND";
			sql += "  u.login_id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, user.getId());
			ps.setString(2, user.getLoginId());
			ps.setString(3, user.getLoginId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt("count") == 0) return true;
			}
			return false;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}