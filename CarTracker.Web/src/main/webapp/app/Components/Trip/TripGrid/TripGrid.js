"use strict";

define("Components/Trip/TripGrid/TripGrid", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 
         "AMD/text!Components/Trip/TripGrid/TripGrid.html"],
	function (moment, system, util, proxy, navigation, template) {
	
	return Vue.component("app-trip-grid", {
		data: function() {
			return {
				trips: []
			}
		},	
		props: {
			carId: {
				type: Number,
				required: true
			}
		},
		template: template,
		methods: {
			fetchTrips: function () {
				proxy.trip.getAllForCar(this.carId).then(function (data) {
					this.update(data);
				}.bind(this),
				function (error) {
					alert("error fetching car!");
				})
			},
			update: function (data) {
				this.trips = data.data;
			},	
			refresh: function () {
				this.fetchTrips();
			}				
		},
		created: function () {
			this.fetchTrips();
		}
	});
	
});