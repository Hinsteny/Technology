package org.hisoka.elastic.search.api;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
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
public class _0Index {

    public static void main(String[] args) {
        try {
            IndexResponse response = doTest();
            solveResponse(response);
        } catch(ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static IndexResponse doTest() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest request = new IndexRequest("hisoka", "doc", "1").source(jsonMap);

//        request.routing("routing");
//        request.parent("parent");
//        request.timeout(TimeValue.timeValueSeconds(1));
//        request.timeout("1s");
//        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
//        request.setRefreshPolicy("wait_for");
//        request.version(1);
//        request.versionType(VersionType.INTERNAL);
//        request.opType(DocWriteRequest.OpType.CREATE);
//        request.opType("create");
//        request.setPipeline("pipeline");

        IndexResponse response = ESClient.getHighLevelClient().index(request);
        return response;
    }

    private static void solveResponse(IndexResponse indexResponse) {
        String index = indexResponse.getIndex();
        String type = indexResponse.getType();
        String id = indexResponse.getId();
        long version = indexResponse.getVersion();
        System.out.println(String.format("Index: %s, type: %s, id: %s, version: %d", index, type, id, version));
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.err.println("The document was created for the first time!");
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.err.println("The document was rewritten as it was already existing!");
        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.err.println("The number of successful shards is less than total shards!");
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
            }
        }

    }
}
