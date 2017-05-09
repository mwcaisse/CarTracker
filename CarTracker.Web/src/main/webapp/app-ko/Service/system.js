"use strict";

define("Service/system", ["Service/applicationProxy"], function (proxy) {
	
	var system = new (function() {
		var self = this;

		self.baseUrl = "/cartracker/";	
		
		self.events = $({});
	
		self.ALERT_TYPE_ERROR = "ERROR";
		self.ALERT_TYPE_SUCCESS = "SUCCESS";
		self.ALERT_TYPE_WARN = "WARN";
		self.ALERT_TYPE_INFO = "INFO";
		
		self.EVENT_ALERT_DISPLAY = "alert:display";
		self.EVENT_ALERT_CLEAR = "alert:clear";
		
		self.isAuthenticated = $("#isAuthenticated").val() === "true";		
		
	})();
	
	return system;
	
});