package com.ricex.cartracker.androidrequester.request.type;

import org.springframework.core.ParameterizedTypeReference;

import com.ricex.cartracker.common.entity.Car;
import com.ricex.cartracker.common.viewmodel.EntityResponse;

public class CarResponseType extends ParameterizedTypeReference<EntityResponse<Car>> {

}
