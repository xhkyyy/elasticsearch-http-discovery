# elasticsearch-http-discovery

### How To Use

installation:

```
bin/elasticsearch-plugin --verbose install  https://github.com/xhkyyy/elasticsearch-http-discovery/releases/download/v7.3.2/elasticsearch-http-discovery-v7.3.2.zip
```

elasticsearch.yml:

```yml
discovery.seed_providers: http

# http://http_server:port
discovery.http.url:

# the initial set of master-eligible nodes
cluster.initial_master_nodes:
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