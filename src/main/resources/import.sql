INSERT INTO meeting_room(name, location) VALUES('회의실 A', '7층 1회의실');
INSERT INTO meeting_room(name, location) VALUES('회의실 B', '7층 2회의실');
INSERT INTO meeting_room(name, location) VALUES('회의실 C', '7층 3회의실');
INSERT INTO meeting_room(name, location) VALUES('회의실 D', '8층 1회의실');
INSERT INTO meeting_room(name, location) VALUES('회의실 E', '8층 2회의실');
INSERT INTO meeting_room(name, location) VALUES('회의실 F', '8층 3회의실');
INSERT INTO meeting_room(name, location) VALUES('회의실 G', '9층 1회의실');

INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (1L, '예약자A', '2018-09-03', '2018-10-05', '2018-10-26', '11:00', '13:00');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (2L, '예약자B', '2018-09-03', '2018-10-01', null, '15:00', '16:30');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (3L, '예약자C', '2018-09-03', '2018-10-08', '2018-10-15', '9:00', '10:00');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (4L, '예약자D', '2018-09-03', '2018-10-23', null, '10:00', '10:30');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (4L, '예약자D', '2018-10-03', '2018-10-10', null, '14:00', '15:30');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (1L, '예약자E', '2018-10-03', '2018-10-19', null, '14:00', '15:30');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (1L, '예약자A', '2018-10-03', '2018-10-19', null, '16:00', '17:00');
INSERT INTO reservation(meeting_room_id, booker_name, create_date, start_date, end_date, start_time, end_time) VALUES (2L, '예약자H', '2018-10-03', '2018-10-19', null, '14:00', '17:00');