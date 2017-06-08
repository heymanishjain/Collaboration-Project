package com.virus.collaborationBE.dao;

import java.util.List;

import com.virus.collaborationBE.model.Event;

public interface EventDAO {

	public List<Event> getAllEvents();
	
	public boolean saveEvent(Event event);
	
	public boolean updateEvent(Event event);
	
	public boolean deleteEvent(Event event);
	
	public Event getEventById(int id);
	
	public List<Event> getEventsByStatusOpen();
	
	public List<Event> getEventsByStatusClose();
	
	public List<Event> getEventsByStatusOngoing();
	
	public List<Event> getEventsByUserid(String userid);
}
