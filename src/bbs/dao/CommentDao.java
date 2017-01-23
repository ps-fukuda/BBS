package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bbs.beans.Comment;
import bbs.exception.SQLRuntimeException;

public class CommentDao {

	public void insert(Connection connection, Comment comment) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments (");
			sql.append("post_id");
			sql.append(", user_id");
			sql.append(", body");
			sql.append(", created_at");
			sql.append(", updated_at");
			sql.append(") VALUES (");
			sql.append(" ?"); // post_id
			sql.append(", ?"); // user_id
			sql.append(", ?"); // body
			sql.append(", NOW()"); // created_at
			sql.append(", NOW()"); // updated_at
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, comment.getPostId());
			ps.setInt(2, comment.getUserId());
			ps.setString(3, comment.getBody());
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
			String sql = "DELETE FROM comments WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}
