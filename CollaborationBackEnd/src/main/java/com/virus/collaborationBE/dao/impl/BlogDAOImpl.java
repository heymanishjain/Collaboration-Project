package com.virus.collaborationBE.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.virus.collaborationBE.dao.BlogDAO;
import com.virus.collaborationBE.model.Blog;

@Repository("blogDAO")
@EnableTransactionManagement
@Transactional
public class BlogDAOImpl implements BlogDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	public BlogDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public List<Blog> getAllUsersBlog() {
		return sessionFactory.getCurrentSession().createQuery("from Blog").list();
	}

	public List<Blog> getApprovedBlogList() {
		return sessionFactory.getCurrentSession().createQuery("from Blog where status='Approved'").list();
	}
	
	public List<Blog> getPendingBlogList() {
		return sessionFactory.getCurrentSession().createQuery("from Blog where status='Pending'").list();
	}
	
	public List<Blog> getRejectedBlogList() {
		return sessionFactory.getCurrentSession().createQuery("from Blog where status='Rejected'").list();
	}

	public List<Blog> getBlogByUserId(String userid) {
		return sessionFactory.getCurrentSession().createQuery("from Blog where user_id='"+userid+"'").list();
	}

	public Blog getBlogById(int id) {
		return (Blog) sessionFactory.getCurrentSession().get(Blog.class, id);
	}

	public boolean saveBlog(Blog blog) {
		try
		{
			sessionFactory.getCurrentSession().save(blog);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateBlog(Blog blog) {
		try
		{
			sessionFactory.getCurrentSession().update(blog);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletBlog(Blog blog) {
		try
		{
			sessionFactory.getCurrentSession().delete(blog);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
}
