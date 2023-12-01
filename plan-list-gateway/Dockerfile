FROM hub.c.163.com/library/java:latest
VOLUME /tmp
ENV spring.profiles.active=prd
EXPOSE 9010
ADD target/plan-list-gateway-1.0-SNAPSHOT.jar plan-list-gateway.jar
ENTRYPOINT ["java","-jar","/plan-list-gateway.jar"]
