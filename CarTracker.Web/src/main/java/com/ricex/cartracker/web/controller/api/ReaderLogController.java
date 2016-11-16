package com.ricex.cartracker.web.controller.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.cartracker.common.entity.ReaderLog;
import com.ricex.cartracker.common.viewmodel.BooleanResponse;
import com.ricex.cartracker.common.viewmodel.BulkUploadResult;
import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.common.viewmodel.PagedEntity;
import com.ricex.cartracker.common.viewmodel.ReaderLogUpload;
import com.ricex.cartracker.common.viewmodel.SortParam;
import com.ricex.cartracker.data.manager.ReaderLogManager;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;

@Controller
@RequestMapping("/api/log/reader")
public class ReaderLogController extends ApiController<ReaderLog> {

	private ReaderLogManager manager;
	
	public ReaderLogController(ReaderLogManager manager) {
		super(EntityType.READER_LOG, manager);	
		this.manager = manager;		
	}
	
	/** Returns the Reader Log with the specified id
	 * 
	 */
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {JSON} )
	public @ResponseBody EntityResponse<ReaderLog> get(@PathVariable long id) {
		return super.get(id);
	}
	
	/** Returns all of the Reader Logs with paging
	 * 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = {JSON} )
	public @ResponseBody EntityResponse<PagedEntity<ReaderLog>> getAll(
			@RequestParam(name = "startAt", required = false, defaultValue = DEFAULT_START_AT) int startAt,
			@RequestParam(name = "maxResults", required = false, defaultValue = DEFAULT_MAX_RESULTS) int maxResults,
			SortParam sort) {
		return super.getAll(startAt, maxResults, sort);
	}
	
	/** Creates the given reader log
	 * 
	 * @param readerLog The reader log to create
	 * @return The reader log created, or an error message stating why it couldn't be created
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = {JSON}, consumes = {JSON})
	public @ResponseBody EntityResponse<ReaderLog> create(@RequestBody ReaderLog readerLog) {
		return super.create(readerLog);
	}
	
	/** Updates the given reader log
	 * 
	 * @param readerLog The readerLog to update
	 * @return true if update was successful, false otherwise
	 */
	@RequestMapping(value = "/", method = RequestMethod.PUT, produces = {JSON}, consumes = {JSON})
	public @ResponseBody EntityResponse<ReaderLog>  update(@RequestBody ReaderLog readerLog) {
		return super.update(readerLog);
	}
	
	/** Performs a bulk upload of reader logs	 * 

	 * @param readerLogs The reader logs to upload
	 * @return Result of each reader log upload
	 */
	
	@RequestMapping(value = "/bulk", method=RequestMethod.POST, produces={JSON}, consumes={JSON})
	public @ResponseBody EntityResponse<List<BulkUploadResult>> bulkUpload(@RequestBody ReaderLogUpload[] readerLogs) {	
		return createEntityResponse(manager.bulkUpload(Arrays.asList(readerLogs)));
	}

}
