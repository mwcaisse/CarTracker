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
			create: function(car) {
				return core.post("car/", car);
			},
			update: function(car) {
				return core.put("car/", car);
			}				
		};
		
		self.trip = {
			get: function(id) {
				return core.get("trip/" + id);
			},
			getCalculated: function (id) {
				return core.get("trip/" + id + "/calculated");
			},
			getAll: function() {
				return core.get("trip/");
			},
			getAllForCar: function(carId) {
				return core.get("car/" + carId + "/trip/");
			},
			getAllForCarPaged: function(carId, startAt, maxResults) {
				return core.get("car/" + carId + "/trip/?startAt=" + startAt + "&maxResults=" + maxResults);
			},
			update: function(trip) {
				return core.put("trip/", trip);
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
				 
		
	})();
	
	return appProxy;
	
});