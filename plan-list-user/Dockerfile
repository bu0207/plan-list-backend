FROM hub.c.163.com/library/java:latest
VOLUME /tmp
ENV spring.profiles.active=prd
EXPOSE 9090
ADD target/plan-list-user-1.0-SNAPSHOT.jar plan-list-user.jar
ENTRYPOINT ["java","-jar","/plan-list-user.jar"]
