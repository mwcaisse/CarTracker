package com.ricex.cartracker.common.viewmodel.entity;

public class CommandViewModel {

	private String name;
	private String pid;
	private boolean supported;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	/**
	 * @return the supported
	 */
	public boolean isSupported() {
		return supported;
	}
	/**
	 * @param supported the supported to set
	 */
	public void setSupported(boolean supported) {
		this.supported = supported;
	}
	
	
	
}
