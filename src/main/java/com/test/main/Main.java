package com.test.main;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.cluster.BucketSettings;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.test.service.IEmployeeService;

@SpringBootApplication
@ComponentScan(basePackages = { "com.test.config" })
public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	@Component
	static class Runner implements CommandLineRunner {

		@Autowired
		private IEmployeeService employeeService;

		@Autowired
		private ClusterManager clusterManager;

		@Autowired
		private Bucket bucketBeerSample;

		@Override
		public void run(String... args) throws Exception {
			log.info("Employee A --> " + employeeService.getEmployeeById(1));
			log.info("Employee A --> " + employeeService.getEmployeeById(1));
			log.info("Employee A --> " + employeeService.getEmployeeById(1));

			final List<BucketSettings> buckets = clusterManager.getBuckets();
			for (final BucketSettings bucket : buckets) {
				log.info("Bucket Information :: " + bucket);
			}

			final N1qlQueryResult selectQueryResult = selectDataFromBucket();

			for (N1qlQueryRow result : selectQueryResult) {
				System.out.println(result.value());
			}
			
			final N1qlQueryResult deleteQueryResult = deleteDataFromBucket();

			for (N1qlQueryRow result : deleteQueryResult) {
				System.out.println(result.value());
			}
		}

		/**
		 * Select data from <code>Bucket</code> as key value pair.
		 * 
		 * @return {@link N1qlQueryResult}
		 */
		private N1qlQueryResult selectDataFromBucket() {
			N1qlQuery query = N1qlQuery
					.simple("SELECT META().id _k, _v from `beer-sample` _v;");
			N1qlQueryResult queryResult = bucketBeerSample.query(query);
			return queryResult;
		}
		
		/**
		 * Select data from <code>Bucket</code> as key value pair.
		 * 
		 * @return {@link N1qlQueryResult}
		 */
		private N1qlQueryResult deleteDataFromBucket() {
			N1qlQuery query = N1qlQuery
					.simple("DELETE from `beer-sample` b where META(b).id LIKE '%zea_rotisserie_and_brewery%'");
			N1qlQueryResult queryResult = bucketBeerSample.query(query);
			return queryResult;
		}


		public void setClusterManager(final ClusterManager clusterManager) {
			this.clusterManager = clusterManager;
		}

		public void setEmployeeService(final IEmployeeService employeeService) {
			this.employeeService = employeeService;
		}
		
		public void setBucketBeerSample(Bucket bucketBeerSample) {
			this.bucketBeerSample = bucketBeerSample;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
