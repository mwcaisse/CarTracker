"use strict";

define("Views/Trip/Trip", 
	["Service/util", 
	 "Service/navigation", 
	 "Service/applicationProxy",
	 "Modules/Common/PageAlert/PageAlert",
	 "Modules/Trip/TripDetails/TripDetails",
	 "Modules/Trip/TripMap/TripMap",
	 "Modules/Trip/TripSpeedChart/TripSpeedChart",
	 "Modules/Trip/TripEngineChart/TripEngineChart",
	 "Modules/Trip/TripTemperatureChart/TripTemperatureChart",
	 "Modules/Trip/TripThrottleChart/TripThrottleChart",
	 "Modules/Trip/TripMAFChart/TripMAFChart",
	 "AMD/koTemplateLoader!Views/Trip/Trip.html"], function (util, navigation, proxy, pageAlert, tripDetails, tripMap, tripSpeedChart, tripEngineChart, 
			 tripTemperatureChart, tripThrottleChart, tripMAFChart) {
	
	var vm = function() {
		var self = this;

		self.tripId = util.getURLParameter("tripId", 92);
		
		self.pageAlert = new pageAlert();
		
		self.tripDetails = new tripDetails({
			tripId: self.tripId
		});
		
		self.tripMap = new tripMap({
			tripId: self.tripId
		});
		
		self.tripSpeedChart = new tripSpeedChart({
			tripId: self.tripId
		});
		
		self.tripEngineChart = new tripEngineChart({
			tripId: self.tripId
		});
		
		self.tripTemperatureChart = new tripTemperatureChart({
			tripId: self.tripId
		});
		
		self.tripThrottleChart = new tripThrottleChart({
			tripId: self.tripId
		});
		
		self.tripMAFChart = new tripMAFChart({
			tripId: self.tripId
		});
		
		self.previousTripId = ko.observable(null);
		self.nextTripId = ko.observable(null);
		
		self.hasPreviousTrip = ko.computed(function () {
			return null !== self.previousTripId();
		});
		
		self.hasNextTrip = ko.computed(function () {
			return null !== self.nextTripId();
		});
		
		self.clickPreviousTrip = function () {
			navigation.navigateToViewTrip(self.previousTripId());
		};
		
		self.clickNextTrip = function () {
			navigation.navigateToViewTrip(self.nextTripId());
		};
		
		self.loadPreviousNextTrip = function () {
			proxy.trip.getPreviousNext(self.tripId).then(function (prevNextTrip) {
				self.previousTripId(prevNextTrip.previousTripId);
				self.nextTripId(prevNextTrip.nextTripId);
			});
		};
		
		self.load = function () {		
			self.tripDetails.load();
			self.tripSpeedChart.load();
			self.tripEngineChart.load();
			self.tripTemperatureChart.load();
			self.tripThrottleChart.load();
			self.tripMAFChart.load();
			
			self.loadPreviousNextTrip();
		};
		
		self.load();
		
		navigation.setActiveNavigation("Trip");
		
	};
	
	return vm;
	
});