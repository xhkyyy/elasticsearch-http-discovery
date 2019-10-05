package com.service;

import org.elasticsearch.SpecialPermission;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Setting;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.function.Function;

/**
 * @author xhkyyy
 */
public interface HttpService {

    List<DiscoveryNode> getSeedAddresses(String url);

    default <T> T doPrivileged(final PrivilegedAction<T> operation) {
        SpecialPermission.check();
        return AccessController.doPrivileged(operation);
    }

    Setting<String> HTTP_URL_SETTING = new Setting<>("discovery.http.url", "",
            Function.identity(), Setting.Property.NodeScope);

}
