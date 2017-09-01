package me.lancer.rxweather.model.http.service;

import me.lancer.rxweather.model.http.entity.envicloud.EnvironmentCloudCityAirLive;
import me.lancer.rxweather.model.http.entity.envicloud.EnvironmentCloudForecast;
import me.lancer.rxweather.model.http.entity.envicloud.EnvironmentCloudWeatherLive;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author 1anc3r
 *         2017/2/16
 */
public interface EnvironmentCloudWeatherService {

    /**
     * 获取指定城市的实时天气
     * <p>
     * API地址：http://service.envicloud.cn:8082/v2/weatherlive/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/101020100
     *
     * @param cityId 城市id
     * @return Observable
     */
    @GET("/v2/weatherlive/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/{cityId}")
    Observable<EnvironmentCloudWeatherLive> getWeatherLive(@Path("cityId") String cityId);

    /**
     * 获取指定城市7日天气预报
     * <p>
     * API地址：http://service.envicloud.cn:8082/v2/weatherforecast/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/101020100
     *
     * @param cityId 城市id
     * @return Observable
     */
    @GET("/v2/weatherforecast/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/{cityId}")
    Observable<EnvironmentCloudForecast> getWeatherForecast(@Path("cityId") String cityId);

    /**
     * 获取指定城市的实时空气质量
     * <p>
     * API地址：http://service.envicloud.cn:8082/v2/cityairlive/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/101020100
     *
     * @param cityId 城市id
     * @return Observable
     */
    @GET("/v2/cityairlive/YMFYB256AGFUZZE0ODQ3MZM1MZE2NTU=/{cityId}")
    Observable<EnvironmentCloudCityAirLive> getAirLive(@Path("cityId") String cityId);
}
