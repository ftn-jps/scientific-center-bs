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
		}
	});
