"use strict";

define("Components/Trip/TripPossiblePlaces/TripPossiblePlaces", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 	
		 "Components/Common/Pager/PagedGridMixin",
		 "AMD/text!Components/Trip/TripPossiblePlaces/TripPossiblePlaces.html",
         "Components/Common/ColumnHeader/ColumnHeader",
         "Components/Common/Pager/Pager"],
	function (moment, system, util, proxy, navigation, pagedGridMixin, template) {
	
	return Vue.component("app-trip-possible-places", {
		mixins: [pagedGridMixin],			
		data: function() {
			return {
				possiblePlaces: [],
				currentSort: { }			
			}
		},	
		props: {
			tripId: {
				type: Number,
				required: true
			},
			type: {
				type: String,
				required: true
			}
		},
		computed: {
			typeDisplay: function () {
				if (this.type === "START") {
					return "Starting Points";
				}
				else {
					return "Destinations";
				}
			}
		},
		template: template,
		methods: {
			fetchPossiblePlaces: function () {				
				proxy.tripPossiblePlaces.getForTrip(this.tripId, this.type, this.startAt, this.take, this.currentSort).then(function (data) {
					this.update(data);
				}.bind(this),
				function (error) {
					alert("error fetching trips!");
				})
			},		
			update: function (data) {
				this.possiblePlaces = data.data;	
				this.totalItems = data.total;
			},	
			refresh: function () {
				this.fetchPossiblePlaces();
			}			
		},
		created: function () {
			this.fetchPossiblePlaces();
		}
	});
	
});