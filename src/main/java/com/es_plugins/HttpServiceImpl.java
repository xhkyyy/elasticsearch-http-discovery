package com.es_plugins;

import org.elasticsearch.common.transport.TransportAddress;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HttpServiceImpl implements HttpService {
    private final Pattern compile = Pattern.compile(",");

    @Override
    public List<TransportAddress> getSeedAddresses(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return Optional.ofNullable(response.body())
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
