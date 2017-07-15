"use strict";

define("Components/Admin/RegistrationKeyGrid/RegistrationKeyGrid", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 	
		 "Components/Common/Pager/PagedGridMixin",
		 "AMD/text!Components/Admin/RegistrationKeyGrid/RegistrationKeyGrid.html",     
         "Components/Common/ColumnHeader/ColumnHeader",
         "Components/Common/Pager/Pager",
         "Components/Admin/RegistrationKeyModal/RegistrationKeyModal"],
	function (moment, system, util, proxy, navigation, pagedGridMixin, template) {	
	
	
	return Vue.component("app-registration-key-grid", {
		mixins: [pagedGridMixin],
		data: function() {
			return {
				keys: [],
				currentSort: { propertyId: "CREATE_DATE", ascending: false}		
			}
		},			
		template: template,
		methods: {
			fetch: function () {							
				proxy.registrationKey.getAllPaged(this.startAt, this.take, this.currentSort).then(function (data) {					
					this.update(data);
				}.bind(this),
				function (error) {
					alert("error fetching regisration keys!");
				})
			},		
			update: function (data) {			
				this.keys = $.map(data.data, function (elm, ind) {
					return this.augmentKey(elm);				
				}.bind(this));
				this.totalItems = data.total;
			},	
			augmentKey: function (key) {			
				key.createDate = moment(key.createDate);
				
				var rowCss = "table-danger";
				if (key.active) {
					if (key.usesRemaining < 1) {
						rowCss = "table-danger";
					}
					else if (key.usesRemaining < 5) {
						rowCss = "table-warning";
					}
					else {
						rowCss = "table-success";
					}
				}
				key.rowCss = rowCss;
				
				return key;
			},
			refresh: function () {
				this.fetch();
			},
			viewKey: function (id) {
				system.bus.$emit("registrationKey:edit", id);
			},
			createKey: function (id) {
				system.bus.$emit("registrationKey:create");
			}
		},
		created: function () {
			this.fetch();
		}
	});
	
});