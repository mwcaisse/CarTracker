"use strict";

define("Views/Car/Car", 
	["Service/util",
	 "Service/navigation", 
	 "Components/Car/CarDetails/CarDetails",
	 "Components/Car/CarSupportedCommands/CarSupportedCommands",
	 "Components/Trip/TripGrid/TripGrid",
	 "AMD/text!Views/Car/Car.html"], 
	 
	 function (util, navigation, carDetails, carSupportedCommands, tripGrid, template) {
	
		var vm = function (elementId) {
			var carId = parseInt(util.getURLParameter("carId", -1), 10);		
			
			return new Vue({			
				el: elementId,
				template: template,
				data: {
					carId: carId
				}
			});
		};
		
		navigation.setActiveNavigation("Car");
		
		return vm;
	
});