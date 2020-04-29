# Basic Chatroom Spring Websocket App

### Functionality:
- basic spring security (login form NOT registration and plain text pass)
- group chat
- "private" chat ( messages delivered to selected username but printed on the same window with all received messages)
- online/offline status (websocket connect/disconnect respectively)
- spring sessions (uses db instead of tomcat memory to keep track of sessions - automatically creates tables, manipulates and deletes after usage)
- Requires connection with Database.

``` 
-- MySQL script to create table needed for the chatroom app

 CREATE TABLE USERS(
     USER_ID INT NOT NULL AUTO_INCREMENT,
     USER_USERNAME VARCHAR(50) NOT NULL,
     USER_PASSWORD VARCHAR(50) NOT NULL,
     USER_ROLE VARCHAR(50) NOT NULL,
     USER_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (USER_ID)
 );
```