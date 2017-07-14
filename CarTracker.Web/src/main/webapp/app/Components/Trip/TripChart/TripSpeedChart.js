"use strict";

define("Components/Trip/TripChart/TripSpeedChart", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 
         "AMD/text!Components/Trip/TripChart/TripChart.html"],
	function (moment, system, util, proxy, navigation, template) {
	
	return Vue.component("app-trip-speed-chart", {
		data: function() {
			return {
				name: "Trip Speed",
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