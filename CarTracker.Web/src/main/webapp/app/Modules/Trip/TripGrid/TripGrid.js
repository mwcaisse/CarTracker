"use strict";

define("Modules/Trip/TripGrid/TripGrid", ["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation",
                                          "Modules/Common/Pager/Pager",
                                          "Modules/Common/ColumnHeader/ColumnHeader",
                                          "Modules/Trip/TripGrid/TripGridBinding"],
		function (moment, system, util, proxy, navigation, pager, columnHeader) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			carId: -1
		};
		
		var opts = $.extend({}, defaults, options);

		self.carId = opts.carId;
		
		self.trips = ko.observableArray([]);
		
		self.sort = null;
		
		self.TripModel = function (data) {
			var trip = this;
			
			trip.id = ko.observable(data.id);
			trip.name = ko.observable(data.name);
			trip.startDate = ko.observable(moment(data.startDate));
			trip.endDate = ko.observable(moment(data.endDate));
			trip.status = ko.observable(data.status);
			trip.distanceTraveled = ko.observable(data.distanceTraveled);
			trip.start = ko.observable(data.start);
			trip.destination = ko.observable(data.destination);
			
			trip.startDateDisplay = ko.computed(function () {
				return util.formatDateTime(trip.startDate());
			});
			
			trip.endDateDisplay = ko.computed(function () {
				return util.formatDateTime(trip.endDate());
			});	
			
			trip.distanceTraveledDisplay = ko.computed(function() {
				if (trip.distanceTraveled()) {
					return util.round(util.convertKmToMi(trip.distanceTraveled()), 2) + " mi";
				}
				return "";				
			});	
			
			trip.destination = ko.computed(function() {
				var destination = trip.destination();
				if (destination) {
					return destination.name;
				}
				return "";
			});
						
			trip.rowCss = ko.computed(function () {
				switch (trip.status()) {
					case util.TRIP_STATUS_NEW:
						return "danger";
					case util.TRIP_STATUS_STARTED:
						return "warning"
					case util.TRIP_STATUS_FINISHED:
						return "info";
					case util.TRIP_STATUS_PROCESSED:
						return "";
					default:
						return "danger";						
				};
			});   
			
			trip.canCalculate = ko.computed(function () {
				return trip.status() !== util.TRIP_STATUS_PROCESSED;
			});
			
			trip.update = function (data) {
				trip.id(data.id);
				trip.name(data.name);
				trip.startDate(moment(data.startDate));
				trip.endDate(moment(data.endDate));
				trip.status(data.status);
				trip.distanceTraveled(data.distanceTraveled);
				trip.start(data.start);
				trip.destination(data.destination);
			};
			
			trip.viewTrip = function () {
				navigation.navigateToViewTrip(trip.id());
			};
			
			trip.processTrip = function () {
				proxy.trip.process(trip.id()).then(function (processedTrip) {
					trip.update(processedTrip);
				});
			};
			
			return trip;
		};	
		
		self.gridPager = new pager({fetchData: function (startAt, maxResults) {
			return self.fetchTrips(startAt, maxResults);
		}});
		
		self.columns = ko.observableArray([]);
		
		self.columns.push(new columnHeader({columnId: "NAME", columnName: "Name"}));
		self.columns.push(new columnHeader({columnId: "START_DATE", columnName: "Start Date"}));
		self.columns.push(new columnHeader({columnId: "END_DATE", columnName: "End Date"}));
		self.columns.push(new columnHeader({columnId: "STATUS", columnName: "Status"}));
		self.columns.push(new columnHeader({columnId: "DISTANCE_TRAVELED", columnName: "Distance Traveled"}));
		self.columns.push(new columnHeader({columnId: "DESTINATION", columnName: "Destination", enableSort: false}));
	
		/** Fetch the trips from the server */
		self.fetchTrips = function(startAt, maxResults) {
			return proxy.trip.getAllForCarPaged(self.carId, startAt, maxResults, self.sort).then(function (data) {
				var trips = $.map(data.data, function (elm, ind) {
					return new self.TripModel(elm);
				});
				self.trips(trips);	
				return data;
			},
			function (error) {
				alert(error);
			});
		};	
		
		
		self.load = function() {
			self.gridPager.fetchData();
		};	
		
		self.refresh = function () {
			self.load();
		};		
		
		system.events.on("clearSort", function (event, data) {
			self.sort = null;
			self.refresh();
		});		

		system.events.on("sort", function (event, data) {
			self.sort = data;
			self.refresh();
		});
		
		system.events.trigger("sort", {propertyId: "START_DATE", ascending: false});
		
	};
	
	return vm;
	
});