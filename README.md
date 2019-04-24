Overview
========
This promedicus-backend is developed using the latest spring-boot v2.1.14. It requires Java 8 to build and run. It provides the below rest-endpoints. It is configured to listen on port 8080 and its context path is defined as /admission-backend. When it starts up, seeding records will be automatically pre-populated for showcasing purpose. 

a. GET http://localhost:8080/admission-backend/admissions/ - to retrieve a list of admissions.

b. GET http://localhost:8080/admission-backend/admissions/{id} - to retrieve an existing admission record by id.

c. POST http://localhost:8080/admission-backend/admissions/ - to create a new admission record.

d. PUT http://localhost:8080/admission-backend/admissions/{id} - to update an existing admission record by id.

e. DELETE http://localhost:8080/admission-backend/admissions/{id} - to delete an existing admission record by id.


Steps To set up the project
===========================
1. mvn clean install
2. java -jar target/promedicus-backend-0.0.1-SNAPSHOT.jar


Creation of External Admission
===============================
curl -X POST \
  http://localhost:8080/admission-backend/admissions/ \
  -H 'Accept: */*' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:8080' \
  -H 'Postman-Token: 9661ff5e-93da-42bf-9b89-f354f7161645,bf549a03-2007-4f56-b637-994191602db0' \
  -H 'User-Agent: PostmanRuntime/7.11.0' \
  -H 'accept-encoding: gzip, deflate' \
  -H 'cache-control: no-cache' \
  -H 'content-length: 239' \
  -d '    {
        "dateAdmission": null,
        "patientName": "Patient Tom",
        "dateOfBirth": "2010-04-13T12:00:00",
        "gender": "MALE",
        "category": "OUTPATIENT",
        "source": "Hospital external system"
    }'
    
Output from the above curl command
==================================
{
    "@UUID": "52e7592c-2fc1-4606-babb-07080c34da2e",
    "id": 6,
    "dateAdmission": "2019-04-24T21:10:04.255",
    "patientName": "Patient Tom",
    "dateOfBirth": "2010-04-13",
    "gender": "Male",
    "category": "Outpatient",
    "source": "Hospital external system"
}
    
