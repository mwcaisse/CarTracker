"use strict";

define("Views/Trips/Trips", 
	["Modules/Trip/TripGrid/TripGrid",
	 "AMD/koTemplateLoader!Views/Trips/Trips.html"], function (tripGrid) {
	
	var vm = function() {
		var self = this;

		self.tripGrid = new tripGrid({
			carId: 7
		});
		
		self.tripGrid.load();
		
	};
	
	return vm;
	
});