'use strict';

angular.module('core.authentication')
	.service('AuthenticationService', function(BackendParamService, $http) {
		const url = BackendParamService.url;

		this.register = (user) => {
			return $http.post(`${url}/api/authentication`, user);
		};
		this.logIn = (user) => {
			return $http.post(`${url}/login`, user).then(
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
			return $http.put(`${url}/api/authentication`, data);
		};
		this.resetPassword = (email) => {
			return $http.put(`${url}/api/authentication/reset/${email}`);
		};
		this.getCurrentUser = () => {
			return $http.get(`${url}/api/authentication`);
		};
	});
