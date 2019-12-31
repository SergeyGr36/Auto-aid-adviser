#Auto Aid Adviser
Backend for Auto Aid Adviser project

##Building project
JDK 11 need to be installed

From the root dir of a project run

- Windows: 
```gradlew.bat bootJar```
- Mac, Linux: 
```./gradlew bootJar```

to skip test phase add `-x test` to the previous command.

##Switch http to https
Add SSl properties to config file

```
server:
  port: 8443
  ssl:
    enabled: true
    key-alias: auto-aid-adviser
    key-password: aaapass
    key-store-type: JKS
    key-store: classpath:auto-aid-adviser.jks
```
Generate Certificate 

```
keytool -genkey -alias auto-aid-adviser -storetype JKS -keyalg RSA -keysize 2048 -validity 365 -keystore auto-aid-adviser.jks
```

use server.ssl data during generation process 

##Deployment process
Run project

```
java -jar auto-aid-adviser-main/build/libs/auto-aid-adviser-main.jar
```

Extend port

```
sudo iptables -t nat -I PREROUTING -p tcp --dport 443 -j REDIRECT --to-ports 8443
```

