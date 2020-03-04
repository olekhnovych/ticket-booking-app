#!/usr/bin/env python3

import requests
import json
from urllib.parse import quote


base = 'http://localhost:8082'
headers = {'Content-Type': 'application/json'}


def do_request(method, url, data=None):
    print({requests.get: 'GET', requests.post: 'POST'}[method] + ' ' + url)

    if data is not None:
        print('data:\n' + json.dumps(data, indent=2))

    result = method(url, headers=headers, data=json.dumps(data) if data is not None else None)

    try:
        print(json.dumps(result.json(), indent=2))
        return result.json()
    except:
        print(f'{result.status_code}: {result.text}')


print('\n* fetch movies')
screening_times = do_request(requests.get, base+f'/screening-times?from={quote("20200401T120000+02")}&to={quote("20200401T130000+02")}')
screening_time_id = screening_times[0]['id']


print('\n* fetch seats')
seats = do_request(requests.get, base+f'/reservations/seats?screeningTimeId={screening_time_id}')
seat_id_a = [seat['id'] for seat in seats if seat['row'] == 1 and seat['seat'] == 1][0]
seat_id_b = [seat['id'] for seat in seats if seat['row'] == 1 and seat['seat'] == 2][0]


print('\n* reserve seats')
result = do_request(requests.post, base+'/reservations', {'person': {'name': 'name-a', 'surname': 'surname-a'},
                                                          'screeningTimeId': screening_time_id,
                                                          'reservationSeats': [{'seatId': seat_id_a, 'ticketType': 'adult'},
                                                                               {'seatId': seat_id_b, 'ticketType': 'child'}]})
confirmUrl = result['confirmUrl']


print('\n* confirm reservation')
do_request(requests.post, confirmUrl)
