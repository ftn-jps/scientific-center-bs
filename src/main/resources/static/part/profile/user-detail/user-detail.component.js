'use strict';

angular.module('profile.userDetail')
	.component('myUserDetail', {
		templateUrl: '/part/profile/user-detail/user-detail.template.html',
		controller: function($rootScope) {
			// Leaflet map
			const coordinates = $rootScope.user.location;
			let map = L.map('map').setView(coordinates.split(','), 10);
			// OpenStreetMap tiles
			L.tileLayer('https://{s}.tile.osm.org/{z}/{x}/{y}.png', {
				attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
			}).addTo(map);
			// Map marker
			L.marker(coordinates.split(',')).addTo(map);
		}
	});
