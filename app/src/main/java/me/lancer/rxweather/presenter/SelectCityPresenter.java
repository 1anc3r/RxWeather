package me.lancer.rxweather.presenter;

import android.content.Context;

import me.lancer.rxweather.ApplicationModule;
import me.lancer.rxweather.contract.SelectCityContract;
import me.lancer.rxweather.model.db.dao.CityDao;
import me.lancer.rxweather.presenter.component.DaggerPresenterComponent;
import me.lancer.rxweather.util.ActivityScoped;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author 1anc3r
 */
@ActivityScoped
public final class SelectCityPresenter implements SelectCityContract.Presenter {

    private final SelectCityContract.View cityListView;

    private CompositeSubscription subscriptions;

    @Inject
    CityDao cityDao;

    @Inject
    SelectCityPresenter(Context context, SelectCityContract.View view) {

        this.cityListView = view;
        this.subscriptions = new CompositeSubscription();
        cityListView.setPresenter(this);

        DaggerPresenterComponent.builder()
                .applicationModule(new ApplicationModule(context))
                .build().inject(this);
    }

    @Override
    public void loadCities() {
        Subscription subscription = Observable.just(cityDao.queryCityList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cityListView::displayCities);
        subscriptions.add(subscription);
    }

    @Override
    public void subscribe() {
        loadCities();
    }

    @Override
    public void unSubscribe() {
        subscriptions.clear();
    }
}
