# orgtree
A simple Spring MVC project to display OrgTree for an organization

Software requirements:
1. Java 1.6 or above
2. Apache tomcat 1.7 or above
3. Gradle-3.1 or above
4. Eclipse (For sourcode editing)

Libraries:
1. Spring (Core container and MVC)
2. Gson for JSON serialization/deserialization from APIs
3. Log4j for logging

Deployment:
1. gradle clean build
2. Copy war to $TOMCAT_HOME/webapps/
3. Start tomcat (sh bin/catalina.sh start)


View Organization Tree:
Hit http://localhost:8080/orgtree on your browser










