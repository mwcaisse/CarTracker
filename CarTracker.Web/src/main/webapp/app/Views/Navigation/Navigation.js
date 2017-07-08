"use strict";

define("Views/Navigation/Navigation", 
		["Service/navigation", "Components/Navigation/NavigationLink/NavigationLink",
		 "AMD/text!Views/Navigation/Navigation.html"], 
		
		function(navigation, navLink, template) {
			
			var isAuthenticated = $("#isAuthenticated").val() === "true";
	
			var vm = function (elementId) {
				return new Vue({			
					el: elementId,
					template: template,
					data: {
						navigationLinks: [],
						rightNavigationLinks: []
					},
					methods: {
						initalizeLinks: function () {
							//we only add navigation links if we are authenticated
							if (isAuthenticated) {
								this.navigationLinks.push({
									id: "Home", name: "Home", link: navigation.homeLink()
								});
								this.navigationLinks.push({
									id: "Car", name: "Car", link: navigation.carsLink()
								});
								this.navigationLinks.push({
									id: "Trip", name: "Trip", link: navigation.tripsLink()
								});
								
								//Create the log links
								var logLink = { id: "Log", name: "Log", link: "#", subLinks: [] };
								logLink.subLinks.push({
									id: "Log/Reader", name: "Reader", link: navigation.readerLogLink()
								});
								
								this.navigationLinks.push(logLink);
								
								var userNav = {
									id: "User", name: "User", link: "#", subLinks: []
								};
								
								userNav.subLinks.push({
									id: "User/AuthTokens", name: "Tokens", link: navigation.userAuthenticationTokensLink()
								});
								userNav.subLinks.push({
									id: "User/Logout", name: "Logout", link: navigation.logoutLink()
								});
								
								this.rightNavigationLinks.push(userNav);
							}
						}
					},
					created: function () {
						this.initalizeLinks();
					}
				});
			}
			
			return vm;
		}
);