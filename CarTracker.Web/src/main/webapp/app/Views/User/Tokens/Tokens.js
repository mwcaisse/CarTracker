"use strict";

define("Views/User/Tokens/Tokens", 
	["Service/util", 
	 "Service/navigation", 	
	 "Modules/User/AuthTokenGrid/AuthTokenGrid",
	 "AMD/koTemplateLoader!Views/User/Tokens/Tokens.html"], function (util, navigation, tokenGrid) {
	
	var vm = function() {
		var self = this;		
		
		self.tokenGrid = new tokenGrid();
		self.tokenGrid.load();
		
		navigation.setActiveNavigation("User");
		
	};
	
	return vm;
	
});