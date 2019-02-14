'use strict';

angular.module('journalList')
	.component('myJournalList', {
		templateUrl: '/part/journal-list/journal-list.template.html',
		controller: function(JournalService) {
			JournalService.getAll().then(
				(response) => {
					this.journals = response.data;
				}
			);
		}
	});
