"use strict";

define("Components/Common/ColumnHeader/ColumnHeader", 
		["moment", "Service/system", "Service/util", "Service/applicationProxy", "Service/navigation", 
         "AMD/text!Components/Common/ColumnHeader/ColumnHeader.html"],
	function (moment, system, util, proxy, navigation, template) {
	
	var SORT_ORDER_ASC = "ASC";
	var SORT_ORDER_DESC = "DESC";
	
	return Vue.component("app-column-header", {
		data: function() {
			return {
			
			}
		},	
		props: {
			columnId: {
				type: String,
				required: true
			},
			columnName: {
				type: String,
				required: true
			},
			enableSort: {
				type: Boolean,
				default: true
			}
		},
		template: template,
		methods: {
			softAscending: function () {
				
			},
			sortDescending: function() {
				
			},
			clearSort: function () {
				
			}
		},
		created: function () {
			
		}
	});
	
});