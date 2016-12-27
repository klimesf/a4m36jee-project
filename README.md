# a4m36jee-project
Semestral project in [A4M36JEE](https://developer.jboss.org/wiki/AdvancedJavaEELabFELCVUTPodzim2016) class at FEE CTU.

Team
===

- Filip Klimeš (vedoucí)
- Jakub Moravec
- Michal Kašpar
- Ondřej Kratochvíl
- Ondřej Slavíček

Specifikace
===

*TODO*

Git WorkFlow
===

*TODO*

Checklist
===
- [ ] Každý student si zřídí účet na github.com, zdrojové kódy projektu budou tamtéž
- [ ] Volba git workflow je na každém týmu, taktéž rozdělení prací na projektu
- [ ] Vytvořit krátkou specifikaci - textovou či s doprovodem UML diagramů
- [ ] Implementace třívrstvé aplikace
  - [ ] Prezentační vrstva - JSF (volitelně použití knihovny RichFaces) nebo moderní JavaScriptový framework s REST backendem, hodnotit se bude funkcionalita, nikoliv grafické provedení
  - [ ] Business vrstva - EJB
  - [ ] Datová vrstva - perzistence pomocí JPA 2 nebo Infinispan (zde se očekává konfigurace Infinispan-u pro trvalé uložení dat)
- [ ] Aplikace bude používat CDI, není vhodné používat dependency injection definovanou v Java EE 5 (tzn. očekáváme i použití např. typově bezpečného persitence contextu)
- [ ] Aplikace bude plně zabezpečena, bude používat minimálně tři úrovně oprávnění (role)
- [ ] Datový model bude anotovaný pomocí Bean Validation - stačí použít předdefinované anotace, použití i vlastních omezení je plus
- [ ] Aplikace bude testovatelná (Arquillian)
- [ ] Aplikace bude nasazena v clusteru dvou uzlů v doménové konfiguraci, přiložen konfigurační skript (JBoss CLI) a demonstrována její odolnost vůči výpadku jednoho z nich (lze simulovat na jednom počítači)
- [ ] Aplikace bude dodána též
  - [ ] nasazená aplikace bežící v OpenShift 3 (developer preview)
  - [ ] volitelně jako standalone fatjar pomocí WildFly Swarm
- [ ] Aplikace bude vystavovat rozhraní pro komunikaci mezi systémy (JAX-RS) - formát zpráv JSON
- [ ] Aplikace bude používat alespoň jeden WebSocket endopint
- [ ] Aplikace bude volat alespoń jeden REST endpoint (JAX-RS)
- [ ] Vystavené endpointy budou podoporovat zabezpečení
- [ ] Aplikace bude obsahovat alespoň jeden use case pro použití Concurrency nebo Batching API
- [ ] Aplikace bude obsahovat alespoň jeden use case pro použití JMS 2.0 API
- [ ] Každý projekt bude prezentován, studenti budou tázáni na části, které implementovali a technologie, které použili
