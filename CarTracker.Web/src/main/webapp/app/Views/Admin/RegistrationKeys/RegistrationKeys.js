"use strict";

define("Views/Admin/RegistrationKeys/RegistrationKeys", 
	["Service/util", 
	 "Service/navigation", 
	 "Modules/Admin/RegistrationKeyGrid/RegistrationKeyGrid",
	 "Modules/Admin/RegistrationKeyDetails/RegistrationKeyDetails",
	 "AMD/koTemplateLoader!Views/Admin/RegistrationKeys/RegistrationKeys.html"], 
	 
	 function (util, navigation, registrationKeyGrid, registrationKeyDetails) {
	
	var vm = function() {
		var self = this;
		
		self.registrationKeyGrid = new registrationKeyGrid();
		self.registrationKeyDetails = new registrationKeyDetails();
		
		self.registrationKeyGrid.load();
				
		navigation.setActiveNavigation("Admin");	
	};
	
	return vm;
	
});