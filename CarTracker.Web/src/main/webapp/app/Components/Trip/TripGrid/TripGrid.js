"use strict";

define("Components/Trip/TripGrid/TripGrid", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 	
         "AMD/text!Components/Trip/TripGrid/TripGrid.html",
         "Components/Common/ColumnHeader/ColumnHeader",
         "Components/Common/Pager/Pager"],
	function (moment, system, util, proxy, navigation, template) {
	
	return Vue.component("app-trip-grid", {
		data: function() {
			return {
				trips: [],
				currentSort: { propertyId: "START_DATE", ascending: false},
				currentPaging: {
					itemsPerPage: 15,
					currentPage: 1
				},
				totalItems: 1
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
				var take = this.currentPaging.itemsPerPage;
				var startAt = (this.currentPaging.currentPage - 1) * take;				
				proxy.trip.getAllForCarPaged(this.carId, startAt, take, this.currentSort).then(function (data) {					
					this.update(data);
				}.bind(this),
				function (error) {
					alert("error fetching car!");
				})
			},
			augmentTrip: function (trip) {
				trip.canProcess = trip.status !== util.TRIP_STATUS_PROCESSED;
				
				switch (trip.status) {
					case util.TRIP_STATUS_NEW:
						trip.rowCss = "table-danger";
						break;
					case util.TRIP_STATUS_STARTED:
						trip.rowCss = "table-warning";
							break;
					case util.TRIP_STATUS_FINISHED:
						trip.rowCss = "table-info";
						break;
					case util.TRIP_STATUS_PROCESSED:
						trip.rowCss = "";
						break;
					default:
						trip.rowCss = "table-danger";
						break;
				}
				
				return trip;
			},
			update: function (data) {
				this.trips = $.map(data.data, function (elm, ind) {
					return this.augmentTrip(elm);
				}.bind(this));
				
				this.totalItems = data.total;
			},	
			refresh: function () {
				this.fetchTrips();
			},
			sortUpdated: function (newSort) {
				this.currentSort = newSort;	
				this.refresh();
			},
			pagingUpdated: function (newPaging) {
				//only update the paging if it is different than the one we currently have
				if (JSON.stringify(newPaging) !== JSON.stringify(this.currentPaging)) {
					this.currentPaging = newPaging;
					this.refresh();
				}
			},
			sortCleared: function () {
				this.currentSort = null;
				this.refresh();
			}
		},
		created: function () {
			this.fetchTrips();
		}
	});
	
});