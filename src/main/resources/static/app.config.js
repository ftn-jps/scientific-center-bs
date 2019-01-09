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
				name: 'error',
				url: '/error',
				template: '<h1>Error 404</h1>'
			});

		$urlRouterProvider
			.when('', '/')
			.otherwise('/error');
	});
