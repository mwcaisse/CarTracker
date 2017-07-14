"use strict";

define("Components/Log/ReaderLogGrid/ReaderLogGrid", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 	
		 "AMD/text!Components/Log/ReaderLogGrid/ReaderLogGrid.html",     
         "Components/Common/ColumnHeader/ColumnHeader",
         "Components/Common/Pager/Pager"],
	function (moment, system, util, proxy, navigation, template) {	
	
	
	return Vue.component("app-reader-log-grid", {
	
		data: function() {
			return {
				logs: [],
				currentSort: { propertyId: "DATE", ascending: false},
				currentPaging: {
					itemsPerPage: 15,
					currentPage: 1
				},
				totalItems: 1
			}
		},			
		template: template,
		methods: {
			fetch: function () {
				var take = this.currentPaging.itemsPerPage;
				var startAt = (this.currentPaging.currentPage - 1) * take;				
				proxy.readerLog.getAllPaged(startAt, take, this.currentSort).then(function (data) {	
					console.log("we be updating logs?");
					this.update(data);
				}.bind(this),
				function (error) {
					alert("error fetching car!");
				})
			},		
			update: function (data) {			
				this.logs = $.map(data.data, function (elm, ind) {
					return this.augmentLog(elm);				
				}.bind(this));
				this.totalItems = data.total;
			},	
			augmentLog: function (log) {	
				log.date = moment(log.date);
				return log;
			},
			refresh: function () {
				this.fetch();
			},
			sortUpdated: function (newSort) {
				this.currentSort = newSort;	
				this.refresh();
			},
			pagingUpdated: function (newPaging) {
				//only update the paging if it is different than the one we currently have
				if (JSON.stringify(newPaging) !== JSON.stringify(this.currentPaging)) {
					this.currentPaging = newPaging;
					this.refresh();
				}
			},
			sortCleared: function () {
				this.currentSort = null;
				this.refresh();
			}
		},
		created: function () {
			this.fetch();
		}
	});
	
});