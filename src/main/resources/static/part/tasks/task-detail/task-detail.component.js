'use strict';

angular.module('tasks.taskDetail')
	.component('myTaskDetail', {
		templateUrl: '/part/tasks/task-detail/task-detail.template.html',
		controller: function(ArticleSubmitService, $stateParams) {
			this.taskId = $stateParams.taskId;
		}
	});
