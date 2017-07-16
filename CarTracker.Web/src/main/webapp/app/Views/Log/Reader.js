"use strict";

define("Views/Log/Reader", 
	["Service/util", 
	 "Service/navigation", 
	 "AMD/text!Views/Log/Reader.html",
	 "Components/Log/ReaderLogGrid/ReaderLogGrid"], function (util, navigation, template) {
	
	var vm = function(elementId) {
		
		navigation.setActiveNavigation("Log");
		
		return new Vue({			
			el: elementId,
			template: template			
		});	
	};
	
	
	
	return vm;
	
});