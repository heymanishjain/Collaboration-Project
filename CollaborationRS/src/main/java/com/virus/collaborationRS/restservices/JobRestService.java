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

import com.virus.collaborationBE.dao.JobDAO;
import com.virus.collaborationBE.model.Blog;
import com.virus.collaborationBE.model.Job;

@RestController
public class JobRestService {
	
	private static final Logger logger = LoggerFactory.getLogger(JobRestService.class);
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private Job job;
	
	@Autowired
	private JobDAO jobDAO;

	@PostMapping("/Jobpost/")
	public ResponseEntity<Job> postJob(@RequestBody Job newJob)
	{
		logger.debug("Satrting of method postJob");
		job = jobDAO.getJobById(newJob.getId());
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newJob.setErrorCode("400");
			newJob.setErrorMessage("User Not Logged In Please Log In First To Create job");
			return new ResponseEntity<Job>(newJob, HttpStatus.OK);
		}
		if(job==null)
		{
			logger.debug("Satrting of if(job==null) method of savejob");
			newJob.setId(ThreadLocalRandom.current().nextInt(100,1000000+1));
			newJob.setStatus("Available");
			newJob.setUser_id(loggedInUserId); //This is to be used when you start with front end
			//newJob.setUser_id("mahi"); // This is for temporary purpose to make the rest service run
			jobDAO.saveJob(newJob);
			newJob.setErrorCode("200");
			newJob.setErrorMessage("Job Successfully Posted");
			return new ResponseEntity<Job>(newJob, HttpStatus.OK);
		}
		else
		{
			logger.debug("Satrting of else method of savejob");
			newJob.setErrorCode("404");
			newJob.setErrorMessage("job Does Not Posted Successfully Please Try Again");
			return new ResponseEntity<Job>(newJob, HttpStatus.OK);
		}
	}
	@PutMapping("/Jobupdate/")
	public ResponseEntity<Job> updatejob(@RequestBody Job newJob)
	{
		logger.debug("Satrting of method updateeUser");
		job = jobDAO.getJobById(newJob.getId());
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newJob.setErrorCode("400");
			newJob.setErrorMessage("User Not Logged In Please Log In First To Update job");
			return new ResponseEntity<Job>(newJob, HttpStatus.OK);
		}
		if(job==null)
		{
			logger.debug("Satrting of if(job==null) method of updatejob");
			newJob.setErrorCode("404");
			newJob.setErrorMessage("No Such job Exists");
			return new ResponseEntity<Job>(newJob, HttpStatus.OK);
		}
		else
		{
			if(job.getUser_id().equals(loggedInUserId))
			{
				logger.debug("Satrting of nested if method of else method of updatejob");
				newJob.setStatus(job.getStatus());
				newJob.setUser_id(job.getUser_id());
				if(newJob.getTitle()==null || newJob.getTitle()=="")
				{
					newJob.setTitle(job.getTitle());
				}
				if(newJob.getDescription()==null || newJob.getDescription()=="")
				{
					newJob.setDescription(job.getDescription());
				}
				if(newJob.getSalary()==null || newJob.getSalary()=="")
				{
					newJob.setSalary(job.getSalary());
				}
				if(newJob.getPost()==null || newJob.getPost()=="")
				{
					newJob.setPost(job.getPost());
				}
				if(newJob.getCompany_name()==null || newJob.getCompany_name()=="")
				{
					newJob.setCompany_name(job.getCompany_name());
				}
				jobDAO.updateJob(newJob);
				newJob.setErrorCode("200");
				newJob.setErrorMessage("job Updated Successfully");
			}
			else
			{
				logger.debug("Satrting of else method of updatejob");
				newJob.setErrorCode("500");
				newJob.setErrorMessage("You Cannot Update This job Because This job Is Created By Another User");
			}
			return new ResponseEntity<Job>(newJob, HttpStatus.OK);
		}
	}
	@PutMapping("/Jobupdatestatus/")
	public ResponseEntity<Job> updatejobStatus(@RequestBody Job newJob)
	{
		logger.debug("Satrting of method updateeJobStatus");
		newJob = jobDAO.getJobById(newJob.getId());
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newJob.setErrorCode("400");
			newJob.setErrorMessage("User Not Logged In Please Log In First To Update job");
			return new ResponseEntity<Job>(newJob, HttpStatus.OK);
		}
		if(newJob==null)
		{
			logger.debug("Satrting of if(job==null) method of updatejobStatus");
			newJob.setErrorCode("404");
			newJob.setErrorMessage("No Such job Exists");
			return new ResponseEntity<Job>(newJob, HttpStatus.OK);
		}
		else
		{
			if(newJob.getUser_id().equals(loggedInUserId))
			{
				logger.debug("Satrting of nested if method of else method of updatejobStatus");
				newJob.setStatus("Closed");
				jobDAO.updateJob(newJob);
				newJob.setErrorCode("200");
				newJob.setErrorMessage("job Updated Status Successfully");
			}
			else
			{
				logger.debug("Satrting of else method of updatejobStatus");
				newJob.setErrorCode("500");
				newJob.setErrorMessage("You Cannot Update This job Because This job Is Created By Another User");
			}
			return new ResponseEntity<Job>(newJob, HttpStatus.OK);
		}
	}
	@DeleteMapping("/Jobdelete/{id}")
	public ResponseEntity<Job> deletejob(@PathVariable("id")String id)
	{
		System.out.println(id);
		Job newJob = new Job();
		int bid = Integer.parseInt(id);
		logger.debug("Starting of deletejob Method");
		job = jobDAO.getJobById(bid);
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newJob.setErrorCode("400");
			newJob.setErrorMessage("User Not Logged In Please Log In First To Delete job");
			return new ResponseEntity<Job>(newJob, HttpStatus.OK);
		}
		if(job==null)
		{
			logger.debug("Satrting of if(job==null) method of deletejob");
			newJob.setErrorCode("404");
			newJob.setErrorMessage("No Such job Exists");
			return new ResponseEntity<Job>(newJob, HttpStatus.OK);
		}
		else
		{
			if(job.getUser_id().equals(loggedInUserId))
			{
				logger.debug("Satrting of nested if method of else method of deletejob");
				jobDAO.deleteJob(job);
				newJob.setErrorCode("200");
				newJob.setErrorMessage("job Deleted Successfully");
			}
			else
			{
				logger.debug("Satrting of nested else method of else method of deletejob");
				newJob.setErrorCode("500");
				newJob.setErrorMessage("You Cannot Delete This job Because This job Is Created By Another User");
			}
			return new ResponseEntity<Job>(newJob, HttpStatus.OK);
		}
	}
	@GetMapping("/JobAllList")
	public ResponseEntity<List<Job>> showAlljobs()
	{
		logger.debug("Satrting of method showAlljob");
		List<Job> jobList =  jobDAO.getAllJobs();
		if(jobList==null)
		{
			logger.debug("Checking whether job List is Null Or Not");
			job.setErrorCode("404");
			job.setErrorMessage("No job Exists");
			return new ResponseEntity<List<Job>>(jobList, HttpStatus.OK);
		}
		else
		{
			logger.debug("Starting of Else Method of showAlljob Function");
			job = new Job();
			job.setErrorCode("200");
			job.setErrorMessage("All jobs Fetched Successfully");
			return new ResponseEntity<List<Job>>(jobList,HttpStatus.OK);
		}
	}
	@GetMapping("/JobAvailableList")
	public ResponseEntity<List<Job>> showAllApprovedjobs()
	{
		logger.debug("Satrting of method showAllApprovedjob");
		List<Job> jobList =  jobDAO.getAvailableJobs();
		if(jobList==null)
		{
			logger.debug("Checking whether job List is Null Or Not");
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("No job Exists");
			return new ResponseEntity<List<Job>>(jobList, HttpStatus.OK);
		}
		else
		{
			logger.debug("Starting of Else Method of showAllApprovedjob Function");
			job = new Job();
			job.setErrorCode("200");
			job.setErrorMessage("Approved jobs Fetched Successfully");
			return new ResponseEntity<List<Job>>(jobList,HttpStatus.OK);
		}
	}
	@GetMapping("/JobByUserId")
	public ResponseEntity<List<Job>> showjobByUserId()
	{
		logger.debug("Satrting of method showjobByUserId");
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		List<Job> jobList = jobDAO.getJobsByUserid(loggedInUserId);
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			job.setErrorCode("400");
			job.setErrorMessage("User Not Logged In Please Log In First To Fetch job");
			return new ResponseEntity<List<Job>>(jobList, HttpStatus.OK);
		}
		if(jobList==null)
		{
			logger.debug("Checking whether job List is Null Or Not");
			job.setErrorCode("404");
			job.setErrorMessage("You Have Not Created Any jobs");
			return new ResponseEntity<List<Job>>(jobList, HttpStatus.OK);
		}
		else
		{
			logger.debug("Starting of Else Method of jobByUserId Function");
			job.setErrorCode("200");
			job.setErrorMessage("jobs By UserId Fetched Successfully");
			return new ResponseEntity<List<Job>>(jobList,HttpStatus.OK);
		}
	}
	@PostMapping("/JobById/{id}")
	public ResponseEntity<Job> showjobById(@PathVariable("id")String id)
	{
		System.out.println(id);
		logger.debug("Satrting of method showjobById");
		int bid = Integer.parseInt(id);
		job = jobDAO.getJobById(bid);
		if(job==null)
		{
			logger.debug("Checking whether job List is Null Or Not");
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("No Such job Exists");
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		else
		{
			job.setErrorCode("200");
			job.setErrorMessage("job By Id Fetched Successfully");
			return new ResponseEntity<Job>(job,HttpStatus.OK);
		}
	}
}
