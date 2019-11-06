# Boot Your Reactor Kotlin Coroutines!

[[toc]]

Build Status: [![Build Status](https://travis-ci.org/daggerok/boot-your-reactor-kotlin-coroutines.svg?branch=master)](https://travis-ci.org/daggerok/boot-your-reactor-kotlin-coroutines)

## build

```bash
./gradlew
./gradlew -b ./user-message/build.gradle.kts
#docker images -f=reference='daggerok/*'
./gradlew -b ./user-message/build.gradle.kts start-user-message
./gradlew -b ./user-message/build.gradle.kts stop-user-message
#docker images -f=reference='*/user-message'
```
