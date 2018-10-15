INSERT INTO meeting_room(name, location) VALUES('Meeting Room A', '7Floor 1Room');
INSERT INTO meeting_room(name, location) VALUES('Meeting Room B', '7Floor 2Room');
INSERT INTO meeting_room(name, location) VALUES('Meeting Room C', '7Floor 3Room');
INSERT INTO meeting_room(name, location) VALUES('Meeting Room D', '8Floor 1Room');
INSERT INTO meeting_room(name, location) VALUES('Meeting Room E', '8Floor 2Room');
INSERT INTO meeting_room(name, location) VALUES('Meeting Room F', '8Floor 3Room');
INSERT INTO meeting_room(name, location) VALUES('Meeting Room G', '9Floor 1Room');

INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (1L, 'bookerA', '2018-09-03', '2018-10-05', '2018-10-26', '11:00', '13:00');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (2L, 'bookerB', '2018-09-03', '2018-10-01', null, '15:00', '16:30');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (3L, 'bookerC', '2018-09-03', '2018-10-08', '2018-10-15', '9:00', '10:00');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (4L, 'bookerD', '2018-09-03', '2018-10-23', null, '10:00', '10:30');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (4L, 'bookerD', '2018-10-03', '2018-10-10', null, '14:00', '15:30');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (1L, 'bookerE', '2018-10-03', '2018-10-19', null, '14:00', '15:30');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (1L, 'bookerA', '2018-10-03', '2018-10-19', null, '16:00', '17:00');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (2L, 'bookerH', '2018-10-03', '2018-10-19', null, '14:00', '17:00');