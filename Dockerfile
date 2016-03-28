FROM owehrens/java
MAINTAINER Oliver Wehrens <oliver@wehrens.de>

RUN echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list
RUN echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886
RUN apt-get update

RUN apt-get install librxtx-java -y
RUN apt-get clean

EXPOSE 9000
ADD build/libs/jpowermeter.jar /jpowermeter.jar
CMD ["java", "-Ddevice=/dev/ttyUSB0","-Djava.library.path=/usr/lib/jni/", "-jar","/jpowermeter.jar"]
