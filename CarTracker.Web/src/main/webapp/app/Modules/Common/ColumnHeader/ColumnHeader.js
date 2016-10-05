"use strict";

define("Modules/Common/ColumnHeader/ColumnHeader", ["moment", "Service/system", "Service/util", "Service/applicationProxy", 
                                                    "Modules/Common/ColumnHeader/ColumnHeaderBinding"],
		function (moment, system, util, proxy) {
	
	var vm = function(options) {
		var self = this;
		
		var SORT_ORDER_ASC = "ASC";
		var SORT_ORDER_DESC = "DESC";
		
		
		var defaults = {
			columnId: "", 
			columnName: ""
		};	
		
		var opts = $.extend({}, defaults, options);
		
		self.columnId = opts.columnId;
		
		self.columnName = ko.observable(opts.columnName);
		
		self.sort = ko.observable(false);
		
		self.sortOrder = ko.observable("");
		
		self.sortIconCss = ko.computed(function () {
			if (self.sort()) {
				return self.sortOrder() === SORT_ORDER_ASC ? "glyphicon-sort-by-attributes" : "glyphicon-sort-by-attributes-alt";
			}
			return "";
		});
		
		self.toggleSort = function() {
			var eventName = "sort";
			var eventData = {
				propertyId: self.columnId,
				ascending: false
			};
			if (self.sort()) {
				if (self.sortOrder() === SORT_ORDER_ASC) {		
					eventData.ascending = false;
				}
				else {							
					eventName = "clearSort";
				}
			}
			else {		
				eventData.ascending = true;
			}
			system.events.trigger(eventName, eventData);
		};
		
		system.events.on("clearSort", function (event, data) {
			self.sortOrder("");
			self.sort(false);
		});
		
		//if we are set to sort, and this is a sort event for another column, clear our sort
		system.events.on("sort", function (event, data) {
			if (data.propertyId === self.columnId) {
				self.sort(true);
				self.sortOrder(data.ascending ? SORT_ORDER_ASC : SORT_ORDER_DESC);
			}
			else if(self.sort()) {
				//clear the sort if this isn't our column
				self.sortOrder("");
				self.sort(false);
			}
		});
		
	};
	
	return vm;
	
});