package com.virus.collaborationBE.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.virus.collaborationBE.dao.FriendsDAO;
import com.virus.collaborationBE.model.Friends;

@Repository("friendsDAO")
@EnableTransactionManagement
@Transactional
public class FriendsDAOImpl implements FriendsDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	public FriendsDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public List<Friends> fetchAllFriends() {
		return sessionFactory.getCurrentSession().createQuery("from Friends").list();
	}

	public List<Friends> fetchAllFriendsByUserId(String id) {
		return sessionFactory.getCurrentSession().createQuery("from Friends where userid='"+id+"'").list();
	}

	public Boolean saveFriend(Friends friend) {
		try 
		{
			sessionFactory.getCurrentSession().save(friend);
			return true;
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public Boolean deleteFriend(Friends friend) {
		try 
		{
			sessionFactory.getCurrentSession().delete(friend);
			return true;
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public Friends getFriendById(int id) {
		return (Friends) sessionFactory.getCurrentSession().get(Friends.class, id);
	}

	public List<Friends> fetchAllApprovedFriends(String userid) {
		return sessionFactory.getCurrentSession().createQuery("from Friends where status = 'Approved' and userid='"+userid+"'").list();
	}

	public List<Friends> fetchAllPendingFriends(String userid) {
		return sessionFactory.getCurrentSession().createQuery("from Friends where status = 'Pending' and friendid='"+userid+"'").list();
	}

	public List<Friends> fetchAllRejectFriends(String userid) {
		return sessionFactory.getCurrentSession().createQuery("from Friends where status = 'Rejected' and userid='"+userid+"'").list();
	}

	public Boolean updateFriend(Friends friend) {
		try 
		{
			sessionFactory.getCurrentSession().update(friend);
			return true;
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public List<Friends> fetchAllPendingFriendsByUserid(String userid) {
		return sessionFactory.getCurrentSession().createQuery("from Friends where status = 'Pending' and userid='"+userid+"'").list();
	}

	public Friends getFriendByUserIdFriendId(String userid, String friendid) {
		return (Friends) sessionFactory.getCurrentSession().createQuery("from Friends where userid='"+userid+"'and friendid='"+friendid+"'").uniqueResult();	
	}
	
}
