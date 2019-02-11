'use strict';

angular.module('articleList')
	.component('myArticleList', {
		templateUrl: '/part/article-list/article-list.template.html',
		controller: function(ArticleService, TransactionService) {
			this.getAll = () => {
				ArticleService.getAll()
					.then((response) => {
						this.articles = response.data;
					});
			};
			this.getAll();

			this.order = null;
			this.isReverse = true;
			this.orderBy = (order) => {
				this.isReverse = (this.order === order) ? !this.isReverse : false;
				this.order = order;
			};

			this.buy = (articleId) => {
				TransactionService.buy(articleId)
					.then((result) => {
						window.location = result.data.paymentUrl;
					});
			};

			this.searchAll = () => {
				if(!this.query)
					this.getAll();
				ArticleService.searchAll(this.query)
					.then((response) => {
						this.articles = response.data;
					}, () => {
						this.articles = null;
					});
			};
			this.searchAdvanced = (advancedQuery) => {
				this.advancedQuery = advancedQuery;
			};

			this.advancedEnabled = false;
			this.advancedReset = false;
			this.resetSearch = () => {
				this.query = null;
				this.advancedQuery = null;
				this.advancedReset = !this.advancedReset;
				ArticleService.getAll()
					.then((response) => {
						this.articles = response.data;
					});
			};
		}
	});
