"use strict";

define("Views/Trip/Trip", 
	["Modules/Trip/TripDetails/TripDetails",
	 "AMD/koTemplateLoader!Views/Trip/Trip.html"], function (tripDetails) {
	
	var vm = function() {
		var self = this;

		self.tripDetails = new tripDetails({
			tripId: 94
		});
		
		self.tripDetails.load();
		
	};
	
	return vm;
	
});