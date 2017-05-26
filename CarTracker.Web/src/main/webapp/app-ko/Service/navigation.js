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
		
		self.readerLogLink = function () {
			return system.baseUrl + "log/reader";
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
		
		self.logoutLink = function () {
			return system.baseUrl + "logout";
		};
		
		self.adminRegistrationKeyLink = function () {
			return system.baseUrl + "admin/registrationKeys";
		};
		
		self.loginLink = function (param) {
			return system.baseUrl + "login" + (param ? ("?" + param) : "");
		};
		
		self.userAuthenticationTokensLink = function () {
			return system.baseUrl + "user/tokens";
		};
		
		self.navigateToHome = function () {
			self.navigateTo(self.homeLink());
		};
		
		self.navigateToViewTrip = function (tripId) {
			self.navigateTo(self.viewTripLink(tripId));
		};
		
		self.navigateToViewCar = function (carId) {
			self.navigateTo(self.viewCarLink(carId));
		};
		
		self.navigateToLogout = function () {
			return self.navigateTo(self.logoutLink());
		};
		
		self.navigateToLogin = function (param) {
			return self.navigateTo(self.loginLink(param));
		};
		
		self.navigateToAdminRegistrationKey = function () {
			return self.navigateTo(self.adminRegistrationKeyLink());
		};
		
		self.EVENT_NAVIGATION_ACTIVE_CHANGED = "navigation:activeChanged";
		
		self.setActiveNavigation = function (navigationId) {
			system.events.trigger(self.EVENT_NAVIGATION_ACTIVE_CHANGED, {id: navigationId});
		};
	
		
	})();
	
	return util;
	
});