"use strict";

define("Modules/Common/ColumnHeader/ColumnHeader", ["moment", "Service/util", "Service/applicationProxy", 
                                                    "Modules/Common/ColumnHeader/ColumnHeaderBinding"],
		function (moment, util, proxy) {
	
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
			if (self.sort()) {
				if (self.sortOrder() === SORT_ORDER_ASC) {
					self.sortOrder(SORT_ORDER_DESC);
				}
				else {
					self.sortOrder("");
					self.sort(false);
				}
			}
			else {
				self.sort(true);
				self.sortOrder(SORT_ORDER_ASC);
			}
		};
		
	};
	
	return vm;
	
});