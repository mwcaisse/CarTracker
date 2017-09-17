"use strict";

define("Service/applicationProxy", ["Service/proxy"], function (core) {
	
	var appProxy = new (function() {
		var self = this;
		
		self.car = {
			get: function(id) {
				return core.get("car/" + id);
			},
			getByVin: function(vin) {
				return core.get("car/vin/" + vin);
			},
			getAll: function() {
				return core.get("car/");
			},
			getAllPaged: function (startAt, maxResults, sort) {				
				return core.getPaged("car/", startAt, maxResults, sort);
			},
			create: function(car) {
				return core.post("car/", car);
			},
			update: function(car) {
				return core.put("car/", car);
			},
			getSupportedCommands: function (id) {
				return core.get("car/" + id + "/supportedCommands/");
			}
		};
		
		self.trip = {
			get: function(id) {
				return core.get("trip/" + id);
			},			
			getAll: function() {
				return core.get("trip/");
			},
			getAllForCar: function(carId) {
				return core.get("car/" + carId + "/trip/");
			},
			getAllForCarPaged: function(carId, startAt, maxResults, sort) {				
				return core.getPaged("car/" + carId + "/trip/", startAt, maxResults, sort);
			},
			getPreviousNext: function (id) {
				return core.get("trip/" + id + "/prevnext");
			},
			update: function(trip) {
				return core.put("trip/", trip);
			},
			process: function (id) {
				return core.post("trip/" + id + "/process"); 
			},
			processUnprocessed: function () {
				return core.post("trip/process/unprocessed");
			},
			setStartingPlace: function (id, placeId) {
				return core.post("trip/" + id + "/starting-place?placeId="+ placeId);
			},
			setDestinationPlace: function(id, placeId) {
				return core.post("trip/" + id + "/destination-place?placeId="+ placeId);
			}
		};
		
		self.tripPossiblePlaces = {
			getForTrip: function (tripId, type, startAt, maxResults, sort) {
				return core.getPaged("trip/" + tripId + "/possibleplaces/" + type, startAt, maxResults, sort);
			}
		};
		
		self.reading = {
			get: function(id) {
				return core.get("reading/" + id);
			},
			getAllForTrip: function(tripId) {
				return core.get("trip/" + tripId + "/reading/");
			}
		};	
		
		self.user = {
			register: function (registration) {
				return core.post("user/register", registration);
			},
			available: function (username) {
				return core.get("user/available?username=" + username);
			},
			me: function () {
				return core.get("user/me", {});
			}
		};
		
		self.registrationKey = {
			get: function (id) {
				return core.get("registration/key/" + id);
			},
			getAllPaged: function (startAt, maxResults, sort) {
				return core.getPaged("registration/key/", startAt, maxResults, sort);						
			},
			create: function (toCreate) {
				return core.post("registration/key/", toCreate);
			},
			update: function (toUpdate) {
				return core.put("registration/key/", toUpdate);
			}
		};
		
		self.authToken = {
			getAllActivePaged: function (startAt, maxResults, sort) {
				return core.getPaged("user/tokens/active", startAt, maxResults, sort);
			}
		};
		
		self.readerLog = {
			getAllPaged: function (startAt, maxResults, sort) {
				return core.getPaged("log/reader/", startAt, maxResults, sort);
			}
		};
				 
		
	})();
	
	return appProxy;
	
});