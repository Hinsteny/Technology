package org.hisoka.elastic.search.api;

import org.elasticsearch.Build;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.Version;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.cluster.ClusterName;
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
public class _7Info {

    public static void main(String[] args) {
        try {
            MainResponse response = doTest();
            solveResponse(response);
        } catch(ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static MainResponse doTest() throws IOException {

        MainResponse response = ESClient.getHighLevelClient().info();
        return response;
    }

    private static void solveResponse(MainResponse  response) {
        ClusterName clusterName = response.getClusterName();
        String clusterUuid = response.getClusterUuid();
        String nodeName = response.getNodeName();
        Version version = response.getVersion();
        Build build = response.getBuild();
        System.err.println(String.format("clusterName: %s, clusterUuid: %s, nodeName: %s, version: %s, build: %s",
                clusterName, clusterUuid, nodeName, version, build));
    }
}
