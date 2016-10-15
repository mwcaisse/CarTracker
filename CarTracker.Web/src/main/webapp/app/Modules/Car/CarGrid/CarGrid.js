"use strict";

define("Modules/Car/CarGrid/CarGrid", ["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation",
                                          "Modules/Common/Pager/Pager",
                                          "Modules/Common/ColumnHeader/ColumnHeader",
                                          "Modules/Car/CarGrid/CarGridBinding"],
		function (moment, system, util, proxy, navigation, pager, columnHeader) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {	
		};
		
		var opts = $.extend({}, defaults, options);

		
		self.cars = ko.observableArray([]);
		
		self.sort = null;
		
		self.CarModel = function (data) {
			var car = this;
			
			car.id = data.id;
			car.vin = data.vin;
			car.name = data.name;
			
			car.viewCar = function () {
				navigation.navigateToViewCar(car.id);
			};
			
			return car;
		};	
		
		self.gridPager = new pager({fetchData: function (startAt, maxResults) {
			return self.fetchCars(startAt, maxResults);
		}});
		
		self.columns = ko.observableArray([]);
		
		self.columns.push(new columnHeader({columnId: "NAME", columnName: "Name", enableSort: false}));
		self.columns.push(new columnHeader({columnId: "VIN", columnName: "VIN", enableSort: false}));
	
		/** Fetch the cars from the server */
		self.fetchCars = function(startAt, maxResults) {
			return proxy.car.getAllPaged(startAt, maxResults, self.sort).then(function (data) {
				var cars = $.map(data.data, function (elm, ind) {
					return new self.CarModel(elm);
				});
				self.cars(cars);	
				return data;
			},
			function (error) {
				alert(error);
			});
		};	
		
		
		self.load = function() {
			self.gridPager.fetchData();
		};	
		
		self.refresh = function () {
			self.load();
		};		
		
		system.events.on("clearSort", function (event, data) {
			self.sort = null;
			self.refresh();
		});		

		system.events.on("sort", function (event, data) {
			self.sort = data;
			self.refresh();
		});
		
	};
	
	return vm;
	
});