package com.virus.collaborationRS.restservices;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.virus.collaborationBE.dao.JobApplicationDAO;
import com.virus.collaborationBE.model.Job;
import com.virus.collaborationBE.model.JobApplication;

@RestController
public class JobApplicationRestService {
	
	private static final Logger logger = LoggerFactory.getLogger(JobApplicationRestService.class);
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private JobApplication jobApplication;
	
	@Autowired
	private JobApplicationDAO jobApplicationDAO;
	
	@PostMapping("/Jobapply/")
	public ResponseEntity<JobApplication> applyJob(@RequestBody JobApplication newJobApplication)
	{
		logger.debug("Satrting of method applyJob");
		jobApplication = jobApplicationDAO.fetchJobApplicationsByID(newJobApplication.getId());
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			newJobApplication.setErrorCode("400");
			newJobApplication.setErrorMessage("User Not Logged In Please Log In First To Apply job");
			return new ResponseEntity<JobApplication>(newJobApplication, HttpStatus.OK);
		}
		if(jobApplication==null)
		{
			logger.debug("Satrting of if(jobApplication==null) method of applyjob");
			newJobApplication.setId(ThreadLocalRandom.current().nextInt(100,1000000+1));
			newJobApplication.setUserid(loggedInUserId); //This is to be used when you start with front end
			//newJob.setUser_id("mahi"); // This is for temporary purpose to make the rest service run
			jobApplicationDAO.saveJobApplication(newJobApplication);
			newJobApplication.setErrorCode("200");
			newJobApplication.setErrorMessage("Job Successfully Applied");
			return new ResponseEntity<JobApplication>(newJobApplication, HttpStatus.OK);
		}
		else
		{
			logger.debug("Satrting of else method of applyjob");
			newJobApplication.setErrorCode("404");
			newJobApplication.setErrorMessage("job Does Not Applied Successfully Please Try Again");
			return new ResponseEntity<JobApplication>(newJobApplication, HttpStatus.OK);
		}
	}
	@GetMapping("/JobApplicationAllList")
	public ResponseEntity<List<JobApplication>> showAllJobApplications()
	{
		logger.debug("Satrting of method showAllJobApplications");
		List<JobApplication> jobApplicationList =  jobApplicationDAO.fetchAllJobApplications();
		if(jobApplicationList==null)
		{
			logger.debug("Checking whether job Application List is Null Or Not");
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("No job Application Exists");
			return new ResponseEntity<List<JobApplication>>(jobApplicationList, HttpStatus.OK);
		}
		else
		{
			logger.debug("Starting of Else Method of showAllJobApplications Function");
			jobApplication = new JobApplication();
			jobApplication.setErrorCode("200");
			jobApplication.setErrorMessage("All jobs Application Fetched Successfully");
			return new ResponseEntity<List<JobApplication>>(jobApplicationList,HttpStatus.OK);
		}
	}
	@GetMapping("/JobApplicationByUserId")
	public ResponseEntity<List<JobApplication>> showjobApplicationByUserId()
	{
		logger.debug("Satrting of method showjobApplicationByUserId");
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		List<JobApplication> jobApplicationList = jobApplicationDAO.fetchAllJobApplicationsByUserID(loggedInUserId);
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			jobApplication.setErrorCode("400");
			jobApplication.setErrorMessage("User Not Logged In Please Log In First To Fetch job Application");
			return new ResponseEntity<List<JobApplication>>(jobApplicationList, HttpStatus.OK);
		}
		if(jobApplicationList==null)
		{
			logger.debug("Checking whether job Application List is Null Or Not");
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("You Have Not Applied Any jobs");
			return new ResponseEntity<List<JobApplication>>(jobApplicationList, HttpStatus.OK);
		}
		else
		{
			logger.debug("Starting of Else Method of jobApplicationByUserId Function");
			jobApplication.setErrorCode("200");
			jobApplication.setErrorMessage("jobs Application By UserId Fetched Successfully");
			return new ResponseEntity<List<JobApplication>>(jobApplicationList,HttpStatus.OK);
		}
	}
	@GetMapping("/JobApplicationByJobId/{jobid}")
	public ResponseEntity<List<JobApplication>> showjobApplicationByJobId(@PathVariable("jobid") int jobid)
	{
		logger.debug("Satrting of method showjobApplicationByUserId");
		String loggedInUserId = (String) session.getAttribute("userLoggedIn");
		List<JobApplication> jobApplicationList = jobApplicationDAO.fetchAllJobApplicationsByJobID(jobid);
		if(loggedInUserId==null)
		{
			logger.debug("Checking whether User Is Logged In Or Not");
			jobApplication.setErrorCode("400");
			jobApplication.setErrorMessage("User Not Logged In Please Log In First To Fetch job Application");
			return new ResponseEntity<List<JobApplication>>(jobApplicationList, HttpStatus.OK);
		}
		if(jobApplicationList==null)
		{
			logger.debug("Checking whether job Application List is Null Or Not");
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("You Have Not Applied Any jobs");
			return new ResponseEntity<List<JobApplication>>(jobApplicationList, HttpStatus.OK);
		}
		else
		{
			logger.debug("Starting of Else Method of jobApplicationByJobId Function");
			jobApplication.setErrorCode("200");
			jobApplication.setErrorMessage("jobs Application By JobId Fetched Successfully");
			return new ResponseEntity<List<JobApplication>>(jobApplicationList,HttpStatus.OK);
		}
	}
	@PostMapping("/JobApplicationById/{id}")
	public ResponseEntity<JobApplication> showjobApplicationById(@PathVariable("id")String id)
	{
		System.out.println(id);
		logger.debug("Satrting of method showjobApplicationById");
		int jid = Integer.parseInt(id);
		jobApplication = jobApplicationDAO.fetchJobApplicationsByID(jid);
		if(jobApplication==null)
		{
			logger.debug("Checking whether job List is Null Or Not");
			jobApplication = new JobApplication();
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("No Such job Exists");
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
		}
		else
		{
			jobApplication.setErrorCode("200");
			jobApplication.setErrorMessage("job Application By Id Fetched Successfully");
			return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
		}
	}
}
