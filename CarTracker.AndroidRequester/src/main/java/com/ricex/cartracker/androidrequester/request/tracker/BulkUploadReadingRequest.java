package com.ricex.cartracker.androidrequester.request.tracker;

import java.util.List;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.BulkReadingUploadResponseType;
import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.viewmodel.ReadingUpload;
import com.ricex.cartracker.common.viewmodel.ReadingUploadResult;

public class BulkUploadReadingRequest extends AbstractRequest<List<ReadingUploadResult>> {

	private final long tripId;
	
	private final List<ReadingUpload> readings;
	
	public BulkUploadReadingRequest(ApplicationPreferences applicationPreferences, Trip trip, List<ReadingUpload> readings) {
		this(applicationPreferences, trip.getId(), readings);
	}
	
	public BulkUploadReadingRequest(ApplicationPreferences applicationPreferences, long tripId, List<ReadingUpload> readings) {
		super(applicationPreferences);
		this.tripId = tripId;
		this.readings = readings;
	}
	

	@Override
	protected RequestResponse<List<ReadingUploadResult>> executeRequest() throws RequestException {
		return postForObject(serverAddress + "/trip/{id}/reading/bulk", readings, new BulkReadingUploadResponseType(), tripId);
	}

}
