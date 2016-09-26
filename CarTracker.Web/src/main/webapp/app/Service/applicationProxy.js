"use strict";

define("Service/applicationProxy", ["Service/proxy"], function (core) {
	
	var appProxy = new (function() {
		var self = this;
		
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
			update: function(trip) {
				return core.post("trip/", trip);
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