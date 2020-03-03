Run service with sbt
------------------------------------------

* configure database connection in ./dev

* create database schema

      ./dev sbt "runMain com.github.olekhnovych.ticketbooking.ManageSchema create"

* run service

      ./dev sbt "runMain com.github.olekhnovych.ticketbooking.Boot"


Run service with bloop
------------------------------------------

* configure database connection in ./dev

* install bloop

      sbt bloopInstall

* create database schema

      ./dev bloop run ticketbooking --main com.github.olekhnovych.ticketbooking.ManageSchema --args create

* run service

      ./dev bloop run ticketbooking --main com.github.olekhnovych.ticketbooking.Boot


Run service with docker
------------------------------------------

    sbt stage
    docker-compose up


Run demo
------------------------------------------

* initialize test data

      ./init.py

* run demo

      ./demo.py
