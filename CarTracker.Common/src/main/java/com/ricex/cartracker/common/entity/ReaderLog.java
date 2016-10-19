package com.ricex.cartracker.common.entity;

import java.util.Date;

public class ReaderLog extends AbstractEntity {

	private LogType type;
	
	private String message;
	
	private Date date;
	
	public ReaderLog() {
		
	}

	/**
	 * @return the type
	 */
	public LogType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(LogType type) {
		this.type = type;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}	
	
}
