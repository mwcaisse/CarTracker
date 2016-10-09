"use strict";

define("Views/Trip/Trip", 
	["Service/util", 
	 "Modules/Trip/TripDetails/TripDetails",
	 "Modules/Trip/TripMap/TripMap",
	 "Modules/Trip/TripSpeedChart/TripSpeedChart",
	 "Modules/Trip/TripEngineChart/TripEngineChart",
	 "AMD/koTemplateLoader!Views/Trip/Trip.html"], function (util, tripDetails, tripMap, tripSpeedChart, tripEngineChart) {
	
	var vm = function() {
		var self = this;

		var tripId = util.getURLParameter("tripId", 92);
		
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
		
		self.tripDetails.load();
		self.tripSpeedChart.load();
		self.tripEngineChart.load();
		
	};
	
	return vm;
	
});