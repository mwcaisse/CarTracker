package com.ricex.cartracker.web.controller.api;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.cartracker.data.entity.AbstractEntity;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.web.viewmodel.ResponseViewModel;

public abstract class ApiController<T extends AbstractEntity> {

	private static Logger log = LoggerFactory.getLogger(ApiController.class);

	/** Creates a new API Controller
	 * 
	 */
	
	public ApiController() {

	}
	
	public ResponseViewModel<T> handleValidationException(EntityValidationException e, HttpServletResponse resp) {
		return null;
	}
	
}
