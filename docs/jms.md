## How to configure JMS for WildFly using out of process ActiveMQ

### 1. Download ActiveMQ

[Download](http://activemq.apache.org/activemq-5141-release.html) the version 5.14.1. and run the script ```ACTIVEMQ_LOCATION/bin/activemq``` (needs superuser rights).


### 2. Setup resource adapter in WildFly
 
 Download the resource adapter:

 ```wget http://repo1.maven.org/maven2/org/apache/activemq/activemq-rar/5.9.1/activemq-rar-5.9.1.rar  ```

 Extract the archive and copy its content into the following folder:
 
 ```WILDFLY_LOCATION/modules/systems/layers/base/org/apache/activemq/activemq-rar/5.14.1```
 
 Create a file ```module.xml``` in the destination above with the content of [module.xml](module.xml).
### 3. Configure ActiveMQ in WildFly 

In standalone.xml, add the following snippets:

```aidl xml
<extensions>
    ...
    <extension module="org.wildfly.extension.messaging-activemq"/>
</extensions>
```

```aidl xml
<subsystem xmlns="urn:jboss:domain:ejb3:4.0">
...
<mdb>
    <resource-adapter-ref resource-adapter-name="activemq-rar"/>
    <bean-instance-pool-ref pool-name="mdb-strict-max-pool"/>
</mdb>
```

Replace the ```<subsystem xmlns="urn:jboss:domain:resource-adapters:4.0">``` block with: 

```aidl xml
<subsystem xmlns="urn:jboss:domain:resource-adapters:4.0">
    <resource-adapters>
    <resource-adapter id="activemq-rar.rar">
    <archive>
        activemq-rar-5.14.1.rar
    </archive>
    <transaction-support>XATransaction</transaction-support>
        <config-property name="ServerUrl">
            tcp://localhost:61616
        </config-property>
        <config-property name="UserName">
            admin
        </config-property>
        <config-property name="UseInboundSession">
            false
        </config-property>
        <config-property name="Password">
            admin
        </config-property>
        <connection-definitions>
            <connection-definition class-name="org.apache.activemq.ra.ActiveMQManagedConnectionFactory" jndi-name="java:/ConnectionFactory" enabled="true" pool-name="ConnectionFactory">
                <xa-pool>
                    <min-pool-size>1</min-pool-size>
                    <max-pool-size>20</max-pool-size>
                    <prefill>false</prefill>
                    <is-same-rm-override>false</is-same-rm-override>
                </xa-pool>
            </connection-definition>
        </connection-definitions>
        <admin-objects>
            <admin-object class-name="org.apache.activemq.command.ActiveMQQueue" jndi-name="java:/jms/queue/reservationQueue" use-java-context="true" pool-name="reservationQueue">
                <config-property name="PhysicalName">
                    jms/queue/reservationQueue
                </config-property>
            </admin-object>
            </admin-objects>
        </resource-adapter>
    </resource-adapters>
</subsystem>
```

``` aidl xml
<subsystem xmlns="urn:jboss:domain:messaging-activemq:1.0">
    <server name="default">
    <security-setting name="#">
        <role name="guest" delete-non-durable-queue="true" create-non-durable-queue="true" consume="true" send="true"/>
    </security-setting>
    <address-setting name="#" message-counter-history-day-limit="10" page-size-bytes="2097152" max-size-bytes="10485760" expiry-address="jms.queue.ExpiryQueue" dead-letter-address="jms.queue.DLQ"/>
    <http-connector name="http-connector" endpoint="http-acceptor" socket-binding="http"/>
    <http-connector name="http-connector-throughput" endpoint="http-acceptor-throughput" socket-binding="http">
        <param name="batch-delay" value="50"/>
    </http-connector>
    <in-vm-connector name="in-vm" server-id="0"/>
    <http-acceptor name="http-acceptor" http-listener="default"/>
    <http-acceptor name="http-acceptor-throughput" http-listener="default">
        <param name="batch-delay" value="50"/>
        <param name="direct-deliver" value="false"/>
    </http-acceptor>
    <in-vm-acceptor name="in-vm" server-id="0"/>
    <jms-queue name="ExpiryQueue" entries="java:/jms/queue/ExpiryQueue"/>
    <jms-queue name="DLQ" entries="java:/jms/queue/DLQ"/>
    <pooled-connection-factory name="activemq-ra" transaction="xa" entries="java:/JmsXA java:jboss/DefaultJMSConnectionFactory" connectors="in-vm"/>
    </server>
</subsystem>
```