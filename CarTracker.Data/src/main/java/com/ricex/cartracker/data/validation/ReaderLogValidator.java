package com.ricex.cartracker.data.validation;

import org.apache.commons.lang3.StringUtils;

import com.ricex.cartracker.common.entity.ReaderLog;
import com.ricex.cartracker.data.mapper.ReaderLogMapper;
import com.ricex.cartracker.data.query.properties.EntityType;

public class ReaderLogValidator extends AbstractEntityValidator<ReaderLog> {

	private ReaderLogMapper mapper;
	
	public ReaderLogValidator(ReaderLogMapper mapper) {
		super(mapper, EntityType.READER_LOG);
		this.mapper = mapper;
	}

	/** Checks that the log entry is valid
	 * 
	 * 		1) Must have a message
	 * 		2) Must have a log date
	 * 		3) Must have a log type
	 */
	
	public void validate(ReaderLog entity) throws EntityValidationException {
		if (StringUtils.isBlank(entity.getMessage())) {
			throw new EntityValidationException("The message cannot be blank!");
		}
		if (null == entity.getDate()) {
			throw new EntityValidationException("The log date cannot be empty!");
		}
		if (null == entity.getType()) {
			throw new EntityValidationException("The log must have a type!");
		}
		
	}

}
