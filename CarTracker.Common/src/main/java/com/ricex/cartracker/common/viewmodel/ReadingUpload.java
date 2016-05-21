package com.ricex.cartracker.common.viewmodel;

import com.ricex.cartracker.common.entity.Reading;

public class ReadingUpload extends Reading {

	private static final long serialVersionUID = 6721122911493961107L;

	private String uuid;

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}	
	
}
