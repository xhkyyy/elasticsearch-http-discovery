package com.es_plugins;

import org.elasticsearch.SpecialPermission;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.transport.TransportAddress;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.function.Function;

/**
 * @author xhkyyy
 */
public interface HttpService {

    List<TransportAddress> getSeedAddresses(String url);

    default <T> T doPrivileged(final PrivilegedAction<T> operation) {
        SpecialPermission.check();
        return AccessController.doPrivileged(operation);
    }

    Setting<String> HOST_URL_SETTING = new Setting<>("discovery.http.host_url", "",
            Function.identity(), Setting.Property.NodeScope);

}
