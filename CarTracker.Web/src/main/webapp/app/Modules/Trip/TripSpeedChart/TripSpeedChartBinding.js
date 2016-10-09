"use strict";

define("Modules/Trip/TripSpeedChart/TripSpeedChartBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Trip/TripSpeedChart/TripSpeedChart.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("tripSpeedChart", templateName);
	
});