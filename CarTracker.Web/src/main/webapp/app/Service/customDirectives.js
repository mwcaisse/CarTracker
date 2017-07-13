"use strict";

define("Service/customDirectives", 
		["moment", "Service/util"], function (moment, util) {
	
	Vue.directive("tooltip", {
		bind: function (el, binding) {
			$(el).tooltip(binding.value);
		}
	});
	
	Vue.filter("formatDate", function (value, formatString) {
		if (typeof value === "undefined" || typeof value.format !== "function") {
			return "";
		}		
		return util.formatDateTime(value, formatString);
	});
	
	Vue.filter("formatDuration", function (value, formatString) {
		if (typeof value === "undefined" || typeof value.format !== "function") {
			return "";
		}
		return util.formatDuration(value, formatString);
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