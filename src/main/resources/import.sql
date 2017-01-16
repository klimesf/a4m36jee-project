INSERT INTO destination (`id`, `name`, `lat`, `lon`, `deleted`) VALUES
  (1, 'Prague', 50.0755381, 14.4378005, NULL ),
  (2, 'Berlin', 52.5243700, 13.4105300, NULL ),
  (3, 'New York', 40.7142700, -74.0059700, NULL );

INSERT INTO flight (`id`, `name`, `seats`, `from_id`, `to_id`, `price`, `date`, `deleted`) VALUES
  (1, 'OK-3867', 100, 1, 2, 399, '2017-01-20', NULL ),
  (2, 'MH-370', 200, 2, 3, 2999, '2017-01-23', NULL ),
  (3, 'XY-1234', 150, 3, 1, 1599, '2017-01-26', NULL ),
  (4, 'IM-666', 300, 3, 2, 1699, '2017-01-31', NULL );

INSERT INTO reservation (`id`, `seat`, `flight_id`, `password`, `created`, `deleted`) VALUES
  (1, 2, 1, 'pass123', NOW(), NULL ),
  (2, 1, 2, 'ich-bin-ein-berliner', NOW(), NULL ),
  (3, 3, 3, 'please-no-bombs-on-plane', NOW(), NULL ),
  (4, 1, 4, 'allahu-akbar', NOW(), NULL );

INSERT INTO `user` (username, `password`, `role`, `deleted`) VALUES
  ('admin', '21232f297a57a5a743894a0e4a801fc3', 'ADMIN', NULL );

INSERT INTO `user` (username, `password`, `role`, `deleted`) VALUES
  ('employee', 'fa5473530e4d1a5a1e1eb53d2fedb10c', 'EMPLOYEE', NULL );

INSERT INTO `user` (username, `password`, `role`, `deleted`) VALUES
  ('user', 'ee11cbb19052e40b07aac0ca060c23ee', 'USER', NULL );
