FROM hub.c.163.com/library/java:latest
VOLUME /tmp
ENV spring.profiles.active=prd
EXPOSE 9090
ADD target/plan-list-user-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
