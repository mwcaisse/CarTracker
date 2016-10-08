"use strict";

define("Modules/Trip/TripDetails/TripDetails", 
		["moment", "Service/util", "Service/applicationProxy", 
		 "moment-duration-format",
         "Modules/Trip/TripDetails/TripDetailsBinding"],
	function (moment, util, proxy) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			tripId: -1
		};
		
		var opts = $.extend({}, defaults, options);

		self.tripId = opts.tripId;
		
		self.name = ko.observable("");		
		self.startDate = ko.observable(moment());
		self.endDate = ko.observable(moment());
		self.averageSpeed = ko.observable(0);
		self.maximumSpeed = ko.observable(0);
		self.averageEngineRPM = ko.observable(0);
		self.maxEngineRPM = ko.observable(0);
		self.distanceTraveled = ko.observable(0);
		self.idleTime = ko.observable(moment.duration(0));
		
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
			self.startDate(moment());
			self.endDate(moment());
			self.averageSpeed(0);
			self.maximumSpeed(0);
			self.averageEngineRPM(0);
			self.maxEngineRPM(0);
			self.distanceTraveled(0);
			self.idleTime(moment.duration(0));
		};
		
		self.update = function (trip) {
			self.name(trip.name);		
			self.startDate(moment(trip.startDate));
			self.endDate(moment(trip.endDate));
			self.averageSpeed(trip.averageSpeed);
			self.maximumSpeed(trip.maximumSpeed);
			self.averageEngineRPM(trip.averageEngineRPM);
			self.maxEngineRPM(trip.maxEngineRPM);
			self.distanceTraveled(trip.distanceTraveled);
			self.idleTime(moment.duration(trip.idleTime));
		};

	
		/** Fetch the trips from the server */
		self.fetchTrip = function() {
			proxy.trip.getCalculated(self.tripId).then(function (data) {
				self.update(data);
			},
			function (error) {
				alert(error);
			});
		};
		
		self.load = function() {
			self.fetchTrip();
		};	
		
		self.refresh = function () {
			self.load();
		};
	};
	
	return vm;
	
});