'use strict';

angular.module('core.journal')
	.service('JournalService', function($http) {
		this.getAll = () => {
			return $http.get('/api/journals/');
		};
	});
