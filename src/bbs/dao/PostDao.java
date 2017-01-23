package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.CustomPost;
import bbs.beans.Post;
import bbs.beans.Search;
import bbs.beans.ViewComment;
import bbs.exception.SQLRuntimeException;

public class PostDao {

	public void insert(Connection connection, Post post) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO posts (");
			sql.append("user_id");
			sql.append(", subject");
			sql.append(", body");
			sql.append(", category");
			sql.append(", created_at");
			sql.append(", updated_at");
			sql.append(") VALUES (");
			sql.append(" ?"); // user_id
			sql.append(", ?"); // subject
			sql.append(", ?"); // body
			sql.append(", ?"); // category
			sql.append(", NOW()"); // created_at
			sql.append(", NOW()"); // updated_at
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, post.getUserId());
			ps.setString(2, post.getSubject());
			ps.setString(3, post.getBody());
			ps.setString(4, post.getCategory());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<Post> findAll(Connection connection) {
		PreparedStatement ps = null;
		try {
			String sql = "select * from posts";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Post> postList = toPostList(rs);
			return postList;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public CustomPost getCustomPost(Connection connection, int postId) {
		PreparedStatement ps = null;
		try {
			String sql = "";
			sql += "SELECT";
			sql += "  p.*";
			sql += ", u.name";
			sql += ", (SELECT COUNT(*) FROM comments WHERE post_id = p.id) AS comment_count";
			sql += " FROM";
			sql += "  posts AS p";
			sql += " LEFT JOIN";
			sql += "  users AS u";
			sql += " ON p.user_id = u.id";
			sql += " WHERE p.id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, postId);
			ResultSet rs = ps.executeQuery();
			List<CustomPost> customPostList = toCustomPostList(rs);
			if (customPostList.isEmpty()) {
				return null;
			} else if (2 <= customPostList.size()) {
				throw new IllegalStateException();
			} else {
				return customPostList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<CustomPost> customPostAll(Connection connection) {
		PreparedStatement ps = null;
		try {
			String sql = "";
			sql += "SELECT";
			sql += "  p.*";
			sql += ", u.name";
			sql += ", (SELECT COUNT(*) FROM comments WHERE post_id = p.id) AS comment_count";
			sql += " FROM";
			sql += "  posts AS p";
			sql += " LEFT JOIN";
			sql += "  users AS u";
			sql += " ON p.user_id = u.id";
			sql += " ORDER BY p.updated_at DESC";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<CustomPost> customPostList = toCustomPostList(rs);
			return customPostList;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Post> toPostList(ResultSet rs) throws SQLException {
		List<Post> ret = new ArrayList<Post>();
		try {
			while (rs.next()) {
				Post post = new Post();
				post.setId(rs.getInt("id"));
				post.setUserId(rs.getInt("user_id"));
				post.setSubject(rs.getString("subject"));
				post.setBody(rs.getString("body"));
				post.setCategory(rs.getString("category"));
				post.setCreatedAt(rs.getDate("created_at"));
				post.setUpdatedAt(rs.getDate("updated_at"));
				ret.add(post);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	private List<CustomPost> toCustomPostList(ResultSet rs) throws SQLException {
		List<CustomPost> ret = new ArrayList<CustomPost>();
		try {
			while (rs.next()) {
				CustomPost customPost = new CustomPost();
				customPost.setId(rs.getInt("id"));
				customPost.setUserId(rs.getInt("user_id"));
				customPost.setSubject(rs.getString("subject"));
				customPost.setBody(rs.getString("body"));
				customPost.setCategory(rs.getString("category"));
				customPost.setName(rs.getString("name"));
				customPost.setCommentCount(rs.getString("comment_count"));
				customPost.setCreatedAt(rs.getDate("created_at"));
				customPost.setUpdatedAt(rs.getDate("updated_at"));
				ret.add(customPost);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public List<ViewComment> getViewComment(Connection connection, int postId) {
		PreparedStatement ps = null;
		try {
			String sql = "";
			sql += "SELECT";
			sql += "  c.*";
			sql += ", u.name";
			sql += " FROM";
			sql += "  comments AS c";
			sql += " LEFT JOIN";
			sql += "  users AS u";
			sql += " ON c.user_id = u.id";
			sql += " WHERE";
			sql += "  post_id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, postId);
			ResultSet rs = ps.executeQuery();
			List<ViewComment> viewCommentList = toViewCommentList(rs);
			return viewCommentList;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<ViewComment> toViewCommentList(ResultSet rs) throws SQLException {
		List<ViewComment> ret = new ArrayList<ViewComment>();
		try {
			while (rs.next()) {
				ViewComment viewComment = new ViewComment();
				viewComment.setId(rs.getInt("id"));
				viewComment.setPostId(rs.getInt("post_id"));
				viewComment.setUserId(rs.getInt("user_id"));
				viewComment.setBody(rs.getString("body"));
				viewComment.setName(rs.getString("name"));
				viewComment.setCreatedAt(rs.getDate("created_at"));
				viewComment.setUpdatedAt(rs.getDate("updated_at"));
				ret.add(viewComment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public List<CustomPost> search(Connection connection, Search search) {
		PreparedStatement ps = null;
		try {
			String sql = "";
			sql += "SELECT";
			sql += "  p.*";
			sql += " , u.name";
			sql += ", (SELECT COUNT(*) FROM comments WHERE post_id = p.id) AS comment_count";
			sql += " FROM";
			sql += "  posts AS p";
			sql += " LEFT JOIN";
			sql += "  users AS u";
			sql += " ON";
			sql += "  p.user_id = u.id";
			sql += " WHERE";
			sql += "  p.id IN";
			sql += " (";
			sql += "	SELECT";
			sql += "	  id";
			sql += "	FROM";
			sql += "	  posts";
			sql += "	WHERE";
			sql += "	  updated_at BETWEEN";

			int count = 1;
			boolean fromFlag = false;
			if (search.getFrom().equals("")) {
				sql += " (SELECT updated_at FROM posts ORDER BY updated_at LIMIT 1)";
			} else {
				sql += " ?";
				count += 1;
				fromFlag = true;
			}
			sql += " AND";
			if (search.getTo().equals("")) {
				sql += " (SELECT updated_at FROM posts ORDER BY updated_at DESC LIMIT 1)";
			} else {
				sql += " ?";
				count += 1;
			}
			sql += " )";
			sql += " AND";
			sql += "  p.category LIKE ?";
			sql += " ORDER BY p.updated_at DESC";
			ps = connection.prepareStatement(sql);
			if (count == 2) {
				if (fromFlag) ps.setString(1, search.getFrom());
				if (!fromFlag) ps.setString(1, search.getTo() + " 23:59:59");
			}
			if (count == 3) {
				ps.setString(1, search.getFrom());
				ps.setString(2, search.getTo() + " 23:59:59");
			}
			ps.setString(count, "%" + search.getCategory() + "%");
			ResultSet rs = ps.executeQuery();
			List<CustomPost> customPostList = toCustomPostList(rs);
			if (customPostList.isEmpty()) {
				return null;
			} else {
				return customPostList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete(Connection connection, int id) {
		PreparedStatement ps = null;
		try {
			String sql = "DELETE FROM posts WHERE id = ?";
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