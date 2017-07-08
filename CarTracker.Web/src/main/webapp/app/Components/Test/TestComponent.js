"use strict";

define("Components/Test/TestComponent", 
		["AMD/text!Components/Test/TestComponent.html", "Service/applicationProxy"],
	function (template, proxy) {
	
	return Vue.component("app-car", {
		data: function() {
			return {	
				vin: "",
				name: "",			
				mileage: 0
			}
		},
		props: ['id'],
		template: template,
		methods: {
			fetch: function () {
				proxy.car.get(this.id).then(function (data) {
					this.update(data);
					console.log("hey this is car: " + data);
				}.bind(this),
				function (error) {
					alert(error);
				});
			},
			update: function (car) {		
				this.vin = car.vin;
				this.name = car.name;
				this.mileage = car.mileage;			
			}
		},
		watch: {
			'id': function () {
				this.fetch(this.id);		
			}
		},
		mounted: function () {
			this.fetch()
		}
	});
	
});