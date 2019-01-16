'use strict';

angular.module('core.article')
	.service('ArticleService', function(BackendParamService, $http) {
		const url = BackendParamService.url;

		this.getAll = () => {
			return $http.get(`${url}/api/articles/`);
		};
	});
