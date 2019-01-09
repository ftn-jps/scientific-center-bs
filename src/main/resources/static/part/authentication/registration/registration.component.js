'use strict';

angular.module('authentication.registration')
	.component('myRegistration', {
		templateUrl: '/part/authentication/registration/registration.template.html',
		controller: function(AuthenticationService) {
			this.send = () => {
				if(this.user.password !== this.user.passwordAgain)
				{
					this.status = 'Passwords don\'t match';
					return;
				}
				AuthenticationService.register(this.user).then(
					() => {
						this.status = 'Registered successfully.';
					},
					() => {
						this.status = 'Error';
					});
			};
		}
	});
