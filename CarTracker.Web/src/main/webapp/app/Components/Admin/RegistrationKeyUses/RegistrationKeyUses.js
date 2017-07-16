"use strict";

define("Components/Admin/RegistrationKeyUses/RegistrationKeyUses", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 	
		 "AMD/text!Components/Admin/RegistrationKeyUses/RegistrationKeyUses.html",   
         "Components/Common/Modal/Modal"],
	function (moment, system, util, proxy, navigation, template) {	
	
	
	return Vue.component("app-registration-key-uses", {
		template: template,
		data: function() {
			return {	
				show: false
			}
		},	
		props: {
			uses: {
				type: Array,
				required: true,
				default: function () {
					return [];
				}
			}
		},	
		watch: {
	
		},
		methods: {
			toggleShow: function () {
				this.show = !this.show;
			}
		}
	});
	
});