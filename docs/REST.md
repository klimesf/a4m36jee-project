# Airlines REST API documentation

## Destinations

### List all Destinations

```bash
curl -v -H 'Content-type: application/json' http://localhost:8080/airlines/rest/destinations
```

```json
[
  {
    "id": 1,
    "name": "Prague",
    "lat": 50.0755381,
    "lon": 14.4378005
  },
  {
    "id": 2,
    "name": "Berlin",
    "lat": 52.52437,
    "lon": 13.41053
  },
  {
    "id": 3,
    "name": "New York",
    "lat": 40.71427,
    "lon": -74.00597
  }
]
```

### Create a Destination

```bash
curl -v -H 'Content-type: application/json' -d '{
  "name": "Prague",
  "lat": 50.0755381,
  "lon": 14.4378005
}' http://localhost:8080/airlines/rest/destinations
```

```
200 OK
```

## Flights

### List all Flights

```bash
curl -v -H 'Content-type: application/json' http://localhost:8080/airlines/rest/flights
```

```json
[
  {
    "id": 1,
    "date": 1484866800000,
    "price": 399.0,
    "seats": 100,
    "name": "OK-3867",
    "from": {
      "id": 1,
      "name": "Prague",
      "lat": 50.0755381,
      "lon": 14.4378005
    },
    "to": {
      "id": 2,
      "name": "Berlin",
      "lat": 52.52437,
      "lon": 13.41053
    }
  },
  
  ...

]
```

### Create a Flight


```bash
curl -v -H 'Content-type: application/json' -d '{
  "name": "AB-0123",
  "from": 1,
  "to": 2,
  "seats": 200,
  "date": "2017-02-01",
  "price": 299
}' http://localhost:8080/airlines/rest/flights
```

```
curl -v -H 'Content-type: application/json' http://localhost:8080/airlines/rest/flights
```


## Reservations

### List all Reservations

```bash

```

```json
[
  {
    "id": 1,
    "seats": 2,
    "password": "pass123",
    "created": 1484045647644,
    "flight": {
      "id": 1,
      "date": 1484866800000,
      "price": 399.0,
      "seats": 100,
      "name": "OK-3867",
      "from": {
        "id": 1,
        "name": "Prague",
        "lat": 50.0755381,
        "lon": 14.4378005
      },
      "to": {
        "id": 2,
        "name": "Berlin",
        "lat": 52.52437,
        "lon": 13.41053
      }
    }
  },
  
  ...
  
]
```

### Create a Reservation

```bash
curl -v -H 'Content-type: application/json' -d '{
  "flight": 1,
  "seats": 200,
  "created": "2017-02-01"
  "password": "secret",
}' http://localhost:8080/airlines/rest/reservations
```
