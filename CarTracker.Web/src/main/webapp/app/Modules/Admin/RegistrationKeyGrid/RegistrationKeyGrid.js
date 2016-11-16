"use strict";

define("Modules/Admin/RegistrationKeyGrid/RegistrationKeyGrid", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation",
		 "Modules/Common/Pager/Pager",
         "Modules/Common/ColumnHeader/ColumnHeader",
         "Modules/Admin/RegistrationKeyGrid/RegistrationKeyGridBinding"],
	function (moment, system, util, proxy, navigation, pager, columnHeader) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			displayKeyDetails: function (keyId) { }
		};
		
		var opts = $.extend({}, defaults, options);	
		
		self.RegistrationKeyUseModel = function (data) {
			var rku = this;
			
			rku.id = data.id;
			rku.userId = ko.observable(data.userId);
			rku.createDate = ko.observable(moment(data.createDate));			
			rku.username = ko.observable(data.user.username);
			rku.name = ko.observable(data.user.name);
			
			rku.createDateDispaly = ko.computed(function () {
				if (rku.createDate()) {
					return rku.createDate().format("YYYY-MM-DD HH:mm:ss");
				}
				return "";
			});
			
			return rku;
		};
		
		self.RegistrationKeyModel = function (data) {
			var key = this;
			
			key.id = data.id;
			key.key = ko.observable(data.key);
			key.active = ko.observable(data.active);
			key.usesRemaining = ko.observable(data.usesRemaining);
			key.createDate = ko.observable(moment(data.createDate));
			
			key.keyUses = ko.observableArray($.map(data.keyUses, function (elm, ind) {
				return new self.RegistrationKeyUseModel(elm);
			}));
			
			key.createDateDisplay = ko.computed(function () {
				if (key.createDate()) {
					return key.createDate().format("YYYY-MM-DD HH:mm:ss");
				}
				return "";
			});
			
			key.activeDisplay = ko.computed(function () {
				return key.active() ? "Yes" : "No";
			});
			
			key.rowCss = ko.computed(function () {
				var usesRemaining = key.usesRemaining();
				var active = key.active();
				
				if (active) {
					if (usesRemaining < 1) {
						return "danger";
					}
					else if (usesRemaining < 5) {
						return "warning";
					}
					else {
						return "success";
					}
				}			
				return "danger";			
			});
			
			key.showUses = ko.observable(false);
			
			key.toggleShowUses = function () {
				key.showUses(!key.showUses());
			};
			
			key.viewKey = function () {
				opts.displayKeyDetails(key.id);
			};
			
			return key;
		};
		
		self.registrationKeys = ko.observableArray([]);
		
		self.sort = null;
		
		self.gridPager = new pager({fetchData: function (startAt, maxResults) {
			return self.fetchRegistrationKeys(startAt, maxResults);
		}});
		
		self.columns = ko.observableArray([]);
		
		self.columns.push(new columnHeader({columnId: "KEY_VAL", columnName: "Key"}));
		self.columns.push(new columnHeader({columnId: "USES_REMAINING", columnName: "Uses Remaining"}));
		self.columns.push(new columnHeader({columnId: "ACTIVE", columnName: "Active"}));
		self.columns.push(new columnHeader({columnId: "CREATE_DATE", columnName: "Created"}));
		
		self.fetchRegistrationKeys = function (startAt, maxResults) {
			return proxy.registrationKey.getAllPaged(startAt, maxResults, self.sort).then(function (data) {
				var registrationKeys = $.map(data.data, function (elm, ind) {
					return new self.RegistrationKeyModel(elm);
				});				
				self.registrationKeys(registrationKeys);
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