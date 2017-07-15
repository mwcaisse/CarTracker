"use strict";

define("Views/Admin/RegistrationKeys/RegistrationKeys", 
	["Service/util", 
	 "Service/navigation", 
	 "AMD/text!Views/Admin/RegistrationKeys/RegistrationKeys.html",
	 "Components/Admin/RegistrationKeyGrid/RegistrationKeyGrid"], 
	 
	function (util, navigation, template) {	

	
		var vm = function (elementId) {	
			
			return new Vue({			
				el: elementId,
				template: template			
			});
		};
		
		navigation.setActiveNavigation("Admin");	
		
		return vm;
	
});