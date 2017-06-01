package com.virus.collaborationRS.restservices;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.virus.collaborationBE.dao.UserDAO;
import com.virus.collaborationBE.model.Blog;
import com.virus.collaborationBE.model.User;

@RestController
public class UserRestServices {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRestServices.class);

	@Autowired
	private User user;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserDAO userDAO;
	
	@GetMapping("/hello")
	public String sayHello()
	{
		logger.debug("Starting of the method sayHello");
		return "Hello";
	}
	
	@PostMapping("/UserLoginValidate")
	public ResponseEntity<User> validateUserLogins(@RequestBody User newUser)
	{
		logger.debug("Starting of the method UserLoginValidates");
		newUser = userDAO.validateUser(newUser.getId(), newUser.getPassword());
		if(newUser==null)
		{
			newUser = new User();
			newUser.setErrorCode("404");
			newUser.setErrorMessage("User Invalid");
			logger.debug("Starting of if method of UserLoginValidates");
			logger.debug("User is null invalid credentials entered");
			logger.debug("Endinging of if method of UserLoginValidates");
			return new ResponseEntity<User>(newUser,HttpStatus.OK);
		}
		else
		{
			newUser.setIsonline("Online");
			userDAO.updateUser(newUser);
			newUser.setErrorCode("200");
			newUser.setErrorMessage("User Successfully Logged In");
			logger.debug("Starting of else method of UserLoginValidates");
			logger.debug("User is not null valid credentials entered");
			logger.debug("ending of else method of UserLoginValidates");
			session.setAttribute("userLoggedIn", newUser.getId());
			session.setAttribute("userLoggedInRole", newUser.getRole());
			return new ResponseEntity<User>(newUser,HttpStatus.OK);
		}
	}
	
	@GetMapping("/UserAllList")
	public ResponseEntity<List<User>> showAllUser()
	{
		logger.debug("Satrting of method showAllUser");
		return new ResponseEntity<List<User>>(userDAO.getUserList(),HttpStatus.OK);
	}
	@PostMapping("/Usersave/")
	public ResponseEntity<User> saveUser(@RequestBody User newUser)
	{
		logger.debug("Satrting of method saveUser");
		user = userDAO.getUserById(newUser.getId());
		if(user==null)
		{
			logger.debug("Satrting of if(user==null) method of saveUser");
			newUser.setIsonline("Offline");
			userDAO.saveUser(newUser);
			newUser.setErrorCode("200");
			newUser.setErrorMessage("User Successfully Registered");
			session.setAttribute("userLoggedIn", newUser.getId());
			session.setAttribute("userLoggedInRole", newUser.getRole());
			return new ResponseEntity<User>(newUser, HttpStatus.OK);
		}
		else
		{
			newUser.setErrorCode("404");
			newUser.setErrorMessage("User Invalid");
			logger.debug("Satrting of else method of saveUser");
			return new ResponseEntity<User>(newUser, HttpStatus.OK);
		}
	}
	@PutMapping("/Userupdate/")
	public ResponseEntity<User> updateUser(@RequestBody User newUser)
	{
		logger.debug("Satrting of method updateUser");
		user = userDAO.getUserById(newUser.getId());
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newUser.setErrorCode("400");
			newUser.setErrorMessage("User Not Logged In Please Log In First To Update User Details");
			return new ResponseEntity<User>(newUser, HttpStatus.OK);
		}
		if(user==null)
		{
			newUser.setErrorCode("404");
			newUser.setErrorMessage("User Does Not Exists");
			logger.debug("Satrting of if method of updateUser");
			return new ResponseEntity<User>(newUser, HttpStatus.OK);
		}
		else
		{
			logger.debug("Satrting of else method of updateUser");
			newUser.setErrorCode("200");
			newUser.setErrorMessage("User Successfully Updated");
			if(newUser.getMail()==null || newUser.getMail()=="")
			{
				newUser.setMail(user.getMail());
			}
			if(newUser.getMobile()==null || newUser.getMobile()=="")
			{
				newUser.setMobile(user.getMobile());
			}
			if(newUser.getAddress()==null || newUser.getAddress()=="")
			{
				newUser.setAddress(user.getAddress());
			}
			if(newUser.getName()==null || newUser.getName()=="")
			{
				newUser.setName(user.getName());
			}
			if(newUser.getConfirmpassword()==null || newUser.getPassword()=="" || newUser.getConfirmpassword()=="" || newUser.getPassword()==null)
			{
				newUser.setConfirmpassword(user.getPassword());
				newUser.setPassword(user.getPassword());
			}
			newUser.setRole(user.getRole());
			userDAO.updateUser(newUser);
			return new ResponseEntity<User>(newUser, HttpStatus.OK);
		}
	}
	@DeleteMapping("/Userdelete/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id")String id)
	{
		logger.debug("Satrting of method deleteUser");
		user = userDAO.getUserById(id);
		if(user==null)
		{
			user.setErrorCode("404");
			user.setErrorMessage("User Invalid");
			logger.debug("Satrting of if method of deleteUser");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		else
		{
			logger.debug("Satrting of else method of deleteUser");
			user.setErrorCode("200");
			user.setErrorMessage("User Successfully Deleted");
			userDAO.deletUser(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}
	@GetMapping("/Userget/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id")String id)
	{
		logger.debug("Satrting of method getUserById");
		user = userDAO.getUserById(id);
		if(user==null)
		{
			user.setErrorCode("404");
			user.setErrorMessage("User Invalid");
			logger.debug("Satrting of if method of getUserById");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		else
		{
			user.setErrorCode("200");
			user.setErrorMessage("User Details By Id Fetched Successfully");
			logger.debug("Satrting of else method of getUserById");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}
	@GetMapping("/Userlogout")
	public ResponseEntity<User> userLogout()
	{
		logger.debug("Starting of the method logout");
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		User newUser = new User();
		newUser = userDAO.getUserById(loggedInUserId);
		if(newUser==null)
		{
			user.setErrorCode("404");
			user.setErrorMessage("No Such User Found");
		}
		else
		{
			newUser.setIsonline("Offline");
			userDAO.updateUser(newUser);
			session.invalidate();
			user = new User();
			user.setErrorCode("200");
			user.setErrorMessage("Logout Successfull");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
