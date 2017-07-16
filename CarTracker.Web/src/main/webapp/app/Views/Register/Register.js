"use strict";

define("Views/Register/Register", 
	["Service/system", "Service/util", "Service/navigation",	 
	 "AMD/text!Views/Register/Register.html",
	 "Components/Auth/Registration/Registration",], 
	 
	 function (system, util, navigation, template) {
	
	var vm = function (elementId) {	
		
		return new Vue({
			el: elementId,
			template: template		
		});
		
	};
	
	return vm;
	
});