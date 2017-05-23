package com.virus.collaborationBE;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.virus.collaborationBE.dao.JobDAO;
import com.virus.collaborationBE.model.Job;

public class JobTestCase {

	@Autowired
	static AnnotationConfigApplicationContext context;
	
	@Autowired
	static Job job;
	
	@Autowired
	static JobDAO jobDAO;
	
	@BeforeClass
	public static void init()
	{
		context = new AnnotationConfigApplicationContext();
		context.scan("com.virus");
		context.refresh();
		job = (Job) context.getBean("job");
		jobDAO = (JobDAO) context.getBean("jobDAO");
	}
	
	@Test
	public void createJobTest()
	{
		job.setId(1);
		job.setTitle("Developer");
		job.setCompany_name("Infosys");
		job.setDescription("Infosys job openings for freshers and new comers");
		job.setPost("Debugger/Tester");
		job.setSalary(50000);
		job.setUser_id("VirusMickey");
		boolean flag = jobDAO.saveJob(job);
		assertEquals("createJobTest", true, flag);
	}
	@Test
	public void updateJobTest()
	{
		job.setId(1);
		job.setTitle("Job Openings");
		job.setCompany_name("Infosys");
		job.setDescription("Infosys job openings for freshers and new comers");
		job.setPost("Debugger/Tester");
		job.setSalary(50000);
		boolean flag = jobDAO.updateJob(job);
		assertEquals("updateJobTest", true, flag);
	}
	//@Test
	public void deleteJobTest()
	{
		job = jobDAO.getJobById(1);
		boolean flag = jobDAO.deleteJob(job);
		assertEquals("deleteTestCase", true, flag);
	}
	
}
