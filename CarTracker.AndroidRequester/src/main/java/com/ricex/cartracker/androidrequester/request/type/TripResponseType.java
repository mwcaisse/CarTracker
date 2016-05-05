package com.ricex.cartracker.androidrequester.request.type;

import org.springframework.core.ParameterizedTypeReference;

import com.ricex.cartracker.common.entity.Trip;
import com.ricex.cartracker.common.viewmodel.EntityResponse;

public class TripResponseType extends ParameterizedTypeReference<EntityResponse<Trip>> {

}
