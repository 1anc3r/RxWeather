package me.lancer.rxweather.contract;

import me.lancer.library.presenter.BasePresenter;
import me.lancer.library.view.BaseView;
import me.lancer.rxweather.model.db.entities.minimalist.Weather;
import me.lancer.rxweather.presenter.DrawerMenuPresenter;

import java.io.InvalidClassException;
import java.util.List;

/**
 * @author 1anc3r
 *         16/4/16
 */
public interface DrawerContract {

    interface View extends BaseView<DrawerMenuPresenter> {

        void displaySavedCities(List<Weather> weatherList);
    }

    interface Presenter extends BasePresenter {

        void loadSavedCities();

        void deleteCity(String cityId);

        void saveCurrentCityToPreference(String cityId) throws InvalidClassException;
    }
}
