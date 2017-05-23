package com.virus.collaborationRS.restservices;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.virus.collaborationBE.dao.ForumDAO;
import com.virus.collaborationBE.model.Blog;
import com.virus.collaborationBE.model.Comments;
import com.virus.collaborationBE.model.Forum;

@RestController
public class ForumRestService {

	private static final Logger logger = LoggerFactory.getLogger(ForumRestService.class);
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private Forum forum;
	
	@Autowired
	private ForumDAO forumDAO;
	
	@PostMapping("/Forumsave/")
	public ResponseEntity<Forum> createForum(@RequestBody Forum forum)
	{
		logger.debug("Satrting of method createForum");
		long d = System.currentTimeMillis();
		Date today = new Date(d);
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			forum.setErrorCode("400");
			forum.setErrorMessage("User Not Logged In Please Log In First To Create Forum");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		else
		{
			logger.debug("Starting of else of create blog");
			forum.setId(ThreadLocalRandom.current().nextInt(100,1000000+1));
			forum.setDateadded(today);
			forum.setUserid(loggedInUserId);
			forumDAO.saveForum(forum);
			forum.setErrorCode("200");
			forum.setErrorMessage("Forum Created Successfully");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
	}
	@DeleteMapping("/Forumdelete/{id}")
	public ResponseEntity<Forum> deleteForum(@PathVariable("id")int id)
	{
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		Forum newForum = forumDAO.getForumById(id);
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			forum.setErrorCode("400");
			forum.setErrorMessage("User Not Logged In Please Log In First To Delete Forum");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		if(newForum==null)
		{
			logger.debug("No such Forum found");
			forum.setErrorCode("404");
			forum.setErrorMessage("Forum Not Found");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		forum = forumDAO.getForumById(id);
		if(forum.getUserid().equals(loggedInUserId))
		{
			boolean b = forumDAO.deleteForum(forum);
			if(b)
			{
				logger.debug("Forum Got Deleted");
				forum.setErrorCode("200");
				forum.setErrorMessage("Forum Deleted Successfully");
			}
			else
			{
				logger.debug("Error Deleting Forum");
				forum.setErrorCode("405");
				forum.setErrorMessage("Error Deleting Forum");
			}
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		else
		{
			logger.debug("You Cannot Delete This Forum Because This Forum Is Created By Another User");
			forum.setErrorCode("500");
			forum.setErrorMessage("You Cannot Delete This Forum Because This Forum Is Created By Another User");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
	}
	@PutMapping("/Forumupdate/")
	public ResponseEntity<Forum> updateForum(@RequestBody Forum forum)
	{
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		Forum newForum = forumDAO.getForumById(forum.getId());
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			forum.setErrorCode("400");
			forum.setErrorMessage("User Not Logged In Please Log In First To Update Forum");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		if(newForum==null)
		{
			logger.debug("No such Forum found");
			forum.setErrorCode("404");
			forum.setErrorMessage("Forum Not Found");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		if(newForum.getUserid().equals(loggedInUserId))
		{
			long d = System.currentTimeMillis();
			Date today = new Date(d);
			forum.setDateadded(today);
			forum.setUserid(newForum.getUserid());
			if (forum.getTopic()==null || forum.getTopic()=="") 
			{
				forum.setTopic(newForum.getTopic());
			}
			boolean b = forumDAO.updateForum(forum);
			if(b)
			{
				logger.debug("Forum Got Updated");
				forum.setErrorCode("200");
				forum.setErrorMessage("Forum Updated Successfully");
			}
			else
			{
				logger.debug("Error Updating Forum");
				forum.setErrorCode("405");
				forum.setErrorMessage("Error Updating Forum");
			}
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		else
		{
			logger.debug("You Cannot Update This Forum Because This Forum Is Created By Another User");
			forum.setErrorCode("500");
			forum.setErrorMessage("You Cannot Update This Forum Because This Forum Is Created By Another User");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
	}
	@GetMapping("/ForumAllList")
	public ResponseEntity<List<Forum>> getAllForums()
	{
		List<Forum> forumList = forumDAO.getAllUsersForums();
		return new ResponseEntity<List<Forum>>(forumList,HttpStatus.OK);
	}
	@GetMapping("/ForumAllListByUserId")
	public ResponseEntity<List<Forum>> getAllForumsByUserId()
	{
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		List<Forum> forumList = forumDAO.getAllForumsByUserId(loggedInUserId);
		return new ResponseEntity<List<Forum>>(forumList,HttpStatus.OK);
	}
	@GetMapping("/ForumById/{id}")
	public ResponseEntity<Forum> getForumById(@PathVariable("id")int id)
	{
		forum = forumDAO.getForumById(id);
		return new ResponseEntity<Forum>(forum,HttpStatus.OK);
	}
}
