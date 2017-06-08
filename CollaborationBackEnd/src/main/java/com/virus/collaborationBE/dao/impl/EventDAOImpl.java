package com.virus.collaborationBE.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.virus.collaborationBE.dao.EventDAO;
import com.virus.collaborationBE.model.Event;

@Repository("eventDAO")
@EnableTransactionManagement
@Transactional
public class EventDAOImpl implements EventDAO{

	private SessionFactory sessionFactory;
	
	public EventDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public List<Event> getAllEvents() {
		return sessionFactory.getCurrentSession().createQuery("from Event").list();
	}

	public boolean saveEvent(Event event) {
		try
		{
			sessionFactory.getCurrentSession().save(event);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateEvent(Event event) {
		try
		{
			sessionFactory.getCurrentSession().update(event);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteEvent(Event event) {
		try
		{
			sessionFactory.getCurrentSession().delete(event);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public Event getEventById(int id) {
		return (Event) sessionFactory.getCurrentSession().get(Event.class, id);
	}

	public List<Event> getEventsByUserid(String userid) {
		return sessionFactory.getCurrentSession().createQuery("from Event where user_id='"+userid+"'").list();
	}

	public List<Event> getEventsByStatusOpen() {
		return sessionFactory.getCurrentSession().createQuery("from Event where status = 'Open'").list();
	}

	public List<Event> getEventsByStatusClose() {
		return sessionFactory.getCurrentSession().createQuery("from Event where status = 'Close'").list();
	}

	public List<Event> getEventsByStatusOngoing() {
		return sessionFactory.getCurrentSession().createQuery("from Event where status = 'Ongoing'").list();
	}
	
}
