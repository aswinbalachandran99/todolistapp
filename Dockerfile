# Pull base image 
FROM tomcat:9-jre11

# Maintainer 
COPY ./target/todo-list-webapp.war /usr/local/tomcat/webapps
EXPOSE 80
