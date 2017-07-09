"use strict";

define("Views/Car/Car", 
	["Service/util",
	 "Service/navigation", 
	 "Components/Car/CarDetails/CarDetails",
	 "AMD/text!Views/Car/Car.html"], function (util, navigation, carDetails, template) {
	
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