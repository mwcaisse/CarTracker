"use strict";

define("Modules/Common/Map/Map", ["AMD/googlemaps!", "moment", "Service/system", "Service/util",         
                                          "Modules/Common/Map/MapBinding"],
		function (gmaps, moment, system, util) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			mapWidth: 600,
			mapHeight: 600,
			mapCenter: {lat: 42.710291, lng: -71.442039},
			mapZoom: 12,
			onLoad: function () { }
		};
		
		var opts = $.extend({}, defaults, options);

		self.tripId = opts.tripId;
		
		self.map = {};
		
		self.onLoad = opts.onLoad;	
		
		self.initializeMapElement = function (element) {
			var elm = $(element)[0];
			self.map = new gmaps.Map(elm, {
				zoom: opts.mapZoom,
				center: opts.mapCenter
			});
			
			self.onLoad();
		};
		
		self.style = ko.computed(function () {
			return {
				width: opts.mapWidth + "px",
				height: opts.mapHeight + "px"
			};
		});
		
		
		
		
	};
	
	return vm;
	
});