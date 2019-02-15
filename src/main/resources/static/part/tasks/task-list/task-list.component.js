'use strict';

angular.module('tasks.taskList')
	.component('myTaskList', {
		templateUrl: '/part/tasks/task-list/task-list.template.html',
		controller: function(ArticleSubmitService, $state) {
			ArticleSubmitService.getAllTasks().then(
				(response) => {
					this.tasks = response.data;
					if(this.tasks && this.tasks.length)
						$state.go('tasks.task', {taskId: this.tasks[0].id});
					else
						$state.go('home');
				}
			);
		}
	});
