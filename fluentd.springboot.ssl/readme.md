# Spring boot + Fluentd 

## What is Fluentd.?
    
Fluentd is an open source data collector, which lets you unify the data collection and consumption for a better use and understanding of data.

https://www.fluentd.org/

## How to log securely

## Step 1
### Making the Fluentd Server SSL enabled 
https://docs.fluentd.org/input/forward#how-to-enable-tls-encryption

## Step 2
### Adding the same fluentd.crt to your java keystore
-Go to your java_home\jre\lib\security

-Run keytool to import certificate:

(Rename path\to\certificate.crt respectively)

_..\\..\bin\keytool -import -trustcacerts -keystore cacerts -storepass changeit -noprompt -alias td-agent -file path\to\certificate.crt_

## Step 3
Create a Sample Spring boot app and add the following pendencies to the pom.xml

#### Maven

```xml
<dependency>
    <groupId>org.komamitsu</groupId>
    <artifactId>fluency-core</artifactId>
    <version>${fluency.version}</version>
</dependency>

<dependency>
    <groupId>org.komamitsu</groupId>
    <artifactId>fluency-fluentd</artifactId>
    <version>${fluency.version}</version>
</dependency>
<dependency>
    <groupId>com.sndyuk</groupId>
    <artifactId>logback-more-appenders</artifactId>
    <version>1.8.0</version>
</dependency>
```

## Step 4

Add a logback.xml with a fluentd appender in it (Please find the sample Here https://github.com/sreyasvpariyath/Pub-Workspace/tree/master/fluentd.springboot.ssl/src/main/resources)

```xml
<remoteHost>localhost</remoteHost>
 <port>24225</port>
 <sslEnabled>true</sslEnabled>
```
Provide your Fluentd Host and port

set *sslEnabled* as true if you do not want the SSL to be enabled,then set this flag as false/just remove it then you will have to  provide the fluentd port which accepts non ssl forwards.

Whatever is the fluentd conf file which I am using can be found in the conf folder(td-agent.conf)

#### Since you are using a logback Fluentd appender, Whatever logs we do in the application will automatically re route to Fluentd.

## References 
- https://github.com/komamitsu/fluency
- https://github.com/sndyuk/logback-more-appenders

Thanks.!



 





