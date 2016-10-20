"use strict";

define("Views/Car/Car", 
	["Service/util", 
	 "Service/navigation", 
	 "Modules/Common/PageAlert/PageAlert",
	 "Modules/Car/CarDetails/CarDetails",
	 "Modules/Trip/TripGrid/TripGrid",
	 "AMD/koTemplateLoader!Views/Car/Car.html"], function (util, navigation, pageAlert, carDetails, tripGrid) {
	
	var vm = function() {
		var self = this;

		var carId = util.getURLParameter("carId", 92);
		
		self.pageAlert = new pageAlert();
		
		self.carDetails = new carDetails({
			carId: carId
		});
		
		self.tripGrid = new tripGrid( {
			carId: carId
		});
			
		self.carDetails.load();
		self.tripGrid.load();
		
		navigation.setActiveNavigation("Car");
		
	};
	
	return vm;
	
});