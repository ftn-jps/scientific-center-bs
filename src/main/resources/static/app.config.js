'use strict';

angular.module('app')
	.config(function($stateProvider, $urlRouterProvider) {
		$stateProvider
			.state({
				name: 'home',
				redirectTo: 'articles',
				url: '/',
			})
			.state({
				name: 'articles',
				url: '/articles',
				template: '<h1>Articles</h1>'
			})
			.state({
				name: 'submit',
				url: '/submit',
				template: '<h1>Submit an article</h1>'
			})
			.state({
				name: 'authentication',
				url: '/authentication',
				component: 'myAuthentication'
			})
			.state({
				name: 'error',
				url: '/error',
				template: '<h1>Error 404</h1>'
			});

		$urlRouterProvider
			.when('', '/')
			.otherwise('/error');
	})
	.run(function(AuthenticationService, $rootScope, $http) {
		const token = localStorage.getItem('token');
		if(token) {
			$http.defaults.headers.common.Authorization = 'Bearer ' + token;
			AuthenticationService.getCurrentUser().then(
				(response) => {
					$rootScope.user = response.data;
				});
		}
	});
