package me.lancer.rxweather.presenter;

import android.content.Context;
import android.widget.Toast;

import me.lancer.library.util.RxSchedulerUtils;
import me.lancer.rxweather.ApplicationModule;
import me.lancer.rxweather.contract.HomePageContract;
import me.lancer.rxweather.model.db.dao.WeatherDao;
import me.lancer.rxweather.model.preference.PreferenceHelper;
import me.lancer.rxweather.model.preference.WeatherSettings;
import me.lancer.rxweather.model.repository.WeatherDataRepository;
import me.lancer.rxweather.presenter.component.DaggerPresenterComponent;
import me.lancer.rxweather.util.ActivityScoped;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author 1anc3r
 */
@ActivityScoped
public final class HomePagePresenter implements HomePageContract.Presenter {

    private final Context context;
    private final HomePageContract.View weatherView;

    private CompositeSubscription subscriptions;

    @Inject
    WeatherDao weatherDao;

    @Inject
    HomePagePresenter(Context context, HomePageContract.View view) {

        this.context = context;
        this.weatherView = view;
        this.subscriptions = new CompositeSubscription();
        weatherView.setPresenter(this);

        DaggerPresenterComponent.builder()
                .applicationModule(new ApplicationModule(context))
                .build().inject(this);
    }

    @Override
    public void subscribe() {
        String cityId = PreferenceHelper.getSharedPreferences().getString(WeatherSettings.SETTINGS_CURRENT_CITY_ID.getId(), "");
        loadWeather(cityId, false);
    }

    @Override
    public void loadWeather(String cityId, boolean refreshNow) {

        Subscription subscription = WeatherDataRepository.getWeather(context, cityId, weatherDao, refreshNow)
                .compose(RxSchedulerUtils.normalSchedulersTransformer())
                .subscribe(weatherView::displayWeatherInformation, throwable -> {
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_LONG).show();
                });
        subscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        subscriptions.clear();
    }
}
