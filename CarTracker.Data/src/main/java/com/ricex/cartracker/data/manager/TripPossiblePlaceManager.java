package com.ricex.cartracker.data.manager;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.ricex.cartracker.common.entity.TripPossiblePlace;
import com.ricex.cartracker.common.entity.TripPossiblePlaceType;
import com.ricex.cartracker.common.viewmodel.PagedEntity;
import com.ricex.cartracker.common.viewmodel.SortParam;
import com.ricex.cartracker.data.mapper.TripPossiblePlaceMapper;
import com.ricex.cartracker.data.query.properties.EntityType;
import com.ricex.cartracker.data.validation.EntityValidationException;
import com.ricex.cartracker.data.validation.PlaceValidator;
import com.ricex.cartracker.data.validation.TripPossiblePlaceValidator;
import com.ricex.cartracker.data.validation.TripValidator;

public class TripPossiblePlaceManager extends AbstractEntityManager<TripPossiblePlace> {

	protected final TripPossiblePlaceMapper mapper;
	protected final TripPossiblePlaceValidator validator;
	
	protected final TripValidator tripValidator;
	protected final PlaceValidator placeValidator;
	
	public TripPossiblePlaceManager(TripPossiblePlaceMapper mapper,	TripPossiblePlaceValidator validator,
			TripValidator tripValidator, PlaceValidator placeValidator) {
		super(mapper, validator, EntityType.TRIP_POSSIBLE_PLACE);
		this.mapper = mapper;
		this.validator = validator;
		this.tripValidator = tripValidator;
		this.placeValidator = placeValidator;
	}
	
	public List<TripPossiblePlace> getForTripOfType(long tripId, TripPossiblePlaceType type) {
		return mapper.getForTripOfType(tripId, type);
	}
	
	public PagedEntity<TripPossiblePlace> getForTripofType(long tripId, TripPossiblePlaceType type,
			int startAt, int maxResults, SortParam sort) {
		String orderBy = parseSortBy(sort);
		List<TripPossiblePlace> possiblePlaces = mapper.getForTripOfType(tripId, type, orderBy, 
				new RowBounds(startAt, maxResults));
		long totalPossiblePlaceCount = mapper.countForTripOfType(tripId, type);
		return new PagedEntity<TripPossiblePlace>(possiblePlaces, startAt, maxResults, totalPossiblePlaceCount);
	}

	@Override
	protected void createValidationLogic(TripPossiblePlace toCreate) throws EntityValidationException {
		tripValidator.exists(toCreate.getTripId());
		placeValidator.exists(toCreate.getPlaceId());
		
	}

	@Override
	protected void updateValidationLogic(TripPossiblePlace toUpdate) throws EntityValidationException {
		createValidationLogic(toUpdate);		
	}

}
