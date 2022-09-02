package ru.job4j.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.job4j.weather.model.Weather;
import ru.job4j.weather.service.WeatherService;

import java.time.Duration;

/**
 * Class is a weather controller
 */
@RestController
public class WeatherControl {
    /**
     * Service
     */
    @Autowired
    private WeatherService weatherService;

    /**
     * Получить список "погоды"
     *
     * @return Flux<Weather>
     */
    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Weather> all() {
        Flux<Weather> data = weatherService.all();
        Flux<Long> delay = Flux.interval(Duration.ofSeconds(3));
        return Flux.zip(data, delay).map(Tuple2::getT1);
    }

    /**
     * Получить "погоду" по её id
     *
     * @param id
     * @return mono by id
     */
    @GetMapping(value = "/get/{id}")
    public Mono<Weather> get(@PathVariable Integer id) {
        return weatherService.findById(id);
    }

    /**
     * Получить "погоду" с самыми большим значением температуры
     *
     * @return mono with max temperature
     */
    @GetMapping(value = "/hottest")
    public Mono<Weather> getHottest() {
        return weatherService.findHottest();
    }

    /**
     * Получить "погоду" с самыми маленьким значением температуры
     *
     * @return mono with min temperature
     */
    @GetMapping(value = "/coldest")
    public Mono<Weather> coldest() {
        return weatherService.findColdest();
    }

    /**
     * Получить список "погоды" с температурой больше, указанной в параметре
     *
     * @param temperature
     * @return flux with greater degree by given parameter
     */
    @GetMapping(value = "/cityGreatThen/{temperature}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Weather> getGreaterThen(@PathVariable Integer temperature) {
        Flux<Weather> data = weatherService.findGreater(temperature);
        Flux<Long> delay = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(data, delay).map(Tuple2::getT1);
    }

    /**
     * Получить список "погоды" с температурой ниже, указанной в параметре
     *
     * @param temperature
     * @return flux with low degree by given parameter
     */
    @GetMapping(value = "/cityLowThen/{temperature}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Weather> getLowest(@PathVariable Integer temperature) {
        Flux<Weather> data = weatherService.findLower(temperature);
        Flux<Long> delay = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(data, delay).map(Tuple2::getT1);
    }
}
