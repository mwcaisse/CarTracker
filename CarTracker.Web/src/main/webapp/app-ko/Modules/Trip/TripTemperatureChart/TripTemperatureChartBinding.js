"use strict";

define("Modules/Trip/TripTemperatureChart/TripTemperatureChartBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Trip/TripTemperatureChart/TripTemperatureChart.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("tripTemperatureChart", templateName);
	
});