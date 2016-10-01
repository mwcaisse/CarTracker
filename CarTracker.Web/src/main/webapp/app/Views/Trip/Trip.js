"use strict";

define("Views/Trip/Trip", 
	["Service/util", "Modules/Trip/TripDetails/TripDetails",
	 "AMD/koTemplateLoader!Views/Trip/Trip.html"], function (util, tripDetails) {
	
	var vm = function() {
		var self = this;

		var tripId = util.getURLParameter("tripId", 92);
		
		self.tripDetails = new tripDetails({
			tripId: tripId
		});
		
		self.tripDetails.load();
		
	};
	
	return vm;
	
});