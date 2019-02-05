'use strict';

angular.module('core.transaction')
	.service('TransactionService', function(BackendParamService, $http) {
		const url = BackendParamService.url;

		this.buy = (articleId) => {
			return $http.post(`${url}/api/transactions/${articleId}`);
		};
	});
