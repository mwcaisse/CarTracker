"use strict";

define("Components/Trip/TripChart/TripEngineChart", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 
			"Components/Trip/TripChart/TripChartMixin"],
	function (moment, system, util, proxy, navigation, tripChartMixin) {
	
	return Vue.component("app-trip-engine-chart", {
		mixins: [tripChartMixin],
		data: function() {
			return {
				name: "Engine Speed"
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
		}
	});
	
});