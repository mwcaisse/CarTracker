"use strict";

define("Views/Cars/Cars", 
	["Modules/Car/CarGrid/CarGrid",
	 "AMD/koTemplateLoader!Views/Cars/Cars.html"], function (carGrid) {
	
	var vm = function() {
		var self = this;

		self.carGrid = new carGrid({	
		});
		
		self.carGrid.load();
		
	};
	
	return vm;
	
});