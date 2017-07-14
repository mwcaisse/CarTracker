"use strict";

define("Views/Trip/Trip", 
	["Service/util", 
	 "Service/navigation", 
	 "Service/applicationProxy",
	 "Modules/Common/PageAlert/PageAlert",
	 "Components/Trip/TripDetails/TripDetails",
	 "Components/Trip/TripMap/TripMap",
	 "AMD/text!Views/Trip/Trip.html"], 
	 function (util, navigation, proxy, pageAlert, tripDetails, tripMap, template) {
	
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