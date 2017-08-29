"use strict";

define("Components/Trip/TripGrid/TripGrid", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 	
		 "Components/Common/Pager/PagedGridMixin",
		 "AMD/text!Components/Trip/TripGrid/TripGrid.html",
         "AMD/text!Components/Trip/TripGrid/TripRow.html",
         "Components/Common/ColumnHeader/ColumnHeader",
         "Components/Common/Pager/Pager"],
	function (moment, system, util, proxy, navigation, pagedGridMixin, template, tripRowTemplate) {
	
	var tripRow = {
		template: tripRowTemplate,	
		data: function () {
			return {
				id: -1,
				name: "",
				startDate: moment(),
				endDate: moment(),
				status: "",
				distanceTraveled: 0,
				start: "",
				destination: ""
			};
		},
		props: {
			trip: {
				type: Object,
				requred: true
			}
		},
		computed: {
			canProcess: function() {
				return this.status !== util.TRIP_STATUS_PROCESSED;
			},
			rowCss: function () {
				switch (this.status) {
					case util.TRIP_STATUS_NEW:
						return "table-danger";
					case util.TRIP_STATUS_STARTED:
						return "table-warning";				
					case util.TRIP_STATUS_FINISHED:
						return "table-info";			
					case util.TRIP_STATUS_PROCESSED:
						return "";			
					default:
						return "table-danger";				
				}
			},
			viewLink: function () {
				return navigation.viewTripLink(this.id);
			},
			destinationName: function () {
				if (null == this.destination || typeof this.destination == "undefined") {
					return "";
				}
				return this.destination.name;
			}
		},
		methods: {
			process: function () {
				proxy.trip.process(this.id).then(function (processedTrip) {
					this.update(processedTrip);
				}.bind(this));
			},
			update: function (data) {
				this.id = data.id;
				this.name = data.name;
				this.startDate = moment(data.startDate);
				this.endDate = moment(data.endDate);
				this.status = data.status;
				this.distanceTraveled = data.distanceTraveled;
				this.start = data.start;
				this.destination = data.destination;
			}
		},
		created: function() {
			this.update(this.trip);
		}
	};
	
	return Vue.component("app-trip-grid", {
		mixins: [pagedGridMixin],	
		components: {
			"app-trip-row": tripRow
		},	
		data: function() {
			return {
				trips: [],
				currentSort: { propertyId: "START_DATE", ascending: false}			
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
				proxy.trip.getAllForCarPaged(this.carId, this.startAt, this.take, this.currentSort).then(function (data) {					
					this.update(data);
				}.bind(this),
				function (error) {
					alert("error fetching trips!");
				})
			},
			processAll: function () {
				proxy.trip.processUnprocessed().then(function (processedTrip) {
					this.refresh();
				}.bind(this));
			},
			createTrip: function (data) {
				return new (function() {
					var trip = this;
					
					trip.id = data.id;
					trip.name = data.name;
				})();	
			},	
			update: function (data) {
				this.trips = data.data;	
				this.totalItems = data.total;
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