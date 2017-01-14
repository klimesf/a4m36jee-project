# Clustering

Dvě Wildfly instance (viz /clustering).

```bash
JBOSS_HOME=/Users/filip/Documents/JavaProjects/a4m36jee/wildfly-10.0.0.Final1
./bin/standalone.sh --server-config="standalone-ha.xml" -Djboss.node.name=node1
```

Dostupný na adrese `http://localhost:8080/airlines/`.

```bash
JBOSS_HOME=/Users/filip/Documents/JavaProjects/a4m36jee/wildfly-10.0.0.Final2
./bin/standalone.sh --server-config="standalone-ha.xml" -Djboss.socket.binding.port-offset=100 -Djboss.node.name=node2
```

Dostupný na adrese `http://localhost:8180/airlines/`.
