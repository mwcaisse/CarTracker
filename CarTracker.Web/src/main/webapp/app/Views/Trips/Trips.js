"use strict";

define("Views/Trips/Trips", 
	["Service/navigation",
	 "Modules/Trip/TripGrid/TripGrid",
	 "AMD/koTemplateLoader!Views/Trips/Trips.html"], function (navigation, tripGrid) {
	
	var vm = function() {
		var self = this;

		self.tripGrid = new tripGrid({
			carId: 7
		});
		
		self.tripGrid.load();
		
		navigation.setActiveNavigation("Trip");
		
	};
	
	return vm;
	
});