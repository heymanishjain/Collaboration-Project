package com.virus.collaborationBE.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.virus.collaborationBE.dao.JobDAO;
import com.virus.collaborationBE.model.Job;

@Repository("jobDAO")
@EnableTransactionManagement
@Transactional
public class JobDAOImpl implements JobDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	public JobDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	public List<Job> getAllJobs() {
		return sessionFactory.getCurrentSession().createQuery("from Job").list();
	}

	public boolean saveJob(Job job) {
		try 
		{
			sessionFactory.getCurrentSession().save(job);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateJob(Job job) {
		try 
		{
			sessionFactory.getCurrentSession().update(job);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteJob(Job job) {
		try 
		{
			sessionFactory.getCurrentSession().delete(job);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public Job getJobById(int id) {
		return (Job) sessionFactory.getCurrentSession().get(Job.class, id);
	}

	public List<Job> getJobsByUserid(String userid) {
		return sessionFactory.getCurrentSession().createQuery("from Job where user_id='"+userid+"'").list();
	}
	
	

}
