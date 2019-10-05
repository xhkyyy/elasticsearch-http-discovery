package com.es_plugins;

import com.service.HttpService;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.zen.UnicastHostsProvider;

import java.util.List;

/**
 * @author xhkyyy
 */
public class HttpBasedSeedHostsProvider extends AbstractComponent implements UnicastHostsProvider {

    private String url;

    private HttpService httpService;


    HttpBasedSeedHostsProvider(Settings settings, HttpService httpService) {
        super(settings);
        url = HttpService.HTTP_URL_SETTING.get(settings);
        this.httpService = httpService;
    }


    @Override
    public List<DiscoveryNode> buildDynamicNodes() {
        return httpService.getSeedAddresses(url);
    }
}
