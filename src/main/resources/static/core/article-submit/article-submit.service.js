'use strict';

angular.module('core.articleSubmit')
	.service('ArticleSubmitService', function($http) {
		this.startSubmission = (journalId) => {
			return $http.post(`/api/article-submit/${journalId}`);
		};
	});
