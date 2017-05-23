package com.virus.collaborationBE.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.virus.collaborationBE.dao.UserDAO;
import com.virus.collaborationBE.model.User;

@Repository("userDAO")
@EnableTransactionManagement
@Transactional
public class UserDAOImpl implements UserDAO{

	private SessionFactory sessionFactory;
	
	public UserDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	public List<User> getUserList() {
		return sessionFactory.getCurrentSession().createQuery("from User").list();
	}

	public User getUserById(String id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	public boolean saveUser(User user) {
		try 
		{
			sessionFactory.getCurrentSession().save(user);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateUser(User user) {
		try 
		{
			sessionFactory.getCurrentSession().update(user);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public User validateUser(String id, String password) {
		/*User user = (User) sessionFactory.getCurrentSession().createQuery("from User where id='"+id+"' and password='"+password+"'").uniqueResult();
		if(user==null)
		{
			return false;
		}
		else
		{
			return true;
		}*/
		Query query = sessionFactory.getCurrentSession().createQuery("from User where id = ? and password = ?");
		query.setString(0, id);
		query.setString(1, password);
		List<User> list = (List<User>) query.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public boolean deletUser(User user) {
		try 
		{
			sessionFactory.getCurrentSession().delete(user);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

}
