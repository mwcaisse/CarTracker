"use strict";

define("Modules/Log/ReaderLogGrid/ReaderLogGrid", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", 
		 "Modules/Common/Pager/Pager",
         "Modules/Common/ColumnHeader/ColumnHeader",
         "Modules/Log/ReaderLogGrid/ReaderLogGridBinding"],
	function (moment, system, util, proxy, pager, columnHeader) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {		
		};
		
		var opts = $.extend({}, defaults, options);

		self.readerLogs = ko.observableArray([]);

		self.sort = null;
		
		self.ReaderLogModel = function (data) {
			
			var log = this;
			
			log.id = data.id;
			log.type = data.type;
			log.message = data.message;
			log.date = moment(data.date);
			
			log.dateDisplay = util.formatDateTime(log.date);
			
			return log;
			
		};
		
		self.gridPager = new pager({
			fetchData: function (startAt, maxResults) {
				return self.fetchLogs(startAt, maxResults);
			}
		});
		
		self.columns = ko.observableArray([]);
		
		self.columns.push(new columnHeader({columnId: "TYPE", columnName: "Type"}));
		self.columns.push(new columnHeader({columnId: "DATE", columnName: "Date"}));
		self.columns.push(new columnHeader({columnId: "MESSAGE", columnName: "Message"}));
	
		/** Fetch the trips from the server */
		self.fetchLogs = function(startAt, maxResults) {
			return proxy.readerLog.getAllPaged(startAt, maxResults, self.sort).then(function (data) {
				var logs = $.map(data.data, function (elm, ind) {
					return new self.ReaderLogModel(elm);
				});
				self.readerLogs(logs);
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
		
		system.events.trigger("sort", {propertyId: "DATE", ascending: false});
	
	};
	
	return vm;
	
});