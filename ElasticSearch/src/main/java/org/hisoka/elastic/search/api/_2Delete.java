package org.hisoka.elastic.search.api;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.rest.RestStatus;
import org.hisoka.elastic.search.client.ESClient;

import java.io.IOException;

/**
 * @author Hinsteny
 * @date 2017-10-10
 * @copyright: 2017 All rights reserved.
 */
public class _2Delete {

    public static void main(String[] args) {
        try {
            DeleteResponse response = doTest();
            solveResponse(response);
        } catch(ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DeleteResponse doTest() throws IOException {
        DeleteRequest request = new DeleteRequest("posts", "doc", "12");

//        request.version(2);
//        request.versionType(VersionType.EXTERNAL);
//        request.routing("routing");
//        request.parent("parent");
//        request.timeout(TimeValue.timeValueMinutes(2));
//        request.timeout("2m");
//        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
//        request.setRefreshPolicy("wait_for");

        DeleteResponse deleteResponse = ESClient.getHighLevelClient().delete(request);
        return deleteResponse;
    }

    private static void solveResponse(DeleteResponse  response) {
        if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {

        }
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        long version = response.getVersion();
        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
        System.out.println(String.format("Index: %s, type: %s, id: %s, version: %d", index, type, id, version));
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
            }
        }

    }
}
