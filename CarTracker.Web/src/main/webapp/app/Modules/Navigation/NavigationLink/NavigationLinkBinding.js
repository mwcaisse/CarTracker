"use strict";

define("Modules/Navigation/NavigationLink/NavigationLinkBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Navigation/NavigationLink/NavigationLink.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("navigationLink", templateName);
	
});