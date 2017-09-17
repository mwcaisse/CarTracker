package com.ricex.cartracker.common.enums;

/** Listing of the Majority of the OBII Commands
 * 
 * Some of the commands are duplicated across different PIDS
 * 
 * @author Mitchell
 *
 */

public enum ObdCommand {

	MONITOR_STATUS ("Monitor status since DTCs cleared"),
	FREEZE_DTC ("Freeze DTC"),
	FUEL_SYSTEM_STATUS ("Fuel system status"),
	CALCULATED_ENGINE_LOAD ("Calculated engine load"),
	ENGINE_COOLANT_TEMPERATURE ("Engine coolant temperature"),
	SHORT_TERM_FUEL_TRIM_BANK_1 ("Short term fuel trim - Bank 1"),
	LONG_TERM_FUEL_TRIM_BANK_1 ("Long term fuel trim - Bank 1"),
	SHORT_TERM_FUEL_TRIM_BANK_2 ("Short term fuel trim - Bank 2"),
	LONG_TERM_FUEL_TRIM_BANK_2 ("Long term fuel trim - Bank 2"),
	FUEL_PRESSURE ("Fuel pressure"),
	INTAKE_MANIFOLD_ABSOLUTE_PRESSURE ("Intake manifold absolute pressure"),
	ENGINE_RPM ("Engine RPM"),
	VEHICLE_SPEED ("Vehicle speed"),
	TIMING_ADVANCE ("Timing advance"),
	INTAKE_AIR_TEMPERATURE ("Intake air temperature"),
	MAF ("Mass air flow rate"),
	THROTTLE_POSITION ("Throttle position"),
	COMMANDED_SECONDARY_AIR_STATUS ("Commanded secondary air status"),
	OXYGEN_SENSORS_PRESENT_BANKS_2 ("Oxygen sensors present"),
	OXYGEN_SENSOR_1 ("Oxygen Sensor 1"),
	OXYGEN_SENSOR_2 ("Oxygen Sensor 2"),
	OXYGEN_SENSOR_3 ("Oxygen Sensor 3"),
	OXYGEN_SENSOR_4 ("Oxygen Sensor 4"),
	OXYGEN_SENSOR_5 ("Oxygen Sensor 5"),
	OXYGEN_SENSOR_6 ("Oxygen Sensor 6"),
	OXYGEN_SENSOR_7 ("Oxygen Sensor 7"),
	OXYGEN_SENSOR_8 ("Oxygen Sensor 8"),
	OBD_STANDARDS ("OBD Standards"),
	OXYGEN_SENSORS_PRESENT_BANKS_4 ("Oxygen seensors present (4 banks)"),
	AUXILIARY_INPUT_STATUS ("Auxiliary input status"),
	RUN_TIME_SINCE_ENGINE_START ("Run time since engine start"),
	DISTANCE_TRAVELED_WITH_MIL_ON ("Distance traveled with MIL on"),
	FUEL_RAIL_PRESSURE ("Fuel rail pressure"),
	FUEL_RAIL_GAUGE_PRESSURE ("Fuel rail gauge pressure"),
	//More Oxygen Sensors?
	COMMANDED_EGR ("Commanded EGR"),
	EGR_ERROR ("EGR Error"),
	COMMANDED_EVAPORATE_PURGE ("Commanded evaporative purge"),
	FUEL_TANK_LEVEL_INPUT ("Fuel tank level input"),
	WARM_UPS_SINCE_CODES_CLEARED ("Warm-ups since codes cleared"),
	DISTANCE_TRAVELED_SINCE_CODES_CLEARED ("Distance traveled since codes cleared"),
	EVAP_SYSTEM_VAPOR_PRESSURE ("Evao system vapor pressure"),
	ABSOLUTE_BAROMETRIC_PRESSURE ("Absolute barometric pressure"),
	//More Oxygen Sensors?
	CATALYST_TEMPERATURE_BANK_1_SENSOR_1 ("Catalist temperature Bank 1 Sensor 1"),
	CATALYST_TEMPERATURE_BANK_2_SENSOR_1 ("Catalist temperature Bank 2 Sensor 1"),
	CATALYST_TEMPERATURE_BANK_1_SENSOR_2 ("Catalist temperature Bank 1 Sensor 2"),
	CATALYST_TEMPERATURE_BANK_2_SENSOR_2 ("Catalist temperature Bank 2 Sensor 2"),
	MONITOR_STATUS_THIS_DRIVE_CYCLE ("Monitor stauts this drive cycle"),
	CONTROL_MODULE_VOLTAGE ("Control module voltage"),
	ABSOLUTE_LOAD_VALUE ("Absolute load value"),
	FUEL_AIR_COMMANDED_EQUIVALENCE_RATIO ("Fuel air commanded equivalence ratio"),
	RELATIVE_THROTTLE_POSITION ("Relative throttle position"),
	AMBIENT_AIR_TEMPERATURE ("Ambient air temperature"),
	ABSOLUTE_THROTTLE_POSITION_B ("Absolute throttle position B"),
	ABSOLUTE_THROTTLE_POSITION_C ("Absolute throttle position C"),
	ACCELERATOR_PEDAL_POSITION_D ("Accelerator pedal position D"),
	ACCELERATOR_PEDAL_POSITION_E ("Accelerator pedal position E"),
	ACCELERATOR_PEDAL_POSITION_F ("Accelerator pedal position F"),
	COMMANDED_THROTTLE_ACTUATOR ("Commanded throttle actuator"),
	TIME_RUN_WITH_MIL_ON ("Time run with MIL on"),
	TIME_SINCE_TROUBLE_CODES_CLEARED ("Time since trouble codes cleared"),
	MAX_FUEL_AIR_EQUIVALENCE_RATIO ("Maximum value for Fuel Air equivalence ratio"),
	MAX_MAF ("Maximum value for MAF"),
	FUEL_TYPE ("Fuel type"),
	ETHANOL_FUEL_PERCENT ("Ethanol fuel %"),
	ABSOLUTE_EVAP_SYSTEM_VAPOR_PRESSURE ("Absolute evap system vapor pressure"),
	//EVAP_SYSTEM_VAPOR_PRESSURE, // duplicated from earlier PID
	//More oxygen sensors
	FUEL_RAIL_ABSOLUTE_PRESSURE ("Fuel rail absolute pressure"),
	RELATIVE_ACCELERATOR_PEDAL_POSITION ("Relative accelerator pedal position"),
	HYBRID_BATTERY_PACK_REMAINING_LIFE ("Hybird battery pack remaining life"),
	ENGINE_OIL_TEMPERATURE ("Engine oil temperature"),
	FUEL_INJECTION_TIMING ("Fuel injection timing"),
	ENGINE_FUEL_RATE ("Engine fuel rate"),
	EMISSIONS_REQUIREMENTS_STANDARDS ("Emission requirement standards"),
	DRIVERS_DEMAND_ENGINE_TORQUE_PERCENT ("Driver's demand engine % torque"),
	ACTUAL_ENGINE_TORQUE_PERCENT ("Actual engine % torque"),
	ENGINE_REFERENCE_TORQUE ("Engine reference torque"),
	ENGINE_TORQUE_PERCENT ("Engine % torque"),
//	ENGINE_COOLANT_TEMPERATURE, // Duplicated?
	EXHAUST_GAS_RECIRCULATION_TEMPERATURE ("Exhaust gas reciculation temperature"),
	TURBOCHARGER_COMPRESSOR_INLET_PRESSURE ("Turbo charger compressor inlet pressure"),
	BOOST_PRESSURE_CONTROL ("Boost pressure control"),
	WASTEGATE_CONTROL ("Wastegate control"),
	EXHAUST_PRESSURE ("Exhaust pressure"),
	TURBOCHARGER_RPM ("Turbocharger RPM"),
	TURBOCHARGER_TEMPERATURE_1 ("Turbocharger temperature 1"),
	TURBOCHARGER_TEMPERATURE_2 ("Turbocharger temperature 1"),
	CHARGE_AIR_COOLER_TEMPERATURE ("Charge air cooler temperature"),
	EXHAUST_GAS_TEMPERATURE_BANK_1 ("Exhaust gas temperature Bank 1"),
	EXHAUST_GAS_TEMPERATURE_BANK_2 ("Exhaust gas temperature Bank 2"),
	ENGINE_RUN_TIME ("Engine run time")
	
	;	
	
	private final String name;
	
	private ObdCommand(String name) {
		this.name = name;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
}
