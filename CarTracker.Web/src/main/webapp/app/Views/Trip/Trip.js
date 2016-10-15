"use strict";

define("Views/Trip/Trip", 
	["Service/util", 
	 "Service/navigation", 
	 "Modules/Common/PageAlert/PageAlert",
	 "Modules/Trip/TripDetails/TripDetails",
	 "Modules/Trip/TripMap/TripMap",
	 "Modules/Trip/TripSpeedChart/TripSpeedChart",
	 "Modules/Trip/TripEngineChart/TripEngineChart",
	 "Modules/Trip/TripTemperatureChart/TripTemperatureChart",
	 "Modules/Trip/TripThrottleChart/TripThrottleChart",
	 "Modules/Trip/TripMAFChart/TripMAFChart",
	 "AMD/koTemplateLoader!Views/Trip/Trip.html"], function (util, navigation, pageAlert, tripDetails, tripMap, tripSpeedChart, tripEngineChart, 
			 tripTemperatureChart, tripThrottleChart, tripMAFChart) {
	
	var vm = function() {
		var self = this;

		var tripId = util.getURLParameter("tripId", 92);
		
		self.pageAlert = new pageAlert();
		
		self.tripDetails = new tripDetails({
			tripId: tripId
		});
		
		self.tripMap = new tripMap({
			tripId: tripId
		});
		
		self.tripSpeedChart = new tripSpeedChart({
			tripId: tripId
		});
		
		self.tripEngineChart = new tripEngineChart({
			tripId: tripId
		});
		
		self.tripTemperatureChart = new tripTemperatureChart({
			tripId: tripId
		});
		
		self.tripThrottleChart = new tripThrottleChart({
			tripId: tripId
		});
		
		self.tripMAFChart = new tripMAFChart({
			tripId: tripId
		});
		
		self.tripDetails.load();
		self.tripSpeedChart.load();
		self.tripEngineChart.load();
		self.tripTemperatureChart.load();
		self.tripThrottleChart.load();
		self.tripMAFChart.load();
		
		navigation.setActiveNavigation("Trip");
		
	};
	
	return vm;
	
});