"use strict";

define("Components/Auth/Registration/Registration", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 	
		 "AMD/text!Components/Auth/Registration/Registration.html",   
         "Components/Common/Modal/Modal"],
	function (moment, system, util, proxy, navigation, template) {	
	
	
	return Vue.component("app-registration", {
		template: template,
		data: function() {
			return {		
				name: "",
				username: "",
				password: "",
				passwordConfirm: "",
				email: "",
				emailConfirm: "",
				registrationKey: "",
				
				nameError: "",
				usernameError: "",
				passwordError: "",
				emailError: "",
				registrationKeyError: "",
				
				pageError: "",
				hasClickedRegister: false
			}
		},	
		computed: {
			hasNameError: function () {
				return !util.isStringNullOrBlank(this.nameError);
			},
			hasUsernameError: function () {
				return !util.isStringNullOrBlank(this.usernameError);
			},
			hasPasswordError: function () {
				return !util.isStringNullOrBlank(this.passwordError);
			},
			hasEmailError: function () {
				return !util.isStringNullOrBlank(this.emailError);
			},
			hasRegistrationKeyError: function () {
				return !util.isStringNullOrBlank(this.registrationKeyError);
			},	
			hasPageError: function () {
				return !util.isStringNullOrBlank(this.pageError);
			},	
			isValid: function () {
				if (!this.hasClickedRegister) {
					return false;
				}
				
				this.nameError = "";
				this.usernameError = "";
				this.passwordError = "";
				this.emailError = "";
				this.registrationKeyError = "";
				
				if (util.isStringNullOrBlank(this.name)) {
					this.nameError = "Name cannot be blank!";
				}
				
				if (util.isStringNullOrBlank(this.username)) {
					this.usernameError = "Username cannot be blank!";
				}
				
				if (util.isStringNullOrBlank(this.password)) {
					this.passwordError = "Password cannot be blank!";
				}
				else if (this.password !== this.passwordConfirm) {
					this.passwordError = "Passwords to not match!";
				}
				
				if (util.isStringNullOrBlank(this.email)) {
					this.emailError = "Email cannot be blank!";
				}
				else if (this.email !== this.emailConfirm) {
					this.emailError = "Emails do not match!";
				}
				
				if (util.isStringNullOrBlank(this.registrationKey)) {
					this.registrationKeyError = "Registration Key cannot be blank!";
				}
				
				return util.isStringNullOrBlank(this.nameError) &&
					   util.isStringNullOrBlank(this.usernameError) &&
					   util.isStringNullOrBlank(this.passwordError) &&
					   util.isStringNullOrBlank(this.emailError) &&
					   util.isStringNullOrBlank(this.registrationKeyError);
			}
		},	
		methods: {		
			createModel: function () {
				return {
					name: this.name,
					username: this.username,
					password: this.password,
					email: this.email,
					registrationKey: this.registrationKey
				};
			},
			registerClick: function () {
				this.hasClickedRegister = true;
				if (this.isValid) {
					this.register();
				}
			},
			register: function () {
				proxy.user.register(this.createModel()).then(function (res) {
					if (res) {
						navigation.navigateToLogin("registered");
					}
					else {
						self.pageError("Failed to Register. An unexpected error occured.");
					}
				}.bind(this),
				function (error) {
					var errorText = error ? error : "An unexpected error occured.";
					this.pageError = "Failed to Register. " + errorText;
				}.bind(this));
			},
			cancel: function () {
				navigation.navigateToHome();
			},
			clearPageError: function () {
				this.pageError = "";
			}
		},
		created: function () {
	
		}
	});
	
});