"use strict";

define("Modules/Trip/TripGrid", ["moment", "Service/util", "Service/applicationProxy", "Modules/Trip/TripGridBinding"],
		function (moment, util, proxy) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			carId: -1
		};
		
		var opts = $.extend({}, defaults, options);

		self.carId = opts.carId;
		
		self.trips = ko.observableArray([]);
		
		self.TripModel = function (data) {
			var trip = this;
			
			trip.id = data.id;
			trip.name = data.name;
			trip.startDate = moment(data.startDate);
			trip.endDate = moment(data.endDate);
			
			trip.startDateDisplay = ko.computed(function () {
				return trip.startDate.format("YYYY-MM-DD HH:mm:ss");
			});
			
			trip.endDateDisplay = ko.computed(function () {
				return trip.endDate.format("YYYY-MM-DD HH:mm:ss");
			});		                                       
			
			return trip;
		};
	
		/** Fetch the trips from the server */
		self.fetchTrips = function() {
			proxy.trip.getAllForCar(self.carId).then(function (data) {
				var trips = $.map(data, function (elm, ind) {
					return new self.TripModel(elm);
				});
				self.trips(trips);
			},
			function (error) {
				alert(error);
			});
		}
		
		self.load = function() {
			self.fetchTrips();
		};		
	};
	
	return vm;
	
});