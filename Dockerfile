FROM openjdk:21
WORKDIR /opt
ENV PORT 8080
EXPOSE 8080
ENV PROFILE=dev
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
COPY target/*-SNAPSHOT.jar /opt/app.jar
ENTRYPOINT ["/wait-for-it.sh", "mysqldb:3306", "-t", "5", "--", "java", "-jar", "/opt/app.jar"]
