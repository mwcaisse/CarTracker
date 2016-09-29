"use strict";

define("Modules/Trip/TripGridBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Trip/TripGrid.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("tripGrid", templateName);
	
});