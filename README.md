# Приложение Chat
[![Build Status](https://travis-ci.org/svedentsov/job4j_weather.svg?branch=master)](https://travis-ci.org/svedentsov/job4j_weather)
[![codecov](https://codecov.io/gh/svedentsov/job4j_weather/branch/master/graph/badge.svg)](https://codecov.io/gh/svedentsov/job4j_weather)
![GitHub repo size](https://img.shields.io/github/repo-size/svedentsov/job4j_weather)

Веб приложение для отображения информации о прогнозе погоды.
Работает в режиме реактивного программирования.
Для упрощения понимания, данные хранятся в классе WeatherService.

## Технологии
> Java 16, Spring (Boot, WebFlux), Checkstyle, Maven

## Сборка
1. Скопируйте файлы репозитория в подготовленную директорию
2. Перейдите в директорию и соберите проект в jar-файл
````
mvn clean install
````
3. Запустите проект через консоль
````
java -jar target/weather-1.0.jar
````

## Как пользоваться
Запросы можно отправлять через браузер
+ `GET /all` - получить список "погоды"
+ `GET /get/{id}` - получить "погоду" по её id
+ `GET /hottest` - получить "погоду" с самыми большим значением температуры
+ `GET /coldest` - получить "погоду" с самыми маленьким значением температуры
+ `GET /ciryGreatThen/{temperature}` - получить список "погоды" с температурой больше, указанной в параметре
+ `GET /cityLowThen/{temperature}` - получить список "погоды" с температурой ниже, указанной в параметре

Чтобы приложение работало в реактивном режиме, нужно передавать между слоями объекты в обертках Mono и Flux.
Mono - поток состоящий из одного объекта. Flux - поток состоящий из набора объектов.
В сервисном слое и слое контроллера, мы оборачиваем все объекты в соответствующие обертки.
Для демонстрации сервиса с долгой загрузкой, метод all использует задержку публикации данных.