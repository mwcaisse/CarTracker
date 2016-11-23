"use strict";

define("Views/User/Tokens/Tokens", 
	["Service/util", 
	 "Service/navigation", 	
	 "AMD/koTemplateLoader!Views/User/Tokens/Tokens.html"], function (util, navigation) {
	
	var vm = function() {
		var self = this;		
		
		navigation.setActiveNavigation("User");
		
	};
	
	return vm;
	
});