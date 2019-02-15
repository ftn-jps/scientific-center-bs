'use strict';

angular.module('core.articleSubmit')
	.service('ArticleSubmitService', function($http) {
		this.startSubmission = (journalId) => {
			return $http.post(`/api/article-submit/${journalId}`);
		};
		this.getAllTasks = () => {
			return $http.get('/api/article-submit');
		};
		this.getTask = (taskId) => {
			return $http.get(`/api/article-submit/${taskId}`);
		};
		this.submitTask = (taskId, formData) => {
			return $http.post(`/api/article-submit/task/${taskId}`, formData);
		};
	});
