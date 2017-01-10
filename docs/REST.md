# Airlines REST API documentation

## Flights

### List all Destinations

```bash
curl -v -X GET -H 'Content-type: application/json' http://localhost:8080/airlines/rest/destinations
```

```json

```

### Create a Destination

```bash
curl -v -X POST -H 'Content-type: application/json' -d '{
  "name": "Prague",
  "lat": 50.0755381,
  "lon": 14.4378005
}' http://localhost:8080/airlines/rest/destinations
```

```bash
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /airlines/rest/destinations HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.49.1
> Accept: */*
> Content-type: application/json
> Content-Length: 64
> 
* upload completely sent off: 64 out of 64 bytes
< HTTP/1.1 200 OK
< Connection: keep-alive
< X-Powered-By: Undertow/1
< Server: WildFly/10
< Content-Length: 0
< Date: Tue, 10 Jan 2017 10:30:29 GMT
< 
* Connection #0 to host localhost left intact
```
