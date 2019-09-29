package com.es_plugins;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.discovery.SeedHostsProvider;

import java.io.IOException;
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

/**
 * @author xhkyyy
 */
public class HttpBasedSeedHostsProvider implements SeedHostsProvider {

    private String url;

    private final Pattern compile = Pattern.compile(",");


    public HttpBasedSeedHostsProvider(Settings settings) {
        url = HttpService.HOST_URL_SETTING.get(settings);
    }

    @Override
    public List<TransportAddress> getSeedAddresses(HostsResolver hostsResolver) {
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
                                InetSocketAddress.createUnresolved(arr[0], Integer.parseInt(arr[1]))
                        );
                    })
                    .collect(Collectors.toList());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
