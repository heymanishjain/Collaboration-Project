package com.virus.collaborationBE.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.virus.collaborationBE.dao.CommentsDAO;
import com.virus.collaborationBE.model.Comments;

@Repository("commentsDAO")
@EnableTransactionManagement
@Transactional
public class CommentsDAOImpl implements CommentsDAO{
	
	private SessionFactory sessionFactory;
	
	public CommentsDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public List<Comments> getCommentsList() {
		return sessionFactory.getCurrentSession().createQuery("from Comments").list();
	}

	public boolean saveComment(Comments comments) {
		try 
		{
			sessionFactory.getCurrentSession().save(comments);
			return true;
		} 
		catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateComment(Comments comments) {
		try 
		{
			sessionFactory.getCurrentSession().update(comments);
			return true;
		} 
		catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteComment(Comments comments) {
		try 
		{
			sessionFactory.getCurrentSession().delete(comments);
			return true;
		} 
		catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Comments> getCommentsListByBlog(int id) {
		return sessionFactory.getCurrentSession().createQuery("from Comments where blogid='"+id+"'").list();
	}

	public Comments getCommentByID(int id) {
		return (Comments) sessionFactory.getCurrentSession().get(Comments.class, id);
	}

	public List<Comments> getCommentsListByForum(int id) {
		return sessionFactory.getCurrentSession().createQuery("from Comments where forumid='"+id+"'").list();
	}
	
}
