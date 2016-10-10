"use strict";

define("Modules/Common/PageAlert/PageAlert", ["moment", "Service/system", "Service/util", "Service/applicationProxy", 
                                              "Modules/Common/PageAlert/PageAlertBinding"],
		function (moment, system, util, proxy) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
		
		};		
		
		var opts = $.extend({}, defaults, options);
		
		self.alertHeader = ko.observable("");
		self.alertType = ko.observable("");
		self.alertMessage = ko.observable("");
		
		self.display = ko.observable(false);
		
		self.alertCss = ko.computed(function () {
			switch (self.alertType()) {
				case system.ALERT_TYPE_ERROR:
					return "alert-danger";
				case system.ALERT_TYPE_SUCCESS:
					return "alert-success";
				case system.ALERT_TYPE_WARN:
					return "alert-warning";
				case system.ALERT_TYPE_INFO:
					return "alert-info";
				default: 
					return "alert-success";
			}
		});
		
		self.clear = function () {
			self.alertMessage("");
			self.alertType("");
			self.alertHeader("");
			self.display(false);
		};		
		
		system.events.on(system.EVENT_ALERT_DISPLAY, function (event, data) {
			var defs = {
				message: "",
				type: system.ALERT_TYPE_SUCCESS,
				header: ""
			};
			
			var opts = $.extend({}, defs, data);
			
			self.alertMessage(opts.message);
			self.alertType(opts.type);
		
			var header = opts.header;
			if (header.length === 0) {
				switch (opts.type) {
					case system.ALERT_TYPE_ERROR:
						header = "Error:";
						break;
					case system.ALERT_TYPE_SUCCESS:
						header = "Success";
						break;
					case system.ALERT_TYPE_WARN:
						header = "Warning:";
						break;
					case system.ALERT_TYPE_INFO:
						header = "Info:";
						break;		
				}
			}
			header += ": ";
			
			self.alertHeader(header);			
			self.display(true);
			
			util.scrollToTop();
		});
		
		system.events.on(system.EVENT_ALERT_CLEAR, function (event, data) {
			self.clear();			
		});
		
	};
	
	return vm;
	
});