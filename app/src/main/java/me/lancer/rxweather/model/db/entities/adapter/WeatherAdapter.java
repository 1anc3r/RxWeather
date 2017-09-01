package me.lancer.rxweather.model.db.entities.adapter;

import me.lancer.rxweather.model.db.entities.minimalist.AirQualityLive;
import me.lancer.rxweather.model.db.entities.minimalist.LifeIndex;
import me.lancer.rxweather.model.db.entities.minimalist.Weather;
import me.lancer.rxweather.model.db.entities.minimalist.WeatherForecast;
import me.lancer.rxweather.model.db.entities.minimalist.WeatherLive;

import java.util.List;

/**
 * @author 1anc3r
 *         16/2/25
 */
public abstract class WeatherAdapter {

    public abstract String getCityId();

    public abstract String getCityName();

    public abstract String getCityNameEn();

    public abstract WeatherLive getWeatherLive();

    public abstract List<WeatherForecast> getWeatherForecasts();

    public abstract List<LifeIndex> getLifeIndexes();

    public abstract AirQualityLive getAirQualityLive();

    public Weather getWeather() {

        Weather weather = new Weather();
        weather.setCityId(getCityId());
        weather.setCityName(getCityName());
        weather.setCityNameEn(getCityNameEn());
        weather.setAirQualityLive(getAirQualityLive());
        weather.setWeatherForecasts(getWeatherForecasts());
        weather.setLifeIndexes(getLifeIndexes());
        weather.setWeatherLive(getWeatherLive());
        return weather;
    }
}
