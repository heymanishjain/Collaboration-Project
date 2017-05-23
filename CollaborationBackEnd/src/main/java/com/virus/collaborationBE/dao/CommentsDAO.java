package com.virus.collaborationBE.dao;

import java.util.List;

import com.virus.collaborationBE.model.Comments;

public interface CommentsDAO {

	public List<Comments> getCommentsList();
	
	public Comments getCommentByID(int id);
	
	public List<Comments> getCommentsListByBlog(int id);
	
	public List<Comments> getCommentsListByForum(int id);
	
	public boolean saveComment(Comments comments);
	
	public boolean updateComment(Comments comments);
	
	public boolean deleteComment(Comments comments);
	
}
