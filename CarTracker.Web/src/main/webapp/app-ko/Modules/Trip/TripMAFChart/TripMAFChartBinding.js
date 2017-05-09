"use strict";

define("Modules/Trip/TripMAFChart/TripMAFChartBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Trip/TripMAFChart/TripMAFChart.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("tripMAFChart", templateName);
	
});