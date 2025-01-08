FROM tomcat:10.1.34-jdk17
LABEL maintainer="cma@ispan.com.tw"

ENV TZ="Asia/Taipei"
EXPOSE 8080/TCP

ARG war_file=xxx.war
ADD ./${war_file} /usr/local/tomcat/webapps/ROOT.war

CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]