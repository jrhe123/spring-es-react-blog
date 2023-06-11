FROM docker.elastic.co/logstash/logstash:8.8.0

# install dependency
RUN /usr/share/logstash/bin/logstash-plugin install logstash-integration-jdbc
RUN /usr/share/logstash/bin/logstash-plugin install logstash-filter-aggregate
RUN /usr/share/logstash/bin/logstash-plugin install logstash-filter-mutate

# copy lib database jdbc jars
COPY ./logstash/mysql-connector-j-8.0.32.jar /usr/share/logstash/logstash-core/lib/jars/mysql-connector-java.jar