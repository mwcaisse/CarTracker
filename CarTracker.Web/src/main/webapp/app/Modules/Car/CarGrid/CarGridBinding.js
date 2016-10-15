"use strict";

define("Modules/Car/CarGrid/CarGridBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Car/CarGrid/CarGrid.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("carGrid", templateName);
	
});