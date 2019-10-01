package com.es_plugins;

import org.apache.http.client.fluent.Request;
import org.elasticsearch.common.transport.TransportAddress;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HttpServiceImpl implements HttpService {
    private final Pattern compile = Pattern.compile(",");

    @Override
    public List<TransportAddress> getSeedAddresses(String url) {
        try {

            final var value = doPrivileged(() -> {
                try {
                    return Request.Get(url).execute().returnContent().asString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "";
            });

            return Optional.ofNullable(value)
                    .stream()
                    .flatMap(compile::splitAsStream)
                    .filter(Predicate.not(String::isBlank))
                    .map(s -> {
                        String[] arr = s.split(":");
                        return new TransportAddress(
                                new InetSocketAddress(arr[0].trim(), Integer.parseInt(arr[1].trim()))
                        );
                    })
                    .collect(Collectors.toList());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
