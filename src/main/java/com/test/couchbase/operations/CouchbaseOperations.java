package com.test.couchbase.operations;

import java.util.ArrayList;
import java.util.List;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.ParameterizedN1qlQuery;

public class CouchbaseOperations implements ICouchbaseOperations {

	@Override
	public N1qlQueryResult selectAll(Bucket bucket) {
		N1qlQuery query = N1qlQuery.simple("SELECT META().id _k, _v from `"
				+ bucket.name() + "` _v;");
		N1qlQueryResult queryResult = bucket.query(query);
		return queryResult;
	}

	@Override
	public N1qlQueryResult deleteAll(final Bucket bucket) {
		final String query = "DELETE from `" + bucket.name() + "` AS b";
		N1qlQuery n1qlQuery = N1qlQuery.simple(query);
		N1qlQueryResult queryResult = bucket.query(n1qlQuery);
		return queryResult;
	}

	@Override
	public List<N1qlQueryResult> delete(final Bucket bucket,
			final List<String> keys) {
		final List<N1qlQueryResult> n1qlQueryResultList = new ArrayList<>();
		for (String key : keys) {
			final String query = "DELETE from `" + bucket.name()
					+ "` AS b where META(b).id = $1";
			final ParameterizedN1qlQuery parameterizedN1qlQuery = ParameterizedN1qlQuery
					.parameterized(query, JsonArray.create().add(key));
			n1qlQueryResultList.add(bucket.query(parameterizedN1qlQuery));
		}
		return n1qlQueryResultList;
	}

	@Override
	public N1qlQueryResult deleteLikeKey(final Bucket bucket, final String key) {
		final String query = "DELETE from `" + bucket.name()
				+ "` AS b where META(b).id LIKE '%" + key + "%'";
		N1qlQuery n1qlQuery = N1qlQuery.simple(query);
		return bucket.query(n1qlQuery);
	}

}
