### curl samples (application deployed in application context `graduation`).
> For windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/graduation/rest/admin/users --user admin@gmail.com:admin`

#### get Users 100001
`curl -s http://localhost:8080/graduation/rest/admin/users/100001 --user admin@gmail.com:admin`

#### register Users
`curl -s -i -X POST -d '{"name":"New User","email":"test@mail.ru","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/profile/register`

#### get Profile
`curl -s http://localhost:8080/graduation/rest/profile --user test@mail.ru:test-password`

#### get All Meals
`curl -s http://localhost:8080/graduation/rest/meals --user user@yandex.ru:password`

#### get Meals 100006
`curl -s http://localhost:8080/graduation/rest/meals/100006  --user user@yandex.ru:password`

#### get Meals not found
`curl -s -v http://localhost:8080/graduation/rest/meals/000 --user user@yandex.ru:password`

#### delete Meals
`curl -s -X DELETE http://localhost:8080/graduation/rest/admin/meals/100006 --user admin@gmail.com:admin`

#### create Meals
`curl -s -X POST -d '{"date":"2020-02-01","description":"Created lunch","price":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/admin/meals --user admin@gmail.com:admin`

#### update Meals
`curl -s -X PUT -d '{"dateTime":"2020-01-30", "description":"Updated breakfast", "price":200}' -H 'Content-Type: application/json' http://localhost:8080/graduation/rest/admin/meals/100006 --user admin@gmail.com:admin`

#### get All Restaurants with today menu which you could vote for
`curl -s http://localhost:8080/graduation/rest/restaurants/today --user user@yandex.ru:password`

#### get Restaurant 100002
`curl -s http://localhost:8080/graduation/rest/restaurants/100002  --user user@yandex.ru:password`

#### get Restaurant by name
`curl -s http://localhost:8080/graduation/rest/restaurants/by?name=ASTORIA  --user user@yandex.ru:password`

#### get All Restaurants
`curl -s http://localhost:8080/graduation/rest/restaurants --user user@yandex.ru:password`

#### update Restaurant
`curl -s -X PUT -d '{"id":"100002", "name":"Updated name"}' -H 'Content-Type: application/json' http://localhost:8080/graduation/rest/admin/restaurants/100002 --user admin@gmail.com:admin`

#### create Vote. Could be used for updating today's vote if it is before 11 a.m.
`curl -s -X POST -d '{"restaurant":{"id":"100002", "name":"ASTORIA"}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/graduation/rest/votes?restaurant_id=100002 --user user@yandex.ru:password`

#### get vote's count for restaurant today
`curl -s http://localhost:8080/graduation/rest/votes/todayByRestaurant/?restaurant_id=100002 --user user@yandex.ru:password`

#### delete vote
`curl -s -X DELETE http://localhost:8080/graduation/rest/votes/100012 --user user@yandex.ru:password`