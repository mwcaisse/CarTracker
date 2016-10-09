"use strict";

define("Modules/Trip/TripEngineChart/TripEngineChartBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Trip/TripEngineChart/TripEngineChart.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("tripEngineChart", templateName);
	
});