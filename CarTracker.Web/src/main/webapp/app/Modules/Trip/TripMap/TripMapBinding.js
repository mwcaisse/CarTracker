"use strict";

define("Modules/Trip/TripMap/TripMapBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Trip/TripMap/TripMap.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("tripMap", templateName);
	
});