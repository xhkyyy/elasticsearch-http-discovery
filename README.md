elasticsearch-http-discovery
=============================

The elasticsearch-http-discovery plugin uses the HTTP API to identify the addresses of seed hosts.

Versions
--------

Plugin branch | ES version | Release
-----------|-----------|-----------
master | 7.3.x | [elasticsearch-http-discovery-v7.3.2](https://github.com/xhkyyy/elasticsearch-http-discovery/releases/tag/v7.3.2)
6.2.x| 6.2.x | [elasticsearch-http-discovery-v6.2.2](https://github.com/xhkyyy/elasticsearch-http-discovery/releases/tag/v6.2.2)


How To Use
--------

***installation:***

```sh
bin/elasticsearch-plugin --verbose install [url]
```

***elasticsearch.yml:***

+ [elasticsearch-6.2.x.yml](test/elasticsearch-6.2.x.yml)

+ [elasticsearch-7.3.x.yml](test/elasticsearch-7.3.x.yml)

Test
--------

simple http server:

```sh
python test/http_server_demo.py
```

Build
--------

```
./gradlew build
```


