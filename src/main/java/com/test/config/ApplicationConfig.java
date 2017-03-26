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
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.spring.cache.CacheBuilder;
import com.couchbase.client.spring.cache.CouchbaseCacheManager;

@Configuration
@EnableCaching
@PropertySource(value = { "classpath:/couchbase/cache.properties" })
@ComponentScan(basePackages = { "com.test.service" })
public class ApplicationConfig {

	@Value("#{'${couchbase.cluster.host}'}")
	private String couchbaseClusterHost;

	@Value("#{'${couchbase.bucket.default}'}")
	private String couchbaseBucketDefault;

	@Value("#{'${couchbase.bucket.beer-sample}'}")
	private String couchbaseBucketBeerSample;

	@Value("#{'${couchbase.bucket.default.username}'}")
	private String couchbaseBucketDefaultUsername;

	@Value("#{'${couchbase.bucket.default.password}'}")
	private String couchbaseBucketDefaultPassword;

	@Value("#{'${couchbase.cache}'}")
	private String couchbaseCache;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(destroyMethod = "disconnect")
	public Cluster cluster() {
		final List<String> nodes = Arrays.asList(couchbaseClusterHost
				.split(","));
		return CouchbaseCluster.create(couchbaseEnvironment(), nodes);
	}

	@Bean
	public CouchbaseEnvironment couchbaseEnvironment() {
		return DefaultCouchbaseEnvironment.builder().sslEnabled(false)
				.connectTimeout(10000).build();
	}

	@Bean(destroyMethod = "close")
	public Bucket bucket() {
		return cluster().openBucket(couchbaseBucketDefault,
				couchbaseBucketDefaultPassword);
	}

	@Bean(destroyMethod = "close")
	public Bucket bucketBeerSample() {
		return cluster().openBucket(couchbaseBucketBeerSample,
				couchbaseBucketDefaultPassword);
	}

	@Bean
	public ClusterManager clusterManager() {
		return cluster().clusterManager(couchbaseBucketDefaultUsername,
				couchbaseBucketDefaultPassword);
	}

	@Bean
	public CacheManager cacheManager() {
		final Map<String, CacheBuilder> cache = new HashMap<>();
		for (final String appCache : couchbaseCache.split(",")) {
			cache.put(appCache, CacheBuilder.newInstance(bucket()));
		}
		return new CouchbaseCacheManager(cache);
	}

}
