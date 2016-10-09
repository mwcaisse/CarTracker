"use strict";

define("Service/koBindingHandlers", [], function () {
	
	var handlers = new (function() {
		var self = this;

		self.customTemplateInit = function (element, valueAccessor, allBindings, viewModel, bindingContext) {			
			ko.virtualElements.emptyNode(element);
		};
		
		self.customTemplateUpdate = function (templateName, element, valueAccessor, allBindings, viewModel, bindingContext) {	
			var accessor = valueAccessor();
			var template = ko.renderTemplate(templateName, accessor, {}, element, "replaceNode");
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
	
	
	ko.bindingHandlers.tooltip = {
		init: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
			
		},
		update: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
			var $el = $(element);
			var value = ko.unwrap(valueAccessor());
			var bindingOptions = allBindings.get("tooltipOptions") || {};
			var defaults = {
				title: value
			};
			var opts = $.extend({}, defaults, bindingOptions);
			
			$el.tooltip(opts);
		}
	};
	
	/** Binding for Bootstrap toggle library
	 * 	http://www.bootstraptoggle.com/
	 */
	ko.bindingHandlers.toggle = {
		init: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
			var $el = $(element);
			var value = valueAccessor();	
			var bindingOptions = allBindings.get("toggleOptions") || {};
			var defaults = {
				on: "On",
				off: "Off",
				onstyle: "primary",
				offstyle: "default"
			};
			var opts = $.extend({}, defaults, bindingOptions);
			
			$el.bootstrapToggle(opts);
			
			$el.change(function () {
				var checked = $el.prop('checked');
				value(checked);
			});
		},
		update: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
			var $el = $(element);
			var value = ko.unwrap(valueAccessor());	
			var checked = $el.prop('checked');
			
			if (value !== checked) {
				$el.bootstrapToggle(value ? "on" : "off");
			}
		}
	};
	
	/** Binding for Bootstrap toggle library
	 * 	http://www.bootstraptoggle.com/
	 */
	ko.bindingHandlers.highchart = {
		init: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
	
		},
		update: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
			var $el = $(element);
			var options = ko.unwrap(valueAccessor());		
			var defaults = {
		
			};
			var opts = $.extend({}, defaults, options);
			
			$el.highcharts(opts);
		}
	};
	
	
	
	return handlers;
	
});