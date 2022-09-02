package ru.job4j.weather.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.job4j.weather.model.Weather;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Class is a service weather
 */
@Service
public class WeatherService {
    /**
     * Weathers
     */
    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();

    {
        weathers.put(1, new Weather(1, "Msc", 20));
        weathers.put(2, new Weather(2, "SPb", 18));
        weathers.put(3, new Weather(3, "Bryansk", 16));
        weathers.put(4, new Weather(4, "Smolensk", 15));
        weathers.put(5, new Weather(5, "Kiev", 15));
        weathers.put(6, new Weather(6, "Minsk", 15));
    }

    /**
     * @return All weathers
     */
    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    /**
     * Return Mono by given id
     *
     * @param id
     * @return Mono
     */
    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    /**
     * @return max temperature mono
     */
    public Mono<Weather> findHottest() {
        return Mono.justOrEmpty(weathers.values().stream()
                .max(Comparator.comparingInt(Weather::getTemperature)));
    }

    /**
     * @return min temperature mono
     */
    public Mono<Weather> findColdest() {
        return Mono.justOrEmpty(weathers.values().stream()
                .min(Comparator.comparingInt(Weather::getTemperature)));
    }

    /**
     * @param temperature
     * @return Flux greater than given temperature
     */
    public Flux<Weather> findGreater(Integer temperature) {
        return Flux.fromIterable(weathers.values().stream()
                .filter(weather -> weather.getTemperature() >= temperature)
                .collect(Collectors.toList())
        );
    }

    /**
     * @param temperature
     * @return Flux lower than given temperature
     */
    public Flux<Weather> findLower(int temperature) {
        return Flux.fromIterable(weathers.values().stream()
                .filter(weather -> weather.getTemperature() < temperature)
                .collect(Collectors.toList()));
    }
}
