package org.hisoka.elastic.search.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.hisoka.elastic.search.config.ESConfig;

/**
 * elastic search client
 * @author Hinsteny
 * @date 2017-10-09
 * @copyright: 2017 All rights reserved.
 */
public class ESClient {

    private static RestClient restClient;

    private static RestHighLevelClient restHighLevelClient;

    static {
        restClient = RestClient.builder(
                new HttpHost(ESConfig.ES_SERVER_URL, ESConfig.ES_SERVER_PORT, ESConfig.ES_SERVER_TYPE)
            ).build();
        restHighLevelClient = new RestHighLevelClient(restClient);
    }

    public static RestClient getClient() {
        return restClient;
    }

    public static RestHighLevelClient getHighLevelClient() {
        return restHighLevelClient;
    }
}
