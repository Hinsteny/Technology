package org.hisoka.elastic.search.api;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.*;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.hisoka.elastic.search.client.ESClient;

import java.io.IOException;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * @author Hinsteny
 * @date 2017-10-09
 * @copyright: 2017 All rights reserved.
 */
public class _6Scroll {

    private static Scroll scroll;

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
        SearchRequest searchRequest = new SearchRequest("posts", "test", "hisoka");
        scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQuery("user", "kimchy"));
        searchRequest.source(searchSourceBuilder);

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

        String scrollId = response.getScrollId();
        SearchHit[] searchHits = printlnHits(response);
        SearchResponse searchResponse = null;
        while (searchHits != null && searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            try {
                searchResponse = ESClient.getHighLevelClient().searchScroll(scrollRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            scrollId = searchResponse.getScrollId();
            System.err.println("ScrollId: " + scrollId);
            searchHits = printlnHits(searchResponse);
        }
        clearScroll(scrollId);
    }

    private static SearchHit[] printlnHits(SearchResponse searchResponse) {
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        SearchHit item;
        for (int i = 0; i < searchHits.length; i++) {
            item = searchHits[i];
            System.err.println(String.format("ID: %s, Index: %s, score: %f, type: %s, source: %s",
                    item.getId(), item.getIndex(), item.getScore(), item.getType(), item.getSourceAsString()));
        }
        return searchHits;
    }

    private static void clearScroll(String scrollId) {
        ClearScrollRequest request = new ClearScrollRequest();
        request.addScrollId(scrollId);
//        request.setScrollIds(scrollIds);
        ESClient.getHighLevelClient().clearScrollAsync(request, new ActionListener<ClearScrollResponse>() {
            @Override
            public void onResponse(ClearScrollResponse clearScrollResponse) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
