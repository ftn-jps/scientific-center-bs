'use strict';

angular.module('documentViewer')
	.component('myDocumentViewer', {
		templateUrl: '/part/document-viewer/document-viewer.template.html',
		controller: function(ArticleService, $stateParams) {
			this.articleId = $stateParams.articleId;
			ArticleService.getPdf(this.articleId).then(
				(result) => {
					this.pdfContent = result.data.pdfContent;
				}
			);
		}
	});
