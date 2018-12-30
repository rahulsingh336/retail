![](https://img.shields.io/appveyor/ci/gruntjs/grunt.svg)

# Retail Billing api

Retail billing api is used to calculate bill amount based in various rules defined for customer

## Installation/Build/Run

you need to have maven (version 3 min.) and java 8.

git clone repo

then go to project root folder

```bash
mvn clean install
```
From command line execute below command from project's root folder

```bash
java -jar target/retailstore-0.0.1-SNAPSHOT.jar
```

For sonar report run below commands[make sure to run 'mvn clean install' before running below command and make sure you have sonarqube version 6.7 running locally on port 9000.

```bash
mvn sonar:sonar
```

## Usage

As this is a simple microservice, so, you need postman to give a rest call.

Http Method is :- POST[there is reason for this to be post]

URL:- http://localhost:8080/bill

Below is sample request body, by default app will run on port 8080.


```json
{
 "customerName": "Rakesh",
 "items": [{
 	"itemName": "slippers"
 },{
 	"itemName": "shirt"
 }
 ]
}
```
## Test


This microservice uses in-memory db h2 for storing reference data such as items, customer records, in real world bill, customer can be separate microservice[one of thing to improve].

Browse console using below link:-

```link
http://localhost:8080/h2-console
```
Driver is :-

```bash
jdbc:h2:mem:testdb
```
Now, If we see above sample request body then it expects customer name and valid items that are present in DB.So, for testing few items has been added. 

Please use console to view item table.

Now, using postman fire request and response will contain "billAmount" field.

For Example:-

Request:-
```json
{
 "customerName": "Rakesh Singh",
 "items": [{
 	"itemName": "shirt"
 }
 ]
}
```
Bill amount would be 20, as it is NONGROCERY and customer doesn't full fill any criteria for discount.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)