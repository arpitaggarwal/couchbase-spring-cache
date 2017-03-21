package com.test.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.cluster.ClusterManager;
import com.test.config.ApplicationConfig;
import com.test.main.Main.Runner;
import com.test.service.IEmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class MainTest {

	@InjectMocks
	private Runner main = new Runner();

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private ClusterManager clusterManager;
	
	@Autowired
	private Bucket bucketBeerSample;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		main.setEmployeeService(employeeService);
		main.setClusterManager(clusterManager);
		main.setBucketBeerSample(bucketBeerSample);
	}

	@Test
	public void shouldDisplayDataFromCouchDatabaseServer() throws Exception {
		main.run(new String[] { "" });
	}
}
