beta
====

To Run:

    ./gradlew run

Server starts up at

    http://localhost:8080/

Nifty Things
============

    curl -F file=@"/Users/scott.tomaszewski/Pictures/bryanPunchCards.jpeg" http://localhost:8080/api/v1/profiles/1/updateProfilePicture
    curl -H "Content-Type: application/json" -d '{"firstName":"Foo","lastName":"Bar"}' http://localhost:8080/api/v1/profiles/1/update
    curl -H "Content-Type: application/json" -d '{"email":"asd","passwordHash":"asd"}' http://localhost:8080/api/v1/profiles/add