package org.hisoka.elastic.search.api;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.hisoka.elastic.search.client.ESClient;

import java.io.IOException;

/**
 * @author Hinsteny
 * @date 2017-10-09
 * @copyright: 2017 All rights reserved.
 */
public class _5Search {

    public static void main(String[] args) {
        try {
            SearchResponse  response = doTest();
            solveResponse(response);
        } catch(ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SearchResponse  doTest() throws IOException {
//        SearchRequest searchRequest = new SearchRequest();
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//        searchRequest.source(searchSourceBuilder);

        SearchRequest searchRequest = new SearchRequest();
//        searchRequest.types("type");
//        searchRequest.routing("routing");

        searchRequest.setBatchedReduceSize(2);

        SearchResponse response = ESClient.getHighLevelClient().search(searchRequest);
        return response;
    }

    private static void solveResponse(SearchResponse response) {
        RestStatus status = response.status();
        TimeValue took = response.getTook();
        Boolean terminatedEarly = response.isTerminatedEarly();
        boolean timedOut = response.isTimedOut();
        int totalShards = response.getTotalShards();
        int successfulShards = response.getSuccessfulShards();
        int failedShards = response.getFailedShards();
        System.err.println(String.format("status: %s, took: %s, terminatedEarly: %s, timedOut: %b, totalShards: %d, successfulShards: %d, failedShards: %d",
                status, took, terminatedEarly, timedOut, totalShards, successfulShards, failedShards));
        for (ShardSearchFailure failure : response.getShardFailures()) {
            // failures should be handled here
        }

        SearchHits hits = response.getHits();
        long totalHits = hits.getTotalHits();
        float maxScore = hits.getMaxScore();
        System.err.println(String.format("totalHits: %d, maxScore: %f", totalHits, maxScore));
        SearchHit[] searchHits = hits.getHits();
        int i = 0;
        for (SearchHit hit : searchHits) {
            // do something with the SearchHit
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            System.err.println(String.format("ID: %d, index: %s, type: %s, id: %s, score: %f, sourceAsString: %s",
                    i, index, type, id, score, sourceAsString));
            i++;
        }
    }
}
