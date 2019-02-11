'use strict';

angular.module('core.article')
	.service('ArticleService', function($http) {
		this.getAll = () => {
			return $http.get('/api/articles/');
		};

		this.getPdf = (articleId) => {
			return $http.get(`/api/articles/${articleId}`);
		};

		this.searchAll = (query) => {
			return $http.get(`/api/articles/search/${query}`);
		};
	});
