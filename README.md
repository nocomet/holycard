### swagger
```
http://localhost:8080/swagger-ui/
```

### 빌드 & 실행
```
$ chomd 777 ./gradlew
$ ./gradlew build
$ cd ./build/libs
$ java -jar mydefault-0.0.1-SNAPSHOT.jar
```

### 테스트 명령
```
$ curl --request POST \
  --url http://127.0.0.1:8080/book/test-book\
  --header 'Content-Type: application/json' \
  --data '{
        "author": "nostar"
}'
{"bookId":1,"bookName":"test-book","bookAuthor":"nostar"}

$ curl --request GET \
  --url http://127.0.0.1:8080/book/1
{"bookId":1,"bookName":"test-book","bookAuthor":"nostar"}
```
