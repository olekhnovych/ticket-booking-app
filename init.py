#!/usr/bin/env python3

import requests
import json
from urllib.parse import quote


base = 'http://localhost:8082'
headers = {'Content-Type': 'application/json'}


def create_movie(title):
    result = requests.post(base+'/movies', headers=headers, data=json.dumps({'title': title}))
    return result.json()['id']


def create_screening_room(name, rows_number, seats_number):
    result = requests.post(base+'/screening-rooms', headers=headers, data=json.dumps({'name': name, 'rowsNumber': rows_number, 'seatsNumber': seats_number}))
    return result.json()['id']


def create_screening_time(movie_id, screening_room_id, start_time):
    result = requests.post(base+'/screening-times', headers=headers, data=json.dumps({'movieId': movie_id, 'screeningRoomId': screening_room_id, 'start': start_time, 'duration': 'PT120M'}))
    return result.json()['id']


movie_a = create_movie('movie-a')
movie_b = create_movie('movie-b')
movie_c = create_movie('movie-c')


screening_room_a = create_screening_room('screening-room-a', 2, 5)
screening_room_b = create_screening_room('screening-room-b', 2, 5)
screening_room_c = create_screening_room('screening-room-c', 2, 5)


screening_room_a1 = create_screening_time(movie_a, screening_room_a, '2020-04-01T10:00:00+02')
screening_room_a2 = create_screening_time(movie_b, screening_room_a, '2020-04-01T12:00:00+02')

screening_room_b1 = create_screening_time(movie_b, screening_room_b, '2020-04-01T11:00:00+02')
screening_room_b2 = create_screening_time(movie_c, screening_room_b, '2020-04-01T13:00:00+02')

screening_room_c1 = create_screening_time(movie_a, screening_room_c, '2020-04-01T12:00:00+02')
screening_room_c2 = create_screening_time(movie_c, screening_room_c, '2020-04-01T14:00:00+02')
