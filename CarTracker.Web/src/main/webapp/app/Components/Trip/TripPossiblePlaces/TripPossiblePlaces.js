"use strict";

define("Components/Trip/TripPossiblePlaces/TripPossiblePlaces", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 	
		 "Components/Common/Pager/PagedGridMixin",
		 "AMD/text!Components/Trip/TripPossiblePlaces/TripPossiblePlaces.html",
		 "AMD/text!Components/Trip/TripPossiblePlaces/TripPossiblePlacesRow.html",
         "Components/Common/ColumnHeader/ColumnHeader",
         "Components/Common/Pager/Pager"],
	function (moment, system, util, proxy, navigation, pagedGridMixin, template, rowTemplate) {
	
	var placeRow = {
			template: rowTemplate,	
			data: function () {
				return {
					id: -1,
					tripId: -1,
					placeId: -1,
					placeType: "",
					distance: 0,
					placeName: ""
				};
			},
			props: {
				possiblePlace: {
					type: Object,
					requred: true
				}
			},
			computed: {
				
			},
			methods: {
				setPlace: function () {
					var promise;
					if (this.placeType === "START") {
						promise = proxy.trip.setStartingPlace(this.tripId, this.placeId);
					}
					else {
						promise = proxy.trip.setDestinationPlace(this.tripId, this.placeId);
					}					
					promise.then(function (res) {
						
					}.bind(this));
				},
				update: function (data) {
					this.id = data.id;
					this.tripId = data.tripId;
					this.placeId = data.placeId;
					this.placeType = data.placeType;
					this.distance = data.distance;
					this.placeName = data.place.name;
				}
			},
			created: function() {
				this.update(this.possiblePlace);
			}
		};
		
	
	return Vue.component("app-trip-possible-places", {
		mixins: [pagedGridMixin],
		components: {
			"app-trip-possible-places-row": placeRow
		},
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