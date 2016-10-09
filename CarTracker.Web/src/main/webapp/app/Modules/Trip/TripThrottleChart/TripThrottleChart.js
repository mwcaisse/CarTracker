"use strict";

define("Modules/Trip/TripThrottleChart/TripThrottleChart", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", 	
         "Modules/Trip/TripThrottleChart/TripThrottleChartBinding"],
	function (moment, system, util, proxy) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			tripId: -1
		};
		
		var opts = $.extend({}, defaults, options);

		self.tripId = opts.tripId;
		
		self.readings = ko.observableArray([]);
		
		self.useImperial = ko.observable(true);		
		
		//listen for updates for the use imperial slider from event details
		system.events.on("trip:useImperialChanged", function (event, data) {
			self.useImperial(data.useImperial);
		});
		
		self.chartOptions = ko.computed(function () {
			var opts = {};	
			var units = "%";
			
			opts.title = {
				text: "Trip Throttle Position"
			};
			
			var data = $.map(self.readings(), function (elm, ind) {
				var throttlePosition = Math.round(elm.throttlePosition);
				return {x: elm.readDate, y: throttlePosition };
			});
			
			opts.plotOptions = {
				series: {
					turboThreshold: 0
				}
			};
			
			opts.yAxis = {
				title: {
					text: "Throttle Position (" + units + ")"
				}
			};
			
			opts.xAxis = {
				type: "datetime",
				startOfWeek: 0
			};
			
			opts.tooltip = {
				valueSuffix: " " + units
			};
			
			opts.series = [{
				name: "Throttle Position",
				data: data
			}];
			
			return opts;
		});
		
		self.useImperial = ko.observable(true);
	
		/** Fetch the readings from the server */
		self.fetchReadings = function() {
			proxy.reading.getAllForTrip(self.tripId).then(function (data) {
				self.readings(data);
			},
			function (error) {
				alert(error);
			});
		};
		
		self.load = function() {
			self.fetchReadings();
		};	
		
		self.refresh = function () {
			self.load();	
		};
	};
	
	return vm;
	
});