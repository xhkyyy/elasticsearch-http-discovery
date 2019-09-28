package com.es_plugins;

import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.coordination.ElectionStrategy;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.network.NetworkService;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.SeedHostsProvider;
import org.elasticsearch.plugins.DiscoveryPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.ReloadablePlugin;
import org.elasticsearch.transport.TransportService;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class HttpBasedDiscoveryPlugin extends Plugin implements DiscoveryPlugin, ReloadablePlugin {

    @Override
    public void reload(Settings settings) throws Exception {

    }

    @Override
    public NetworkService.CustomNameResolver getCustomNameResolver(Settings settings) {
        return null;
    }

    @Override
    public Map<String, Supplier<SeedHostsProvider>> getSeedHostProviders(TransportService transportService, NetworkService networkService) {
        return null;
    }

    @Override
    public BiConsumer<DiscoveryNode, ClusterState> getJoinValidator() {
        return null;
    }

    @Override
    public Map<String, ElectionStrategy> getElectionStrategies() {
        return null;
    }
}
