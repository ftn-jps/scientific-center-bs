'use strict';

angular.module('header')
	.component('myHeader', {
		templateUrl: '/part/header/header.template.html',
		controller: function(AuthenticationService, $rootScope, $state) {
			this.logOut = () => {
				AuthenticationService.logOut();
				delete $rootScope.user;
				$state.go('home');
			};
		}
	});
