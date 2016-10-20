package com.ricex.cartracker.data.manager;

import com.ricex.cartracker.common.entity.ReaderLog;
import com.ricex.cartracker.data.mapper.ReaderLogMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.ReaderLogValidator;

public class ReaderLogManager extends AbstractEntityManager<ReaderLog> {

	private ReaderLogMapper mapper;
	
	private ReaderLogValidator validator;
	
	public ReaderLogManager(ReaderLogMapper readerLogMapper, ReaderLogValidator readerLogValidator) {
		super(readerLogMapper, readerLogValidator, EntityType.READER_LOG);
		
		this.mapper = readerLogMapper;
		this.validator = readerLogValidator;
	}

	@Override
	protected void createValidationLogic(ReaderLog toCreate) throws EntityValidationException {
		
	}

	@Override
	protected void updateValidationLogic(ReaderLog toUpdate) throws EntityValidationException {
		
	}

}
