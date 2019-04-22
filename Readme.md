Work Force Management Backend.
Groomed as Restful [Absolutely Stateless] and secured with JSON Web Tokens( industry standard RFC 7519 met).


 _    _            _     __                   
| |  | |          | |   / _|                  
| |  | | ___  _ __| | _| |_ ___  _ __ ___ ___ 
| |/\| |/ _ \| '__| |/ /  _/ _ \| '__/ __/ _ \
\  /\  / (_) | |  |   <| || (_) | | | (_|  __/
 \/  \/ \___/|_|  |_|\_\_| \___/|_|  \___\___|
                                              
                                              
                                                               


# WORK FORCE PLATFORM 
![N|Solid](http://6dtech.co.in/wp-content/uploads/2015/02/6d-logo.png)

Work Force Management is a restful backend built using SPRING Boot basking in the glory of Java 8.
SPRING Security underlies the security.

Gestation Status : Beta

### Starting Up Key points:

  - WFM can be run on Tomcat/JBOSS 9+ (Wildfly)
  - WFM loads the Configurations and Contexts "eventedly".
  - JSON Token is indispensable in case of intended interaction with the system.
  - Error landings : Both standard and custom are incorporated.
  - Supports all conventional HTTP Methods.


### Technological participation

* SPRING Boot - evented I/O for the backend
* SPRING Security - State of the art security module in Java Web applications arena.
* slf4j Logger - For logging purpose
* FTP4j - as for File transfer Protocol.
* Maven - as build mechanism.
 are a few to name majorly.


### Installation

WFM PLATFORM  requires JAVA 8|Maven.
git it Up:
```
git clone git@10.0.0.15:girija/WF_Backend.git
$ cd WF_Backend
$ mvn clean compile package install
#post the patch in say JBOSS WildFly and start the Server.
```

### Collaborative Development

Not open for collaborations.

### TODOs
 - Tests !
 - JAVA Doc Comments.
 

License
----
Sixdee proprietary
