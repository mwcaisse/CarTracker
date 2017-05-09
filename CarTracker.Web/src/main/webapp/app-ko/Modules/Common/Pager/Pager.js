"use strict";

define("Modules/Common/Pager/Pager", ["moment", "Service/util", "Service/applicationProxy", "Modules/Common/Pager/PagerBinding"],
		function (moment, util, proxy) {
	
	var vm = function(options) {
		var self = this;
		
		var defaults = {
			itemsPerPageOptions: [5, 10, 15, 25],
			itemsPerPage: 15,
			fetchData: function (startAt, maxResults) {
				return Q.fcall(function() {
					return {total: 40};
				});
			}
		};		

		var ELLIPSIS = "...";
		
		var opts = $.extend({}, defaults, options);
		
		self.itemsPerPageOptions = opts.itemsPerPageOptions;
		
		self.itemsPerPage = ko.observable(opts.itemsPerPage);
		
		self.currentPage = ko.observable(1);
		
		self.totalItems = ko.observable(45);
		
		self.totalPages = ko.computed(function () {
			return Math.ceil(self.totalItems() / self.itemsPerPage());
		});
		
		self.pages = ko.computed(function () {
			var totalPages = self.totalPages();
			var currentPage = self.currentPage();
			
			var pages = [];
			
			if (totalPages > 6) {
				var maxBefore = currentPage - 1;
				var maxAfter = totalPages - currentPage;
				var maxPages = 4;
				
				var pagesBefore = Math.floor(maxPages / 2);
				var pagesAfter = Math.ceil(maxPages / 2);
				
				if (pagesAfter > maxAfter) {
					pagesBefore += (pagesAfter - maxAfter);
					pagesAfter = maxAfter;
				}
				else if (pagesBefore > maxBefore) {
					pagesAfter += (pagesBefore - maxBefore);
					pagesBefore = maxBefore;
				}	
				//push the pages into the array
				for (var i=currentPage - pagesBefore; i <=currentPage + pagesAfter; i++) {
					pages.push(i);
				};				                                   
				
			}
			else {
				for (var i = 0; i < totalPages; i++) {
					pages.push(i+1);
				}
			}
			
			return pages;
		});
		
		self.itemStart = ko.computed(function () {
			return (self.currentPage() - 1) * self.itemsPerPage() + 1;
		});
		
		self.itemEnd = ko.computed(function () {
			return Math.min(self.totalItems(), self.itemStart() + self.itemsPerPage() - 1);
		});
		
		self.showFirstPageButton = ko.computed(function () {
			return $.inArray(1, self.pages()) === -1;
		});
		
		self.showLastPageButton = ko.computed(function () {
			return $.inArray(self.totalPages(), self.pages()) === -1;
		});
		
		self.itemsPerPage.subscribe(function (newValue) {
			var currentPage = self.currentPage();
			self.currentPage(1);
			if (currentPage === 1) {
				self.fetchData();
			}
		});
		
		self.currentPage.subscribe(function (newValue) {
			self.fetchData();
		});
		
		self.changePage = function (pageNum) {
			if (pageNum === undefined) {
				pageNum = this;
			}
			if (pageNum === ELLIPSIS) {
				return; //id it is the ELLIPSIS then do nothing
			}
			self.currentPage(pageNum);
		};
		
		self.firstPage = function () {
			self.currentPage(1);
		};
		
		self.lastPage = function () {
			self.currentPage(self.totalPages());
		};
		
		self.fetchData = function () {
			var itemsPerPage = self.itemsPerPage();
			var startAt = (self.currentPage() - 1) * itemsPerPage;
			opts.fetchData(startAt, itemsPerPage).then(function (data) {
				self.totalItems(data.total);
			});
		};
		
	};
	
	return vm;
	
});