"use strict";

define("Views/Home/Home", 
	["Service/util",
	 "Service/navigation", 
	 "Components/Car/CarSelection/CarSelection",
	 "AMD/text!Views/Home/Home.html"], function (util, navigation, carSelection, template) {
	
	var vm = function (elementId) {
		return new Vue({			
			el: elementId,
			template: template		
		});
	};
	
	navigation.setActiveNavigation("Home");
	
	return vm;
	
});