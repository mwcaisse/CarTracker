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
		
		self.registrationKeyDetails = new registrationKeyDetails();
		self.registrationKeyGrid = new registrationKeyGrid({
			displayKeyDetails: function (keyId) {
				self.displayKey(keyId);
			}
		});
		
		self.registrationKeyGrid.load();
		
		self.createKey = function () {
			self.registrationKeyDetails.display();
		};
		
		self.displayKey = function (keyId) {
			self.registrationKeyDetails.display(keyId);
		}
				
		navigation.setActiveNavigation("Admin");	
	};
	
	return vm;
	
});