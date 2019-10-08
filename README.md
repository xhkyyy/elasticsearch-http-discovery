# elasticsearch-http-discovery

### How To Use

installation:

```
bin/elasticsearch-plugin --verbose install  https://github.com/xhkyyy/elasticsearch-http-discovery/releases/download/v6.2.2/elasticsearch-http-discovery-v6.2.2.zip
```

elasticsearch.yml:

```yml
discovery.zen.hosts_provider: http

# http://http_server:port
discovery.http.url:
```

### Test

simple http server:

```sh
python test/http_server_demo.py
```


### Build

```
git clone git@github.com:xhkyyy/elasticsearch-http-discovery.git
$ cd elasticsearch-http-discovery/
$ ./gradlew build
```