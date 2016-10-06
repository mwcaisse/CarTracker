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
		
		$(document).ready(function () {
			
			self.map = new gmaps.Map(document.getElementById("tripMap"), {
				zoom: 12,
				center: {lat: 42.710291, lng: -71.442039}		        
			});
			
			proxy.reading.getAllForTrip(self.tripId).then(function (data) {
				var coords = $.map(data, function (elm, ind) {
					if (elm.longitude === 0 && elm.latitude === 0) {
						return null; //ignore any coordinates that have 0 as latitude and longitude (data issue)
					}
					return {
						lat: elm.latitude,
						lng: elm.longitude
					};
				});	
				
				var routePath = new gmaps.Polyline({
					path: coords,
					geodesic: true,
					strokeColor: "#FF0000",
					strokeOpacity: 1.0,
					strokeWeight: 2.0
				});
				
				routePath.setMap(self.map);
				
			});
		});
		
		self.load = function() {
			/*
			proxy.reading.getAllForTrip(self.tripId).then(function (data) {
				var coords = $.map(data, function (elm, ind) {
					if (elm.longitude === 0 && elm.latitude === 0) {
						return; //ignore any coordinates that have 0 as latitude and longitude (data issue)
					}
					return {
						lat: elm.latitude,
						lng: elm.longitude
					};
				});	
				
				var routePath = new gmaps.Polyline({
					path: coords,
					geodesic: true,
					strokeColor: "#FF0000",
					strokeOpacity: 1.0,
					strokeWeight: 2.0
				});
				
				routePath.setMap(self.Map);
				
			});*/
			                                                                
		};	
		
		self.refresh = function () {
			self.load();
		};		
	};
	
	return vm;
	
});