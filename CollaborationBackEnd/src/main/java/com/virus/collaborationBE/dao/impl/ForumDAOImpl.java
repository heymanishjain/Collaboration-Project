package com.virus.collaborationBE.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.virus.collaborationBE.dao.ForumDAO;
import com.virus.collaborationBE.model.Forum;

@Repository("forumDAO")
@EnableTransactionManagement
@Transactional
public class ForumDAOImpl implements ForumDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	public ForumDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public List<Forum> getAllUsersForums() {
		return sessionFactory.getCurrentSession().createQuery("from Forum").list();
	}

	public List<Forum> getAllForumsByUserId(String id) {
		return sessionFactory.getCurrentSession().createQuery("from Forum where userid='"+"'").list();
	}

	public Forum getForumById(int id) {
		return (Forum) sessionFactory.getCurrentSession().get(Forum.class, id);
	}

	public boolean saveForum(Forum forum) {
		try 
		{
			sessionFactory.getCurrentSession().save(forum);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateForum(Forum forum) {
		try 
		{
			sessionFactory.getCurrentSession().update(forum);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteForum(Forum forum) {
		try 
		{
			sessionFactory.getCurrentSession().delete(forum);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
}
