# spring-es-react-blog

### Performance: MySQL vs Elastic search
![Jun-11-2023 22-10-28](https://github.com/jrhe123/spring-es-react-blog/assets/17329299/a2164bc9-4945-4438-88c0-15839eae2dee)


### Postman collection (attached file)

- Auth -> Basic Auth -> username & password: elastic & changeme
- get all index
- create index
- delete index
- add data to index
- search data

### ES Java Client

- https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/index.html
- https://www.pixeltrice.com/spring-boot-elasticsearch-crud-example/

### Elastic search & Kibana

- ES

  - https://hub.docker.com/_/elasticsearch
  - https://www.elastic.co/guide/en/welcome-to-elastic/current/getting-started-general-purpose.html
  - version: 8.8.0

- Kibana

  - https://hub.docker.com/_/kibana
  - version: 8.8.0

- logstash

  - https://hub.docker.com/_/logstash
  - version: 8.8.0

### Logstash migrate data from mysql to es

- https://github.com/dimMaryanto93/docker-logstash-input-jdbc
- https://github.com/elkninja/elastic-stack-docker-part-one/tree/main
- cmd: logstash -f logstash.conf

### Steps to setup environment

#### -1. Docker compose

- docker-compose up
- docker volume prune
- check your es
  - https://localhost:9200/\_all

#### 0. Create docker bridge network (we want to connect es & kibana within same network)

- docker network ls
- docker network create elastic
- docker inspect elastic

#### 1. Run elastic

- docker run --name elasticdemo --net elastic -p 9200:9200 -p 9300:9300 -t docker.elastic.co/elasticsearch/elasticsearch:8.8.0

#### 2. Reset elastic (root user) password, or keep it default

- default username: "elastic"
- docker exec -it elasticdemo /usr/share/elasticsearch/bin/elasticsearch-reset-password -u elastic

#### 3. Copy the certificate from docker to your path

- docker cp elasticdemo:/usr/share/elasticsearch/config/certs/http_ca.crt .

#### 4. Curl with your certificate (testing connection), enter the above password

- curl --cacert http_ca.crt -u elastic https://localhost:9200

```
{
  "name" : "5a51e467ac05",
  "cluster_name" : "docker-cluster",
  "cluster_uuid" : "1piXHVrzTcqrxQQc9NJSlQ",
  "version" : {
    "number" : "8.8.0",
    "build_flavor" : "default",
    "build_type" : "docker",
    "build_hash" : "c01029875a091076ed42cdb3a41c10b1a9a5a20f",
    "build_date" : "2023-05-23T17:16:07.179039820Z",
    "build_snapshot" : false,
    "lucene_version" : "9.6.0",
    "minimum_wire_compatibility_version" : "7.17.0",
    "minimum_index_compatibility_version" : "7.0.0"
  },
  "tagline" : "You Know, for Search"
}
```

#### 5. Reset elastic enrollment token for kibana (30 mins valid)

- docker exec -it elasticdemo /usr/share/elasticsearch/bin/elasticsearch-create-enrollment-token -s kibana

#### 6. Run kibana

- docker run --name kibanademo --net elastic -p 5601:5601 docker.elastic.co/kibana/kibana:8.8.0
- open your browser & use the above enrollment token to finish the configuration: http://0.0.0.0:5601/?code=xxxxxx

#### 7. Integrations (check them from your sidebar)

- Dev Tools
  - https://www.elastic.co/guide/en/welcome-to-elastic/current/getting-started-general-purpose.html
  - copy the local link into web console for demo

```
 POST /customer/_doc/1
{
  "name": "John Doe"
}
```

```
 GET /customer/_doc/1
```

```
 POST bank/_bulk
{ "create":{ } }
{ "account_number":1,"balance":39225,"firstname":"Amber","lastname":"Duke","age":32,"gender":"M","address":"880 Holmes Lane","employer":"Pyrami","email":"amberduke@pyrami.com","city":"Brogan","state":"IL" }
{ "create":{ } }
{ "account_number":6,"balance":5686,"firstname":"Hattie","lastname":"Bond","age":36,"gender":"M","address":"671 Bristol Street","employer":"Netagy","email":"hattiebond@netagy.com","city":"Dante","state":"TN" }
```

```
 GET /bank/_search
{
  "query": { "match": { "address": "mill lane" } }
}
```

```
 GET /bank/_search
{
  "query": {
    "bool": {
      "must": [
        { "match": { "age": "39" } }
      ],
      "must_not": [
        { "match": { "state": "PA" } }
      ]
    }
  }
}
```

- Integrations
- Elasticsearch & choose your client (Java, etc..)

<img width="1184" alt="Screenshot 2023-06-10 at 9 44 14 AM" src="https://github.com/jrhe123/spring-es-react-blog/assets/17329299/03749341-ef8f-4e2f-8cbe-a14c4d986032">
<img width="1184" alt="Screenshot 2023-06-10 at 9 46 31 AM" src="https://github.com/jrhe123/spring-es-react-blog/assets/17329299/99465a34-e655-4b0d-a534-f70347f1139f">
