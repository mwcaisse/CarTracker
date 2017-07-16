"use strict";

define("Service/customDirectives", 
		["moment", "Service/util"], function (moment, util) {
	
	Vue.directive("tooltip", {
		bind: function (el, binding) {
			$(el).tooltip(binding.value);
		}
	});
	
	/** Binding for High Charts
	 * 
	 * 	Uses the value as the options to initialize high charts	
	 */
	Vue.directive("highcharts", function (el, binding) {
		if (typeof binding.oldValue === "undefined" || binding.oldValue !== binding.value) {
			var $el = $(el);
			$el.highcharts(binding.value);
		}
	});
	
	Vue.filter("formatDate", function (value, formatString) {
		if (typeof value === "undefined") {
			return "";
		}		
		if (typeof value.format !== "function") {
			value = moment(value);
		}
		return util.formatDateTime(value, formatString);
	});
	
	Vue.filter("formatDuration", function (value, formatString) {
		if (typeof value === "undefined" || typeof value.format !== "function") {
			return "";
		}
		return util.formatDuration(value, formatString);
	});
	
	Vue.filter("formatBoolean", function (value, nullIsBlank) {
		if (value === true) {
			return "Yes";
		}
		else if (value === false) {
			return "No";
		}
		else if (nullIsBlank) {
			return "";
		}
		else {
			return "No";
		}
	});
	
	Vue.filter("round", function (value, places) {
		if (typeof value === "undefined") {
			return "";
		}
		
		if (typeof places === "undefined") {
			places = 2;
		}
		return util.round(value, places);
	
	});
	
	Vue.filter("kmToMi", function (value) {
		if (typeof value == "undefined") {
			return "";
		}
		return util.convertKmToMi(value);
	});	
	
	return {};	
});