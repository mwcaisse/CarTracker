package com.ricex.cartracker.data.manager;

import java.util.ArrayList;
import java.util.List;

import com.ricex.cartracker.common.entity.ReaderLog;
import com.ricex.cartracker.common.viewmodel.BulkUploadResult;
import com.ricex.cartracker.common.viewmodel.ReaderLogUpload;
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

	
	/** Creates a series of Log entries and records the results for each one
	 * 
	 * @param readerLogs The logs to create
	 * @return The results of the upload
	 */
	public List<BulkUploadResult> bulkUpload(List<ReaderLogUpload> readerLogs) {
		List<BulkUploadResult> results = new ArrayList<BulkUploadResult>();
		
		for (ReaderLogUpload upload : readerLogs) {
			BulkUploadResult result = new BulkUploadResult();
			result.setUuid(upload.getUuid());
			try {
				create(upload);
				result.setId(upload.getId());
				result.setSuccessful(true);
			}
			catch (EntityValidationException e) {
				result.setSuccessful(false);
				result.setErrorMessage(e.getMessage());
			}			
			
			results.add(result);
		}
		
		return results;
	}

}
