"use strict";

define("Modules/Common/Map/MapBinding", 
		["Service/koBindingHandlers", "AMD/koTemplateLoader!Modules/Common/Map/Map.html"], 
		
	function (handlers, templateName) {	
	
	handlers.createBindingHandler("map", templateName);
	
	ko.bindingHandlers.mapModule = {
			init: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
				var value = ko.unwrap(valueAccessor());
				
				value.initializeMapElement(element);
			}
		};
	
});