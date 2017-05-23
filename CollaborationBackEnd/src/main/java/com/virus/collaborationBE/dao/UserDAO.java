package com.virus.collaborationBE.dao;

import java.util.List;

import com.virus.collaborationBE.model.User;

public interface UserDAO {

	public List<User> getUserList();
	
	public User getUserById(String id);
	
	public boolean saveUser(User user);
	
	public boolean deletUser(User user);
	
	public boolean updateUser(User user);
	
	public User validateUser(String id,String password);
}
