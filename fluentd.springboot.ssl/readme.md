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

_..\..\bin\keytool -import -trustcacerts -keystore cacerts -storepass changeit -noprompt -alias td-agent -file path\to\certificate.crt_


