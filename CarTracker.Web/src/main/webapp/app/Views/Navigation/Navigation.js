"use strict";

define("Views/Navigation/Navigation", 
	["Service/system", "Service/util", "Service/navigation", 
	 "Modules/Navigation/NavigationLink/NavigationLink", 
	 "AMD/koTemplateLoader!Views/Navigation/Navigation.html"], function (system, util, navigation, navigationLink) {
	
	var vm = function() {
		var self = this;	
		
		self.isAuthenticated = system.isAuthenticated;
		
		self.navigationLinks = ko.observableArray([]);
		
		if (self.isAuthenticated) {
			self.navigationLinks.push(new navigationLink({
				id: "Home", name: "Home", link: navigation.homeLink()
			}));
			
			self.navigationLinks.push(new navigationLink({
				id: "Car", name: "Car", link: navigation.carsLink()
			}));
			
			self.navigationLinks.push(new navigationLink({
				id: "Trip", name: "Trip", link: navigation.tripsLink()
			}));
			
			self.navigationLinks.push(new navigationLink({
				id: "Admin", name: "Admin", link: navigation.adminRegistrationKeyLink()
			}));
		}
		
		self.logoutClick = function () {
			navigation.navigateToLogout();
		};

	};
	
	return vm;
	
});