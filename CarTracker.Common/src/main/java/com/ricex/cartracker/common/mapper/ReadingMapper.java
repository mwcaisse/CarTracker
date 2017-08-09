package com.ricex.cartracker.common.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ricex.cartracker.common.entity.Reading;
import com.ricex.cartracker.common.viewmodel.entity.ReadingViewModel;

public class ReadingMapper {

	
	public static ReadingViewModel toViewModel(Reading reading) {
		if (null == reading) {
			return null;
		}
		
		ReadingViewModel vm = new ReadingViewModel();
		vm.setReadDate(reading.getReadDate());
		vm.setTripId(reading.getTripId());
		vm.setLatitude(reading.getLatitude());
		vm.setLongitude(reading.getLongitude());
		vm.setAirIntakeTemperature(reading.getAirIntakeTemperature());
		vm.setAmbientAirTemperature(reading.getAmbientAirTemperature());
		vm.setEngineCoolantTemperature(reading.getEngineCoolantTemperature());
		vm.setOilTemperature(reading.getOilTemperature());
		vm.setEngineRPM(reading.getEngineRPM());
		vm.setSpeed(reading.getSpeed());
		vm.setMassAirFlow(reading.getMassAirFlow());
		vm.setThrottlePosition(reading.getThrottlePosition());
		vm.setFuelLevel(reading.getFuelLevel());
		vm.setFuelType(reading.getFuelType());
		
		return vm;
	}
	
	public static List<ReadingViewModel> toViewModel(List<Reading> readings) {
		List<ReadingViewModel> vms = new ArrayList<ReadingViewModel>();
		if (null == readings) {
			return vms;
		}
		for(Reading reading : readings) {
			vms.add(toViewModel(reading));
		}
		return vms;
	}
	
}
