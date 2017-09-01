package me.lancer.rxweather.contract;

import java.util.List;

import me.lancer.rxweather.model.db.entities.City;
import me.lancer.library.presenter.BasePresenter;
import me.lancer.library.view.BaseView;

/**
 * @author 1anc3r
 */
public interface SelectCityContract {

    interface View extends BaseView<Presenter> {

        void displayCities(List<City> cities);
    }

    interface Presenter extends BasePresenter {

        void loadCities();
    }
}
