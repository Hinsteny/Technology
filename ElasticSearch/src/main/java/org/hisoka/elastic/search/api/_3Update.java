package org.hisoka.elastic.search.api;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.rest.RestStatus;
import org.hisoka.elastic.search.client.ESClient;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hinsteny
 * @date 2017-10-09
 * @copyright: 2017 All rights reserved.
 */
public class _3Update {

    public static void main(String[] args) {
        try {
            UpdateResponse response = doTest();
            solveResponse(response);
        } catch(ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static UpdateResponse doTest() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("updated", new Date());
        jsonMap.put("reason", "daily update");
        UpdateRequest request = new UpdateRequest("posts", "doc", "1").doc(jsonMap);

//        request.routing("routing");
//        request.parent("parent");
//        request.timeout(TimeValue.timeValueSeconds(1));
//        request.timeout("1s");
//        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
//        request.setRefreshPolicy("wait_for");
//        request.retryOnConflict(3);
//        request.fetchSource(true);
//        request.version(1);
//        request.versionType(VersionType.INTERNAL);
//        request.opType(DocWriteRequest.OpType.CREATE);
//        request.opType("create");
//        request.setPipeline("pipeline");

//        String[] includes = new String[]{"updated", "r*"};
//        String[] excludes = Strings.EMPTY_ARRAY;
//        request.fetchSource(new FetchSourceContext(true, includes, excludes));

        UpdateResponse response = ESClient.getHighLevelClient().update(request);
        return response;
    }

    private static void solveResponse(UpdateResponse response) {
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        long version = response.getVersion();
        System.out.println(String.format("Index: %s, type: %s, id: %s, version: %d", index, type, id, version));
        if (response.getResult() == DocWriteResponse.Result.CREATED) {

        } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {

        } else if (response.getResult() == DocWriteResponse.Result.DELETED) {

        } else if (response.getResult() == DocWriteResponse.Result.NOOP) {

        }

        GetResult result = response.getGetResult();
        if (result.isExists()) {
            String sourceAsString = result.sourceAsString();
            Map<String, Object> sourceAsMap = result.sourceAsMap();
            byte[] sourceAsBytes = result.source();
        } else {

        }
    }
}
