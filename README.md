# couchbase-spring-cache


 - Databases Path - `/Users/ArpitAggarwal/Library/Application Support/Couchbase/var/lib/couchbase/data`

 - Indexes Path - `/Users/ArpitAggarwal/Library/Application Support/Couchbase/var/lib/couchbase/data`

 - hostname - 127.0.0.1

 - username - Administrator

 - password - password


## Download Couchbase Server

 - http://www.couchbase.com/nosql-databases/downloads

## Managing connections

If the first one in the list fails the other ones can be tried. After initial contact is made, the bootstrap list provided by the user is thrown away and replaced with a list provided by the server (which contains all nodes of the cluster).

```
List<String> nodes = Arrays.asList("192.168.56.101", "192.168.56.102");
Cluster cluster = CouchbaseCluster.create(nodes);
```

## Create PRIMARY INDEX on bucket

```
CREATE PRIMARY INDEX `default-primary-index` ON `default` USING GSI;
```

## Select all indexes

```
SELECT * FROM system:indexes WHERE name="default-primary-index";
```


## GET all KEYS

```
http://127.0.0.1:8092/bucket_name/_design/cache/_view/view_name?connection_timeout=60000&inclusive_end=true&limit=1000&skip=0&stale=false
```


## [Couchbase Workbench][couchbase-workbench]

It provides a rich graphical user interface to prepare and execute simple to complex [N1QL][n1ql] queries, you can found tutorial to learn N1QL queries here - http://query.pub.couchbase.com/tutorial/#1

[n1ql]: https://www.couchbase.com/n1ql
[couchbase-workbench]: https://developer.couchbase.com/documentation/server/4.1/tools/query-workbench-intro.html




Sources:

1. http://blog.couchbase.com/2015/december/couchbase-spring-cache

2. http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html

3. http://docs.couchbase.com/developer/java-2.0/managing-connections.html
