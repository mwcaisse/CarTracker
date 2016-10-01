package com.ricex.cartracker.common.viewmodel;

import java.io.Serializable;
import java.util.List;

public class PagedEntity<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3964102662989362006L;

	private final List<T> data;
	
	private final int startAt;
	
	private final int maxResults;
	
	private final long total;
	
	public PagedEntity(List<T> data, int startAt, int maxResults, long total) {
		this.data = data;
		this.startAt = startAt;
		this.maxResults = maxResults;
		this.total = total;
	}

	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * @return the startAt
	 */
	public int getStartAt() {
		return startAt;
	}

	/**
	 * @return the maxResults
	 */
	public int getMaxResults() {
		return maxResults;
	}

	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}
	
	
	
}
