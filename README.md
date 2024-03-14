# thymeleaf example

Build the plugins

```sh
pushd plugins && mvn package && popd
```

Run the app

```sh
mvn spring-boot:run -pl=app
```

```sh
curl --location 'http://localhost:8080' # plugin list
curl --location 'http://localhost:8080/app' # app template
curl --location 'http://localhost:8080/sample-plugin' # plugin template
```

For prod build create the usual root/plugins folder architecture and place the app jar in the root and the plugin in the plugins folder, then we can use the spring profiles to trigger the switch

```
java -jar testfolder/app-1.0-SNAPSHOT.jar --spring.profiles.active=deployment
```
