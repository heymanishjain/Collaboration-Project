package com.virus.collaborationRS.restservices;

import java.util.ArrayList;
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

import com.virus.collaborationBE.dao.CommentsDAO;
import com.virus.collaborationBE.model.Blog;
import com.virus.collaborationBE.model.Comments;

@RestController
public class CommentsRestService {

	private static final Logger logger = LoggerFactory.getLogger(CommentsRestService.class);
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private Comments comments;
	
	@Autowired
	private CommentsDAO commentsDAO;
	
	@PostMapping("/Commentsave/")
	public ResponseEntity<Comments> createComment(@RequestBody Comments comments)
	{
		logger.debug("Satrting of method creatComment");
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		long d = System.currentTimeMillis();
		Date today = new Date(d);
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			comments.setErrorCode("400");
			comments.setErrorMessage("User Not Logged In Please Log In First To Create Blog");
			return new ResponseEntity<Comments>(comments, HttpStatus.OK);
		}
		else
		{
			logger.debug("Saving Comment In the Else Part");
			comments.setId(ThreadLocalRandom.current().nextInt(100,1000000+1));
			comments.setUserid(loggedInUserId);
			comments.setDateadded(today);
			commentsDAO.saveComment(comments);
			comments.setErrorCode("200");
			comments.setErrorMessage("Comments Saved Successfully");
			return new ResponseEntity<Comments>(comments, HttpStatus.OK);
		}
	}
	@DeleteMapping("/Commentdelete/{id}")
	public ResponseEntity<Comments> deleteComment(@PathVariable("id")int id)
	{
		logger.debug("Starting of deleteComment");
		Comments newComments = commentsDAO.getCommentByID(id);
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			comments.setErrorCode("400");
			comments.setErrorMessage("User Not Logged In Please Log In First To Delete Comment");
			return new ResponseEntity<Comments>(comments, HttpStatus.OK);
		}
		if(newComments==null)
		{
			logger.debug("No such comment found");
			comments.setErrorCode("404");
			comments.setErrorMessage("Comments Not Found");
			return new ResponseEntity<Comments>(comments, HttpStatus.OK);
		}
		comments = commentsDAO.getCommentByID(id);
		if(comments.getUserid().equals(loggedInUserId))
		{
			boolean b = commentsDAO.deleteComment(comments);
			if(b)
			{
				logger.debug("Comment Got Deleted");
				comments.setErrorCode("200");
				comments.setErrorMessage("Comments Deleted Successfully");
			}
			else
			{
				logger.debug("Error Deleting Comment");
				comments.setErrorCode("405");
				comments.setErrorMessage("Error Deleting Comment");
			}
			return new ResponseEntity<Comments>(comments, HttpStatus.OK);
		}
		else
		{
			logger.debug("You Cannot Delete This Comment Because This Comment Is Created By Another User");
			comments.setErrorCode("500");
			comments.setErrorMessage("You Cannot Delete This Comment Because This Comment Is Created By Another User");
			return new ResponseEntity<Comments>(comments, HttpStatus.OK);
		}
	}
	@PutMapping("/Commentupdate/")
	public ResponseEntity<Comments> updateComment(@RequestBody Comments comments)
	{
		logger.debug("Start Of updateComment");
		Comments newComment = commentsDAO.getCommentByID(comments.getId());
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			comments.setErrorCode("400");
			comments.setErrorMessage("User Not Logged In Please Log In First To Update Comment");
			return new ResponseEntity<Comments>(comments, HttpStatus.OK);
		}
		if(newComment==null)
		{
			logger.debug("No such comment found");
			comments.setErrorCode("404");
			comments.setErrorMessage("Comments Not Found");
			return new ResponseEntity<Comments>(comments, HttpStatus.OK);
		}
		if(newComment.getUserid().equals(loggedInUserId))
		{
			long d = System.currentTimeMillis();
			Date today = new Date(d);
			comments.setDateadded(today);
			if(comments.getCommentsmsg()==null || comments.getCommentsmsg()=="")
			{
				comments.setCommentsmsg(newComment.getCommentsmsg());
			}
			comments.setBlogid(newComment.getBlogid());
			comments.setUserid(newComment.getUserid());
			boolean b = commentsDAO.updateComment(comments);
			if(b)
			{
				logger.debug("Comment Got Updated");
				comments.setErrorCode("200");
				comments.setErrorMessage("Comments Updated Successfully");
			}
			else
			{
				logger.debug("Error Updating Comment");
				comments.setErrorCode("405");
				comments.setErrorMessage("Error Updating Comment");
			}
			return new ResponseEntity<Comments>(comments, HttpStatus.OK);
		}
		else
		{
			logger.debug("You Cannot Update This Comment Because This Comment Is Created By Another User");
			comments.setErrorCode("500");
			comments.setErrorMessage("You Cannot Update This Comment Because This Comment Is Created By Another User");
			return new ResponseEntity<Comments>(comments, HttpStatus.OK);
		}
	}
	@GetMapping("/CommentsList")
	public ResponseEntity<List<Comments>> displayAllComments()
	{
		List<Comments> commentsList = commentsDAO.getCommentsList();
		return new ResponseEntity<List<Comments>>(commentsList,HttpStatus.OK);
	}
	@GetMapping("/CommentsListByBlog/{id}")
	public ResponseEntity<List<Comments>> displayAllCommentsByBlog(@PathVariable("id")int id)
	{
		List<Comments> commentsList = commentsDAO.getCommentsListByBlog(id);
		return new ResponseEntity<List<Comments>>(commentsList,HttpStatus.OK);
	}
	@GetMapping("/CommentsListByForum/{id}")
	public ResponseEntity<List<Comments>> displayAllCommentsByForum(@PathVariable("id")int id)
	{
		List<Comments> commentsList = commentsDAO.getCommentsListByForum(id);
		return new ResponseEntity<List<Comments>>(commentsList,HttpStatus.OK);
	}
	/*@GetMapping("/CommentsBlogList")
	public ResponseEntity<List<Comments>> displayAllCommentsForBlog()
	{
		List<Comments> commentsList = commentsDAO.getCommentsList();
		List<Comments> blogCommentList = new ArrayList<Comments>();
		int size = commentsList.size();
		for(int i=0;i<size;i++)
		{
			
		}
		return new ResponseEntity<List<Comments>>(commentsList,HttpStatus.OK);
	}*/
}
