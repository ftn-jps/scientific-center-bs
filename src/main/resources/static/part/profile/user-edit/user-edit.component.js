'use strict';

angular.module('profile.userEdit')
	.component('myUserEdit', {
		templateUrl: '/part/profile/user-edit/user-edit.template.html',
		controller: function(AuthenticationService) {
			this.send = () => {
				if(this.user.newPassword != this.user.newPasswordAgain) {
					this.status = 'Passwords don\'t match';
					return;
				}
				AuthenticationService.changePassword(this.user)
					.then( () => {
						this.status = 'Password changed successfully';
					}, () => {
						this.status = 'Error';
					});
			};
		}
	});
