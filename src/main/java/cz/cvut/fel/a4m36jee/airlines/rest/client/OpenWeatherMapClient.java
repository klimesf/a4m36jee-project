package cz.cvut.fel.a4m36jee.airlines.rest.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.cvut.fel.a4m36jee.airlines.exception.ErrorWhileContactingWeatherAPIException;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Client wrapper for API at openweathermap.org.
 *
 * @author klimefi1
 */
@Stateless
public class OpenWeatherMapClient {

    @JsonIgnoreProperties({"coord", "weather", "base", "wind", "clouds", "dt", "sys", "id", "name", "cod", "visibility"})
    public static class WeatherResponse {
        public InnerWeatherResponse main;

        public InnerWeatherResponse getMain() {
            return main;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InnerWeatherResponse {
        public Double temp;
        public Double pressure;
        public Integer humidity;

        public Double getTemp() {
            return temp;
        }

        public Double getPressure() {
            return pressure;
        }

        public Integer getHumidity() {
            return humidity;
        }
    }

    /**
     * Reads weather for Destination info from API.
     *
     * @param destination Destination
     * @return The weather info
     * @throws ErrorWhileContactingWeatherAPIException When API call fails.
     */
    public WeatherResponse getWeather(Destination destination) throws ErrorWhileContactingWeatherAPIException {
        if (destination == null) {
            return null;
        }

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://api.openweathermap.org/data/2.5")
                .path("weather")
                .queryParam("lat", destination.getLat())
                .queryParam("lon", destination.getLon())
                .queryParam("appid", "bda3d220fca2353760ff32c8c5ced63e");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        if (response.getStatus() != 200) {
            throw new ErrorWhileContactingWeatherAPIException();
        }

        WeatherResponse weatherResponse = response.readEntity(WeatherResponse.class);
        response.close();

        return weatherResponse;
    }

}
