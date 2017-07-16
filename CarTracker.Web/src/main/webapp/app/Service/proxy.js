"use strict";

define("Service/proxy", [], function () {
	
	var proxy = new (function() {
		var self = this;

		self.baseUrl = "/cartracker/api/";
		
		self.ajax = function(options) {
			var def = Q.defer();
			var defaults = {
				async: true,
				contentType: "application/json",
				data: null,
				method: "GET",
				success: function(data, textStatus, jqXHR) {
					self.successHandler(def, data);
				},
				error: function(jqXHR, textStatus, error) {
					self.errorHandler(def, textStatus, error)
				}				
			};
			var ajaxOptions =$.extend(defaults, options);
			
			$.ajax(ajaxOptions);
			return def.promise;
		};
		
		self.successHandler = function(deferred, data) {
			if (data && data.valid) {
				deferred.resolve(data.data);
			}
			else if (data) {
				deferred.reject(data.errorMessage);		
			}
			else {
				deferred.reject("Error reading the results from the server!");
			}
		};
		
		self.errorHandler = function(deferred, textStatus, error) {
			deferred.reject("An error occured when trying to communicate with the server!");
		};
		
		self.getAsbsolute = function(url) {
			return self.ajax({
				url: url,
				method: "GET"
			});
		};
		
		self.get = function(relativeUrl) {
			return self.getAsbsolute(self.baseUrl + relativeUrl);
		};
		
		self.getPaged = function (relativeUrl, startAt, maxResults, sort) {
			var sortString = "";
			if (sort) {
				sortString = "&propertyId=" + sort.propertyId +"&ascending=" + (sort.ascending ? "true" : "false");
			}
			return self.get(relativeUrl + "?startAt=" + startAt + "&maxResults=" + maxResults + sortString);
		};
		
		self.postAbsolute = function(url, body) {
			return self.ajax({
				url: url,
				data: JSON.stringify(body),	
				method: "POST"
			});
		};	
		
		self.post = function(relativeUrl, body) {
			return self.postAbsolute(self.baseUrl + relativeUrl, body);
		};
		
		self.putAbsolute = function (url, body) {
			return self.ajax({
				url: url,
				data: JSON.stringify(body),		
				method: "PUT"
			});
		};
		
		self.put = function(relativeUrl, body) {
			return self.putAbsolute(self.baseUrl + relativeUrl, body);
		};
		
		
	})();
	
	return proxy;
	
});