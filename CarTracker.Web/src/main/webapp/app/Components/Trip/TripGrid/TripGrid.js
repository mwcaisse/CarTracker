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
			update: function (data) {
				this.trips = data.data;
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
				this.currentPaging = newPaging;
				this.refresh();
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