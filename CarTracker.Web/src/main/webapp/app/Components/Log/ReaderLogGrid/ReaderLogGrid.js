"use strict";

define("Components/Log/ReaderLogGrid/ReaderLogGrid", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 	
		 "Components/Common/Pager/PagedGridMixin",
		 "AMD/text!Components/Log/ReaderLogGrid/ReaderLogGrid.html",     
         "Components/Common/ColumnHeader/ColumnHeader",
         "Components/Common/Pager/Pager"],
	function (moment, system, util, proxy, navigation, pagedGridMixin, template) {	
	
	
	return Vue.component("app-reader-log-grid", {
		mixins: [pagedGridMixin],
		data: function() {
			return {
				logs: [],
				currentSort: { propertyId: "DATE", ascending: false},			
			}
		},			
		template: template,
		methods: {
			fetch: function () {							
				proxy.readerLog.getAllPaged(this.startAt, this.take, this.currentSort).then(function (data) {					
					this.update(data);
				}.bind(this),
				function (error) {
					alert("error fetching reader log!");
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
			}		
		},
		created: function () {
			this.fetch();
		}
	});
	
});