package com.virus.collaborationBE;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.virus.collaborationBE.dao.EventDAO;
import com.virus.collaborationBE.model.Event;

public class EventTestCase {

	@Autowired
	static AnnotationConfigApplicationContext context;
	
	@Autowired
	static Event event;
	
	@Autowired
	static EventDAO eventDAO;
	
	@BeforeClass
	public static void init()
	{
		context = new AnnotationConfigApplicationContext();
		context.scan("com.virus");
		context.refresh();
		event = (Event) context.getBean("event");
		eventDAO = (EventDAO) context.getBean("eventDAO");
	}

	@Test
	public void createEventTest()
	{
		event.setId(1);
		event.setTitle("IPL");
		event.setDescription("Indian Premier League");
		event.setStatus("Running");
		event.setEventdate("1/10/11");
		event.setUser_id("VirusMickey");
		boolean flag = eventDAO.saveEvent(event);
		assertEquals("createEventTest",true , flag);
	}
	@Test
	public void updateEventTest()
	{
		event.setId(1);
		event.setTitle("IPL 2017");
		event.setDescription("Indian Premier League");
		event.setStatus("Running");
		event.setEventdate("1/10/11");
		event.setUser_id("VirusMickey");
		boolean flag = eventDAO.updateEvent(event);
		assertEquals("updateEventTest",true , flag);
	}
	//@Test
	public void deleteEventTest()
	{
		event = eventDAO.getEventById(1);
		boolean flag = eventDAO.deleteEvent(event);
		assertEquals("deleteEventTest", true, flag);
	}
}
