package com.ricex.cartracker.common.viewmodel.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ricex.cartracker.common.entity.CarSupportedCommands;
import com.ricex.cartracker.common.enums.ObdCommand;
import com.ricex.cartracker.common.enums.ObdPids;
import com.ricex.cartracker.common.enums.ObdPids.ObdPidSupport;

public class CarSupportedCommandsViewModel {

	public CarSupportedCommandsViewModel() {
		
	}
	
	public CarSupportedCommandsViewModel(CarSupportedCommands supportedCommands) {
		this.commands = parseFromEntity(supportedCommands);
	}
	
	private Map<ObdCommand, Boolean> commands;
	
	/**
	 * @return the commands
	 */
	public Map<ObdCommand, Boolean> getCommands() {
		return commands;
	}

	/**
	 * @param commands the commands to set
	 */
	public void setCommands(Map<ObdCommand, Boolean> commands) {
		this.commands = commands;
	}
	
	
	public static HashMap<ObdCommand, Boolean> parseFromEntity(CarSupportedCommands supportedCommands) {
		HashMap<ObdCommand, Boolean> commandsMap = new HashMap<ObdCommand, Boolean>();	

		
		addCommandsFromSupportPid(commandsMap, 
				supportedCommands.getPids0120Bitmask(), 
				Arrays.asList(ObdPids.PIDS_01_20_SUPPORT.values()));
		
		addCommandsFromSupportPid(commandsMap, 
				supportedCommands.getPids2140Bitmask(), 
				Arrays.asList(ObdPids.PIDS_21_40_SUPPORT.values()));
		
		addCommandsFromSupportPid(commandsMap, 
				supportedCommands.getPids4160Bitmask(), 
				Arrays.asList(ObdPids.PIDS_41_60_SUPPORT.values()));
		
		addCommandsFromSupportPid(commandsMap, 
				supportedCommands.getPids6180Bitmask(), 
				Arrays.asList(ObdPids.PIDS_61_80_SUPPORT.values()));
		
		addCommandsFromSupportPid(commandsMap, 
				supportedCommands.getPids81A0Bitmask(), 
				Arrays.asList(ObdPids.PIDS_81_A0_SUPPORT.values()));		
		
		return commandsMap;
	}
	
	protected static void addCommandsFromSupportPid(HashMap<ObdCommand, Boolean> commandsMap, 
			int supportedCommandsBitmask, List<? extends ObdPidSupport> pids) {
		
		for (ObdPidSupport pid : pids) {
			boolean supported = false;
			if ((supportedCommandsBitmask & pid.getBitmask()) > 0) {
				supported = true;				
			}
			commandsMap.put(pid.getCommand(), supported);
		}
	}
	
}
