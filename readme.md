# Project: weather
provides weather information on the basis of pincode for any particular date.
swagger documentation is available on /swagger-ui.html
## End-point: weather info valid input and response case
### Method: GET
>```
>http://localhost:8084/weather-info?pincode=530068&date=1997-02-26
>```


### Response: 200
```json
{
    "data": {
        "weather_info": [
            {
                "dt": 856895400,
                "sunrise": 856857851,
                "temp": 267.47,
                "wind_deg": 50,
                "dew_point": 264.31,
                "sunset": 856881552,
                "weather": [
                    {
                        "icon": "04n",
                        "description": "overcast clouds",
                        "main": "Clouds",
                        "id": 804
                    }
                ],
                "humidity": 76,
                "wind_speed": 10.73,
                "pressure": 972,
                "clouds": 100,
                "feels_like": 260.47
            }
        ]
    },
    "status": "success"
}
```

## End-point: weather info invalid input case
Invalid date in the query params

### Method: GET
>```
>http://localhost:8084/weather-info?pincode=530068&date=1997/02/26
>```


### Response: 400
```json
{
    "message": "Couldn't parse date, date should be in format: yyyy-MM-dd ",
    "status": "failed"
}
```

## End-point: weather info invalid input case
Invalid pincode in the query params
### Method: GET
>```
>http://localhost:8084/weather-info?pincode=5300681234&date=1997-02-26
>```


### Response: 400
```json
{
    "message": "Invalid pincode entered",
    "status": "failed"
}
```

