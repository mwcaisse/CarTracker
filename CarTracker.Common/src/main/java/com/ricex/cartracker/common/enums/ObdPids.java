package com.ricex.cartracker.common.enums;

public class ObdPids {
		
	public enum PIDS_01_20_SUPPORT {

		PID_01(1 << 31, ObdCommand.MONITOR_STATUS),
		PID_02(1 << 30, ObdCommand.FREEZE_DTC),
		PID_03(1 << 29, ObdCommand.FUEL_SYSTEM_STATUS),
		PID_04(1 << 28, ObdCommand.CALCULATED_ENGINE_LOAD),
		PID_05(1 << 27, ObdCommand.ENGINE_COOLANT_TEMPERATURE),
		PID_06(1 << 26, ObdCommand.SHORT_TERM_FUEL_TRIM_BANK_1),
		PID_07(1 << 25, ObdCommand.LONG_TERM_FUEL_TRIM_BANK_1),
		PID_08(1 << 24, ObdCommand.SHORT_TERM_FUEL_TRIM_BANK_2),
		PID_09(1 << 23, ObdCommand.LONG_TERM_FUEL_TRIM_BANK_2),
		PID_0A(1 << 22, ObdCommand.FUEL_PRESSURE),
		PID_0B(1 << 21, ObdCommand.INTAKE_MANIFOLD_ABSOLUTE_PRESSURE),
		PID_0C(1 << 20, ObdCommand.ENGINE_RPM),
		PID_0D(1 << 19, ObdCommand.VEHICLE_SPEED),
		PID_0E(1 << 18, ObdCommand.TIMING_ADVANCE),
		PID_0F(1 << 17, ObdCommand.INTAKE_AIR_TEMPERATURE),
		PID_10(1 << 16, ObdCommand.MAF),
		PID_11(1 << 15, ObdCommand.THROTTLE_POSITION),
		PID_12(1 << 14, ObdCommand.COMMANDED_SECONDARY_AIR_STATUS),
		PID_13(1 << 13, ObdCommand.OXYGEN_SENSORS_PRESENT_BANKS_2),
		PID_14(1 << 12, ObdCommand.OXYGEN_SENSOR_1),
		PID_15(1 << 11, ObdCommand.OXYGEN_SENSOR_2),
		PID_16(1 << 10, ObdCommand.OXYGEN_SENSOR_3),
		PID_17(1 << 9, ObdCommand.OXYGEN_SENSOR_4),
		PID_18(1 << 8, ObdCommand.OXYGEN_SENSOR_5),
		PID_19(1 << 7, ObdCommand.OXYGEN_SENSOR_6),
		PID_1A(1 << 6, ObdCommand.OXYGEN_SENSOR_7),
		PID_1B(1 << 5, ObdCommand.OXYGEN_SENSOR_8),
		PID_1C(1 << 4, ObdCommand.OBD_STANDARDS),
		PID_1D(1 << 3, ObdCommand.OXYGEN_SENSORS_PRESENT_BANKS_4),
		PID_1E(1 << 2, ObdCommand.AUXILIARY_INPUT_STATUS),
		PID_1F(1 << 1, ObdCommand.RUN_TIME_SINCE_ENGINE_START);	
		
		
		private final int bitmask;
		private final ObdCommand command;
		
		private PIDS_01_20_SUPPORT(int bitmask, ObdCommand command) {
			this.bitmask = bitmask;
			this.command = command;
		}

		
		/**
		 * @return the bitmask
		 */
		public int getBitmask() {
			return bitmask;
		}

		
		/**
		 * @return the command
		 */
		public ObdCommand getCommand() {
			return command;
		}
		
		
	}
	
	public enum PIDS_21_40_SUPPORT {
		
	}	
	
	public enum PIDS_41_60_SUPPORT {
		
	}
	
	public enum PIDS_61_80_SUPPORT {
		
	}
	
	public enum PIDS_81_A0_SUPPORT {
		; // empty, we didn't define any PIDS for this section yet
	}
	
	
	
	
}
