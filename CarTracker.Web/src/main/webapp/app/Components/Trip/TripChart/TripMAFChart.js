"use strict";

define("Components/Trip/TripChart/TripMAFChart", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 
			"Components/Trip/TripChart/TripChartMixin"],
	function (moment, system, util, proxy, navigation, tripChartMixin) {
	
	return Vue.component("app-trip-maf-chart", {
		mixins: [tripChartMixin],
		data: function() {
			return {
				name: "Mass Air Flow"
			}
		},		
		computed: {
			chartOptions: function () {
				var opts = {};	
				var units = "";
				
				opts.title = {
					text: this.name
				};
				
				var data = $.map(this.readings, function (elm, ind) {		
					return {x: elm.readDate, y: elm.massAirFlow };
				});
				
				opts.plotOptions = {
					series: {
						turboThreshold: 0
					}
				};
				
				opts.yAxis = {
					title: {
						text: "MAF " + units
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
					name: "MAF",
					data: data
				}];
				
				return opts;
			}	
		}	
	});
	
});