"use strict";

define("Modules/Common/Pager/PagerBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Common/Pager/Pager.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("pager", templateName);
	
});