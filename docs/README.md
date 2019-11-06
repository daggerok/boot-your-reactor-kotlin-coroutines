# Boot Your Reactor Kotlin Coroutines!

[[toc]]

Build Status: [![Build Status](https://travis-ci.org/daggerok/boot-your-reactor-kotlin-coroutines.svg?branch=master)](https://travis-ci.org/daggerok/boot-your-reactor-kotlin-coroutines)

## build user-message

```bash
./gradlew
./gradlew -b ./user-message/build.gradle.kts
#docker images -f=reference='daggerok/user-message'
./gradlew -b ./user-message/build.gradle.kts start-user-message
http :8080
http :8080/users
http :8080/messages
./gradlew -b ./user-message/build.gradle.kts stop-user-message
```

## build all jibs

```bash
for i in bot city user-message ; do ./gradlew -b ./$i/build.gradle.kts ; done
docker images -f=reference='daggerok/*'
docker rmi -f `docker images -q -f=reference='dag*/bot' -f=reference='dag*/city' -f=reference='dag*/user-message'`
```
