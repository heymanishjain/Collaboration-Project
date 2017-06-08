package com.virus.collaborationRS.restservices;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.virus.collaborationBE.dao.EventDAO;
import com.virus.collaborationBE.model.Event;

@RestController
public class EventRestService {
	
	private static final Logger logger = LoggerFactory.getLogger(EventRestService.class);
	
	@Autowired
	private HttpSession session;
	 
	@Autowired
	private Event event;
	
	@Autowired
	private EventDAO eventDAO;
	
	@PostMapping("/Eventpost/")
	public ResponseEntity<Event> postEvent(@RequestBody Event newEvent)
	{
		logger.debug("Satrting of method postevent");
		event = eventDAO.getEventById(newEvent.getId());
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newEvent.setErrorCode("400");
			newEvent.setErrorMessage("User Not Logged In Please Log In First To Create event");
			return new ResponseEntity<Event>(newEvent, HttpStatus.OK);
		}
		if(event==null)
		{
			logger.debug("Satrting of if(event==null) method of saveevent");
			newEvent.setId(ThreadLocalRandom.current().nextInt(100,1000000+1));
			newEvent.setStatus("Open");
			newEvent.setUser_id(loggedInUserId); //This is to be used when you start with front end
			DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	        LocalDate currentdate = LocalDate.now();
	        LocalDate eventDate = LocalDate.parse(newEvent.getEventdate(), sdf);
	        if(eventDate.isAfter(currentdate))
	        {
	        	eventDAO.saveEvent(newEvent);
	        	newEvent.setErrorCode("200");
	        	newEvent.setErrorMessage("event Successfully Posted");
	        }
	        else
	        {
	        	newEvent.setErrorCode("410");
	        	newEvent.setErrorMessage("Date Entered Is Not Valid");
	        }
			return new ResponseEntity<Event>(newEvent, HttpStatus.OK);
		}
		else
		{
			logger.debug("Satrting of else method of saveevent");
			newEvent.setErrorCode("404");
			newEvent.setErrorMessage("event Does Not Posted Successfully Please Try Again");
			return new ResponseEntity<Event>(newEvent, HttpStatus.OK);
		}
	}
	@GetMapping("/EventAllList")
	public ResponseEntity<List<Event>> showAllEvents()
	{
		logger.debug("Satrting of method showAllEvents");
		List<Event> eventList =  eventDAO.getAllEvents();
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate currentdate = LocalDate.now();
		int size = eventList.size();
		for(int i = 0;i<size;i++)
		{
			event = eventList.get(i);
			LocalDate eventDate = LocalDate.parse(event.getEventdate(), sdf);
			if(eventDate.isBefore(currentdate))
			{
				event.setStatus("Close");
				eventDAO.updateEvent(event);
			}
			if(eventDate.isAfter(currentdate))
			{
				event.setStatus("Open");
				eventDAO.updateEvent(event);
			}
			if(eventDate.isEqual(currentdate))
			{
				event.setStatus("Ongoing");
				eventDAO.updateEvent(event);
			}
		}
		return new ResponseEntity<List<Event>>(eventList,HttpStatus.OK);	
	}
	@GetMapping("/EventListByStatusOpen")
	public ResponseEntity<List<Event>> showAllEventsByStatus()
	{
		logger.debug("Satrting of method showAllEventsByStatus");
		List<Event> eventList =  eventDAO.getEventsByStatusOpen();
		return new ResponseEntity<List<Event>>(eventList,HttpStatus.OK);
	}
	@GetMapping("/EventListByStatusClose")
	public ResponseEntity<List<Event>> showAllEventsByStatusClose()
	{
		logger.debug("Satrting of method showAllEventsByStatusClose");
		List<Event> eventList =  eventDAO.getEventsByStatusClose();
		return new ResponseEntity<List<Event>>(eventList,HttpStatus.OK);
	}
	@GetMapping("/EventListByStatusOngoing")
	public ResponseEntity<List<Event>> showAllEventsByStatusOngoing()
	{
		logger.debug("Satrting of method showAllEventsByStatusOngoing");
		List<Event> eventList =  eventDAO.getEventsByStatusOngoing();
		return new ResponseEntity<List<Event>>(eventList,HttpStatus.OK);
	}
}
