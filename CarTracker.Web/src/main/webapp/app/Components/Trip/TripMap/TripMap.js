"use strict";

define("Components/Trip/TripMap/TripMap", 
		["AMD/googlemaps!", "moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 
         "AMD/text!Components/Trip/TripMap/TripMap.html",
         "Components/Common/Map/Map"],
	function (gmaps, moment, system, util, proxy, navigation, template) {
	
	return Vue.component("app-trip-map", {
		data: function() {
			return {	
				map: {},
				routePathes: []
			}
		},	
		props: {
			tripId: {
				type: Number,
				required: true
			}
		},
		computed: {		
				
		},
		template: template,
		methods: {
			fetch: function () {
				proxy.reading.getAllForTrip(this.tripId).then(function (data) {
					
					//if we have an existing route path remove it from the map
					if (this.routePathes.length > 0) {
						$.each(this.routePathes, function (elm, ind) {
							elm.setMap(null);
						});
					}
					
					var coords = $.map(data, function (elm, ind) {
						if (elm.longitude === 0 && elm.latitude === 0) {
							return null; // ignore any coordinates that have 0 as lat an long (data issue)
						}
						return {
							speed: elm.speed,						
							coords: {
								lat: elm.latitude,
								lng: elm.longitude
							}
						};
					});
					
					var mapBounds = new gmaps.LatLngBounds();
					$.each(coords, function (ind, elm) {
						mapBounds.extend(elm.coords);
					});
					
					for (var i = 0; i < coords.length - 1; i ++) {
						var color = "";
						
						if (coords[i].speed < 60) {
							color = "#FF0000";
						}
						else if (coords[i].speed < 100) {
							color = "#E59400"
						}
						else {
							color = "#00FF00";
						}						
						var subPath = new gmaps.Polyline({
							path: [coords[i].coords, coords[i+1].coords],
							geodesic: true,
							strokeColor: color,
							strokeOpacity: 1.0,
							strokeWeight: 2.0
						});
						
						subPath.setMap(this.map);
						
						this.routePathes.push(subPath);
					};
					
					this.map.fitBounds(mapBounds);
					
					if (coords.length > 1) {				
						var endMarker = new gmaps.Marker({
							position: coords[coords.length - 1].coords,
							map: this.map,
							title: "End"
						});
					}
				}.bind(this),
				function (error) {
					alert("error fetching trip!");
				})
			},				
			refresh: function () {
				this.fetch();
			},
			mapInitialized: function (map) {
				this.map = map;
				this.fetch();
			}
				
		}	
	});
	
});