package com.test.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.spring.cache.CacheBuilder;
import com.couchbase.client.spring.cache.CouchbaseCacheManager;

@Configuration
@EnableCaching
@PropertySource(value = { "classpath:application.properties" })
@ComponentScan(basePackages = { "com.test.service" })
public class ApplicationConfig {

	@Value("#{'${couchbase.cluster.host}'}")
	private String couchbaseClusterHost;

	@Value("#{'${couchbase.bucket.default}'}")
	private String couchbaseBucketDefault;

	@Value("#{'${couchbase.cache.employee}'}")
	private String couchbaseCacheEmployee;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(destroyMethod = "disconnect")
	public Cluster cluster() {
		List<String> nodes = Arrays.asList(couchbaseClusterHost);
		// this connects to a Couchbase instance running on localhost
		return CouchbaseCluster.create(nodes);
	}

	@Bean(destroyMethod = "close")
	public Bucket bucket() {
		// this will be the bucket where every cache-related data will be stored
		// note that the bucket "default" must exist
		return cluster().openBucket(couchbaseBucketDefault, "");
	}

	@Bean
	public CacheManager cacheManager() {
		Map<String, CacheBuilder> cache = new HashMap<>();
		// we'll make this cache manager recognize a single cache named "books"
		cache.put(couchbaseCacheEmployee, CacheBuilder.newInstance(bucket()));
		return new CouchbaseCacheManager(cache);
	}

}
