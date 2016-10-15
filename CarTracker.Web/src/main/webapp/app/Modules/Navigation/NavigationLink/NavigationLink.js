"use strict";

define("Modules/Navigation/NavigationLink/NavigationLink", 
		["moment", "Service/system", "Service/util", "Service/navigation", 
         "Modules/Navigation/NavigationLink/NavigationLinkBinding"],
	function (moment, system, util, navigation) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			id: "",
			name: "",
			link: "#"
		};
		
		var opts = $.extend({}, defaults, options);

		self.id = opts.id;		
		self.name = opts.name;
		self.link = opts.link;
		
		self.subNavigationLinks = opts.subNavigationLinks;
		
		self.hasSubNavigation = self.subNavigationLinks && self.subNavigationLinks.length > 0;
		
		self.active = ko.observable(false);
		
		self.addSubNavigationLink = function (link) {
			self.subNavigationLinks.push(link);
		};
		
		system.events.on(navigation.EVENT_NAVIGATION_ACTIVE_CHANGED, function (event, data) {
			if (data.id === self.id) {
				self.active(true);
			}
			else {
				self.active(false);
			}
		});
		
	};
	
	return vm;
	
});