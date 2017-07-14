"use strict";

define("Components/Trip/TripChart/TripEngineChart", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 
         "AMD/text!Components/Trip/TripChart/TripChart.html"],
	function (moment, system, util, proxy, navigation, template) {
	
	return Vue.component("app-trip-engine-chart", {
		data: function() {
			return {
				name: "Trip Engine Speed",
				readings: []
			}
		},	
		props: {
			tripId: {
				type: Number,
				required: true
			}
		},
		computed: {
			chartOptions: function () {
				var opts = {};	
				
				opts.title = {
					text: this.name
				};				
				
				var data = $.map(this.readings, function (elm, ind) {
					return {x: elm.readDate, y: elm.engineRPM };
				});
				
				opts.plotOptions = {
					series: {
						turboThreshold: 0
					}
				};
				
				opts.yAxis = {
					title: {
						text: "Engine Speed (RPM)"
					}
				};
				
				opts.xAxis = {
					type: "datetime",
					startOfWeek: 0
				};
				
				opts.tooltip = {
					valueSuffix: " RPM"
				};
				
				opts.series = [{
					name: "Engine Speed",
					data: data
				}];
				
				return opts;
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
	});
	
});