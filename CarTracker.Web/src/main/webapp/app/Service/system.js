"use strict";

define("Service/system", ["Service/applicationProxy", "Service/customDirectives"], function (proxy) {
	
	var system = new (function() {
		var self = this;

		self.baseUrl = "/cartracker/";	
		
		self.events = $({});
		
		//Vue Instance for an event bus
		self.bus = new Vue();
	
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