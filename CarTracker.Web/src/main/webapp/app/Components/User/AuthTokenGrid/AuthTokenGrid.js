"use strict";

define("Components/User/AuthTokenGrid/AuthTokenGrid", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 	
		 "Components/Common/Pager/PagedGridMixin",
		 "AMD/text!Components/User/AuthTokenGrid/AuthTokenGrid.html",     
         "Components/Common/ColumnHeader/ColumnHeader",
         "Components/Common/Pager/Pager"],
	function (moment, system, util, proxy, navigation, pagedGridMixin, template) {	
	
	
	return Vue.component("app-auth-token-grid", {
		mixins: [pagedGridMixin],
		data: function() {
			return {
				tokens: [],
				currentSort: { propertyId: "LAST_LOGIN", ascending: false}		
			}
		},			
		template: template,
		methods: {
			fetch: function () {							
				proxy.authToken.getAllActivePaged(this.startAt, this.take, this.currentSort).then(function (data) {					
					this.update(data);
				}.bind(this),
				function (error) {
					alert("error fetching auth tokens!");
				})
			},		
			update: function (data) {			
				this.tokens = $.map(data.data, function (elm, ind) {
					return this.augmentToken(elm);				
				}.bind(this));
				this.totalItems = data.total;
			},	
			augmentToken: function (token) {			
				token.lastLogin = moment(token.lastLogin);
				token.expirationDate = moment(token.expirationDate);
				token.createDate = moment(token.createDate);
				return token;
			},
			refresh: function () {
				this.fetch();
			}		
		},
		created: function () {
			this.fetch();
		}
	});
	
});