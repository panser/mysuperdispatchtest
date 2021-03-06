## Task

```
Java backend

[backend-test-tasks.zip](https://www.notion.so/ef809a6a6a7944ab8bd3341867802a4d#bf56588a4ca843059365c77fc08d572d)

For this task you should implement rest endpoints in Spring Boot application. Gradle build script is configured for project. `com.backend.tasks.Application` class is entrypoint for running application. It defines H2 in-memory database bean which is used as database for this project.

Your task is to implement classes for controllers, services and database entities and write tests for them:
1. `com.backend.tasks.repository.Organization`:
a. Map organization with users. Use OneToMany association and map by organization field in User class. Fetch lazy, cascade all
b. Add equals and hashCode methods
2. `com.backend.tasks.repository.User`:
a. Map user with organization by `org_id` field. Use ManyToOne association.
b. Add equals and hashCode methods
3. `com.backend.tasks.controller.OrganizationController`:
a. Implement rest controller endpoints for organization and map endpoints to `/orgs` path
b. Post to `/orgs` endpoint should create and return organization. Response status should be 201.
c. Put to `/orgs/{orgId}` endpoint should update, save and return organization with id=orgId.
d. Get to `/orgs/{orgId}` endpoint should fetch and return organization with id=orgId.
e. Delete to `/orgs/{orgId}` endpoint should delete organization with id=orgId. Response status should be 204.
f. Get to `/orgs` endpoint should return list of all organizations
4. `com.backend.tasks.controller.UserController`:
a. Implement create, read, update, delete rest controller endpoints for user and map endpoints to `/orgs/{orgId}/users` path
b. Post to `/orgs/{orgId}/users` endpoint should create and return user for organization with id=orgId. Response status should be 201.
c. Put to `/orgs/{orgId}/users/{userId}` endpoint should update, save and return user with id=userId for organization with id=orgId.
d. Get to `/orgs/{orgId}/users/{userId}` endpoint should fetch and return user with id=userId for organization with id=orgId.
e. Delete to `/orgs/{orgId}/users/{userId}` endpoint should delete user with id=userId for organization with id=orgId. Response status should be 204.
f. Get to `/orgs/{orgId}/users` endpoint should return list of all users for organization with id=orgId
5. Implement `com.backend.tasks.service.org.impl.OrganizationServiceImpl` for using in controller class
6. Implement `com.backend.tasks.service.org.impl.UserServiceImpl` for using in controller class
7. Write tests for implemented controllers and services

```

## Run
```
./gradlew clean bootRun
```
and open in browser [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Excuted notes !!!
I did above task with few notes
  * I put JPO in `domain.entity` package instead `repository`, as for me it's more common project structure  
  * I didn't use interfaces for service layer, as for me it's outdated project structure for current layers project