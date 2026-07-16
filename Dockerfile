FROM tomcat:9-jdk11
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/AlbionMarket-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
ENV JAVA_OPTS="-Xms128m -Xmx350m"
CMD ["catalina.sh", "run"]
