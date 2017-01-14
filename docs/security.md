## How to configure WildFly for authorization and authentication
 
 In standalone.xml (or other used config file), add the following block directly under the ```<security-domains>``` tag:
 
 ```xml
 <security-domain name="airlines" cache-type="default">
    <authentication>
        <login-module code="Database" flag="required">
            <module-option name="dsJndiName" value="java:jboss/datasources/ExampleDS"/>
            <module-option name="rolesQuery" value="SELECT role, 'Roles' FROM user WHERE username=?"/>
            <module-option name="hashAlgorithm" value="MD5"/>
            <module-option name="hashEncoding" value="hex"/>
            <module-option name="principalsQuery" value="SELECT password from user WHERE username=?"/>
        </login-module>
    </authentication>
    <authorization>
        <policy-module code="Database" flag="required">
            <module-option name="dsJndiName" value="java:jboss/datasources/ExampleDS"/>
            <module-option name="rolesQuery" value="SELECT role, 'Roles' FROM user WHERE username=?"/>
            <module-option name="hashAlgorithm" value="MD5"/>
            <module-option name="hashEncoding" value="hex"/>
            <module-option name="principalsQuery" value="SELECT password from user WHERE username=?"/>
        </policy-module>
    </authorization>
</security-domain>
 ```