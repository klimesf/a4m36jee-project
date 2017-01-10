INSERT INTO destination (id, name, lat, lon) VALUES
  (1, 'Prague', 50.0755381, 14.4378005),
  (2, 'Berlin', 52.5243700, 13.4105300),
  (3, 'New York', 40.7142700, -74.0059700);

INSERT INTO flight (id, name, seats, from_id, to_id, price, `date`) VALUES
  (1, 'OK-3867', 100, 1, 2, 399, '2017-01-20'),
  (2, 'MH-370', 200, 2, 3, 2999, '2017-01-23'),
  (3, 'XY-1234', 150, 3, 1, 1599, '2017-01-26'),
  (4, 'IM-666', 300, 3, 2, 1699, '2017-01-31');

INSERT INTO reservation (id, seats, flight_id, `password`, created) VALUES
  (1, 2, 1, 'pass123', NOW()),
  (2, 1, 2, 'ich-bin-ein-berliner', NOW()),
  (3, 3, 3, 'please-no-bombs-on-plane', NOW()),
  (4, 1, 4, 'allahu-akbar', NOW());
