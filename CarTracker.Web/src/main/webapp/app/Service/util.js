"use strict";

define("Service/util", [], function (text) {
	
	var util = new (function() {
		var self = this;

		self.KM_IN_MI = 0.621371;
		
		self.convertKmToMi = function (km) {
			return km * self.KM_IN_MI;
		};		
		
		self.round = function (num, places) {
			if (!places) {
				places = 2;
			}
			return parseFloat(num).toFixed(places);
		};
	
		
	})();
	
	return util;
	
});