'use strict';

angular.module('core.authentication')
	.service('AuthenticationService', function($http) {
		this.register = (user) => {
			return $http.post('/api/authentication', user);
		};
		this.logIn = (user) => {
			return $http.post('/login', user).then(
				(response) => {
					localStorage.setItem('token', response.data);
					$http.defaults.headers.common.Authorization = 'Bearer ' + response.data;
				});
		};
		this.logOut = () => {
			localStorage.removeItem('token');
			delete $http.defaults.headers.common.Authorization;
		};
		this.changePassword = (data) => {
			return $http.put('/api/authentication', data);
		};
		this.resetPassword = (email) => {
			return $http.put(`/api/authentication/reset/${email}`);
		};
		this.getCurrentUser = () => {
			return $http.get('/api/authentication');
		};
	});
