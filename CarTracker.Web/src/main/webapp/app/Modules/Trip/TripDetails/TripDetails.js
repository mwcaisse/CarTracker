"use strict";

define("Modules/Trip/TripDetails/TripDetails", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", 
		 "moment-duration-format",
         "Modules/Trip/TripDetails/TripDetailsBinding"],
	function (moment, system, util, proxy) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			tripId: -1
		};
		
		var opts = $.extend({}, defaults, options);

		self.originalTrip = ko.observable({});
		
		self.tripId = opts.tripId;
		
		self.name = ko.observable("");		
		self.carId = ko.observable(-1);
		self.startDate = ko.observable(moment());
		self.endDate = ko.observable(moment());
		self.averageSpeed = ko.observable(0);
		self.maximumSpeed = ko.observable(0);
		self.averageEngineRPM = ko.observable(0);
		self.maxEngineRPM = ko.observable(0);
		self.distanceTraveled = ko.observable(0);
		self.idleTime = ko.observable(moment.duration(0));
		self.status = ko.observable("");		
		
		self.tripTime = ko.computed(function () {
			var msDiff = self.endDate().diff(self.startDate());
			return moment.duration(msDiff, "ms");
		});
		
		self.tripTimeDisplay = ko.computed(function () {
			return self.tripTime().format("hh:mm:ss");
		});
		
		self.startDateDisplay = ko.computed(function () {
			return self.startDate().format("YYYY-MM-DD HH:mm:ss");
		});
		
		self.endDateDisplay = ko.computed(function () {
			return self.endDate().format("YYYY-MM-DD HH:mm:ss");
		});	
		
		self.idleTimeDisplay = ko.computed(function () {
			return self.idleTime().format("hh:mm:ss");
		});
		
		self.useImperial = ko.observable(true);
		
		self.useImperial.subscribe(function (newValue) {
			system.events.trigger("trip:useImperialChanged", {useImperial: newValue});
		});
		
		
		self.averageSpeedDisplay = ko.computed(function () {
			var units = self.useImperial() ? "mph" : "kmph";
			var speed = self.averageSpeed();
			if (self.useImperial()) {
				speed = util.convertKmToMi(speed);
			}			
			return util.round(speed, 2) + " " + units;		
		});
		
		self.maximumSpeedDisplay = ko.computed(function () {
			var units = self.useImperial() ? "mph" : "kmph";
			var speed = self.maximumSpeed();
			if (self.useImperial()) {
				speed = util.convertKmToMi(speed);
			}
			return util.round(speed, 2) + " " + units;	
		});
		
		self.distanceTraveledDisplay = ko.computed(function () {
			var units = self.useImperial() ? "mi" : "km";
			var distance = self.distanceTraveled();
			if (self.useImperial()) {
				distance = util.convertKmToMi(distance);		
			}
			return util.round(distance, 2) + " " + units;	
		});
		
		self.maxEngineRPMDisplay = ko.computed(function () {
			return util.round(self.maxEngineRPM(), 2);
		});
		
		self.averageEngineRPMDisplay = ko.computed(function () {
			return util.round(self.averageEngineRPM(), 2);
		});
		   
		self.clear = function () {
			self.name("");		
			self.carId(-1);
			self.startDate(moment());
			self.endDate(moment());
			self.averageSpeed(0);
			self.maximumSpeed(0);
			self.averageEngineRPM(0);
			self.maxEngineRPM(0);
			self.distanceTraveled(0);
			self.idleTime(moment.duration(0));
			self.status("");
			
			self.originalTrip(self.toTripObject());
		};
		
		self.update = function (trip) {
			self.name(trip.name);		
			self.carId(trip.carId);
			self.startDate(moment(trip.startDate));
			self.endDate(moment(trip.endDate));
			self.averageSpeed(trip.averageSpeed);
			self.maximumSpeed(trip.maximumSpeed);
			self.averageEngineRPM(trip.averageEngineRPM);
			self.maxEngineRPM(trip.maxEngineRPM);
			self.distanceTraveled(trip.distanceTraveled);
			self.idleTime(moment.duration(trip.idleTime));
			self.status(trip.status);
			
			self.originalTrip(self.toTripObject());
		};
		
		self.toTripObject = function () {
			var trip = {
				id: self.tripId,
				name: self.name(),
				carId: self.carId(),
				startDate: self.startDate().toDate().getTime(),
				endDate: self.endDate().toDate().getTime(),
				averageSpeed: self.averageSpeed(),
				maximumSpeed: self.maximumSpeed(),
				averageEngineRPM: self.averageEngineRPM(),
				distanceTraveled: self.distanceTraveled(),
				idleTime: self.idleTime().asMilliseconds(),
				status: self.status()				
			};
			return trip;
		};

	
		/** Fetch the trips from the server */
		self.fetchTrip = function() {
			proxy.trip.get(self.tripId).then(function (data) {
				self.update(data);
			},
			function (error) {
				alert(error);
			});
		};
		
		/** Saves the trip */
		self.saveTrip = function () {
			var toSave = self.toTripObject();
			proxy.trip.update(toSave).then(function (data) {
				self.originalTrip(self.toTripObject());
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
			var currentTrip = self.toTripObject();
			if (ko.toJSON(currentTrip) !== ko.toJSON(self.originalTrip())) {
				return true;
			}
			return false;
		});
		
		self.load = function() {
			self.fetchTrip();
		};	
		
		self.refresh = function () {
			self.load();
		};
		
		self.saveClicked = function () {
			self.saveTrip();
		};
	};
	
	return vm;
	
});