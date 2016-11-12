"use strict";

define("Modules/Auth/Registration/Registration", 
		["Service/system", "Service/util", "Service/applicationProxy", 
         "Modules/Auth/Registration/RegistrationBinding"],
	function (system, util, proxy) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {

		};
		
		var opts = $.extend({}, defaults, options);
		
		
		self.userName = ko.observable("");
		self.password = ko.observable("");
		self.passwordConfirm = ko.observable("");
		self.emailAddress = ko.observable("");
		self.emailAddressConfirm = ko.observable("");
		self.registrationKey = ko.observable("");


	};
	
	return vm;
	
});