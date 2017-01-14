# How to Deploy Airlines project to Wildfly AS without killng yourself ("it works on my machine" edition)

**RESTART YOUR FUCKING COMPUTOR BEFORE YOU START THIS PROCESS!**

First, run your goddamn ActiveMQ:

```bash
activemq start
```

Make sure it's working by visiting `http://localhost:8161/` or `http://localhost:8161/admin`.
Obviously, the combination is `admin:admin`, duh!

Then, navigate to your Wildfly node 1 directory and run it:

```bash
cd /Users/filip/Documents/JavaProjects/a4m36jee/wildfly-10.0.0.Final1/
JBOSS_HOME=/Users/filip/Documents/JavaProjects/a4m36jee/wildfly-10.0.0.Final1
./bin/standalone.sh --server-config="standalone-ha.xml" -Djboss.node.name=node1
```

In another terminal window, run Wildfly node 2:

```bash
cd /Users/filip/Documents/JavaProjects/a4m36jee/wildfly-10.0.0.Final2/
JBOSS_HOME=/Users/filip/Documents/JavaProjects/a4m36jee/wildfly-10.0.0.Final2
./bin/standalone.sh --server-config="standalone-ha.xml" -Djboss.socket.binding.port-offset=100 -Djboss.node.name=node2
```

Voi-fucking-lá, the app should be running on `http://localhost:8080/airlines`.
