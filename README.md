# es-http-discovery-plugin


```
bin/elasticsearch-plugin --verbose install  http://172.20.10.2:8000/es-http-discovery-plugin-1.0-SNAPSHOT.zip

discovery.seed_providers: http
discovery.http.url: http://172.20.10.2:18000
cluster.initial_master_nodes: ["node-11"]
```



#### TODO

+ httpclient timeout

+ https