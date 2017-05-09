"use strict";

define("Views/Navigation/Navigation", 
	["Service/system", "Service/util", "Service/navigation", "Service/applicationProxy",
	 "Modules/Navigation/NavigationLink/NavigationLink", 
	 "AMD/koTemplateLoader!Views/Navigation/Navigation.html"], function (system, util, navigation, proxy, navigationLink) {
	
	var vm = function() {
		var self = this;	
		
		self.isAuthenticated = system.isAuthenticated;
		
		self.navigationLinks = ko.observableArray([]);
		
		self.rightNavigationLinks = ko.observableArray([]);
		
		self.currentUserName = ko.observable("");
		
		self.logoutLink = navigation.logoutLink();
		
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
			
			self.userNavigationLink = new navigationLink({
				id: "User", name: self.currentUserName}
			);
			
			self.userNavigationLink.addSubNavigationLink(new navigationLink({
				id: "AuthTokens", name: "Tokens", link: navigation.userAuthenticationTokensLink()
			}));
			
			self.userNavigationLink.addSubNavigationLink(new navigationLink({
				id: "Logout", name: "Logout", link: navigation.logoutLink()
			}));
			
			self.rightNavigationLinks.push(self.userNavigationLink);
			
			proxy.user.me().then(function(user) {
				self.currentUserName(user.name);
			});
			
		}		
		
	};
	
	return vm;
	
});