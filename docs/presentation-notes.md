# Presentation notes

- Git project: [https://github.com/klimesf/a4m36jee-project](https://github.com/klimesf/a4m36jee-project)
- Specifikace + UML viz [README](https://github.com/klimesf/a4m36jee-project/blob/master/README.md#specifikace)
- Prezentační vrstva
    - Omnifaces, Primefaces
    - TODO
- Byznys vrstva
    - Viz [service package](https://github.com/klimesf/a4m36jee-project/tree/master/src/main/java/cz/cvut/fel/a4m36jee/airlines/service)
- Datová vrstva
    - Viz [model](https://github.com/klimesf/a4m36jee-project/tree/master/src/main/java/cz/cvut/fel/a4m36jee/airlines/model) a [dao](https://github.com/klimesf/a4m36jee-project/tree/master/src/main/java/cz/cvut/fel/a4m36jee/airlines/dao) packages
    - Pomocí JPA (Hibernate)
    - Java EE bean validation, see classes in [model package](https://github.com/klimesf/a4m36jee-project/tree/master/src/main/java/cz/cvut/fel/a4m36jee/airlines/model).
        - Custom validation for Longitude and Latitude in [model.validation package](https://github.com/klimesf/a4m36jee-project/tree/master/src/main/java/cz/cvut/fel/a4m36jee/airlines/model/validation)
- [Arquillian testy](https://github.com/klimesf/a4m36jee-project/tree/master/src/test/java/cz/cvut/fel/a4m36jee/airlines/)
    - Integrace s [Travis CI](https://travis-ci.org/klimesf/a4m36jee-project)
        - Automatické buildování a spouštění unit testů
- Openshift nasazení [http://a4m36jee-project-a4m36jee-airlines.44fs.preview.openshiftapps.com](http://a4m36jee-project-a4m36jee-airlines.44fs.preview.openshiftapps.com)
    - Dořešit `standalone.xml`
- Swarm
    - TODO
- Batching
    - TODO
- Websocket
    - TODO
- JAX-RS REST API
    - Viz [rest package](https://github.com/klimesf/a4m36jee-project/tree/master/src/main/java/cz/cvut/fel/a4m36jee/airlines/rest)
- JMS 2.0
    - Viz [jms](https://github.com/klimesf/a4m36jee-project/tree/master/src/main/java/cz/cvut/fel/a4m36jee/airlines/jms) a [event](https://github.com/klimesf/a4m36jee-project/tree/master/src/main/java/cz/cvut/fel/a4m36jee/airlines/event) packages
