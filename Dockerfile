FROM tomcat:jdk8-openjdk

RUN ["rm", "-fr", "/usr/local/tomcat/webapps/ROOT"]
COPY ./target/Eshop.war /usr/local/tomcat/webapps/ROOT.war
