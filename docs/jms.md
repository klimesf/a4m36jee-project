## How to configure WildFly for JMS

In standalone.xml, add the following snippets:

```
<extensions>
    ...
    <extension module="org.wildfly.extension.messaging-activemq"/>
</extensions>
```

```
<profile>
    ...
    <subsystem xmlns="urn:jboss:domain:messaging-activemq:1.0">
        <server name="default">
            <http-connector name="http-connector"
                            socket-binding="http"
                            endpoint="http-acceptor" />
            <http-connector name="http-connector-throughput"
                            socket-binding="http"
                            endpoint="http-acceptor-throughput">
                <param name="batch-delay"
                       value="50"/>
            </http-connector>
            <in-vm-connector name="in-vm"
                             server-id="0"/>
            <connection-factory name="InVmConnectionFactory"
                                connectors="in-vm"
                                entries="java:/ConnectionFactory" />
            <pooled-connection-factory name="activemq-ra"
                                       transaction="xa"
                                       connectors="in-vm"
                                       entries="java:/JmsXA java:jboss/DefaultJMSConnectionFactory"/>
            </server>
            
            <jms-queue name="reservationQueue"
                       entries="jms/queue/reservation java:jboss/exported/jms/queue/reservation" />
        </subsystem>
</profile>
```