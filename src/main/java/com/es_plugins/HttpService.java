package com.es_plugins;

import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.transport.TransportAddress;

import java.util.List;
import java.util.function.Function;

/**
 * @author xhkyyy
 */
public interface HttpService {

    List<TransportAddress> getSeedAddresses(String url);

    Setting<String> HOST_URL_SETTING = new Setting<>("discovery.http.host_url", "",
            Function.identity(), Setting.Property.NodeScope);

}
