package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bbs.beans.CustomPost;
import bbs.beans.Post;
import bbs.beans.Search;
import bbs.beans.ViewComment;
import bbs.dao.PostDao;

public class PostService {

	public void register(Post post) {
		Connection connection = null;
		try {
			connection = getConnection();
			PostDao postDao = new PostDao();
			postDao.insert(connection, post);
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

	public List<Post> findAll() {
		Connection connection = null;
		try {
			connection = getConnection();
			PostDao postDao = new PostDao();
			List<Post> postList = postDao.findAll(connection);
			commit(connection);
			return postList;
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

	public List<ViewComment> getViewComment(int postId) {
		Connection connection = null;
		try {
			connection = getConnection();
			PostDao postDao = new PostDao();
			List<ViewComment> viewCommentList = postDao.getViewComment(connection, postId);
			commit(connection);
			return viewCommentList;
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

	public CustomPost getCustomPost(int postId) {
		Connection connection = null;
		try {
			connection = getConnection();
			PostDao postDao = new PostDao();
			CustomPost customPost = postDao.getCustomPost(connection, postId);
			commit(connection);
			return customPost;
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

	public List<CustomPost> customPostAll() {
		Connection connection = null;
		try {
			connection = getConnection();
			PostDao postDao = new PostDao();
			List<CustomPost> customPostList = postDao.customPostAll(connection);
			commit(connection);
			return customPostList;
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

	public List<CustomPost> search(Search search) {
		Connection connection = null;
		try {
			connection = getConnection();
			PostDao postDao = new PostDao();
			List<CustomPost> customPostList = postDao.search(connection, search);
			commit(connection);
			return customPostList;
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
			PostDao postDao = new PostDao();
			postDao.delete(connection, id);
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
}