'use strict';

angular.module('tasks.taskList')
	.component('myTaskList', {
		templateUrl: '/part/tasks/task-list/task-list.template.html',
		controller: function(ArticleSubmitService) {
			ArticleSubmitService.getAllTasks().then(
				(response) => {
					this.tasks = response.data;
				}
			);
		}
	});
