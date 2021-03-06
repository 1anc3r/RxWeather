package me.lancer.rxweather.model.repository;

import android.content.Context;
import android.text.TextUtils;

import me.lancer.library.util.NetworkUtils;
import me.lancer.rxweather.model.db.dao.WeatherDao;
import me.lancer.rxweather.model.db.entities.adapter.CloudWeatherAdapter;
import me.lancer.rxweather.model.db.entities.adapter.KnowWeatherAdapter;
import me.lancer.rxweather.model.db.entities.adapter.MiWeatherAdapter;
import me.lancer.rxweather.model.db.entities.minimalist.Weather;
import me.lancer.rxweather.model.http.ApiClient;
import me.lancer.rxweather.model.http.ApiConstants;
import me.lancer.rxweather.model.http.entity.envicloud.EnvironmentCloudCityAirLive;
import me.lancer.rxweather.model.http.entity.envicloud.EnvironmentCloudForecast;
import me.lancer.rxweather.model.http.entity.envicloud.EnvironmentCloudWeatherLive;

import java.sql.SQLException;

import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

/**
 * @author 1anc3r
 *         2016/12/10
 */
public class WeatherDataRepository {

    public static Observable<Weather> getWeather(Context context, String cityId, WeatherDao weatherDao, boolean refreshNow) {

        //从数据库获取天气数据
        Observable<Weather> observableForGetWeatherFromDB = Observable.create(subscriber -> {
            try {
                Weather weather = weatherDao.queryWeather(cityId);
                subscriber.onNext(weather);
                subscriber.onCompleted();
            } catch (SQLException e) {
                throw Exceptions.propagate(e);
            }
        });

        if (!NetworkUtils.isNetworkConnected(context))
            return observableForGetWeatherFromDB;

        //从服务端获取天气数据
        Observable<Weather> observableForGetWeatherFromNetWork = null;
        switch (ApiClient.configuration.getDataSourceType()) {
            case ApiConstants.WEATHER_DATA_SOURCE_TYPE_KNOW:
                observableForGetWeatherFromNetWork = ApiClient.weatherService.getKnowWeather(cityId)
                        .map(knowWeather -> new KnowWeatherAdapter(knowWeather).getWeather());
                break;
            case ApiConstants.WEATHER_DATA_SOURCE_TYPE_MI:
                observableForGetWeatherFromNetWork = ApiClient.weatherService.getMiWeather(cityId)
                        .map(miWeather -> new MiWeatherAdapter(miWeather).getWeather());
                break;
            case ApiConstants.WEATHER_DATA_SOURCE_TYPE_ENVIRONMENT_CLOUD:

                Observable<EnvironmentCloudWeatherLive> weatherLiveObservable = ApiClient.environmentCloudWeatherService.getWeatherLive(cityId);
                Observable<EnvironmentCloudForecast> forecastObservable = ApiClient.environmentCloudWeatherService.getWeatherForecast(cityId);
                Observable<EnvironmentCloudCityAirLive> airLiveObservable = ApiClient.environmentCloudWeatherService.getAirLive(cityId);

                observableForGetWeatherFromNetWork = Observable.combineLatest(weatherLiveObservable, forecastObservable, airLiveObservable,
                        (weatherLive, forecast, airLive) -> new CloudWeatherAdapter(weatherLive, forecast, airLive).getWeather());

                break;
        }
        assert observableForGetWeatherFromNetWork != null;
        observableForGetWeatherFromNetWork = observableForGetWeatherFromNetWork.doOnNext(weather -> Schedulers.io().createWorker().schedule(() -> {
            try {
                weatherDao.insertOrUpdateWeather(weather);
            } catch (SQLException e) {
                throw Exceptions.propagate(e);
            }
        }));

        return Observable.concat(observableForGetWeatherFromDB, observableForGetWeatherFromNetWork)
                .filter(weather -> weather != null && !TextUtils.isEmpty(weather.getCityId()))
                .distinct(weather -> weather.getWeatherLive().getTime())
                .takeUntil(weather -> !refreshNow && System.currentTimeMillis() - weather.getWeatherLive().getTime() <= 15 * 60 * 1000);
    }
}
