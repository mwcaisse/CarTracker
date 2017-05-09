"use strict";

define("Modules/Trip/TripTemperatureChart/TripTemperatureChart", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", 	
         "Modules/Trip/TripTemperatureChart/TripTemperatureChartBinding"],
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
		
		self.createDataElement = function (reading, fieldName, useImperial) {
			var temp = reading[fieldName];
			if (useImperial) {
				temp = util.convertCelsiusToFah(temp);
			}
			temp = Math.round(temp);
			return {x: reading.readDate, y: temp};
		};
		
		self.chartOptions = ko.computed(function () {
			var opts = {};
			var useImperial = self.useImperial();
			var units = util.DEGREE_SYMBOL + (useImperial ? "F" : "C");
			
			opts.title = {
				text: "Trip Temperatures"
			};
			
			var ambientAirTemperature = $.map(self.readings(), function (elm, ind) {
				return self.createDataElement(elm, "ambientAirTemperature", useImperial);
			});
			
			var engineCoolantTemperature = $.map(self.readings(), function (elm, ind) {
				return self.createDataElement(elm, "engineCoolantTemperature", useImperial);
			});
			
			var oilTemperature = $.map(self.readings(), function (elm, ind) {
				return self.createDataElement(elm, "oilTemperature", useImperial);
			});
			
			opts.plotOptions = {
				series: {
					turboThreshold: 0
				}
			};
			
			opts.yAxis = {
				title: {
					text: "Temperature " + units
				}
			};
			
			opts.xAxis = {
				type: "datetime",
				startOfWeek: 0
			};
			
			opts.tooltip = {
				valueSuffix: " " + units
			};
			
			opts.series = [
			{
				name: "Ambient Air Temperature",
				data: ambientAirTemperature
			},
			{
				name: "Engine Coolant Temperature",
				data: engineCoolantTemperature
			},
			{
				name: "Oil Temperature",
				data: oilTemperature
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