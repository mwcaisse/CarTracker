package com.ricex.cartracker.common.enums;

public class ObdPids {
		
	public interface ObdPidSupport {
		
		public int getBitmask();
		
		public ObdCommand getCommand();
		
		public String getName();
	}
	
	public enum PIDS_01_20_SUPPORT implements ObdPidSupport {

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
		
		public String getName() {
			return toString().replaceAll("_", " ");
		}
		
		
	}
	
	public enum PIDS_21_40_SUPPORT implements ObdPidSupport {
		
		PID_21(1 << 31, ObdCommand.DISTANCE_TRAVELED_WITH_MIL_ON),
		PID_22(1 << 30, ObdCommand.FUEL_RAIL_PRESSURE),
		PID_23(1 << 29, ObdCommand.FUEL_RAIL_GAUGE_PRESSURE),
		//More Oxygen Sensors, Not mapping right now
		//PID_24(1 << 28, ObdCommand.UN_MAPPED),
		//PID_25(1 << 27, ObdCommand.UN_MAPPED),
		//PID_26(1 << 26, ObdCommand.UN_MAPPED),
		//PID_27(1 << 25, ObdCommand.UN_MAPPED),
		//PID_28(1 << 24, ObdCommand.UN_MAPPED),
		//PID_29(1 << 23, ObdCommand.UN_MAPPED),
		//PID_2A(1 << 22, ObdCommand.UN_MAPPED),
		//PID_2B(1 << 21, ObdCommand.UN_MAPPED),
		PID_2C(1 << 20, ObdCommand.COMMANDED_EGR),
		PID_2D(1 << 19, ObdCommand.EGR_ERROR),
		PID_2E(1 << 18, ObdCommand.COMMANDED_EVAPORATE_PURGE),
		PID_2F(1 << 17, ObdCommand.FUEL_TANK_LEVEL_INPUT),
		PID_30(1 << 16, ObdCommand.WARM_UPS_SINCE_CODES_CLEARED),
		PID_31(1 << 15, ObdCommand.DISTANCE_TRAVELED_SINCE_CODES_CLEARED),
		PID_32(1 << 14, ObdCommand.EVAP_SYSTEM_VAPOR_PRESSURE),
		PID_33(1 << 13, ObdCommand.ABSOLUTE_BAROMETRIC_PRESSURE),
		//More Oxygen Sensors, Again not mapping right now
		//PID_34(1 << 12, ObdCommand.UN_MAPPED),
		//PID_35(1 << 11, ObdCommand.UN_MAPPED),
		//PID_36(1 << 10, ObdCommand.UN_MAPPED),
		//PID_37(1 << 9, ObdCommand.UN_MAPPED),
		//PID_38(1 << 8, ObdCommand.UN_MAPPED),
		//PID_39(1 << 7, ObdCommand.UN_MAPPED),
		//PID_3A(1 << 6, ObdCommand.UN_MAPPED),
		//PID_3B(1 << 5, ObdCommand.UN_MAPPED),
		PID_3C(1 << 4, ObdCommand.CATALYST_TEMPERATURE_BANK_1_SENSOR_1),
		PID_3D(1 << 3, ObdCommand.CATALYST_TEMPERATURE_BANK_2_SENSOR_1),
		PID_3E(1 << 2, ObdCommand.CATALYST_TEMPERATURE_BANK_1_SENSOR_2),
		PID_3F(1 << 1, ObdCommand.CATALYST_TEMPERATURE_BANK_2_SENSOR_2);	
		
		private final int bitmask;
		private final ObdCommand command;
		
		private PIDS_21_40_SUPPORT(int bitmask, ObdCommand command) {
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
		
		public String getName() {
			return toString().replaceAll("_", " ");
		}
	}	
	
	public enum PIDS_41_60_SUPPORT implements ObdPidSupport  {
		
		PID_41(1 << 31, ObdCommand.MONITOR_STATUS_THIS_DRIVE_CYCLE),
		PID_42(1 << 30, ObdCommand.CONTROL_MODULE_VOLTAGE),
		PID_43(1 << 29, ObdCommand.ABSOLUTE_LOAD_VALUE),
		PID_44(1 << 28, ObdCommand.FUEL_AIR_COMMANDED_EQUIVALENCE_RATIO),
		PID_45(1 << 27, ObdCommand.RELATIVE_THROTTLE_POSITION),
		PID_46(1 << 26, ObdCommand.AMBIENT_AIR_TEMPERATURE),
		PID_47(1 << 25, ObdCommand.ABSOLUTE_THROTTLE_POSITION_B),
		PID_48(1 << 24, ObdCommand.ABSOLUTE_THROTTLE_POSITION_C),
		PID_49(1 << 23, ObdCommand.ACCELERATOR_PEDAL_POSITION_D),
		PID_4A(1 << 22, ObdCommand.ACCELERATOR_PEDAL_POSITION_E),
		PID_4B(1 << 21, ObdCommand.ACCELERATOR_PEDAL_POSITION_F),
		PID_4C(1 << 20, ObdCommand.COMMANDED_THROTTLE_ACTUATOR),
		PID_4D(1 << 19, ObdCommand.TIME_RUN_WITH_MIL_ON),
		PID_4E(1 << 18, ObdCommand.TIME_SINCE_TROUBLE_CODES_CLEARED),
		PID_4F(1 << 17, ObdCommand.MAX_FUEL_AIR_EQUIVALENCE_RATIO),
		PID_50(1 << 16, ObdCommand.MAX_MAF),
		PID_51(1 << 15, ObdCommand.FUEL_TYPE),
		PID_52(1 << 14, ObdCommand.ETHANOL_FUEL_PERCENT),
		PID_53(1 << 13, ObdCommand.ABSOLUTE_EVAP_SYSTEM_VAPOR_PRESSURE),
		//Duplicated EVAP_SYSTEM_VAPOR_PRESSURE PID,
		// More Oxygen sensors
		//PID_54(1 << 12, ObdCommand.UN_MAPPED),
		//PID_55(1 << 11, ObdCommand.UN_MAPPED),
		//PID_56(1 << 10, ObdCommand.UN_MAPPED),
		//PID_57(1 << 9, ObdCommand.UN_MAPPED),
		//PID_58(1 << 8, ObdCommand.UN_MAPPED),
		PID_59(1 << 7, ObdCommand.FUEL_RAIL_ABSOLUTE_PRESSURE),
		PID_5A(1 << 6, ObdCommand.RELATIVE_ACCELERATOR_PEDAL_POSITION),
		PID_5B(1 << 5, ObdCommand.HYBRID_BATTERY_PACK_REMAINING_LIFE),
		PID_5C(1 << 4, ObdCommand.ENGINE_OIL_TEMPERATURE),
		PID_5D(1 << 3, ObdCommand.FUEL_INJECTION_TIMING),
		PID_5E(1 << 2, ObdCommand.ENGINE_FUEL_RATE),
		PID_5F(1 << 1, ObdCommand.EMISSIONS_REQUIREMENTS_STANDARDS);	
		
		
		private final int bitmask;
		private final ObdCommand command;
		
		private PIDS_41_60_SUPPORT(int bitmask, ObdCommand command) {
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
		
		public String getName() {
			return toString().replaceAll("_", " ");
		}
		
	}
	
	public enum PIDS_61_80_SUPPORT implements ObdPidSupport {
		
		PID_61(1 << 31, ObdCommand.DRIVERS_DEMAND_ENGINE_TORQUE_PERCENT),
		PID_62(1 << 30, ObdCommand.ACTUAL_ENGINE_TORQUE_PERCENT),
		PID_63(1 << 29, ObdCommand.ENGINE_REFERENCE_TORQUE),
		PID_64(1 << 28, ObdCommand.ENGINE_TORQUE_PERCENT),
		//PID_65(1 << 27, ObdCommand.UN_MAPPED),
		//PID_66(1 << 26, ObdCommand.UN_MAPPED),
		//PID_67(1 << 25, ObdCommand.UN_MAPPED),
		//PID_68(1 << 24, ObdCommand.UN_MAPPED),
		//PID_69(1 << 23, ObdCommand.UN_MAPPED),
		//PID_6A(1 << 22, ObdCommand.UN_MAPPED),
		PID_6B(1 << 21, ObdCommand.EXHAUST_GAS_RECIRCULATION_TEMPERATURE),
		//PID_6C(1 << 20, ObdCommand.UN_MAPPED),
		//PID_6D(1 << 19, ObdCommand.UN_MAPPED),
		//PID_6E(1 << 18, ObdCommand.UN_MAPPED),
		PID_6F(1 << 17, ObdCommand.TURBOCHARGER_COMPRESSOR_INLET_PRESSURE),
		PID_70(1 << 16, ObdCommand.BOOST_PRESSURE_CONTROL),
		//PID_71(1 << 15, ObdCommand.UN_MAPPED),
		PID_72(1 << 14, ObdCommand.WASTEGATE_CONTROL),
		PID_73(1 << 13, ObdCommand.EXHAUST_PRESSURE),
		PID_74(1 << 12, ObdCommand.TURBOCHARGER_RPM),
		PID_75(1 << 11, ObdCommand.TURBOCHARGER_TEMPERATURE_1),
		PID_76(1 << 10, ObdCommand.TURBOCHARGER_TEMPERATURE_2),
		PID_77(1 << 9, ObdCommand.CHARGE_AIR_COOLER_TEMPERATURE),
		PID_78(1 << 8, ObdCommand.EXHAUST_GAS_TEMPERATURE_BANK_1),
		PID_79(1 << 7, ObdCommand.EXHAUST_GAS_TEMPERATURE_BANK_2),
		//PID_7A(1 << 6, ObdCommand.UN_MAPPED),
		//PID_7B(1 << 5, ObdCommand.UN_MAPPED),
		//PID_7C(1 << 4, ObdCommand.UN_MAPPED),
		//PID_7D(1 << 3, ObdCommand.UN_MAPPED),
		//PID_7E(1 << 2, ObdCommand.UN_MAPPED),
		PID_7F(1 << 1, ObdCommand.ENGINE_RUN_TIME);		
		
		
		private final int bitmask;
		private final ObdCommand command;
		
		private PIDS_61_80_SUPPORT(int bitmask, ObdCommand command) {
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
		
		public String getName() {
			return toString().replaceAll("_", " ");
		}
		
	}
	
	public enum PIDS_81_A0_SUPPORT implements ObdPidSupport {
		; // empty, we didn't define any PIDS for this section yet
		
		private final int bitmask;
		private final ObdCommand command;
		
		private PIDS_81_A0_SUPPORT(int bitmask, ObdCommand command) {
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
		
		public String getName() {
			return toString().replaceAll("_", " ");
		}
				
	}
	
	
	
	
}
