"use strict";

define("Components/Trip/TripChart/TripSpeedChart", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 
		 "Components/Trip/TripChart/TripChartMixin"],
	function (moment, system, util, proxy, navigation, tripChartMixin) {
	
	return Vue.component("app-trip-speed-chart", {
		mixins: [tripChartMixin],
		data: function() {
			return {
				name: "Speed"
			}
		},		
		computed: {
			chartOptions: function () {
				var opts = {};
				var useImperial = true;
				var units = useImperial ? "mph" : "km/h";
				
				opts.title = {
					text: this.name
				};				
				
				var data = $.map(this.readings, function (elm, ind) {
					var speed = elm.speed;
					if (useImperial) {
						speed = util.convertKmToMi(speed);
					}	
					speed = Math.round(speed);
					return {x: elm.readDate, y: speed };
				});
				
				opts.plotOptions = {
					series: {
						turboThreshold: 0
					}
				};
				
				opts.yAxis = {
					title: {
						text: "Speed " + units
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
					name: "Speed",
					data: data
				}];
				
				return opts;
			}	
		}
	});
	
});