"use strict";

define("Modules/Trip/TripMap/TripMap", ["AMD/googlemaps!", "moment", 
                                        "Service/system", 
                                        "Service/util", 
                                        "Service/applicationProxy", 
                                        "Service/navigation",  
                                        "Modules/Common/Map/Map",
                                        "Modules/Trip/TripMap/TripMapBinding"],
		function (gmaps, moment, system, util, proxy, navigation, map ) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			tripId: -1
		};
		
		var opts = $.extend({}, defaults, options);

		self.tripId = opts.tripId;
	
		self.map = new map({
			onLoad: function () {
				self.load();
			}
		});
		
		self.routePath = null;
		
		self.load = function() {
			proxy.reading.getAllForTrip(self.tripId).then(function (data) {
				//check if we have an existing route path, if so, remove it from the map
				if (self.routePath) {
					self.routePath.setMap(null);
				}
				
				var coords = $.map(data, function (elm, ind) {
					if (elm.longitude === 0 && elm.latitude === 0) {
						return null; //ignore any coordinates that have 0 as latitude and longitude (data issue)
					}
					return {
						lat: elm.latitude,
						lng: elm.longitude
					};
				});	
				
				var mapBounds = new gmaps.LatLngBounds();
				$.each(coords, function(ind, elm) {
					mapBounds.extend(elm);
				});
				
				self.routePath = new gmaps.Polyline({
					path: coords,
					geodesic: true,
					strokeColor: "#FF0000",
					strokeOpacity: 1.0,
					strokeWeight: 2.0
				});
				
				self.routePath.setMap(self.map.map);
				
				self.map.map.fitBounds(mapBounds);
				
				if (coords.length > 1) {	
					var endMarker = new gmaps.Marker( {
						position: coords[coords.length - 1],
						map: self.map.map,
						title: "End"			
					});		
				}
				
			});			                                                                
		};	
		
		self.refresh = function () {
			self.load();
		};		
	};
	
	return vm;
	
});