package com.virus.collaborationBE.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.virus.collaborationBE.dao.JobApplicationDAO;
import com.virus.collaborationBE.model.JobApplication;

@Repository("jobApplicationDAO")
@EnableTransactionManagement
@Transactional
public class JobApplicationDAOImpl implements JobApplicationDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	public JobApplicationDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public List<JobApplication> fetchAllJobApplications() {
		return sessionFactory.getCurrentSession().createQuery("from JobApplication").list();
	}

	public List<JobApplication> fetchAllJobApplicationsByJobID(int jobid) {
		return sessionFactory.getCurrentSession().createQuery("from JobApplication where jobid='"+jobid+"'").list();
	}

	public JobApplication fetchJobApplicationsByID(int id) {
		return (JobApplication) sessionFactory.getCurrentSession().get(JobApplication.class, id);
	}

	public List<JobApplication> fetchAllJobApplicationsByUserID(String userid) {
		return sessionFactory.getCurrentSession().createQuery("from JobApplication where userid='"+userid+"'").list();
	}

	public boolean saveJobApplication(JobApplication jobApplication) {
		try 
		{
			sessionFactory.getCurrentSession().save(jobApplication);
			return true;
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateJobApplication(JobApplication jobApplication) {
		try 
		{
			sessionFactory.getCurrentSession().update(jobApplication);
			return true;
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteJobApplication(JobApplication jobApplication) {
		try 
		{
			sessionFactory.getCurrentSession().delete(jobApplication);
			return true;
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
}
