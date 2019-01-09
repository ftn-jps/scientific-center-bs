'use strict';

angular.module('app')
	.config(function($stateProvider, $urlRouterProvider) {
		$stateProvider
			.state({
				name: 'home',
				redirectTo: 'home.link1',
				url: '/',
				component: 'myHome'
			})
			.state({
				name: 'home.link1',
				url: '^/link-1',
				template: '<h1>Link 1</h1>'
			})
			.state({
				name: 'home.link2',
				url: '^/link2',
				template: '<h1>Link 2</h1>'
			})
			.state({
				name: 'home.link3',
				url: '^/link-3',
				template: '<h1>Link 3</h1>'
			})
			.state({
				name: 'linkA',
				url: '/link-a',
				template: '<h1>Link A</h1>'
			})
			.state({
				name: 'linkB',
				url: '/link-b',
				template: '<h1>Link B</h1>'
			})
			.state({
				name: 'linkC',
				url: '/link-c',
				template: '<h1>Link C</h1>'
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
