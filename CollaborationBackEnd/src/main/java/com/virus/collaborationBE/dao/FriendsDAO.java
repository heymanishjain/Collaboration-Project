package com.virus.collaborationBE.dao;

import java.util.List;

import com.virus.collaborationBE.model.Friends;

public interface FriendsDAO {

	public List<Friends> fetchAllFriends();
	
	public List<Friends> fetchAllFriendsByUserId(String id);
	
	public Friends getFriendById(int id);
	
	public Boolean saveFriend(Friends friend);
	
	public Boolean deleteFriend(Friends friend);
}
