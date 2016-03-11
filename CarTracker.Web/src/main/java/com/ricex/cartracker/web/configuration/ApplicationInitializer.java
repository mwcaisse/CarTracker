package com.ricex.cartracker.web.configuration;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	/** Defines the root spring config classes
	 * 
	 */
	
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { ApplicationConfiguration.class, ThymeleafConfiguration.class };
	}

	/** Defines the web config classes
	 * 
	 */
	
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebConfiguration.class };
	}

	/** Defines the servlet mappings
	 * 
	 */
	
	protected String[] getServletMappings() {
		return new String[] { "/"};
	}
	
	/** Creates the Filters for the Servlet
	 * 
	 */
	
	protected Filter[] getServletFilters() {
		return new Filter[] {};
	}
}
