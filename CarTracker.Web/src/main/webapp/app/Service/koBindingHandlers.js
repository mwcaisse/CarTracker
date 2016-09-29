"use strict";

define("Service/koBindingHandlers", [], function () {
	
	var handlers = new (function() {
		var self = this;

		self.customTemplateInit = function (element, valueAccessor, allBindings, viewModel, bindingContext) {			
			ko.virtualElements.emptyNode(element);
		};
		
		self.customTemplateUpdate = function (templateName, element, valueAccessor, allBindings, viewModel, bindingContext) {	
			var accessor = valueAccessor();
			var template = ko.renderTemplate(templateName, accessor, {}, element);
		};
		
		/** Creates a binding handler alias for the given template
		 * @param bindingName The name of the bindng handler
		 * @param templateName The name of the template
		 */
		self.createBindingHandler = function (bindingName, templateName) {
			ko.bindingHandlers[bindingName] = {
				init: self.customTemplateInit,
				update: function (element, valueAccessor, allBindings, viewModel, bindingContext) {	
					self.customTemplateUpdate(templateName, element, valueAccessor, allBindings, viewModel, bindingContext);
				}
			};	
		}
		
	})();
	
	return handlers;
	
});