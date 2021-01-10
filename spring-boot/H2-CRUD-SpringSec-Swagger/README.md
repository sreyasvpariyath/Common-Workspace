# gini-skill-challange
Navigate to the root of the project(folder containing pom.xml)<br/>
To build, use command - mvn clean install<br/>
To run, use command - java -jar target/gini-0.0.1-SNAPSHOT.jar<br/>

Users available (As configured in the src/main/resources/application.yml)
------------------------------------------------------------------
	username/password/role - user/user/USER
	username/password/role - admin/admin/ADMIN

Swagger documentation - http://localhost:8080/swagger-ui.html

Health check url - http://localhost:8080/actuator/health

APIs (All apis secured using basic auth)
------------------------------------------------------------------
Save a document
	POST
	http://localhost:8080/v1/documents
	body - {"userid":"userid","type":"pdf|jpg|png","description":"description"}

Update a document
	PUT
	http://localhost:8080/v1/documents/{id}
	body - {"description":"description"}

Get all documents for userid (Only available for ADMIN)
	GET
	http://localhost:8080/v1/documents/{userid}

Get a document by id for a given userid
	GET
	http://localhost:8080/v1/documents/{userid}/{id}

Upload all documents for userid (Only available for ADMIN)
	GET
	http://localhost:8080/v1/documents/upload/{userid}
	
