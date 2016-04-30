package com.ricex.cartracker.web.controller.api;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.cartracker.common.entity.Reading;
import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.common.viewmodel.ReadingUpload;
import com.ricex.cartracker.common.viewmodel.ReadingUploadResult;
import com.ricex.cartracker.data.manager.ReadingManager;

@Controller
@RequestMapping("/api")
public class ReadingController extends ApiController<Reading> {

	private static final String ENTITY_NAME = "Reading";
	
	private final ReadingManager manager;
	
	/** Creates a new Reading Controller with the specific Reading Manager to perform
	 * 		data access
	 * @param manager The manager to perform data access
	 */
	
	public ReadingController(ReadingManager manager) {
		super(ENTITY_NAME, manager);
		this.manager = manager;
	}
	
	@RequestMapping(value = "/trip/{tripId}/reading/bulk", method=RequestMethod.POST, produces={JSON}, consumes={JSON})
	public @ResponseBody EntityResponse<List<ReadingUploadResult>> bulkUpload(@PathVariable long tripId, @RequestBody List<ReadingUpload> readings) {
		return null;
	}

}
