"use strict";

define("Modules/Trip/TripDetails/TripDetailsBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Trip/TripDetails/TripDetails.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("tripDetails", templateName);
	
});