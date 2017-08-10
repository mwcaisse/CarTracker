package com.ricex.cartracker.web.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricex.cartracker.common.entity.TripPossiblePlace;
import com.ricex.cartracker.common.entity.TripPossiblePlaceType;
import com.ricex.cartracker.common.viewmodel.EntityResponse;
import com.ricex.cartracker.common.viewmodel.PagedEntity;
import com.ricex.cartracker.common.viewmodel.SortParam;
import com.ricex.cartracker.data.manager.TripPossiblePlaceManager;
import com.ricex.cartracker.data.query.properties.EntityType;

@Controller
@RequestMapping("/api")
public class TripPossiblePlaceController extends ApiController<TripPossiblePlace> {

	private final TripPossiblePlaceManager manager;
	
	public TripPossiblePlaceController(TripPossiblePlaceManager manager) {
		super(EntityType.TRIP_POSSIBLE_PLACE, manager);
		
		this.manager = manager;
	}
	
	@RequestMapping(value = "/trip/{tripId}/possibleplaces/{type}", method = RequestMethod.GET, produces={JSON})
	public @ResponseBody EntityResponse<PagedEntity<TripPossiblePlace>> getPossiblePlacesForTrip(
			@PathVariable int tripId, @PathVariable TripPossiblePlaceType type,
			@RequestParam(name = "startAt", required = false, defaultValue = DEFAULT_START_AT) int startAt, 
			@RequestParam(name = "maxResults", required = false, defaultValue = DEFAULT_MAX_RESULTS) int maxResults,
			SortParam sort) {
		
		
		return createEntityResponse(manager.getForTripofType(tripId, type, startAt, maxResults, sort));
	}

	
	
}
