package com.ricex.cartracker.common.viewmodel.entity;

import java.util.HashMap;
import java.util.Map;

import com.ricex.cartracker.common.entity.CarSupportedCommands;
import com.ricex.cartracker.common.enums.ObdCommand;
import com.ricex.cartracker.common.enums.ObdPids;

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
		
		int bitmask = supportedCommands.getPids0120Bitmask();
		
		for (ObdPids.PIDS_01_20_SUPPORT pid : ObdPids.PIDS_01_20_SUPPORT.values()) {
			boolean supported = false;
			if ((bitmask & pid.getBitmask()) > 0) {
				supported = true;				
			}
			commandsMap.put(pid.getCommand(), supported);
		}
		
		
		return commandsMap;
	}
	
}
