package com.ricex.cartracker.data.query.properties;

import org.apache.commons.lang3.StringUtils;

public final class EntityProperties {

	public enum Car implements EntityProperty {
		
		ID,
		VIN,
		NAME,
		OWNER_ID,
		CREATE_DATE,
		MODIFIED_DATE;
		
		private final String propertyField;
		
		private Car() {
			this.propertyField = this.name();
		}
		
		private Car(String propertyField) {
			this.propertyField = propertyField;
		}

		public String getPropertyField() {
			return propertyField;
		}		
	}
	
	public enum Reading implements EntityProperty {
		
		ID,
		READ_DATE,
		TRIP_ID,
		LATITUDE,
		LONGITUDE,
		AIR_INTAKE_TEMP,
		AMBIENT_AIR_TEMP,
		RENGINE_COOLANT_TEMP,
		OIL_TEMP,
		ENGINE_RPM,
		SPEED,
		MASS_AIR_FLOW,
		THROTTLE_POSITION,
		FUEL_TYPE,
		FUEL_LEVEL,
		CREATE_DATE,
		MODIFIED_DATE;
		
		private final String propertyField;
		
		private Reading() {
			this.propertyField = this.name();
		}
		
		private Reading(String propertyField) {
			this.propertyField = propertyField;
		}

		public String getPropertyField() {
			return propertyField;
		}		
	}
	
	public enum Trip implements EntityProperty {
		
		ID,
		START_DATE,
		END_DATE,
		NAME,
		CAR_ID,
		CREATE_DATE,
		MODIFIED_DATE;
		
		private final String propertyField;
		
		private Trip() {
			this.propertyField = this.name();
		}
		
		private Trip(String propertyField) {
			this.propertyField = propertyField;
		}

		public String getPropertyField() {
			return propertyField;
		}
		
		public static Trip parseFromPropertyField(String propertyField) {
			for (Trip trip : values()) {
				if (StringUtils.equalsIgnoreCase(propertyField, trip.getPropertyField())) {
					return trip;
				}
			}
			return null;
		}
	}
	
	public enum User implements EntityProperty {
		
		ID,
		USERNAME,
		PASSWORD,
		USER_EMAIL,
		ACTIVE,
		LOCKED,
		EXPIRATION_DATE,
		PASSWORD_EXPIRATION_DATE,
		CREATE_DATE,
		MODIFIED_DATE;
		
		private final String propertyField;
		
		private User() {
			this.propertyField = this.name();
		}
		
		private User(String propertyField) {
			this.propertyField = propertyField;
		}

		public String getPropertyField() {
			return propertyField;
		}
	}
	
}
