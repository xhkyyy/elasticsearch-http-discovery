package com.es_plugins;

import org.elasticsearch.common.network.NetworkService;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.SeedHostsProvider;
import org.elasticsearch.plugins.DiscoveryPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.ReloadablePlugin;
import org.elasticsearch.transport.TransportService;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author xhkyyy
 */
public class HttpBasedDiscoveryPlugin extends Plugin implements DiscoveryPlugin, ReloadablePlugin {

    private Settings settings;

    private final String httpBased = "http";

    public HttpBasedDiscoveryPlugin(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void reload(Settings settings) throws Exception {
    }

   /* @Override
    public NetworkService.CustomNameResolver getCustomNameResolver(Settings settings) {
        return null;
    }*/

    @Override
    public Map<String, Supplier<SeedHostsProvider>> getSeedHostProviders(TransportService transportService, NetworkService networkService) {
        return Collections.singletonMap(httpBased, () -> new HttpBasedSeedHostsProvider(settings));
    }

   /* @Override
    public BiConsumer<DiscoveryNode, ClusterState> getJoinValidator() {
        return null;
    }

    @Override
    public Map<String, ElectionStrategy> getElectionStrategies() {
        return null;
    }*/
}
