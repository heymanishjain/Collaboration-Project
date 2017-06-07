package com.virus.collaborationBE.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity
public class JobApplication extends BaseDomain{

	@Id
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private String contact;
	private String qualification;
	private String marks10th;
	private String marks12th;
	private String ugmarks;
	private String experience;
	private String userid;
	private int jobid;
	public int getJobid() {
		return jobid;
	}
	public void setJobid(int jobid) {
		this.jobid = jobid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getMarks10th() {
		return marks10th;
	}
	public void setMarks10th(String marks10th) {
		this.marks10th = marks10th;
	}
	public String getMarks12th() {
		return marks12th;
	}
	public void setMarks12th(String marks12th) {
		this.marks12th = marks12th;
	}
	public String getUgmarks() {
		return ugmarks;
	}
	public void setUgmarks(String ugmarks) {
		this.ugmarks = ugmarks;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	
}
