"use strict";

define("Modules/Trip/TripGrid/TripGridBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Trip/TripGrid/TripGrid.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("tripGrid", templateName);
	
});