"use strict";

define("Modules/Trip/TripGrid/TripGrid", ["moment", "Service/util", "Service/applicationProxy", "Service/navigation",
                                          "Modules/Common/Pager/Pager",
                                          "Modules/Common/ColumnHeader/ColumnHeader",
                                          "Modules/Trip/TripGrid/TripGridBinding"],
		function (moment, util, proxy, navigation, pager, columnHeader) {
	
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
			
			trip.viewTrip = function () {
				navigation.navigateToViewTrip(trip.id);
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
	
		/** Fetch the trips from the server */
		self.fetchTrips = function(startAt, maxResults) {
			return proxy.trip.getAllForCarPaged(self.carId, startAt, maxResults).then(function (data) {
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
		}
	};
	
	return vm;
	
});