"use strict";

define("Components/Trip/TripChart/TripThrottleChart", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 
			"Components/Trip/TripChart/TripChartMixin"],
	function (moment, system, util, proxy, navigation, tripChartMixin) {
	
	return Vue.component("app-trip-throttle-chart", {
		mixins: [tripChartMixin],
		data: function() {
			return {
				name: "Trip Throttle Position"
			}
		},		
		computed: {
			chartOptions: function () {
				var opts = {};	
				var units = "%";
				
				opts.title = {
					text: this.name
				};
				
				var data = $.map(this.readings, function (elm, ind) {
					var throttlePosition = Math.round(elm.throttlePosition);
					return {x: elm.readDate, y: throttlePosition };
				});
				
				opts.plotOptions = {
					series: {
						turboThreshold: 0
					}
				};
				
				opts.yAxis = {
					title: {
						text: "Throttle Position (" + units + ")"
					}
				};
				
				opts.xAxis = {
					type: "datetime",
					startOfWeek: 0
				};
				
				opts.tooltip = {
					valueSuffix: " " + units
				};
				
				opts.series = [{
					name: "Throttle Position",
					data: data
				}];
				
				return opts;
			}	
		}	
	});
	
});