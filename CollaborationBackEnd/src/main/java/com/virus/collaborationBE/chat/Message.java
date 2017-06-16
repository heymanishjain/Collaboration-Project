package com.virus.collaborationBE.chat;

public class Message {

	private String message;
	  private int id;
	  private String userid;
	  private int msgid;
	  
	  public Message() {
	    
	  }
	  public Message(int id, String message, String userid,int msgid) {
	    this.id = id;
	    this.message = message;
	    this.userid = userid;
	    this.msgid = msgid;
	  }
	  
	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}
	public String getMessage() {
	    return message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }

	  public int getId() {
	    return id;
	  }

	  public void setId(int id) {
	    this.id = id;
	  }
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	  
}
