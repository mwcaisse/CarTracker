"use strict";

define("Service/util", [], function (text) {
	
	var util = new (function() {
		var self = this;

		self.UPDATE_INTERVAL_MS = "MS";
		self.UPDATE_INTERVAL_SECOND = "SECOND";
		self.UPDATE_INTERVAL_MINUTE = "MINUTE";
		self.UPDATE_INTERVAL_HOUR = "HOUR";
		
		/** Runs the given function every x milliseconds
		 * 
		 */
		self.runEveryMs = function(func, ms) {
			return setInterval(func, ms);
		};
		
		/** Runs the given function every x seconds
		 *  @param func The function to run
		 *  @param seconds The internval in seconds between each execution
		 *  @return The interval ID 
		 */
		self.runEverySecond = function(func, seconds) {
			return self.runEveryMs(func, seconds * 1000);
		};
		
		/** Runs the given function every x minutes
		 *  @param func The function to run
		 *  @param minutes The internal in minutes between each execution
		 *  @return The interval ID 
		 */
		self.runEveryMinute = function(func, minutes) {
			return self.runEverySecond(func, minutes * 60);
		};
		
		/** Runs the given function every x hours
		 * 	@param func The function to run
		 *  @param hours The interval in hours between each execution
		 *  @return The interval ID 
		 */
		self.runEveryHour = function(func, hours) {
			return self.runEveryMinute(func, hours * 60)
		};
		
		self.runEvery = function (func, intervalType, interval) {
			var ms = interval;
			
			switch (invervalType) {
				case self.UPDATE_INTERVAL_HOUR:
					ms *= 60; // 60 minutes in an hour
				case self.UPDATE_INTERVAL_MINUTE:
					ms *= 60; // 60 seconds in a minute
				case self.UPDATE_INTERVAL_SECOND:
					ms *= 1000; // 1000 ms in a second
			}
			
			return self.runEveryMs(func, ms);			
		}
		
	})();
	
	return util;
	
});