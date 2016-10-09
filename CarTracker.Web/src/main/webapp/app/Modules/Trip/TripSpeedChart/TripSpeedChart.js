"use strict";

define("Modules/Trip/TripSpeedChart/TripSpeedChart", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", 	
         "Modules/Trip/TripSpeedChart/TripSpeedChartBinding"],
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
			var useImperial = self.useImperial();
			var units = useImperial ? "mph" : "km/h";
			
			opts.title = {
				text: "Trip Speed"
			};
			
			
			var data = $.map(self.readings(), function (elm, ind) {
				var speed = elm.speed;
				if (useImperial) {
					speed = util.convertKmToMi(speed);
				}	
				speed = Math.round(speed);
				return {x: elm.readDate, y: speed };
			});
			
			opts.yAxis = {
				title: {
					text: "Speed " + units
				}
			};
			
			opts.xAxis = {
				type: "datetime"
			};
			
			opts.tooltip = {
				valueSuffix: " " + units
			};
			
			opts.series = [{
				name: "Speed",
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