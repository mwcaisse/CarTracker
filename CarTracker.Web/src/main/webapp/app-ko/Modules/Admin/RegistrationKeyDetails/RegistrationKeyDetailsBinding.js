"use strict";

define("Modules/Admin/RegistrationKeyDetails/RegistrationKeyDetailsBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Admin/RegistrationKeyDetails/RegistrationKeyDetails.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("registrationKeyDetails", templateName);
	
});