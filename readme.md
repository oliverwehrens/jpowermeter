Version 0.1 (a)

A tiny REST Service for reading your power meter at home.

Since 2010 every new house in Germany needs to have a 'smart' power meter. Some (all?) of them have an optical
interface (infrared).

This software uses the jSML Lib from openmuc.org (http://www.openmuc.org/index.php?id=63). It converts the output
from the infrared reader to something useful.

The only documentation I found for the  format used by EMH eHZ-H  is at
http://wiki.volkszaehler.org/hardware/channels/meters/power/edl-ehz/emh-ehz-h1

I got a USB IR Reader like this one:

http://www.energie-zaehler.com/epages/61422236.sf/de_DE/?ObjectPath=/Shops/61422236/Products/EDL-USB-Adapter


SETUP
-----

My setup is:
- Raspberry Pi
- USB IR Reader

The new Raspberry images already come with Java preloaded. Since jSML uses JNI to read do the serial communication
you need to

```
apt-get install librxtx-java
```

BUILDING
--------

```
gradle jar
```

RUNNING
-------

Do a

```
java -Djava.library.path=/usr/lib/jni/ -jar ./jpowermeter.jar
```

To start the service.

If you want to specify a device for your IR reader different than /dev/ttyUSB0 you need to specify it at startup.

```
java -Ddevice=/dev/ttyS0 -Djava.library.path=/usr/lib/jni/ -jar ./jpowermeter-0.1.0.jar  
```

You now can connect to the service on port 9000.

At the moment it pulls the values every 5 seconds from the meter. Doing it every second caused many IO problems on the
Pi.

```
> curl http://localhost:9000/

{
  "date" : 1459172538229,
  "consumptionTotal" : {
    "value" : 23962794,
    "unit" : "WH"
  },
  "consumptionOne" : {
    "value" : 23962794,
    "unit" : "WH"
  },
  "consumptionTwo" : {
    "value" : 0.0,
    "unit" : "WH"
  },
  "consumptionNow" : {
    "value" : 2101.8,
    "unit" : "W"
  },
  "complete" : true
}

```

If you want to run it in test mode without a real IR usb infrared sensor attached use the option
```
-Ddevice=SIMULATED
```

to get a fake reader.

Have fun.

Docker
------

To build the docker image use:

```
docker build -t 'jpm' .
```

To run it on a machine with an USB opto reader:

```
docker run -t -i --rm=true --privileged -v /dev/ttyUSB0:/dev/ttyUSB0 -p 9000:9000 -p 9001:9001 jpm
```

if your USB port is a different one, pass it with the above -v option as /dev/ttyUSB0 to the system.

