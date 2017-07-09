"use strict";

define("Components/Navigation/NavigationLink/NavigationLink", 
		["moment", "Service/system", "Service/util", "Service/navigation", 
         "AMD/text!Components/Navigation/NavigationLink/NavigationLink.html"],
	function (moment, system, util, navigation, template) {
	
	return Vue.component("app-nav-link", {
		data: function() {
			return {
				active: false
			};
		},	
		props: {
			linkId: {
				type: String,
				required: true
			},
			name: {
				type: String,
				required: true
			},
			link: {
				type: String,
				required: false,
				default: function () {
					return "#";
				}
			},
			subLinks: {
				type: Array,
				required: false,
				default: function () {
					return [];
				}
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
			system.events.on(navigation.EVENT_NAVIGATION_ACTIVE_CHANGED, function (event, data) {
				this.active = (this.linkId === data.id);
			}.bind(this));
		}
	});
	
});