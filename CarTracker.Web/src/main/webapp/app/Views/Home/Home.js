"use strict";

define("Views/Home/Home", 
	["Service/util",
	 "Service/navigation", 
	 "AMD/koTemplateLoader!Views/Home/Home.html"], function (util, navigation) {
	
	var vm = function() {
		var self = this;	
		
		navigation.setActiveNavigation("Home");
	};
	
	return vm;
	
});