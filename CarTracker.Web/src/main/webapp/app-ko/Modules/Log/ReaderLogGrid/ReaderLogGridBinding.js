"use strict";

define("Modules/Log/ReaderLogGrid/ReaderLogGridBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Log/ReaderLogGrid/ReaderLogGrid.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("readerLogGrid", templateName);
	
});