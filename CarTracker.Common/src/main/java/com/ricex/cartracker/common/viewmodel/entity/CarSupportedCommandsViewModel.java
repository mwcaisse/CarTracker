package com.ricex.cartracker.common.viewmodel.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.ricex.cartracker.common.entity.CarSupportedCommands;
import com.ricex.cartracker.common.enums.ObdCommand;
import com.ricex.cartracker.common.enums.ObdPids;
import com.ricex.cartracker.common.enums.ObdPids.ObdPidSupport;

public class CarSupportedCommandsViewModel {

	private List<CommandViewModel> supportedCommands;	
	
	public CarSupportedCommandsViewModel() {
		supportedCommands = new ArrayList<CommandViewModel>();
	}
	
	public CarSupportedCommandsViewModel(CarSupportedCommands e) {
		this.supportedCommands = parseFromEntity(e);				
	}	

	/**
	 * @return the supportedCommands
	 */
	public List<CommandViewModel> getSupportedCommands() {
		return supportedCommands;
	}

	/**
	 * @param supportedCommands the supportedCommands to set
	 */
	public void setSupportedCommands(List<CommandViewModel> supportedCommands) {
		this.supportedCommands = supportedCommands;
	}

	public static List<CommandViewModel> parseFromEntity(CarSupportedCommands e) {
		List<CommandViewModel> supportedCommands = new ArrayList<CommandViewModel>();

		
		supportedCommands.addAll(commandsFromSupportPid(e.getPids0120Bitmask(), 
				Arrays.asList(ObdPids.PIDS_01_20_SUPPORT.values())));
		
		supportedCommands.addAll(commandsFromSupportPid(e.getPids2140Bitmask(), 
				Arrays.asList(ObdPids.PIDS_21_40_SUPPORT.values())));
		
		supportedCommands.addAll(commandsFromSupportPid(e.getPids4160Bitmask(), 
				Arrays.asList(ObdPids.PIDS_41_60_SUPPORT.values())));
		
		supportedCommands.addAll(commandsFromSupportPid(e.getPids6180Bitmask(), 
				Arrays.asList(ObdPids.PIDS_61_80_SUPPORT.values())));
		
		supportedCommands.addAll(commandsFromSupportPid(e.getPids81A0Bitmask(), 
				Arrays.asList(ObdPids.PIDS_81_A0_SUPPORT.values())));		
		
		return supportedCommands;
	}
	
	protected static List<CommandViewModel> commandsFromSupportPid(
			int supportedCommandsBitmask, List<? extends ObdPidSupport> pids) {
		
		List<CommandViewModel> supportedCommands = new ArrayList<CommandViewModel>();
		
		for (ObdPidSupport pid : pids) {
			boolean supported = false;
			if ((supportedCommandsBitmask & pid.getBitmask()) > 0) {
				supported = true;				
			}
			CommandViewModel vm = new CommandViewModel();
			vm.setName(pid.getCommand().getName());
			vm.setPid(pid.getName());
			vm.setSupported(supported);
			supportedCommands.add(vm);
		}
		
		return supportedCommands;
	}
	
}
