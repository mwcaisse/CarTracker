"use strict";

define("Views/Admin/RegistrationKeys/RegistrationKeys", 
	["Service/util", 
	 "Service/navigation", 
	 "AMD/koTemplateLoader!Views/Admin/RegistrationKeys/RegistrationKeys.html"], 
	 
	 function (util, navigation) {
	
	var vm = function() {
		var self = this;
		
		navigation.setActiveNavigation("Admin");
		
	};
	
	return vm;
	
});