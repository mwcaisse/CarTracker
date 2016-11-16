"use strict";

define("Views/Admin/RegistrationKeys/RegistrationKeys", 
	["Service/util", 
	 "Service/navigation", 
	 "Modules/Admin/RegistrationKeyGrid/RegistrationKeyGrid",
	 "AMD/koTemplateLoader!Views/Admin/RegistrationKeys/RegistrationKeys.html"], 
	 
	 function (util, navigation, registrationKeyGrid) {
	
	var vm = function() {
		var self = this;
		
		self.registrationKeyGrid = new registrationKeyGrid();
		
		self.registrationKeyGrid.load();
				
		navigation.setActiveNavigation("Admin");	
	};
	
	return vm;
	
});