"use strict";

define("Service/navigation", ["Service/system"], function (system) {
	
	var util = new (function() {
		var self = this;

		self.navigateTo = function(url) {
			window.location = url;
		};		
		
		self.viewTripLink = function (tripId) {
			return system.baseUrl + "trip/details?tripId=" + tripId;
		};
		
		self.tripGridLink = function () {
			return system.baseUrl + "trip/";
		};
		
		self.navigateToViewTrip = function (tripId) {
			self.navigateTo(self.viewTripLink(tripId));
		};
	
		
	})();
	
	return util;
	
});