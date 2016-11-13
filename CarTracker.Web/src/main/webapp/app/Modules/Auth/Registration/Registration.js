"use strict";

define("Modules/Auth/Registration/Registration", 
		["Service/system", "Service/util", "Service/applicationProxy", "Service/navigation",
         "Modules/Auth/Registration/RegistrationBinding"],
	function (system, util, proxy, navigation) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {

		};
		
		var opts = $.extend({}, defaults, options);		
		
		self.username = ko.observable("");
		self.password = ko.observable("");
		self.passwordConfirm = ko.observable("");
		self.email = ko.observable("");
		self.emailConfirm = ko.observable("");
		self.registrationKey = ko.observable("");

		self.usernameError = ko.observable("");
		self.passwordError = ko.observable("");
		self.emailError = ko.observable("");
		self.registrationKeyError = ko.observable("");
		
		self.pageError = ko.observable("");
		
		self.hasUsernameError = ko.computed(function () {
			return !util.isStringNullOrBlank(self.usernameError());
		});
		
		self.hasPasswordError = ko.computed(function () {
			return !util.isStringNullOrBlank(self.passwordError());
		});
		
		self.hasEmailError = ko.computed(function () {
			return !util.isStringNullOrBlank(self.emailError());
		});
		
		self.hasRegistrationKeyError = ko.computed(function () {
			return !util.isStringNullOrBlank(self.registrationKeyError());
		});
		
		self.hasPageError = ko.computed(function () {
			return !util.isStringNullOrBlank(self.pageError());
		});
		
		self.hasClickedRegister = ko.observable(false);
		
		self.cancelClick = function () {
			navigation.navigateToHome();
		};
		
		self.registerClick = function () {
			self.hasClickedRegister(true);
			if (self.isValid()) {
				self.register();
			}		
		};
		
		self.dismissPageError = function () {
			self.pageError("");
		};
		
		self.register = function () {
			var registration = {
				username: self.username(),
				password: self.password(),
				email: self.email(),
				registrationKey: self.registrationKey()
			};
			
			proxy.user.register(registration).then(function (res) {
				if (res) {
					navigation.navigateToLogin("registered");
				}
				else {
					self.pageError("Failed to Register. An unexpected error occured.");
				}
			}, function (error) {
				var errorText = error ? error : "An unexpected error occured.";
				self.pageError("Failed to Register. " + errorText);
			});
		};
		
		self.isValid = ko.computed(function () {
			var usernameError = "";
			var passwordError = "";
			var emailError = "";
			var registrationKeyError = "";
			
			if (self.hasClickedRegister() && util.isStringNullOrBlank(self.username())) {
				usernameError = "Username cannot be blank!";
			}
			
			if (self.hasClickedRegister() && util.isStringNullOrBlank(self.password())) {
				passwordError = "Password cannot be blank!";			
			}
			else if (self.password() !== self.passwordConfirm()) {
				passwordError = "Passwords do not match!";
			}
			
			if (self.hasClickedRegister() && util.isStringNullOrBlank(self.email())) {
				emailError = "Email cannot be blank!";
			}
			else if (self.email() !== self.emailConfirm()) {
				emailError = "Emails do not match!";
			}
			
			if (self.hasClickedRegister() && util.isStringNullOrBlank(self.registrationKey())) {
				registrationKeyError = "Registration Key cannot be blank!";
			}
			
			self.usernameError(usernameError);
			self.passwordError(passwordError);
			self.emailError(emailError);
			self.registrationKeyError(registrationKeyError);
			
			return (util.isStringNullOrBlank(usernameError) &&
					util.isStringNullOrBlank(passwordError) &&
					util.isStringNullOrBlank(emailError) &&
					util.isStringNullOrBlank(registrationKeyError));				
			
			
		});
		
	};
	
	return vm;
	
});