"use strict";

define("Views/Trip/Trip", 
	["Service/util", 
	 "Service/navigation", 
	 "Service/applicationProxy",
	 "AMD/text!Views/Trip/Trip.html",	 
	 "Components/Trip/TripDetails/TripDetails",
	 "Components/Trip/TripMap/TripMap",
	 "Components/Trip/TripChart/TripSpeedChart",
	 "Components/Trip/TripChart/TripEngineChart",
	 "Components/Trip/TripChart/TripThrottleChart",
	 "Components/Trip/TripChart/TripMAFChart",
	 "Components/Trip/TripChart/TripTemperatureChart",
	 "Components/Trip/TripPossiblePlaces/TripPossiblePlaces"], 
	 function (util, navigation, proxy, template) {
	
	var vm = function(elementId) {
		var tripId = parseInt(util.getURLParameter("tripId", 92), 10);	
		
		return new Vue({
			el: elementId,
			template: template,
			data: {
				tripId: tripId
			}
		});
		
	};
	
	navigation.setActiveNavigation("Trip");
	
	return vm;
	
});