"use strict";

define("Views/Cars/Cars", 
	["Service/navigation",
	 "Modules/Car/CarGrid/CarGrid",
	 "AMD/koTemplateLoader!Views/Cars/Cars.html"], function (navigation, carGrid) {
	
	var vm = function() {
		var self = this;

		self.carGrid = new carGrid({	
		});
		
		self.carGrid.load();
		
		navigation.setActiveNavigation("Car");
		
	};
	
	return vm;
	
});