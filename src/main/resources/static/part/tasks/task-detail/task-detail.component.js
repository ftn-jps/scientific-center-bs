'use strict';

angular.module('tasks.taskDetail')
	.component('myTaskDetail', {
		templateUrl: '/part/tasks/task-detail/task-detail.template.html',
		controller: function(ArticleSubmitService, $stateParams, $state) {
			this.taskId = $stateParams.taskId;

			ArticleSubmitService.getTask(this.taskId).then(
				(response) => {
					this.task = response.data;
				}
			);

			this.submit = () => {
				if(this.form.coauthors.length === 0)
					delete this.form.coauthors;
				ArticleSubmitService.submitTask(this.taskId, this.form).then(
					() => {
						$state.reload('tasks');
					}
				);
			};

			this.coauthorNumber = [{}];
			this.coauthorFields = [
				{ code: 'name', name: 'First name' },
				{ code: 'lastName', name: 'Last name' },
				{ code: 'email', name: 'Email' },
				{ code: 'city', name: 'City' },
				{ code: 'country', name: 'Country' }
			];
			this.form = {};
			this.form.coauthors = [];
		}
	});
