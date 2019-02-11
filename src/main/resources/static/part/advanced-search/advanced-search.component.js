'use strict';

angular.module('advancedSearch')
	.component('myAdvancedSearch', {
		templateUrl: '/part/advanced-search/advanced-search.template.html',
		bindings: {
			reset: '<',
			onChange: '<'
		},
		controller: function() {
			this.$onChanges = (bindings) => {
				if(bindings.reset) {
					this.query = {};
				}
			};

			this.change = () => {
				this.onChange(this.query);
			};

			this.fields = [
				{ code: 'journalName', name: 'Journal Name' },
				{ code: 'title', name: 'Title' },
				{ code: 'keywords', name: 'Keywords' },
				{ code: 'articleAbstract', name: 'Abstract' },
				{ code: 'attachment', name: 'Attachment content' },
				{ code: 'fieldOfStudy', name: 'Field of study' },
			];
		}
	});
