"use strict";

define("Modules/Car/CarDetails/CarDetails", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", 
		 "moment-duration-format",
         "Modules/Car/CarDetails/CarDetailsBinding"],
	function (moment, system, util, proxy) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			carId: -1
		};
		
		var opts = $.extend({}, defaults, options);

		self.originalCar = ko.observable({});
		
		self.carId = opts.carId;
		
		self.name = ko.observable("");		
		self.vin = ko.observable("");		
		   
		self.clear = function () {
			self.name("");		
			selfvin("");
			
			self.originalCar(self.toCarObject());
		};
		
		self.update = function (car) {
			self.name(car.name);		
			self.vin(car.vin);
			
			self.originalCar(self.toCarObject());
		};
		
		self.toCarObject = function () {
			var car = {
				id: self.carId,
				name: self.name(),
				vin: self.vin()				
			};
			return car;
		};

	
		/** Fetch the trips from the server */
		self.fetchCar = function() {
			proxy.car.get(self.carId).then(function (data) {
				self.update(data);
			},
			function (error) {
				alert(error);
			});
		};
		
		/** Saves the car */
		self.saveCar = function () {
			var toSave = self.toCarObject();
			proxy.car.update(toSave).then(function (data) {
				self.originalCar(self.toCarObject());
				system.events.trigger(system.EVENT_ALERT_DISPLAY, {
					message: "Successfully updated the trip!",
					type: system.ALERT_TYPE_SUCCESS
				});
			},
			function (error) {
				system.events.trigger(system.EVENT_ALERT_DISPLAY, {
					message: error,
					type: system.ALERT_TYPE_ERROR
				});
			});
		};
		
		self.canSave = ko.computed(function () {
			var currentCar = self.toCarObject();
			if (ko.toJSON(currentCar) !== ko.toJSON(self.originalCar())) {
				return true;
			}
			return false;
		});
		
		self.load = function() {
			self.fetchCar();
		};	
		
		self.refresh = function () {
			self.load();
		};
		
		self.saveClicked = function () {
			self.saveCar();
		};
	};
	
	return vm;
	
});