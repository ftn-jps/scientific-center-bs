package ftnjps.scientificcenter.elasticsearch;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.elasticsearch.action.ingest.PutPipelineRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttachmentPipeline {

	@Autowired
	RestHighLevelClient elasticClient;

	public boolean create() {
		String source =
				"{"
				+ "\"processors\" : ["
				+   "{"
				+     "\"attachment\" : {"
				+       "\"field\" : \"pdfContent\""
				+     "}"
				+   "}"
				+ "]"
				+"}";
		PutPipelineRequest request = new PutPipelineRequest(
				"attachment",
				new BytesArray(source.getBytes(StandardCharsets.UTF_8)),
				XContentType.JSON);
		try {
			elasticClient.ingest().putPipeline(request, RequestOptions.DEFAULT);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
