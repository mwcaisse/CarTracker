"use strict";

define("Modules/Trip/TripMap/TripMap", ["AMD/googlemaps!", "moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation",         
                                          "Modules/Trip/TripMap/TripMapBinding"],
		function (gmaps, moment, system, util, proxy, navigation ) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			tripId: -1
		};
		
		var opts = $.extend({}, defaults, options);

		self.tripId = opts.tripId;
		
		self.load = function() {
			
		};	
		
		self.refresh = function () {
			self.load();
		};		
	};
	
	return vm;
	
});