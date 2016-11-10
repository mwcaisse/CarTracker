package com.ricex.cartracker.web.controller.view;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.ricex.cartracker.web.BuildProperties;

public abstract class ViewController {

	/** The build version attribute
	 * 
	 * @return The build version
	 */
	@ModelAttribute("version")
	public String getVersion() {
		return BuildProperties.getVersion();
	}
	
	/** The build date attribute
	 * 
	 * @return The build date
	 */
	@ModelAttribute("buildDate")
	public String getBuildDate() {
		return BuildProperties.getBuildDate();
	}
	
	@ModelAttribute("gitInformation")
	public String getGitInformation() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(BuildProperties.getGitBranch())
			   .append(" ")
			   .append(BuildProperties.getGitShortHash());		
		
		return builder.toString();
	}
	
	@ModelAttribute("gitUrl")
	public String getGiturl() {
		return "https://github.com/mwcaisse/CarTracker/commit/" + BuildProperties.getGitLongHash();
	}
}
