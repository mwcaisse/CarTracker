"use strict";

define("Views/Navigation/Navigation", 
	["Service/util", "Service/navigation", 
	 "Modules/Navigation/NavigationLink/NavigationLink", 
	 "AMD/koTemplateLoader!Views/Navigation/Navigation.html"], function (util, navigation, navigationLink) {
	
	var vm = function() {
		var self = this;	
		
		self.navigationLinks = ko.observableArray([]);
		
		self.navigationLinks.push(new navigationLink({
			id: "Home", name: "Home", link: navigation.homeLink()
		}));
		
		self.navigationLinks.push(new navigationLink({
			id: "Car", name: "Car", link: navigation.carsLink()
		}));
		
		self.navigationLinks.push(new navigationLink({
			id: "Trip", name: "Trip", link: navigation.tripsLink()
		}));
		
	};
	
	return vm;
	
});