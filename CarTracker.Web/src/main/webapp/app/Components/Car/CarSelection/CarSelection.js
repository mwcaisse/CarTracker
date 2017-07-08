"use strict";

define("Components/Car/CarSelection/CarSelection", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy",  
         "AMD/text!Components/Car/CarSelection/CarSelection.html"],
	function (moment, system, util, proxy, template) {
	
	return Vue.component("app-car-selection", {
		data: function() {
			return {
				cars: []
			}
		},	
		template: template,
		methods: {
			fetchCars: function () {
				proxy.car.getAll().then(function (data) {
					this.cars = data.data;
				}.bind(this),
				function (error) {
					
				})
			}
				
		},
		created: function () {
			this.fetchCars();
		}
	});
	
});