"use strict";

define("Components/Common/Pager/Pager", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 
         "AMD/text!Components/Common/Pager/Pager.html"],
	function (moment, system, util, proxy, navigation, template) {
	
	var SORT_ORDER_ASC = "ASC";
	var SORT_ORDER_DESC = "DESC";
	
	return Vue.component("app-pager", {
		data: function() {
			return {
				itemsPerPage: 15,
				currentPage: 1
			}
		},	
		computed: {
			totalPages: function () {
				return Math.ceil(this.totalItems / this.itemsPerPage);
			},
			itemStart: function () {
				return (this.currentPage - 1) * this.itemsPerPage + 1;
			},
			itemEnd: function () {
				return  Math.min(this.totalItems, this.itemStart + this.itemsPerPage - 1);
			},
			showFirstPageButton: function () {
				return this.currentPage > 1;
			},
			showLastPageButton: function () {
				return this.currentPage < this.totalPages;
			},
			pageOptions: function () {
				var pages = [];
				for (var i=1; i<= this.totalPages; i++) {
					pages.push(i);
				}
				return pages;
			},
			currentPageUserInput: {
				get: function () {
					return this.currentPage;
				},
				set: _.debounce(function (newValue) {
					if (!$.isNumeric(newValue)) {
						newValue = 1;
					}
					if (newValue > this.totalPages) {
						newValue = this.totalPages;
					}
					this.currentPage = newValue;
				}, 500)
			}
		},
		watch: {
			itemsPerPage: function () {
				this.updatePaging();
			},
			currentPage: function () {				
				this.updatePaging();
			},
			currentPaging: function (newPaging) {
				this.itemsPerPage = newPaging.itemsPerPage;
				this.currentPage = newPaging.currentPage;
			}
		},
		props: {
			itemsPerPageOptions: {
				type: Array,
				default: function() { return [5, 10, 15, 25] },
				required: false
			},
			totalItems: {
				type: Number,
				required: true
			},
			currentPaging: {
				type: Object,
				required: false
			}
		},		
		template: template,
		methods: {
			updatePaging: function () {
				var eventData = {
					itemsPerPage: this.itemsPerPage,
					currentPage: this.currentPage
				};
				this.$emit("paging:update", eventData);				
			},
			previousPage: function () {
				this.setPage(this.currentPage - 1);
			},
			nextPage: function () {
				this.setPage(this.currentPage + 1);
			},
			firstPage: function () {
				this.setPage(1);
			},
			lastPage: function () {
				this.setPage(this.totalPages);
			},
			setPage: function (newPage) {
				if (newPage < 1 || newPage > this.totalPages) {
					return;
				}
				this.currentPage = newPage;			
			}
	
		},
		created: function () {
			
		}
	});
	
});