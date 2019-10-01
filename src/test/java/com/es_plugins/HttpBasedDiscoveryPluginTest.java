package com.es_plugins;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.test.ESIntegTestCase;
import org.elasticsearch.transport.TransportService;
import org.junit.After;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.singletonList;
import static org.elasticsearch.discovery.DiscoveryModule.DISCOVERY_SEED_PROVIDERS_SETTING;
import static org.elasticsearch.test.hamcrest.ElasticsearchAssertions.assertNoTimeout;

@ESIntegTestCase.ClusterScope(supportsDedicatedMasters = false, numDataNodes = 0, numClientNodes = 0)
public class HttpBasedDiscoveryPluginTest extends ESIntegTestCase {

    private static final Map<String, DiscoveryNode> nodes = new ConcurrentHashMap<>();


    @After
    public void clearGceNodes() {
        nodes.clear();
    }

    @Override
    protected Settings nodeSettings(int nodeOrdinal) {
        return Settings.builder()
                .put(super.nodeSettings(nodeOrdinal))
                .put(DISCOVERY_SEED_PROVIDERS_SETTING.getKey(), "http")
                .put(HttpService.HOST_URL_SETTING.getKey(), "http://127.0.0.1:18000")
                .build();
    }

    @Override
    protected Collection<Class<? extends Plugin>> nodePlugins() {
        return singletonList(TestPlugin.class);
    }

    public void testJoin() {
        final String masterNode = internalCluster().startMasterOnlyNode();
        registerNode(masterNode);

        ClusterStateResponse clusterStateResponse = client(masterNode).admin().cluster().prepareState()
                .setMasterNodeTimeout("1s")
                .clear()
                .setNodes(true)
                .get();
        assertNotNull(clusterStateResponse.getState().nodes().getMasterNodeId());

        final String secondNode = internalCluster().startNode();
        registerNode(secondNode);
        clusterStateResponse = client(secondNode).admin().cluster().prepareState()
                .setMasterNodeTimeout("1s")
                .clear()
                .setNodes(true)
                .setLocal(true)
                .get();
        assertNotNull(clusterStateResponse.getState().nodes().getMasterNodeId());

        assertNoTimeout(client().admin().cluster().prepareHealth().setWaitForNodes(Integer.toString(2)).get());
        assertNumberOfNodes(2);

        final String thirdNode = internalCluster().startDataOnlyNode();
        registerNode(thirdNode);
        assertNoTimeout(client().admin().cluster().prepareHealth().setWaitForNodes(Integer.toString(3)).get());
        assertNumberOfNodes(3);
    }

    private static void registerNode(final String nodeName) {
        final TransportService transportService = internalCluster().getInstance(TransportService.class, nodeName);
        assertNotNull(transportService);
        final DiscoveryNode discoveryNode = transportService.getLocalNode();
        assertNotNull(discoveryNode);
        if (nodes.put(discoveryNode.getName(), discoveryNode) != null) {
            throw new IllegalArgumentException("Node [" + discoveryNode.getName() + "] cannot be registered twice");
        }
    }

    private static void assertNumberOfNodes(final int expected) {
        assertEquals(expected, client().admin().cluster().prepareNodesInfo().clear().get().getNodes().size());
    }


    public static class TestPlugin extends HttpBasedDiscoveryPlugin {
        @Override
        public HttpService createHttpService() {
            return url -> {
                List<TransportAddress> list = new ArrayList<>();
                nodes.forEach((k, v) -> {
                    list.add(v.getAddress());
                });
                return list;
            };
        }

        public TestPlugin(Settings settings) {
            super(settings);
        }
    }


}
