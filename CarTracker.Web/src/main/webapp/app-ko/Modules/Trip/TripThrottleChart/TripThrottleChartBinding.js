"use strict";

define("Modules/Trip/TripThrottleChart/TripThrottleChartBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Trip/TripThrottleChart/TripThrottleChart.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("tripThrottleChart", templateName);
	
});