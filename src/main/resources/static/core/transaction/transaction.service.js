'use strict';

angular.module('core.transaction')
	.service('TransactionService', function($http) {
		this.buy = (articleId) => {
			return $http.post(`/api/transactions/${articleId}`);
		};
	});
