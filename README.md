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
