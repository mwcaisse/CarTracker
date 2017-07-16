"use strict";

define("Components/Common/Modal/Modal", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 	
		 "AMD/text!Components/Common/Modal/Modal.html",     
         "Components/Common/ColumnHeader/ColumnHeader"],
	function (moment, system, util, proxy, navigation, template) {	
	
	
	return Vue.component("app-modal", {
		data: function() {
			return {
				modalOpen: false	
			}
		},		
		props: {
			title: {
				type: String,
				required: true
			},
			onClose: {
				type: Function,
				required: false,
				default: function () { }
			},
			width: {
				type: String,
				required: false,
				default: "800px"
			}
		},
		template: template,
		methods: {
			open: function () {
				//only call show if the modal isn't already open
				if (this.modalOpen == false) {
					$(this.$el).modal("show");
				}				
			},
			close: function () {
				//only call hide if the modal is open
				if (this.modalOpen === true) {
					$(this.$el).modal("hide");
				}
			}
		},
		mounted: function () {			
			//subscribe to the model events so we know when it is open or closed
			$(this.$el).on("hide.bs.modal", function (e) {
				this.modalOpen = false;
				this.onClose(); // perform any onClose actions
			}.bind(this));
			
			$(this.$el).on("show.bs.modal", function (e) {
				this.modalOpen = true;
			}.bind(this));
		}
	});
	
});