package com.example.blog.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.SneakyThrows;


//class ElasticSearchConfiguration extends ElasticsearchConfiguration {
//@Override
//public ClientConfiguration clientConfiguration() {
//  ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//		        .connectedTo(hostAndPort)
//		        .usingSsl(getSSLContext())
//		        .withBasicAuth(username, password)
//		        .build();
//	
//  return clientConfiguration;
//}
//}

// https://www.pixeltrice.com/spring-boot-elasticsearch-crud-example/
// https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/_encrypted_communication.html

@Configuration
class ElasticSearchConfiguration {

    @Value("${spring.elasticsearch.client.certificate}")
    private String certificateBase64;
    
    @Value("${elasticsearch.username}")
    private String username;
    
    @Value("${elasticsearch.password}")
    private String password;
    
    @Value("${elasticsearch.host}")
    private String hostAndPort;

    @Bean
    public RestClient getRestClient () {
    	final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "changeme"));
        
    	SSLContext sslContext = getSSLContext();
    	
    	RestClientBuilder builder = RestClient.builder(
    			new HttpHost("localhost", 9200, "https")
    			).setHttpClientConfigCallback(new HttpClientConfigCallback() {
					@Override
					public HttpAsyncClientBuilder customizeHttpClient(
							HttpAsyncClientBuilder httpClientBuilder) {
						httpClientBuilder.disableAuthCaching();
						return httpClientBuilder
								.setSSLContext(sslContext)
								.setDefaultCredentialsProvider(credentialsProvider);
					}
    				
    			});
    	
    	RestClient restClient = builder.build();
        return restClient;
    }

    @Bean
    public  ElasticsearchTransport getElasticsearchTransport() {
        return new RestClientTransport(
                getRestClient(), new JacksonJsonpMapper());
    }


    @Bean
    public ElasticsearchClient getElasticsearchClient(){
        ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
        return client;
    }

    @SneakyThrows
    private SSLContext getSSLContext() {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca;
        try (InputStream certificateInputStream = new FileInputStream(certificateBase64)) {
            ca = cf.generateCertificate(certificateInputStream);
        }

        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);
        return context;
    }

}
