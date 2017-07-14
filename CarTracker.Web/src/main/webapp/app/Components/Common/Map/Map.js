"use strict";

define("Components/Common/Map/Map", 
		["AMD/googlemaps!",
		 "AMD/text!Components/Common/Map/Map.html"],
	function (gmaps, template) {
	
	return Vue.component("app-map", {
		data: function() {
			return {	
				map: {}
			}
		},	
		computed: {
			styles: function () {
				return {
					width: this.width + "px",
					height: this.height + "px"
				};
			}
		},	
		props: {
			width: {
				type: Number,
				default: 600,
				required: false
			},
			height: {
				type: Number,
				default: 600,
				required: false
			},
			zoom: {
				type: Number,
				default: 12,
				required: false
			},
			center: {
				type: Object,
				required: false,
				default: function () {
					return {
						lat: 42.710291, 
						lng: -71.442039
					};
				}
			}
		},		
		template: template,
		methods: {			
	
		},
		mounted: function () {
			this.map = new gmaps.Map(this.$el, {
				zoom: this.zoom,
				center: this.center
			});
			
			this.$emit("map:loaded", this.map);		
		}
	});
	
});