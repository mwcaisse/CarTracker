"use strict";

define("Service/navigation", ["Service/system"], function (system) {
	
	var util = new (function() {
		var self = this;

		self.navigateTo = function(url) {
			window.location = url;
		};		
		
		self.homeLink = function() {
			return system.baseUrl;
		};
		
		self.carsLink = function() {
			return system.baseUrl + "car/";
		};
		
		self.tripsLink = function() {
			return system.baseUrl + "trip/";
		};
		
		self.viewTripLink = function (tripId) {
			return system.baseUrl + "trip/details?tripId=" + tripId;
		};
		
		self.viewCarLink = function (carId) {
			return system.baseUrl + "car/details?carId=" + carId;
		};
		
		self.tripGridLink = function () {
			return system.baseUrl + "trip/";
		};
		
		self.navigateToViewTrip = function (tripId) {
			self.navigateTo(self.viewTripLink(tripId));
		};
		
		self.navigateToViewCar = function (carId) {
			self.navigateTo(self.viewCarLink(carId));
		};
		
		self.EVENT_NAVIGATION_ACTIVE_CHANGED = "navigation:activeChanged";
		
		self.setActiveNavigation = function (navigationId) {
			system.events.trigger(self.EVENT_NAVIGATION_ACTIVE_CHANGED, {id: navigationId});
		}
	
		
	})();
	
	return util;
	
});