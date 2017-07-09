"use strict";

define("Service/customDirectives", ["Service/util"], function (util) {
	
	Vue.directive("tooltip", {
		bind: function (el, binding) {
			$(el).tooltip(binding.value);
		}
	});
	
	Vue.filter("formatDate", function (value) {
		if (value) {
			return util.formatDateTime(moment(value));
		}
		return "";
	});
	
	Vue.filter("round", function (value, places) {
		if (typeof value == "undefined") {
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