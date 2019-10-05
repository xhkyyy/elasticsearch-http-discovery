package com.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Request;
import org.elasticsearch.Version;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.transport.TransportAddress;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;

/**
 * @author xhkyyy
 */
public class HttpServiceImpl implements HttpService {

    private final Pattern compile = Pattern.compile(",");

    @Override
    public List<DiscoveryNode> getSeedAddresses(String url) {
        AtomicInteger index = new AtomicInteger();
        String value = doPrivileged(new PrivilegedAction<String>() {
            @Override
            public String run() {
                try {
                    return Request.Get(url).execute().returnContent().asString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "";
            }
        });

        return Stream.of(value)
                .flatMap(compile::splitAsStream)
                .filter(StringUtils::isNotBlank)
                .map(s -> buildDiscoveryNode(index, s))
                .collect(Collectors.toList());
    }

    private DiscoveryNode buildDiscoveryNode(AtomicInteger index, String bindAddr) {
        String[] arr = bindAddr.split(":");
        TransportAddress address = new TransportAddress(
                new InetSocketAddress(arr[0].trim(), Integer.parseInt(arr[1].trim()))
        );
        return new DiscoveryNode(
                "http-provider-" + (index.getAndIncrement()),
                address,
                emptyMap(),
                emptySet(),
                Version.CURRENT.minimumCompatibilityVersion()
        );
    }
}
