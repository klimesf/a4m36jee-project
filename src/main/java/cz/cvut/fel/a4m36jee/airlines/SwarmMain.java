package cz.cvut.fel.a4m36jee.airlines;

import cz.cvut.fel.a4m36jee.airlines.rest.DestinationResource;
import cz.cvut.fel.a4m36jee.airlines.rest.FlightResource;

import cz.cvut.fel.a4m36jee.airlines.rest.ReservationResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

public class SwarmMain {
    public static void main(String[] args) throws Exception {
        Swarm swarm = new Swarm();
        swarm.fraction(datasourceWithH2());
        String driverModule = "com.h2database.h2";

        swarm.start();
        JAXRSArchive appDeployment = ShrinkWrap.create(JAXRSArchive.class);
        appDeployment.addResource(DestinationResource.class);
        appDeployment.addResource(FlightResource.class);
        appDeployment.addResource(ReservationResource.class);
        appDeployment.addModule(driverModule);

        // Deploy your app
        swarm.deploy(appDeployment);
    }

    private static DatasourcesFraction datasourceWithH2() {
        return new DatasourcesFraction()
//                .jdbcDriver("h2", (d) -> {
//                    d.driverClassName("org.h2.Driver");
//                    d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
//                    d.driverModuleName("com.h2database.h2");
//                })
//                .dataSource("ExampleDS", (ds) -> {
//                    ds.driverName("h2");
//                    ds.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
//                    ds.userName("sa");
//                    ds.password("sa");
//                })
                ;
    }
}
