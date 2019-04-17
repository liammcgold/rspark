FROM tomcat:9.0.17-jre11

ADD JobAppServer/target/JobAppServer.war /usr/local/tomcat/webapps/ROOT.war
ADD JobAppServer/target/JobAppServer /usr/local/tomcat/webapps/ROOT

EXPOSE 8080

CMD ["catalina.sh", "run"]
