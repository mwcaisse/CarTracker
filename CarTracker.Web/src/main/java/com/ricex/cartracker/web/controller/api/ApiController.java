package com.ricex.cartracker.web.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.cartracker.data.entity.AbstractEntity;
import com.ricex.cartracker.data.manager.AbstractEntityManager;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.web.viewmodel.ResponseViewModel;

public abstract class ApiController<T extends AbstractEntity> {

	private static Logger log = LoggerFactory.getLogger(ApiController.class);

	private AbstractEntityManager<T> manager;
	
	/** Creates a new API Controller
	 * 
	 */
	
	public ApiController() {

	}
	
	public ResponseViewModel<List<T>> getAll() {		
		return createResponseViewModel(manager.getAll());
	}
	
	public ResponseViewModel<T> get(long id) {
		return createResponseViewModel(manager.get(id));
	}
		
	public ResponseViewModel<T> handleValidationException(EntityValidationException e, HttpServletResponse resp) {
		return null;
	}
	
	public void setManager(AbstractEntityManager<T> manager) {
		this.manager = manager;
	}
	
	protected <R> ResponseViewModel<R> createResponseViewModel(R data) {
		return new ResponseViewModel<R>(data);
	}
	
}
