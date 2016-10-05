"use strict";

define("Modules/Common/ColumnHeader/ColumnHeaderBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Common/ColumnHeader/ColumnHeader.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("columnHeader", templateName);
	
});