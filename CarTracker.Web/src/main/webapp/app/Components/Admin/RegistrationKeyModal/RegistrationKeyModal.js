"use strict";

define("Components/Admin/RegistrationKeyModal/RegistrationKeyModal", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 	
		 "AMD/text!Components/Admin/RegistrationKeyModal/RegistrationKeyModal.html",   
         "Components/Common/Modal/Modal",
         "Components/Admin/RegistrationKeyUses/RegistrationKeyUses"],
	function (moment, system, util, proxy, navigation, template) {	
	
	
	return Vue.component("app-registration-key-modal", {
		data: function() {
			return {
				title: "Create Registration Key",			
				id: -1,
				key: "",
				usesRemaining: 0,
				active: false,
				keyUses: []
			}
		},			
		template: template,
		methods: {
			fetchKey: function () {							
				proxy.registrationKey.get(this.id).then(function (data) {					
					this.update(data);
				}.bind(this),
				function (error) {
					alert("error fetching auth tokens!");
				})
			},
			update: function (key) {
				this.id = key.id;
				this.key = key.key;
				this.usesRemaining = key.usesRemaining;
				this.active = key.active;
				this.keyUses = key.keyUses;
			},
			clear: function () {
				this.id = -1;
				this.key = "";
				this.usesRemaining = 0;
				this.active = false;
				this.keyUses = [];
			},
			createModel: function () {
				return {
					id: this.id,
					key: this.key,
					usesRemaining: this.usesRemaining,
					active: this.active
				};
			},
			save: function () {
				var func;
				if (this.id < 0) {
					func = proxy.registrationKey.create;
				}
				else {
					func = proxy.registrationKey.update;
				}
				
				func(this.createModel()).then(function (key) {
					this.$emit("registrationKey:updated");	
					this.$refs.modal.close();
				}.bind(this),
				function (error) {
					alert(error);
				});
			},
			refresh: function () {
				this.fetchKey();
			},
			generateKey: function () {
				this.key = util.generateUuid();
			}
		},
		created: function () {
			system.bus.$on("registrationKey:create", function () {
				this.$refs.modal.open();
				this.title = "Create Registration Key";
			}.bind(this));
			
			system.bus.$on("registrationKey:edit", function (keyId) {
				this.id = keyId;
				this.fetchKey();
				this.title = "Edit Registration Key";
				this.$refs.modal.open();
			}.bind(this));
		}
	});
	
});