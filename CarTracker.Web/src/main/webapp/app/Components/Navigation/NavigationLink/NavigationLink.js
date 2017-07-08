"use strict";

define("Components/Navigation/NavigationLink/NavigationLink", 
		["moment", "Service/system", "Service/util", "Service/navigation", 
         "AMD/text!Components/Navigation/NavigationLink/NavigationLink.html"],
	function (moment, system, util, navigation, template) {
	
	return Vue.component("app-nav-link", {
		data: function() {
			var vm = $.extend({}, {
				id: "", 
				name: "", 
				link: "#",
				subLinks: [],
				active: false
			}, this.navLink);	
			
			return vm;
		},	
		props: {
			navLink: {
				type: Object,
				required: true		
			},
			right: {
				type: Boolean,
				default: false
			}
		},
		computed: {
			hasSubLinks: function () {
				return this.subLinks.length > 0;
			}
		},
		template: template,
		methods: {
			addSubLink: function (link) {
				this.subLinks.push(link);
			}
		},
		created: function () {
			var vm = this;
			system.events.on(navigation.EVENT_NAVIGATION_ACTIVE_CHANGED, function (event, data) {
				vm.active = (vm.id === data.id);
			});
		}
	});
	
});