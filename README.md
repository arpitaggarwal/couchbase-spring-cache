# couchbase-spring-cache


 - Databases Path - `/Users/ArpitAggarwal/Library/Application Support/Couchbase/var/lib/couchbase/data`

 - Indexes Path - `/Users/ArpitAggarwal/Library/Application Support/Couchbase/var/lib/couchbase/data`

 - hostname - 127.0.0.1

 - username - Administrator

 - password - ****


## Download Couchbase Server

 - http://www.couchbase.com/nosql-databases/downloads

## Managing connections

If the first one in the list fails the other ones can be tried. After initial contact is made, the bootstrap list provided by the user is thrown away and replaced with a list provided by the server (which contains all nodes of the cluster).

```
List<String> nodes = Arrays.asList("192.168.56.101", "192.168.56.102");
Cluster cluster = CouchbaseCluster.create(nodes);
```


Sources:

1. http://blog.couchbase.com/2015/december/couchbase-spring-cache

2. http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html

3. http://docs.couchbase.com/developer/java-2.0/managing-connections.html
