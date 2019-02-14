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
				component: 'myArticleList'
			})
			.state({
				name: 'document',
				url: '/document/{articleId:\\d*}',
				component: 'myDocumentViewer'
			})
			.state({
				name: 'journals',
				url: '/journals',
				component: 'myJournalList'
			})
			.state({
				name: 'tasks',
				url: '/tasks',
				component: 'myTasks'
			})
			.state({
				name: 'tasks.task',
				url: '/{taskId}',
				component: 'myTaskDetail'
			})
			.state({
				name: 'profile',
				url: '/profile',
				component: 'myProfile'
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
