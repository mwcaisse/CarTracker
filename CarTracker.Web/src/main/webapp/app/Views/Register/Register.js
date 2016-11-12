"use strict";

define("Views/Register/Register", 
	["Service/system", "Service/util", "Service/navigation",
	 "Modules/Auth/Registration/Registration",
	 "AMD/koTemplateLoader!Views/Register/Register.html"], function (system, util, navigation, registration) {
	
	var vm = function() {
		var self = this;	
		
		self.registration = new registration();
		
	};
	
	return vm;
	
});