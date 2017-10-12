package org.hisoka.elastic.search.api;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.hisoka.elastic.search.client.ESClient;

import java.io.IOException;
import java.util.Map;

/**
 * @author Hinsteny
 * @date 2017-10-09
 * @copyright: 2017 All rights reserved.
 */
public class _1Get {

    public static void main(String[] args) {
        try {
            GetResponse response = doTest();
            solveResponse(response);
        } catch(ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static GetResponse doTest() throws IOException {
        GetRequest request = new GetRequest("posts", "doc", "1");

        request.fetchSourceContext(new FetchSourceContext(true));
        String[] includes = new String[]{"message", "*Date"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        request.fetchSourceContext(fetchSourceContext);

        request.version(2);
        request.routing("routing");
        request.parent("parent");
        request.preference("preference");
        request.realtime(false);
        request.refresh(true);
        request.versionType(VersionType.EXTERNAL);
        request.storedFields("message");

        GetResponse response = ESClient.getHighLevelClient().get(request);
        return response;
    }

    private static void solveResponse(GetResponse  response) {
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        long version = response.getVersion();
        System.out.println(String.format("Index: %s, type: %s, id: %s, version: %d", index, type, id, version));
        if (response.isExists()) {
            String sourceAsString = response.getSourceAsString();
            System.err.println(sourceAsString);
            Map<String, Object> sourceAsMap = response.getSourceAsMap();
            System.err.println(sourceAsMap.toString());
            byte[] sourceAsBytes = response.getSourceAsBytes();
            System.err.println(new String(sourceAsBytes));
        } else {

        }

    }
}
