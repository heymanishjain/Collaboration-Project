package com.virus.collaborationBE.dao;

import java.util.List;

import com.virus.collaborationBE.model.Friends;

public interface FriendsDAO {

	public List<Friends> fetchAllFriends();
	
	public List<Friends> fetchAllApprovedFriends(String userid);
	
	public List<Friends> fetchAllPendingFriends(String userid);
	
	public List<Friends> fetchAllPendingFriendsByUserid(String userid);
	
	public List<Friends> fetchAllRejectFriends(String userid);
	
	public List<Friends> fetchAllFriendsByUserId(String id);
	
	public Friends getFriendById(int id);
	
	public Friends getFriendByUserIdFriendId(String userid,String friendid);
	
	public Boolean saveFriend(Friends friend);
	
	public Boolean updateFriend(Friends friend);
	
	public Boolean deleteFriend(Friends friend);
}
