package com.es_plugins;

import com.service.HttpService;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.discovery.SeedHostsProvider;

import java.util.List;

/**
 * @author xhkyyy
 */
public class HttpBasedSeedHostsProvider implements SeedHostsProvider {

    private String url;

    private HttpService httpService;


    HttpBasedSeedHostsProvider(Settings settings, HttpService httpService) {
        url = HttpService.HTTP_URL_SETTING.get(settings);
        this.httpService = httpService;
    }

    @Override
    public List<TransportAddress> getSeedAddresses(HostsResolver hostsResolver) {
        return httpService.getSeedAddresses(url);
    }
}
