"use strict";

define("Modules/Trip/TripEngineChart/TripEngineChart", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", 	
         "Modules/Trip/TripEngineChart/TripEngineChartBinding"],
	function (moment, system, util, proxy) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			tripId: -1
		};
		
		var opts = $.extend({}, defaults, options);

		self.tripId = opts.tripId;
		
		self.readings = ko.observableArray([]);
		
		self.chartOptions = ko.computed(function () {
			var opts = {};	
			
			opts.title = {
				text: "Trip Engine Speed"
			};
			
			
			var data = $.map(self.readings(), function (elm, ind) {
				return {x: elm.readDate, y: elm.engineRPM };
			});
			
			opts.plotOptions = {
				series: {
					turboThreshold: 0
				}
			};
				
			
			opts.yAxis = {
				title: {
					text: "Engine Speed (RPM)"
				}
			};
			
			opts.xAxis = {
				type: "datetime",
				startOfWeek: 0
			};
			
			opts.tooltip = {
				valueSuffix: " RPM"
			};
			
			opts.series = [{
				name: "Engine Speed",
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