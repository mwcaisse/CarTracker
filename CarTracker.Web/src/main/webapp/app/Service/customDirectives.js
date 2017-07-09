"use strict";

define("Service/customDirectives", [], function () {
	
	Vue.directive("tooltip", {
		bind: function (el, binding) {
			$(el).tooltip(binding.value);
		}
	});
	
	return {};	
});