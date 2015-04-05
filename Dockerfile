FROM webskin/docker-debian-base:1.0.0
MAINTAINER webskin <mickael.gauvin@gmail.com>

# auto validate license
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections

# update repos
RUN echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list
RUN echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886
RUN apt-get update

# install java
RUN apt-get install oracle-java8-installer -y
RUN apt-get install librxtx-java -y
RUN apt-get clean

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

ADD jpowermeter.jar /jpowermeter.jar
EXPOSE 9000 9001
CMD ["java", "-Ddevice=/dev/ttyUSB0","-Djava.library.path=/usr/lib/jni/", "-jar","/jpowermeter.jar"]