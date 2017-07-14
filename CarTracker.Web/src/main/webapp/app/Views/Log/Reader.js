"use strict";

define("Views/Log/Reader", 
	["Service/util", 
	 "Service/navigation", 
	 "Modules/Common/PageAlert/PageAlert",	
	 "AMD/text!Views/Log/Reader.html",
	 "Components/Log/ReaderLogGrid/ReaderLogGrid"], function (util, navigation, pageAlert, template) {
	
	var vm = function(elementId) {
		
		navigation.setActiveNavigation("Log");
		
		return new Vue({			
			el: elementId,
			template: template			
		});	
	};
	
	
	
	return vm;
	
});