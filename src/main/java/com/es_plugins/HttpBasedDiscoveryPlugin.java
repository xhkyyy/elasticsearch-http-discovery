package com.es_plugins;

import org.elasticsearch.common.network.NetworkService;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.SeedHostsProvider;
import org.elasticsearch.plugins.DiscoveryPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.ReloadablePlugin;
import org.elasticsearch.transport.TransportService;

import java.util.*;
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
        // TODO
    }

   /* @Override
    public NetworkService.CustomNameResolver getCustomNameResolver(Settings settings) {
        return null;
    }*/

    @Override
    public List<Setting<?>> getSettings() {
        List<Setting<?>> settings = new ArrayList<>(
                Arrays.asList(HttpService.HOST_URL_SETTING)
        );
        return Collections.unmodifiableList(settings);
    }

    public HttpService createHttpService() {
        return new HttpServiceImpl();
    }

    @Override
    public Map<String, Supplier<SeedHostsProvider>> getSeedHostProviders(TransportService transportService, NetworkService networkService) {
        System.out.println("-=-=-=-=-=-=-=-=-=-=");
        System.out.println("-=-=-=-=-=-=-=-=-=-=");
        System.out.println("-=-=-=-=getSeedHostProviders(1)-=-=-=-=-=-=");
        System.out.println("-=-=-=-=-=-=-=-=-=-=");
        System.out.println("-=-=-=-=-=-=-=-=-=-=");
        return Collections.singletonMap(httpBased, () -> new HttpBasedSeedHostsProvider(settings, createHttpService()));
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
