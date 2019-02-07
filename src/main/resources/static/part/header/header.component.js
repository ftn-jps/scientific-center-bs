'use strict';

angular.module('header')
	.component('myHeader', {
		templateUrl: '/part/header/header.template.html',
		controller: function(AuthenticationService, $rootScope, $state, TransactionService) {
			this.logOut = () => {
				AuthenticationService.logOut();
				delete $rootScope.user;
				$state.go('home');
			};


			this.subscribe = (journalId) => {
				TransactionService.subscribe(journalId)
					.then((result) => {
						window.location = result.data.paymentUrl;
					});
			};
		}
	});
