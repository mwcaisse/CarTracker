package com.ricex.cartracker.data.entity;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 745522159251623207L;	
	
	protected long id;
	
	protected Date createDate;
	
	protected Date modifiedDate;	
	
	public AbstractEntity() {
		
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
		
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof AbstractEntity)) {
			return false;
		}
		AbstractEntity o = (AbstractEntity)other;
		return this.id == o.id;				
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + Long.hashCode(id);
		return hash;
	}
	
	
	
}
