package me.lancer.rxweather.contract;

import me.lancer.rxweather.model.db.entities.minimalist.Weather;
import me.lancer.library.presenter.BasePresenter;
import me.lancer.library.view.BaseView;

/**
 * @author 1anc3r
 */
public interface HomePageContract {

    interface View extends BaseView<Presenter> {

        void displayWeatherInformation(Weather weather);
    }

    interface Presenter extends BasePresenter {

        void loadWeather(String cityId, boolean refreshNow);
    }
}
