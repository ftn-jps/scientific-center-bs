'use strict';

angular.module('articleList')
	.component('myArticleList', {
		templateUrl: '/part/article-list/article-list.template.html',
		controller: function(ArticleService) {
			ArticleService.getAll()
				.then((response) => {
					this.articles = response.data;
				});

			this.order = null;
			this.isReverse = true;
			this.orderBy = (order) => {
				this.isReverse = (this.order === order) ? !this.isReverse : false;
				this.order = order;
			};
		}
	});
