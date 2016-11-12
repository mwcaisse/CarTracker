"use strict";

define("Modules/Auth/Registration/RegistrationBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Auth/Registration/Registration.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("registration", templateName);
	
});