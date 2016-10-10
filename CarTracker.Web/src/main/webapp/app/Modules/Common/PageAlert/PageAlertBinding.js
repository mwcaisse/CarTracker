"use strict";

define("Modules/Common/PageAlert/PageAlertBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Common/PageAlert/PageAlert.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("pageAlert", templateName);		
	
});