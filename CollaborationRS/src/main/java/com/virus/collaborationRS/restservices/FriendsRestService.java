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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.virus.collaborationBE.dao.FriendsDAO;
import com.virus.collaborationBE.model.Blog;
import com.virus.collaborationBE.model.Friends;

@RestController
public class FriendsRestService {
	
	private static final Logger logger = LoggerFactory.getLogger(FriendsRestService.class);
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private Friends friends;
	
	@Autowired
	private FriendsDAO friendsDAO;
	
	@PostMapping("/Friendsave/")
	public ResponseEntity<Friends> saveFriend(@RequestBody Friends newFriend)
	{
		logger.debug("Satrting of method saveFriend");
		friends = friendsDAO.getFriendById(newFriend.getId());
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newFriend.setErrorCode("400");
			newFriend.setErrorMessage("User Not Logged In Please Log In First To Create friends");
			return new ResponseEntity<Friends>(newFriend, HttpStatus.OK);
		}
		if(friends==null)
		{
			logger.debug("Satrting of if(friends==null) method of savefriends");
			long d = System.currentTimeMillis();
			Date today = new Date(d);
			newFriend.setId(ThreadLocalRandom.current().nextInt(100,1000000+1));
			newFriend.setUserid(loggedInUserId);
			friendsDAO.saveFriend(newFriend);
			newFriend.setErrorCode("200");
			newFriend.setErrorMessage("Friend Successfully Added");
			return new ResponseEntity<Friends>(newFriend, HttpStatus.OK);
		}
		else
		{
			logger.debug("Satrting of else method of savefriends");
			newFriend.setErrorCode("404");
			newFriend.setErrorMessage("friends Does Not Posted Successfully Please Try Again");
			return new ResponseEntity<Friends>(newFriend, HttpStatus.OK);
		}
	}
	@DeleteMapping("/Frienddelete/{id}")
	public ResponseEntity<Friends> deletefriends(@PathVariable("id")String id)
	{
		System.out.println(id);
		Friends newFriend = new Friends();
		int bid = Integer.parseInt(id);
		logger.debug("Starting of deletefriends Method");
		friends = friendsDAO.getFriendById(bid);
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newFriend.setErrorCode("400");
			newFriend.setErrorMessage("User Not Logged In Please Log In First To Delete friends");
			return new ResponseEntity<Friends>(newFriend, HttpStatus.OK);
		}
		if(friends==null)
		{
			logger.debug("Satrting of if(friends==null) method of deletefriends");
			newFriend.setErrorCode("404");
			newFriend.setErrorMessage("No Such friends Exists");
			return new ResponseEntity<Friends>(newFriend, HttpStatus.OK);
		}
		else
		{
			logger.debug("Satrting of nested if method of else method of deletefriends");
			friendsDAO.deleteFriend(friends);
			newFriend.setErrorCode("200");
			newFriend.setErrorMessage("friends Deleted Successfully");
			return new ResponseEntity<Friends>(newFriend, HttpStatus.OK);
		}
	}
	@PostMapping("/FriendsById/{id}")
	public ResponseEntity<Friends> showfriendsById(@PathVariable("id")String id)
	{
		System.out.println(id);
		logger.debug("Satrting of method showfriendsById");
		int bid = Integer.parseInt(id);
		friends = friendsDAO.getFriendById(bid);
		if(friends==null)
		{
			logger.debug("Checking whether friends List is Null Or Not");
			friends = new Friends();
			friends.setErrorCode("404");
			friends.setErrorMessage("No Such friends Exists");
			return new ResponseEntity<Friends>(friends, HttpStatus.OK);
		}
		else
		{
			friends.setErrorCode("200");
			friends.setErrorMessage("friends By Id Fetched Successfully");
			return new ResponseEntity<Friends>(friends,HttpStatus.OK);
		}
	}
	@GetMapping("/FriendsByUserId")
	public ResponseEntity<List<Friends>> showfriendsByUserId()
	{
		logger.debug("Satrting of method showfriendsByUserId");
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		List<Friends> friendsList = friendsDAO.fetchAllFriendsByUserId(loggedInUserId);
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			friends.setErrorCode("400");
			friends.setErrorMessage("User Not Logged In Please Log In First To Fetch friends");
			return new ResponseEntity<List<Friends>>(friendsList, HttpStatus.OK);
		}
		if(friendsList==null)
		{
			logger.debug("Checking whether friends List is Null Or Not");
			friends.setErrorCode("404");
			friends.setErrorMessage("You Have Not Created Any friendss");
			return new ResponseEntity<List<Friends>>(friendsList, HttpStatus.OK);
		}
		else
		{
			logger.debug("Starting of Else Method of friendsByUserId Function");
			friends = new Friends();
			friends.setErrorCode("200");
			friends.setErrorMessage("friendss By UserId Fetched Successfully");
			return new ResponseEntity<List<Friends>>(friendsList,HttpStatus.OK);
		}
	}
}
