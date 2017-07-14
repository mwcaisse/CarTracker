"use strict";

define("Components/Trip/TripChart/TripChartMixin", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 
         "AMD/text!Components/Trip/TripChart/TripChart.html"],
	function (moment, system, util, proxy, navigation, template) {
	
	return {
		data: function() {
			return {	
				name: "Trip Chart",
				readings: []
			}
		},	
		props: {
			tripId: {
				type: Number,
				required: true
			}
		},		
		template: template,
		methods: {
			fetch: function () {
				proxy.reading.getAllForTrip(this.tripId).then(function (data) {
					this.readings = data;
				}.bind(this),
				function (error) {
					alert("error fetching trip readings!");
				})
			},	
			refresh: function () {
				this.fetch();
			}				
		},
		created: function () {
			this.fetch();
		}
	};
	
});