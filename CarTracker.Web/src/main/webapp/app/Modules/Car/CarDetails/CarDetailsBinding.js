"use strict";

define("Modules/Car/CarDetails/CarDetailsBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Car/CarDetails/CarDetails.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("carDetails", templateName);
	
});