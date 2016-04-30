package com.ricex.cartracker.web.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ricex.cartracker.data.entity.Reading;
import com.ricex.cartracker.data.manager.ReadingManager;

@Controller
@RequestMapping("/api/reading")
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

}
