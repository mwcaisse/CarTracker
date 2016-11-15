"use strict";

define("Modules/Admin/RegistrationKeyGrid/RegistrationKeyGridBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Admin/RegistrationKeyGrid/RegistrationKeyGridBinding.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("registrationKeyGrid", templateName);
	
});