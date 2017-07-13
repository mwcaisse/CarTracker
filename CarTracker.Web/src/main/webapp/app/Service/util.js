"use strict";

define("Service/util", ["moment", "moment-duration-format"], function () {
	
	var util = new (function() {
		var self = this;

		self.KM_IN_MI = 0.621371;
		
		self.DEGREE_SYMBOL = String.fromCharCode(176);
		
		self.TRIP_STATUS_NEW = "New";
		self.TRIP_STATUS_STARTED = "Started";
		self.TRIP_STATUS_FINISHED = "Finished";
		self.TRIP_STATUS_PROCESSED = "Processed";
		
		self.TRIP_STATUSES = [
		                      self.TRIP_STATUS_NEW,
		                      self.TRIP_STATUS_STARTED,
		                      self.TRIP_STATUS_FINISHED,
		                      self.TRIP_STATUS_PROCESSED
		                      ];
	
		
		self.convertKmToMi = function (km) {
			return km * self.KM_IN_MI;
		};	
		
		self.convertCelsiusToFah = function (c) {
			return c * 1.8 + 32
		};
		
		self.round = function (num, places) {
			if (!places) {
				places = 2;
			}
			return parseFloat(num).toFixed(places);
		};
		
		self.getURLParameter = function(name, def) {
			if (typeof def === undefined) {
				def = "";
			}
		    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
		        results = regex.exec(location.search);
		    return results == null ? def : decodeURIComponent(results[1].replace(/\+/g, " "));
		};
		
		self.scrollToTop = function () {
			window.scrollTo(0,0);
		};
		
		self.isStringNullOrBlank = function (string) {
			return (typeof string === undefined ||
					string === null ||
					typeof string !== "string" ||
					string.length === 0 ||
					string.trim().length === 0);
		};
		
		self.formatDateTime = function (date, formatString) {	
			if (typeof formatString === "undefined") {
				formatString = "YYYY-MM-DD HH:mm:ss";
			}
			if (date && date.isValid()) {
				return date.format(formatString);
			}
			return "";
		};
		
		self.formatDuration = function (duration, formatString) {
			if (typeof formatString === "undefined") {
				formatString = "hh:mm:ss";
			}
			if (duration) {
				return duration.format(formatString);
			}
			return "";
		}
	
		
	})();
	
	return util;
	
});