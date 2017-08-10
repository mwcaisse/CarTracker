package com.ricex.cartracker.data.query.properties;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public enum ReaderLog implements EntityProperty {
		ID,
		TYPE,
		MESSAGE,
		DATE,
		CREATE_DATE,
		MODIFIED_DATE;
		
		private final String propertyField;
		
		private ReaderLog() {
			this.propertyField = this.name();
		}
		
		private ReaderLog(String propertyField) {
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
	}
	
	public enum TripPossiblePlace implements EntityProperty {
		
		ID,
		TRIP_ID,
		PLACE_ID,
		PLACE_TYPE,
		DISTANCE,
		CREATE_DATE,
		MODIFIED_DATE;
		
		private final String propertyField;
		
		private TripPossiblePlace() {
			this.propertyField = this.name();
		}
		
		private TripPossiblePlace(String propertyField) {
			this.propertyField = propertyField;
		}

		public String getPropertyField() {
			return propertyField;
		}
	}
	
	public enum User implements EntityProperty {
		
		ID,
		NAME,
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
	
	public enum RegistrationKey implements EntityProperty {
		
		ID,
		KEY_VAL,
		USES_REMAINING,
		ACTIVE,
		CREATE_DATE,
		MODIFIED_DATE;
		
		private final String propertyField;
		
		private RegistrationKey() {
			this.propertyField = this.name();
		}
		
		private RegistrationKey(String propertyField) {
			this.propertyField = propertyField;
		}

		public String getPropertyField() {
			return propertyField;
		}
	}
	
	public enum RegistrationKeyUse implements EntityProperty {
		
		ID,
		KEY_ID,
		USER_ID,
		CREATE_DATE,
		MODIFIED_DATE;
		
		private final String propertyField;
		
		private RegistrationKeyUse() {
			this.propertyField = this.name();
		}
		
		private RegistrationKeyUse(String propertyField) {
			this.propertyField = propertyField;
		}

		public String getPropertyField() {
			return propertyField;
		}
	}
	
	public enum UserAuthenticationToken implements EntityProperty {
		
		ID,
		USER_ID,
		TOKEN,
		DEVICE_UUID,
		ACTIVE,
		LAST_LOGIN,
		LAST_LOGIN_ADDRESS,
		EXPIRATION_DATE,
		CREATE_DATE,
		MODIFIED_DATE;
		
		private final String propertyField;
		
		private UserAuthenticationToken() {
			this.propertyField = this.name();
		}
		
		private UserAuthenticationToken(String propertyField) {
			this.propertyField = propertyField;
		}

		public String getPropertyField() {
			return propertyField;
		}
	}
	
	public static EntityProperty parseFromPropertyField(EntityType type, String propertyField) {
		List<EntityProperty> properties = Arrays.asList(getPropertiesForEntity(type));
		for (EntityProperty property : properties) {
			if (StringUtils.equalsIgnoreCase(propertyField, property.getPropertyField())) {
				return property;
			}
		}
		return null;
	}
	
	private static Map<EntityType, EntityProperty[]> entityTypeProperties;
	
	static {
		entityTypeProperties = new HashMap<EntityType, EntityProperty[]>();
		
		entityTypeProperties.put(EntityType.CAR, Car.values());
		entityTypeProperties.put(EntityType.READER_LOG, ReaderLog.values());
		entityTypeProperties.put(EntityType.READING, Reading.values());
		entityTypeProperties.put(EntityType.TRIP, Trip.values());
		entityTypeProperties.put(EntityType.TRIP_POSSIBLE_PLACE, TripPossiblePlace.values());
		entityTypeProperties.put(EntityType.USER, User.values());
		entityTypeProperties.put(EntityType.REGISTRATION_KEY, RegistrationKey.values());
		entityTypeProperties.put(EntityType.REGISTRATION_KEY_USE, RegistrationKeyUse.values());
		entityTypeProperties.put(EntityType.USER_AUTHENTICATION_TOKEN, UserAuthenticationToken.values());
	}
	
	public static EntityProperty[] getPropertiesForEntity(EntityType entityType) {
		return entityTypeProperties.get(entityType);
	}
	
}
