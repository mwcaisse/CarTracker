"use strict";

define("Components/Trip/TripDetails/TripDetails", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 
         "AMD/text!Components/Trip/TripDetails/TripDetails.html"],
	function (moment, system, util, proxy, navigation, template) {
	
	return Vue.component("app-trip-details", {
		data: function() {
			return {
				name: "",
				carId: -1,
				startDate: moment(),
				endDate: moment(),
				averageSpeed: 0,
				maximumSpeed: 0,
				averageEngineRPM: 0,
				maxEngineRPM: 0,
				distanceTraveled: 0,
				idleTime: 0,
				status: ""
			}
		},	
		props: {
			tripId: {
				type: Number,
				required: true
			}
		},
		computed: {
			tripLength: function () {
				var msDiff = this.endDate.diff(this.startDate);
				return moment.duration(msDiff, "ms");
			}
				
		},
		template: template,
		methods: {
			fetch: function () {
				proxy.trip.get(this.tripId).then(function (data) {
					this.update(data);
				}.bind(this),
				function (error) {
					alert("error fetching trip!");
				})
			},
			update: function (trip) {
				this.name = trip.name;
				this.carId = trip.carId;
				this.startDate = moment(trip.startDate);
				this.endDate = moment(trip.endDate);
				this.averageSpeed = trip.averageSpeed;
				this.maximumSpeed = trip.maximumSpeed;
				this.averageEngineRPM = trip.averageEngineRPM;
				this.maxEngineRPM = trip.maxEngineRPM;
				this.distanceTraveled = trip.distanceTraveled;
				this.idleTime = moment.duration(trip.idleTime);
				this.status = trip.status;
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