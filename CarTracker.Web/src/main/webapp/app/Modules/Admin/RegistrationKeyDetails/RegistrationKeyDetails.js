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
		
		self.id = ko.observable(1);		
		self.key = ko.observable("");		
		self.usesRemaining = ko.observable(0);		
		self.active = ko.observable(false);
		
		self.visible = ko.observable(false);
		
		self.isNew = ko.computed(function () {
			return self.id() === -1;
		});
		
		self.generateKey = function () {
			self.key("BEST KEY EVER");
		};
		
		self.close = function() {
			self.visible(false);
		};
		
		self.loadRegistrationKey = function () {
			if (!self.isNew()) {
				proxy.registrationKey.get(self.id()).then (function (registrationKey) {
					self.update(registrationKey);
				},
				function (error) {
					alert(error);
				});
			}
		};
		self.createModel = function () {
			var model = {
				id: self.id(),
				key: self.key(),
				usesRemaining: self.usesRemaining(),
				active: self.active()
			};
			return model;
		}
		
		
		self.save = function () {
			var model = self.createModel();
			var promise;
			if (self.isNew()) {
				promise = proxy.registrationKey.create(model);
			}
			else {
				promise = proxy.registrationKey.update(model);
			}
			
			promise.then(function(registrationKey) {
				self.update(registrationKey);
			},
			function (error) {
				alert(error)
			});			
		};
		
		self.discard = function () {
			if (self.isNew()) {
				self.clear();
			}
			else {
				self.loadRegistrationKey();
			}
		};
		
		self.update = function (data) {
			self.id(data.id);
			self.key(data.key);
			self.usesRemaining(data.usesRemaining);
			self.active(data.active);
		};
		
		self.clear = function () {
			self.id(-1);
			self.key("");
			self.usesRemaining(0);
			self.active(false);
		};
		
		self.display = function (keyId) {
			if (typeof keyId === "undefined") {
				keyId = -1;
			}			
			self.id(keyId);
			if (!self.isNew()) {
				self.loadRegistrationKey();
			}	
			else {
				self.clear();
			}
			
			self.visible(true);
		}
				
	};
	
	return vm;
	
});