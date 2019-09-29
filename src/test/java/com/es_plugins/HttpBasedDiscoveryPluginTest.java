package com.es_plugins;

import org.elasticsearch.common.transport.TransportAddress;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HttpBasedDiscoveryPluginTest {
    @Test
    public void testReload() {
        String x = "127.0.0.1:1222,127.0.0.1:4543";
        var l = Optional.ofNullable(x)
                .stream()
                .flatMap(Pattern.compile(",")::splitAsStream)
                .filter(Predicate.not(String::isBlank))
                .map(s -> {
                    var arr = s.split(":");
                    return new TransportAddress(
                            new InetSocketAddress(
                                    arr[0], Integer.parseInt(arr[1])
                            )
                    );
                })
                .collect(Collectors.toList());

        Assert.assertEquals(2, l.size());

    }
}
