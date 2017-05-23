package com.virus.collaborationBE.dao;

import java.util.List;

import com.virus.collaborationBE.model.Forum;

public interface ForumDAO {

	public List<Forum> getAllUsersForums();//To Show Complete List Of Forums To Admin
	
	public List<Forum> getAllForumsByUserId(String id);//To show list of forums created by particular user
	
	public Forum getForumById(int id);//To get a particular forum by its unique id
	
	public boolean saveForum(Forum forum);
	
	public boolean updateForum(Forum forum);
	
	public boolean deleteForum(Forum forum);
}
