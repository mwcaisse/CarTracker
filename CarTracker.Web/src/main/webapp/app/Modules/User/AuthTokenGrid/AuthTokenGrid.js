"use strict";

define("Modules/User/AuthTokenGrid/AuthTokenGrid", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation",
		 "Modules/Common/Pager/Pager",
         "Modules/Common/ColumnHeader/ColumnHeader",
         "Modules/User/AuthTokenGrid/AuthTokenGridBinding"],
	function (moment, system, util, proxy, navigation, pager, columnHeader) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			displayKeyDetails: function (keyId) { }
		};
		
		var opts = $.extend({}, defaults, options);		
				
		self.AuthTokenModel = function (data) {
			var token = this;
			
			token.id = data.id;
			token.userId = data.userId;
			token.deviceUuid = ko.observable(data.deviceUuid);
			token.active = ko.observable(data.active);
			token.lastLogin = ko.observable(moment(data.lastLogin));
			token.lastLoginAddress = ko.observable(data.lastLoginAddress);
			token.expirationDate = ko.observable(moment(data.expirationDate));
			token.createDate = ko.observable(moment(data.createDate));
			
			token.lastLoginDisplay = ko.computed(function () {
				return util.formatDateTime(token.lastLogin());
			});
			
			token.expirationDateDisplay = ko.computed(function () {
				return util.formatDateTime(token.expirationDate());
			});
			
			token.createDateDisplay = ko.computed(function () {
				return util.formatDateTime(token.createDate());
			});
			
			token.activeDisplay = ko.computed(function () {
				return token.active() ? "Yes" : "No";
			});			
			
			return token;
		};
				
		
		self.authTokens = ko.observableArray([]);
		
		self.sort = null;
		
		self.gridPager = new pager({fetchData: function (startAt, maxResults) {
			return self.fetchAuthTokens(startAt, maxResults);
		}});
		
		self.columns = ko.observableArray([]);
		
		self.columns.push(new columnHeader({columnId: "DEVICE_UUID", columnName: "Device UUID"}));
		self.columns.push(new columnHeader({columnId: "ACTIVE", columnName: "Active"}));
		self.columns.push(new columnHeader({columnId: "LAST_LOGIN", columnName: "Last Login Date"}));
		self.columns.push(new columnHeader({columnId: "LAST_LOGIN_ADDRESS", columnName: "Last Login Address"}));
		self.columns.push(new columnHeader({columnId: "EXPIRATION_DATE", columnName: "Expiration Date"}));
		self.columns.push(new columnHeader({columnId: "CREATE_DATE", columnName: "Create Date"}));
		
		self.fetchAuthTokens = function (startAt, maxResults) {
			return proxy.authToken.getAllActivePaged(startAt, maxResults, self.sort).then(function (data) {
				var authTokens = $.map(data.data, function (elm, ind) {
					return new self.AuthTokenModel(elm);
				});				
				self.authTokens(authTokens);
				return data;
			},
			function (error) {
				alert(error);
			});
		};
		
		self.load = function() {
			self.gridPager.fetchData();
		};
		
		self.refresh = function () {
			self.load();
		};
		
		system.events.on("clearSort", function (event, data) {
			self.sort = null;
			self.refresh();
		});		

		system.events.on("sort", function (event, data) {
			self.sort = data;
			self.refresh();
		});
		
	};
	
	return vm;
	
});