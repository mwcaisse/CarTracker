"use strict";

var googleMapsApiKey = $("#googleMapsApiKey").val();

require.config({
	
	baseUrl: "/cartracker/app/",	
	paths: {
		"q": "../js/q",
		"moment": "../js/moment",
		"moment-duration-format": "../js/moment-duration-format"
	},
	googlemaps: {
		params: {
			key: googleMapsApiKey
		}
	}
});

//Highcharts options
Highcharts.setOptions({
	global: {
		useUTC: false
	}
});