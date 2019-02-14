'use strict';

angular.module('journalList')
	.component('myJournalList', {
		templateUrl: '/part/journal-list/journal-list.template.html',
		controller: function(JournalService, ArticleSubmitService, $state) {
			JournalService.getAll().then(
				(response) => {
					this.journals = response.data;
				}
			);

			this.submit = (journalId) => {
				ArticleSubmitService.startSubmission(journalId).then(
					() => {
						$state.go('tasks');
					}
				);
			};
		}
	});
