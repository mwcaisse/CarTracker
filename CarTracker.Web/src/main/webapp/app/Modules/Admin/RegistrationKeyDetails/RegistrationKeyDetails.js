"use strict";

define("Modules/Admin/RegistrationKeyDetails/RegistrationKeyDetails", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation",
         "Modules/Admin/RegistrationKeyDetails/RegistrationKeyDetailsBinding"],
	function (moment, system, util, proxy, navigation) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {

		};
		
		var opts = $.extend({}, defaults, options);	
		
		self.id = ko.observable(-1);		
		self.key = ko.observable("");		
		self.usesRemaining = ko.observable(10);		
		self.active = ko.observable(false);
		
		self.visible = ko.observable(true);
		
		self.isNew = ko.computed(function () {
			return self.id() === -1;
		});
		
		self.generateKey = function () {
			self.key("BEST KEY EVER");
		};
		
		self.close = function() {
			self.visible(false);
		};
				
	};
	
	return vm;
	
});