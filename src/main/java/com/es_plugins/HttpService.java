package com.es_plugins;

import org.elasticsearch.common.settings.Setting;

import java.util.function.Function;

/**
 * @author xhkyyy
 */
public interface HttpService {
    Setting<String> HOST_URL_SETTING = new Setting<>("discovery.http.host_url", "",
            Function.identity(), Setting.Property.NodeScope);

}
