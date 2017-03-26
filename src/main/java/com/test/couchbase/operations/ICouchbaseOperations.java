package com.test.couchbase.operations;

import java.util.List;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.query.N1qlQueryResult;

public interface ICouchbaseOperations {

	N1qlQueryResult selectAll(final Bucket bucket);

	N1qlQueryResult deleteAll(final Bucket bucket);

	List<N1qlQueryResult> delete(final Bucket bucket, final List<String> keys);

	N1qlQueryResult deleteLikeKey(final Bucket bucket, final String key);
}
