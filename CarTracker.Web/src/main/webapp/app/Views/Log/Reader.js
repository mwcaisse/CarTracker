"use strict";

define("Views/Log/Reader", 
	["Service/util", 
	 "Service/navigation", 
	 "Modules/Common/PageAlert/PageAlert",
	 "Modules/Log/ReaderLogGrid/ReaderLogGrid",
	 "AMD/koTemplateLoader!Views/Log/Reader.html"], function (util, navigation, pageAlert, readerLogGrid) {
	
	var vm = function() {
		var self = this;
	
		self.readerLogGrid = new readerLogGrid();
		
		self.readerLogGrid.load();
		
		navigation.setActiveNavigation("Log");
		
	};
	
	return vm;
	
});