"use strict";

define("Components/Admin/RegistrationKeyModal/RegistrationKeyModal", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 	
		 "AMD/text!Components/Admin/RegistrationKeyModal/RegistrationKeyModal.html",     
         "Components/Common/ColumnHeader/ColumnHeader",
         "Components/Common/Pager/Pager"],
	function (moment, system, util, proxy, navigation, template) {	
	
	
	return Vue.component("app-registration-key-modal", {
		data: function() {
			return {
				showModal: false	
			}
		},			
		template: template,
		methods: {
			fetchKey: function () {							
				proxy.registrationKey.get(1).then(function (data) {					
					this.update(data);
				}.bind(this),
				function (error) {
					alert("error fetching auth tokens!");
				})
			},
			update: function (key) {
				//do stuff here
			},
			refresh: function () {
				this.fetchKey();
			}		
		},
		created: function () {
			system.bus.$on("registrationKey:create", function () {
				console.log("Create registration key showModal: " + this.showModal);
				this.showModal = true;
			}.bind(this));
		}
	});
	
});