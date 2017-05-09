"use strict";

define("Modules/User/AuthTokenGrid/AuthTokenGridBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/User/AuthTokenGrid/AuthTokenGrid.html"], 
		
	function (handlers, templateName) {	
	
		handlers.createBindingHandler("authTokenGrid", templateName);
		
	});