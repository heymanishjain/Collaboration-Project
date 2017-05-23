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

import com.virus.collaborationBE.dao.BlogDAO;
import com.virus.collaborationBE.dao.UserDAO;
import com.virus.collaborationBE.model.Blog;
import com.virus.collaborationBE.model.User;

@RestController
public class BlogRestServices {

	private static final Logger logger = LoggerFactory.getLogger(BlogRestServices.class);
	
	@Autowired
	private User user;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private Blog blog;
	
	@Autowired
	private BlogDAO blogDAO;
	
	@PostMapping("/Blogsave/")
	public ResponseEntity<Blog> saveBlog(@RequestBody Blog newBlog)
	{
		logger.debug("Satrting of method saveBlog");
		blog = blogDAO.getBlogById(newBlog.getId());
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newBlog.setErrorCode("400");
			newBlog.setErrorMessage("User Not Logged In Please Log In First To Create Blog");
			return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
		}
		if(blog==null)
		{
			logger.debug("Satrting of if(blog==null) method of saveBlog");
			long d = System.currentTimeMillis();
			Date today = new Date(d);
			newBlog.setId(ThreadLocalRandom.current().nextInt(100,1000000+1));
			newBlog.setStatus("Pending");
			newBlog.setDate_added(today);
			newBlog.setUser_id(loggedInUserId); //This is to be used when you start with front end
			//newBlog.setUser_id("mahi"); // This is for temporary purpose to make the rest service run
			blogDAO.saveBlog(newBlog);
			newBlog.setErrorCode("200");
			newBlog.setErrorMessage("Blog Successfully Posted Waiting To Be Approved By Admin");
			return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
		}
		else
		{
			logger.debug("Satrting of else method of saveBlog");
			newBlog.setErrorCode("404");
			newBlog.setErrorMessage("Blog Does Not Posted Successfully Please Try Again");
			return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
		}
	}
	@PutMapping("/Blogupdate/")
	public ResponseEntity<Blog> updateBlog(@RequestBody Blog newBlog)
	{
		logger.debug("Satrting of method updateeUser");
		blog = blogDAO.getBlogById(newBlog.getId());
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newBlog.setErrorCode("400");
			newBlog.setErrorMessage("User Not Logged In Please Log In First To Update Blog");
			return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
		}
		if(blog==null)
		{
			logger.debug("Satrting of if(blog==null) method of updateBlog");
			newBlog.setErrorCode("404");
			newBlog.setErrorMessage("No Such Blog Exists");
			return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
		}
		else
		{
			if(blog.getUser_id().equals(loggedInUserId))
			{
				logger.debug("Satrting of nested if method of else method of updateBlog");
				long d = System.currentTimeMillis();
				Date today = new Date(d);
				newBlog.setDate_added(today);
				newBlog.setStatus(blog.getStatus());
				newBlog.setUser_id(blog.getUser_id());
				if(newBlog.getTitle()==null || newBlog.getTitle()=="")
				{
					newBlog.setTitle(blog.getTitle());
				}
				if(newBlog.getDescription()==null || newBlog.getDescription()=="")
				{
					newBlog.setDescription(blog.getDescription());
				}
				blogDAO.updateBlog(newBlog);
				newBlog.setErrorCode("200");
				newBlog.setErrorMessage("Blog Updated Successfully");
			}
			else
			{
				logger.debug("Satrting of else method of updateBlog");
				newBlog.setErrorCode("500");
				newBlog.setErrorMessage("You Cannot Update This Blog Because This Blog Is Created By Another User");
			}
			return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
		}
	}
	@PutMapping("/BlogRejectupdate/{id}")
	public ResponseEntity<Blog> setBlogStatusRejected(@PathVariable("id")int id)
	{
		logger.debug("Satrting of method updateeUser");
		Blog newBlog = new Blog();
		blog = blogDAO.getBlogById(id);
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newBlog.setErrorCode("400");
			newBlog.setErrorMessage("User Not Logged In Please Log In First To Update Blog");
			return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
		}
		if(blog==null)
		{
			logger.debug("Satrting of if(blog==null) method of updateBlog");
			newBlog.setErrorCode("404");
			newBlog.setErrorMessage("No Such Blog Exists");
			return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
		}
		else
		{
			user = userDAO.getUserById(loggedInUserId);
			if(user.getRole().equals("Admin"))
			{
				blog.setStatus("Rejected");
				blogDAO.updateBlog(blog);
				blog.setErrorCode("200");
				blog.setErrorMessage("Blog Status Changed To Rejected Successfully");
				return new ResponseEntity<Blog>(blog, HttpStatus.OK);
			}
			else
			{
				logger.debug("Satrting of else method of updateApproveBlog");
				newBlog.setErrorCode("500");
				newBlog.setErrorMessage("You Cannot Update This Blog Because This Blog Rights Is Given To Admin Only");
				return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
			}
			
		}
	}
	@PutMapping("/BlogApproveupdate/{id}")
	public ResponseEntity<Blog> setBlogStatusApproved(@PathVariable("id") int id)
	{
		logger.debug("Satrting of method updateeUser");
		Blog newBlog = new Blog();
		blog = blogDAO.getBlogById(id);
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newBlog.setErrorCode("400");
			newBlog.setErrorMessage("User Not Logged In Please Log In First To Update Blog");
			return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
		}
		if(blog==null)
		{
			logger.debug("Satrting of if(blog==null) method of updateBlog");
			newBlog.setErrorCode("404");
			newBlog.setErrorMessage("No Such Blog Exists");
			return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
		}
		else
		{
			user = userDAO.getUserById(loggedInUserId);
			if(user.getRole().equals("Admin"))
			{
				blog.setStatus("Approved");
				blogDAO.updateBlog(blog);
				blog.setErrorCode("200");
				blog.setErrorMessage("Blog Status Changed To Approved Successfully");
				return new ResponseEntity<Blog>(blog, HttpStatus.OK);
			}
			else
			{
				logger.debug("Satrting of else method of updateApproveBlog");
				newBlog.setErrorCode("500");
				newBlog.setErrorMessage("You Cannot Update This Blog Because This Blog Rights Is Given To Admin Only");
				return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
			}
			
		}
	}
	@DeleteMapping("/Blogdelete/{id}")
	public ResponseEntity<Blog> deleteBlog(@PathVariable("id")String id)
	{
		System.out.println(id);
		Blog newBlog = new Blog();
		int bid = Integer.parseInt(id);
		logger.debug("Starting of deleteBlog Method");
		blog = blogDAO.getBlogById(bid);
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newBlog.setErrorCode("400");
			newBlog.setErrorMessage("User Not Logged In Please Log In First To Delete Blog");
			return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
		}
		if(blog==null)
		{
			logger.debug("Satrting of if(blog==null) method of deleteBlog");
			newBlog.setErrorCode("404");
			newBlog.setErrorMessage("No Such Blog Exists");
			return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
		}
		else
		{
			if(blog.getUser_id().equals(loggedInUserId))
			{
				logger.debug("Satrting of nested if method of else method of deleteBlog");
				blogDAO.deletBlog(blog);
				newBlog.setErrorCode("200");
				newBlog.setErrorMessage("Blog Deleted Successfully");
			}
			else
			{
				logger.debug("Satrting of nested else method of else method of deleteBlog");
				newBlog.setErrorCode("500");
				newBlog.setErrorMessage("You Cannot Delete This Blog Because This Blog Is Created By Another User");
			}
			return new ResponseEntity<Blog>(newBlog, HttpStatus.OK);
		}
	}
	@GetMapping("/BlogAllList")
	public ResponseEntity<List<Blog>> showAllBlogs()
	{
		logger.debug("Satrting of method showAllBlog");
		List<Blog> blogList =  blogDAO.getAllUsersBlog();
		if(blogList==null)
		{
			logger.debug("Checking whether Blog List is Null Or Not");
			blog.setErrorCode("404");
			blog.setErrorMessage("No Blog Exists");
			return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
		}
		else
		{
			logger.debug("Starting of Else Method of showAllBlog Function");
			blog = new Blog();
			blog.setErrorCode("200");
			blog.setErrorMessage("All Blogs Fetched Successfully");
			return new ResponseEntity<List<Blog>>(blogList,HttpStatus.OK);
		}
	}
	@GetMapping("/BlogAllApprovedList")
	public ResponseEntity<List<Blog>> showAllApprovedBlogs()
	{
		logger.debug("Satrting of method showAllApprovedBlog");
		List<Blog> blogList =  blogDAO.getApprovedBlogList();
		if(blogList==null)
		{
			logger.debug("Checking whether Blog List is Null Or Not");
			blog.setErrorCode("404");
			blog.setErrorMessage("No Blog Exists");
			return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
		}
		else
		{
			logger.debug("Starting of Else Method of showAllApprovedBlog Function");
			blog.setErrorCode("200");
			blog.setErrorMessage("Approved Blogs Fetched Successfully");
			return new ResponseEntity<List<Blog>>(blogList,HttpStatus.OK);
		}
	}
	@GetMapping("/BlogAllPendingList")
	public ResponseEntity<List<Blog>> showAllPendingBlogs()
	{
		logger.debug("Satrting of method showAllPendingBlog");
		List<Blog> blogList =  blogDAO.getPendingBlogList();
		if(blogList==null)
		{
			logger.debug("Checking whether Blog List is Null Or Not");
			blog.setErrorCode("404");
			blog.setErrorMessage("No Blog Exists");
			return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
		}
		else
		{
			logger.debug("Starting of Else Method of showAllPendingBlog Function");
			blog.setErrorCode("200");
			blog.setErrorMessage("Pending Blogs Fetched Successfully");
			return new ResponseEntity<List<Blog>>(blogList,HttpStatus.OK);
		}
	}
	@GetMapping("/BlogAllRejectedList")
	public ResponseEntity<List<Blog>> showAllRejectedBlogs()
	{
		logger.debug("Satrting of method showAllRejectedBlog");
		List<Blog> blogList =  blogDAO.getRejectedBlogList();
		if(blogList==null)
		{
			logger.debug("Checking whether Blog List is Null Or Not");
			blog.setErrorCode("404");
			blog.setErrorMessage("No Blog Exists");
			return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
		}
		else
		{
			logger.debug("Starting of Else Method of showAllRejectedBlog Function");
			blog.setErrorCode("200");
			blog.setErrorMessage("Rejected Blogs Fetched Successfully");
			return new ResponseEntity<List<Blog>>(blogList,HttpStatus.OK);
		}
	}
	@PostMapping("/BlogById/{id}")
	public ResponseEntity<Blog> showBlogById(@PathVariable("id")String id)
	{
		System.out.println(id);
		logger.debug("Satrting of method showBlogById");
		int bid = Integer.parseInt(id);
		blog = blogDAO.getBlogById(bid);
		if(blog==null)
		{
			logger.debug("Checking whether Blog List is Null Or Not");
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("No Such Blog Exists");
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		else
		{
			blog.setErrorCode("200");
			blog.setErrorMessage("Blog By Id Fetched Successfully");
			return new ResponseEntity<Blog>(blog,HttpStatus.OK);
		}
	}
	@GetMapping("/BlogByUserId")
	public ResponseEntity<List<Blog>> showBlogByUserId()
	{
		logger.debug("Satrting of method showBlogByUserId");
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		List<Blog> blogList = blogDAO.getBlogByUserId(loggedInUserId);
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			blog.setErrorCode("400");
			blog.setErrorMessage("User Not Logged In Please Log In First To Delete Blog");
			return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
		}
		if(blogList==null)
		{
			logger.debug("Checking whether Blog List is Null Or Not");
			blog.setErrorCode("404");
			blog.setErrorMessage("You Have Not Created Any Blogs");
			return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
		}
		else
		{
			logger.debug("Starting of Else Method of BlogByUserId Function");
			blog.setErrorCode("200");
			blog.setErrorMessage("Blogs By UserId Fetched Successfully");
			return new ResponseEntity<List<Blog>>(blogList,HttpStatus.OK);
		}
	}
}
