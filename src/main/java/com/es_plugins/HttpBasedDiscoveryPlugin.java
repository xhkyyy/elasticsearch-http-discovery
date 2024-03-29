package com.es_plugins;

import com.service.HttpService;
import com.service.HttpServiceImpl;
import org.elasticsearch.common.network.NetworkService;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.SeedHostsProvider;
import org.elasticsearch.plugins.DiscoveryPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.ReloadablePlugin;
import org.elasticsearch.transport.TransportService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author xhkyyy
 */
public class HttpBasedDiscoveryPlugin extends Plugin implements DiscoveryPlugin, ReloadablePlugin {

    private Settings settings;

    private final String HTTP_KEY = "http";

    public HttpBasedDiscoveryPlugin(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void reload(Settings settings) throws Exception {
        // TODO
    }

    @Override
    public List<Setting<?>> getSettings() {
        return Collections.unmodifiableList(Arrays.asList(HttpService.HTTP_URL_SETTING));
    }

    HttpService createHttpService() {
        return new HttpServiceImpl();
    }

    @Override
    public Map<String, Supplier<SeedHostsProvider>> getSeedHostProviders(TransportService transportService, NetworkService networkService) {
        return Collections.singletonMap(HTTP_KEY, () -> new HttpBasedSeedHostsProvider(settings, createHttpService()));
    }
}
